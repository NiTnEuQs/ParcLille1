package fr.univ_lille1.parclille1.typeconverters;

import android.arch.persistence.room.TypeConverter;

import fr.univ_lille1.parclille1.model.ProblemType;

/**
 * Ce TypeConverter converti un type de problème (ProblemType) en une valeur entière (int) ainsi
 * que l'inverse. Cette classe est nécessaire pour pouvoir stocker un type de problème dans la
 * base de données.
 */
public class ProblemTypeTypeConverter {

    /**
     * Converti une valeur entière (int) en un type de problème (ProblemType)
     *
     * @param value valeur entière, "ordinal" du type du problème
     * @return Le type du problème
     */
    @TypeConverter
    public static ProblemType toProblemType(int value) {
        return (value < 0 || value >= ProblemType.values().length)
                ? ProblemType.OTHER
                : ProblemType.values()[value];
    }

    /**
     * Converti un type de problème (ProblemType) en une valeur entière (int)
     *
     * @param value le type du problème
     * @return valuer entière, "ordinal" du type du problème
     */
    @TypeConverter
    public static int toInteger(ProblemType value) {
        return value == null
                ? ProblemType.OTHER.ordinal()
                : value.ordinal();
    }

}
