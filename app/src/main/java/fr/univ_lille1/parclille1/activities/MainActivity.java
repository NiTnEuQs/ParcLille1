package fr.univ_lille1.parclille1.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import fr.univ_lille1.parclille1.model.Problem;
import fr.univ_lille1.parclille1.adapters.ProblemAdapter;
import fr.univ_lille1.parclille1.R;
import fr.univ_lille1.parclille1.database.ProblemsDatabase;

/**
 * L'activité principale. Elle permet d'afficher la liste des problèmes recensés et d'en ajouter
 */
public class MainActivity extends AppCompatActivity {

    // Attributs

    private static List<Problem> problems = new ArrayList<>();
    private static ProblemAdapter problem_adapter;

    private ListView problems_listview;
    private FloatingActionButton add_problem_fab;

    // Getter/Setters

    public static List<Problem> getProblems() {
        return problems;
    }

    public static ProblemAdapter getProblem_adapter() {
        return problem_adapter;
    }

    // Méthodes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        problems_listview = findViewById(R.id.problems_listview);
        add_problem_fab = findViewById(R.id.add_problem_fab);
        problems = ProblemsDatabase.getInstance(this).problemDao().getAll();

        // On initialise les composants de la vue
        initializeProblemsListView();
        initializeAddProblemFab();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateListView();
    }

    public void initializeProblemsListView() {
        problem_adapter = new ProblemAdapter(MainActivity.this, problems);
        problems_listview.setAdapter(problem_adapter);
        problems_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent_details = new Intent(getApplicationContext(), ProblemDetailsActivity.class);
                intent_details.putExtra("PROBLEM_ID", i);
                startActivity(intent_details);
            }
        });
    }

    public void initializeAddProblemFab() {
        add_problem_fab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent_add = new Intent(getApplicationContext(), AddProblemActivity.class);
                        startActivity(intent_add);
                    }
                }
        );
    }

    /**
     * Met à jour la vue de la liste si un élément a été ajouté, modifié ou supprimé.
     */
    private void updateListView() {
        problem_adapter.notifyDataSetChanged();
        problem_adapter.notifyDataSetInvalidated();
    }

}
