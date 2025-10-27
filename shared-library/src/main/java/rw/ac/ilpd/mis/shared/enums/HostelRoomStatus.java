/*
 * HostelRoomStatus.java
 *
 * Authors: Mohamed Gaye, Kabera Clapton(ckabera6@gmail.com)
 *
 * This is an enum that contains all the possible statuses that a hostel room
 * may fall under from time to time.
 * */
package rw.ac.ilpd.mis.shared.enums;

public enum HostelRoomStatus
{
    BOOKED_GRANTED("Booked Granted"),
    BOOKED_NON_GRANTED("Booked Non Granted"),
    OCCUPIED("Occupied"),
    FREE("Free");

    private final String _name;

    HostelRoomStatus(String name)
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