package br.com.hallisondesenv.combustivelmanagement.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import br.com.hallisondesenv.combustivelmanagement.R;

/**
 * Classe responsável pela activity About
 *
 * @author Hallison Oliveira on 16/04/2016
 */
public class AboutActivity extends AppCompatActivity {

    private ImageView imgAppIcon;


    /**
     * Método que inicia a activity
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tbr_about);
        setSupportActionBar(toolbar);

        this.imgAppIcon = (ImageView) findViewById(R.id.img_about);
        this.imgAppIcon.setImageResource(R.mipmap.comb_management);

    }
}
