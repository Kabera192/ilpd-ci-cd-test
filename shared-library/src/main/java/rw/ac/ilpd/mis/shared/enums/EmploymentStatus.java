package rw.ac.ilpd.mis.shared.enums;

public enum EmploymentStatus {
    ACTIVE("Active"),
    INACTIVE("Inactive");
    private final String value;

    EmploymentStatus(String status)
    {
        value = status;
    }

    public String toString()
    {
        return value;
    }

    public static EmploymentStatus fromString(String value)
    {
        for (EmploymentStatus status : EmploymentStatus.values())
        {
            if (status.toString().equalsIgnoreCase(value))
                return status;
        }

        throw new IllegalArgumentException("No enum constant " + value + " for EmployeeStatus");
    }
}
