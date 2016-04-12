package br.com.hallisondesenv.combustivelmanagement.model;

import android.content.Context;

import java.io.Serializable;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Hallison on 05/04/2016.
 */
public class AverageConsumption extends RealmObject implements Serializable {

    @PrimaryKey
    private int id;
    private String date;
    private float amountLiters;
    private float priceByLiter;
    private float km;

    public AverageConsumption(){}

    public AverageConsumption(int id, String date, float amountLiters, float priceByLiter, float km){
        this.id = id;
        this.date = date;
        this.amountLiters = amountLiters;
        this.priceByLiter = priceByLiter;
        this.km = km;
    }

    public Double getAverageConsumption(){
        double averageConsumption;
        averageConsumption = this.km / this.amountLiters;
        return averageConsumption;
    }

    @Override
    public String toString() {
        return "AverageConsumption{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", amountLiters=" + amountLiters +
                ", priceByLiter=" + priceByLiter +
                ", km=" + km +
                '}';
    }

    public int getNextKey(Context context){
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(context).build();
        Realm realm = Realm.getInstance(realmConfiguration);

        try {
            return realm.where(AverageConsumption.class).max("id").intValue() + 1;
        } catch (Exception e){
            return 1;
        }
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public float getAmountLiters() {
        return amountLiters;
    }

    public float getPriceByLiter() {
        return priceByLiter;
    }

    public float getKm() {
        return km;
    }
}

