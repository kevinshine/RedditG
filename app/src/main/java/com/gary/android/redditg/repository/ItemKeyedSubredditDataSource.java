package com.gary.android.redditg.repository;

import android.arch.paging.ItemKeyedDataSource;
import android.support.annotation.NonNull;
import android.util.Log;

import com.gary.android.redditg.api.RedditApi;
import com.gary.android.redditg.model.RedditPost;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemKeyedSubredditDataSource extends ItemKeyedDataSource<String, RedditPost> {
    private static final String TAG = ItemKeyedSubredditDataSource.class.getSimpleName();

    private String mSubredditName;
    private RedditApi mRedditApi;

    public ItemKeyedSubredditDataSource(String subredditName, RedditApi redditApi) {
        mSubredditName = subredditName;
        mRedditApi = redditApi;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<String> params, @NonNull LoadInitialCallback<RedditPost> callback) {
        Call<RedditApi.ListingResponse> call = mRedditApi.getTop(mSubredditName, params.requestedLoadSize);
        try {
            Response<RedditApi.ListingResponse> response = call.execute();
            if (response != null) {
                RedditApi.ListingResponse listingResponse = response.body();

                ArrayList<RedditPost> posts = new ArrayList<>(listingResponse.data.children.size());
                for (RedditApi.RedditChildrenResponse child : listingResponse.data.children) {
                    posts.add(child.data);
                }
                callback.onResult(posts);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadAfter(@NonNull LoadParams<String> params, @NonNull LoadCallback<RedditPost> callback) {
        mRedditApi.getTopAfter(mSubredditName, params.key, params.requestedLoadSize).enqueue(new Callback<RedditApi.ListingResponse>() {
            @Override
            public void onResponse(Call<RedditApi.ListingResponse> call, Response<RedditApi.ListingResponse> response) {

            }

            @Override
            public void onFailure(Call<RedditApi.ListingResponse> call, Throwable t) {
                Log.d(TAG, "loadAfter failed");
            }
        });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<String> params, @NonNull LoadCallback<RedditPost> callback) {

    }

    @NonNull
    @Override
    public String getKey(@NonNull RedditPost item) {
        return item.getName();
    }
}
