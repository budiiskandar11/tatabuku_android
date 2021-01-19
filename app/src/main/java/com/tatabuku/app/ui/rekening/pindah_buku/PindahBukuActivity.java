package com.tatabuku.app.ui.rekening.pindah_buku;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.DatePicker;

import com.tatabuku.app.R;
import com.tatabuku.app.databinding.ActivityPindahBukuBinding;
import com.tatabuku.app.model.rekening.PindahBukuBank;
import com.tatabuku.app.model.rekening.PindahBukuHeader;
import com.tatabuku.app.ui.BaseActivity;
import com.tatabuku.app.util.StringHelper;

import java.util.Calendar;
import java.util.List;

public class PindahBukuActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener {

    public final static String BANK_FROM_ID = "BANK_FROM_ID";

    private ActivityPindahBukuBinding binding;
    private LinearLayoutManager layoutManager;
    private PindahBukuListAdapter listAdapter;
    private PindahBukuViewModel viewModel;
    private PindahBukuListener listener;
    private DatePickerDialog datePicker;

    private String currentNominal = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupViewModel();
        configureViewModel();
        setupView();
        setupDatePicker();
        configureView();
        setupList();

        configureIntent();
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(PindahBukuViewModel.class);
    }

    private void configureViewModel() {
        viewModel.getHeader().observe(this, new Observer<PindahBukuHeader>() {
            @Override
            public void onChanged(PindahBukuHeader header) {
                binding.bankType.setText(header.getBankName());
                binding.bankAmount.setText(StringHelper.numberFormatWithDecimal(header.getTotalSaldo()));
            }
        });

        viewModel.getBanks().observe(this, new Observer<List<PindahBukuBank>>() {
            @Override
            public void onChanged(List<PindahBukuBank> pindahBukuBanks) {
                listAdapter.notifyDataSetChanged();
            }
        });

        viewModel.getToBank().observe(this, new Observer<PindahBukuBank>() {
            @Override
            public void onChanged(PindahBukuBank pindahBukuBank) {
                listAdapter.notifyDataSetChanged();
            }
        });

        viewModel.getOnSuccess().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                hideLoading();
                finish();
            }
        });

        viewModel.getOnError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                hideLoading();
                showAlert("Perhatian", s);
            }
        });
    }

    private void setupView(){
        binding = ActivityPindahBukuBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }

    private void setupDatePicker() {
        Calendar calendar = Calendar.getInstance();
        datePicker = new DatePickerDialog( this,
                PindahBukuActivity.this,
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

        binding.buttonBatalkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.tanggalPindahBuku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker.show();
            }
        });

        binding.buttonPosting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoading();
                String amount = binding.nominalPindahBuku.getText().toString().replaceAll("[,.]","").trim();
                String date = binding.tanggalPindahBuku.getText().toString();
                viewModel.submitPindahBuku(amount, StringHelper.formatBackendDate(date));
            }
        });

        binding.nominalPindahBuku.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (!s.toString().equals(currentNominal)) {
                    binding.nominalPindahBuku.removeTextChangedListener(this);
                    String cleanString = s.toString().replaceAll("[,.]", "").replaceAll(" ", "").trim();
                    if (!cleanString.equals("")) {
                        double parsed = Double.parseDouble(cleanString);
                        String formatted = StringHelper.numberFormatWithoutCurrency(parsed);

                        currentNominal = formatted;
                        binding.nominalPindahBuku.setText(formatted);
                        binding.nominalPindahBuku.setSelection(formatted.length());
                    } else {
                        currentNominal = "";
                        binding.nominalPindahBuku.setText("");
                    }
                    binding.nominalPindahBuku.addTextChangedListener(this);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        listener = new PindahBukuListener() {
            @Override
            public void onItemClickListener(PindahBukuBank bank) {
                viewModel.setToBank(bank);
            }
        };
    }

    private void setupList(){
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.list.setLayoutManager(layoutManager);

        listAdapter = new PindahBukuListAdapter(viewModel);
        listAdapter.setListener(listener);
        binding.list.setAdapter(listAdapter);
    }

    private void configureIntent(){
        Intent intent = getIntent();
        int bank_from = intent.getIntExtra(BANK_FROM_ID, 0);
        viewModel.fetchData(bank_from);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        String date = year + "-" + (month+1) + "-" + day;
        binding.tanggalPindahBuku.setText(StringHelper.formatDatePicker(date));
    }
}