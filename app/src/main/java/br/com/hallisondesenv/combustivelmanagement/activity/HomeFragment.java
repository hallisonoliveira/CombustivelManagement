package br.com.hallisondesenv.combustivelmanagement.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import br.com.hallisondesenv.combustivelmanagement.R;
import br.com.hallisondesenv.combustivelmanagement.dao.VehicleDataDao;
import br.com.hallisondesenv.combustivelmanagement.model.AverageConsumption;
import br.com.hallisondesenv.combustivelmanagement.model.VehicleData;


public class HomeFragment extends Fragment {

    public static final String TAG = "HomeFragment";

    private TextView txvGeneralAverage;
    private TextView txvAverageSpending;
    private TextView txvRemainingVolume;
    private TextView txvApproximateAutonomy;
    private ImageView imgApp;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initializeComponents(view);
        return view;
    }

    private void initializeComponents(View view){

        this.imgApp = (ImageView) view.findViewById(R.id.img_main);
        this.imgApp.setImageResource(R.mipmap.comb_management);

        this.txvGeneralAverage = (TextView) view.findViewById(R.id.txv_home_generalAverage);
        this.txvApproximateAutonomy = (TextView) view.findViewById(R.id.txv_home_approximateAutonomy);
        this.txvRemainingVolume = (TextView) view.findViewById(R.id.txv_home_remainingVolume);
        this.txvAverageSpending = (TextView) view.findViewById(R.id.txv_home_averageSpending);

        if (hasVehicleData()){
            this.txvGeneralAverage.setText(new DecimalFormat(".###").format(getGeneralAverage()));
            this.txvApproximateAutonomy.setText(new DecimalFormat(".###").format(getApproximateAutonomy()));
            this.txvRemainingVolume.setText(new DecimalFormat(".###").format(getRemainingVolume()));
            this.txvAverageSpending.setText(NumberFormat.getCurrencyInstance().format(getAverageSpending()));
        } else {

            Toast.makeText(getContext(), R.string.error_vehicleData_required, Toast.LENGTH_LONG).show();

            this.txvGeneralAverage.setText(R.string.main_noValue);
            this.txvApproximateAutonomy.setText(R.string.main_noValue);
            this.txvRemainingVolume.setText(R.string.main_noValue);
            this.txvAverageSpending.setText(R.string.main_noValue);
        }

    }

    private float getApproximateAutonomy(){
        float approximateAutonomy = 0;

        approximateAutonomy = getRemainingVolume() * getGeneralAverage();

        return approximateAutonomy;
    }

    private float getRemainingVolume(){
        VehicleDataDao vehicleDataDao = new VehicleDataDao();
        VehicleData vehicleData = vehicleDataDao.findById(getContext());

        if (vehicleData != null) {
            return vehicleData.getRemainingVolume();
        } else {
            return 0;
        }
    }

    @NonNull
    private float getGeneralAverage(){
        float generalAverage = new AverageConsumption().getGeneralAverage(getContext());

//        if (generalAverage > 0){
            return generalAverage;
//        } else {
//            return 0.0f;
//        }

    }

    @NonNull
    private float getAverageSpending(){
        return new AverageConsumption().getAverageSpending(getContext());
    }

    private boolean hasVehicleData(){
        VehicleDataDao vehicleDataDao = new VehicleDataDao();
        VehicleData vehicleData = vehicleDataDao.findById(getContext());

        if (vehicleData != null) {
            return true;
        } else {
            return false;
        }
    }


}
