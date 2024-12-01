package pe.edu.vallegrande.monitoreo.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
@AllArgsConstructor
@Table(name = "person")
public class Persona {

    public Persona() {
        //TODO Auto-generated constructor stub
    }

    @Id
    @Column("id_person")
    private Integer idPerson;

    @Column("name")
    private String name;

    @Column("surname")
    private String surname;

    @Column("type_document")
    private String typeDocument;

    @Column("document_number")
    private String documentNumber;
    
    @Column("type_kinship")
    private String typeKinship;

    @Column("education_id_education")
    private Integer educationIdEducation;

    @Column("health_id_headlth")
    private Integer healthIdHealth;

    @Column("family_id")
    private Integer familiaId;
    
    @Column("state")
    private String state;

}

