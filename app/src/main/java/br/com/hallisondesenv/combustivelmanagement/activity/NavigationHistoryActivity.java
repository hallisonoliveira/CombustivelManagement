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
import br.com.hallisondesenv.combustivelmanagement.model.AverageConsumption;
import br.com.hallisondesenv.combustivelmanagement.model.NavigationHistory;

/**
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

    private void initializeComponents(){

        edtDate = (EditText) findViewById(R.id.edt_newNavigationHistory_date);
        edtDistance = (EditText) findViewById(R.id.edt_newNavigationHistory_distante);

        fabSave = (FloatingActionButton) findViewById(R.id.btn_save_navigationHistory);
        fabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNavigationHistory();
            }
        });
    }

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

    private void saveNavigationHistory(){

        if(!isEmptyFields(
                edtDate.getText().toString(),
                edtDistance.getText().toString())){

                NavigationHistory navigationHistory = new NavigationHistory(
                    new NavigationHistory().getNextKey(this),
                    edtDate.getText().toString(),
                    Float.parseFloat(edtDistance.getText().toString()));

            try{
                NavigationHistoryDao navigationHistoryDao = new NavigationHistoryDao();
                navigationHistoryDao.save(navigationHistory, this);
                Toast.makeText(this, "Histórico de navegação cadastrado com sucesso.", Toast.LENGTH_SHORT).show();

                this.finish();

            } catch (Exception e){
                Toast.makeText(this, "Houve um erro ao salvar os dados.", Toast.LENGTH_SHORT).show();
                Log.e(TAG, e.getMessage());
            }

        }

    }

}
