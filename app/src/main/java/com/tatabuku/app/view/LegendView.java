package com.tatabuku.app.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.tatabuku.app.R;
import com.tatabuku.app.databinding.HomeMenuItemBinding;
import com.tatabuku.app.databinding.LegendItemBinding;

public class LegendView extends LinearLayout {

    private String text;
    private String percent;
    private int color;

    private LegendItemBinding binding;

    public LegendView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setupView(context);
        configureView(context, attrs);
    }

    private void setupView(Context context) {
        binding = LegendItemBinding.inflate(LayoutInflater.from(context), this, true);
    }

    private void configureView(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.LegendView,
                0, 0);

        try {
            text = a.getText(R.styleable.LegendView_legendViewText).toString();
            percent = a.getText(R.styleable.LegendView_legendViewPercent).toString();
            color = a.getResourceId(R.styleable.LegendView_legendViewColor, 0);

            binding.name.setText(text);
            binding.percent.setText(percent);
            binding.color.setBackgroundColor(color);

        } catch (Exception e){

        } finally {
            a.recycle();
        }
    }

    public void setText(String text) {
        binding.name.setText(text);
    }

    public void setPercent(String percent) {
        binding.percent.setText(percent);
    }

    public void setColor(int color) {
        binding.color.setBackgroundColor(color);
    }
}
