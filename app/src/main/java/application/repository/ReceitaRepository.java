package application.repository;

import org.springframework.data.repository.CrudRepository;
import application.model.Receita;

public interface ReceitaRepository extends CrudRepository<Receita, Long> {
}