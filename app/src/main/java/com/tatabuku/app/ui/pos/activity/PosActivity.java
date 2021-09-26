package com.tatabuku.app.ui.pos.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.tatabuku.app.databinding.ActivityPosBinding;
import com.tatabuku.app.model.pembelian.Category;
import com.tatabuku.app.model.pembelian.OrderModel;
import com.tatabuku.app.model.pembelian.SupplierResult;
import com.tatabuku.app.model.pos.OrderBill;
import com.tatabuku.app.model.pos.OrderLineModel;
import com.tatabuku.app.ui.penjualan.tambah.customer.TambahCustomerActivity;
import com.tatabuku.app.ui.pos.PosViewModel;
import com.tatabuku.app.ui.pos.adapter.PosCategoryAdapter;
import com.tatabuku.app.ui.pos.adapter.PosCostumerAdapter;
import com.tatabuku.app.ui.pos.adapter.PosListAdapter;
import com.tatabuku.app.ui.pos.adapter.PosListItemBillAdapter;
import com.tatabuku.app.ui.pos.adapter.PosSaveOrderAdapter;
import com.tatabuku.app.ui.pos.listener.PosCostumerListener;
import com.tatabuku.app.ui.pos.listener.PosListener;
import com.tatabuku.app.util.StringHelper;

import java.util.ArrayList;
import java.util.Objects;

public class PosActivity extends AppCompatActivity implements PosListener, PosCostumerListener {

    private ActivityPosBinding binding;
    private PosListAdapter listAdapter;
    private PosCostumerAdapter costumerAdapter;
    private PosCategoryAdapter categoryAdapter;
    private PosSaveOrderAdapter saveOrderAdapter;
    private PosListItemBillAdapter posListItemBillAdapter;
    private PosViewModel viewModel;
    private BottomSheetBehavior bottomSheetBehavior;
    private ArrayList<OrderBill> list = new ArrayList<>();
    private ArrayList<OrderLineModel> order_line = new ArrayList<>();

    private Double subtotal = 0.0;
    private Integer qty = 0;
    private SupplierResult patnerId;

    public enum DashboardType {
        PEMBELIAN
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupViewModel();

        setupView();
        setupList();
        setupCategoryList();
        setupItemBillList();
        setupCostumerList();
        setupSaveOrderList();
        configureView();
        configureViewModel();

    }

    private void setupSaveOrderList() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.layoutSave.list.setLayoutManager(layoutManager);

