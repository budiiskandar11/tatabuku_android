package com.tatabuku.app.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.bumptech.glide.Glide;
import com.tatabuku.app.R;
import com.tatabuku.app.databinding.HomeMenuItemBinding;

public class HomeMenuView extends FrameLayout {

    private String text;
    private Integer image;

    private HomeMenuItemBinding binding;

    public HomeMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setupView(context);
        configureView(context, attrs);
    }

    private void setupView(Context context) {
        binding = HomeMenuItemBinding.inflate(LayoutInflater.from(context), this, true);
    }

    private void configureView(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.HomeMenu,
                0, 0);

        try {
            text = a.getText(R.styleable.HomeMenu_homeMenuText).toString();
            image = a.getResourceId(R.styleable.HomeMenu_homeMenuImage, 0);

            binding.label.setText(text);
            Glide.with(this).load(image).into(binding.image);

        } finally {
            a.recycle();
        }
    }
}
