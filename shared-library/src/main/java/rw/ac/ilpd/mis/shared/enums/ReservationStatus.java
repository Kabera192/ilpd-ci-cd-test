/*
 * ReservationStatus.java
 *
 * Authors: Mohamed Gaye, Kabera Clapton(ckabera6@gmail.com)
 *
 * This is an enum that contains all the possible statuses a reservation might undergo
 * from time to time.
 * */
package rw.ac.ilpd.mis.shared.enums;

public enum ReservationStatus
{
    ONGOING("Ongoing"),
    CANCELLED("Cancelled"),
    ACCEPTED("Accepted"),
    REJECTED("Rejected"),
    COMPLETED("Completed");

    private final String _name;

    ReservationStatus(String name)
    {
        _name = name;
    }

    public boolean equals(String name)
    {
        return _name.equals(name);
    }

    public String toString()
    {
        return _name;
    }
}
