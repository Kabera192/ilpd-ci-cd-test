package rw.ac.ilpd.reportingservice.model.nosql.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document("report_evaluated_users")
public class EvaluatedUser {
    @Id
    private String id;
    private  String  evaluationFormId;
    private UUID userId;

}
