package br.com.hallisondesenv.combustivelmanagement.model;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Hallison on 13/03/2016.
 */
public class VehicleData extends RealmObject implements Serializable{

    @PrimaryKey
    private int id;
    private String manufacturer;
    private String model;
    private int year;
    private int fuelCapacity;
    private float remainingVolume;

    public VehicleData(){}

    public VehicleData(int id, String manufacturer, String model, int year, int fuelCapacity, float remainingVolume){
        super();
        this.id = id;
        this.manufacturer = manufacturer;
        this.model = model;
        this.year = year;
        this.fuelCapacity = fuelCapacity;
        this.remainingVolume = remainingVolume;
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
                '}';
    }

    public int getId() {
        return id;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public int getFuelCapacity() {
        return fuelCapacity;
    }

    public float getRemainingVolume() {
        return remainingVolume;
    }

    public void setRemainingVolume(float volume) {
        this.remainingVolume = remainingVolume - volume;
    }

    public void fillTank() {
        this.remainingVolume = this.fuelCapacity;
    }
}
