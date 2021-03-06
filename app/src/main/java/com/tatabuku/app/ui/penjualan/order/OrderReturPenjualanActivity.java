package com.tatabuku.app.ui.penjualan.order;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.tatabuku.app.R;
import com.tatabuku.app.databinding.ActivityOrderPenjualanBinding;
import com.tatabuku.app.model.pembelian.Category;
import com.tatabuku.app.model.pembelian.OrderModel;
import com.tatabuku.app.model.pembelian.Produk;
import com.tatabuku.app.ui.BaseActivity;
import com.tatabuku.app.ui.pembelian.order.OrderPembelianListener;
import com.tatabuku.app.ui.pembelian.tambah.produk.TambahProdukActivity;
import com.tatabuku.app.ui.penjualan.checkout.CheckoutReturPenjualanActivity;
import com.tatabuku.app.util.StringHelper;

import java.util.ArrayList;
import java.util.List;

public class OrderReturPenjualanActivity extends BaseActivity implements OrderPembelianListener {

    private ActivityOrderPenjualanBinding binding;
    private OrderReturPenjualanListAdapter listAdapter;
    private OrderReturPenjualanCategoryAdapter categoryAdapter;
    private LinearLayoutManager layoutManager, categoryLayoutManager;
    private OrderReturPenjualanViewModel viewModel;

    public static final String ARG_CUSTOMER = "ARG_CUSTOMER";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupViewModel();
        viewModel.getCustomerData().setValue(getIntent().getParcelableExtra(ARG_CUSTOMER));

        setupView();
        setupList();
        setupCategoryList();
        configureView();
        configureViewModel();
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.fetchCategory();
        viewModel.fetchProduk(viewModel.getSelectedCategory().getValue(), binding.search.getText().toString());
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(OrderReturPenjualanViewModel.class);
    }

    private void configureViewModel() {

        viewModel.getSelectedCategory().observe(this, new Observer<Category>() {
            @Override
            public void onChanged(Category category) {
                categoryAdapter.notifyDataSetChanged();
                viewModel.fetchProduk(viewModel.getSelectedCategory().getValue(), binding.search.getText().toString());
            }
        });

        viewModel.getProdukList().observe(this, new Observer<List<Produk>>() {
            @Override
            public void onChanged(List<Produk> produks) {
                listAdapter.notifyDataSetChanged();
            }
        });

        viewModel.getCategories().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                categoryAdapter.notifyDataSetChanged();
            }
        });
    }

    private void setupView() {
        binding = ActivityOrderPenjualanBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.title.setText(R.string.retur_barang);

        binding.address.setText(viewModel.getCustomerData().getValue().getStreet() + " " + viewModel.getCustomerData().getValue().getStreet2());
        binding.name.setText(viewModel.getCustomerData().getValue().getName());
//        binding.phone.setText(viewModel.getCustomerData().getValue().getPhone());
        try {
            Glide.with(this).asBitmap()
                    .load(Base64.decode(viewModel.getCustomerData().getValue().getImage(), Base64.DEFAULT))
                    .into(binding.image);
        } catch (Exception e) {

        }
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
                Intent intent = new Intent(OrderReturPenjualanActivity.this, CheckoutReturPenjualanActivity.class);
                ArrayList<OrderModel> list = new ArrayList<>();
                for (OrderModel order :
                        viewModel.getOrders().getValue()) {
                    if (order.getProduct_qty() != 0) {
                        list.add(order);
                    }
                }
                intent.putParcelableArrayListExtra(CheckoutReturPenjualanActivity.ARG_ORDERS, list);
                intent.putExtra(CheckoutReturPenjualanActivity.ARG_CUSTOMER, viewModel.getCustomerData().getValue());
                if (list.isEmpty()) {
                    showAlert("Perhatian", "Order kosong");
                } else {
                    startActivity(intent);
                }
            }
        });

        binding.search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                viewModel.fetchProduk(viewModel.getSelectedCategory().getValue(), binding.search.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void setupList() {
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.list.setLayoutManager(layoutManager);

        listAdapter = new OrderReturPenjualanListAdapter(this, viewModel);
        listAdapter.setListener(this);
        binding.list.setAdapter(listAdapter);
    }

    private void setupCategoryList() {
        categoryLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        binding.categoryList.setLayoutManager(categoryLayoutManager);

        categoryAdapter = new OrderReturPenjualanCategoryAdapter(this, viewModel);
        categoryAdapter.setListener(this);
        binding.categoryList.setAdapter(categoryAdapter);
    }

    @Override
    public void onOrderChanged() {
        Double subtotal = 0.0;
        for (int i = 0; i < viewModel.getOrders().getValue().size(); i++) {
            OrderModel order = viewModel.getOrders().getValue().get(i);
            subtotal += order.getPrice_unit() * order.getProduct_qty();
        }

        binding.subtotal.setText(StringHelper.numberFormat(subtotal));
    }

    @Override
    public void onEditProduct(int productId) {
    }

    @Override
    public void onSelectCategory(Category category) {
        viewModel.getSelectedCategory().setValue(category);
    }
}