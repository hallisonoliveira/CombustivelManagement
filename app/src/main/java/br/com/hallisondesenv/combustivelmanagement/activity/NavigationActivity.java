package br.com.hallisondesenv.combustivelmanagement.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import br.com.hallisondesenv.combustivelmanagement.R;

/**
 * Created by Hallison on 10/04/2016.
 */
public class NavigationActivity extends AppCompatActivity {

    GoogleMap mGoogleMap;
    LatLng mOrigem;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_navigation);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tbr_navigation);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        SupportMapFragment fragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation);

        mGoogleMap = fragment.getMap();
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        mOrigem = new LatLng(-23.561706,-46.655981);

        updateMap();

    }

    private void updateMap(){
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mOrigem, 17.0f));
        mGoogleMap.addMarker(new MarkerOptions()
                .position(mOrigem)
                .title("Avenida Paulista")
                .snippet("São Paulo"));
    }

}
