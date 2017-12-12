package fr.univ_lille1.parclille1.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * La classe Problem est le modèle d'un problème que l'on peut recenser dans le parc. Elle est aussi
 * nécessaire pour créer la base de données et y stocker les informations dans une table.
 */
@Entity(tableName = "problems")
public class Problem {

    // Attributs

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "pid")
    private long pid;

    @ColumnInfo(name = "type")
    private ProblemType type;

    @ColumnInfo(name = "coordinate")
    private Coordinate coordinate;

    @ColumnInfo(name = "address")
    private String address;

    @ColumnInfo(name = "description")
    private String description;

    // Constructeurrs

    /**
     * Pas de constructeurs ici car nous n'instancions jamais la valeur pid qui est auto générée
     * Nous pouvons cependant utiliser les Getters/Setters ci-dessous pour assigner les valeurs
     */

    // Getters/Setters


    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public ProblemType getType() {
        return type;
    }

    public void setType(ProblemType type) {
        this.type = type;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Méthodes

    @Override
    public String toString() {
        return type + "\n" + address;
    }

}
