package pe.edu.vallegrande.monitoreo.service;

import pe.edu.vallegrande.monitoreo.dto.PersonaRequest;
import pe.edu.vallegrande.monitoreo.dto.PersonaUpdateDTO;
import pe.edu.vallegrande.monitoreo.dto.PersonaWithDetailsDTO;
import pe.edu.vallegrande.monitoreo.model.Persona;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PersonaService {

    public Flux<Persona> saveAllStudents(Flux<PersonaWithDetailsDTO> personaWithDetailsDTO);

    Mono<Persona> saveSingleStudent(Persona persona);

    Mono<Persona> findStudentById(Integer id);

    Mono<Persona> findPersonaInactive(Integer id);

    Mono<Persona> findPersonaRestore(Integer id);

    Flux<Persona> findAllStudents();

    Flux<Persona> getInactivePersons();
    Flux<Persona> getActivePersons();

    Mono<Void> deleteById(Integer id);



    
    Mono<Persona> updatePersona(Integer id, PersonaUpdateDTO updateDTO);
    Mono<Persona> registerPersona(PersonaWithDetailsDTO personaWithDetailsDTO);
    Mono<Persona> registerPersona(PersonaRequest personaRequest);




    Flux<PersonaWithDetailsDTO> getAllPersonasWithDetails();

}