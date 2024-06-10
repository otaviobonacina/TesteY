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

import application.model.Ingrediente;
import application.repository.IngredienteRepository;

@RestController
@RequestMapping("/ingredientes")
public class IngredienteController {
    @Autowired
    private IngredienteRepository ingredienteRepo;

    @GetMapping
    public Iterable<Ingrediente> getAll() {
        return ingredienteRepo.findAll();
    }

    @GetMapping("/{id}")
    public Ingrediente getOne(@PathVariable long id) {
        Optional<Ingrediente> result = ingredienteRepo.findById(id);
        if(result.isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Ingrediente Não Encontrado"
            );
        }
        return result.get();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        if(ingredienteRepo.existsById(id)) {
            ingredienteRepo.deleteById(id);
        } else {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Ingrediente Não Encontrado"
            );
        }
    }

    @PostMapping
    public Ingrediente post(@RequestBody Ingrediente ingrediente) {
        return ingredienteRepo.save(ingrediente);
    }

    @PutMapping("/{id}")
    public Ingrediente put(@RequestBody Ingrediente ingrediente, @PathVariable long id) {
        Optional<Ingrediente> result = ingredienteRepo.findById(id);
        if(result.isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Ingrediente Não Encontrado"
            );
        }
        Ingrediente ingredienteAtualizado = result.get();
        ingredienteAtualizado.setNome(ingrediente.getNome());

        return ingredienteRepo.save(ingredienteAtualizado);
    }
}
