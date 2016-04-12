package br.com.hallisondesenv.combustivelmanagement.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.com.hallisondesenv.combustivelmanagement.R;
import br.com.hallisondesenv.combustivelmanagement.adapter.AverageConsumptionAdapter;
import br.com.hallisondesenv.combustivelmanagement.dao.AverageConsumptionDao;
import br.com.hallisondesenv.combustivelmanagement.model.AverageConsumption;

/**
 * Created by Hallison on 01/04/2016.
 */
public class AverageConsumptionFragment extends Fragment implements View.OnClickListener {

    List<AverageConsumption> averageConsumptions;
    AverageConsumptionAdapter adapter;
    ListView lsvAverageConsumptions;

    public AverageConsumptionFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        averageConsumptions = getAverageConsumptions(context);
    }


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_average_consumption, null);

        lsvAverageConsumptions = (ListView) view.findViewById(R.id.lsv_averageComsumption);
        adapter = new AverageConsumptionAdapter(super.getContext(), averageConsumptions);
        lsvAverageConsumptions.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.btn_add_averageComsumption);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AverageConsumptionActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private List<AverageConsumption> getAverageConsumptions(Context context){
        AverageConsumptionDao averageConsumptionDao = new AverageConsumptionDao();
        List<AverageConsumption> averageConsumptionsList = new ArrayList<AverageConsumption>();
        averageConsumptionsList = averageConsumptionDao.list(context);

        return averageConsumptionsList;
    }

    @Override
    public void onClick(View v) {


    }
}
