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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import br.com.hallisondesenv.combustivelmanagement.R;
import br.com.hallisondesenv.combustivelmanagement.adapter.AverageConsumptionAdapter;
import br.com.hallisondesenv.combustivelmanagement.dao.AverageConsumptionDao;
import br.com.hallisondesenv.combustivelmanagement.model.AverageConsumption;

/**
 * Classe responsável pelo fragment AverageConsumption
 *
 * Created by Hallison on 01/04/2016.
 */
public class AverageConsumptionFragment extends Fragment {

    List<AverageConsumption> averageConsumptions;
    AverageConsumptionAdapter adapter;
    ListView lsvAverageConsumptions;
    TextView txvGeneralAverage;

    /**
     * Construtor vazio
     */
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

    /**
     * Inicialização de todos os views da tela
     * @param view View do fragment para que os componentes do fragment possam ser encontrados através do findViewById
     */
    private void initializeComponents(View view){
        lsvAverageConsumptions = (ListView) view.findViewById(R.id.lsv_average_consumption);
        adapter = new AverageConsumptionAdapter(super.getContext(), averageConsumptions);
        lsvAverageConsumptions.setAdapter(adapter);
        lsvAverageConsumptions.setClickable(true);

        lsvAverageConsumptions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AverageConsumption averageConsumption = (AverageConsumption) adapter.getItem(position);
                Log.d("AverageConsumption", averageConsumption.toString());
            }
        });

        txvGeneralAverage = (TextView) view.findViewById(R.id.txv_average_consumption_general_average);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.btn_add_average_consumption);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AverageConsumptionActivity.class);
                startActivity(intent);
            }
        });

        txvGeneralAverage.setText(new DecimalFormat(".###").format(new AverageConsumption().getGeneralAverage(view.getContext())) + "Km/L");
    }

    /**
     * Busca todas as médias de consumo cadastradas
     * @param context Contexto da aplicação
     * @return Lista com as médias de consumo
     */
    private List<AverageConsumption> getAverageConsumptions(Context context){
        AverageConsumptionDao averageConsumptionDao = new AverageConsumptionDao();
        List<AverageConsumption> averageConsumptionsList = averageConsumptionDao.list(context);

        return averageConsumptionsList;
    }

}
