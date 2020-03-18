package com.example.onlineshop;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class MyDialog extends DialogFragment {

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        @SuppressLint("InflateParams") final View prompt = inflater.inflate(R.layout.dialog_add, null);

        final EditText carName = prompt.findViewById(R.id.carName);
        final EditText carYear = prompt.findViewById(R.id.carYear);
        final EditText carPrice = prompt.findViewById(R.id.carPrice);

        builder.setView(prompt)
                .setPositiveButton(R.string.add_car, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String name = carName.getText().toString();
                        Integer year = Integer.parseInt(carYear.getText().toString());
                        Integer price = Integer.parseInt(carPrice.getText().toString());

                        Car newCar = new Car(name, year, price, null);

                        MainActivity.carList.add(newCar);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        return builder.create();
    }
}
