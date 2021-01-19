package com.tatabuku.app.ui.penjualan.tambah.dp;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.widget.DatePicker;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.tatabuku.app.R;
import com.tatabuku.app.databinding.ActivityTambahDpCustomerBinding;
import com.tatabuku.app.model.pembelian.RekeningBank;
import com.tatabuku.app.model.pembelian.RekeningDataResult;
import com.tatabuku.app.model.penjualan.CustomerDPResult;
import com.tatabuku.app.ui.BaseActivity;
import com.tatabuku.app.util.StringHelper;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TambahDpCustomerActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener {

    private ActivityTambahDpCustomerBinding binding;
    private TambahDpCustomerListAdapter listAdapter;
    private LinearLayoutManager layoutManager;
    private Boolean isRefund = false;
    private TambahDpCustomerViewModel viewModel;
    private Integer currentValue = 0;
    private int customerId = 0;
    private int dpId = 0;
    private Date selectedDate = new Date();
    private DatePickerDialog datePicker;

    public static final String ARG_IS_REFUND = "ARG_IS_REFUND";
    public static final String ARG_CUSTOMER_ID = "ARG_CUSTOMER_ID";
    public static final String ARG_DP_ID = "ARG_DP_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isRefund = getIntent().getBooleanExtra(ARG_IS_REFUND, false);
        customerId = getIntent().getIntExtra(ARG_CUSTOMER_ID, 0);
        dpId = getIntent().getIntExtra(ARG_DP_ID, 0);


        setupViewModel();
        configureViewModel();
        setupView();
        setupDatePicker();
        configureView();

        setupList();

        if (dpId != 0) {
            viewModel.getIsEdit().setValue(false);
        } else {
            viewModel.getIsEdit().setValue(true);
        }

        viewModel.fetchRekeningData(customerId, dpId);
    }

    private void setupDatePicker() {
        Calendar calendar = Calendar.getInstance();
        datePicker = new DatePickerDialog(this,
                this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(TambahDpCustomerViewModel.class);
    }

    private void configureViewModel() {
        viewModel.getDpData().observe(this, new Observer<CustomerDPResult>() {
            @Override
            public void onChanged(CustomerDPResult customerDPResult) {
                binding.noVoucher.setText(customerDPResult.getName());
                binding.value.setText(StringHelper.numberFormat(customerDPResult.getAmountDp()));

                if (customerDPResult.getDpType().equalsIgnoreCase("cus_payment_out")) {
                    isRefund = true;
                    binding.title.setText(R.string.customer_tarik_dp);
                    binding.subtitle.setText(R.string.refund_dp);
                }
            }
        });

        viewModel.getRekeningData().observe(this, new Observer<RekeningDataResult>() {
            @Override
            public void onChanged(RekeningDataResult rekeningDataResult) {
                binding.supplierAddress.setText(rekeningDataResult.getAlamat());
                binding.supplierPhone.setText(rekeningDataResult.getPhone());
                binding.supplierName.setText(rekeningDataResult.getName());
//                binding.saldoDp.setText(StringHelper.numberFormat(rekeningDataResult.getTotalDp()));

                try {
                    Glide.with(TambahDpCustomerActivity.this).asBitmap()
                            .load(Base64.decode(rekeningDataResult.getImage(), Base64.DEFAULT))
                            .into(binding.image);
                } catch (Exception e) {

                }
            }
        });

        viewModel.getRekeningList().observe(this, new Observer<List<RekeningBank>>() {
            @Override
            public void onChanged(List<RekeningBank> rekeningBanks) {
                listAdapter.notifyDataSetChanged();
            }
        });

        viewModel.getOnError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                hideLoading();
                showAlert("Perhatian", s);
            }
        });

        viewModel.getOnSuccess().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                hideLoading();
                finish();
            }
        });

        viewModel.getSelectedBank().observe(this, new Observer<RekeningBank>() {
            @Override
            public void onChanged(RekeningBank rekeningBank) {
                listAdapter.notifyDataSetChanged();
            }
        });

        viewModel.getIsEdit().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    binding.bottomLayout.setVisibility(View.VISIBLE);
                    binding.edit.setVisibility(View.GONE);
                } else {
                    binding.bottomLayout.setVisibility(View.GONE);
                    binding.edit.setVisibility(View.VISIBLE);
                }
                binding.date.setEnabled(aBoolean);
                binding.value.setEnabled(aBoolean);
                listAdapter.notifyDataSetChanged();
            }
        });
    }

    private void setupView() {
        binding = ActivityTambahDpCustomerBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        if (isRefund) {
            binding.title.setText(R.string.customer_tarik_dp);
            binding.subtitle.setText(R.string.refund_dp);
        }

        if (dpId == 0) {
            binding.edit.setVisibility(View.GONE);
        } else {
            binding.bottomLayout.setVisibility(View.GONE);
            binding.edit.setVisibility(View.VISIBLE);
            binding.date.setEnabled(false);
            binding.value.setEnabled(false);
        }
        binding.date.setText(StringHelper.getTodayDate());
    }

    private void configureView() {

        binding.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.getIsEdit().setValue(true);
            }
        });

        binding.date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker.show();
            }
        });

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoading();
                viewModel.addDP(customerId, currentValue, isRefund, selectedDate, dpId);
            }
        });

        binding.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.value.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (!s.toString().equals(currentValue)) {
                    binding.value.removeTextChangedListener(this);
                    String cleanString = s.toString().replaceAll("[Rp,.]", "").replaceAll(" ", "").trim();
                    if (!cleanString.equals("")) {
                        Double parsed = Double.parseDouble(cleanString);
                        String formatted = StringHelper.numberFormat(parsed);

                        currentValue = parsed.intValue();
                        binding.value.setText(formatted);
                        binding.value.setSelection(formatted.length());
                    } else {
                        currentValue = 0;
                        binding.value.setText("");
                    }
                    binding.value.addTextChangedListener(this);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void setupList() {
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.list.setLayoutManager(layoutManager);

        listAdapter = new TambahDpCustomerListAdapter(viewModel, this);
        binding.list.setAdapter(listAdapter);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        String date = year + "-" + (month + 1) + "-" + day;
        selectedDate = StringHelper.getDate(date, "yyyy-MM-dd");
        binding.date.setText(StringHelper.formatDate(selectedDate, "dd/MM/yyyy"));
    }
}