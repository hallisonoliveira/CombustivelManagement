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
import android.widget.ImageView;
import android.widget.TextView;


import br.com.hallisondesenv.combustivelmanagement.R;
import br.com.hallisondesenv.combustivelmanagement.dao.VehicleDataDao;
import br.com.hallisondesenv.combustivelmanagement.model.VehicleData;


/**
 * Classe principal do aplicativo.
 * Nessa classe são gerenciadas as ações do Navigation Drawer para iniciar os fragments
 *
 * Created by Hallison on 01/04/2016.
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String TAG = "MainActivity";

    private TextView navVehicleManufacturer;

    private AverageConsumptionFragment averageConsumptionFragment;
    private NavigationHistoryFragment navigationHistoryFragment;

    private ImageView imgMenu;

    private Toolbar toolbar;
    private NavigationView navigationView;

    private boolean isInHome = false;


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

        openHomeFragment();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }


        @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
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

    /**
     * Inicialização dos views presentes no cabeçalho do menu
     */
    private void initializeComponents(){
        this.imgMenu = (ImageView) findViewById(R.id.img_menu);
        this.imgMenu.setImageResource(R.mipmap.comb_management);

        this.navVehicleManufacturer = (TextView) findViewById(R.id.txt_nav_manufacturerModel);
    }

    /**
     * Busca a marca e o modelo do veículo cadastrado para exibir no cabeçalho do menu
     */
    private void searchVehicleData(){
        initializeComponents();
        VehicleData vehicleData = new VehicleDataDao().findById(this);

        if (vehicleData != null) {
            this.navVehicleManufacturer.setText(vehicleData.getManufacturer() + "/" + vehicleData.getModel());
        }

    }

    /**
     * Abre o fragment Home
     */
    private void openHomeFragment(){
        toolbar.setTitle(getResources().getString(R.string.app_name));
        navigationView.setCheckedItem(R.id.nav_home);

        detachFragments();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameContent, new HomeFragment())
                .addToBackStack(null)
                .commit();
        isInHome = true;
    }

    /**
     * Abre o fragment AverageConsumption
     */
    private void openAverageConsumptionFragment(){
        isInHome = false;

        detachFragments();

        averageConsumptionFragment = new AverageConsumptionFragment();

        toolbar.setTitle(getResources().getString(R.string.averageConsumption_list_title));
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameContent, averageConsumptionFragment)
                .addToBackStack(null)
                .commit();
    }

    /**
     * Abre o fragment VehicleData
     */
    private void openVehicleDataFragment(){
        isInHome = false;

        detachFragments();

        toolbar.setTitle(getResources().getString(R.string.vehicleData_title));
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameContent, new VehicleDataFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (isInHome){
            finish();
        } else {
           openHomeFragment();
        }
    }

    /**
     * Finaliza a instancia de todos os fragments a cada troca de tela
     */
    private void detachFragments(){
        averageConsumptionFragment = null;
        navigationHistoryFragment = null;
    }


    /**
     * Trata os eventos de seleção vindos do menu
     * @param item Item selecionado no menu
     * @return Retorna True por padrão
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            openHomeFragment();

        } else if (id == R.id.nav_navigation_history) {
            isInHome = false;
            detachFragments();
            navigationHistoryFragment = new NavigationHistoryFragment();
            toolbar.setTitle(getResources().getString(R.string.navigationHistory_title));
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameContent, navigationHistoryFragment)
                    .addToBackStack(null)
                    .commit();


        } else if (id == R.id.nav_average_consumption) {
            openAverageConsumptionFragment();

        } else if (id == R.id.nav_vehicleData) {
            openVehicleDataFragment();

        } else if (id == R.id.nav_about) {
            isInHome = false;
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);


        } else if (id == R.id.nav_exit) {
            this.finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

}
