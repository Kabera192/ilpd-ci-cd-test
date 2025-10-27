package rw.ac.ilpd.sharedlibrary.enums;

public enum RequestApprovalStatus 
{
    APPROVED("Approved"), 
    REJECTED("Rejected");

    private final String _name;

    RequestApprovalStatus(String name)
    {
        _name = name;
    }

    public String toString()
    {
        return _name;
    }

    public static RequestApprovalStatus fromString(String name)
    {
        for (RequestApprovalStatus status : RequestApprovalStatus.values())
        {
            if (status._name.equalsIgnoreCase(name))
                return status;
        }

        throw new IllegalArgumentException("Status with the name: " + name + " not found.");
    }
}