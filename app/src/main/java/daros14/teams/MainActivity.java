package daros14.teams;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private final static String LOG_TAG = "Planetas";
    private final static String EQUIPO = "daros14.teams.equipo";
    private static final String DIRECCION = "daros14.teams.direccion";
    private static final String LATLON = "daros14.teams.latlon";
    private static final String JERSEY = "daros14.teams.jersey";

    Spinner spinner;
    ArrayAdapter arrayNombresAdapter;
    ArrayAdapter arrayDireccionesAdapter;
    ArrayAdapter arrayLatLonAdapter;
    ArrayAdapter arrayJerseysAdapter;
    boolean onload=false;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Names
        arrayNombresAdapter = ArrayAdapter.createFromResource(this, R.array.primera_nacional_nombres_array,android.R.layout.simple_spinner_item);
        //Direcciones
        arrayDireccionesAdapter = ArrayAdapter.createFromResource(this, R.array.primera_nacional_direcciones_array,android.R.layout.simple_spinner_item);
        //Lat Lon
        arrayLatLonAdapter = ArrayAdapter.createFromResource(this, R.array.primera_nacional_lat_lon_array,android.R.layout.simple_spinner_item);
        //Jerseys Colors
        arrayJerseysAdapter = ArrayAdapter.createFromResource(this, R.array.primera_nacional_jerseys_array,android.R.layout.simple_spinner_item);


        // Create adapter passing in the sample user data
        TeamsAdapter adapter = new TeamsAdapter(new TeamsData(context).getTeams(), context);

        // Lookup the recyclerview in activity layout
        //RecyclerView rvEquipos = (RecyclerView) findViewById(R.id.recycler_view);
        RecyclerView teams_list = (RecyclerView) findViewById(R.id.recycler_view);
        // Attach the adapter to the recyclerview to populate items
        teams_list.setAdapter(adapter);
        // Set layout manager to position the items
        teams_list.setLayoutManager(new LinearLayoutManager(this));
        //Decorate each Element
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);
        teams_list.addItemDecoration(itemDecoration);

    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        // An item was selected. You can retrieve the selected item using

        if (!onload){
            onload = true;
        }
        else {
            //Creamos intent para abrir equipo
            Intent intent = new Intent(this, TeamActivity.class);
            intent.putExtra(EQUIPO, parent.getItemAtPosition(pos).toString());
            intent.putExtra(DIRECCION, arrayDireccionesAdapter.getItem(pos).toString());
            intent.putExtra(LATLON, arrayLatLonAdapter.getItem(pos).toString());
            String aux =  arrayJerseysAdapter.getItem(pos).toString();
            intent.putExtra(JERSEY, aux);


            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
                overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    //creo una lista de 30 Strings que se le pasa al Adapter para pintarlos en el RecyclerView
    private ArrayList<String> createList(){
        ArrayList<String> lista_equipos = new ArrayList<String>();

        return lista_equipos;
    }
}