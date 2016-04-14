package br.com.hallisondesenv.combustivelmanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.hallisondesenv.combustivelmanagement.R;
import br.com.hallisondesenv.combustivelmanagement.model.NavigationHistory;

/**
 * Created by Hallison on 05/04/2016.
 */
public class NavigationHistoryAdapter extends BaseAdapter {

    Context context;
    List<NavigationHistory> navigationHistories;

    public NavigationHistoryAdapter(Context context, List<NavigationHistory> navigationHistories){
        this.context = context;
        this.navigationHistories = navigationHistories;
    }

    @Override
    public int getCount() {
        return navigationHistories.size();
    }

    @Override
    public Object getItem(int position) {
        return navigationHistories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NavigationHistory navigationHistory = navigationHistories.get(position);

        View line = LayoutInflater.from(context).inflate(R.layout.item_navigation_history, null);

        TextView txvDate = (TextView) line.findViewById(R.id.item_navigationHistory_date);
        txvDate.setText(navigationHistory.getDate());

        TextView txvDistance = (TextView) line.findViewById(R.id.item_navigationHistory_distance);
        txvDistance.setText(String.valueOf(navigationHistory.getDistance()));


        return line;
    }

    static class ViewHolder{
        TextView txvDate;
        TextView txvDistance;
    }
}
