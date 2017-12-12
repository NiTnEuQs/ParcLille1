package fr.univ_lille1.parclille1.typeconverters;

import android.arch.persistence.room.TypeConverter;

import java.util.regex.PatternSyntaxException;

import fr.univ_lille1.parclille1.model.Coordinate;

/**
 * Ce TypeConverter converti une coordonnée (Coordinate) en une chaîne de caractère (String) ainsi
 * que l'inverse. Cette classe est nécessaire pour pouvoir stocker une coordonnée dans la
 * base de données.
 */
public class CoordinateTypeConverter {

    /**
     * Converti une chaîne de caractères (String) en une coordonnée (Coordinate)
     *
     * @param value la chaîne de caractère de la coordonnée
     * @return une coordonnée correspondant à la chaîne de caractère passée en paramètre
     */
    @TypeConverter
    public static Coordinate toCoordinate(String value) {
        try {
            float latitude = Float.parseFloat(value.split(";")[0].replace(',', '.'));
            float longitude = Float.parseFloat(value.split(";")[1].replace(',', '.'));

            return new Coordinate(latitude, longitude);
        } catch(PatternSyntaxException e) {
            return null;
        }
    }

    /**
     * Converti une coordonnée (Coordinate) en une chaîne de caractères (String)
     *
     * @param value une coordonnée correspondant à la chaîne de caractère passée en paramètre
     * @return la chaîne de caractère de la coordonnée
     */
    @TypeConverter
    public static String toString(Coordinate value) {
        return value == null ? null : value.toString();
    }

}
