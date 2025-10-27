/*
 * MaritalStatus.java
 *
 * Authors: Mohamed Gaye, Kabera Clapton(ckabera6@gmail.com)
 *
 * This is an enum that contains all the possible marital statuses that various users
 * of the MIS might subscribe to
 * */
package rw.ac.ilpd.sharedlibrary.enums;

public enum MaritalStatus
{
    SINGLE("Single"),
    MARRIED("Married"),
    DIVORCED("Divorced"),
    WIDOWED("Widowed");

    private final String _name;

    MaritalStatus(String name)
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
