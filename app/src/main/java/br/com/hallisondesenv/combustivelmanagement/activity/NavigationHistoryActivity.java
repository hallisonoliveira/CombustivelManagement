package br.com.hallisondesenv.combustivelmanagement.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import br.com.hallisondesenv.combustivelmanagement.R;
import br.com.hallisondesenv.combustivelmanagement.dao.AverageConsumptionDao;
import br.com.hallisondesenv.combustivelmanagement.dao.NavigationHistoryDao;
import br.com.hallisondesenv.combustivelmanagement.dao.VehicleDataDao;
import br.com.hallisondesenv.combustivelmanagement.model.AverageConsumption;
import br.com.hallisondesenv.combustivelmanagement.model.NavigationHistory;

/**
 * Classe responsável pela activity de adição de um novo histórico de navegação
 *
 * Created by Hallison on 07/04/2016.
 */
public class NavigationHistoryActivity extends AppCompatActivity {

    private static final String TAG = "NavigationHistory";

    private EditText edtDate;
    private EditText edtDistance;
    private FloatingActionButton fabSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_navigation_history);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tbr_newNavigationHistory);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);

        initializeComponents();

    }

    /**
     * Inicialização de todos os views da tela
     */
    private void initializeComponents(){

        edtDate = (EditText) findViewById(R.id.edt_new_navigation_history_date);
        edtDistance = (EditText) findViewById(R.id.edt_new_navigation_history_distance);

        fabSave = (FloatingActionButton) findViewById(R.id.btn_save_navigation_history);
        fabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNavigationHistory();
            }
        });
    }

    /**
     * Verifica se os campos da tela estão em branco quando o usuário clica em Salvar
     * @param date
     * @param distance
     * @return True se algum dos campos estiverem vazios e False se todos os campos estiverem preenchidos
     */
    private boolean isEmptyFields(String date, String distance) {
        if (TextUtils.isEmpty(date)) {
            edtDate.requestFocus();
            edtDate.setError(getResources().getString(R.string.error_required_field));
            return true;
        } else if (TextUtils.isEmpty(distance)) {
            edtDistance.requestFocus();
            edtDistance.setError(getResources().getString(R.string.error_required_field));
            return true;
        }

        return false;
    }

    /**
     * Salva o histórico de navegação
     */
    private void saveNavigationHistory(){

        // Verifica se os campos estão vazios
        if(!isEmptyFields(
                edtDate.getText().toString(),
                edtDistance.getText().toString())){

                float distance = Float.parseFloat(edtDistance.getText().toString());

                NavigationHistory navigationHistory = new NavigationHistory(
                    new NavigationHistory().getNextKey(this),
                    edtDate.getText().toString(),
                    distance);

            try{
                float generalAverageConsumption = new AverageConsumption().getGeneralAverage(this);
                float fuelSpend = distance / generalAverageConsumption;

                VehicleDataDao vehicleDataDao = new VehicleDataDao();
                vehicleDataDao.updateRemaingVolume(fuelSpend, this);

                NavigationHistoryDao navigationHistoryDao = new NavigationHistoryDao();
                navigationHistoryDao.save(navigationHistory, this);
                Toast.makeText(this, R.string.info_navigation_history_saved, Toast.LENGTH_SHORT).show();

                this.finish();

            } catch (Exception e){
                Toast.makeText(this, R.string.info_navigation_history_not_saved, Toast.LENGTH_SHORT).show();
                Log.e(TAG, e.getMessage());
            }

        }

    }

}
