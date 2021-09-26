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
import com.tatabuku.app.databinding.OrderListItemBinding;
import com.tatabuku.app.model.pembelian.OrderModel;
import com.tatabuku.app.model.pembelian.Produk;
import com.tatabuku.app.ui.pembelian.order.OrderPembelianListener;
import com.tatabuku.app.ui.pembelian.order.OrderPembelianViewModel;
import com.tatabuku.app.util.StringHelper;

public class OrderPenjualanListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private OrderPembelianListener listener;
    private OrderPenjualanViewModel viewModel;

    public void setListener(OrderPembelianListener listener) {
        this.listener = listener;
    }

    public OrderPenjualanListAdapter(Context context, OrderPenjualanViewModel viewModel) {
        this.context = context;
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderPenjualanListAdapter.ViewHolder(OrderListItemBinding.inflate(LayoutInflater.from(parent.getContext()),
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
            if (order.getPrice_unit() == 0) {
                order.setPrice_unit(produk.getStandardPrice());
            }

            vh.binding.price.removeTextChangedListener(vh.textWatcher);
            vh.binding.qty.removeTextChangedListener(vh.qtyWatcher);
            vh.viewModel = viewModel;
            vh.listener = listener;
            vh.order = order;
            vh.binding.price.addTextChangedListener(vh.textWatcher);
            vh.binding.qty.addTextChangedListener(vh.qtyWatcher);

            vh.binding.name.setText(produk.getName());
            vh.binding.price.setText(StringHelper.numberFormat(order.getPrice_unit() != null ? order.getPrice_unit() : produk.getStandardPrice()));
            vh.binding.historyPrice.setText(String.format(context.getString(R.string.harga_history), StringHelper.numberFormat(produk.getStandardPrice())));
            vh.binding.stok.setText(String.format(context.getString(R.string.stok_saat_ini), produk.getQtyAvailable().intValue()));
            vh.binding.category.setText(produk.getCategId().getName());
            vh.binding.qty.setText(order.getProduct_qty() + "");
            vh.binding.image.setImageResource(R.drawable.no_image);
            try {
                Glide.with(context).asBitmap()
                        .load(Base64.decode(produk.getImage(), Base64.DEFAULT))
                        .into(vh.binding.image);
            } catch (Exception e) {

            }

            vh.binding.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onEditProduct(order.getProduct_id());
                    }
                }
            });

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
        private OrderListItemBinding binding;
        private OrderPenjualanViewModel viewModel;
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
        private TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {

                binding.price.removeTextChangedListener(this);
                String cleanString = s.toString().replaceAll("[Rp,.]", "").replaceAll(" ", "").trim();
                if (!cleanString.equals("")) {
                    double parsed = Double.parseDouble(cleanString);
                    String formatted = StringHelper.numberFormat(parsed);

                    binding.price.setText(formatted);
                    binding.price.setSelection(formatted.length());
                    order.setPrice_unit(parsed);
                    viewModel.updateOrder(order);
                    if (listener != null) {
                        listener.onOrderChanged();
                    }
                } else {
                    order.setPrice_unit(0.0);
                    viewModel.updateOrder(order);
                    if (listener != null) {
                        listener.onOrderChanged();
                    }
                    binding.price.setText("Rp");
                }
                binding.price.addTextChangedListener(this);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };

        public ViewHolder(OrderListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}