package br.com.hallisondesenv.combustivelmanagement.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;



import br.com.hallisondesenv.combustivelmanagement.R;
import br.com.hallisondesenv.combustivelmanagement.dao.VehicleDataDao;
import br.com.hallisondesenv.combustivelmanagement.model.VehicleData;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String TAG = "MainActivity";

    private TextView navVehicleManufacturer;
    private TextView navVehicleFuelType;

    private AverageConsumptionFragment averageConsumptionFragment;
    private NavigationHistoryFragment navigationHistoryFragment;

    Toolbar toolbar;
    NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//        searchVehicleData();

        openHomeFragment();

    }

    @Override
    protected void onResumeFragments() {
        if(averageConsumptionFragment != null){
            openAverageConsumptionFragment();
        } else {
            super.onResumeFragments();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        searchVehicleData();
        return super.onCreateOptionsMenu(menu);
    }

    private void initializeComponents(){
        this.navVehicleManufacturer = (TextView) findViewById(R.id.txt_nav_manufacturerModel);
        this.navVehicleFuelType = (TextView) findViewById(R.id.txt_nav_fuelType);
    }

    private void searchVehicleData(){
        initializeComponents();

        VehicleData vehicleData = new VehicleDataDao().findById(this);

        if (vehicleData != null) {
            this.navVehicleManufacturer.setText(vehicleData.getManufacturer() + "/" + vehicleData.getModel());
//            this.navVehicleFuelType.setText(vehicleData.getFuelType());
        }
    }

    private void openHomeFragment(){
        toolbar.setTitle(getResources().getString(R.string.app_name));
        navigationView.setCheckedItem(R.id.nav_home);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameContent, new HomeFragment())
                .addToBackStack(null)
                .commit();
    }

    private void openAverageConsumptionFragment(){
        averageConsumptionFragment = new AverageConsumptionFragment();

        toolbar.setTitle(getResources().getString(R.string.averageConsumption_list_title));
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameContent, averageConsumptionFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();


        openHomeFragment();

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            openHomeFragment();

        } else if (id == R.id.nav_navigation) {
            Intent intent = new Intent(this, NavigationActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_navigation_history) {
            navigationHistoryFragment = new NavigationHistoryFragment();
            toolbar.setTitle(getResources().getString(R.string.navigationHistory_title));
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameContent, navigationHistoryFragment)
                    .addToBackStack(null)
                    .commit();

        } else if (id == R.id.nav_graphics) {
            toolbar.setTitle(getResources().getString(R.string.graphics_title));
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameContent, new GraphicsFragment())
                    .addToBackStack(null)
                    .commit();

        } else if (id == R.id.nav_average_consumption) {
            openAverageConsumptionFragment();

        } else if (id == R.id.nav_vehicleData) {
            toolbar.setTitle(getResources().getString(R.string.vehicleData_title));
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameContent, new VehicleDataFragment())
                    .addToBackStack(null)
                    .commit();

//        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_exit) {
            this.finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);


        return true;
    }

}
