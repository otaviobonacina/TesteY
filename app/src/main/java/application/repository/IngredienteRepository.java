package application.repository;

import org.springframework.data.repository.CrudRepository;
import application.model.Ingrediente;

public interface IngredienteRepository extends CrudRepository<Ingrediente, Long> {
}