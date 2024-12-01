package pe.edu.vallegrande.monitoreo.service.impl;

import lombok.extern.slf4j.Slf4j;
import pe.edu.vallegrande.monitoreo.dto.PersonaRequest;
import pe.edu.vallegrande.monitoreo.dto.PersonaUpdateDTO;
import pe.edu.vallegrande.monitoreo.dto.PersonaWithDetailsDTO;
import pe.edu.vallegrande.monitoreo.model.Education;
import pe.edu.vallegrande.monitoreo.model.Health;
import pe.edu.vallegrande.monitoreo.model.Persona;
import pe.edu.vallegrande.monitoreo.repository.EducationRepository;
import pe.edu.vallegrande.monitoreo.repository.HealthRepository;
import pe.edu.vallegrande.monitoreo.repository.PersonaRepository;
import pe.edu.vallegrande.monitoreo.service.PersonaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class PersonaServiceImpl implements PersonaService {

    @Autowired
    PersonaRepository personaRepository;

    @Autowired
    private EducationRepository educationRepository;

    @Autowired
    private HealthRepository healthRepository;


    @Autowired
    public PersonaServiceImpl(PersonaRepository personaRepository,
                              EducationRepository educationRepository,
                              HealthRepository healthRepository) {
        this.personaRepository = personaRepository;
        this.educationRepository = educationRepository;
        this.healthRepository = healthRepository;
    }


    @Override
    public Mono<Persona> findStudentById(Integer id) {
        log.info("Request to find student by ID: {}", id);
        return personaRepository.findById(id)
                .doOnSuccess(persona -> log.info("Found student: {}", persona))
                .doOnError(error -> log.error("Error finding student by ID: {}", id, error));
    }


    @Override
    public Mono<Persona> findPersonaInactive(Integer id) {
        log.info("Solicitud para inactivar al persona con ID: {}", id);
        return personaRepository.findById(id)
                .flatMap(persona -> {
                    if (persona != null) {
                        persona.setState("I");
                        return personaRepository.save(persona)
                                .doOnSuccess(updatedPersona -> log.info("Persona inactivado: {}", updatedPersona))
                                .doOnError(error -> log.error("Error al actualizar el estado de la persona a inactivo para el ID: {}", id, error));
                    } else {
                        log.warn("Persona no encontrado con el ID: {}", id);
                        return Mono.empty();
                    }
                });
    }

    @Override
    public Mono<Persona> findPersonaRestore(Integer id) {
        log.info("Solicitud para activar persona con ID: {}", id);
        return personaRepository.findById(id)
                .flatMap(persona -> {
                    if (persona != null) {
                        persona.setState("A");
                        return personaRepository.save(persona)
                                .doOnSuccess(updatedPersona -> log.info("Persona activado: {}", updatedPersona))
                                .doOnError(error -> log.error("Error al actualizar el estado de la persona a activo para el ID: {}", id, error));
                    } else {
                        log.warn("Persona no encontrado con el ID: {}", id);
                        return Mono.empty();
                    }
                });
    }


    @Override
    public Flux<Persona> findAllStudents() {
        log.info("Request to find all students");
        return personaRepository.findAll()
                .doOnComplete(() -> log.info("Successfully retrieved all students"))
                .doOnError(error -> log.error("Error finding all students", error));
    }

    @Override
    public Mono<Void> deleteById(Integer id) {
        log.info("Request to delete student by ID: {}", id);
        return personaRepository.deleteById(id)
                .doOnSuccess(unused -> log.info("Successfully deleted student with ID: {}", id))
                .doOnError(error -> log.error("Error deleting student by ID: {}", id, error));
    }

    @Override
    public Flux<Persona> getActivePersons() {
        return personaRepository.findByState("A");
    }

    @Override
    public Flux<Persona> getInactivePersons() {
        return personaRepository.findByState("I");
    }

    @Override
    public Mono<Persona> updatePersona(Integer id, PersonaUpdateDTO updateDTO) {
        return personaRepository.findById(id)
                .flatMap(persona -> {
                    persona.setName(updateDTO.getPersona().getName());
                    persona.setSurname(updateDTO.getPersona().getSurname());
                    persona.setTypeDocument(updateDTO.getPersona().getTypeDocument());
                    persona.setDocumentNumber(updateDTO.getPersona().getDocumentNumber());
                    persona.setTypeKinship(updateDTO.getPersona().getTypeKinship());
                    persona.setFamiliaId(updateDTO.getPersona().getFamiliaId());

                    persona.setEducationIdEducation(updateDTO.getEducation().getIdEducation());
                    persona.setHealthIdHealth(updateDTO.getHealth().getIdHealth());

                    return personaRepository.save(persona);
                })
                .switchIfEmpty(Mono.empty()); 
    }

    
    @Override
    public Flux<PersonaWithDetailsDTO> getAllPersonasWithDetails() {
        return personaRepository.findAll()
                .flatMap(persona -> {
                    Mono<Education> educationMono = educationRepository.findById(persona.getEducationIdEducation());
                    Mono<Health> healthMono = healthRepository.findById(persona.getHealthIdHealth());

                    return Mono.zip(educationMono, healthMono, (education, health) -> {
                        PersonaWithDetailsDTO dto = new PersonaWithDetailsDTO();
                        dto.setIdPerson(persona.getIdPerson());
                        dto.setName(persona.getName());
                        dto.setSurname(persona.getSurname());
                        dto.setTypeDocument(persona.getTypeDocument());
                        dto.setDocumentNumber(persona.getDocumentNumber());
                        dto.setTypeKinship(persona.getTypeKinship());
                        dto.setFamiliaId(persona.getFamiliaId());
                        dto.setState(persona.getState());
                        dto.setEducation(education);
                        dto.setHealth(health);
                        return dto;
                    });
                });
    }







    // Necesario para la parte de registrar

    @Override
    public Mono<Persona> registerPersona(PersonaRequest personaRequest) {
        Education education = new Education(
                personaRequest.getEducation().getIdEducation(),
                personaRequest.getEducation().getGradeBook(),
                personaRequest.getEducation().getGradeAverage(),
                personaRequest.getEducation().getFullNotebook(),
                personaRequest.getEducation().getEducationalAssistance(),
                personaRequest.getEducation().getAcademicTutorias(),
                personaRequest.getEducation().getDegreeStudy());

        return educationRepository.save(education)
                .flatMap(savedEducation -> {
                    Health health = new Health();
                    health.setIdHealth(personaRequest.getHealth().getIdHealth());
                    health.setVaccineSchemes(personaRequest.getHealth().getVaccineSchemes());
                    health.setVph(personaRequest.getHealth().getVph());
                    health.setInfluenza(personaRequest.getHealth().getInfluenza());
                    health.setDeworning(personaRequest.getHealth().getDeworming());
                    health.setHemoglobin(personaRequest.getHealth().getHemoglobin());

                    return healthRepository.save(health)
                            .flatMap(savedHealth -> {
                                Persona persona = new Persona();
                                persona.setName(personaRequest.getName());
                                persona.setSurname(personaRequest.getSurname());
                                persona.setTypeDocument(personaRequest.getTypeDocument());
                                persona.setDocumentNumber(personaRequest.getDocumentNumber());
                                persona.setTypeKinship(personaRequest.getTypeKinship());
                                persona.setFamiliaId(personaRequest.getFamiliaId());
                                persona.setState("A");
                                persona.setEducationIdEducation(savedEducation.getIdEducation());
                                persona.setHealthIdHealth(savedHealth.getIdHealth());

                                return personaRepository.save(persona);
                            });
                });
    }


    @Override
    public Mono<Persona> updatePersona(Integer idPerson, PersonaRequest personaRequest) {
        return personaRepository.findById(idPerson)
            .flatMap(existingPersona -> {

                existingPersona.setName(personaRequest.getName());
                existingPersona.setSurname(personaRequest.getSurname());
                existingPersona.setTypeDocument(personaRequest.getTypeDocument());
                existingPersona.setDocumentNumber(personaRequest.getDocumentNumber());
                existingPersona.setTypeKinship(personaRequest.getTypeKinship());
                existingPersona.setEducationIdEducation(personaRequest.getEducationIdEducation());
                existingPersona.setHealthIdHealth(personaRequest.getHealthIdHealth());
                existingPersona.setFamiliaId(personaRequest.getFamiliaId());
                existingPersona.setState("A");

                if (personaRequest.getEducation() != null) {
                    return educationRepository.findById(personaRequest.getEducation().getIdEducation())
                        .flatMap(existingEducation -> {
                            existingEducation.setGradeBook(personaRequest.getEducation().getGradeBook());
                            existingEducation.setGradeAverage(personaRequest.getEducation().getGradeAverage());
                            existingEducation.setFullNotebook(personaRequest.getEducation().getFullNotebook());
                            existingEducation.setEducationalAssitence(personaRequest.getEducation().getEducationalAssistance());
                            existingEducation.setAcademicTutorias(personaRequest.getEducation().getAcademicTutorias());
                            existingEducation.setDegreeStudy(personaRequest.getEducation().getDegreeStudy());

                            return educationRepository.save(existingEducation)
                                .flatMap(updatedEducation -> {
                                    existingPersona.setEducationIdEducation(updatedEducation.getIdEducation());
                                    return personaRepository.save(existingPersona);
                                });
                        })
                        .switchIfEmpty(Mono.just(existingPersona));  
                }

                if (personaRequest.getHealth() != null) {
                    return healthRepository.findById(personaRequest.getHealth().getIdHealth())
                        .flatMap(existingHealth -> {
                            existingHealth.setVaccineSchemes(personaRequest.getHealth().getVaccineSchemes());
                            existingHealth.setVph(personaRequest.getHealth().getVph());
                            existingHealth.setInfluenza(personaRequest.getHealth().getInfluenza());
                            existingHealth.setDeworning(personaRequest.getHealth().getDeworming());
                            existingHealth.setHemoglobin(personaRequest.getHealth().getHemoglobin());

                            return healthRepository.save(existingHealth)
                                .flatMap(updatedHealth -> {
                                    existingPersona.setHealthIdHealth(updatedHealth.getIdHealth());
                                    return personaRepository.save(existingPersona);
                                });
                        })
                        .switchIfEmpty(Mono.just(existingPersona)); 
                }

                return personaRepository.save(existingPersona);
            })
            .switchIfEmpty(Mono.error(new Exception("Persona no encontrada"))); 
    }



    



    @Override
    public Mono<PersonaWithDetailsDTO> getPersonaWithDetailsById(Integer idPerson) {
        return personaRepository.findById(idPerson)
                .flatMap(persona -> {
                    // Consultamos la información de Education y Health
                    Mono<Education> educationMono = educationRepository.findById(persona.getEducationIdEducation());
                    Mono<Health> healthMono = healthRepository.findById(persona.getHealthIdHealth());

                    // Combinamos ambos Mono (Education y Health) y creamos el DTO
                    return Mono.zip(educationMono, healthMono, (education, health) -> {
                        PersonaWithDetailsDTO dto = new PersonaWithDetailsDTO();
                        dto.setIdPerson(persona.getIdPerson());
                        dto.setName(persona.getName());
                        dto.setSurname(persona.getSurname());
                        dto.setTypeDocument(persona.getTypeDocument());
                        dto.setDocumentNumber(persona.getDocumentNumber());
                        dto.setTypeKinship(persona.getTypeKinship());
                        dto.setFamiliaId(persona.getFamiliaId());
                        dto.setState(persona.getState());
                        dto.setEducation(education);
                        dto.setHealth(health);
                        return dto;
                    });
                });
    }   

}