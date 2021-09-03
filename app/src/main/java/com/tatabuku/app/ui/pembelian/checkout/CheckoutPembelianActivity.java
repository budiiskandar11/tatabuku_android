package com.tatabuku.app.ui.pembelian.checkout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.tatabuku.app.R;
import com.tatabuku.app.databinding.ActivityCheckoutPembelianBinding;
import com.tatabuku.app.model.pembelian.OrderDetailResult;
import com.tatabuku.app.model.pembelian.OrderModel;
import com.tatabuku.app.model.pembelian.SupplierResult;
import com.tatabuku.app.model.pembelian.WarehouseAddress;
import com.tatabuku.app.ui.BaseActivity;
import com.tatabuku.app.ui.pembelian.detail.DetailSupplierActivity;
import com.tatabuku.app.ui.pembelian.order.OrderPembelianActivity;
import com.tatabuku.app.ui.pembelian.penerimaan.PenerimaanBarangActivity;
import com.tatabuku.app.util.StringHelper;

import java.util.ArrayList;

public class CheckoutPembelianActivity extends BaseActivity {

    private ActivityCheckoutPembelianBinding binding;
    private CheckoutPembelianListAdapter listAdapter;
    private LinearLayoutManager layoutManager;
    private CheckoutPembelianViewModel viewModel;
    private Integer orderId = 0;

    public static final String ARG_ORDERS = "ARG_ORDERS";
    public static final String ARG_SUPPLIER = "ARG_SUPPLIER";
    public static final String ARG_ORDER_ID = "ARG_ORDER_ID";
    public static final String ARG_ORDER_DETAIL = "ARG_ORDER_DETAIL";
    public static final String ARG_IS_VIEW = "ARG_IS_VIEW";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupViewModel();
        configureViewModel();

        viewModel.getIsView().setValue(getIntent().getBooleanExtra(ARG_IS_VIEW, false));
        viewModel.getSupplierResult().setValue(getIntent().getParcelableExtra(ARG_SUPPLIER));
        OrderDetailResult orderDetailResult = getIntent().getParcelableExtra(ARG_ORDER_DETAIL);
        if (orderDetailResult != null) {
            viewModel.getOrderDetailResult().setValue(orderDetailResult);
        }
        ArrayList<OrderModel> list = getIntent().getParcelableArrayListExtra(ARG_ORDERS);
        int id = getIntent().getIntExtra(ARG_ORDER_ID, 0);
        if (id != 0) {
            orderId = id;
        }
        if (list != null) {
            viewModel.getOrders().setValue(list);
        }

        setupView();
        configureView();
        setupList();

        if (orderId != 0 && list == null) {
            viewModel.fetchOrderData(id);
        }
        viewModel.fetchWarehouseAddress();
        showLoading();
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(CheckoutPembelianViewModel.class);
    }

    private void configureViewModel() {
        viewModel.getIsView().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    binding.addItem.setVisibility(View.INVISIBLE);
                    binding.bottomLayout.setVisibility(View.INVISIBLE);
                    listAdapter.notifyDataSetChanged();
                }
            }
        });

        viewModel.getSupplierResult().observe(this, new Observer<SupplierResult>() {
            @Override
            public void onChanged(SupplierResult supplierResult) {
                binding.supplierName.setText(supplierResult.getName());
                binding.supplierAddress.setText(supplierResult.getStreet() + " " + supplierResult.getStreet2());
                binding.supplierPhone.setText(supplierResult.getPhone());
                try {
                    Glide.with(CheckoutPembelianActivity.this).asBitmap()
                            .load(Base64.decode(supplierResult.getImage(), Base64.DEFAULT))
                            .into(binding.supplierImage);
                } catch (Exception e) {

                }
            }
        });
        viewModel.getOrderDetailResult().observe(this, new Observer<OrderDetailResult>() {
            @Override
            public void onChanged(OrderDetailResult orderDetailResult) {
                binding.date.setText(StringHelper.formatOrderDate(orderDetailResult.getDateOrder()));
                binding.orderNo.setText(String.format(getString(R.string.order_no_n), orderDetailResult.getName()));
                listAdapter.notifyDataSetChanged();
            }
        });

        viewModel.getWarehouseAddress().observe(this, new Observer<WarehouseAddress>() {
            @Override
            public void onChanged(WarehouseAddress warehouseAddress) {
                hideLoading();
                binding.receiveAddress.setText(warehouseAddress.getStreet() + " " + warehouseAddress.getStreet2());
                binding.receivePhone.setText(warehouseAddress.getPhone());
            }
        });

        viewModel.getOrders().observe(this, new Observer<ArrayList<OrderModel>>() {
            @Override
            public void onChanged(ArrayList<OrderModel> orderModels) {
                binding.itemCount.setText(viewModel.getItemCount() + " Unit");
                binding.ppn.setText(viewModel.getPPN());
                binding.total.setText(viewModel.getTotal());
                binding.subtotal.setText(viewModel.getSubtotal());
            }
        });

        viewModel.getOnError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                hideLoading();
                showAlert("Perhatian", s);
            }
        });

        viewModel.getOnSuccess().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer id) {
                hideLoading();
                if  (id == 0) {
                    Intent intent = new Intent(CheckoutPembelianActivity.this, DetailSupplierActivity.class);
                    intent.putExtra(DetailSupplierActivity.ARG_SUPPLIER_ID, viewModel.getSupplierResult().getValue().getId());
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(CheckoutPembelianActivity.this, PenerimaanBarangActivity.class);
                    intent.putExtra(PenerimaanBarangActivity.ARG_ORDER_ID, id);
                    intent.putExtra(PenerimaanBarangActivity.ARG_SUPPLIER, viewModel.getSupplierResult().getValue());
                    startActivity(intent);
                }
            }
        });
    }

    private void setupView() {
        binding = ActivityCheckoutPembelianBinding.inflate(getLayoutInflater());
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

                if (orderId!= 0) {
                    showLoading();
                    viewModel.cancelOrder(orderId);
                }else {
                    Intent intent = new Intent(CheckoutPembelianActivity.this, DetailSupplierActivity.class);
                    intent.putExtra(DetailSupplierActivity.ARG_SUPPLIER_ID, viewModel.getSupplierResult().getValue().getId());
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
        });

        binding.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showLoading();
                viewModel.saveOrder(orderId, false);

            }
        });

//        binding.save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showLoading();
//                viewModel.saveOrder(orderId, true);
//            }
//        });

        binding.addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CheckoutPembelianActivity.this, OrderPembelianActivity.class);
                intent.putExtra(OrderPembelianActivity.ARG_ORDER_ID, orderId);
                intent.putExtra(OrderPembelianActivity.ARG_SUPPLIER, viewModel.getSupplierResult().getValue());
                if (viewModel.getOrderDetailResult().getValue() != null) {
                    intent.putExtra(CheckoutPembelianActivity.ARG_ORDER_DETAIL, viewModel.getOrderDetailResult().getValue());
                }
                ArrayList<OrderModel> list = new ArrayList<>();
                for (OrderModel order :
                        viewModel.getOrders().getValue()) {
                    if (order.getPrice_unit() != 0 && order.getProduct_qty() != 0) {
                        list.add(order);
                    }
                }
                intent.putParcelableArrayListExtra(OrderPembelianActivity.ARG_ORDERS, list);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

    }

    private void setupList() {
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.list.setLayoutManager(layoutManager);

        listAdapter = new CheckoutPembelianListAdapter(viewModel, this);
        binding.list.setAdapter(listAdapter);
    }
}