        saveOrderAdapter = new PosSaveOrderAdapter(this, viewModel);
        binding.layoutSave.list.setAdapter(saveOrderAdapter);

    }

    private void setupCostumerList() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.layoutCustomer.list.setLayoutManager(layoutManager);

        costumerAdapter = new PosCostumerAdapter(this, viewModel);
        costumerAdapter.setListener(this);
        binding.layoutCustomer.list.setAdapter(costumerAdapter);
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(PosViewModel.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.fetchCategory();
        viewModel.fetchProduk(viewModel.getSelectedCategory().getValue(), Objects.requireNonNull(binding.search.getText()).toString());
        viewModel.fetchSupplierList(DashboardType.PEMBELIAN, "");
    }

    @SuppressLint("NotifyDataSetChanged")
    private void configureViewModel() {

        viewModel.getSelectedCategory().observe(this, category -> {
            categoryAdapter.notifyDataSetChanged();
            viewModel.fetchProduk(viewModel.getSelectedCategory().getValue(), Objects.requireNonNull(binding.search.getText()).toString());
        });

        viewModel.getProdukList().observe(this, produks -> listAdapter.notifyDataSetChanged());

        viewModel.getProdukList().observe(this, produks -> posListItemBillAdapter.notifyDataSetChanged());

        viewModel.getCategories().observe(this, categories -> categoryAdapter.notifyDataSetChanged());

        viewModel.getSupplierList().observe(this, supplierResults -> listAdapter.notifyDataSetChanged());

        viewModel.getOrderBill().observe(this, addOrderBills -> saveOrderAdapter.notifyDataSetChanged());

    }

    private void setupCategoryList() {
        LinearLayoutManager categoryLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        binding.categoryList.setLayoutManager(categoryLayoutManager);

        categoryAdapter = new PosCategoryAdapter(this, viewModel);
        categoryAdapter.setListener(this);
        binding.categoryList.setAdapter(categoryAdapter);
    }

    private void setupList() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        binding.list.setLayoutManager(gridLayoutManager);

        listAdapter = new PosListAdapter(this, viewModel);
        listAdapter.setListener(this);
        binding.list.setAdapter(listAdapter);
    }

    private void setupItemBillList() {
        LinearLayoutManager listItemBillManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.behafior.listOrder.setLayoutManager(listItemBillManager);

        posListItemBillAdapter = new PosListItemBillAdapter(this);
        posListItemBillAdapter.setList(new ArrayList<>());
        posListItemBillAdapter.setListener(this);
        binding.behafior.listOrder.setAdapter(posListItemBillAdapter);
    }

    private void setupView() {
        binding = ActivityPosBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }


    @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
    private void configureView() {

        binding.back.setOnClickListener(v -> finish());

        binding.search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                viewModel.fetchProduk(viewModel.getSelectedCategory().getValue(), Objects.requireNonNull(binding.search.getText()).toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        bottomSheetBehavior = BottomSheetBehavior.from(binding.behafior.bottomSheet);
        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        binding.tvTotal.setText(StringHelper.numberFormat(subtotal));
        binding.tvQty.setText(qty + "");

        binding.delete.setOnClickListener(v -> {
            if (qty > 0) {
                new AlertDialog.Builder(this)
                        .setTitle("Hapus Orderan")
                        .setMessage("Apakah anda yakin menghapus orderan?")
                        .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                            for (int i = 0; i < Objects.requireNonNull(viewModel.getOrders().getValue()).size(); i++) {
                                OrderModel order = viewModel.getOrders().getValue().get(i);
                                order.setProduct_qty(0);
                                order.setSelected(0);
                                viewModel.updateOrder(order);
                            }
                            list.clear();
                            onOrderChanged();
                            listAdapter.notifyDataSetChanged();
                        })
                        .setNegativeButton(android.R.string.cancel, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            } else {
                new AlertDialog.Builder(this)
                        .setTitle("Hapus Orderan")
                        .setMessage("Belum ada data Order")
                        .setNegativeButton(android.R.string.ok, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }

        });

        binding.save.setOnClickListener(v -> {
            if (patnerId == null) {
                patnerId = new SupplierResult();
                patnerId.setName("notCostumer");
            }
            viewModel.setOrderBill(list, patnerId, subtotal);
            for (int i = 0; i < Objects.requireNonNull(viewModel.getOrders().getValue()).size(); i++) {
                OrderModel order = viewModel.getOrders().getValue().get(i);
                order.setProduct_qty(0);
                order.setSelected(0);
                viewModel.updateOrder(order);
            }
            list.clear();
            onOrderChanged();
            listAdapter.notifyDataSetChanged();
            if (viewModel.getOrderBill().getValue().size() > 0) {
                new AlertDialog.Builder(this)
                        .setTitle("Save Order")
                        .setMessage("Berhasil Simpan Order")
                        .setNegativeButton(android.R.string.ok, null)
                        .show();
            } else {
                new AlertDialog.Builder(this)
                        .setTitle("Save Order")
                        .setMessage("Gagal Simpan Order")
                        .setNegativeButton(android.R.string.ok, null)
                        .show();
            }
        });

        binding.customer.setOnClickListener(v -> {
            binding.llCos.setVisibility(View.VISIBLE);
            binding.back.setVisibility(View.GONE);
        });

        binding.layoutCustomer.back.setOnClickListener(v -> {
            binding.llCos.setVisibility(View.GONE);
            binding.back.setVisibility(View.VISIBLE);
        });

        binding.layoutCustomer.btnAdd.setOnClickListener(v -> startActivity(new Intent(this, TambahCustomerActivity.class)));

        binding.daftar.setOnClickListener(v -> {
            if (viewModel.getOrderBill().getValue() != null) {
                binding.llSave.setVisibility(View.VISIBLE);
                binding.back.setVisibility(View.GONE);
            } else {
                new AlertDialog.Builder(this)
                        .setTitle("Daftar Order")
                        .setMessage("Tidak ada orderan yang tersimpan")
                        .setNegativeButton(android.R.string.ok, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });


        binding.layoutSave.back.setOnClickListener(v -> {
            binding.llSave.setVisibility(View.GONE);
            binding.back.setVisibility(View.VISIBLE);
        });

        binding.llOrder.setOnClickListener(v -> {
            if (subtotal > 0) {
                if (patnerId == null) {
                    new AlertDialog.Builder(this)
                            .setTitle("Pembayaran")
                            .setMessage("Bayar Sekarang?")
                            .setCancelable(false)
                            .setPositiveButton("Sekarang", (dialog, which) -> {
                                AlertDialog pdialog =  new AlertDialog.Builder(this)
                                        .setTitle("Sedang Memproses")
                                        .setMessage("Mohon Tunggu...")
                                        .setCancelable(false)
                                        .show();
                                viewModel.createSalePos(list, this, false, pdialog);
                            })
                            .setNegativeButton("Nanti", (dialog, which) -> {
                                binding.llCos.setVisibility(View.VISIBLE);
                                binding.back.setVisibility(View.GONE);
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                } else {
                    AlertDialog pdialog =  new AlertDialog.Builder(this)
                            .setTitle("Sedang Memproses")
                            .setMessage("Mohon Tunggu...")
                            .setCancelable(false)
                            .show();
                    viewModel.createSalePos(list, this, true, pdialog);
                }
            } else {
                new AlertDialog.Builder(this)
                        .setTitle("Pembayaran")
                        .setMessage("Silahkan memilih item terlebih dahulu")
                        .setNegativeButton(android.R.string.ok, null)
                        .setCancelable(false)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }

        });


    }

    @Override
    public void onBackPressed() {
    }

    @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
    @Override
    public void onOrderChanged() {
        subtotal = 0.0;
        qty = 0;
        list = new ArrayList<>();
        for (int i = 0; i < Objects.requireNonNull(viewModel.getOrders().getValue()).size(); i++) {
            OrderModel order = viewModel.getOrders().getValue().get(i);
            subtotal += order.getPrice_unit() * order.getProduct_qty();
            qty += order.getProduct_qty();
            if (order.getSelected() == 1) {
                list.add(new OrderBill(
                        order.getProduct_id(),
                        order.getName(),
                        order.getImage(),
                        order.getProduct_qty(),
                        order.getPrice_unit() * order.getProduct_qty())
                );
            }
        }

        posListItemBillAdapter.setList(list);

        binding.behafior.lblSubtotal.setText(StringHelper.numberFormat(subtotal));
        binding.tvTotal.setText(StringHelper.numberFormat(subtotal));
        binding.tvQty.setText(qty + "");
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onEditOrder(OrderBill orderBill) {
        subtotal = 0.0;
        qty = 0;
        for (int i = 0; i < Objects.requireNonNull(viewModel.getOrders().getValue()).size(); i++) {
            OrderModel order = viewModel.getOrders().getValue().get(i);
            if (order.getProduct_id().equals(orderBill.getId())){
                order.setProduct_qty(order.getProduct_qty() - 1);
                viewModel.updateOrder(order);
            }
            subtotal += order.getPrice_unit() * order.getProduct_qty();
            qty += order.getProduct_qty();
            if (order.getProduct_qty() < 1){
                order.setSelected(0);
            }
        }
        listAdapter.notifyDataSetChanged();

    }

    @Override
    public void onEditProduct(int productId) {

    }

    @Override
    public void onSelectCategory(Category category) {
        viewModel.getSelectedCategory().setValue(category);
    }

    @Override
    public void onPaymentResultCostumer(String message) {
        if (message.equals("Success")) {
            finish();
        } else {
            Toast.makeText(this, "Terjadi Kesalahan Pembayaran response :" + message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPaymentResultNotCostumer(String message, Integer id) {
        if (message.equals("Success")) {
            Intent intent = new Intent(this, PembayaranPosActivity.class);
            intent.putExtra(PembayaranPosActivity.ARG_ORDER_ID, id);
            intent.putExtra(PembayaranPosActivity.ARG_IS_EDIT, false);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Terjadi Kesalahan Pembayaran response :" + message, Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onCostumerSelect(SupplierResult supplier) {
        if (supplier != null) {
            binding.name.setText(supplier.getName());
            try {
                Glide.with(getApplicationContext()).asBitmap()
                        .load(Base64.decode(supplier.getImage(), Base64.DEFAULT))
                        .into(binding.image);
            } catch (Exception ignored) {

            }
            patnerId = supplier;
        }
        viewModel.setCostumerSelected(supplier);
        binding.back.setVisibility(View.VISIBLE);
        binding.llCos.setVisibility(View.GONE);
    }
}