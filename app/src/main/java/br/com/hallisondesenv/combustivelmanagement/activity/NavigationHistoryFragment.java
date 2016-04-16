package br.com.hallisondesenv.combustivelmanagement.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.com.hallisondesenv.combustivelmanagement.R;
import br.com.hallisondesenv.combustivelmanagement.adapter.NavigationHistoryAdapter;
import br.com.hallisondesenv.combustivelmanagement.dao.NavigationHistoryDao;
import br.com.hallisondesenv.combustivelmanagement.model.NavigationHistory;

/**
 * Classe responsável pelo fragment que exibe a lista de histórico de navegação
 *
 * Created by Hallison on 02/04/2016.
 */
public class NavigationHistoryFragment extends Fragment {

    List<NavigationHistory> navigationHistories;
    NavigationHistoryAdapter adapter;
    ListView lsvNavigationHistories;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        lsvNavigationHistories.setAdapter(adapter);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_navigation_history, null);

        initializeComponents(view);

        return view;

    }

    /**
     * Inicialização de todos os views da tela
     * @param view View do fragment para que os componentes do fragment possam ser encontrados através do findViewById
     */
    private void initializeComponents(View view){

        navigationHistories = getNavigationHistories(getContext());

        lsvNavigationHistories = (ListView) view.findViewById(R.id.lsv_navigationHistory);
        adapter = new NavigationHistoryAdapter(super.getContext(), navigationHistories);
        lsvNavigationHistories.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.btn_add_navigationHistory);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), NavigationHistoryActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    /**
     * Busca todos os históricos de navegação cadastrados
     * @param context Contexto da aplicação
     * @return Lista com todos os históricos cadastrados
     */
    private List<NavigationHistory> getNavigationHistories(Context context){
        NavigationHistoryDao navigationHistoryDao = new NavigationHistoryDao();
        List<NavigationHistory> navigationHistories = navigationHistoryDao.list(context);

        return navigationHistories;
    }
}
