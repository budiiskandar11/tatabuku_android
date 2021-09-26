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
import com.tatabuku.app.databinding.DashboardCustomerPagerItemBinding;
import com.tatabuku.app.databinding.DashboardSupplierPagerItemBinding;
import com.tatabuku.app.model.pembelian.SupplierResult;
import com.tatabuku.app.model.penjualan.CustomerResult;
import com.tatabuku.app.ui.pembelian.dashboard.DashboardSupplierActivity;
import com.tatabuku.app.ui.pembelian.dashboard.DashboardSupplierListener;
import com.tatabuku.app.ui.pembelian.dashboard.DashboardSupplierViewModel;
import com.tatabuku.app.util.StringHelper;

public class DashboardCustomerPagerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private DashboardCustomerActivity.DashboardType currentType;
    private DashboardCustomerViewModel viewModel;
    private Context context;
    private DashboardCustomerListener listener;

    public void setListener(DashboardCustomerListener listener) {
        this.listener = listener;
    }

    public DashboardCustomerPagerAdapter(Context context, DashboardCustomerViewModel viewModel) {
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
        return new ViewHolder(DashboardCustomerPagerItemBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DashboardCustomerPagerAdapter.ViewHolder) {
            DashboardCustomerPagerAdapter.ViewHolder vh = (DashboardCustomerPagerAdapter.ViewHolder) holder;
            CustomerResult customer = viewModel.getListFaveCustomer().getValue().get(position);

            vh.binding.name.setText(customer.getName());
//            vh.binding.address.setText(customer.getStreet());
//            vh.binding.phone.setText(customer.getPhone());
            Glide.with(context).asBitmap()
                    .load(Base64.decode(customer.getImage(), Base64.DEFAULT))
                    .into(vh.binding.image);

            vh.binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (listener != null) {
                        listener.onItemClick(customer);
                    }
                }
            });
            vh.binding.leftText.setText(R.string.hari_ini);
            vh.binding.middleText.setText(R.string.bulan_ini);
            vh.binding.rightText.setText(R.string.tahun_ini);
            switch (currentType) {
                case PIUTANG:
                    vh.binding.leftText.setText(R.string.belum_jatuh_tempo);
                    vh.binding.middleText.setText(R.string.terlambat_1_30_hari);
                    vh.binding.rightText.setText(R.string.terlambat_31_60_hari);
                    vh.binding.leftValue.setText(StringHelper.numberFormat(customer.getHutangBlmJatuhTempo()));
                    vh.binding.middleValue.setText(StringHelper.numberFormat(customer.getHutang30()));
                    vh.binding.rightValue.setText(StringHelper.numberFormat(customer.getHutang60()));
                    break;
                case UANGMUKA:
                    vh.binding.leftText.setText(R.string.dp_diterima);
                    vh.binding.middleText.setText(R.string.dp_terpakai);
                    vh.binding.rightText.setText(R.string.saldo_dp);
                    vh.binding.leftValue.setText(StringHelper.numberFormat(customer.getDpMasuk()));
                    vh.binding.middleValue.setText(StringHelper.numberFormat(customer.getDpKeluar()));
                    vh.binding.rightValue.setText(StringHelper.numberFormat(customer.getSaldoDp()));
                    break;
                case PEMBAYARAN:
                    vh.binding.leftValue.setText(StringHelper.numberFormat(customer.getPaymentHari()));
                    vh.binding.middleValue.setText(StringHelper.numberFormat(customer.getPaymentBulan()));
                    vh.binding.rightValue.setText(StringHelper.numberFormat(customer.getPaymentTahun()));
                    break;
                case PENJUALAN:
                    vh.binding.leftValue.setText(StringHelper.numberFormat(customer.getPenjualanHari()));
                    vh.binding.middleValue.setText(StringHelper.numberFormat(customer.getPenjualanBulan()));
                    vh.binding.rightValue.setText(StringHelper.numberFormat(customer.getTotalPenjualanTahun()));
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return viewModel.getListFaveCustomer().getValue().size();
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {
        private DashboardCustomerPagerItemBinding binding;

        public ViewHolder(DashboardCustomerPagerItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
