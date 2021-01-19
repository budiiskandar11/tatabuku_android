package com.tatabuku.app.ui.penjualan.checkout;

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
import com.tatabuku.app.databinding.ActivityCheckoutReturBinding;
import com.tatabuku.app.databinding.ActivityCheckoutReturPenjualanBinding;
import com.tatabuku.app.model.pembelian.OrderModel;
import com.tatabuku.app.model.pembelian.SupplierResult;
import com.tatabuku.app.model.penjualan.CustomerDetailTotalData;
import com.tatabuku.app.ui.BaseActivity;
import com.tatabuku.app.ui.pembelian.checkout.CheckoutReturActivity;
import com.tatabuku.app.ui.pembelian.checkout.CheckoutReturListAdapter;
import com.tatabuku.app.ui.pembelian.checkout.CheckoutReturViewModel;
import com.tatabuku.app.ui.pembelian.detail.DetailSupplierActivity;
import com.tatabuku.app.ui.penjualan.detail.DetailCustomerActivity;
import com.tatabuku.app.util.StringHelper;

import java.util.ArrayList;

public class CheckoutReturPenjualanActivity extends BaseActivity {

    private ActivityCheckoutReturPenjualanBinding binding;
    private CheckoutReturPenjualanListAdapter listAdapter;
    private LinearLayoutManager layoutManager;
    private CheckoutReturPenjualanViewModel viewModel;

    public static final String ARG_ORDERS = "ARG_ORDERS";
    public static final String ARG_CUSTOMER = "ARG_CUSTOMER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupViewModel();
        configureViewModel();
        viewModel.getCustomerData().setValue(getIntent().getParcelableExtra(ARG_CUSTOMER));

        ArrayList<OrderModel> list = getIntent().getParcelableArrayListExtra(ARG_ORDERS);
        if (list != null) {
            viewModel.getOrders().setValue(list);
        }

        setupView();
        configureView();
        setupList();
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(CheckoutReturPenjualanViewModel.class);
    }

    private void configureViewModel() {
        viewModel.getCustomerData().observe(this, new Observer<CustomerDetailTotalData>() {
            @Override
            public void onChanged(CustomerDetailTotalData customerDetailTotalData) {
                binding.supplierName.setText(customerDetailTotalData.getName());
                binding.supplierAddress.setText(customerDetailTotalData.getStreet() + " " + customerDetailTotalData.getStreet2());
//                binding.supplierPhone.setText(customerDetailTotalData.getPhone());
                try {
                    Glide.with(CheckoutReturPenjualanActivity.this).asBitmap()
                            .load(Base64.decode(customerDetailTotalData.getImage(), Base64.DEFAULT))
                            .into(binding.supplierImage);
                } catch (Exception e) {

                }
            }
        });

        viewModel.getOrders().observe(this, new Observer<ArrayList<OrderModel>>() {
            @Override
            public void onChanged(ArrayList<OrderModel> orderModels) {
                binding.itemCount.setText(viewModel.getItemCount() + " Unit");
                binding.total.setText(viewModel.getSubtotal());
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
                Intent intent = new Intent(CheckoutReturPenjualanActivity.this, DetailCustomerActivity.class);
                intent.putExtra(DetailCustomerActivity.ARG_CUSTOMER_ID, viewModel.getCustomerData().getValue().getId());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void setupView() {
        binding = ActivityCheckoutReturPenjualanBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.orderNo.setText(String.format(getString(R.string.order_no_n), "-"));
        binding.date.setText(String.format(getString(R.string.tanggal_n), StringHelper.getTodayDate()));
    }

    private void configureView() {

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        binding.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoading();
                viewModel.saveRetur();
            }
        });

        binding.addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void setupList() {
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.list.setLayoutManager(layoutManager);

        listAdapter = new CheckoutReturPenjualanListAdapter(viewModel, this);
        binding.list.setAdapter(listAdapter);
    }
}