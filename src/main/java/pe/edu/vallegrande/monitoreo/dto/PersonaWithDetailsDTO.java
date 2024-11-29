package pe.edu.vallegrande.monitoreo.dto;

import lombok.Data;
import pe.edu.vallegrande.monitoreo.model.Persona;
import pe.edu.vallegrande.monitoreo.model.Education;
import pe.edu.vallegrande.monitoreo.model.Health;

@Data 
public class PersonaWithDetailsDTO {

    private Persona persona;
    private Education education;
    private Health health;

}
