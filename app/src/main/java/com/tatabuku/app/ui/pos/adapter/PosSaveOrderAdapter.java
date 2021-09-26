package com.tatabuku.app.ui.pos.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tatabuku.app.R;
import com.tatabuku.app.databinding.AdapterItemBillBinding;
import com.tatabuku.app.databinding.AdapterListOrderBillBinding;
import com.tatabuku.app.model.pembelian.SupplierResult;
import com.tatabuku.app.model.pos.AddOrderBill;
import com.tatabuku.app.ui.pos.PosViewModel;
import com.tatabuku.app.util.StringHelper;

import java.util.Objects;

public class PosSaveOrderAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final Context context;
    private final PosViewModel viewModel;

    public PosSaveOrderAdapter(Context context, PosViewModel viewModel) {
        this.context = context;
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(AdapterListOrderBillBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent,false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder){
            ViewHolder vh = (ViewHolder) holder;
            AddOrderBill orderBill = viewModel.getOrderBill().getValue().get(position);;
            if (orderBill.getPartnerId().getName().equals("notCostumer")){
                vh.binding.name.setText(orderBill.getPartnerId().getName() + " (" + (position+1) + ")");
            } else {
                vh.binding.name.setText(orderBill.getPartnerId().getName());
            }
            vh.binding.image.setImageResource(R.drawable.no_image);
            try {
                Glide.with(context).asBitmap()
                        .load(Base64.decode(orderBill.getPartnerId().getImage(), Base64.DEFAULT))
                        .into(vh.binding.image);
            } catch (Exception ignored) {

            }
            vh.binding.total.setText(StringHelper.numberFormat(orderBill.getTotal()));
        }
    }

    @Override
    public int getItemCount() {
        return Objects.requireNonNull(viewModel.getOrderBill().getValue()).size();
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {
        private final AdapterListOrderBillBinding binding;

        public ViewHolder(AdapterListOrderBillBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
