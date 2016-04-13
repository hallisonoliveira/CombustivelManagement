package br.com.hallisondesenv.combustivelmanagement.dao;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.hallisondesenv.combustivelmanagement.model.AverageConsumption;
import br.com.hallisondesenv.combustivelmanagement.model.VehicleData;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by Hallison on 23/03/2016.
 */
public class AverageConsumptionDao {

    private static final String TAG = "AverageConsumptionDao";

    public void save(AverageConsumption averageConsumption, Context context){
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(context).build();
        Realm realm = Realm.getInstance(realmConfiguration);

        realm.beginTransaction();

        realm.copyToRealmOrUpdate(averageConsumption);

        realm.commitTransaction();

    }

    public List<AverageConsumption> list(Context context){
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(context).build();
        Realm realm = Realm.getInstance(realmConfiguration);

        realm.beginTransaction();

        List<AverageConsumption> averageConsumptions = realm.where(AverageConsumption.class).findAll();

        realm.commitTransaction();

        return averageConsumptions;

    }
}
