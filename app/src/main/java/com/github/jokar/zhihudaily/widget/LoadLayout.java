package com.github.jokar.zhihudaily.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.jokar.zhihudaily.R;

/**
 * Created by JokAr on 2017/7/2.
 */

public class LoadLayout extends LinearLayout {

    ProgressBar progressBar;
    LinearLayout llContainer;
    ImageView imageError;
    TextView tvErrorInfo;
    TextView btnRetry;

    public RetryListener retryListener;

    public LoadLayout(Context context) {
        this(context, null);
    }

    public LoadLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_load, this, true);
        setOrientation(LinearLayout.VERTICAL);
        setGravity(Gravity.CENTER);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        llContainer = (LinearLayout) findViewById(R.id.llContainer);
        imageError = (ImageView) findViewById(R.id.imageError);
        tvErrorInfo = (TextView) findViewById(R.id.tvErrorInfo);
        btnRetry = (TextView) findViewById(R.id.btnRetry);

        btnRetry.setOnClickListener(v -> {
            if (retryListener != null) {
                retryListener.onRetry();
            }
        });

        showLoad();
    }


    public void showLoad() {
        llContainer.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    public void showError(String error) {
        progressBar.setVisibility(View.GONE);
        llContainer.setVisibility(View.VISIBLE);
        tvErrorInfo.setText(error);
    }

    public interface RetryListener {
        void onRetry();
    }
}
