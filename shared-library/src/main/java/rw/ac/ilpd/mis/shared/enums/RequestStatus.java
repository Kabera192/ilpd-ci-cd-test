package rw.ac.ilpd.mis.shared.enums;

public enum RequestStatus 
{
    PENDING("Pending"), ON_REVIEW("On Review"), APPROVED("Approved");

    private final String _name;

    RequestStatus(String name) 
    {
        this._name = name;
    }

    public String toString() 
    {
        return _name;
    }

    public static RequestStatus fromString(String name) 
    {
        for (RequestStatus status : RequestStatus.values()) 
        {
            if (status._name.equalsIgnoreCase(name)) 
            {
                return status;
            }
        }
        throw new IllegalArgumentException("No constant with name " + name + " found");
    }
}
