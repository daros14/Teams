package daros14.teams;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, SearchView.OnQueryTextListener {

    private final static String TEAM = "daros14.teams.team";
    private static final String ADDRESS = "daros14.teams.address";
    private static final String LATLON = "daros14.teams.latlon";
    private static final String JERSEY = "daros14.teams.jersey";

    Spinner spinner;
    ArrayAdapter arrayNombresAdapter;
    ArrayAdapter arrayDireccionesAdapter;
    ArrayAdapter arrayLatLonAdapter;
    ArrayAdapter arrayJerseysAdapter;
    boolean onload=false;
    Context context;
    RecyclerView teams_list;
    TeamsAdapter adapter;

    //SiderBar Variables
    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private String mActivityTitle;

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
        adapter = new TeamsAdapter(new TeamsData(context).getTeams(), context);

        // Lookup the recyclerview in activity layout
        teams_list = (RecyclerView) findViewById(R.id.recycler_view);
        // Attach the adapter to the recyclerview to populate items
        teams_list.setAdapter(adapter);
        // Set layout manager to position the items
        teams_list.setLayoutManager(new LinearLayoutManager(this));
        //Decorate each Element
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);
        teams_list.addItemDecoration(itemDecoration);

        //Define Sidebar List
        //Working with ActionBarDrawerToggle
        mDrawerList = (ListView)findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();

        addDrawerItems();
        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        // An item was selected. You can retrieve the selected item using

        if (!onload){
            onload = true;
        }
        else {
            //Creamos intent para abrir equipo
            Intent intent = new Intent(this, TeamActivity.class);
            intent.putExtra(TEAM, parent.getItemAtPosition(pos).toString());
            String prueba = parent.getItemAtPosition(pos).toString();
            String prueba2 = arrayDireccionesAdapter.getItem(pos).toString();
            intent.putExtra(ADDRESS, arrayDireccionesAdapter.getItem(pos).toString());
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

        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);

        return true;
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        final List<Team> filteredModelList = filter(new TeamsData(context).getTeams(), query);
        adapter.animateTo(filteredModelList);
        teams_list.scrollToPosition(0);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //Check for DrawerToggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Sync state of status bar icon
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    //Sync state of status bar even on goind landscape
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private List<Team> filter(List<Team> teams, String query) {
        query = query.toLowerCase();

        final List<Team> filteredModelList = new ArrayList<>();
        for (Team team : teams) {
            final String text = team.getName().toLowerCase();
                    //.getText().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(team);
            }
        }
        return filteredModelList;
    }

    //Aux SideBar Method (Sample Values)
    private void addDrawerItems() {

        String[] categoryArray = getResources().getStringArray(R.array.categories);
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, categoryArray);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, mAdapter.getItem(position).toString(), Toast.LENGTH_SHORT).show();
                mDrawerLayout.closeDrawer(mDrawerList);
            }
        });
    }

    //helper method that we will call from onCreate() after the drawer items have been set up
    private void setupDrawer() {

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(R.string.select_category);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }
}