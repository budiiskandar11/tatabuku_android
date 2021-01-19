package com.tatabuku.app.ui.pembelian.penerimaan;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.tatabuku.app.R;
import com.tatabuku.app.databinding.ActivityPenerimaanBarangBinding;
import com.tatabuku.app.model.pembelian.PenerimaanBarangItem;
import com.tatabuku.app.model.pembelian.PenerimaanBarangResult;
import com.tatabuku.app.model.pembelian.SupplierResult;
import com.tatabuku.app.ui.BaseActivity;
import com.tatabuku.app.ui.pembelian.detail.DetailSupplierActivity;
import com.tatabuku.app.util.StringHelper;

import java.util.List;

public class PenerimaanBarangActivity extends BaseActivity {

    public static final String ARG_SUPPLIER = "ARG_SUPPLIER";
    public static final String ARG_ORDER_ID = "ARG_ORDER_ID";

    private ActivityPenerimaanBarangBinding binding;
    private PenerimaanBarangViewModel viewModel;
    private PenerimaanBarangListAdapter listAdapter;
    private LinearLayoutManager layoutManager;
    private Integer id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupView();
        setupViewModel();
        configureViewModel();

        viewModel.getSupplierResult().setValue(getIntent().getParcelableExtra(ARG_SUPPLIER));
        id = getIntent().getIntExtra(ARG_ORDER_ID, 0);

        configureView();
        setupList();
        viewModel.fetchData(id);
    }

    @Override
    public void onBackPressed() {
        backToDetail();
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(PenerimaanBarangViewModel.class);
    }

    private void configureViewModel() {
        viewModel.getSupplierResult().observe(this, new Observer<SupplierResult>() {
            @Override
            public void onChanged(SupplierResult supplierResult) {
                binding.supplierName.setText(supplierResult.getName());
                binding.supplierAddress.setText(supplierResult.getStreet() + " " + supplierResult.getStreet2());
                binding.supplierPhone.setText(supplierResult.getPhone());
                try {
                    Glide.with(PenerimaanBarangActivity.this).asBitmap()
                            .load(Base64.decode(supplierResult.getImage(), Base64.DEFAULT))
                            .into(binding.supplierImage);
                } catch (Exception e) {

                }
            }
        });

        viewModel.getReceivedResult().observe(this, new Observer<PenerimaanBarangResult>() {
            @Override
            public void onChanged(PenerimaanBarangResult penerimaanBarangResult) {
                binding.orderNo.setText(String.format(getString(R.string.order_no_n), penerimaanBarangResult.getPartnerData().getOrderNo()));
                binding.date.setText(StringHelper.formatReceivedDate(penerimaanBarangResult.getPartnerData().getDate()));
                listAdapter.notifyDataSetChanged();
            }
        });

        viewModel.getListItem().observe(this, new Observer<List<PenerimaanBarangItem>>() {
            @Override
            public void onChanged(List<PenerimaanBarangItem> penerimaanBarangDetails) {
                binding.diterima.setText(viewModel.getItemCount() + " Unit");
                binding.sisa.setText(viewModel.getDiffCount() + " Unit");
                binding.subtotal.setText(StringHelper.numberFormat(viewModel.calculateSubtotal()));
                binding.ppn.setText(StringHelper.numberFormat(viewModel.calculatePPN()));
                binding.total.setText(StringHelper.numberFormat(viewModel.calculateGrandTotal()));
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
                backToDetail();
            }
        });
    }

    private void setupView() {
        binding = ActivityPenerimaanBarangBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.orderNo.setText(String.format(getString(R.string.order_no_n), "-"));
        binding.date.setText("");
    }

    private void backToDetail() {
        Intent intent = new Intent(this, DetailSupplierActivity.class);
        intent.putExtra(DetailSupplierActivity.ARG_SUPPLIER_ID, viewModel.getSupplierResult().getValue().getId());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void configureView() {

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToDetail();
            }
        });

        binding.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (id != 0) {
                    showLoading();
                    viewModel.cancelOrder(id);
                } else {
                    backToDetail();
                }
            }
        });

        binding.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewModel.getItemCount() > 0) {
                    showLoading();
                    viewModel.submitReceived();
                }
            }
        });

    }

    private void setupList() {
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.list.setLayoutManager(layoutManager);

        listAdapter = new PenerimaanBarangListAdapter(viewModel, this);
        binding.list.setAdapter(listAdapter);
    }
}