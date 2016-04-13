package br.com.hallisondesenv.combustivelmanagement.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import br.com.hallisondesenv.combustivelmanagement.R;
import br.com.hallisondesenv.combustivelmanagement.model.AverageConsumption;


public class HomeFragment extends Fragment {

    public static final String TAG = "HomeFragment";

    private TextView txvGeneralAverage;
    private TextView txvAverageSpending;
    private TextView txvRemainingVolume;
    private TextView txvApproximateAutonomy;

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
        this.txvGeneralAverage = (TextView) view.findViewById(R.id.txv_home_generalAverage);
        this.txvAverageSpending = (TextView) view.findViewById(R.id.txv_home_averageSpending);
        this.txvApproximateAutonomy = (TextView) view.findViewById(R.id.txv_home_approximateAutonomy);
        this.txvRemainingVolume = (TextView) view.findViewById(R.id.txv_home_remainingVolume);

        this.txvGeneralAverage.setText(getGeneralAverage());
        this.txvAverageSpending.setText(getAverageSpending());

    }

    @NonNull
    private String getGeneralAverage(){
        return String.valueOf(new AverageConsumption().getGeneralAverage(getContext()));
    }

    @NonNull
    private String getAverageSpending(){
        return String.valueOf(new AverageConsumption().getAverageSpending(getContext()));
    }


}
