package rw.ac.ilpd.sharedlibrary.enums;

public enum RequestPartyType 
{
    INITIATORS("Intiators"), 
    VALIDATORS("Validators");

    private final String _name;

    RequestPartyType(String name)
    {
        _name = name;
    }

    @Override
    public String toString() 
    {
        return _name;
    }

    public static RequestPartyType fromString(String name) 
    {
        for (RequestPartyType type : RequestPartyType.values())
        {
            if (type._name.equalsIgnoreCase(name))
                return type;
        }
        throw new IllegalArgumentException("Request Party type: " + name + " not found.");
    }

}