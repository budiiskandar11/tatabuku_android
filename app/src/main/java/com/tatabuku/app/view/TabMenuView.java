package com.tatabuku.app.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.tatabuku.app.R;
import com.tatabuku.app.databinding.TabMenuItemBinding;

public class TabMenuView extends FrameLayout {

    private String text;
    private Integer image;
    private Context context;

    private TabMenuItemBinding binding;

    public TabMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context = context;

        setupView(context);
        configureView(context, attrs);
    }

    private void setupView(Context context) {
        binding = TabMenuItemBinding.inflate(LayoutInflater.from(context), this, true);
    }

    private void configureView(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.TabMenu,
                0, 0);

        try {
            text = a.getText(R.styleable.TabMenu_tabMenuText).toString();
            image = a.getResourceId(R.styleable.TabMenu_tabMenuImage, 0);

            binding.label.setText(text);
            Glide.with(this).load(image).into(binding.image);

        } finally {
            a.recycle();
        }
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        if (selected) {
            binding.label.setTextColor(ContextCompat.getColor(context, R.color.orange));
            binding.image.setColorFilter(ContextCompat.getColor(context, R.color.orange), PorterDuff.Mode.SRC_ATOP);
        } else {
            binding.label.setTextColor(ContextCompat.getColor(context, R.color.hijau_tatabuku));
            binding.image.setColorFilter(ContextCompat.getColor(context, R.color.hijau_tatabuku), PorterDuff.Mode.SRC_ATOP);
//            binding.image.setColorFilter(null);
        }
    }

}
