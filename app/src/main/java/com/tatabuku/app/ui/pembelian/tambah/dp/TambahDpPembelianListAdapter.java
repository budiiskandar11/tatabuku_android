package com.tatabuku.app.ui.pembelian.tambah.dp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tatabuku.app.databinding.RekeningCheckListItemBinding;
import com.tatabuku.app.model.pembelian.RekeningBank;
import com.tatabuku.app.util.StringHelper;


public class TambahDpPembelianListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private TambahDpPembelianViewModel viewModel;
    private Context context;

    public TambahDpPembelianListAdapter(TambahDpPembelianViewModel viewModel, Context context) {
        this.viewModel = viewModel;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TambahDpPembelianListAdapter.ViewHolder(RekeningCheckListItemBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder vh = (ViewHolder) holder;
            RekeningBank rekeningBank = viewModel.getRekeningList().getValue().get(position);

            if(rekeningBank.equals(viewModel.getSelectedBank().getValue())) {
                vh.binding.checkbox.setChecked(true);
            } else {
                vh.binding.checkbox.setChecked(false);
            }

            vh.binding.name.setText(rekeningBank.getName());
            vh.binding.value.setText(StringHelper.numberFormat(rekeningBank.getBalance().doubleValue()));

            vh.binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (viewModel.getIsEdit().getValue()) {
                        viewModel.getSelectedBank().setValue(rekeningBank);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return viewModel.getRekeningList().getValue().size();
    }


    private static class ViewHolder extends RecyclerView.ViewHolder {
        private RekeningCheckListItemBinding binding;

        public ViewHolder(RekeningCheckListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}