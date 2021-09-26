package com.tatabuku.app.ui.rekening.rincian_biaya;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tatabuku.app.databinding.RincianBiayaListItemBinding;
import com.tatabuku.app.model.rekening.CatatBiayaList;
import com.tatabuku.app.model.rekening.PostedBiayaList;
import com.tatabuku.app.util.StringHelper;

public class RincianBiayaListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private RincianBiayaViewModel viewModel;

    public RincianBiayaListAdapter(RincianBiayaViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RincianBiayaListItemBinding binding = RincianBiayaListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new RincianBiayaListAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder vh = (ViewHolder) holder;

            if (viewModel.getPageType() == RincianBiayaViewModel.PageType.CATAT_BIAYA
                    || viewModel.getPageType() == RincianBiayaViewModel.PageType.EDIT_BIAYA) {
                CatatBiayaList biaya = viewModel.getCatatBiayaList().getValue().get(position);
                vh.setCatatBiayaData(biaya);
            } else {
                PostedBiayaList biaya = viewModel.getBiayaList().getValue().get(position);
                vh.setPostedBiayaData(biaya);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (viewModel.getPageType() == RincianBiayaViewModel.PageType.CATAT_BIAYA
                || viewModel.getPageType() == RincianBiayaViewModel.PageType.EDIT_BIAYA) {
            return viewModel.getCatatBiayaList().getValue().size();
        }

        return viewModel.getBiayaList().getValue().size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        private RincianBiayaListItemBinding binding;

        public ViewHolder(RincianBiayaListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setPostedBiayaData(PostedBiayaList biaya) {
            binding.tipeBiaya.setText(biaya.getJudul());
            binding.jumlahBiaya.setText(StringHelper.numberFormatWithDecimal(biaya.getAmount()));
            binding.textCatatan.setText(biaya.getDescription());

        }

        public void setCatatBiayaData(CatatBiayaList biaya) {
            binding.tipeBiaya.setText(biaya.getAccountName());
            binding.jumlahBiaya.setText(StringHelper.numberFormatWithDecimal(biaya.getAmount().doubleValue()));
            binding.textCatatan.setText(biaya.getDescription());
        }
    }
}
