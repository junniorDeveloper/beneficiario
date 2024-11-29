// HealthRepository.java
package pe.edu.vallegrande.monitoreo.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import pe.edu.vallegrande.monitoreo.model.Health;

public interface HealthRepository extends ReactiveCrudRepository<Health, Integer> {
}
