package com.tatabuku.app.ui.pos.adapter;

import android.content.Context;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tatabuku.app.R;
import com.tatabuku.app.databinding.DashboardCustomerListItemBinding;
import com.tatabuku.app.model.pembelian.SupplierResult;
import com.tatabuku.app.ui.pos.PosViewModel;
import com.tatabuku.app.ui.pos.listener.PosCostumerListener;
import com.tatabuku.app.util.StringHelper;

import java.util.Objects;

public class PosCostumerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final Context context;
    private final PosViewModel viewModel;
    private PosCostumerListener listener;

    public PosCostumerAdapter(Context context, PosViewModel viewModel) {
        this.context = context;
        this.viewModel = viewModel;
    }

    public void setListener(PosCostumerListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(DashboardCustomerListItemBinding.inflate(LayoutInflater.from(parent.getContext()),
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
            vh.binding.valueName.setText(R.string.total_pembelian);
            vh.binding.value.setText(StringHelper.numberFormat(supplier.getPurchaseOrderTotal()));
            vh.binding.getRoot().setOnClickListener(v -> listener.onCostumerSelect(supplier));

        }
    }

    @Override
    public int getItemCount() {
        return Objects.requireNonNull(viewModel.getSupplierList().getValue()).size();
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {
        private final DashboardCustomerListItemBinding binding;

        public ViewHolder(DashboardCustomerListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
