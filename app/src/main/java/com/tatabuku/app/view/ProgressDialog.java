package com.tatabuku.app.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.fragment.app.DialogFragment;

import com.tatabuku.app.databinding.ProgressLayoutBinding;

public class ProgressDialog extends DialogFragment {

    private ProgressLayoutBinding binding;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new
                AlertDialog.Builder(getActivity());
//use layoutinflater to inflate xml

        binding = ProgressLayoutBinding.inflate(LayoutInflater.from(getActivity()));
        builder.setView(binding.getRoot());
        return builder.create();
    }
}
