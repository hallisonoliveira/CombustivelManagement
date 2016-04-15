package br.com.hallisondesenv.combustivelmanagement.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.text.NumberFormat;

/**
 * Created by Hallison on 13/04/2016.
 */

public abstract class MaskUtil implements TextWatcher{

    public static String removeMask(String string){
        return string.replaceAll("[^\\d.]", "");
    }

    public static Float convertStringToFloat(String string){
        string = string.replaceAll("[R$]", "").replaceAll("[.]", "");
        string = string.replaceAll("[,]", ".");

        Float value = Float.parseFloat(string);

        return value;
    }

    public static TextWatcher insertMask(final EditText editText) {
        return new TextWatcher() {
            private boolean isUpdating = false;
            private NumberFormat numberFormat = NumberFormat.getCurrencyInstance();

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (isUpdating) {
                    isUpdating = false;
                    return;
                }

                isUpdating = true;

                String formattedValue = removeMask(charSequence.toString());

                try {
                    double value = (Double.parseDouble(formattedValue) / 100);
                    formattedValue = numberFormat.format(value);
                } catch (Exception e) {
                    e.printStackTrace();
                    formattedValue = numberFormat.format(0.0D);
                }

                editText.setText(formattedValue);
                editText.setSelection(formattedValue.length());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
    }

}