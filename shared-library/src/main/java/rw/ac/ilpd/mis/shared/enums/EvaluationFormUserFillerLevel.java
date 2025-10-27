package rw.ac.ilpd.mis.shared.enums;

public enum EvaluationFormUserFillerLevel 
{
        INTAKE("Intake"), 
        PROGRAM("Program"), 
        ROLE("Role"), 
        ALUMNI("Alumni"), 
        COMPONENT("Component");

        private final String _name;

        EvaluationFormUserFillerLevel(String name) 
        {
                _name = name;
        }

        public String toString() 
        {
                return _name;
        }

        public static EvaluationFormUserFillerLevel fromString(String name) 
        {
                for (EvaluationFormUserFillerLevel level : EvaluationFormUserFillerLevel.values()) 
                {
                        if (level._name.equalsIgnoreCase(name)) 
                        {
                                return level;
                        }
                }
                throw new IllegalArgumentException("No constant with name " + name + " found");
        }
}
