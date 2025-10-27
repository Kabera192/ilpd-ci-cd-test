
package rw.ac.ilpd.reportingservice.model.nosql.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document(collection = "report_evaluation_form_options")
public class EvaluationFormOption {
    @Id
    private String id;
    private  String questionId;
    private  String optionText;
    private  Boolean isCorrect;

}