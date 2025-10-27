package rw.ac.ilpd.mis.shared.enums;

public enum EngagementType {
    PERMANENT("Permanent"),
    TEMPORARY("Temporary");

    private final String value;

    EngagementType(String status)
    {
        value = status;
    }

    public String toString()
    {
        return value;
    }

    public static EngagementType fromString(String value)
    {
        for (EngagementType status : EngagementType.values())
        {
            if (status.toString().equalsIgnoreCase(value))
                return status;
        }

        throw new IllegalArgumentException("No enum constant " + value + " for Employee Status");
    }
}
