package com.tatabuku.app.ui.saldo_hutang;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tatabuku.app.R;
import com.tatabuku.app.databinding.ActivitySaldoHutangBinding;
import com.tatabuku.app.databinding.DashboardSupplierListItemBinding;
import com.tatabuku.app.databinding.PiutangListCustomerBinding;
import com.tatabuku.app.model.pembelian.SupplierResult;
import com.tatabuku.app.model.penjualan.CustomerResult;
import com.tatabuku.app.ui.pembelian.dashboard.DashboardSupplierActivity;
import com.tatabuku.app.ui.saldoPiutang.ActivitySaldoPiutangListener;
import com.tatabuku.app.ui.saldoPiutang.ActivitySaldoPiutangViewModel;
import com.tatabuku.app.ui.saldoPiutang.PiutangListCustomerAdapter;

public class ActivitySaldoHutangAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private ActivitySaldoHutangViewModel viewModel;
    private Context context;
    private ActivitySaldoHutangListener listener;

    public void setListener(ActivitySaldoHutangListener listener) {
        this.listener = listener;
    }

    public ActivitySaldoHutangAdapter(Context context, ActivitySaldoHutangViewModel viewModel) {
        this.context = context;
        this.viewModel = viewModel;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ActivitySaldoHutangAdapter.ViewHolder(DashboardSupplierListItemBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ActivitySaldoHutangAdapter.ViewHolder) {
            ActivitySaldoHutangAdapter.ViewHolder vh = (ActivitySaldoHutangAdapter.ViewHolder) holder;
            SupplierResult supplier = viewModel.getSupplierList().getValue().get(position);

            vh.binding.name.setText(supplier.getName());
            vh.binding.address.setText(supplier.getStreet());
            vh.binding.phone.setText(supplier.getPhone());
            vh.binding.valueName.setText(R.string.total_hutang);

            vh.binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (listener != null) {
                        listener.onItemClick(supplier);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return viewModel.getSupplierList().getValue().size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private DashboardSupplierListItemBinding binding;

        public ViewHolder(DashboardSupplierListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
