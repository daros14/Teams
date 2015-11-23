package daros14.teams;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;

import java.util.List;

/**
 * Created by david on 5/11/15.
 */
// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
public class TeamsAdapter extends RecyclerView.Adapter<TeamsAdapter.ViewHolder> {

    private final static String TEAM = "daros14.teams.team";
    private static final String ADDRESS = "daros14.teams.address";
    private static final String LATLON = "daros14.teams.latlon";
    private static final String JERSEY = "daros14.teams.jersey";
    private Context mContext;
    private List<Team> mTeams;

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView teamNameView;
        public TextView teamNameCapitalView;
        public ImageView teamNameCapitalCircleView;
        private Context contxt;
        private List<Team> equips;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(Context context, View itemView, List<Team> equipos) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            itemView.setOnClickListener(this);
            contxt = context;
            equips = equipos;
            teamNameView = (TextView) itemView.findViewById(R.id.team_name);
            //teamNameCapitalView = (TextView) itemView.findViewById(R.id.team_name_capital_letter);
            teamNameCapitalCircleView = (ImageView) itemView.findViewById(R.id.team_name_capital_letter_circle);
        }

        // Handles the row being being clicked
        @Override
        public void onClick(View view) {
            int position = getLayoutPosition(); // gets item position
            Team team = equips.get(position);

            //Calling activity Equipo from Adapter
            //Creamos intent para abrir equipo
            Intent intent = new Intent(contxt, TeamActivity.class);
            intent.putExtra(TEAM, team.getName());
            intent.putExtra(ADDRESS, team.getAddress());
            intent.putExtra(LATLON, team.getLatLon());
            intent.putExtra(JERSEY, team.getJersey());

            Activity activity = (Activity) contxt;
            activity.startActivity(intent);
            activity.overridePendingTransition(R.anim.enter, R.anim.exit);
        }
    }

    // Pass in the contact array into the constructor
    public TeamsAdapter(List<Team> equipos, Context context) {
        mTeams = equipos;
        mContext = context;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public TeamsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.equipo, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(context, contactView, mTeams);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(TeamsAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Team equipo = mTeams.get(position);

        // Set item views based on the data model
        TextView textView = viewHolder.teamNameView;
        textView.setText(equipo.getName());

        //TextView capitalLetter = viewHolder.teamNameCapitalView;
        //capitalLetter.setText(equipo.getCapitalLetter());

        TextDrawable drawable = TextDrawable.builder().buildRound(equipo.getCapitalLetter(), R.color.blueDark);
        ImageView roundBackground = viewHolder.teamNameCapitalCircleView;
        roundBackground.setImageDrawable(drawable);

    }

    // Return the total count of items
    @Override
    public int getItemCount() {
        return mTeams.size();
    }

    public List<Team> getmTeams (){
        return this.mTeams;
    }

    /////SEARCHVIEW METHODS////
    public void animateTo(List<Team> tems) {
        applyAndAnimateRemovals(tems);
        applyAndAnimateAdditions(tems);
        applyAndAnimateMovedItems(tems);
    }

    private void applyAndAnimateRemovals(List<Team> newModels) {
        for (int i = mTeams.size() - 1; i >= 0; i--) {
            final Team model = mTeams.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<Team> newModels) {
        for (int i = 0, count = newModels.size(); i < count; i++) {
            final Team model = newModels.get(i);
            if (!mTeams.contains(model)) {
                addItem(i, model);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<Team> newModels) {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final Team model = newModels.get(toPosition);
            final int fromPosition = mTeams.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    public Team removeItem(int position) {
        final Team model = mTeams.remove(position);
        notifyItemRemoved(position);
        return model;
    }

    public void addItem(int position, Team team) {
        mTeams.add(position, team);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        final Team team = mTeams.remove(fromPosition);
        mTeams.add(toPosition, team);
        notifyItemMoved(fromPosition, toPosition);
    }

}
