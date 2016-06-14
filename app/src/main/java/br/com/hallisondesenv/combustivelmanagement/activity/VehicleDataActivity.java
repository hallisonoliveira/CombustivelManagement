package br.com.hallisondesenv.combustivelmanagement.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.hallisondesenv.combustivelmanagement.R;
import br.com.hallisondesenv.combustivelmanagement.model.VehicleData;

/**
 * Created by ${USER_NAME} on 13/06/2016.
 */
public class VehicleDataActivity extends AppCompatActivity {

    private static final String TAG = VehicleDataActivity.class.getSimpleName();
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private static final int VEHICLE_ID = 1;

    private File vehicleImage = null;

    private EditText edtManufacturer;
    private EditText edtModel;
    private EditText edtYear;
    private EditText edtFuelCapacity;
    private ImageButton imbVehicleImage;
    private FloatingActionButton fabSave;

    VehicleData vehicleData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_data);

        setUI();

        vehicleData = new VehicleData().getVehicleData(getBaseContext());

        if (vehicleData == null){
            vehicleData = new VehicleData();
            Toast.makeText(this, R.string.info_vehicle_data_empty, Toast.LENGTH_SHORT).show();
        } else {
            setVehicleDataOnFields();
        }

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

    private void setVehicleDataOnFields(){

    }


    private void setUI(){
        edtManufacturer = (EditText) findViewById(R.id.edittext_vehicledata_manufacturer);
        edtModel = (EditText) findViewById(R.id.edittext_vehicledata_model);
        edtFuelCapacity = (EditText) findViewById(R.id.edittext_vehicledata_fuelcapacity);
        edtYear = (EditText) findViewById(R.id.edittext_vehicledata_year);

        imbVehicleImage = (ImageButton) this.findViewById(R.id.imageview_vehicledata_vehicleimage);
        imbVehicleImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImageFromCamera();
            }
        });

        fabSave = (FloatingActionButton) findViewById(R.id.button_vehicledata_save);
        fabSave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                saveVehicleData(view);
            }
        });
    }

    private void getImageFromCamera(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        vehicleImage = null;

        if (takePictureIntent.resolveActivity(this.getPackageManager()) != null) {
            try {
                vehicleImage = createImageFile();
            } catch (IOException ex) {
                Log.e(TAG, "Erro ao criar arquivo.\n", ex.getCause());
            }

            if (vehicleImage != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(vehicleImage));
                startActivityForResult(takePictureIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                Uri uri = Uri.parse(vehicleImage.getAbsolutePath());
                imbVehicleImage.setImageURI(uri);

            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this,"Operação cancelada", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this,"Erro ao capturar foto.", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private File createImageFile() throws IOException {
        // Create an image file name

        String filePrefix = "CM_vehicleData";
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = filePrefix + "_" + timeStamp + "_";

        File storageDirectory = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES + "/" + "CombustivelManagement"
        );

        if (!storageDirectory.exists()) {
            File photoDirectory = new File(storageDirectory.getPath());
            photoDirectory.mkdirs();
        }

        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDirectory
        );

        // Save a file: path for use with ACTION_VIEW intents
//        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    private void saveVehicleData(View view){
        if(!isEmptyFields(
                edtManufacturer.getText().toString(),
                edtModel.getText().toString(),
                edtYear.getText().toString(),
                edtFuelCapacity.getText().toString())) {

            float remaingVolume;
            if(vehicleData == null){
                remaingVolume = 0;
            } else {
                remaingVolume = vehicleData.getRemainingVolume();
            }

            String vehicleImagePath = new String();
            if (vehicleImage == null){
                vehicleImagePath = "";
            } else {
                vehicleImagePath = vehicleImage.getAbsolutePath();
            }

            VehicleData vehicleData = new VehicleData(
                    VEHICLE_ID,
                    edtManufacturer.getText().toString(),
                    edtModel.getText().toString(),
                    Integer.valueOf(edtYear.getText().toString()),
                    Integer.valueOf(edtFuelCapacity.getText().toString()),
                    remaingVolume,
                    vehicleImagePath);

            try{
                this.vehicleData.save(vehicleData, this);
                Snackbar.make(view, getString(R.string.info_vehicle_saved), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            } catch (Exception e){
                Log.e(TAG, "Erro ao salvar os dados do veículo.", e);
            }


        }

    }
}
