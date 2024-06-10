package application.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import application.model.Receita;
import application.repository.ReceitaRepository;
import application.repository.CategoriaRepository;
import application.repository.IngredienteRepository;

@RestController
@RequestMapping("/receitas")
public class ReceitaController {
    @Autowired
    private ReceitaRepository receitaRepo;

    @Autowired
    private CategoriaRepository categoriaRepo;

    @Autowired
    private IngredienteRepository ingredienteRepo;

    @GetMapping
    public Iterable<Receita> getAll() {
        return receitaRepo.findAll();
    }

    @GetMapping("/{id}")
    public Receita getOne(@PathVariable long id) {
        Optional<Receita> result = receitaRepo.findById(id);
        if(result.isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Receita Não Encontrada"
            );
        }
        return result.get();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        if(receitaRepo.existsById(id)) {
            receitaRepo.deleteById(id);
        } else {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Receita Não Encontrada"
            );
        }
    }

    @PostMapping
    public Receita post(@RequestBody Receita receita) {
        if(!categoriaRepo.existsById(receita.getCategoria().getId())) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Categoria vinculada não encontrada"
            );
        }
        if(!ingredienteRepo.existsById(receita.getIngrediente().getId())) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Ingrediente vinculado não encontrado"
            );
        }
        return receitaRepo.save(receita);
    }

    @PutMapping("/{id}")
    public Receita put(@RequestBody Receita receita, @PathVariable long id) {
        Optional<Receita> result = receitaRepo.findById(id);
        if(result.isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Receita Não Encontrada"
            );
        }
        if(!categoriaRepo.existsById(receita.getCategoria().getId())) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Categoria vinculada não encontrada"
            );
        }
        if(!ingredienteRepo.existsById(receita.getIngrediente().getId())) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Ingrediente vinculado não encontrado"
            );
        }
        Receita receitaAtualizada = result.get();
        receitaAtualizada.setNome(receita.getNome());
        receitaAtualizada.setInstrucoes(receita.getInstrucoes());
        receitaAtualizada.setCategoria(receita.getCategoria());
        receitaAtualizada.setIngrediente(receita.getIngrediente());

        return receitaRepo.save(receitaAtualizada);
    }
}
