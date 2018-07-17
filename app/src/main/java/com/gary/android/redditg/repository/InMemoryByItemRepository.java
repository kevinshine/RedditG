package com.gary.android.redditg.repository;

import android.arch.lifecycle.LiveData;
import android.arch.paging.DataSource;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.util.Log;

import com.gary.android.redditg.RedditgApp;
import com.gary.android.redditg.api.RedditApi;
import com.gary.android.redditg.model.RedditPost;
import com.google.gson.JsonArray;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InMemoryByItemRepository {
    private RedditApi mRedditApi;

    public InMemoryByItemRepository(RedditApi redditApi) {
        mRedditApi = redditApi;
    }

    public LiveData<PagedList<RedditPost>> postsOfSubreddit(String subReddit, int pageSize) {
        DataSource.Factory<String, RedditPost> sourceFactory = new DataSource.Factory<String, RedditPost>() {
            @Override
            public DataSource<String, RedditPost> create() {
                return new ItemKeyedSubredditDataSource(subReddit, mRedditApi);
            }
        };

        PagedList.Config pagedListConfig = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(pageSize * 2)
                .setPageSize(pageSize)
                .build();

        LiveData<PagedList<RedditPost>> pagedList = new LivePagedListBuilder<String, RedditPost>(sourceFactory, pagedListConfig)
                // provide custom executor for network requests, otherwise it will default to
                // Arch Components' IO pool which is also used for disk access
                .setFetchExecutor(RedditgApp.getInstance().getAppExecutors().networkIO())
                .build();

        return pagedList;
    }

    public void getPostDetail(String subReddit, String id, Callback<JsonArray> callback) {
        Call<JsonArray> call = mRedditApi.getPostDetail(subReddit, id, 30);
        call.enqueue(callback);
    }
}