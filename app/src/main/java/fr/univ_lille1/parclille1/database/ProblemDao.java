package fr.univ_lille1.parclille1.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import fr.univ_lille1.parclille1.model.Problem;

/**
 * La DAO permet de faire le lien entre l'application et la base de données. On peut y référencer
 * des requêtes SQL pour ensuite les utiliser dans l'application.
 */
@Dao
public interface ProblemDao {

    @Query("SELECT * FROM problems")
    List<Problem> getAll();

    @Query("SELECT * FROM problems WHERE pid = :pid")
    Problem getById(int pid);

    @Insert
    void insert(Problem... problems);

    @Delete
    void delete(Problem problem);

}
