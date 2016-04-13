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


public class VehicleDataFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = "VehicleDataFragment";

    private EditText edtManufacturer;
    private EditText edtModel;
    private EditText edtYear;
    private EditText edtFuelCapacity;
    private ImageButton imgVehicle;
    private FloatingActionButton fabSave;


    private int vehicleId;


    public VehicleDataFragment() {
    }

    public static VehicleDataFragment newInstance() {
        VehicleDataFragment fragment = new VehicleDataFragment();
        return fragment;
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

    private void initializeComponents(View view){
        edtFuelCapacity = (EditText) view.findViewById(R.id.edt_vehicleData_fuelCapacity);

        // Inicializando o ImageButton
        this.imgVehicle = (ImageButton) view.findViewById(R.id.imb_vehicleData_vehicleImage);
        this.imgVehicle.setOnClickListener(this);

        // Inicializando o EditText de Marca
        this.edtManufacturer = (EditText) view.findViewById(R.id.edt_vehicleData_manufacturer);

        // Inicializando o EditText de Modelo
        this.edtModel = (EditText) view.findViewById(R.id.edt_vehicleData_model);
        // Inicializando o EditText de Ano
        this.edtYear = (EditText) view.findViewById(R.id.edt_vehicleData_year);

        fabSave = (FloatingActionButton) view.findViewById(R.id.btn_vehicleData_save);
        fabSave.setOnClickListener(this);

    }

    private void loadVehicleData(View view){

        VehicleData vehicleData = new VehicleDataDao().findById(view.getContext());

        if (vehicleData != null) {
            this.vehicleId = vehicleData.getId();
            this.edtManufacturer.setText(vehicleData.getManufacturer());
            this.edtModel.setText(vehicleData.getModel());
            this.edtYear.setText(String.valueOf(vehicleData.getYear()));
            this.edtFuelCapacity.setText(String.valueOf(vehicleData.getFuelCapacity()));
        } else {
            this.vehicleId = 0;
            Toast.makeText(view.getContext(), "Nenhum veículo cadastrado.", Toast.LENGTH_SHORT).show();
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

    private void saveVehicleData(){

        int idToSave;

        if(!isEmptyFields(edtManufacturer.getText().toString(), edtModel.getText().toString(),
                edtYear.getText().toString(), edtFuelCapacity.getText().toString())) {

            if (vehicleId == 0) {
                idToSave = 1;
            } else {
                idToSave = vehicleId;
            }

            VehicleData vehicleData = new VehicleData(
                    idToSave,
                    edtManufacturer.getText().toString(),
                    edtModel.getText().toString(),
                    Integer.parseInt(edtYear.getText().toString()),
                    Integer.parseInt(edtFuelCapacity.getText().toString()));

            try {
                VehicleDataDao vehicleDataDao = new VehicleDataDao();
                vehicleDataDao.save(vehicleData, getContext());

                Toast.makeText(super.getContext(), "Veículo salvo com sucesso.", Toast.LENGTH_SHORT).show();

            } catch (Exception e){
                Toast.makeText(super.getContext(), "Houve um erro ao salvar os dados do veículo.", Toast.LENGTH_SHORT).show();
                Log.e(TAG, e.getMessage());
            }

        } else {
            Log.e(TAG, "Campos obrigatórios em branco.");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_vehicleData_save:{
                    saveVehicleData();
                break;
            }
        }

    }
}
