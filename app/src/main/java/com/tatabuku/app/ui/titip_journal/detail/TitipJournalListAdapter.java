package com.tatabuku.app.ui.titip_journal.detail;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.tatabuku.app.databinding.TitipJournalTransaksiItemBinding;
import com.tatabuku.app.model.titip_jurnal.DetailTitipJurnalModel;
import com.tatabuku.app.util.StringHelper;


public class TitipJournalListAdapter extends RecyclerView.Adapter<TitipJournalListAdapter.TitipViewHolder> {

    private TitipJurnalViewModel viewModel;

    public TitipJournalListAdapter(TitipJurnalViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public TitipViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        TitipJournalTransaksiItemBinding binding = TitipJournalTransaksiItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new TitipViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull TitipViewHolder holder, int position) {
        holder.configureData(viewModel.getJurnalList().getValue().get(position));

    }

    @Override
    public int getItemCount() {
        return viewModel.getJurnalList().getValue().size();
    }

    public class TitipViewHolder extends RecyclerView.ViewHolder{

        private TitipJournalTransaksiItemBinding binding;

        public TitipViewHolder(TitipJournalTransaksiItemBinding binding){
            super(binding.getRoot());

            this.binding = binding;
        }

        public void configureData(DetailTitipJurnalModel model) {
            binding.titipKeterangan.setText(model.getDeskripsi());
            binding.titipTanggal.setText(StringHelper.formatDueDate(model.getTanggal()));
            binding.titipNominal.setText(StringHelper.numberFormatWithDecimal(model.getNilai()));
//            binding.titipSeri.
        }
    }
}
