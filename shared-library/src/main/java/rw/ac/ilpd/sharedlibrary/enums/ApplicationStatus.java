package rw.ac.ilpd.sharedlibrary.enums;

public enum ApplicationStatus
{
    DRAFT("Draft"), PENDING("Pending"), ACCEPTED("Accepted"), REJECTED("Rejected"), DEFERRED("Deferred");

    private final String _name;

    ApplicationStatus(String name)
    {
        _name = name;
    }

    public String toString()
    {
        return _name;
    }

    public ApplicationStatus fromString(String name)
    {
        for (ApplicationStatus status : ApplicationStatus.values())
        {
            if (status._name.equals(name))
                return status;
        }
        throw new IllegalArgumentException("Unknown ApplicationStatus: " + name);
    }
}
