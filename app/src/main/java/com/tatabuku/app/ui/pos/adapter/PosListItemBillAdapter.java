package com.tatabuku.app.ui.pos.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tatabuku.app.R;
import com.tatabuku.app.databinding.AdapterItemBillBinding;
import com.tatabuku.app.model.pos.OrderBill;
import com.tatabuku.app.ui.pos.listener.PosListener;
import com.tatabuku.app.util.StringHelper;

import java.util.ArrayList;

public class PosListItemBillAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
    private PosListener listener;
    private ArrayList<OrderBill> list;

    public void setListener(PosListener listener) {
        this.listener = listener;
    }

    public PosListItemBillAdapter(Context context) {
        this.context = context;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setList(ArrayList<OrderBill> list){
        this.list = list;
        notifyDataSetChanged();
    }

    public ArrayList<OrderBill> getList() {
        return list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(AdapterItemBillBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent,false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder vh = (ViewHolder) holder;
            if (list.size() > 0){
                vh.binding.price.setText(StringHelper.numberFormat(list.get(position).getProduct_price()));
                vh.binding.name.setText(list.get(position).getName() + " ("+list.get(position).getProduct_qty()+")");
                vh.binding.image.setImageResource(R.drawable.no_image);
                try {
                    Glide.with(context).asBitmap()
                            .load(Base64.decode(list.get(position).getImage(), Base64.DEFAULT))
                            .into(vh.binding.image);
                } catch (Exception ignored) {

                }
                vh.binding.btnMin.setOnClickListener(v -> {
                    Integer qty = list.get(position).getProduct_qty() - 1;
                    vh.binding.name.setText(list.get(position).getName() + " ("+ qty +")");
                    listener.onEditOrder(list.get(position));
                    if (qty < 1){
                        list.remove(list.get(position));
                        listener.onOrderChanged();
                    }
                });
            }

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {
        private final AdapterItemBillBinding binding;

        public ViewHolder(AdapterItemBillBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
