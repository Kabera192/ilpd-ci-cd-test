package rw.ac.ilpd.sharedlibrary.enums;

public enum ItemCategory 
{
    CONSUMABLE("Consumable"), 
    NOT_CONSUMABLE("Not Consumable");

    private final String _name;

    ItemCategory(String name) 
    {
        _name = name;
    }

    public String toString() 
    {
        return _name;
    }

    public static ItemCategory fromString(String name) 
    {
        for (ItemCategory category : ItemCategory.values()) 
        {
            if (category._name.equalsIgnoreCase(name)) 
            {
                return category;
            }
        }
        throw new IllegalArgumentException("No constant with name " + name + " found");
    }
}
