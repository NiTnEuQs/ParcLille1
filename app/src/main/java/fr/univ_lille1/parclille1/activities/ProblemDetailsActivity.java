package fr.univ_lille1.parclille1.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import fr.univ_lille1.parclille1.model.Problem;
import fr.univ_lille1.parclille1.R;
import fr.univ_lille1.parclille1.database.ProblemsDatabase;

/**
 * Cette activité détaille un problème en particulier. Ce problème est sélectionné depuis
 * l'activité principale (MainActivity)
 */
public class ProblemDetailsActivity extends AppCompatActivity {

    // Attributs

    /** pid est l'identifiant du problème dans la liste */
    private int pid;
    /** problem est juste le problème que l'on veut voir (trouvé selon le pid) */
    private Problem problem;

    private TextView problem_type_textview;
    private TextView problem_coordinates_textview;
    private TextView problem_address_textview;
    private TextView problem_description_textview;
    private Button location_button;
    private Button delete_button;

    // Méthodes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem_details);

        problem_type_textview = findViewById(R.id.problem_type_textview);
        problem_coordinates_textview = findViewById(R.id.problem_coordinates_textview);
        problem_address_textview = findViewById(R.id.problem_address_textview);
        problem_description_textview = findViewById(R.id.problem_description_textview);
        location_button = findViewById(R.id.location_button);
        delete_button = findViewById(R.id.delete_button);

        /*
         * Le bout de code suivant permet de récupérer un attribut passé en paramètre.
         * Dans ce cas, on récupère "pid" qui correspond à l'identifiant du problème
         */
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                pid = -1;
            } else {
                pid = extras.getInt("PROBLEM_ID");
            }
        } else {
            pid = (Integer) savedInstanceState.getSerializable("PROBLEM_ID");
        }

        // On initialise les composants de la vue
        initializeProblemDetails();
        initializeLocationButton();
        initializeDeleteButton();
    }

    public void initializeProblemDetails() {
        problem = MainActivity.getProblems().get(pid);

        problem_type_textview.setText(problem.getType().toString());
        problem_coordinates_textview.setText(problem.getCoordinate().toString());
        problem_address_textview.setText(problem.getAddress());
        problem_description_textview.setText(problem.getDescription());
    }

    public void initializeLocationButton() {
        location_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.fr/maps/place/Université+de+Lille+1,+59650+Villeneuve‐d'Ascq/@" + problem.getCoordinate().getLatitudeAsString() + "," + problem.getCoordinate().getLongitudeAsString() + ",20z"));
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/search/?api=1&query=" + problem.getCoordinate().getLatitudeAsString() + "," + problem.getCoordinate().getLongitudeAsString()));
                startActivity(browserIntent);
            }
        });
    }

    public void initializeDeleteButton() {
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showConfirmationDeleteAlert();
            }
        });
    }

    /**
     * Permet d'afficher une alerte si jamais un problème est venu à être supprimé.
     */
    private void showConfirmationDeleteAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Supprimer le problème")
                .setMessage("Êtes-vous sûr de vouloir supprimer ce problème ?")
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        // Supprime le problème de la liste
                        MainActivity.getProblems().remove(problem);
                        // Supprime le problème de la base de données
                        ProblemsDatabase.getInstance(getApplicationContext()).problemDao()
                                .delete(problem);

                        /*
                         * On supprime de la liste puis de la base de données pour éviter de
                         * remettre à jour la liste avec toutes les données de la base de données
                         */

                        finish();
                    }
                })
                .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                    }
                });
        dialog.show();
    }

}
