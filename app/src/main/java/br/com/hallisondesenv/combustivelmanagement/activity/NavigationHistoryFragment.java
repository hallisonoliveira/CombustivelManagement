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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_navigation_history, null);

        initializeComponents(view);

        return view;
    }

    private void initializeComponents(View view){
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
        navigationHistories = getNavigationHistories(context);
    }

    private List<NavigationHistory> getNavigationHistories(Context context){
        NavigationHistoryDao navigationHistoryDao = new NavigationHistoryDao();
        List<NavigationHistory> navigationHistories = navigationHistoryDao.list(context);

        return navigationHistories;
    }
}
