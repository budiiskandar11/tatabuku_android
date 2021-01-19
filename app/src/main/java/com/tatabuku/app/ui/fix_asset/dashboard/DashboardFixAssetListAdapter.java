package com.tatabuku.app.ui.fix_asset.dashboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tatabuku.app.R;
import com.tatabuku.app.databinding.DashboardFixAssetItemBinding;
import com.tatabuku.app.model.fixed_asset.DashboardAssetModel;
import com.tatabuku.app.util.StringHelper;


public class DashboardFixAssetListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private DashboardFixAssetViewModel viewModel;
    private DashboardFixAssetListener listener;

    public void setListener(DashboardFixAssetListener listener) {
        this.listener = listener;
    }

    public DashboardFixAssetListAdapter (DashboardFixAssetViewModel viewModel){
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DashboardFixAssetItemBinding binding = DashboardFixAssetItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent, false);
        return new DashboardFixAssetListAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ViewHolder) {
            ViewHolder vh = (ViewHolder) holder;
            DashboardAssetModel assetModel = viewModel.getAssetList().getValue().get(position);

            vh.binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(assetModel);
                }
            });

            vh.binding.title.setText(assetModel.getCategoryName());

            vh.binding.jumlahCounter.setText(String.format("%s Unit", assetModel.getJumlah().intValue()));
            if (assetModel.getPembelian() > 0) {
                vh.binding.nilaiPembelian.setText(StringHelper.numberFormat(assetModel.getPembelian()));
            } else {
                vh.binding.nilaiPembelian.setText("-");
            }

            if (assetModel.getDepresiasi() > 0) {
                vh.binding.nilaiDepresiasi.setText(StringHelper.numberFormat(assetModel.getDepresiasi()));
            } else {
                vh.binding.nilaiDepresiasi.setText("-");
            }

            if (assetModel.getNilaiBuku() > 0) {
                vh.binding.nilaiBuku.setText(StringHelper.numberFormat(assetModel.getNilaiBuku()));
            } else {
                vh.binding.nilaiBuku.setText("-");
            }

            switch (assetModel.getCategoryName()) {
                case "Motor":
                    vh.binding.background.setImageResource(R.drawable.background_green);
                    vh.binding.jumlahText.setText("Jumlah Motor");
                    vh.binding.iconAsset.setImageResource(R.drawable.motorcycle);
                    break;
                case "Mobil":
                    vh.binding.background.setImageResource(R.drawable.background_blue);
                    vh.binding.jumlahText.setText("Jumlah Mobil");
                    vh.binding.iconAsset.setImageResource(R.drawable.car);
                    break;
                case "Tanah":
                    vh.binding.background.setImageResource(R.drawable.background_orange);
                    vh.binding.jumlahText.setText("Jumlah Tanah");
                    vh.binding.iconAsset.setImageResource(R.drawable.ic_fixed_asset);
                    break;
                case "Bangunan":
                    vh.binding.background.setImageResource(R.drawable.background_red);
                    vh.binding.jumlahText.setText("Jumlah Bangunan");
                    vh.binding.iconAsset.setImageResource(R.drawable.house);
                    break;
                case "Perlengkapan":
                    vh.binding.background.setImageResource(R.drawable.background_red);
                    vh.binding.jumlahText.setText("Jumlah");
                    vh.binding.iconAsset.setImageResource(R.drawable.air_conditioner);
                    break;
                case "Peralatan Kantor":
                    vh.binding.background.setImageResource(R.drawable.background_green);
                    vh.binding.jumlahText.setText("Jumlah");
                    vh.binding.iconAsset.setImageResource(R.drawable.desktop);
                    break;
                default:
                    vh.binding.background.setImageResource(R.drawable.background_green);
                    vh.binding.jumlahText.setText("Jumlah");
                    vh.binding.iconAsset.setImageResource(0);
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return viewModel.getAssetList().getValue().size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        public DashboardFixAssetItemBinding binding;

        public ViewHolder(DashboardFixAssetItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
