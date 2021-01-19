package com.tatabuku.app.ui.accounting.detail;

import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import com.tatabuku.app.databinding.ActivityDetailAccountingBinding;
import com.tatabuku.app.ui.BaseActivity;
import com.tatabuku.app.util.StringHelper;

import java.util.Calendar;

public class DetailAccountingActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener{

    private ActivityDetailAccountingBinding binding;
    private LinearLayoutManager layoutManager;
    private DetailAccountingListAdapter listAdapter;
    private DatePickerDialog datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupView();
        setupList();
        configureView();
    }

    private void setupView(){
        binding = ActivityDetailAccountingBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }

    private void setupList() {
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.list.setLayoutManager(layoutManager);

        listAdapter = new DetailAccountingListAdapter();
        binding.list.setAdapter(listAdapter);
    }

    private void setupDatePicker() {
        Calendar calendar = Calendar.getInstance();
        datePicker = new DatePickerDialog( this,
                DetailAccountingActivity.this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
    }

    private void configureView() {
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.tanggalJournal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker.show();
            }
        });
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        String date = year + "-" + (month+1) + "-" + day;
        binding.tanggalJournal.setText(StringHelper.formatDatePicker(date));
    }

}