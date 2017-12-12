package fr.univ_lille1.parclille1.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import fr.univ_lille1.parclille1.model.Problem;
import fr.univ_lille1.parclille1.typeconverters.CoordinateTypeConverter;
import fr.univ_lille1.parclille1.typeconverters.ProblemTypeTypeConverter;

/**
 * Cette classe sert à implémenter la base de données. Elle a un lien direct avec la DAO.
 */
@Database(entities = {Problem.class}, version = 1, exportSchema = false)
@TypeConverters({ProblemTypeTypeConverter.class, CoordinateTypeConverter.class})
public abstract class ProblemsDatabase extends RoomDatabase {

    /**
     * L'instance de la base de données
     */
    private static ProblemsDatabase INSTANCE;

    /**
     * Méthode qui retourne la DAO de la base de données (se référer à la classe elle-même pour plus d'informations)
     *
     * @return La DAO de la base de données
     */
    public abstract ProblemDao problemDao();

    /**
     * Objet pour l'accès concurrent
     */
    private static final Object sLock = new Object();

    /**
     * Récupère l'instance de la base de données
     *
     * @param context contexte de l'application
     * @return l'instance de la base de données
     */
    public static ProblemsDatabase getInstance(Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        ProblemsDatabase.class, "problems")
                        /* allowMainThreadQueries() est une méthode très importante puisqu'elle
                           permet de faire des requêtes directement depuis la MainActivity */
                        .allowMainThreadQueries()
                        .build();
            }
            return INSTANCE;
        }
    }

}
