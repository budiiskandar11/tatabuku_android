package com.tatabuku.app.ui.penjualan.dashboard;

import android.content.Context;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tatabuku.app.R;
import com.tatabuku.app.databinding.DashboardCustomerListItemBinding;
import com.tatabuku.app.databinding.DashboardSupplierListHutangItemBinding;
import com.tatabuku.app.databinding.DashboardSupplierListItemBinding;
import com.tatabuku.app.model.penjualan.CustomerResult;
import com.tatabuku.app.ui.pembelian.dashboard.DashboardSupplierActivity;
import com.tatabuku.app.util.StringHelper;

public class DashboardCustomerListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private DashboardCustomerActivity.DashboardType currentType;
    private DashboardCustomerViewModel viewModel;
    private Context context;

    private DashboardCustomerListener listener;

    public void setListener(DashboardCustomerListener listener) {
        this.listener = listener;
    }


    public DashboardCustomerListAdapter(Context context, DashboardCustomerViewModel viewModel) {
        this.context = context;
        this.currentType = DashboardCustomerActivity.DashboardType.PENJUALAN;
        this.viewModel = viewModel;
    }

    public void setCurrentType(DashboardCustomerActivity.DashboardType currentType) {
        this.currentType = currentType;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DashboardCustomerListAdapter.ViewHolder(DashboardCustomerListItemBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ViewHolder) {
            ViewHolder vh = (ViewHolder) holder;
            CustomerResult customer = viewModel.getListCustomer().getValue().get(position);

            vh.binding.name.setText(customer.getName());
            vh.binding.address.setText(customer.getStreet());
            vh.binding.phone.setText(customer.getPhone());

            try {
                Glide.with(context).asBitmap()
                        .load(Base64.decode(customer.getImage(), Base64.DEFAULT))
                        .into(vh.binding.image);
            } catch (Exception e) {

            }

            vh.binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (listener != null) {
                        listener.onItemClick(customer);
                    }
                }
            });

            switch (currentType) {
                case UANGMUKA:
//                    vh.binding.leftText.setText(R.string.dp_diterima);
//                    vh.binding.middleText.setText(R.string.dp_terpakai);
//                    vh.binding.rightText.setText(R.string.saldo_dp);
//                    vh.binding.leftValue.setText(StringHelper.numberFormat(customer.getDpMasuk()));
//                    vh.binding.middleValue.setText(StringHelper.numberFormat(customer.getDpKeluar()));
                    vh.binding.value.setText(StringHelper.numberFormat(customer.getSaldoDp()));
                    vh.binding.valueName.setText("Saldo DP");
                    break;

                case PENJUALAN:
                    vh.binding.valueName.setText("Total Penjualan");
                    vh.binding.value.setText(StringHelper.numberFormat(customer.getTotalPenjualanTahun()));
//                    vh.binding.leftText.setText(R.string.hari_ini);
//                    vh.binding.middleText.setText(R.string.bulan_ini);
//                    vh.binding.rightText.setText(R.string.tahun_ini);
//                    vh.binding.leftValue.setText(StringHelper.numberFormat(customer.getPenjualanHari()));
//                    vh.binding.middleValue.setText(StringHelper.numberFormat(customer.getPenjualanBulan()));
//                    vh.binding.rightValue.setText(StringHelper.numberFormat(customer.getTotalPenjualanTahun()));
                    break;
                case PEMBAYARAN:
//                    vh.binding.leftText.setText(R.string.hari_ini);
//                    vh.binding.middleText.setText(R.string.bulan_ini);
//                    vh.binding.rightText.setText(R.string.tahun_ini);
//                    vh.binding.leftValue.setText(StringHelper.numberFormat(customer.getPaymentHari()));
//                    vh.binding.middleValue.setText(StringHelper.numberFormat(customer.getPaymentBulan()));
//                    vh.binding.rightValue.setText(StringHelper.numberFormat(customer.getPaymentTahun()));
                    vh.binding.valueName.setText("Total Pembayaran");
                    vh.binding.value.setText(StringHelper.numberFormat(customer.getPaymentTahun()));
                    break;

                case PIUTANG:
//                    vh.binding.leftText.setText(R.string.belum_jatuh_tempo);
//                    vh.binding.middleText.setText(R.string.terlambat_1_30_hari);
//                    vh.binding.rightText.setText(R.string.terlambat_31_60_hari);
//                    vh.binding.leftValue.setText(StringHelper.numberFormat(customer.getHutangBlmJatuhTempo()));
//                    vh.binding.middleValue.setText(StringHelper.numberFormat(customer.getHutang30()));
//                    vh.binding.rightValue.setText(StringHelper.numberFormat(customer.getHutang60()));
                    vh.binding.valueName.setText("Total Piutang");
                    vh.binding.value.setText(StringHelper.numberFormat(customer.getHutang60()));
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return viewModel.getListCustomer().getValue().size();
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {
        private DashboardCustomerListItemBinding binding;

        public ViewHolder(DashboardCustomerListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}