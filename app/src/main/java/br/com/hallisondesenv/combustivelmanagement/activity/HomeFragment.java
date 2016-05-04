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

/**
 * Classe responsável pelo fragment Home
 *
 * Created by Hallison on 01/04/2016.
 */
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

    /**
     * Inicialização dos view da tela
     * @param view View do fragment para que os componentes do fragment possam ser encontrados através do findViewById
     */
    private void initializeComponents(View view){

        this.imgApp = (ImageView) view.findViewById(R.id.img_main);
        this.imgApp.setImageResource(R.mipmap.comb_management);

        this.txvGeneralAverage = (TextView) view.findViewById(R.id.txv_home_general_average);
        this.txvApproximateAutonomy = (TextView) view.findViewById(R.id.txv_home_approximate_autonomy);
        this.txvRemainingVolume = (TextView) view.findViewById(R.id.txv_home_remaining_volume);
        this.txvAverageSpending = (TextView) view.findViewById(R.id.txv_home_average_spending);

        // Verifica se há dados de veículo cadastrados
        // Se já houver dados do veículo, busca as informações e apresenta na tela com as devidas máscaras
        if (hasVehicleData()){
            this.txvGeneralAverage.setText(new DecimalFormat(".###").format(getGeneralAverage()));
            this.txvApproximateAutonomy.setText(new DecimalFormat(".###").format(getApproximateAutonomy()));
            this.txvRemainingVolume.setText(new DecimalFormat(".###").format(getRemainingVolume()));
            this.txvAverageSpending.setText(NumberFormat.getCurrencyInstance().format(getAverageSpending()));

        // Se não houver, informa ao usuário que é necessário cadastrar os dados do veículo
        } else {
            Toast.makeText(getContext(), R.string.error_vehicle_data_required, Toast.LENGTH_LONG).show();

            this.txvGeneralAverage.setText(R.string.main_no_value);
            this.txvApproximateAutonomy.setText(R.string.main_no_value);
            this.txvRemainingVolume.setText(R.string.main_no_value);
            this.txvAverageSpending.setText(R.string.main_no_value);
        }

    }

    /**
     * Calcula a autonomia aproximada com base na quantidade de combustível restante e a média geral de consumo
     * @return Autonomia aproximada (float)
     */
    private float getApproximateAutonomy(){
        float approximateAutonomy = 0;

        approximateAutonomy = getRemainingVolume() * getGeneralAverage();

        return approximateAutonomy;
    }

    /**
     * Busca no objeto VehicleData a quantidade de combustível restante
     * @return Quantidade de combustível restante
     */
    private float getRemainingVolume(){
        VehicleDataDao vehicleDataDao = new VehicleDataDao();
        VehicleData vehicleData = vehicleDataDao.findById(getContext());

        if (vehicleData != null) {
            return vehicleData.getRemainingVolume();
        } else {
            return 0;
        }
    }

    /**
     * Busca a média geral calculada com base em todas as médias de consumo cadastrada
     * @return Média geral
     */
    @NonNull
    private float getGeneralAverage(){
        float generalAverage = new AverageConsumption().getGeneralAverage(getContext());

        return generalAverage;
    }


    /**
     * Busca o total de gastos financeiros com combustível
     * @return Total de gastos
     */
    @NonNull
    private float getAverageSpending(){
        return new AverageConsumption().getAverageSpending(getContext());
    }

    /**
     * Verifica se já há dados de veículo cadastrados
     * @return True se houver cadastro e False se não houver
     */
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
