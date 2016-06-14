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

    private static final String TAG = VehicleDataDao.class.getSimpleName();

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

            VehicleData vehicleData = realm.where(VehicleData.class).equalTo("id", 1).findFirst();

            realm.commitTransaction();

            return vehicleData;
        } catch (Exception e){
            Log.i(TAG, e.getMessage());
            return null;
        }
    }

    public void fillTank(Context context){
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(context).build();
        Realm realm = Realm.getInstance(realmConfiguration);
        VehicleData vehicleData = realm.where(VehicleData.class).equalTo("id", 1).findFirst();
        realm.beginTransaction();
//        vehicleData.fillTank(g);
        realm.commitTransaction();
        Log.i(TAG,"Fill Tank >>> " + vehicleData.getRemainingVolume());
    }

    public void updateRemaingVolume(float newVolume, Context context){
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(context).build();
        Realm realm = Realm.getInstance(realmConfiguration);
        VehicleData vehicleData = realm.where(VehicleData.class).equalTo("id", 1).findFirst();
        realm.beginTransaction();
        vehicleData.setRemainingVolume(newVolume);
        realm.commitTransaction();
        Log.i(TAG,"Remaining Volume >>> " + vehicleData.getRemainingVolume());
    }
}
