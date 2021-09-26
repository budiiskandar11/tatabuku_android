package com.tatabuku.app.ui.rekening.catat_biaya;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tatabuku.app.R;
import com.tatabuku.app.databinding.CatatBiayaListItemBinding;
import com.tatabuku.app.model.rekening.CatatBiayaList;
import com.tatabuku.app.util.StringHelper;

import org.w3c.dom.Text;

public class CatatBiayaListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private CatatBiayaViewModel viewModel;
    private CatatBiayaListener listener;
    private Context context;

    public void setListener(CatatBiayaListener listener) {
        this.listener = listener;
    }

    public CatatBiayaListAdapter(Context context, CatatBiayaViewModel viewModel) {
        this.viewModel = viewModel;
        this.context = context;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CatatBiayaListItemBinding binding = CatatBiayaListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CatatBiayaListAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder vh = (ViewHolder) holder;
            CatatBiayaList biaya = viewModel.getItem(position);

            vh.setData(biaya);
            vh.setIndicator(position);
            vh.setListener(listener);
        }
    }

    @Override
    public int getItemCount() {
        return viewModel.getBiayaList().getValue().size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        private CatatBiayaListItemBinding binding;
        private CatatBiayaListener vhListener;
        private CatatBiayaList biaya;
        private String currentNominal;

        private TextWatcher amountTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                binding.checkbox.setChecked(true);

                if (!s.toString().equals(currentNominal)) {
                    binding.jumlahBiaya.removeTextChangedListener(amountTextWatcher);
                    String cleanString = s.toString().replaceAll("[Rp,.]", "").replaceAll(" ", "").trim();
                    if (!cleanString.equals("")) {
                        double parsed = Double.parseDouble(cleanString);
                        String formatted = StringHelper.numberFormat(parsed);

                        currentNominal = formatted;
                        binding.jumlahBiaya.setText(formatted);
                        binding.jumlahBiaya.setSelection(formatted.length());
                    } else {
                        currentNominal = "";
                        binding.jumlahBiaya.setText("");
                    }
                    binding.jumlahBiaya.addTextChangedListener(amountTextWatcher);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String strAmount = binding.jumlahBiaya.getText().toString().replaceAll("[Rp,.]","").trim();

                strAmount = strAmount.isEmpty() ? "0" : strAmount;

                double amount = Double.valueOf(strAmount);
                biaya.setAmount(amount);
                if (biaya.getAmount() <= 0) {
                    binding.checkbox.setChecked(false);
                }

                vhListener.onItemChanged(biaya);
            }
        };

        private TextWatcher descTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String description = binding.textCatatan.getText().toString();
                biaya.setDescription(description);
                vhListener.onItemChanged(biaya);
            }
        };

        public ViewHolder(CatatBiayaListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setListener(CatatBiayaListener listener) {
            vhListener = listener;
            binding.jumlahBiaya.addTextChangedListener(amountTextWatcher);
            binding.textCatatan.addTextChangedListener(descTextWatcher);

            binding.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        vhListener.onItemChanged(biaya);
                    } else {
                        vhListener.onItemUnchecked(biaya);
                    }
                }
            });
        }

        public void setData(CatatBiayaList biaya) {
            this.biaya = biaya;
            binding.jumlahBiaya.removeTextChangedListener(amountTextWatcher);
            binding.textCatatan.removeTextChangedListener(descTextWatcher);
            binding.tipeBiaya.setText(biaya.getAccountName());
            binding.jumlahBiaya.setText(biaya.getAmount() == null ? "" : String.valueOf(biaya.getAmount().intValue()));
            binding.textCatatan.setText(biaya.getDescription() == null ? "" : biaya.getDescription());
            binding.checkbox.setChecked(biaya.getAmount() != null && biaya.getAmount() > 0);
            binding.jumlahBiaya.setInputType(InputType.TYPE_CLASS_NUMBER);
        }

        @SuppressLint("ResourceAsColor")
        public void setIndicator(int position) {
            int mod = position % 4;
            Drawable color;
            switch (mod) {
                case 0:
                    color = context.getResources().getDrawable(R.color.red);
                    break;
                case 1:
                    color = context.getResources().getDrawable(R.color.green);
                    break;
                case 2:
                    color = context.getResources().getDrawable(R.color.blue);
                    break;
                case 3:
                    color = context.getResources().getDrawable(R.color.orange);
                    break;
                default:
                    color = context.getResources().getDrawable(R.color.blue);
                    break;
            }
//            binding.topIndicator.setBackground(color);
//            binding.bottomIndicator.setBackground(color);
        }
    }
}
