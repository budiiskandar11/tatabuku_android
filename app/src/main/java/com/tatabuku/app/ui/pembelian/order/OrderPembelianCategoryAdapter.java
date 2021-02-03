package com.tatabuku.app.ui.pembelian.order;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tatabuku.app.R;
import com.tatabuku.app.databinding.CategoryItemBinding;
import com.tatabuku.app.databinding.OrderListItemBinding;
import com.tatabuku.app.model.pembelian.Category;
import com.tatabuku.app.model.pembelian.OrderModel;
import com.tatabuku.app.model.pembelian.Produk;
import com.tatabuku.app.util.StringHelper;

public class OrderPembelianCategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private OrderPembelianListener listener;
    private OrderPembelianViewModel viewModel;

    public void setListener(OrderPembelianListener listener) {
        this.listener = listener;
    }

    public OrderPembelianCategoryAdapter(Context context, OrderPembelianViewModel viewModel) {
        this.context = context;
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderPembelianCategoryAdapter.ViewHolder(CategoryItemBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder vh = (ViewHolder) holder;
            if (position == 0) {
                if (viewModel.getSelectedCategory().getValue() == null) {
                    vh.binding.text.setTextColor(ContextCompat.getColor(context, R.color.blue));
                } else {
                    vh.binding.text.setTextColor(ContextCompat.getColor(context, R.color.gray));
                }
                vh.binding.text.setText(R.string.semua_kategori);
                vh.binding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (listener != null) {
                            listener.onSelectCategory(null);
                        }
                    }
                });
            } else {
                Category category = viewModel.getCategories().getValue().get(position - 1);
                vh.binding.text.setText(category.getName());
                if (viewModel.getSelectedCategory().getValue() != null && viewModel.getSelectedCategory().getValue().getId().equals(category.getId())) {
                    vh.binding.text.setTextColor(ContextCompat.getColor(context, R.color.blue));
                } else {
                    vh.binding.text.setTextColor(ContextCompat.getColor(context, R.color.gray));
                }
                vh.binding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (listener != null) {
                            listener.onSelectCategory(category);
                        }
                    }
                });
            }

        }
    }

    @Override
    public int getItemCount() {
        return viewModel.getCategories().getValue().size() + 1;
    }


    private static class ViewHolder extends RecyclerView.ViewHolder {
        private CategoryItemBinding binding;

        public ViewHolder(CategoryItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}