package rw.ac.ilpd.registrationservice.model.nosql.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * This creates a link between an external user with an application in case this user is to be the
 * sponsor of a student who is applying to undertake a diploma program at ILPD.
 */
@Document(collection = "reg_application_sponsor")
public class ApplicationSponsor {
    @Id
    private String id;

    // this references the mongo collection containing ExternalUsers.
    private String sponsorId;

    private String applicationId;

    // References the actual recommendation document in the Document table.
    private String recommendationLetterId;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getSponsorId()
    {
        return sponsorId;
    }

    public void setSponsorId(String sponsorId)
    {
        this.sponsorId = sponsorId;
    }

    public String getApplicationId()
    {
        return applicationId;
    }

    public void setApplicationId(String applicationId)
    {
        this.applicationId = applicationId;
    }

    public String getRecommendationLetterId()
    {
        return recommendationLetterId;
    }

    public void setRecommendationLetterId(String recommendationLetterId)
    {
        this.recommendationLetterId = recommendationLetterId;
    }

    @Override
    public String toString()
    {
        return "ApplicationSponsor{" + "id='" + id + '\'' + ", sponsorId='" + sponsorId + '\'' + ", applicationId='" + applicationId + '\'' + ", recommendationLetterId='" + recommendationLetterId + '\'' + '}';
    }
}
