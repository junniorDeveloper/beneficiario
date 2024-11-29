// EducationRepository.java
package pe.edu.vallegrande.monitoreo.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import pe.edu.vallegrande.monitoreo.model.Education;

public interface EducationRepository extends ReactiveCrudRepository<Education, Integer> {
}
