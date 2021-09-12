package com.tatabuku.app.ui.saldoPiutang;

import android.content.Context;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tatabuku.app.databinding.DashboardCustomerListItemBinding;
import com.tatabuku.app.databinding.PiutangListCustomerBinding;
import com.tatabuku.app.model.penjualan.CustomerResult;
import com.tatabuku.app.ui.penjualan.dashboard.DashboardCustomerActivity;
import com.tatabuku.app.ui.penjualan.dashboard.DashboardCustomerListAdapter;
import com.tatabuku.app.ui.penjualan.dashboard.DashboardCustomerListener;
import com.tatabuku.app.ui.penjualan.dashboard.DashboardCustomerViewModel;
import com.tatabuku.app.util.StringHelper;

public class PiutangListCustomerAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {
    
    private ActivitySaldoPiutangViewModel viewModel;
    private Context context;


    public PiutangListCustomerAdapter(Context context, ActivitySaldoPiutangViewModel viewModel) {
        this.context = context;
        this.viewModel = viewModel;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PiutangListCustomerAdapter.ViewHolder(PiutangListCustomerBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof PiutangListCustomerAdapter.ViewHolder) {
            PiutangListCustomerAdapter.ViewHolder vh = (PiutangListCustomerAdapter.ViewHolder) holder;
            CustomerResult customer = viewModel.getListCustomer().getValue().get(position);

            vh.binding.name.setText(customer.getName());
            vh.binding.address.setText(customer.getStreet());
            vh.binding.phone.setText(customer.getPhone());
        }
    }

    @Override
    public int getItemCount() {
        return viewModel.getListCustomer().getValue().size();
    }

    public void setListener(DashboardCustomerListener listener) {
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private PiutangListCustomerBinding binding;

        public ViewHolder(PiutangListCustomerBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
