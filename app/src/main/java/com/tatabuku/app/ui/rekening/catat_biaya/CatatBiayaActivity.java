package com.tatabuku.app.ui.rekening.catat_biaya;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;

import com.tatabuku.app.databinding.ActivityCatatBiayaBinding;
import com.tatabuku.app.model.rekening.CatatBiayaHeader;
import com.tatabuku.app.model.rekening.CatatBiayaList;
import com.tatabuku.app.ui.BaseActivity;
import com.tatabuku.app.ui.rekening.rincian_biaya.RincianBiayaActivity;
import com.tatabuku.app.util.StringHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CatatBiayaActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener {

    public static final String CATAT_BIAYA_BANK_ID = "FROM_BANK";
    public static final String CATAT_BIAYA_BANK_NAME = "FROM_BANK_NAME";
    public static final String CATAT_BIAYA_TYPE = "CATAT_BIAYA_TYPE";

    public static final String CATAT_BIAYA_EDIT_BIAYA = "CATAT_BIAYA_EDIT_BIAYA";
    public static final String CATAT_BIAYA_EDIT_DATE = "CATAT_BIAYA_EDIT_DATE";
    public static final String CATAT_BIAYA_VOUCHER_NO = "CATAT_BIAYA_VOUCHER_NO";
    public static final String CATAT_BIAYA_VOUCHER_ID = "CATAT_BIAYA_VOUCHER_ID";

    private ActivityCatatBiayaBinding binding;
    private CatatBiayaListAdapter listAdapter;
    private LinearLayoutManager layoutManager;
    private CatatBiayaViewModel viewModel;
    private CatatBiayaListener listener;
    private DatePickerDialog datePicker;

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
        viewModel = new ViewModelProvider(this).get(CatatBiayaViewModel.class);
    }

    private void configureViewModel() {
        viewModel.getHeader().observe(this, new Observer<CatatBiayaHeader>() {
            @Override
            public void onChanged(CatatBiayaHeader header) {
                binding.bankType.setText(viewModel.getBank_name());
                binding.bankAmount.setText(StringHelper.numberFormatWithDecimal(header.getSaldo()));
            }
        });

        viewModel.getBiayaList().observe(this, new Observer<List<CatatBiayaList>>() {
            @Override
            public void onChanged(List<CatatBiayaList> catatBiayaLists) {
                listAdapter.notifyDataSetChanged();
            }
        });

        viewModel.getSubtotal().observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double subtotal) {
                binding.textSubtotal.setText(StringHelper.numberFormatWithDecimal(subtotal));
            }
        });

        viewModel.getVoucher_no().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.voucherNo.setText(s);
            }
        });
    }

    private void setupView() {
        binding = ActivityCatatBiayaBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }

    private void setupDatePicker() {
        Calendar calendar = Calendar.getInstance();
        datePicker = new DatePickerDialog( this,
                CatatBiayaActivity.this,
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

        binding.search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String query = binding.search.getText().toString();
                viewModel.fetchData(query);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.buttonBatalkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.buttonSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = binding.date.getText().toString();
                ArrayList<CatatBiayaList> selectedBiayaList = viewModel.getSelectedBiayaList();

                Intent intent = new Intent(CatatBiayaActivity.this, RincianBiayaActivity.class);
                intent.putExtra(RincianBiayaActivity.BANK_FROM, viewModel.getFrom_bank());
                intent.putExtra(RincianBiayaActivity.BANK_NAME, viewModel.getBank_name());
                intent.putExtra(RincianBiayaActivity.TYPE, viewModel.getType());
                intent.putExtra(RincianBiayaActivity.SELECTED_DATE, date);
                intent.putExtra(RincianBiayaActivity.SELECTED_LISTED_BIAYA, selectedBiayaList);

                Integer voucher_id = viewModel.getVoucher_id();

                if (voucher_id != null) {
                    intent.putExtra(RincianBiayaActivity.EDITED_VOUCHER_ID, voucher_id);
                    intent.putExtra(RincianBiayaActivity.EDITED_VOUCHER_NO, viewModel.getVoucher_no().getValue());
                }

                startActivity(intent);
            }
        });

        binding.date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer voucher_id = viewModel.getVoucher_id();
                Log.d("Voucher ID", String.valueOf(voucher_id));
                if (voucher_id == null) {
                    datePicker.show();
                }
            }
        });

        listener = new CatatBiayaListener() {
            @Override
            public void onItemChanged(CatatBiayaList biaya) {
                viewModel.updateListedBiaya(biaya);
            }

            @Override
            public void onItemUnchecked(CatatBiayaList biaya) {
                viewModel.removeListedBiaya(biaya);
            }
        };
    }

    private void setupList() {
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.list.setLayoutManager(layoutManager);

        listAdapter = new CatatBiayaListAdapter(this, viewModel);
        listAdapter.setListener(listener);
        binding.list.setAdapter(listAdapter);
    }

    private void configureIntent() {
        Intent intent = getIntent();
        int from_bank = intent.getIntExtra(CATAT_BIAYA_BANK_ID, 0);
        String bank_name = intent.getStringExtra(CATAT_BIAYA_BANK_NAME);

        if (intent.hasExtra(CATAT_BIAYA_TYPE)) {
            String type = intent.getStringExtra(CATAT_BIAYA_TYPE);
            if (type != null && type.equals("pemasukan")) {
                binding.title.setText("Penerimaan Uang");
            }
            viewModel.setType(type);
        }

        Log.d("Voucher ID", "Dari Intent " + intent.getExtras());

        if (intent.hasExtra(CATAT_BIAYA_VOUCHER_ID)) {
            ArrayList<CatatBiayaList> biayaLists = (ArrayList<CatatBiayaList>) intent.getSerializableExtra(CATAT_BIAYA_EDIT_BIAYA);
            String date = intent.getStringExtra(CATAT_BIAYA_EDIT_DATE);
            int voucher_id = intent.getIntExtra(CATAT_BIAYA_VOUCHER_ID, 0);
            String voucher_no = intent.getStringExtra(CATAT_BIAYA_VOUCHER_NO);
            viewModel.setSelectedBiayaList(biayaLists);
            viewModel.getVoucher_no().setValue(voucher_no);
            viewModel.setVoucher_id(voucher_id);
            binding.date.setText(date);
            binding.date.setFocusable(false);
            binding.date.setClickable(false);
        }
        viewModel.setFrom_bank(from_bank);
        viewModel.setBank_name(bank_name);
        viewModel.fetchData("");
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        String date = year + "-" + (month+1) + "-" + day;
        binding.date.setText(StringHelper.formatDatePicker(date));
    }
}