package pe.edu.vallegrande.monitoreo.dto;

import lombok.Data;
import pe.edu.vallegrande.monitoreo.model.Education;
import pe.edu.vallegrande.monitoreo.model.Health;
import pe.edu.vallegrande.monitoreo.model.Persona;

@Data
public class PersonaUpdateDTO {

    private Persona persona;
    private Education education;
    private Health health;

}
