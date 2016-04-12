package br.com.hallisondesenv.combustivelmanagement.dao;

import android.content.Context;
import android.util.Log;

import br.com.hallisondesenv.combustivelmanagement.model.VehicleData;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by Hallison on 23/03/2016.
 */
public class VehicleDataDao {

    private static final String TAG = "VehicleDataDAO";

    public void save(VehicleData vehicleData, Context context){
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(context).build();
        Realm realm = Realm.getInstance(realmConfiguration);

        realm.beginTransaction();

        realm.copyToRealmOrUpdate(vehicleData);

        realm.commitTransaction();

    }

    public VehicleData findById(Context context){
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(context).build();
        Realm realm = Realm.getInstance(realmConfiguration);

        try {
            realm.beginTransaction();

            RealmResults<VehicleData> results = realm.where(VehicleData.class).findAll();

            realm.commitTransaction();

            return results.get(0);
        } catch (ArrayIndexOutOfBoundsException e){
            Log.i(TAG, e.getMessage());
            return null;
        }
    }
}
