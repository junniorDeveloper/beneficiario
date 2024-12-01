package pe.edu.vallegrande.monitoreo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonaRequest {

    private Integer idPerson;
    private String name;
    private String surname;
    private String typeDocument;
    private String documentNumber;
    private String typeKinship;
    private Integer educationIdEducation;
    private Integer healthIdHealth;
    private Integer familiaId;
    private EducationRequest education;
    private HealthRequest health;
    private String state;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EducationRequest {
        private Integer idEducation;
        private String gradeBook;
        private Integer gradeAverage;
        private String fullNotebook;
        private String educationalAssistance;
        private String academicTutorias;
        private String degreeStudy;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class HealthRequest {
        private Integer idHealth;
        private String vaccineSchemes;
        private String vph;
        private String influenza;
        private String deworming;
        private String hemoglobin;
    }
}
