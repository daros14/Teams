package daros14.teams;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by david on 6/11/15.
 */
public class TeamsData {

    List<Team> teams;
    ArrayAdapter arrayNombresAdapter;
    ArrayAdapter arrayDireccionesAdapter;
    ArrayAdapter arrayLatLonAdapter;
    ArrayAdapter arrayJerseysAdapter;
    Context mContext;


    public TeamsData(Context context){

        mContext = context;
        teams = new ArrayList<Team>();

        //Names
        arrayNombresAdapter = ArrayAdapter.createFromResource(context, R.array.primera_nacional_nombres_array, android.R.layout.simple_spinner_item);
        //Adresses
        arrayDireccionesAdapter = ArrayAdapter.createFromResource(context, R.array.primera_nacional_direcciones_array,android.R.layout.simple_spinner_item);
        //Lat Lon
        arrayLatLonAdapter = ArrayAdapter.createFromResource(context, R.array.primera_nacional_lat_lon_array,android.R.layout.simple_spinner_item);
        //Jerseys Colors
        arrayJerseysAdapter = ArrayAdapter.createFromResource(context, R.array.primera_nacional_jerseys_array,android.R.layout.simple_spinner_item);

        int aux = arrayNombresAdapter.getCount();
        for (int i = 0; i < aux; i++) {
            teams.add(new Team(arrayNombresAdapter.getItem(i).toString(),
                    arrayDireccionesAdapter.getItem(i).toString(), arrayLatLonAdapter.getItem(i).toString(),
                    arrayJerseysAdapter.getItem(i).toString()));
        }
    }


    public List<Team> getTeams(){
        return teams;
    }


}
