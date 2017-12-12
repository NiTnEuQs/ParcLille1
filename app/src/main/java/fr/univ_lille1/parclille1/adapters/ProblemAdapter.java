package fr.univ_lille1.parclille1.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import fr.univ_lille1.parclille1.R;
import fr.univ_lille1.parclille1.model.Problem;

/**
 * Cette classe permet de surcharger la classe ArrayAdapter pour avoir un design de liste comme
 * on le souhaite. Elle ne gère que le fait de peupler la vue.
 */
public class ProblemAdapter extends ArrayAdapter<Problem> {

    /**
     * Un constructeur de la classe ProblemAdapter
     *
     * @param context Le context dans lequel implémenter l'adapter
     * @param objects La liste des objets qu'il faut que la classe gère
     */
    public ProblemAdapter(@NonNull Context context, @NonNull List<Problem> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_problem, parent, false);
        }

        ProblemViewHolder viewHolder = (ProblemViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new ProblemViewHolder();
            viewHolder.type = convertView.findViewById(R.id.problem_type_textview);
            viewHolder.address = convertView.findViewById(R.id.problem_address_textview);
            viewHolder.description = convertView.findViewById(R.id.problem_description_textview);
            convertView.setTag(viewHolder);
        }

        // getItem(position) va récupérer l'item [position] de la List<Problem> problems
        Problem problem = getItem(position);

        // Il ne reste plus qu'à remplir notre vue
        viewHolder.type.setText(problem != null ? problem.getType().toString() : "");
        viewHolder.address.setText(problem != null ? problem.getAddress() : "");
        viewHolder.description.setText(problem != null ? problem.getDescription() : "");

        return convertView;
    }

    private class ProblemViewHolder {
        private TextView type;
        private TextView address;
        private TextView description;
    }
    
}
