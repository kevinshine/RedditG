package com.gary.android.redditg;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import com.gary.android.redditg.reddit.RedditManager;

import net.dean.jraw.RedditClient;
import net.dean.jraw.android.AndroidHelper;
import net.dean.jraw.android.AppInfo;
import net.dean.jraw.android.AppInfoProvider;
import net.dean.jraw.android.ManifestAppInfoProvider;
import net.dean.jraw.http.OkHttpNetworkAdapter;
import net.dean.jraw.http.UserAgent;
import net.dean.jraw.models.Submission;
import net.dean.jraw.oauth.AccountHelper;
import net.dean.jraw.oauth.Credentials;
import net.dean.jraw.oauth.OAuthHelper;
import net.dean.jraw.pagination.DefaultPaginator;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import okhttp3.OkHttpClient;

public class RedditgApp extends Application {

    private static RedditgApp mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

    }

    public static RedditgApp getInstance(){
        return mInstance;
    }
}
