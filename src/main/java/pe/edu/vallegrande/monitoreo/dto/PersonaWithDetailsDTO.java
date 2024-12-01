package pe.edu.vallegrande.monitoreo.dto;

import lombok.Data;
import pe.edu.vallegrande.monitoreo.model.Education;
import pe.edu.vallegrande.monitoreo.model.Health;

@Data
public class PersonaWithDetailsDTO {

    private Integer idPerson;  
    private String name;  
    private String surname;   
    private String typeDocument;
    private String documentNumber; 
    private String typeKinship;
    private Integer familiaId; 
    private String state; 
    
    private Education education;  
    private Health health;      

}
