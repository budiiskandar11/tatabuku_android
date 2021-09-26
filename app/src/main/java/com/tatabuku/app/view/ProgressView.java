package com.tatabuku.app.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.tatabuku.app.R;
import com.tatabuku.app.databinding.ProgressViewBinding;

public class ProgressView extends LinearLayout {

    private String text;
    private Drawable drawable;

    private ProgressViewBinding binding;

    public ProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setupView(context);
        configureView(context, attrs);
    }

    private void setupView(Context context) {
        binding = ProgressViewBinding.inflate(LayoutInflater.from(context), this, true);
    }

    private void configureView(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.ProgressView,
                0, 0);

        try {
            text = a.getText(R.styleable.ProgressView_progressText).toString();
            drawable = a.getDrawable(R.styleable.ProgressView_progressDrawable);

            binding.label.setText(text);
            binding.progress.setProgressDrawable(drawable);

        } finally {
            a.recycle();
        }
    }

    public void setText(String text) {
        binding.label.setText(text);
    }

    public void setProgress(int value) {
        binding.progress.setProgress(value);
    }
}
