package com.tatabuku.app.ui.pembelian.checkout;

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
import com.tatabuku.app.databinding.CheckoutListItemBinding;
import com.tatabuku.app.model.pembelian.OrderModel;
import com.tatabuku.app.ui.pembelian.order.OrderPembelianListener;
import com.tatabuku.app.ui.pembelian.order.OrderPembelianViewModel;
import com.tatabuku.app.util.StringHelper;

public class CheckoutPembelianListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private CheckoutPembelianViewModel viewModel;
    private Context context;


    public CheckoutPembelianListAdapter(CheckoutPembelianViewModel viewModel, Context context) {
        this.viewModel = viewModel;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CheckoutPembelianListAdapter.ViewHolder(CheckoutListItemBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder vh = (ViewHolder) holder;
            OrderModel order = viewModel.getOrders().getValue().get(position);

            vh.binding.qty.removeTextChangedListener(vh.qtyWatcher);
            vh.viewModel = viewModel;
            vh.order = order;
            vh.binding.qty.addTextChangedListener(vh.qtyWatcher);

            vh.binding.name.setText(order.getName());
            vh.binding.price.setText(StringHelper.numberFormat(order.getPrice_unit()));
            vh.binding.subtotal.setText(StringHelper.numberFormat(order.getPrice_unit() * order.getProduct_qty()));
            vh.binding.qty.setText(order.getProduct_qty() + "");
            vh.binding.image.setImageResource(R.drawable.no_image);

            try {
                Glide.with(context).asBitmap()
                        .load(Base64.decode(order.getImage(), Base64.DEFAULT))
                        .into(vh.binding.image);
            } catch (Exception e) {

            }

            vh.binding.plus.setEnabled(!viewModel.getIsView().getValue());
            vh.binding.minus.setEnabled(!viewModel.getIsView().getValue());
            vh.binding.qty.setEnabled(!viewModel.getIsView().getValue());

            vh.binding.plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (order.getProduct_qty() < StringHelper.QTY_LIMIT) {
                        order.setProduct_qty(order.getProduct_qty() + 1);
                        vh.binding.qty.setText(order.getProduct_qty() + "");
                        viewModel.getOrders().setValue(viewModel.getOrders().getValue());
                    }
                }
            });

            vh.binding.minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (order.getProduct_qty() > 0) {
                        order.setProduct_qty(order.getProduct_qty() - 1);
                        vh.binding.qty.setText(order.getProduct_qty() + "");
                        viewModel.getOrders().setValue(viewModel.getOrders().getValue());
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return viewModel.getOrders().getValue().size();
    }


    private static class ViewHolder extends RecyclerView.ViewHolder {
        private CheckoutListItemBinding binding;
        private CheckoutPembelianViewModel viewModel;
        private OrderModel order;

        private TextWatcher qtyWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                binding.qty.removeTextChangedListener(this);
                if (s.toString().equalsIgnoreCase("")) {
                    order.setProduct_qty(0);
                    binding.qty.setSelection(binding.qty.getText().length());
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
                }
                viewModel.getOrders().setValue(viewModel.getOrders().getValue());
                binding.qty.addTextChangedListener(this);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };

        public ViewHolder(CheckoutListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}