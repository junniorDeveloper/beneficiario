package pe.edu.vallegrande.monitoreo.dto;

import lombok.Data;
import pe.edu.vallegrande.monitoreo.model.Education;
import pe.edu.vallegrande.monitoreo.model.Health;

@Data
public class PersonaWithDetailsDTO {

    private Integer idPerson;  // ID de la persona
    private String name;       // Nombre
    private String surname;    // Apellido
    private String typeDocument;  // Tipo de documento
    private String documentNumber; // Número de documento
    private String typeKinship; // Tipo de parentesco (relación con la persona)
    private Integer familiaId; // ID de la familia, si es necesario para la lógica
    private String state; // Estado (activo, inactivo, etc.)
    
    // Relaciones
    private Education education;  // Entidad Education relacionada
    private Health health;        // Entidad Health relacionada

}
