package br.com.hallisondesenv.combustivelmanagement.model;

import android.content.Context;
import android.util.Log;


import java.io.Serializable;

import br.com.hallisondesenv.combustivelmanagement.R;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.exceptions.RealmException;

/**
 * Created by ${USER_NAME} on 19/05/2016.
 */
public class VehicleData extends RealmObject implements Serializable{

    private static final String TAG = VehicleData.class.getSimpleName();

    @PrimaryKey
    private int id;
    private String manufacturer;
    private String model;
    private int year;
    private int fuelCapacity;
    private float remainingVolume;
    private String imageFile;

    public VehicleData() {
    }

    public VehicleData(
            int id,
            String manufacturer,
            String model,
            int year,
            int fuelCapacity,
            float remainingVolume,
            String imageFile) {
        this.id = id;
        this.manufacturer = manufacturer;
        this.model = model;
        this.year = year;
        this.fuelCapacity = fuelCapacity;
        this.remainingVolume = remainingVolume;
        this.imageFile = imageFile;
    }


    @Override
    public String toString() {
        return "VehicleData{" +
                "id=" + id +
                ", manufacturer='" + manufacturer + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", fuelCapacity=" + fuelCapacity +
                ", remainingVolume=" + remainingVolume +
                ", imageFile='" + imageFile + '\'' +
                '}';
    }

    public boolean save (VehicleData vehicleData, Context context){
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(context).build();
        Realm realm = Realm.getInstance(realmConfiguration);

        try{
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(vehicleData);
            realm.commitTransaction();
            return true;
        } catch (Exception e){
            Log.e(TAG, context.getString(R.string.error_average_consumption_not_saved), e);
            return false;

        }
    }

    public VehicleData getVehicleData(Context context){
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(context).build();
        Realm realm = Realm.getInstance(realmConfiguration);

        try {
            realm.beginTransaction();
            VehicleData vehicleData = realm.where(VehicleData.class).equalTo("id", 1).findFirst();
            realm.commitTransaction();
            return vehicleData;
        } catch (Exception e){
            Log.e(TAG, context.getString(R.string.error_find_vehicle_data), e);
            return null;
        }
    }

    public void fillTank(Context context){
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(context).build();
        Realm realm = Realm.getInstance(realmConfiguration);

        VehicleData vehicleData;

        try {
            vehicleData = realm.where(VehicleData.class).equalTo("id", 1).findFirst();
            realm.beginTransaction();
            vehicleData.remainingVolume = vehicleData.fuelCapacity;
            realm.commitTransaction();
        } catch (RealmException e){
            Log.e(TAG, context.getString(R.string.error_fill_vehicle_tank), e);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getFuelCapacity() {
        return fuelCapacity;
    }

    public void setFuelCapacity(int fuelCapacity) {
        this.fuelCapacity = fuelCapacity;
    }

    public float getRemainingVolume() {
        return remainingVolume;
    }

    public void setRemainingVolume(float remainingVolume) {
        this.remainingVolume = remainingVolume;
    }

    public String getImageFile() {
        return imageFile;
    }

    public void setImageFile(String imageFile) {
        this.imageFile = imageFile;
    }
}
