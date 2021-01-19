package com.tatabuku.app.ui.pembelian.pembayaran;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Base64;
import android.view.View;

import com.bumptech.glide.Glide;
import com.tatabuku.app.R;
import com.tatabuku.app.databinding.ActivityPembayaranBinding;
import com.tatabuku.app.databinding.PopupOrderListBinding;
import com.tatabuku.app.model.pembelian.InvoiceLineDetail;
import com.tatabuku.app.model.pembelian.PartnerData;
import com.tatabuku.app.model.pembelian.PaymentDataResult;
import com.tatabuku.app.model.pembelian.PaymentDetailDataResult;
import com.tatabuku.app.model.pembelian.RekeningBank;
import com.tatabuku.app.model.pembelian.SupplierResult;
import com.tatabuku.app.ui.BaseActivity;
import com.tatabuku.app.util.StringHelper;

import java.util.List;

public class PembayaranActivity extends BaseActivity implements PembayaranListener {

    private ActivityPembayaranBinding binding;
    private PembayaranListAdapter listAdapter;
    private LinearLayoutManager layoutManager;
    private PembayaranViewModel viewModel;
    private Integer orderId;


    public static final String ARG_ORDER_ID = "ARG_ORDER_ID";
    public static final String ARG_IS_EDIT = "ARG_IS_EDIT";
    public static final String ARG_IS_VIEW = "ARG_IS_VIEW";
    public static final String ARG_IS_FROM_PAYMENT = "ARG_IS_FROM_PAYMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupViewModel();
        viewModel.getIsEdit().setValue(getIntent().getBooleanExtra(ARG_IS_EDIT, false));
        viewModel.getIsView().setValue(getIntent().getBooleanExtra(ARG_IS_VIEW, false));
        viewModel.getIsFromPayment().setValue(getIntent().getBooleanExtra(ARG_IS_FROM_PAYMENT, false));
        orderId = getIntent().getIntExtra(ARG_ORDER_ID, 0);

