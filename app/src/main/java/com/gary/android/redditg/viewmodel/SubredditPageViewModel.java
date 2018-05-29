package com.gary.android.redditg.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;
import android.util.Log;

import com.gary.android.redditg.reddit.RedditManager;

import net.dean.jraw.models.Listing;
import net.dean.jraw.models.Submission;
import net.dean.jraw.pagination.DefaultPaginator;

public class SubredditPageViewModel extends ViewModel {
    private static final String TAG = SubredditPageViewModel.class.getSimpleName();

    private MutableLiveData<DefaultPaginator<Submission>> mPaginatorData;
    private MutableLiveData<Listing<Submission>> mSubmissionList;

    public MutableLiveData<Listing<Submission>> getSubmissionList() {
        if (mSubmissionList == null) {
            mSubmissionList = new MutableLiveData<>();
        }
        return mSubmissionList;
    }

    public MutableLiveData<DefaultPaginator<Submission>> getPaginatorData() {
        if (mPaginatorData == null) {
            mPaginatorData = new MutableLiveData<>();
        }
        return mPaginatorData;
    }

    public void createPaginator(String mSubName) {
        AsyncTask.THREAD_POOL_EXECUTOR.execute(() -> {
            Log.d(TAG, "create paginator:" + mSubName);
            DefaultPaginator<Submission> paginator = RedditManager.getInstance().getRedditClient()
                    .subreddit(mSubName)
                    .posts()
                    .build();
            mPaginatorData.postValue(paginator);
        });
    }

    public void loadSubmissionList(DefaultPaginator<Submission> paginator) {
        Log.d(TAG, "loadSubmissionList");
        AsyncTask.THREAD_POOL_EXECUTOR.execute(() -> {
            Log.d(TAG, "baseUrl:" + paginator.getBaseUrl());
            Listing<Submission> submissions = null;
            // catch java.net.SocketTimeoutException
            try {
                submissions = paginator.next();
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "load data timeout:" + paginator.getBaseUrl());
            }

            mSubmissionList.postValue(submissions);
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
