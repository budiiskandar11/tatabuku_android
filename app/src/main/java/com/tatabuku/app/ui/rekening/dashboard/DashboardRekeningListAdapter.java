package com.tatabuku.app.ui.rekening.dashboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tatabuku.app.R;
import com.tatabuku.app.databinding.DashboardRekeningListItemBinding;
import com.tatabuku.app.model.rekening.DashboardRekeningModel;
import com.tatabuku.app.util.StringHelper;

public class DashboardRekeningListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private DashboardRekeningListener listener;
    private DashboardRekeningViewModel viewModel;

    public void setListener(DashboardRekeningListener listener) {
        this.listener = listener;
    }

    public DashboardRekeningListAdapter (DashboardRekeningViewModel viewModel) {
        this.viewModel = viewModel;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DashboardRekeningListItemBinding binding = DashboardRekeningListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new DashboardRekeningListAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder vh = (ViewHolder) holder;

            DashboardRekeningModel rekening = viewModel.getRekenings().getValue().get(position);

            vh.setData(rekening);

            vh.binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(rekening);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return viewModel.getRekenings().getValue().size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        private DashboardRekeningListItemBinding binding;

        public ViewHolder(DashboardRekeningListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setData(DashboardRekeningModel rekening) {
            binding.rekeningType.setText(rekening.getName());
            binding.balanceAmount.setText(StringHelper.numberFormatWithDecimal(rekening.getSaldo()));
            binding.cashInAmount.setText(StringHelper.numberFormatWithDecimal(rekening.getMasuk()));
            binding.cashOutAmount.setText(StringHelper.numberFormatWithDecimal(rekening.getKeluar()));

            switch (rekening.getName()) {
                case "Kas":
                    binding.bgImage.setImageResource(R.drawable.background_blue);
                    binding.bgImage2.setImageResource(R.drawable.kas);
                    break;
                case "Bank Mandiri":
                    binding.bgImage.setImageResource(R.drawable.background_orange);
                    binding.bgImage2.setImageResource(R.drawable.mandiri);
                    break;
                case "Bank BCA":
                    binding.bgImage.setImageResource(R.drawable.background_blue);
                    binding.bgImage2.setImageResource(R.drawable.bca);
                    break;
                case "BRI":
                    binding.bgImage.setImageResource(R.drawable.background_red);
                    binding.bgImage2.setImageResource(R.drawable.bri);
                    break;
                case "BNI":
                    binding.bgImage.setImageResource(R.drawable.background_green);
                    binding.bgImage2.setImageResource(R.drawable.bni);
                    break;
                default:
                    binding.bgImage.setImageResource(R.drawable.background_blue);
                    binding.bgImage2.setImageResource(R.drawable.kas);
                    break;
            }
        }
    }
}