        setupView();
        setupList();
        configureView();
        configureViewModel();
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(PembayaranViewModel.class);
    }

    private void configureViewModel() {

        viewModel.getIsEdit().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                hideLoading();
                if (aBoolean) {
                    binding.bayar.setVisibility(View.GONE);
                    binding.edit.setVisibility(View.VISIBLE);
                } else {
                    binding.bayar.setVisibility(View.VISIBLE);
                    binding.edit.setVisibility(View.GONE);
                }
                listAdapter.notifyDataSetChanged();
                viewModel.fetchData(orderId);
                showLoading();
            }
        });

        viewModel.getIsView().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    binding.bayar.setVisibility(View.GONE);
                    binding.edit.setVisibility(View.GONE);
                }
            }
        });

        viewModel.getSupplierResult().observe(this, new Observer<PartnerData>() {
            @Override
            public void onChanged(PartnerData supplierResult) {
//                if (supplierResult.getAlamat() == null) {
//                    binding.address.setText(viewModel.getSupplierResult().getValue().getStreet());
//                } else {
//                    binding.address.setText(viewModel.getSupplierResult().getValue().getAlamat());
//                }
                if (supplierResult.getPartnerId() == null) {
                    binding.name.setText(viewModel.getSupplierResult().getValue().getName());
                } else {
                    binding.name.setText(viewModel.getSupplierResult().getValue().getPartnerId());
                }
//                binding.phone.setText(viewModel.getSupplierResult().getValue().getPhone());
            }
        });

        viewModel.getPaymentData().observe(this, new Observer<PaymentDataResult>() {
            @Override
            public void onChanged(PaymentDataResult paymentDataResult) {
                hideLoading();

                binding.orderNo.setText(String.format(getString(R.string.inv_no_n), paymentDataResult.getInvoiceData().getNumber()));
                binding.orderNo.setText(String.format(getString(R.string.tanggal_n), StringHelper.formatInvoiceDate(paymentDataResult.getInvoiceData().getDate())));
                binding.totalPembelian.setText(StringHelper.numberFormatWithDecimal(paymentDataResult.getInvoiceData().getTotal()));
                binding.totalPayment.setText(StringHelper.numberFormatWithDecimal(paymentDataResult.getInvoiceData().getAmountAlreadyPaid()));
                binding.sisa.setText(StringHelper.numberFormatWithDecimal(paymentDataResult.getInvoiceData().getAmountToPaid()));
                listAdapter.notifyDataSetChanged();
            }
        });

        viewModel.getPaymentDetailData().observe(this, new Observer<PaymentDetailDataResult>() {
            @Override
            public void onChanged(PaymentDetailDataResult paymentDataResult) {
                hideLoading();
                if (paymentDataResult.getInvoiceData().size() > 0) {

                    binding.orderNo.setText(String.format(getString(R.string.inv_no_n), paymentDataResult.getInvoiceData().get(0).getNumber()));
                    binding.orderNo.setText(String.format(getString(R.string.tanggal_n), StringHelper.formatInvoiceDate(paymentDataResult.getInvoiceData().get(0).getDate())));
                    binding.totalPembelian.setText(StringHelper.numberFormatWithDecimal(paymentDataResult.getInvoiceData().get(0).getTotal()));
                    binding.totalPayment.setText(StringHelper.numberFormatWithDecimal(paymentDataResult.getInvoiceData().get(0).getAmountAlreadyPaid()));
                    binding.sisa.setText(StringHelper.numberFormatWithDecimal(paymentDataResult.getInvoiceData().get(0).getAmountToPaid()));
                }
                listAdapter.notifyDataSetChanged();
            }
        });
        viewModel.getItemList().observe(this, new Observer<List<InvoiceLineDetail>>() {
            @Override
            public void onChanged(List<InvoiceLineDetail> invoiceLineDetails) {
//                if (invoiceLineDetails.size() > 0) {
//                    InvoiceLineDetail item = invoiceLineDetails.get(0);
//                    binding.itemName.setText(item.getItem());
//                    binding.itemQty.setText(String.format(getString(R.string.x_n_unit), item.getQty().intValue() + ""));
//                    try {
//                        Glide.with(PembayaranActivity.this).asBitmap()
//                                .load(Base64.decode(item.getImage(), Base64.DEFAULT))
//                                .into(binding.itemImage);
//                    } catch (Exception e) {
//
//                    }
//                }
                if (invoiceLineDetails.size() > 0) {
                    binding.otherItem.setVisibility(View.VISIBLE);
                    binding.otherItem.setText(String.format(getString(R.string.n_produk_lainnya)));
                } else {
                    binding.otherItem.setVisibility(View.GONE);
                }
            }
        });

        viewModel.getBankList().observe(this, new Observer<List<RekeningBank>>() {
            @Override
            public void onChanged(List<RekeningBank> rekeningBanks) {
                binding.total.setText(StringHelper.numberFormatWithDecimal(viewModel.getTotal()));

                String from = "", value = "";
                for (RekeningBank bank :
                        rekeningBanks) {
                    if (bank.getChecked()) {
                        from += String.format(getString(R.string.bayar_dengan_n), bank.getName()) + "\n";
                        value += StringHelper.numberFormatWithDecimal(bank.getValue()) + "\n";
                    }
                }

                binding.subpayment.setText(from.trim());
                binding.subtotal.setText(value.trim());

                if (from.equals("")) {
                    binding.listPayment.setVisibility(View.GONE);
                } else {
                    binding.listPayment.setVisibility(View.VISIBLE);
                }
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
    }

    private void setupView() {
        binding = ActivityPembayaranBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }

    private void configureView() {
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.otherItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewModel.getPaymentData().getValue() != null) {
                    showDialog();
                }
            }
        });

        binding.bayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoading();
                viewModel.bayar(orderId);
            }
        });

        binding.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoading();
                viewModel.edit(orderId);
            }
        });
    }

    private void setupList() {
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.list.setLayoutManager(layoutManager);

        listAdapter = new PembayaranListAdapter(viewModel, this);
        listAdapter.setListener(this);
        binding.list.setAdapter(listAdapter);
    }


    public void showDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("List Produk");
        dialog.setCancelable(true);

        PopupOrderListBinding popup = PopupOrderListBinding.inflate(getLayoutInflater());
        View view = popup.getRoot();

        PopupProdukListAdapter adapter = new PopupProdukListAdapter(viewModel, this);

        popup.list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        popup.list.setAdapter(adapter);

        dialog.setView(view);
        dialog.show();
    }

    @Override
    public void onPembayaranChanged() {
        binding.list.post(new Runnable() {
            @Override
            public void run() {
                listAdapter.notifyDataSetChanged();
            }
        });
    }
}