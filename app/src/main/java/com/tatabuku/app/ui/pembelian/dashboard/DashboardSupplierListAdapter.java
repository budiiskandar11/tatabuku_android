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
import com.tatabuku.app.databinding.DashboardSupplierListHutangItemBinding;
import com.tatabuku.app.databinding.DashboardSupplierListItemBinding;
import com.tatabuku.app.model.pembelian.SupplierResult;
import com.tatabuku.app.util.StringHelper;

public class DashboardSupplierListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private DashboardSupplierActivity.DashboardType currentType;
    private DashboardSupplierListener listener;
    private DashboardSupplierViewModel viewModel;
    private Context context;

    public void setListener(DashboardSupplierListener listener) {
        this.listener = listener;
    }

    public DashboardSupplierListAdapter(Context context, DashboardSupplierViewModel viewModel) {
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
        return new DashboardSupplierListAdapter.ViewHolder(DashboardSupplierListItemBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ViewHolder) {
            ViewHolder vh = (ViewHolder) holder;
            SupplierResult supplier = viewModel.getSupplierList().getValue().get(position);

            vh.binding.name.setText(supplier.getName());
            vh.binding.address.setText(supplier.getStreet());
            vh.binding.phone.setText(supplier.getPhone());
            try {
                Glide.with(context).asBitmap()
                        .load(Base64.decode(supplier.getImage(), Base64.DEFAULT))
                        .into(vh.binding.image);
            } catch (Exception e) {

            }

            vh.binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (listener != null) {
                        listener.onItemClick(supplier);
                    }
                }
            });

            switch (currentType) {
                case UANGMUKA:
                    vh.binding.valueName.setText(R.string.total_saldo_dp);
                    vh.binding.value.setText(StringHelper.numberFormat(supplier.getDpTotal()));
                    break;

                case PEMBELIAN:
                    vh.binding.valueName.setText(R.string.total_pembelian);
                    vh.binding.value.setText(StringHelper.numberFormat(supplier.getPurchaseOrderTotal()));
                    break;
                case PEMBAYARAN:
                    vh.binding.valueName.setText(R.string.total_pembayaran);
                    vh.binding.value.setText(StringHelper.numberFormat(supplier.getPaymentTahun()));
                    break;

                case HUTANG:
                case LUNAS:
                    vh.binding.valueName.setText(R.string.total_hutang);
                    vh.binding.value.setText(StringHelper.numberFormat(supplier.getHutangTotal()));
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return viewModel.getSupplierList().getValue().size();
    }

    @Override
    public int getItemViewType(int position) {
        switch (currentType) {
            case HUTANG:
            case LUNAS:
                return 1;
            default:
                return 0;
        }
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {
        private DashboardSupplierListItemBinding binding;

        public ViewHolder(DashboardSupplierListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    private static class HutangViewHolder extends RecyclerView.ViewHolder {
        private DashboardSupplierListHutangItemBinding binding;

        public HutangViewHolder(DashboardSupplierListHutangItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}