package rw.ac.ilpd.mis.shared.enums;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum IntakeStudentStatus
{
    SUSPENDED("Suspended"),
    ENROLLED("Enrolled"),
    GRADUATED("Graduated"),
    RETAKE("Retake"),
    RESIT("Resit"),
    DROP_OUT("Drop Out");
//
    private final String value;

    IntakeStudentStatus(String status)
    {
        value = status;
    }

    public String toString()
    {
        return value;
    }

    public static IntakeStudentStatus fromString(String status)
    {
        for (IntakeStudentStatus intakeStudentStatus : values())
        {
            if (intakeStudentStatus.value.equalsIgnoreCase(status))
            {
                log.debug("Converting status: {} to IntakeStudentStatus", status);
                return intakeStudentStatus;
            }
        }

        throw new IllegalArgumentException("Unknown intake student status: " + status);
    }

}
