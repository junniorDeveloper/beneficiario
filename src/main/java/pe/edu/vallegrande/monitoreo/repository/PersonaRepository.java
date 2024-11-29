package pe.edu.vallegrande.monitoreo.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import pe.edu.vallegrande.monitoreo.model.Persona;
import reactor.core.publisher.Mono;

@Repository
public interface PersonaRepository extends ReactiveCrudRepository<Persona,Integer> {
     Mono<Persona> findById(Integer id);
}