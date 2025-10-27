package rw.ac.ilpd.mis.shared.enums;

public enum ToDoStatus 
{
    APPROVED("Approved"), REJECTED("Rejected"), PENDING("Pending"),
    IN_PROGRESS("IN_PROGRESS"),
    COMPLETED("COMPLETED"),
    CANCELLED("CANCELLED"),
    ON_HOLD("ON_HOLD");
    private final String _name;

    ToDoStatus(String name) 
    {
        _name = name;
    }

    public String toString() 
    {
        return _name;
    }

    public static ToDoStatus fromString(String name) 
    {
        for (ToDoStatus status : ToDoStatus.values()) 
        {
            if (status._name.equalsIgnoreCase(name)) 
            {
                return status;
            }
        }
        throw new IllegalArgumentException("No constant with name " + name + " found");
    }
}