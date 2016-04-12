package br.com.hallisondesenv.combustivelmanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.hallisondesenv.combustivelmanagement.R;
import br.com.hallisondesenv.combustivelmanagement.model.AverageConsumption;

/**
 * Created by Hallison on 05/04/2016.
 */
public class AverageConsumptionAdapter extends BaseAdapter {

    Context context;
    List<AverageConsumption> averageConsumptions;

    public AverageConsumptionAdapter (Context context, List<AverageConsumption> averageConsumptions){
        this.context = context;
        this.averageConsumptions = averageConsumptions;
    }

    @Override
    public int getCount() {
        return averageConsumptions.size();
    }

    @Override
    public Object getItem(int position) {
        return averageConsumptions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AverageConsumption averageConsumption = averageConsumptions.get(position);

        View line = LayoutInflater.from(context).inflate(R.layout.item_average_consumption, null);

        TextView txvDate = (TextView) line.findViewById(R.id.item_averageConsumption_date);
        TextView txvAmountLiters = (TextView) line.findViewById(R.id.item_averageConsumption_amountLiters);
        TextView txvAverage = (TextView) line.findViewById(R.id.item_averageConsumption_average);

        txvDate.setText(averageConsumption.getDate());
        txvAmountLiters.setText(String.valueOf(averageConsumption.getAmountLiters()));
        txvAverage.setText(String.valueOf(averageConsumption.getAverageConsumption()));

        return line;
    }
}