package com.tatabuku.app.ui.titip_journal.detail;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.tatabuku.app.databinding.TitipJournalTransaksiItemBaruBinding;
import com.tatabuku.app.model.titip_jurnal.DetailTitipJurnalModel;
import com.tatabuku.app.util.StringHelper;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TitipJournalBaruListAdapter extends RecyclerView.Adapter<TitipJournalBaruListAdapter.TitipBaruViewHolder> {
    private TitipJurnalViewModel viewModel;

    public TitipJournalBaruListAdapter(TitipJurnalViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public TitipBaruViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        TitipJournalTransaksiItemBaruBinding binding = TitipJournalTransaksiItemBaruBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new TitipBaruViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TitipBaruViewHolder holder, int position) {
        holder.configureData(viewModel.getJurnalListbelum().getValue().get(position));
    }

    @Override
    public int getItemCount() {
        return viewModel.getJurnalListbelum().getValue().size();
    }

    public class TitipBaruViewHolder extends RecyclerView.ViewHolder{

        private TitipJournalTransaksiItemBaruBinding binding;

        public TitipBaruViewHolder(com.tatabuku.app.databinding.TitipJournalTransaksiItemBaruBinding binding){
            super(binding.getRoot());

            this.binding = binding;

        }

        public void configureData(DetailTitipJurnalModel model){
            binding.titipKeteranganBaru.setText((model.getDeskripsi()));
            binding.titipNominalBaru.setText(StringHelper.numberFormatWithDecimal(model.getNilai()));
            binding.titipTanggalBaru.setText(StringHelper.formatDueDate(model.getTanggal()));
            binding.titipTenggatWaktu.setText(StringHelper.formatDueDate(model.getDueDate()));
            binding.titipSisaWaktu.setText(model.getSisaWaktu().intValue() + " jam");
        }
    }
}
