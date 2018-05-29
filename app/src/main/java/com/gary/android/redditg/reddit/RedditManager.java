package com.gary.android.redditg.reddit;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.gary.android.redditg.RedditgApp;

import net.dean.jraw.RedditClient;
import net.dean.jraw.android.AppInfo;
import net.dean.jraw.android.AppInfoProvider;
import net.dean.jraw.android.ManifestAppInfoProvider;
import net.dean.jraw.http.OkHttpNetworkAdapter;
import net.dean.jraw.http.UserAgent;
import net.dean.jraw.models.Listing;
import net.dean.jraw.models.Submission;
import net.dean.jraw.oauth.Credentials;
import net.dean.jraw.oauth.OAuthHelper;
import net.dean.jraw.pagination.DefaultPaginator;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class RedditManager {
    private static final String TAG = RedditManager.class.getSimpleName();

    private volatile static RedditManager instance = null;
    private CountDownLatch mCountDownLatch = new CountDownLatch(1);

    private RedditClient mRedditClient;

    private RedditManager() {
        initRedditClient(RedditgApp.getInstance());
    }

    public static RedditManager getInstance() {
        if (instance == null) {
            synchronized (RedditManager.class) {
                if (instance == null) {
                    instance = new RedditManager();
                }
            }
        }

        return instance;
    }

    private void initRedditClient(Context context) {
        Log.d(TAG, "initRedditClient start");

        AsyncTask.THREAD_POOL_EXECUTOR.execute(()->{
            AppInfoProvider provider = new ManifestAppInfoProvider(context);
            AppInfo appInfo = provider.provide();

            OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
            UserAgent userAgent = appInfo.getUserAgent();
            OkHttpNetworkAdapter httpAdapter = new OkHttpNetworkAdapter(userAgent, okHttpClient);

            Credentials fcreds =
                    Credentials.userlessApp(appInfo.getClientId(), UUID.randomUUID());
            mRedditClient = OAuthHelper.automatic(httpAdapter, fcreds);
            mRedditClient.setRetryLimit(3);

            mCountDownLatch.countDown();
            Log.d(TAG, "initRedditClient finish");
        });
    }

    public RedditClient getRedditClient() {
        try {
            if (mCountDownLatch != null && mCountDownLatch.getCount() > 0)
                mCountDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "getRedditClient");
        return mRedditClient;
    }

    public interface RedditCallback {
        void onComplete(DefaultPaginator<Submission> paginator,Listing<Submission> submissions);
    }



}
