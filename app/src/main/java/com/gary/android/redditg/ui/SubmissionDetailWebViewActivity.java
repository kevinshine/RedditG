package com.gary.android.redditg.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.gary.android.redditg.Constants;
import com.gary.android.redditg.R;

public class SubmissionDetailWebViewActivity extends Activity {
    private static final String TAG = SubmissionDetailWebViewActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submission_detail_web_view);

        String url = getIntent().getStringExtra(Constants.EXTRA_SUBMISSION_URL);
        Log.d(TAG, "url:" + url);

        WebView webView = findViewById(R.id.wv_detail);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setDefaultTextEncodingName("utf-8");

        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

}
