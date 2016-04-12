package br.com.hallisondesenv.combustivelmanagement.activity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
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

    private Spinner spnFuelType;
    private Button btnSave;
    private EditText edtManufacturer;
    private EditText edtModel;
    private EditText edtYear;
    private ImageButton imgVehicle;

    private int vehicleId;

//    private OnFragmentInteractionListener mListener;

    public VehicleDataFragment() {
        // Required empty public constructor
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
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_vehicle_data, container, false);

        initializeComponents(view);

        loadVehicleData(view);

        return view;
    }

    private void initializeComponents(View view){
        // Inicializando o Spinner com os tipos de combustivel
        this.spnFuelType = (Spinner) view.findViewById(R.id.spn_vehicleData_fuelType);
        ArrayAdapter<CharSequence> fuelTypeAdapter = ArrayAdapter.createFromResource(view.getContext(),
                R.array.sta_fuelTypes, android.R.layout.simple_spinner_item);

        fuelTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnFuelType.setAdapter(fuelTypeAdapter);

        // Inicializando o botão que salva o cadastro do veículo
        this.btnSave = (Button) view.findViewById(R.id.btn_vehicleData_save);
        this.btnSave.setOnClickListener(this);

        // Inicializando o ImageButton
        this.imgVehicle = (ImageButton) view.findViewById(R.id.imb_vehicleData_vehicleImage);
        this.imgVehicle.setOnClickListener(this);

        // Inicializando o EditText de Marca
        this.edtManufacturer = (EditText) view.findViewById(R.id.edt_vehicleData_manufacturer);

        // Inicializando o EditText de Modelo
        this.edtModel = (EditText) view.findViewById(R.id.edt_vehicleData_model);
        // Inicializando o EditText de Ano
        this.edtYear = (EditText) view.findViewById(R.id.edt_vehicleData_year);

    }

    private void loadVehicleData(View view){

        VehicleData vehicleData = new VehicleDataDao().findById(view.getContext());

        if (vehicleData != null) {
            this.vehicleId = vehicleData.getId();
            this.edtManufacturer.setText(vehicleData.getManufacturer());
            this.edtModel.setText(vehicleData.getModel());
            this.edtYear.setText(String.valueOf(vehicleData.getYear()));
            this.spnFuelType.setSelection(vehicleData.getFuelType());
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

    private boolean isEmptyFields(String manufacturer, String model, String year) {
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
        }

        return false;
    }

    private void saveVehicleData(){

        int idToSave;

        if(!isEmptyFields(edtManufacturer.getText().toString(), edtModel.getText().toString(),
                edtYear.getText().toString())) {

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
                    spnFuelType.getSelectedItemPosition());

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
