package br.com.hallisondesenv.combustivelmanagement.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

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
    TextView txvGeneralAverage;

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

        initializeComponents(view);

        return view;
    }

    private void initializeComponents(View view){
        lsvAverageConsumptions = (ListView) view.findViewById(R.id.lsv_averageComsumption);
        adapter = new AverageConsumptionAdapter(super.getContext(), averageConsumptions);
        lsvAverageConsumptions.setAdapter(adapter);

        setListViewHeightBasedOnChildren(lsvAverageConsumptions);


        txvGeneralAverage = (TextView) view.findViewById(R.id.txv_averageConsumption_generalAverage);


        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.btn_add_averageComsumption);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AverageConsumptionActivity.class);
                startActivity(intent);
            }
        });

        txvGeneralAverage.setText(String.valueOf(new AverageConsumption().getGeneralAverage(view.getContext())));
    }

    private List<AverageConsumption> getAverageConsumptions(Context context){
        AverageConsumptionDao averageConsumptionDao = new AverageConsumptionDao();
        List<AverageConsumption> averageConsumptionsList = averageConsumptionDao.list(context);

        return averageConsumptionsList;
    }

    @Override
    public void onClick(View v) {


    }


    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

}
