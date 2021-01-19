package com.tatabuku.app.ui.penjualan.pengiriman;

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
import com.tatabuku.app.databinding.PenerimaanListItemBinding;
import com.tatabuku.app.model.pembelian.PenerimaanBarangItem;
import com.tatabuku.app.model.penjualan.PengirimanBarangItem;
import com.tatabuku.app.ui.pembelian.penerimaan.PenerimaanBarangViewModel;
import com.tatabuku.app.util.StringHelper;

public class PengirimanBarangListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private PengirimanBarangViewModel viewModel;
    private Context context;

    public PengirimanBarangListAdapter(PengirimanBarangViewModel viewModel, Context context) {
        this.viewModel = viewModel;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PengirimanBarangListAdapter.ViewHolder(PenerimaanListItemBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder vh = (ViewHolder) holder;
            PengirimanBarangItem order = viewModel.getListItem().getValue().get(position);

            vh.binding.qty.removeTextChangedListener(vh.qtyWatcher);
            vh.viewModel = viewModel;
            vh.order = order;
            vh.binding.qty.addTextChangedListener(vh.qtyWatcher);

            vh.binding.name.setText(order.getProductName());
            vh.binding.price.setText(StringHelper.numberFormat(order.getPriceUnit()));
            vh.binding.qty.setText(order.getQtyDeliver().intValue() + "");
            vh.binding.maxQty.setText("x " + order.getQtyOrder().intValue() + " Unit");
            vh.binding.sisa.setText("x " + order.getDiffQty().intValue() + " Unit");
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
                    if (order.getDiffQty() > 0) {
                        order.setQtyDeliver(order.getQtyDeliver() + 1);
                        vh.binding.qty.setText(order.getQtyDeliver().intValue() + "");
                        viewModel.getListItem().setValue(viewModel.getListItem().getValue());
                    }
                }
            });

            vh.binding.minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (order.getQtyDeliver() > 0) {
                        order.setQtyDeliver(order.getQtyDeliver() - 1);
                        vh.binding.qty.setText(order.getQtyDeliver().intValue() + "");
                        viewModel.getListItem().setValue(viewModel.getListItem().getValue());
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return viewModel.getListItem().getValue().size();
    }


    private static class ViewHolder extends RecyclerView.ViewHolder {
        private PenerimaanListItemBinding binding;
        private PengirimanBarangViewModel viewModel;
        private PengirimanBarangItem order;

        private TextWatcher qtyWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                binding.qty.removeTextChangedListener(this);
                if (s.toString().equalsIgnoreCase("")) {
                    order.setQtyDeliver(0.0);
                    binding.qty.setText(order.getQtyDeliver().intValue() + "");
                    binding.qty.setSelection(binding.qty.getText().length());
                } else {
                    Double parsed = Double.parseDouble(s.toString());
                    if(s.toString().startsWith("0")){
                        binding.qty.setText(parsed.intValue() + "");
                        binding.qty.setSelection(binding.qty.getText().length());
                    }
                    if (parsed > order.getQtyOrder()){
                        parsed = order.getQtyOrder();
                        binding.qty.setText(parsed.intValue() + "");
                        binding.qty.setSelection(binding.qty.getText().length());
                    }
                    order.setQtyDeliver(parsed);
                }
                viewModel.getListItem().setValue(viewModel.getListItem().getValue());
                binding.qty.addTextChangedListener(this);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };

        public ViewHolder(PenerimaanListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}