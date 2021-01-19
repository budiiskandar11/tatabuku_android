package com.tatabuku.app.ui.penjualan.pembayaran;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tatabuku.app.R;
import com.tatabuku.app.databinding.PembayaranListItemBinding;
import com.tatabuku.app.model.pembelian.RekeningBank;
import com.tatabuku.app.ui.pembelian.pembayaran.PembayaranListener;
import com.tatabuku.app.ui.pembelian.pembayaran.PembayaranViewModel;
import com.tatabuku.app.util.StringHelper;

public class PembayaranCustomerListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private PembayaranCustomerViewModel viewModel;
    private Context context;
    private PembayaranListener listener;

    public void setListener(PembayaranListener listener) {
        this.listener = listener;
    }

    public PembayaranCustomerListAdapter(PembayaranCustomerViewModel viewModel, Context context) {
        this.viewModel = viewModel;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PembayaranCustomerListAdapter.ViewHolder(PembayaranListItemBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder vh = (ViewHolder) holder;
            RekeningBank bank = viewModel.getBankList().getValue().get(position);

            vh.binding.checkbox.setOnCheckedChangeListener(null);
            vh.binding.value.removeTextChangedListener(vh.textWatcher);
            vh.viewModel = viewModel;
            vh.bank = bank;
            vh.listener = listener;


            vh.binding.name.setText(bank.getName());

            if (viewModel.getIsEdit().getValue() || viewModel.getIsView().getValue()) {
                vh.binding.saldo.setText("");
                vh.binding.value.setText(StringHelper.numberFormatWithDecimal(bank.getAmount()));
                vh.binding.checkbox.setChecked(bank.getChecked());
                vh.binding.checkbox.setEnabled(false);
                vh.binding.value.setEnabled(false);
            } else {
                vh.binding.saldo.setText(String.format(context.getString(R.string.saldo_n), StringHelper.numberFormat(bank.getBalance().doubleValue())));
                vh.binding.value.setText(StringHelper.numberFormatWithDecimal(bank.getValue()));
                vh.binding.checkbox.setChecked(bank.getChecked());
                vh.binding.checkbox.setEnabled(true);
                vh.binding.value.setEnabled(true);
            }

            vh.binding.checkbox.setOnCheckedChangeListener(vh.checkedListener);
            vh.binding.value.addTextChangedListener(vh.textWatcher);

            vh.binding.value.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    vh.binding.value.setSelection(vh.binding.value.getText().length());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return viewModel.getBankList().getValue().size();
    }


    private static class ViewHolder extends RecyclerView.ViewHolder {
        private PembayaranListItemBinding binding;

        private PembayaranCustomerViewModel viewModel;
        private RekeningBank bank;
        private PembayaranListener listener;

        private CompoundButton.OnCheckedChangeListener checkedListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                binding.checkbox.setOnCheckedChangeListener(null);
                if (b && bank.getId() != 0) {
                    for (int i = 0; i < viewModel.getBankList().getValue().size(); i++) {
                        RekeningBank rb = viewModel.getBankList().getValue().get(i);
                        if (rb.getId() != 0) {
                            rb.setChecked(false);
                        }
                    }
                }

                bank.setChecked(b);
                viewModel.getBankList().setValue(viewModel.getBankList().getValue());
                binding.checkbox.setOnCheckedChangeListener(this);
                if (listener != null) {
                    listener.onPembayaranChanged();
                }

            }
        };

        private TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {

                binding.value.removeTextChangedListener(this);
                String cleanString = s.toString().replaceAll("[Rp.,]", "").replaceAll(" ", "").trim();
                if (!cleanString.equals("")) {
                    double parsed = Double.parseDouble(cleanString) / 100;
                    String formatted = StringHelper.numberFormatWithDecimal(parsed);

                    binding.value.setText(formatted);
                    binding.value.setSelection(formatted.length());
                    bank.setValue(parsed);
                } else {
                    bank.setValue(0.0);
                    binding.value.setText("Rp");
                }
                binding.value.addTextChangedListener(this);
                viewModel.getBankList().setValue(viewModel.getBankList().getValue());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };

        public ViewHolder(PembayaranListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}