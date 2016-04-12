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
    private int fuelType;

    public VehicleData(){}

    public VehicleData(int id, String manufacturer, String model, int year, int fuelType){
        super();
        this.id = id;
        this.manufacturer = manufacturer;
        this.model = model;
        this.year = year;
        this.fuelType = fuelType;
    }

    @Override
    public String toString() {
        return "VehicleData{" +
                "id=" + id +
                ", manufacturer='" + manufacturer + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", fuelType='" + fuelType + '\'' +
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

    public int getFuelType() {
        return fuelType;
    }
}