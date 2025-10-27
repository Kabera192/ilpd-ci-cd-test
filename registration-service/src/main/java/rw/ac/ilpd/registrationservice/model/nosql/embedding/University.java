package rw.ac.ilpd.registrationservice.model.nosql.embedding;

import org.springframework.data.annotation.Id;

/**
 * This entity records a list of all universities so that applicants can select which
 * university they have attended. Applicants will be able to add a new university in case
 * their particular university is not in the list.
 */
public class University {
    @Id
    private String id;

    private String name;

    private String country;

    public University() {
    }

    public University(String id, String name, String country)
    {
        this.id = id;
        this.name = name;
        this.country = country;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getCountry()
    {
        return country;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }

    @Override
    public String toString()
    {
        return "University{" + "id: '" + id + '\'' + ", name: '" + name + '\'' + ", country: '" + country + '\'' + '}';
    }
}