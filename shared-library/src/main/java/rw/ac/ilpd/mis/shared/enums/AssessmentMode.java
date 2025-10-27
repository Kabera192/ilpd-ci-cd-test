package rw.ac.ilpd.mis.shared.enums;

public enum AssessmentMode 
{
    INDIVIDUAL("Individual"), GROUP("Group");

    private final String _name;

    AssessmentMode(String name) 
    {
        _name = name;
    }

    public String toString() 
    {
        return _name;
    }

    public static AssessmentMode fromString(String name) 
    {
        for (AssessmentMode mode : AssessmentMode.values()) 
        {
            if (mode._name.equalsIgnoreCase(name)) 
            {
                return mode;
            }
        }
        throw new IllegalArgumentException("No constant with name " + name + " found");
    }
}