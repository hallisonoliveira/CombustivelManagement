package br.com.hallisondesenv.combustivelmanagement.activity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import br.com.hallisondesenv.combustivelmanagement.R;
import br.com.hallisondesenv.combustivelmanagement.dao.VehicleDataDao;
import br.com.hallisondesenv.combustivelmanagement.model.VehicleData;

/**
 * Classe responsável pelo fragment de cadastro dos dados do veículo
 *
 * Created by Hallison on 09/04/2016.
 */
public class VehicleDataFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = "VehicleDataFragment";

    private EditText edtManufacturer;
    private EditText edtModel;
    private EditText edtYear;
    private EditText edtFuelCapacity;
    private FloatingActionButton fabSave;

    private float remainingVolume = 0;
    private int vehicleId = 0;
    private boolean isEditableFields = true;


    public VehicleDataFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_vehicle_data, container, false);

        initializeComponents(view);

        loadVehicleData(view);

        return view;
    }

    /**
     * Inicialização de todos os views da tela
     * @param view
     */
    private void initializeComponents(View view){
        edtFuelCapacity = (EditText) view.findViewById(R.id.edt_vehicleData_fuelCapacity);

        this.edtManufacturer = (EditText) view.findViewById(R.id.edt_vehicleData_manufacturer);

        this.edtModel = (EditText) view.findViewById(R.id.edt_vehicleData_model);

        this.edtYear = (EditText) view.findViewById(R.id.edt_vehicleData_year);

        fabSave = (FloatingActionButton) view.findViewById(R.id.btn_vehicleData_save);
        fabSave.setOnClickListener(this);

    }

    /**
     * Carrega os dados do veículo, se já houver e preenche os campos
     * @param view View do fragment para obter o contexto da aplicação
     */
    private void loadVehicleData(View view){
        VehicleData vehicleData = new VehicleDataDao().findById(view.getContext());

        // Se houver dados de veículo já cadastrados, os campos são preenchidos
        if (vehicleData != null) {
            this.vehicleId = vehicleData.getId();
            this.edtManufacturer.setText(vehicleData.getManufacturer());
            this.edtModel.setText(vehicleData.getModel());
            this.edtYear.setText(String.valueOf(vehicleData.getYear()));
            this.edtFuelCapacity.setText(String.valueOf(vehicleData.getFuelCapacity()));
            this.remainingVolume = vehicleData.getRemainingVolume();

            this.isEditableFields = false;
            setFieldsStatus();

        // Se não houver dados de veículo já cadastrado, uma mensagem é apresentada ao usuário
        } else {
            this.vehicleId = 0;
            Toast.makeText(view.getContext(), R.string.info_vehicleData_empty, Toast.LENGTH_SHORT).show();

            this.isEditableFields = true;
            setFieldsStatus();
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    /**
     * Verifica se os campos da tela estão em branco quando o usuário clica em Salvar
     * @param manufacturer
     * @param model
     * @param year
     * @param fuelCapacity
     * @return True se algum dos campos estiver vazio e False se todos os campos estiverem preenchidos
     */
    private boolean isEmptyFields(String manufacturer, String model, String year, String fuelCapacity) {
        if (TextUtils.isEmpty(manufacturer)) {
            edtManufacturer.requestFocus();
            edtManufacturer.setError(getResources().getString(R.string.error_required_field));
            return true;
        } else if (TextUtils.isEmpty(model)) {
            edtModel.requestFocus();
            edtModel.setError(getResources().getString(R.string.error_required_field));
            return true;
        } else if (TextUtils.isEmpty(year)) {
            edtYear.requestFocus();
            edtYear.setError(getResources().getString(R.string.error_required_field));
        } else if (TextUtils.isEmpty(fuelCapacity)) {
            edtFuelCapacity.requestFocus();
            edtFuelCapacity.setError(getResources().getString(R.string.error_required_field));
        }

        return false;
    }

    /**
     * Altera o status dos campos na tela para editável ou não editável.
     * Os campos são alterados para não editavel quando há dados de veículo cadastrado
     */
    private void setFieldsStatus(){
        this.edtManufacturer.setEnabled(this.isEditableFields);
        this.edtModel.setEnabled(this.isEditableFields);
        this.edtYear.setEnabled(this.isEditableFields);
        this.edtFuelCapacity.setEnabled(this.isEditableFields);

        if (this.isEditableFields){
            this.fabSave.setImageResource(R.drawable.ic_save_white_24dp);
        }else {
            this.fabSave.setImageResource(R.drawable.ic_edit_white_24dp);
        }

    }

    /**
     * Salva os dados do veículo
     */
    private void saveVehicleData(){

        int idToSave;

        // Verifica se os campos na tela estão preenchidos
        if(!isEmptyFields(edtManufacturer.getText().toString(), edtModel.getText().toString(),
                edtYear.getText().toString(), edtFuelCapacity.getText().toString())) {

            if (vehicleId == 0) {
                idToSave = 1;
                this.remainingVolume = 0;
            } else {
                idToSave = vehicleId;
            }

            VehicleData vehicleData = new VehicleData(
                    idToSave,
                    edtManufacturer.getText().toString(),
                    edtModel.getText().toString(),
                    Integer.parseInt(edtYear.getText().toString()),
                    Integer.parseInt(edtFuelCapacity.getText().toString()),
                    remainingVolume);

            try {
                VehicleDataDao vehicleDataDao = new VehicleDataDao();
                vehicleDataDao.save(vehicleData, getContext());

                Toast.makeText(super.getContext(), R.string.info_vehicle_saved, Toast.LENGTH_SHORT).show();

                this.isEditableFields = false;
                setFieldsStatus();

            } catch (Exception e){
                Toast.makeText(super.getContext(), R.string.error_vehicle_not_saved, Toast.LENGTH_SHORT).show();
                Log.e(TAG, e.getMessage());
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_vehicleData_save:{
                if (this.isEditableFields){
                    saveVehicleData();
                } else {
                    this.isEditableFields = true;
                    setFieldsStatus();
                }


                break;
            }
        }

    }
}
