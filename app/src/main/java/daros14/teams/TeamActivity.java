package daros14.teams;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class TeamActivity extends AppCompatActivity implements OnMapReadyCallback {

    private final static String EQUIPO = "daros14.teams.equipo";
    private static final String DIRECCION = "daros14.teams.direccion";
    private static final String LATLON = "daros14.teams.latlon";
    private static final String JERSEY = "daros14.teams.jersey";
    private final static String LOG_TAG = "Planetas";
    private GoogleMap m_map;
    private CameraPosition camera;
    private String latlon;
    private String direccion;
    private String nombre;
    private String jersey;
    private double lat, lon;


    static final LatLng madrid = new LatLng(40.416741, -3.703302);
    static final CameraPosition MADRID = CameraPosition.builder()
            .target(madrid).zoom(10).bearing(0).tilt(45).build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Gestionamos BACK ARROW
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Compartir ubicacion
                compartirUbicacion();
            }
        });

        TextView textView_nombre = (TextView)findViewById(R.id.equipo_nombre);
        TextView textView_direccion = (TextView)findViewById(R.id.equipo_direccion);
        //TextView textView_latlon = (TextView)findViewById(R.id.equipo_latlon);
        TextView textView_jersey = (TextView)findViewById(R.id.equipo_camiseta);

        //Recogemos valor de intent
        Intent intent = this.getIntent();
        if (intent == null){
            Log.i(LOG_TAG, "La actividad no se ha llamado mediante un intent.");
        }
        else{
            if (intent.getStringExtra(LATLON) != null)
                latlon =intent.getStringExtra(LATLON);
            else
                latlon = "40.416718, -3.703603";
            direccion = intent.getStringExtra(DIRECCION);
            nombre = intent.getStringExtra(EQUIPO);
            jersey = intent.getStringExtra(JERSEY);

            setTitle(intent.getStringExtra(EQUIPO));
            textView_direccion.setText(intent.getStringExtra(DIRECCION));
            textView_jersey.setText(intent.getStringExtra(JERSEY));
        }

        //Asignamos y declaramos mapFragment
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Cardview - listener
        TextView card_mapa_capa = (TextView)findViewById(R.id.card_mapa_capa);
        card_mapa_capa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirGoogleMaps();
            }
        });

        //Assign lat lon from intent extra data
        String[] separated = latlon.split(",");
        lat = Double.parseDouble(separated[0]);
        lon = Double.parseDouble(separated[1]);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.enter_backwards, R.anim.exit_backwards);
    }

    @Override
    public void onMapReady(GoogleMap map){
        m_map = map;

        //Set arena position on Map
        LatLng arena_pos = new LatLng(lat, lon);
        CameraPosition arena = CameraPosition.builder()
                .target(arena_pos).zoom(13).bearing(0).tilt(45).build();
        m_map.animateCamera(CameraUpdateFactory.newCameraPosition(arena), 1, null);

        //Add arena marker
        MarkerOptions arena_marker = new MarkerOptions().position(arena_pos).title(nombre);
        m_map.addMarker(arena_marker);
    }

    private void abrirGoogleMaps(){

        //Intent to call Google Maps
        Uri gmmIntentUri = null;
        try {
            gmmIntentUri = Uri.parse("geo:" + lat + "," + lon + "?q=" + URLEncoder.encode(direccion, "UTF-8"));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        }
    }

    private void compartirUbicacion(){

        String uri = null;
        uri = "http://maps.google.com/maps?q="+lat +","+lon;
        //Log.i(LOG_TAG,"URI="+uri);

        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String ShareSub = nombre+" plays here ";
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, ShareSub);
        sharingIntent.putExtra(Intent.EXTRA_TEXT, uri);
        startActivity(Intent.createChooser(sharingIntent, "Share"));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_team, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.share){
            compartirUbicacion();
        }


        return super.onOptionsItemSelected(item);
    }
}
