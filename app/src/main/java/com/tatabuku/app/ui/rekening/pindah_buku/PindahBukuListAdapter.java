package com.tatabuku.app.ui.rekening.pindah_buku;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tatabuku.app.R;
import com.tatabuku.app.databinding.PindahBukuListItemBinding;
import com.tatabuku.app.model.rekening.PindahBukuBank;
import com.tatabuku.app.util.StringHelper;

public class PindahBukuListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private PindahBukuViewModel viewModel;
    private PindahBukuListener listener;

    public void setListener(PindahBukuListener listener) {
        this.listener = listener;
    }

    public PindahBukuListAdapter(PindahBukuViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PindahBukuListItemBinding binding = PindahBukuListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new PindahBukuListAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder vh = (ViewHolder) holder;
            PindahBukuBank bank = viewModel.getBanks().getValue().get(position);
            PindahBukuBank selectedBank = viewModel.getToBank().getValue();

            vh.setData(bank);
            vh.setListener(listener);

            if (selectedBank != null) {
                vh.setSelected(bank.getId() == selectedBank.getId());
            }
        }
    }

    @Override
    public int getItemCount() {
        return viewModel.getBanks().getValue().size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        private PindahBukuListItemBinding binding;
        private PindahBukuBank bank;

        public ViewHolder(PindahBukuListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setSelected(Boolean isSelected) {
            binding.toggleSelectedIcon.setChecked(isSelected);
        }

        public void setData(PindahBukuBank bank) {
            this.bank = bank;
            String name = bank.getName();
            binding.bankType.setText(name);
            binding.bankCurrentAmount.setText(StringHelper.numberFormatWithDecimal(bank.getSaldo()));

            switch (name) {
                case "Mandiri":
                    binding.backgroundImage.setImageResource(R.drawable.background_orange);
                    break;
                case "BCA":
                    binding.backgroundImage.setImageResource(R.drawable.background_green);
                    break;
                case "BRI":
                    binding.backgroundImage.setImageResource(R.drawable.background_red);
                    break;
                default:
                    binding.backgroundImage.setImageResource(R.drawable.background_blue);
                    break;
            }
        }

        public void setListener (PindahBukuListener listener) {
            binding.toggleSelectedIcon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        listener.onItemClickListener(bank);
                    }
                }
            });
        }
    }
}
