/**
 * DocumentVerificationStatus.java
 *
 * Enum representing the possible statuses for document verification.
 * This can be used to indicate the current state of a document's verification process.
 *
 * Possible statuses include:
 * - PENDING: The document is awaiting verification.
 * - ACCEPTED: The document has been successfully verified.
 * - REJECTED: The document verification has failed.
 * - DEFERRED: the document is to be returned to the applicant for further action.
 *
 * @author  Kabera Clapton (ckabera6@gmail.com)
 * @since   2025-01-04
 */
package rw.ac.ilpd.mis.shared.enums;

public enum DocumentVerificationStatus 
{
    PENDING("Pending"),   // The document is awaiting verification
    ACCEPTED("Accepted"),  // The document has been successfully verified
    REJECTED("Rejected"),  // The document verification has failed
    DEFERRED("Deferred");   // The document is to be returned to the applicant for further action

    private final String _status;

    DocumentVerificationStatus(String status) 
    {
        _status = status;
    }

    public String toString() 
    {
        return _status;
    }

    public boolean equals(String status) 
    {
        return _status.equalsIgnoreCase(status);
    }

    public static DocumentVerificationStatus fromString(String status) 
    {
        for (DocumentVerificationStatus s : DocumentVerificationStatus.values()) 
        {
            if (s.equals(status)) 
            {
                return s;
            }
        }
        throw new IllegalArgumentException("Unknown status: " + status);
    }
}
