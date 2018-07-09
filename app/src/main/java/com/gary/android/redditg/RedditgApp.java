package com.gary.android.redditg;

import android.app.Application;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ProcessLifecycleOwner;
import android.util.Log;

import com.gary.android.redditg.api.RedditApi;
import com.gary.android.redditg.db.AppDatabase;
import com.gary.android.redditg.repository.InMemoryByItemRepository;

import java.util.Collections;

import okhttp3.CipherSuite;
import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import okhttp3.TlsVersion;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.arch.lifecycle.Lifecycle.Event.*;

public class RedditgApp extends Application {
    private static final String BASE_URL = "https://www.reddit.com/";

    private static RedditgApp mInstance;
    private AppExecutors mAppExecutors;
    private InMemoryByItemRepository repository;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mAppExecutors = new AppExecutors();

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d("RedditAPI", message);
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        RedditApi redditApi = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RedditApi.class);

        repository = new InMemoryByItemRepository(redditApi);
        ProcessLifecycleOwner.get().getLifecycle().addObserver(new AppObserver());
    }

    public InMemoryByItemRepository getRepository() {
        return repository;
    }

    static class AppObserver implements LifecycleObserver {
        private String TAG = "AppObserver";

        @OnLifecycleEvent(ON_ANY)
        void onAny(LifecycleOwner owner, Lifecycle.Event event) {
            Log.i(TAG, "App goes to ON_ANY" + event.toString());
        }

        @OnLifecycleEvent(ON_START)
        void onForeground() {
            Log.i(TAG, "App goes to foreground");
        }

        @OnLifecycleEvent(ON_STOP)
        void onBackground() {
            Log.i(TAG, "App goes to background");
        }
    }

    public static RedditgApp getInstance() {
        return mInstance;
    }

    public AppDatabase getDatabase() {
        return AppDatabase.getInstance(this);
    }

    public AppExecutors getAppExecutors() {
        return mAppExecutors;
    }
}
