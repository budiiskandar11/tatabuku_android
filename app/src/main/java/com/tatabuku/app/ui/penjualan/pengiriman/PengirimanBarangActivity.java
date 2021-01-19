package com.tatabuku.app.ui.penjualan.pengiriman;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;

import com.bumptech.glide.Glide;
import com.tatabuku.app.R;
import com.tatabuku.app.databinding.ActivityPenerimaanBarangBinding;
import com.tatabuku.app.databinding.ActivityPengirimanBarangBinding;
import com.tatabuku.app.model.pembelian.PenerimaanBarangItem;
import com.tatabuku.app.model.pembelian.PenerimaanBarangResult;
import com.tatabuku.app.model.pembelian.SupplierResult;
import com.tatabuku.app.model.penjualan.CustomerDetailTotalData;
import com.tatabuku.app.model.penjualan.PengirimanBarangItem;
import com.tatabuku.app.model.penjualan.PengirimanBarangResult;
import com.tatabuku.app.ui.BaseActivity;
import com.tatabuku.app.ui.pembelian.detail.DetailSupplierActivity;
import com.tatabuku.app.ui.pembelian.penerimaan.PenerimaanBarangActivity;
import com.tatabuku.app.ui.pembelian.penerimaan.PenerimaanBarangListAdapter;
import com.tatabuku.app.ui.pembelian.penerimaan.PenerimaanBarangViewModel;
import com.tatabuku.app.ui.penjualan.detail.DetailCustomerActivity;
import com.tatabuku.app.util.StringHelper;

import java.util.List;

public class PengirimanBarangActivity extends BaseActivity {

    public static final String ARG_CUSTOMER = "ARG_CUSTOMER";
    public static final String ARG_ORDER_ID = "ARG_ORDER_ID";

    private ActivityPengirimanBarangBinding binding;
    private PengirimanBarangViewModel viewModel;
    private PengirimanBarangListAdapter listAdapter;
    private LinearLayoutManager layoutManager;
    private Integer id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupView();
        setupViewModel();
        configureViewModel();

        viewModel.getCustomerData().setValue(getIntent().getParcelableExtra(ARG_CUSTOMER));
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
        viewModel = new ViewModelProvider(this).get(PengirimanBarangViewModel.class);
    }

    private void configureViewModel() {
        viewModel.getCustomerData().observe(this, new Observer<CustomerDetailTotalData>() {
            @Override
            public void onChanged(CustomerDetailTotalData customerDetailTotalData) {
                binding.supplierName.setText(customerDetailTotalData.getName());
                binding.supplierAddress.setText(customerDetailTotalData.getStreet() + " " + customerDetailTotalData.getStreet2());
//                binding.supplierPhone.setText(customerDetailTotalData.getPhone());
                try {
                    Glide.with(PengirimanBarangActivity.this).asBitmap()
                            .load(Base64.decode(customerDetailTotalData.getImage(), Base64.DEFAULT))
                            .into(binding.supplierImage);
                } catch (Exception e) {

                }
            }
        });

        viewModel.getShippingResult().observe(this, new Observer<PengirimanBarangResult>() {
            @Override
            public void onChanged(PengirimanBarangResult pengirimanBarangResult) {
                binding.orderNo.setText(String.format(getString(R.string.order_no_n), pengirimanBarangResult.getPartnerData().getOrderNo()));
                binding.date.setText(StringHelper.formatReceivedDate(pengirimanBarangResult.getPartnerData().getDate()));
                listAdapter.notifyDataSetChanged();
            }
        });

        viewModel.getListItem().observe(this, new Observer<List<PengirimanBarangItem>>() {
            @Override
            public void onChanged(List<PengirimanBarangItem> pengirimanBarangItems) {
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
        binding = ActivityPengirimanBarangBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.orderNo.setText(String.format(getString(R.string.order_no_n), "-"));
        binding.date.setText("");
    }

    private void backToDetail() {
        Intent intent = new Intent(this, DetailCustomerActivity.class);
        intent.putExtra(DetailCustomerActivity.ARG_CUSTOMER_ID, viewModel.getCustomerData().getValue().getId());
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
                    viewModel.submitShipping();
                } else {
                    viewModel.getOnError().setValue("Tidak ada barang yg dikirim");
                }
            }
        });

    }

    private void setupList() {
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.list.setLayoutManager(layoutManager);

        listAdapter = new PengirimanBarangListAdapter(viewModel, this);
        binding.list.setAdapter(listAdapter);
    }
}