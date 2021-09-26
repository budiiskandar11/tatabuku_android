package com.tatabuku.app.ui.rekening.detail;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tatabuku.app.R;
import com.tatabuku.app.databinding.RekeningDetailHeaderListItemBinding;
import com.tatabuku.app.databinding.RekeningDetailListItemBinding;
import com.tatabuku.app.model.rekening.RekeningDetailTransaction;
import com.tatabuku.app.ui.rekening.dashboard.DashboardRekeningListener;
import com.tatabuku.app.util.StringHelper;

import static com.tatabuku.app.R.color.accounting_dark_blue;
import static com.tatabuku.app.R.color.black;
import static com.tatabuku.app.R.color.blue;
import static com.tatabuku.app.R.color.green;
import static com.tatabuku.app.R.color.hijau_tatabuku;
import static com.tatabuku.app.R.color.orange;
import static com.tatabuku.app.R.color.red;
import static com.tatabuku.app.R.color.yellow;

public class RekeningDetailListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private RekeningDetailListener listener;
    private RekeningDetailViewModel viewModel;

    public void setListener(RekeningDetailListener listener) {
        this.listener = listener;
    }

    public RekeningDetailListAdapter(Context context, RekeningDetailViewModel viewModel) {
        this.viewModel = viewModel;
        this.context = context;
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case 1:
                RekeningDetailHeaderListItemBinding headerBinding = RekeningDetailHeaderListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
                return new RekeningDetailListAdapter.HeaderViewHolder(headerBinding);
            default:
                RekeningDetailListItemBinding binding = RekeningDetailListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
                return  new RekeningDetailListAdapter.ViewHolder(binding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder vh = (ViewHolder) holder;
            RekeningDetailTransaction transaction = viewModel.getTransaction(position);
            vh.setData(transaction);

            vh.binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (transaction != null) {
                        listener.onItemClick(transaction);
                    }
                }
            });
        } else if (holder instanceof HeaderViewHolder) {
            HeaderViewHolder vh = (HeaderViewHolder) holder;
            String date = viewModel.getHeaderDate(position);

            String type = "middle";

            if (date.equals(viewModel.getHeaderDates().get(0))) {
                type = "top";
            } else if (date.equals(viewModel.getHeaderDates().get(viewModel.getHeaderDates().size() - 1))) {
                type = "bottom";
            }

            vh.setData(date, type);
        }
    }

    @Override
    public int getItemCount() {
        return viewModel.getTotalListSize();
    }

    @Override
    public int getItemViewType(int position) {
        Boolean isTransactionCell = viewModel.checkTransactionCell(position);
        return isTransactionCell ? 0 : 1;
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        private RekeningDetailListItemBinding binding;

        public ViewHolder(RekeningDetailListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @SuppressLint("ResourceAsColor")
        public void setData(RekeningDetailTransaction transaction) {
            binding.transactionAmount.setText(StringHelper.numberFormatWithDecimal(transaction.getAmount()));
            binding.transactionNumber.setText("Voucher : " + transaction.getVoucherNo());
//            binding.transactionType.setText(transaction.getStatus());
            binding.transactionDescription.setText(transaction.getDeskripsi());
            binding.transactionName.setText(transaction.getPartnerId());

//            switch (transaction.getStatus()) {
//                case "Uang Keluar":
//                    binding.viewBorder.setBackgroundColor(orange);
//                    break;
//                case "Uang Masuk":
//                    binding.viewBorder.setBackgroundColor(blue);
//                    break;
//                case "Pindah Buku":
//                    binding.viewBorder.setBackgroundColor(green);
//                    break;
//                default:
//                    binding.viewBorder.setBackgroundColor(blue);
//                    break;
//            }
            switch (transaction.getStatus()) {
                case "Uang Keluar":
                    binding.transactionType.setText(transaction.getStatus());
                    binding.transactionType.setTextColor(orange);
                    break;
                case "Uang Masuk":
                    binding.transactionType.setText(transaction.getStatus());
                    binding.transactionType.setTextColor(hijau_tatabuku);
                    break;
                case "Pindah Buku":
                    binding.transactionType.setText(transaction.getStatus());
                    binding.transactionType.setTextColor(black);
                    break;
                default:
                    binding.transactionType.setTextColor(hijau_tatabuku);
                    break;
            }
        }
    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder {
        private RekeningDetailHeaderListItemBinding binding;

        public HeaderViewHolder(RekeningDetailHeaderListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setData(String date, String type) {
            binding.date.setText(StringHelper.formatRekeningDate(date));

            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) binding.centerLine.getLayoutParams();
            final float scale = context.getResources().getDisplayMetrics().density;
            int left, top, right, bottom, height;

            switch (type) {
                case "top":
                    left = (int) (21.5 * scale + 0.5f);
                    top = (int) (22 * scale + 0.5f);
                    right = (int) (0 * scale + 0.5f);
                    bottom = (int) (0 * scale + 0.5f);
                    height = (int) (22 * scale + 0.5f);
                    params.setMargins(left, top, right, bottom);
                    params.height = height;
                    binding.centerLine.setLayoutParams(params);
                    break;
                case "middle":
                    left = (int) (21.5 * scale + 0.5f);
                    top = (int) (0 * scale + 0.5f);
                    right = (int) (0 * scale + 0.5f);
                    bottom = (int) (0 * scale + 0.5f);
                    height = (int) (44 * scale + 0.5f);
                    params.setMargins(left, top, right, bottom);
                    params.height = height;
                    binding.centerLine.setLayoutParams(params);
                    break;
                case "bottom":
                    left = (int) (21.5 * scale + 0.5f);
                    top = (int) (0 * scale + 0.5f);
                    right = (int) (0 * scale + 0.5f);
                    bottom = (int) (22 * scale + 0.5f);
                    height = (int) (22 * scale + 0.5f);
                    params.setMargins(left, top, right, bottom);
                    params.height = height;
                    binding.centerLine.setLayoutParams(params);
                    break;
                default:
                    break;
            }
        }
    }
}
