package rw.ac.ilpd.sharedlibrary.enums;

public enum ToDoStatus 
{
    APPROVED("Approved"), REJECTED("Rejected"), PENDING("Pending"),
    IN_PROGRESS("In Progress"),
    COMPLETED("Completed"),
    CANCELLED("Cancelled"),
    ON_HOLD("On Hold"),
    OVERDUE("Overdue");
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