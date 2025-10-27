package rw.ac.ilpd.sharedlibrary.enums;

public enum Relationship 
{
    PARENT("Parent"),
    CHILD("Child"),
    SIBLING("Sibling"),
    FRIEND("Friend"),
    CO_WORKER("Co-Worker"),
    OTHER("Other");

    private final String _value;

    Relationship(String value) 
    {
        _value = value;
    }

    public String toString() 
    {
        return _value;
    }

    public static Relationship fromString(String value) 
    {
        if (value == null) 
        {
            throw new IllegalArgumentException("Value cannot be null");
        }
        for (Relationship relationship : Relationship.values()) 
        {
            if (relationship.toString().equals(value))   
            {
                return relationship;
            }
        }
        throw new IllegalArgumentException("Invalid Relationship: " + value);
    }
}
