package com.tatabuku.app.ui.fix_asset.detail_asset;

import android.view.ViewGroup;
import android.view.LayoutInflater;

import com.tatabuku.app.databinding.ListDepresiasiItemBinding;
import com.tatabuku.app.model.fixed_asset.DetailAssetDepresiasi;
import com.tatabuku.app.util.StringHelper;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DetailAssetAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private DetailAssetViewModel viewModel;

    public DetailAssetAdapter(DetailAssetViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public DetailAssetAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ListDepresiasiItemBinding binding = ListDepresiasiItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new DetailAssetAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder vh = (ViewHolder) holder;
            DetailAssetDepresiasi depresiasi = viewModel.getListDepresiasi().getValue().get(position);
            vh.setData(depresiasi);
        }
    }

    @Override
    public int getItemCount() {
        return viewModel.getListDepresiasi().getValue().size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder{

        private ListDepresiasiItemBinding binding;

        public ViewHolder (ListDepresiasiItemBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setData (DetailAssetDepresiasi depresiasi) {
            binding.tanggalListDepresiasi.setText(depresiasi.getTanggal());
            binding.nomorListDepresiasi.setText(depresiasi.getNomor());
            binding.nilaiListDepresiasi.setText(StringHelper.numberFormat(depresiasi.getNilaiDepresiasi()));
            binding.KeteranganListDepresiasi.setText(depresiasi.getUrut());
        }
    }
}
