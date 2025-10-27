/*
 * Gender.java
 *
 * Authors: Mohamed Gaye, Kabera Clapton(ckabera6@gmail.com)
 *
 * This is an enum that contains all the possible genders that will be used in the MIS
 * */
package rw.ac.ilpd.sharedlibrary.enums;

public enum Gender {
    MALE("Male"),
    FEMALE("Female");

    private final String _name;

    Gender(String name)
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
