package com.tatabuku.app.ui.fix_asset.list_asset;

import android.content.Context;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.tatabuku.app.databinding.ListAssetItemBinding;
import com.tatabuku.app.model.fixed_asset.ListAssetModel;
import com.tatabuku.app.ui.fix_asset.dashboard.DashboardFixAssetListener;
import com.tatabuku.app.ui.pembelian.checkout.CheckoutPembelianActivity;
import com.tatabuku.app.util.ImageHelper;
import com.tatabuku.app.util.StringHelper;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListAssetAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ListAssetViewModel viewModel;
    private ListAssetListener listener;
    private Context context;

    public void setListener(ListAssetListener listener) {
        this.listener = listener;
    }

    public ListAssetAdapter(Context context,ListAssetViewModel viewModel){
        this.context = context;
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public ListAssetAdapter.ListAssetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListAssetItemBinding binding = ListAssetItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ListAssetAdapter.ListAssetViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ListAssetViewHolder){
            ListAssetViewHolder vh = (ListAssetViewHolder) holder;
            ListAssetModel asset = viewModel.getListAssetModels().getValue().get(position);
            vh.setData(asset);

            vh.binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClickItem(asset);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return viewModel.getListAssetModels().getValue().size();
    }

    private class ListAssetViewHolder extends RecyclerView.ViewHolder{
        private ListAssetItemBinding binding;

        public ListAssetViewHolder (ListAssetItemBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setData(ListAssetModel asset) {
            binding.merkAsset.setText(asset.getAssetName());
            binding.tahunAsset.setText(asset.getTahun());
            binding.kodeAsset.setText(asset.getNumber());

            binding.fotoAsset.setImageResource(0);
            binding.iconAsset.setImageResource(0);

            try {
                Glide.with(context).asBitmap()
                        .load(Base64.decode(asset.getImage(), Base64.DEFAULT))
                        .into(binding.fotoAsset);
            } catch (Exception e) {

            }

            if (asset.getNilaiPerolehan() > 0 ) {
                binding.hargabeliAsset.setText(StringHelper.numberFormat(asset.getNilaiPerolehan()));
            } else {
                binding.hargabeliAsset.setText("-");
            }

            if (asset.getDepresiasi() > 0 ) {
                binding.depresiasiAsset.setText(StringHelper.numberFormat(asset.getDepresiasi()));
            } else {
                binding.depresiasiAsset.setText("-");
            }

            if (asset.getNilaiBuku() > 0 ) {
                binding.nilaibukuAsset.setText(StringHelper.numberFormat(asset.getNilaiBuku()));
            } else {
                binding.nilaibukuAsset.setText("-");
            }
        }
    }
}
