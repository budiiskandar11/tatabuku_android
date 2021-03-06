package com.tatabuku.app.ui.pembelian.penerimaan;

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
import com.tatabuku.app.model.pembelian.OrderModel;
import com.tatabuku.app.model.pembelian.PenerimaanBarangItem;
import com.tatabuku.app.ui.pembelian.checkout.CheckoutPembelianViewModel;
import com.tatabuku.app.util.StringHelper;

public class PenerimaanBarangListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private PenerimaanBarangViewModel viewModel;
    private Context context;


    public PenerimaanBarangListAdapter(PenerimaanBarangViewModel viewModel, Context context) {
        this.viewModel = viewModel;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PenerimaanBarangListAdapter.ViewHolder(PenerimaanListItemBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder vh = (ViewHolder) holder;
            PenerimaanBarangItem order = viewModel.getListItem().getValue().get(position);

            vh.binding.qty.removeTextChangedListener(vh.qtyWatcher);
            vh.viewModel = viewModel;
            vh.order = order;
            vh.binding.qty.addTextChangedListener(vh.qtyWatcher);

            vh.binding.name.setText(order.getProductName());
            vh.binding.price.setText(StringHelper.numberFormat(order.getPriceUnit()));
            vh.binding.qty.setText(order.getQuantityDone().intValue() + "");
            vh.binding.maxQty.setText("x " + order.getProductUomQty().intValue() + " Unit");
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
                        order.setQuantityDone(order.getQuantityDone() + 1);
                        vh.binding.qty.setText(order.getQuantityDone().intValue() + "");
                        viewModel.getListItem().setValue(viewModel.getListItem().getValue());
                    }
                }
            });

            vh.binding.minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (order.getQuantityDone() > 0) {
                        order.setQuantityDone(order.getQuantityDone() - 1);
                        vh.binding.qty.setText(order.getQuantityDone().intValue() + "");
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

        private PenerimaanBarangViewModel viewModel;
        private PenerimaanBarangItem order;

        private TextWatcher qtyWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                binding.qty.removeTextChangedListener(this);
                if (s.toString().equalsIgnoreCase("")) {
                    order.setQuantityDone(0.0);
                    binding.qty.setText(order.getQuantityDone().intValue() + "");
                    binding.qty.setSelection(binding.qty.getText().length());
                } else {
                    Double parsed = Double.parseDouble(s.toString());
                    if(s.toString().startsWith("0")){
                        binding.qty.setText(parsed.intValue() + "");
                        binding.qty.setSelection(binding.qty.getText().length());
                    }
                    if (parsed > order.getProductUomQty()){
                        parsed = order.getProductUomQty();
                        binding.qty.setText(parsed.intValue() + "");
                        binding.qty.setSelection(binding.qty.getText().length());
                    }
                    order.setQuantityDone(parsed);
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