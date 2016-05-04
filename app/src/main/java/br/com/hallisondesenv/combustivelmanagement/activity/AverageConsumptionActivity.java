package br.com.hallisondesenv.combustivelmanagement.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.NumberFormat;

import br.com.hallisondesenv.combustivelmanagement.R;
import br.com.hallisondesenv.combustivelmanagement.dao.AverageConsumptionDao;
import br.com.hallisondesenv.combustivelmanagement.dao.VehicleDataDao;
import br.com.hallisondesenv.combustivelmanagement.model.AverageConsumption;
import br.com.hallisondesenv.combustivelmanagement.model.VehicleData;
import br.com.hallisondesenv.combustivelmanagement.util.MaskUtil;

/**
 * Classe responsável pela Activity AverageConsumption
 *
 * Created by Hallison Oliveira on 07/04/2016.
 */
public class AverageConsumptionActivity extends AppCompatActivity {

    // TAG para o log
    private static final String TAG = "AverageConsumption";

    private EditText edtDate;
    private EditText edtAmountLiters;
    private EditText edtPriceByLiter;
    private EditText edtKilometer;
    private FloatingActionButton fabSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_average_consumption);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tbr_new_average_consumption);
        setSupportActionBar(toolbar);

        initializeComponents();

    }

    /**
     * Inicialização de todos os views da activity
     */
    private void initializeComponents(){

        edtDate = (EditText) findViewById(R.id.edt_new_average_consumption_date);
        edtAmountLiters = (EditText) findViewById(R.id.edt_new_average_consumption_amount_liters);
        edtKilometer = (EditText) findViewById(R.id.edt_new_average_consumption_kilometer);

        edtPriceByLiter = (EditText) findViewById(R.id.edt_new_average_consumption_price_by_liter);
        edtPriceByLiter.addTextChangedListener(MaskUtil.insertMask(edtPriceByLiter));

        fabSave = (FloatingActionButton) findViewById(R.id.btn_save_average_comsumption);
        fabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveAverageConsumption();
            }
        });
    }

    /**
     * Verifica se os campos da tela estão em branco quando o usuário clica em Salvar
     * @param date
     * @param amountLiters
     * @param priceByLiter
     * @param kilometer
     * @return True se algum dos campos estiver vazio e False se todos os campos estiverem preenchidos
     */
    private boolean isEmptyFields(String date, String amountLiters, String priceByLiter, String kilometer) {
        if (TextUtils.isEmpty(date)) {
            edtDate.requestFocus();
            edtDate.setError(getResources().getString(R.string.error_required_field));
            return true;
        } else if (TextUtils.isEmpty(amountLiters)) {
            edtAmountLiters.requestFocus();
            edtAmountLiters.setError(getResources().getString(R.string.error_required_field));
            return true;
        } else if (TextUtils.isEmpty(priceByLiter)) {
            edtPriceByLiter.requestFocus();
            edtPriceByLiter.setError(getResources().getString(R.string.error_required_field));
        } else if (TextUtils.isEmpty(kilometer)) {
            edtKilometer.requestFocus();
            edtKilometer.setError(getResources().getString(R.string.error_required_field));
        }

        return false;
    }

    /**
     * Salva a nova média de consumo
     */
    private void saveAverageConsumption(){

        // Verificando se os campos estão preenchidos
        if(!isEmptyFields(
                edtDate.getText().toString(),
                edtAmountLiters.getText().toString(),
                edtPriceByLiter.getText().toString(),
                edtKilometer.getText().toString())){

            Float priceByLiter = MaskUtil.convertStringToFloat(edtPriceByLiter.getText().toString());

            // Criando uma nova instancia de averageConsumption para ser salva posteriormente
            AverageConsumption averageConsumption = new AverageConsumption(
                    new AverageConsumption().getNextKey(this),
                    edtDate.getText().toString(),
                    Float.parseFloat(edtAmountLiters.getText().toString()),
                    priceByLiter,
                    Float.parseFloat(edtKilometer.getText().toString())
            );

            try{

                // Cria uma instancia de VehicleDataDao utilizada para persistencia na base de dados
                VehicleDataDao vehicleDataDao = new VehicleDataDao();
                vehicleDataDao.fillTank(this);

                AverageConsumptionDao averageConsumptionDao = new AverageConsumptionDao();
                averageConsumptionDao.save(averageConsumption, this);
                Toast.makeText(this, R.string.info_average_consumption_saved, Toast.LENGTH_SHORT).show();

                this.finish();

            } catch (Exception e){
                Toast.makeText(this, R.string.error_average_consumption_not_saved, Toast.LENGTH_SHORT).show();
                Log.e(TAG, e.getMessage());
            }

        }

    }

}
