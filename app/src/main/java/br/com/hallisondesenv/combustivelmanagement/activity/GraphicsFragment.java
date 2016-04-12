package br.com.hallisondesenv.combustivelmanagement.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.hallisondesenv.combustivelmanagement.R;

/**
 * Created by Hallison on 02/04/2016.
 */
public class GraphicsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_graphics, container, false);
        return view;
    }
}
