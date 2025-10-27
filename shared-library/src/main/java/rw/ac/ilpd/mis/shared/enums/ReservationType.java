/*
 * ReservationType.java
 *
 * Authors: Kabera Clapton(ckabera6@gmail.com)
 *
 * This is an enum that contains all the possible reservation types that are
 * currently handled in hostel management
 * */
package rw.ac.ilpd.mis.shared.enums;

public enum ReservationType
{
    GRANTED("Granted"),
    NON_GRANTED("Non Granted");

    private final String _name;

    ReservationType(String name)
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
