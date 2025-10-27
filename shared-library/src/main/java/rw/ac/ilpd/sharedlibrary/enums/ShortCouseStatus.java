package rw.ac.ilpd.sharedlibrary.enums;

public enum ShortCouseStatus 
{
    ONGOING("Ongoing"), DONE("Done"), CANCELLED("Cancelled");

    private final String _name;

    ShortCouseStatus(String name) 
    {
        _name = name;
    }

    public String toString() 
    {
        return _name;
    }

    public static ShortCouseStatus fromString(String name) 
    {
        for (ShortCouseStatus status : ShortCouseStatus.values()) 
        {
            if (status._name.equalsIgnoreCase(name)) 
            {
                return status;
            }
        }
        throw new IllegalArgumentException("No constant with text " + name + " found");
    }
}