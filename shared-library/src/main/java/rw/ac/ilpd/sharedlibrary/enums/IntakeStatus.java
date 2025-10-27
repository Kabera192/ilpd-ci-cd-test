package rw.ac.ilpd.sharedlibrary.enums;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum IntakeStatus {
    OPEN("Open"), CLOSED("Closed");

    private final String value;

    IntakeStatus(String status)
    {
        value = status;
    }

    public String toString()
    {
        return value;
    }

    public static IntakeStatus fromString(String status)
    {
        for (IntakeStatus intakeStatus : IntakeStatus.values())
        {
            if (intakeStatus.value.equalsIgnoreCase(status))
            {
                log.debug("Converting status: {} to IntakeStatus", status);
                return intakeStatus;
            }
        }

        throw new IllegalArgumentException("Invalid status: " + status);
    }
}
