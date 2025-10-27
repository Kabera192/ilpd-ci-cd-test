package rw.ac.ilpd.sharedlibrary.enums;

public enum QuestionType 
{
    RANGE("Range"), 
    OPEN_ENDED("Open Ended"), 
    MCQ("Multiple Choice"), 
    MULTIPLE_CHOICE("Multiple Choice");

    private String _name;

    QuestionType(String name) 
    {
        this._name = name;
    }

    public String toString() 
    {
        return _name;
    }

    public static QuestionType fromString(String name) 
    {
        for (QuestionType type : QuestionType.values()) 
        {
            if (type._name.equalsIgnoreCase(name)) 
            {
                return type;
            }
        }
        throw new IllegalArgumentException("No constant with name " + name + " found");
    }
}