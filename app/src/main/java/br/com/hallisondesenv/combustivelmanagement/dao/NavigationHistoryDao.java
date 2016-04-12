package br.com.hallisondesenv.combustivelmanagement.dao;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import br.com.hallisondesenv.combustivelmanagement.model.AverageConsumption;
import br.com.hallisondesenv.combustivelmanagement.model.NavigationHistory;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Hallison on 23/03/2016.
 */
public class NavigationHistoryDao {

    private static final String TAG = "NavigationHistoryDao";

    public void save(NavigationHistory navigationHistory, Context context){
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(context).build();
        Realm realm = Realm.getInstance(realmConfiguration);

        realm.beginTransaction();

        realm.copyToRealmOrUpdate(navigationHistory);

        realm.commitTransaction();

    }

    public List<NavigationHistory> list(Context context){
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(context).build();
        Realm realm = Realm.getInstance(realmConfiguration);
        List<NavigationHistory> navigationHistories;

        realm.beginTransaction();

        navigationHistories = realm.where(NavigationHistory.class).findAll();

        realm.commitTransaction();

        return navigationHistories;

    }
}
