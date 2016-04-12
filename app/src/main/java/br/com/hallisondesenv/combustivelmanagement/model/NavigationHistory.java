package br.com.hallisondesenv.combustivelmanagement.model;

import android.content.Context;

import java.io.Serializable;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Hallison on 11/04/2016.
 */
public class NavigationHistory extends RealmObject implements Serializable {

    @PrimaryKey
    private int id;
    private String date;
    private float distance;

    public NavigationHistory (){

    }

    public NavigationHistory (int id, String date, float distance){
        super();
        this.id = id;
        this.date = date;
        this.distance = distance;
    }

    public String getDate() {
        return date;
    }

    public float getDistance() {
        return distance;
    }

    public int getNextKey(Context context){
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(context).build();
        Realm realm = Realm.getInstance(realmConfiguration);

        try {
            return realm.where(NavigationHistory.class).max("id").intValue() + 1;
        } catch (Exception e){
            return 1;
        }
    }
}
