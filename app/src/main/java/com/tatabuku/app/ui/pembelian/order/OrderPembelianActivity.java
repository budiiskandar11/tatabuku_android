package com.tatabuku.app.ui.pembelian.order;

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
import com.tatabuku.app.databinding.ActivityOrderPembelianBinding;
import com.tatabuku.app.model.pembelian.Category;
import com.tatabuku.app.model.pembelian.OrderDetailResult;
import com.tatabuku.app.model.pembelian.OrderModel;
import com.tatabuku.app.model.pembelian.Produk;
import com.tatabuku.app.ui.BaseActivity;
import com.tatabuku.app.ui.pembelian.checkout.CheckoutPembelianActivity;
import com.tatabuku.app.ui.pembelian.tambah.produk.TambahProdukActivity;
import com.tatabuku.app.util.StringHelper;

import java.util.ArrayList;
import java.util.List;

public class OrderPembelianActivity extends BaseActivity implements OrderPembelianListener {

    private ActivityOrderPembelianBinding binding;
    private OrderPembelianListAdapter listAdapter;
    private OrderPembelianCategoryAdapter categoryAdapter;
    private LinearLayoutManager layoutManager, categoryLayoutManager;
    private OrderPembelianViewModel viewModel;
    private int orderId = 0;

    public static final String ARG_SUPPLIER = "ARG_SUPPLIER";
    public static final String ARG_ORDERS = "ARG_ORDERS";
    public static final String ARG_ORDER_ID = "ARG_ORDER_ID";
    public static final String ARG_ORDER_DETAIL = "ARG_ORDER_DETAIL";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupViewModel();
        orderId = getIntent().getIntExtra(ARG_ORDER_ID, 0);
        viewModel.getSupplierResult().setValue(getIntent().getParcelableExtra(ARG_SUPPLIER));
        OrderDetailResult orderDetailResult = getIntent().getParcelableExtra(ARG_ORDER_DETAIL);
        if (orderDetailResult != null) {
            viewModel.getOrderDetailResult().setValue(orderDetailResult);
        }

        setupView();
        setupList();
        setupCategoryList();
        configureView();
        configureViewModel();

        ArrayList<OrderModel> list = getIntent().getParcelableArrayListExtra(ARG_ORDERS);
        if (list != null) {
            viewModel.getOrders().setValue(list);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.fetchCategory();
        viewModel.fetchProduk(viewModel.getSelectedCategory().getValue(), binding.search.getText().toString());
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(OrderPembelianViewModel.class);
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
        binding = ActivityOrderPembelianBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.address.setText(viewModel.getSupplierResult().getValue().getStreet() + " " + viewModel.getSupplierResult().getValue().getStreet2());
        binding.name.setText(viewModel.getSupplierResult().getValue().getName());
        binding.phone.setText(viewModel.getSupplierResult().getValue().getPhone());
        try {
            Glide.with(this).asBitmap()
                    .load(Base64.decode(viewModel.getSupplierResult().getValue().getImage(), Base64.DEFAULT))
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
                Intent intent = new Intent(OrderPembelianActivity.this, CheckoutPembelianActivity.class);
                intent.putExtra(CheckoutPembelianActivity.ARG_ORDER_ID, orderId);
                ArrayList<OrderModel> list = new ArrayList<>();
                for (OrderModel order :
                        viewModel.getOrders().getValue()) {
                    if (order.getPrice_unit() != 0 && order.getProduct_qty() != 0) {
                        list.add(order);
                    }
                }
                intent.putParcelableArrayListExtra(CheckoutPembelianActivity.ARG_ORDERS, list);
                intent.putExtra(CheckoutPembelianActivity.ARG_SUPPLIER, viewModel.getSupplierResult().getValue());
                if (viewModel.getOrderDetailResult().getValue() != null) {
                    intent.putExtra(CheckoutPembelianActivity.ARG_ORDER_DETAIL, viewModel.getOrderDetailResult().getValue());
                }
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                if (list.isEmpty()) {
                    showAlert("Perhatian", "Order kosong");
                } else {
                    startActivity(intent);
                }
            }
        });

        binding.addNewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderPembelianActivity.this, TambahProdukActivity.class);
                startActivity(intent);
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

        listAdapter = new OrderPembelianListAdapter(this, viewModel);
        listAdapter.setListener(this);
        binding.list.setAdapter(listAdapter);
    }

    private void setupCategoryList() {
        categoryLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        binding.categoryList.setLayoutManager(categoryLayoutManager);

        categoryAdapter = new OrderPembelianCategoryAdapter(this, viewModel);
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
        Intent intent = new Intent(this, TambahProdukActivity.class);
        intent.putExtra(TambahProdukActivity.ARG_PRODUCT_ID, productId);
        startActivity(intent);
    }

    @Override
    public void onSelectCategory(Category category) {
        viewModel.getSelectedCategory().setValue(category);
    }
}