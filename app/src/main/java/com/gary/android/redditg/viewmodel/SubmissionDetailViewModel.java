package com.gary.android.redditg.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.PagedList;
import android.os.AsyncTask;
import android.util.Log;

import com.gary.android.redditg.RedditgApp;
import com.gary.android.redditg.api.RedditApi;
import com.gary.android.redditg.model.RedditPost;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubmissionDetailViewModel extends ViewModel {
    private static final String TAG = SubmissionDetailViewModel.class.getSimpleName();

    private MutableLiveData<RedditPost> mSubmission;

    public MutableLiveData<RedditPost> getRedditPostDetail() {
        if (mSubmission == null) {
            mSubmission = new MutableLiveData<>();
        }
        return mSubmission;
    }

    public void loadContent(String subreddit,String id){
        RedditgApp.getInstance().getRepository().getPostDetail(subreddit, id, new Callback<List<RedditApi.ListingResponse>>() {
            @Override
            public void onResponse(Call<List<RedditApi.ListingResponse>> call, Response<List<RedditApi.ListingResponse>> response) {
                try{
                    RedditApi.ListingResponse detail = response.body().get(0);
                    if (detail.data.children.size() > 0){
                        RedditApi.RedditChildrenResponse child = detail.data.children.get(0);
                        mSubmission.setValue(child.data);
                    }
                }catch (Exception e){
                    //do nothing
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<RedditApi.ListingResponse>> call, Throwable t) {
                Log.d(TAG,"getPostDetail failed" + t.getMessage());
            }
        });
    }
}
