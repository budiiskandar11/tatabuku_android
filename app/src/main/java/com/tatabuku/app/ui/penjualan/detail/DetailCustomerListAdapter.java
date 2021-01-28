package com.tatabuku.app.ui.penjualan.detail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.tatabuku.app.R;
import com.tatabuku.app.databinding.DetailSupplierDpItemBinding;
import com.tatabuku.app.databinding.DetailSupplierListItemBinding;
import com.tatabuku.app.databinding.DetailSupplierLunasItemBinding;
import com.tatabuku.app.model.pembelian.PembayaranItem;
import com.tatabuku.app.model.penjualan.DPData;
import com.tatabuku.app.model.penjualan.HutangData;
import com.tatabuku.app.model.penjualan.PenjualanData;
import com.tatabuku.app.ui.pembelian.detail.DetailSupplierListAdapter;
import com.tatabuku.app.ui.penjualan.dashboard.DashboardCustomerActivity;
import com.tatabuku.app.util.StringHelper;

public class DetailCustomerListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private DashboardCustomerActivity.DashboardType currentType;
    private DetailCustomerViewModel viewModel;
    private Context context;
    private DetailCustomerListener listener;

    public void setListener(DetailCustomerListener listener) {
        this.listener = listener;
    }

    public DetailCustomerListAdapter(Context context, DetailCustomerViewModel viewModel) {
        this.viewModel = viewModel;
        this.context = context;
        this.currentType = DashboardCustomerActivity.DashboardType.PENJUALAN;
    }

    public void setCurrentType(DashboardCustomerActivity.DashboardType currentType) {
        this.currentType = currentType;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case 1:
                return new DetailCustomerListAdapter.DpViewHolder(DetailSupplierDpItemBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent, false));
            case 2:
                return new DetailCustomerListAdapter.PaymentViewHolder(DetailSupplierLunasItemBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent, false));
            default:
                return new DetailCustomerListAdapter.ViewHolder(DetailSupplierListItemBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ViewHolder) {
            ViewHolder vh = (ViewHolder) holder;
            if (currentType == DashboardCustomerActivity.DashboardType.PENJUALAN) {
                PenjualanData penjualanData = viewModel.getPenjualanList().getValue().get(position);
                vh.binding.orderId.setText("Order No " + penjualanData.getOrderNumber() + " : " + StringHelper.formatReceivedDate(penjualanData.getDate()));
                vh.binding.name.setText(penjualanData.getPartnerName());
                vh.binding.value.setText(StringHelper.numberFormat(penjualanData.getAmountTotal()));
                vh.binding.label.setVisibility(View.VISIBLE);
                vh.binding.count.setText(String.format(context.getString(R.string.n_barang), penjualanData.getJumlahBarang()));

                vh.binding.status.setText(penjualanData.getStatus());
                if (penjualanData.getStatus().equalsIgnoreCase("Belum Checkout")) {
                    vh.binding.label.setText(context.getString(R.string.checkout));
                    vh.binding.label.setBackgroundResource(R.drawable.rounded_gray);
                    vh.binding.status.setTextColor(ContextCompat.getColor(context, R.color.blue));
                } else if (penjualanData.getStatus().equalsIgnoreCase("Belum dikirim")) {
                    vh.binding.label.setText(context.getString(R.string.kirim_barang));
                    vh.binding.label.setBackgroundResource(R.drawable.rounded_red);
                    vh.binding.status.setTextColor(ContextCompat.getColor(context, R.color.red));
                } else if (penjualanData.getStatus().equalsIgnoreCase("Sudah dikirim")) {
                    vh.binding.label.setVisibility(View.INVISIBLE);
                    vh.binding.status.setTextColor(ContextCompat.getColor(context, R.color.gray));
                } else if (penjualanData.getStatus().equalsIgnoreCase("cancel")) {
                    vh.binding.label.setVisibility(View.INVISIBLE);
                    vh.binding.status.setTextColor(ContextCompat.getColor(context, R.color.red));
                } else if (penjualanData.getStatus().equalsIgnoreCase("Kirim Sebagian")) {
                    vh.binding.label.setVisibility(View.INVISIBLE);
                    vh.binding.status.setTextColor(ContextCompat.getColor(context, R.color.gray));
                }
                vh.binding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (listener != null) {
                            if (penjualanData.getStatus().equalsIgnoreCase("Belum Checkout")) {
                                listener.onCheckout(penjualanData.getId());
                            } else if (penjualanData.getStatus().equalsIgnoreCase("Belum dikirim")) {
                                listener.onKirimBarang(penjualanData.getId());
                            } else {
                                listener.onViewOrder(penjualanData.getId());
                            }
                        }
                    }
                });
            } else if (currentType == DashboardCustomerActivity.DashboardType.PIUTANG || currentType == DashboardCustomerActivity.DashboardType.LUNAS) {
                HutangData hutangData = viewModel.getHutangList().getValue().get(position);
                vh.binding.orderId.setText("Order No " + hutangData.getInvNo() + " : " + StringHelper.formatInvoiceDate(hutangData.getDate()));
                vh.binding.name.setText(hutangData.getPartnerName());

                vh.binding.count.setText(String.format(context.getString(R.string.n_barang), hutangData.getJumlahBarang()));
                vh.binding.label.setText(hutangData.getStatus());
                vh.binding.status.setText(hutangData.getStatus());

                vh.binding.label.setVisibility(View.VISIBLE);
                vh.binding.status.setText(hutangData.getStatus());
                if (hutangData.getStatus().equals("Belum Bayar")) {
                    vh.binding.label.setText(context.getString(R.string.bayar));
                    vh.binding.value.setText(StringHelper.numberFormat(hutangData.getTotalHarusDibayar()));
                    vh.binding.label.setBackgroundResource(R.drawable.rounded_red);
                    vh.binding.status.setTextColor(ContextCompat.getColor(context, R.color.red));
                } else {
                    vh.binding.label.setVisibility(View.INVISIBLE);
                    vh.binding.value.setText(StringHelper.numberFormat(hutangData.getTotalInvoice()));
                    vh.binding.status.setTextColor(ContextCompat.getColor(context, R.color.gray));
                }
                vh.binding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (listener != null) {
                            if (hutangData.getStatus().equals("Belum Bayar")) {
                                listener.onBayarHutang(hutangData.getId(), false);
                            } else {
                                listener.onBayarHutang(hutangData.getId(), true);
                            }
                        }
                    }
                });
            }
        } else if (holder instanceof DpViewHolder) {
            DpViewHolder vh = (DpViewHolder) holder;
            DPData dp = viewModel.getDpList().getValue().get(position);

            vh.binding.name.setText(dp.getPartnerName());
            vh.binding.orderId.setText(dp.getDpNumber() + " : " + StringHelper.formatInvoiceDate(dp.getDate()));
            vh.binding.value.setText(StringHelper.numberFormat(dp.getAmount()));
            vh.binding.status.setText(dp.getStatus());

            vh.binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (listener != null) {
                        listener.onEditDp(dp.getId());
                    }
                }
            });
        } else if (holder instanceof PaymentViewHolder) {
            DetailCustomerListAdapter.PaymentViewHolder vh = (DetailCustomerListAdapter.PaymentViewHolder) holder;
            PembayaranItem payment = viewModel.getPembayaranList().getValue().get(position);

            vh.binding.name.setText(viewModel.getTotalData().getValue().getName());
            vh.binding.orderId.setText("Order No " + payment.getPaymentNo() + " : " + StringHelper.formatInvoiceDate(payment.getPaymentDate()));
            vh.binding.value.setText(StringHelper.numberFormat(payment.getTotalPaymentAmount()));

            vh.binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (listener != null) {
                        listener.onViewPayment(payment.getId());
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        switch (currentType) {
            case PENJUALAN:
                return viewModel.getPenjualanList().getValue().size();
            case PIUTANG:
            case LUNAS:
                return viewModel.getHutangList().getValue().size();
            case UANGMUKA:
                return viewModel.getDpList().getValue().size();
            case PEMBAYARAN:
                return viewModel.getPembayaranList().getValue().size();
        }
        return 0;

    }

    @Override
    public int getItemViewType(int position) {
        switch (currentType) {
            case UANGMUKA:
                return 1;
            case PEMBAYARAN:
                return 2;
            default:
                return 0;
        }
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {
        private DetailSupplierListItemBinding binding;

        public ViewHolder(DetailSupplierListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    private static class DpViewHolder extends RecyclerView.ViewHolder {
        private DetailSupplierDpItemBinding binding;

        public DpViewHolder(DetailSupplierDpItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    private static class PaymentViewHolder extends RecyclerView.ViewHolder {
        private DetailSupplierLunasItemBinding binding;

        public PaymentViewHolder(DetailSupplierLunasItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}