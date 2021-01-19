package com.tatabuku.app.ui.pembelian.detail;

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
import com.tatabuku.app.model.pembelian.DownPaymentResult;
import com.tatabuku.app.model.pembelian.InvoiceResult;
import com.tatabuku.app.model.pembelian.LunasDataResult;
import com.tatabuku.app.model.pembelian.OrderResult;
import com.tatabuku.app.model.pembelian.PembayaranItem;
import com.tatabuku.app.ui.pembelian.dashboard.DashboardSupplierActivity;
import com.tatabuku.app.util.StringHelper;

public class DetailSupplierListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private DashboardSupplierActivity.DashboardType currentType;
    private DetailSupplierListener listener;
    private DetailSupplierViewModel viewModel;
    private Context context;

    public void setListener(DetailSupplierListener listener) {
        this.listener = listener;
    }

    public DetailSupplierListAdapter(Context context, DetailSupplierViewModel viewModel) {
        this.viewModel = viewModel;
        this.context = context;
        this.currentType = DashboardSupplierActivity.DashboardType.PEMBELIAN;
    }

    public void setCurrentType(DashboardSupplierActivity.DashboardType currentType) {
        this.currentType = currentType;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case 1:
                return new DetailSupplierListAdapter.DpViewHolder(DetailSupplierDpItemBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent, false));
            case 2:
                return new DetailSupplierListAdapter.PaymentViewHolder(DetailSupplierLunasItemBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent, false));
            default:
                return new DetailSupplierListAdapter.ViewHolder(DetailSupplierListItemBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ViewHolder) {
            ViewHolder vh = (ViewHolder) holder;
            if (currentType == DashboardSupplierActivity.DashboardType.PEMBELIAN) {
                OrderResult orderResult = viewModel.getOrderList().getValue().get(position);
                vh.binding.orderId.setText("Order No " + orderResult.getName() + " : " + StringHelper.formatOrderDate(orderResult.getDateOrder()));
                vh.binding.name.setText(viewModel.getSupplierResult().getValue().getName());
                vh.binding.value.setText(StringHelper.numberFormat(orderResult.getAmountTotal()));
                vh.binding.label.setVisibility(View.VISIBLE);
                vh.binding.count.setText(String.format(context.getString(R.string.n_barang), orderResult.getOrderLine().size()));
                if (orderResult.getState().equals("draft")) {
                    vh.binding.label.setText(context.getString(R.string.checkout));
                    vh.binding.label.setBackgroundResource(R.drawable.rounded_gray);
                    vh.binding.status.setText(context.getString(R.string.belum_di_checkout));
                    vh.binding.status.setTextColor(ContextCompat.getColor(context, R.color.blue));
                } else if (orderResult.getState().equals("purchase")) {
                    vh.binding.label.setText(context.getString(R.string.terima_barang));
                    vh.binding.label.setBackgroundResource(R.drawable.rounded_red);
                    vh.binding.status.setText(context.getString(R.string.belum_diterima));
                    vh.binding.status.setTextColor(ContextCompat.getColor(context, R.color.red));
                } else if (orderResult.getState().equals("done")) {
                    vh.binding.label.setVisibility(View.INVISIBLE);
                    vh.binding.status.setText(context.getString(R.string.sudah_diterima));
                    vh.binding.status.setTextColor(ContextCompat.getColor(context, R.color.gray));
                } else if (orderResult.getState().equals("cancel")) {
                    vh.binding.label.setVisibility(View.INVISIBLE);
                    vh.binding.status.setText(context.getString(R.string.cancel));
                    vh.binding.status.setTextColor(ContextCompat.getColor(context, R.color.red));
                } else if (orderResult.getState().equals("partial_lock")) {
                    vh.binding.label.setVisibility(View.INVISIBLE);
                    vh.binding.status.setText(context.getString(R.string.partially_done));
                    vh.binding.status.setTextColor(ContextCompat.getColor(context, R.color.gray));
                }
                vh.binding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (listener != null) {
                            if (orderResult.getState().equals("draft")) {
                                listener.onCheckout(orderResult.getId());
                            } else if (orderResult.getState().equals("purchase")) {
                                listener.onTerimaBarang(orderResult.getId());
                            } else {
                                listener.onViewOrder(orderResult.getId());
                            }
                        }
                    }
                });
            } else if (currentType == DashboardSupplierActivity.DashboardType.HUTANG || currentType == DashboardSupplierActivity.DashboardType.LUNAS) {
                InvoiceResult invoiceResult = viewModel.getInvoiceList().getValue().get(position);
                vh.binding.orderId.setText("Order No " + invoiceResult.getNumber() + " : " + StringHelper.formatInvoiceDate(invoiceResult.getDateInvoice()));
                vh.binding.name.setText(viewModel.getSupplierResult().getValue().getName());
                vh.binding.value.setText(StringHelper.numberFormat(invoiceResult.getResidual()));
                vh.binding.count.setText(String.format(context.getString(R.string.n_barang), invoiceResult.getInvoiceLineIds().get(0)));

                vh.binding.label.setVisibility(View.VISIBLE);
                if (invoiceResult.getState().equals("open")) {
                    vh.binding.label.setText(context.getString(R.string.bayar));
                    vh.binding.label.setBackgroundResource(R.drawable.rounded_red);
                    vh.binding.status.setText(context.getString(R.string.belum_lunas));
                    vh.binding.status.setTextColor(ContextCompat.getColor(context, R.color.red));
                } else if (invoiceResult.getState().equals("paid")) {
                    vh.binding.label.setVisibility(View.INVISIBLE);
                    vh.binding.status.setText(context.getString(R.string.lunas));
                    vh.binding.status.setTextColor(ContextCompat.getColor(context, R.color.gray));
                }
                vh.binding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (listener != null) {
                            if (invoiceResult.getState().equals("open")) {
                                listener.onBayarHutang(invoiceResult.getId(), false);
                            } else {
                                listener.onBayarHutang(invoiceResult.getId(), true);
                            }
                        }
                    }
                });
            }
        } else if (holder instanceof DpViewHolder) {
            DpViewHolder vh = (DpViewHolder) holder;
            DownPaymentResult dp = viewModel.getDpList().getValue().get(position);

            vh.binding.name.setText(viewModel.getSupplierResult().getValue().getName());
            vh.binding.orderId.setText(dp.getName() + " : " + StringHelper.formatInvoiceDate(dp.getDate()));
            vh.binding.value.setText(StringHelper.numberFormat(dp.getAmountDp()));


            if (dp.getDpType().equals("sup_payment_in")) {
                vh.binding.background.setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_blue));
                vh.binding.status.setText(context.getString(R.string.bayar_dp));
            } else if (dp.getDpType().equals("sup_payment_out")) {
                vh.binding.background.setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_green));
                vh.binding.status.setText(context.getString(R.string.refund_dp));
            } else if (dp.getDpType().equals("retur_purchase")) {
                vh.binding.background.setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_red));
                vh.binding.status.setText(context.getString(R.string.retur_barang));
            }

            vh.binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (listener != null) {
                        listener.onEditDP(dp.getId());
                    }
                }
            });
        } else if (holder instanceof PaymentViewHolder) {

            PaymentViewHolder vh = (PaymentViewHolder) holder;
            PembayaranItem payment = viewModel.getPaymentList().getValue().get(position);

            vh.binding.name.setText(viewModel.getSupplierResult().getValue().getName());
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
            case PEMBELIAN:
                return viewModel.getOrderList().getValue().size();
            case HUTANG:
            case LUNAS:
                return viewModel.getInvoiceList().getValue().size();
            case UANGMUKA:
                return viewModel.getDpList().getValue().size();
            case PEMBAYARAN:
                return viewModel.getPaymentList().getValue().size();
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