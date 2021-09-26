package com.tatabuku.app.ui.fix_asset.jual_asset;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;

import com.bumptech.glide.Glide;
import com.tatabuku.app.databinding.ActivityJualAssetBinding;
import com.tatabuku.app.model.fixed_asset.DetailAssetHeader;
import com.tatabuku.app.model.fixed_asset.GetCustomerModel;
import com.tatabuku.app.ui.BaseActivity;
import com.tatabuku.app.ui.penjualan.tambah.customer.TambahCustomerActivity;
import com.tatabuku.app.util.StringHelper;

import java.util.Calendar;
import java.util.List;

public class JualAssetActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener {
    public final static String JUAL_ASSET_HEADER_INFO = "JUAL_ASSET_HEADER_INFO";

    private ActivityJualAssetBinding binding;
    private DatePickerDialog datePicker;
    private JualAssetViewModel viewModel;
    private String currentNominal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupViewModel();
        configureViewModel();
        setupView();
        setupDatePicker();
        configureView();
        configureIntent();
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(JualAssetViewModel.class);
    }

    private void configureViewModel() {
        viewModel.getCustomers().observe(this, new Observer<List<GetCustomerModel>>() {
            @Override
            public void onChanged(List<GetCustomerModel> getCustomerModels) {
                openContextMenu(binding.searchCustomer);
            }
        });

        viewModel.getSelectedCustomer().observe(this, new Observer<GetCustomerModel>() {
            @Override
            public void onChanged(GetCustomerModel customer) {
                binding.searchCustomer.setText(customer.getName());
            }
        });

        viewModel.getHeaderInfo().observe(this, new Observer<DetailAssetHeader>() {
            @Override
            public void onChanged(DetailAssetHeader detailAssetHeader) {
                binding.namaAsset.setText(detailAssetHeader.getAssetName());
                binding.tahunAsset.setText(detailAssetHeader.getTahun());
                binding.nilaiPerolehanAsset.setText(StringHelper.numberFormat(detailAssetHeader.getNilaiPerolehan()));
                binding.depresiasiAsset.setText(StringHelper.numberFormat(detailAssetHeader.getDepresiasi()));
                binding.nilaiBukuAsset.setText(StringHelper.numberFormat(detailAssetHeader.getNilaiBuku()));
                binding.kodeAsset.setText(detailAssetHeader.getNumber());

                try {
                    Glide.with(JualAssetActivity.this).asBitmap()
                            .load(Base64.decode(detailAssetHeader.getImage(), Base64.DEFAULT))
                            .into(binding.fotoAsset);
                } catch (Exception e) {

                }
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
        binding = ActivityJualAssetBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }

    private void setupDatePicker() {
        Calendar calendar = Calendar.getInstance();
        datePicker = new DatePickerDialog( this,
                JualAssetActivity.this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
    }

    private void configureView(){
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        registerForContextMenu(binding.searchCustomer);
        binding.searchCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.fetchCustomer();
            }
        });

        binding.hargaJualAsset.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (!s.toString().equals(currentNominal)) {
                    binding.hargaJualAsset.removeTextChangedListener(this);
                    String cleanString = s.toString().replaceAll("[Rp,.]", "").replaceAll(" ", "").trim();
                    if (!cleanString.equals("")) {
                        double parsed = Double.parseDouble(cleanString);
                        String formatted = StringHelper.numberFormat(parsed);

                        currentNominal = formatted;
                        binding.hargaJualAsset.setText(formatted);
                        binding.hargaJualAsset.setSelection(formatted.length());
                    } else {
                        currentNominal = "";
                        binding.hargaJualAsset.setText("");
                    }
                    binding.hargaJualAsset.addTextChangedListener(this);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                double total = 0;
                if (!binding.hargaJualAsset.getText().toString().equals("")) {
                    total = Double.valueOf(binding.hargaJualAsset.getText().toString().replaceAll("[Rp,.]","").trim());
                }

                binding.nilaiTotalAsset.setText(StringHelper.numberFormatWithDecimal(total));
            }
        });

        binding.tanggalJualAset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker.show();
            }
        });

        binding.buttonAddCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(JualAssetActivity.this, TambahCustomerActivity.class);
                startActivity(intent);
            }
        });

        binding.batalkanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.jualBarangButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = StringHelper.formatBackendDate(binding.tanggalJualAset.getText().toString());
                String customer_name = binding.searchCustomer.getText().toString();
                String price = binding.hargaJualAsset.getText().toString().replaceAll("[Rp,.]", "").trim();

                showLoading();
                viewModel.sellAsset(date, customer_name, price);
            }
        });
    }

    private void configureIntent() {
        Intent intent = getIntent();
        if (intent.hasExtra(JUAL_ASSET_HEADER_INFO) && intent.getSerializableExtra(JUAL_ASSET_HEADER_INFO) instanceof DetailAssetHeader) {
            DetailAssetHeader headerInfo = (DetailAssetHeader) intent.getSerializableExtra(JUAL_ASSET_HEADER_INFO);
            viewModel.getHeaderInfo().setValue(headerInfo);
        }
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        String date = year + "-" + (month+1) + "-" + day;
        binding.tanggalJualAset.setText(StringHelper.formatDatePicker(date));
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v == binding.searchCustomer) {
            menu.setHeaderTitle("Customers");
            for (int i = 0; i < viewModel.getCustomers().getValue().size(); i++) {
                menu.add(0, i, 0, viewModel.getCustomers().getValue().get(i).getName());
            }
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getGroupId() == 0) {
            viewModel.getSelectedCustomer().setValue(viewModel.getCustomers().getValue().get(item.getItemId()));
        }
        return super.onContextItemSelected(item);
    }

}