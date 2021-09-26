package com.tatabuku.app.ui.pos.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tatabuku.app.R;
import com.tatabuku.app.databinding.OrderListItemGridBinding;
import com.tatabuku.app.model.pembelian.OrderModel;
import com.tatabuku.app.model.pembelian.Produk;
import com.tatabuku.app.ui.pos.listener.PosListener;
import com.tatabuku.app.ui.pos.PosViewModel;
import com.tatabuku.app.util.StringHelper;

import java.util.Objects;

public class PosListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
    private final PosViewModel viewModel;
    private PosListener listener;

    public PosListAdapter(Context context, PosViewModel viewModel) {
        this.context = context;
        this.viewModel = viewModel;
    }

    public void setListener(PosListener listener) {
        this.listener = listener;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PosListAdapter.ViewHolder(OrderListItemGridBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder vh = (ViewHolder) holder;
            Produk product = viewModel.getProdukList().getValue().get(position);
            OrderModel order = viewModel.getOrder(product.getId());
            order.setImage(product.getImage());
            if (order.getPrice_unit() == 0) {
                order.setPrice_unit(product.getStandardPrice());
            }
            order.setName(product.getName());

            vh.binding.name.setText(product.getName());
            vh.binding.image.setImageResource(R.drawable.no_image);
            try {
                Glide.with(context).asBitmap()
                        .load(Base64.decode(product.getImage(), Base64.DEFAULT))
                        .into(vh.binding.image);
            } catch (Exception ignored) {

            }
            vh.binding.price.setText(StringHelper.numberFormat(order.getPrice_unit() != null ? order.getPrice_unit() : product.getStandardPrice()));
            vh.binding.main.setOnClickListener(v -> {
                        vh.binding.llAdd.setVisibility(View.VISIBLE);
                        order.setProduct_qty(order.getProduct_qty() + 1);
                        order.setSelected(1);
                        viewModel.updateOrder(order);

                        vh.binding.qty.setText(order.getProduct_qty() + "");

                        if (listener != null) {
                            listener.onOrderChanged();
                        }
                    }
            );

            if (order.getSelected() == 1){
                vh.binding.llAdd.setVisibility(View.VISIBLE);
                vh.binding.qty.setText(order.getProduct_qty() + "");
                vh.binding.price.setText(StringHelper.numberFormat(order.getPrice_unit() != null ? order.getPrice_unit() : product.getStandardPrice()));
                if (listener != null) {
                    listener.onOrderChanged();
                }
            } else {
                vh.binding.llAdd.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return Objects.requireNonNull(viewModel.getProdukList().getValue()).size();
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {
        private final OrderListItemGridBinding binding;

        public ViewHolder(OrderListItemGridBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
