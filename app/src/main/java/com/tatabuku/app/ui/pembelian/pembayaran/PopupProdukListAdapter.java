package com.tatabuku.app.ui.pembelian.pembayaran;

import android.content.Context;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tatabuku.app.R;
import com.tatabuku.app.databinding.PopupOrderListItemBinding;
import com.tatabuku.app.model.pembelian.InvoiceLineDetail;
import com.tatabuku.app.util.StringHelper;

public class PopupProdukListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private PembayaranViewModel viewModel;
    private Context context;

    public PopupProdukListAdapter(PembayaranViewModel viewModel, Context context) {
        this.viewModel = viewModel;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PopupProdukListAdapter.ViewHolder(PopupOrderListItemBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder vh = (ViewHolder) holder;
            InvoiceLineDetail item = viewModel.getItemList().getValue().get(position);

            vh.binding.name.setText(item.getItem());
            vh.binding.maxQty.setText(String.format(context.getString(R.string.x_n_unit),item.getQty() + ""));
            vh.binding.price.setText(StringHelper.numberFormat(item.getPriceTotal()));
            vh.binding.image.setImageResource(R.drawable.no_image);

            try {
                Glide.with(context).asBitmap()
                        .load(Base64.decode(item.getImage(), Base64.DEFAULT))
                        .into(vh.binding.image);
            }catch (Exception e){

            }
        }
    }

    @Override
    public int getItemCount() {
        return viewModel.getItemList().getValue().size();
    }


    private static class ViewHolder extends RecyclerView.ViewHolder {
        private PopupOrderListItemBinding binding;

        public ViewHolder(PopupOrderListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}