package rw.ac.ilpd.sharedlibrary.enums;

public enum EvaluationFormStatus 
{
    PUBLISHED("Published"),
    PRIVATE("Private");

    private final String _name;

    EvaluationFormStatus(String name) 
    {
        _name = name;
    }

    public String toString()
    {
        return _name;
    }

    public static EvaluationFormStatus fromString(String name) 
    {
        for (EvaluationFormStatus status : EvaluationFormStatus.values()) 
        {
            if (status._name.equalsIgnoreCase(name)) 
            {
                return status;
            }
        }
        throw new IllegalArgumentException("No constant with name " + name + " found");
    }
}
