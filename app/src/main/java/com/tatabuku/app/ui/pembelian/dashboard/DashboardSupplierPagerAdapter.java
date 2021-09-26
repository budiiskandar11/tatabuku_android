package com.tatabuku.app.ui.pembelian.dashboard;

import android.content.Context;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tatabuku.app.R;
import com.tatabuku.app.databinding.DashboardSupplierPagerItemBinding;
import com.tatabuku.app.model.pembelian.SupplierResult;
import com.tatabuku.app.util.StringHelper;

public class DashboardSupplierPagerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private DashboardSupplierActivity.DashboardType currentType;
    private DashboardSupplierListener listener;
    private DashboardSupplierViewModel viewModel;
    private Context context;

    public void setListener(DashboardSupplierListener listener) {
        this.listener = listener;
    }

    public DashboardSupplierPagerAdapter(Context context, DashboardSupplierViewModel viewModel) {
        this.context = context;
        this.viewModel = viewModel;
        this.currentType = DashboardSupplierActivity.DashboardType.PEMBELIAN;
    }

    public void setCurrentType(DashboardSupplierActivity.DashboardType currentType) {
        this.currentType = currentType;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(DashboardSupplierPagerItemBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DashboardSupplierPagerAdapter.ViewHolder) {
            DashboardSupplierPagerAdapter.ViewHolder vh = (DashboardSupplierPagerAdapter.ViewHolder) holder;
            SupplierResult supplier = viewModel.getFaveSupplierList().getValue().get(position);

            vh.binding.name.setText(supplier.getName());
            vh.binding.address.setText(supplier.getStreet());
            vh.binding.phone.setText(supplier.getPhone());
            Glide.with(context).asBitmap()
                    .load(Base64.decode(supplier.getImage(), Base64.DEFAULT))
                    .into(vh.binding.image);

            vh.binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (listener != null) {
                        listener.onItemClick(supplier);
                    }
                }
            });
            vh.binding.leftText.setText(R.string.hari_ini);
            vh.binding.middleText.setText(R.string.bulan_ini);
            vh.binding.rightText.setText(R.string.tahun_ini);
            switch (currentType) {
                case HUTANG:
                case LUNAS:
                    vh.binding.leftText.setText(R.string.belum_jatuh_tempo);
                    vh.binding.middleText.setText(R.string.terlambat_1_30_hari);
                    vh.binding.rightText.setText(R.string.terlambat_31_60_hari);
                    vh.binding.leftValue.setText(StringHelper.numberFormat(supplier.getHutangHari()));
                    vh.binding.middleValue.setText(StringHelper.numberFormat(supplier.getHutangBulan()));
                    vh.binding.rightValue.setText(StringHelper.numberFormat(supplier.getHutangTotal()));
                    break;
                case UANGMUKA:
                    vh.binding.leftText.setText(R.string.dp_setor);
                    vh.binding.middleText.setText(R.string.dp_terpakai);
                    vh.binding.rightText.setText(R.string.saldo_dp);
                    vh.binding.leftValue.setText(StringHelper.numberFormat(supplier.getDpTotalHari()));
                    vh.binding.middleValue.setText(StringHelper.numberFormat(supplier.getDpTotalBulan()));
                    vh.binding.rightValue.setText(StringHelper.numberFormat(supplier.getDpTotal()));
                    break;
                case PEMBAYARAN:
                    vh.binding.leftValue.setText(StringHelper.numberFormat(supplier.getPaymentHari()));
                    vh.binding.middleValue.setText(StringHelper.numberFormat(supplier.getPaymentBulan()));
                    vh.binding.rightValue.setText(StringHelper.numberFormat(supplier.getPaymentTahun()));
                    break;
                case PEMBELIAN:
                    vh.binding.leftValue.setText(StringHelper.numberFormat(supplier.getPurchaseOrderHari()));
                    vh.binding.middleValue.setText(StringHelper.numberFormat(supplier.getPurchaseOrderBulan()));
                    vh.binding.rightValue.setText(StringHelper.numberFormat(supplier.getPurchaseOrderTotal()));
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return viewModel.getFaveSupplierList().getValue().size();
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {
        private DashboardSupplierPagerItemBinding binding;

        public ViewHolder(DashboardSupplierPagerItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
