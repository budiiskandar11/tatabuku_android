package com.tatabuku.app.ui.accounting.detail;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tatabuku.app.databinding.DetailAccountingListItemBinding;

public class DetailAccountingListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DetailAccountingListItemBinding binding = DetailAccountingListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new DetailAccountingListAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        private DetailAccountingListItemBinding binding;

        public ViewHolder(DetailAccountingListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
