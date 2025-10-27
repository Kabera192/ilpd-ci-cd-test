package rw.ac.ilpd.sharedlibrary.enums;

public enum AssessmentStatus 
{
    CONSIDERED("Considered"), NOT_CONSIDERED("Not Considered");

    private final String _name;

    AssessmentStatus(String name) {
        _name = name;
    }

    public String toString() 
    {
        return _name;
    }

    public static AssessmentStatus fromString(String name) 
    {
        for (AssessmentStatus status : AssessmentStatus.values()) 
        {
            if (status._name.equalsIgnoreCase(name)) 
            {
                return status;
            }
        }
        throw new IllegalArgumentException("No constant with name " + name + " found");
    }
}