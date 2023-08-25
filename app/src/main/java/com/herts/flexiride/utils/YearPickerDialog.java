package com.herts.flexiride.utils;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.herts.flexiride.R;

import java.util.Calendar;

public class YearPickerDialog extends DialogFragment {

    private int MAX_YEAR = Calendar.getInstance().get(Calendar.YEAR);
    private int MIN_YEAR = 1950;
    private DatePickerDialog.OnDateSetListener listener;

    public void setListener(DatePickerDialog.OnDateSetListener listener) {
        this.listener = listener;
    }


    public void setMAX_YEAR(int MAX_YEAR) {
        this.MAX_YEAR = MAX_YEAR;
    }

    public void setMIN_YEAR(int MIN_YEAR) {
        this.MIN_YEAR = MIN_YEAR;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
        LayoutInflater inflater = getActivity().getLayoutInflater();

        Calendar cal = Calendar.getInstance();

        View dialog = inflater.inflate(R.layout.year_picker_dialog, null);
        final NumberPicker yearPicker = (NumberPicker) dialog.findViewById(R.id.picker_year);

        int year = cal.get(Calendar.YEAR);
        yearPicker.setMinValue(MIN_YEAR);
        yearPicker.setMaxValue(MAX_YEAR);
        yearPicker.setValue(year);

        builder.setView(dialog).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
//                if (getActivity() instanceof Home)
//                    ((Home)getActivity()).setBottomBarVisivelty(View.VISIBLE);

                listener.onDateSet(null, yearPicker.getValue(), 0, 0);
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
//                if (getActivity() instanceof Home)
//                    ((Home)getActivity()).setBottomBarVisivelty(View.VISIBLE);

                YearPickerDialog.this.getDialog().cancel();
            }
        });
        return builder.create();
    }

    @Override
    public void onResume() {
        super.onResume();
        int width = getResources().getDimensionPixelSize(R.dimen.popup_width);
        int height = getResources().getDimensionPixelSize(R.dimen.popup_height);
        getDialog().getWindow().setLayout(width, height);
    }
}