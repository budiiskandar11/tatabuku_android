package com.tatabuku.app.ui.penjualan.order;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tatabuku.app.R;
import com.tatabuku.app.databinding.ReturnListItemBinding;
import com.tatabuku.app.model.pembelian.OrderModel;
import com.tatabuku.app.model.pembelian.Produk;
import com.tatabuku.app.ui.pembelian.order.OrderPembelianListener;
import com.tatabuku.app.util.StringHelper;

public class OrderReturPenjualanListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private OrderPembelianListener listener;
    private OrderReturPenjualanViewModel viewModel;

    public void setListener(OrderPembelianListener listener) {
        this.listener = listener;
    }

    public OrderReturPenjualanListAdapter(Context context, OrderReturPenjualanViewModel viewModel) {
        this.context = context;
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderReturPenjualanListAdapter.ViewHolder(ReturnListItemBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder vh = (ViewHolder) holder;
            Produk produk = viewModel.getProdukList().getValue().get(position);
            OrderModel order = viewModel.getOrder(produk.getId());
            order.setName(produk.getName());
            order.setImage(produk.getImage());
            order.setPrice_unit(produk.getStandardPrice());

            vh.binding.qty.removeTextChangedListener(vh.qtyWatcher);
            vh.viewModel = viewModel;
            vh.listener = listener;
            vh.order = order;
            vh.binding.qty.addTextChangedListener(vh.qtyWatcher);

            vh.binding.name.setText(produk.getName());
            vh.binding.price.setText(StringHelper.numberFormat(order.getPrice_unit()));
            vh.binding.qty.setText(order.getProduct_qty() + "");
            vh.binding.maxQty.setVisibility(View.GONE);
            vh.binding.image.setImageResource(R.drawable.no_image);

            try {
                Glide.with(context).asBitmap()
                        .load(Base64.decode(order.getImage(), Base64.DEFAULT))
                        .into(vh.binding.image);
            } catch (Exception e) {

            }


            vh.binding.plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (order.getProduct_qty() < StringHelper.QTY_LIMIT) {
                        order.setProduct_qty(order.getProduct_qty() + 1);
                        vh.binding.qty.setText(order.getProduct_qty() + "");
                        viewModel.updateOrder(order);
                        if (listener != null) {
                            listener.onOrderChanged();
                        }
                    }
                }
            });

            vh.binding.minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (order.getProduct_qty() > 0) {
                        order.setProduct_qty(order.getProduct_qty() - 1);
                        vh.binding.qty.setText(order.getProduct_qty() + "");
                        viewModel.updateOrder(order);
                        if (listener != null) {
                            listener.onOrderChanged();
                        }
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return viewModel.getProdukList().getValue().size();
    }


    private static class ViewHolder extends RecyclerView.ViewHolder {
        private ReturnListItemBinding binding;
        private OrderReturPenjualanViewModel viewModel;
        private OrderModel order;
        private OrderPembelianListener listener;

        private TextWatcher qtyWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                binding.qty.removeTextChangedListener(this);
                if (s.toString().equalsIgnoreCase("")) {
                    order.setProduct_qty(0);
                    binding.qty.setText("0");
                    binding.qty.setSelection(binding.qty.getText().length());
                    viewModel.updateOrder(order);
                    if (listener != null) {
                        listener.onOrderChanged();
                    }
                } else {
                    int parsed = Integer.parseInt(s.toString());
                    if(s.toString().startsWith("0")){
                        binding.qty.setText(parsed + "");
                        binding.qty.setSelection(binding.qty.getText().length());
                    }
                    if (parsed > StringHelper.QTY_LIMIT) {
                        parsed = StringHelper.QTY_LIMIT;
                        binding.qty.setText(parsed + "");
                        binding.qty.setSelection(binding.qty.getText().length());
                    }
                    order.setProduct_qty(parsed);
                    viewModel.updateOrder(order);
                    if (listener != null) {
                        listener.onOrderChanged();
                    }
                }
                binding.qty.addTextChangedListener(this);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };

        public ViewHolder(ReturnListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}