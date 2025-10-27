
package rw.ac.ilpd.sharedlibrary.enums;

public enum SponsorType
{
    INDIVIDUAL("Individual"),
    ORGANIZATION("Organization");

    private final String _value;

    SponsorType(String value)
    {
        _value = value;
    }

    public String toString()
    {
        return _value;
    }

    public static SponsorType fromString(String value)
    {
        if (value == null)
        {
            throw new IllegalArgumentException("Value cannot be null");
        }

        for (SponsorType type : SponsorType.values())
        {
            if (type.toString().equals(value))
            {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid SponsorType: " + value);
    }

    public boolean equals(String value)
    {
        return _value.equals(value);
    }
}
