package fr.univ_lille1.parclille1.model;

import java.text.DecimalFormat;

/**
 * Une classe qui représente une latitude et une longitude (Des coordonnées sur un GPS)
 * http://www.java2s.com/Code/Java/2D-Graphics-GUI/Aclasstorepresentalatitudeandlongitude.htm
 */
public class Coordinate {

    // Attributs

    /** La latitude minimale autorisée */
    private static float MIN_LATITUDE = Float.valueOf("-90.0000");

    /** La latitude maximale autorisée */
    private static float MAX_LATITUDE = Float.valueOf("90.0000");

    /** La longitude minimale autorisée */
    private static float MIN_LONGITUDE = Float.valueOf("-180.0000");

    /** La longitude maximale autorisée */
    private static float MAX_LONGITUDE = Float.valueOf("180.0000");

    private float latitude;
    private float longitude;
    private DecimalFormat format;

    /**
     * Constructeur pour la classe Coordinate
     *
     * @param latitude  une coordonnée de latitude en notation décimal
     * @param longitude une coordonnée de longitude en notation décimal
     */
    public Coordinate(float latitude, float longitude) {
        if (isValidLatitude(latitude) && isValidLongitude(longitude)) {
            this.latitude = latitude;
            this.longitude = longitude;
        } else {
            throw new IllegalArgumentException("Les paramètres ne sont pas valides");
        }

        this.format = new DecimalFormat("##.######");
    }

    // Getters/Setters

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        if (isValidLatitude(latitude)) {
            this.latitude = latitude;
        } else {
            throw new IllegalArgumentException("Les paramètres ne sont pas valides");
        }
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        if (isValidLongitude(longitude)) {
            this.longitude = longitude;
        } else {
            throw new IllegalArgumentException("Les paramètres ne sont pas valides");
        }
    }

    // Méthodes

    /**
     * Une méthode qui valide une latitude
     *
     * @param latitude la latitude a vérifier
     *
     * @return         vrai si et seulement si la latitude est entre le MIN et le MAX, faux sinon
     */
    private static boolean isValidLatitude(float latitude) {
        return latitude >= MIN_LATITUDE && latitude <= MAX_LATITUDE;
    }

    /**
     * Une méthode qui valide une longitude
     *
     * @param longitude la longitude a vérifier
     *
     * @return          vrai si et seulement si la longitude est entre le MIN et le MAX, faux sinon
     */
    private static boolean isValidLongitude(float longitude) {
        return longitude >= MIN_LONGITUDE && longitude <= MAX_LONGITUDE;
    }

    /**
     * Permet de récupérer la latitude sous forme d'une chaîne de caractères
     */
    public String getLatitudeAsString() {
        return format.format(latitude).replace(',', '.');
    }

    /**
     * Permet de récupérer la longitude sous forme d'une chaîne de caractères
     */
    public String getLongitudeAsString() {
        return format.format(longitude).replace(',', '.');
    }

    /**
     * Surcharge de la méthode toString()
     *
     * @return une chaîne de caractères avec latitude et longitude
     */
    public String toString() {
        return format.format(latitude) + "; " + format.format(longitude);
    }

}
