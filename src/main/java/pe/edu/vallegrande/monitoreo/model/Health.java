// Health.java
package pe.edu.vallegrande.monitoreo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import lombok.Data;

@Data
@Table("health")
public class Health {
    @Id
    @Column("id_headlth")
    private Integer idHealth;
    @Column("vaccine_schemes")
    private String vaccineSchemes;
    private String vph;
    private String influenza;
    @Column("deworming")
    private String deworming;
    private String hemoglobin;
}
