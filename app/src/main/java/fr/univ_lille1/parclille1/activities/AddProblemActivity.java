package fr.univ_lille1.parclille1.activities;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import fr.univ_lille1.parclille1.model.Coordinate;
import fr.univ_lille1.parclille1.model.Problem;
import fr.univ_lille1.parclille1.model.ProblemType;
import fr.univ_lille1.parclille1.R;
import fr.univ_lille1.parclille1.database.ProblemsDatabase;
import fr.univ_lille1.parclille1.typeconverters.CoordinateTypeConverter;
import fr.univ_lille1.parclille1.typeconverters.ProblemTypeTypeConverter;

/**
 * Cette activité permet d'ajouter un problème pour le recenser dans l'application.
 * Les coordonnées GPS sont automatiquement recherchées et remplies pour plus de précision.
 */
public class AddProblemActivity extends AppCompatActivity {

    // Attributs

    private Spinner problem_type_spinner;
    private EditText coordinate_edittext, address_edittext, description_edittext;
    private Button add_problem_button;

    private LocationManager locationManager;
    private LocationListener locationListener;

    // Méthodes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_problem);

        problem_type_spinner = findViewById(R.id.problem_type_spinner);
        coordinate_edittext = findViewById(R.id.coordinate_edittext);
        address_edittext = findViewById(R.id.address_edittext);
        description_edittext = findViewById(R.id.description_edittext);
        add_problem_button = findViewById(R.id.add_problem_button);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // On initialise les composants de la vue
        initializeProblemTypeSpinner();
        initializeAddProblemButton();
    }

    @Override
    protected void onResume() {
        initializeLocationListener();
        populateEditTexts();

        super.onResume();
    }

    public void initializeLocationListener() {
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location loc) {
                Geocoder gcd = new Geocoder(getApplicationContext(), Locale.getDefault());

                try {
                    List<Address> addresses = gcd.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
                    if (addresses.size() > 0) {
                        address_edittext.setText(addresses.get(0).getAddressLine(0));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                coordinate_edittext.setText(new Coordinate((float) loc.getLatitude(), (float) loc.getLongitude()).toString());
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {}

            @Override
            public void onProviderEnabled(String s) {}

            @Override
            public void onProviderDisabled(String s) {}
        };
    }

    // Ajoute les valeurs de l'énumération dans le Spinner dynamiquement
    public void initializeProblemTypeSpinner() {
        List<String> list = new ArrayList<>();
        ProblemType[] problem_type_values = ProblemType.values();

        for (ProblemType type : problem_type_values) {
            list.add(type.toString());
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        problem_type_spinner.setAdapter(dataAdapter);
    }

    public void initializeAddProblemButton() {
        add_problem_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationManager.removeUpdates(locationListener);

                ProblemType type = ProblemTypeTypeConverter.toProblemType(problem_type_spinner.getSelectedItemPosition());
                Coordinate coordinate = CoordinateTypeConverter.toCoordinate(coordinate_edittext.getText().toString());
                String address = address_edittext.getText().toString();
                String description = description_edittext.getText().toString();

                Problem problem = new Problem();
                problem.setType(type);
                problem.setCoordinate(coordinate);
                problem.setAddress(address);
                problem.setDescription(description);

                // Ajoute le problème dans la liste
                MainActivity.getProblems().add(problem);
                // Ajoute le problème dans la base de données
                ProblemsDatabase.getInstance(getApplicationContext()).problemDao()
                        .insert(problem);

                /*
                 * On ajoute dans la liste puis dans la base de données pour éviter de
                 * remettre à jour la liste avec toutes les données de la base de données
                 */

                finish();
            }
        });
    }

    /**
     * Cette méthode permet de remplir le formulaire automatiquement avec les coordonnées GPS
     * ainsi que l'adresse exacte
     */
    public void populateEditTexts() {
        if (!checkLocation())
            return;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        String provider = locationManager.getBestProvider(criteria, true);

        if (provider != null) {
            locationManager.requestLocationUpdates(provider, 60/*2 * 60 * 1000*/, 1/*10*/, locationListener);
        }
    }

    /**
     * Permet d'afficher une alerte si les coordonnées GPS ne sont pas actives
     *
     * @return vrai si le GPS est activé, faux sinon
     */
    private boolean checkLocation() {
        if(!isLocationEnabled())
            showGPSOffAlert();

        return isLocationEnabled();
    }

    /**
     * Vérifie si les données GPS sont activées
     *
     * @return vrai si le GPS est activé, faux sinon
     */
    private boolean isLocationEnabled() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                //|| locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    /**
     * Permet d'afficher une alerte si jamais les données GPS ne sont pas actives.
     */
    private void showGPSOffAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Activation données GPS")
                .setMessage("Vos données GPS sont inaccessibles. Merci de les activer pour une performance optimale")
                .setPositiveButton("Paramètres", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    }
                });
        dialog.show();
    }

}

