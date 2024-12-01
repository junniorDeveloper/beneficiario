package pe.edu.vallegrande.monitoreo.rest;

import lombok.extern.slf4j.Slf4j;
import pe.edu.vallegrande.monitoreo.dto.PersonaUpdateDTO;
import pe.edu.vallegrande.monitoreo.dto.PersonaWithDetailsDTO;
import pe.edu.vallegrande.monitoreo.model.Persona;
import pe.edu.vallegrande.monitoreo.service.impl.PersonaServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@CrossOrigin(origins = "*")
@Slf4j
@RestController
@RequestMapping("/persona")
public class PersonaRest {

    @Autowired
    private PersonaServiceImpl personaService;

    @PostMapping
    public Mono<Persona> saveStudent(@RequestBody Persona persona) {
        log.info("Request to save student: {}", persona);
        return personaService.saveSingleStudent(persona)
                .doOnSuccess(savedPersona -> log.info("Exito persona registrada: {}", savedPersona))
                .doOnError(error -> log.error("Error al guardar persona: {}", persona, error));
    }

    @PostMapping("/registrar_varios")
    public Flux<Persona> saveAllStudents(@RequestBody Flux<PersonaWithDetailsDTO> personaWithDetailsDTO) {
        log.info("Request to save batch of students with education and health");
        
        return personaService.saveAllStudents(personaWithDetailsDTO)
                .doOnComplete(() -> log.info("Successfully saved all students with education and health"))
                .doOnError(error -> log.error("Error saving batch of students with education and health", error));
    }


    @GetMapping("/listadoDetalle")
    public Flux<PersonaWithDetailsDTO> getAllPersonasWithDetails() {
        return personaService.getAllPersonasWithDetails();
    }

    @GetMapping("/{id}")
    public Mono<Persona> findStudentById(@PathVariable Integer id) {
        log.info("Request to find student by ID: {}", id);
        return personaService.findStudentById(id)
                .doOnSuccess(persona -> log.info("Found student: {}", persona))
                .doOnError(error -> log.error("Error finding student by ID: {}", id, error));
    }

    @GetMapping
    public Flux<Persona> findAllStudents() {
        log.info("Request to find all students");
        return personaService.findAllStudents()
                .doOnComplete(() -> log.info("Successfully retrieved all students"))
                .doOnError(error -> log.error("Error finding all students", error));
    }




    @GetMapping("/personas/ListaActivos")
    public Flux<Persona> getActivePersons() {
        return personaService.getActivePersons();
    }

    @GetMapping("/personas/ListaInactivos")
    public Flux<Persona> getInactivePersons() {
        return personaService.getInactivePersons();
    }

    @DeleteMapping("/{id}/eliminadoFisico")
    public Mono<String> deleteStudentById(@PathVariable Integer id) {
        log.info("Request to delete student by ID: {}", id);
        return personaService.findStudentById(id)
                .flatMap(persona -> personaService.deleteById(id)
                        .then(Mono.just("Persona con el ID: " + id + " eliminado"))
                        .doOnSuccess(msg -> log.info(msg))
                        .doOnError(error -> log.error("Error deleting student by ID: {}", id, error)));
    }


    @PutMapping("/{id}/inactivar")
    public Mono<ResponseEntity<String>> inactivateStudent(@PathVariable Integer id) {
        return personaService.findPersonaInactive(id)
                .map(persona -> ResponseEntity.ok("Persona con ID " + id + " inactivado correctamente."))
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Persona con ID " + id + " no encontrado.")))
                .onErrorResume(error -> {
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("Ocurrió un error al intentar inactivar persona."));
                });
    }

    @PutMapping("/{id}/restore")
    public Mono<ResponseEntity<String>> restoreStudent(@PathVariable Integer id) {
        return personaService.findPersonaRestore(id)
                .map(persona -> ResponseEntity.ok("Persona con ID " + id + " activada correctamente."))
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Persona con ID " + id + " no encontrado.")))
                .onErrorResume(error -> {
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("Ocurrió un error al intentar activar persona."));
                });
    }


  

   @PutMapping("/{id}")
    public Mono<ResponseEntity<Persona>> updatePersona(@PathVariable("id") Integer id,
                                                       @RequestBody PersonaUpdateDTO updateDTO) {
        return personaService.updatePersona(id, updateDTO)
                .map(persona -> ResponseEntity.ok(persona)) 
                .defaultIfEmpty(ResponseEntity.notFound().build()); 
    }
    

    @PostMapping("/registrar")
    public Mono<ResponseEntity<String>> registerPersona(@RequestBody PersonaWithDetailsDTO personaWithDetailsDTO) {
        return personaService.registerPersona(personaWithDetailsDTO)
                .map(persona -> ResponseEntity.ok("Persona registrada con éxito."))
                .onErrorResume(e -> Mono
                        .just(ResponseEntity.badRequest().body("Error al registrar la persona: " + e.getMessage())));
    }
}
