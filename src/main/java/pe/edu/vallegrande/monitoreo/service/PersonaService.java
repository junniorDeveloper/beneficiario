package pe.edu.vallegrande.monitoreo.service;

import pe.edu.vallegrande.monitoreo.dto.PersonaRequest;
import pe.edu.vallegrande.monitoreo.dto.PersonaUpdateDTO;
import pe.edu.vallegrande.monitoreo.dto.PersonaWithDetailsDTO;
import pe.edu.vallegrande.monitoreo.model.Persona;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PersonaService {



    Mono<Persona> findStudentById(Integer id);

    Mono<Persona> findPersonaInactive(Integer id);

    Mono<Persona> findPersonaRestore(Integer id);

    Flux<Persona> findAllStudents();

  
    Mono<Void> deleteById(Integer id);


    Flux<Persona> getInactivePersons();
    Flux<Persona> getActivePersons();
    Mono<Persona> updatePersona(Integer id, PersonaUpdateDTO updateDTO);
    Mono<Persona> registerPersona(PersonaRequest personaRequest);
    Mono<Persona> updatePersona(Integer idPerson, PersonaRequest personaRequest);
    Mono<PersonaWithDetailsDTO> getPersonaWithDetailsById(Integer idPerson);


    Flux<PersonaWithDetailsDTO> getAllPersonasWithDetails();

}