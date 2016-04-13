package br.com.hallisondesenv.combustivelmanagement.model;

import android.content.Context;
import android.util.Log;

import java.io.Serializable;
import java.util.List;

import br.com.hallisondesenv.combustivelmanagement.dao.AverageConsumptionDao;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Hallison on 05/04/2016.
 */
public class AverageConsumption extends RealmObject implements Serializable {

    private static final String TAG = "AverageConsumption";

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

    public float getAverageConsumption(){
        float averageConsumption;
        averageConsumption =  this.km / this.amountLiters;
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

    public float getGeneralAverage(Context context){
        AverageConsumptionDao averageConsumptionDao = new AverageConsumptionDao();

        float averageConsumptionSum = 0;
        
        List<AverageConsumption> averageConsumptions = averageConsumptionDao.list(context);

        if (averageConsumptions.size() > 0) {

            for (AverageConsumption averageConsumption : averageConsumptions) {
                averageConsumptionSum = averageConsumptionSum + averageConsumption.getAverageConsumption();
            }

            float generalAverage = averageConsumptionSum / averageConsumptions.size();

            return generalAverage;
        } else {
            return 0;
        }
    }

    public float getAverageSpending(Context context){
        AverageConsumptionDao averageConsumptionDao = new AverageConsumptionDao();

        float averageSpending = 0;

        List<AverageConsumption> averageConsumptions = averageConsumptionDao.list(context);

        if (averageConsumptions.size() > 0) {

            for (AverageConsumption averageConsumption : averageConsumptions) {
                averageSpending = averageSpending + averageConsumption.getSpending();
            }

            return averageSpending;
        } else {
            return 0;
        }
    }

    public float getSpending(){
        return this.amountLiters * this.priceByLiter;
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

