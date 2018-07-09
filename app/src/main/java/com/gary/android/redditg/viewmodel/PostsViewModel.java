package com.gary.android.redditg.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.PagedList;
import android.text.TextUtils;

import com.gary.android.redditg.RedditgApp;
import com.gary.android.redditg.model.RedditPost;

public class PostsViewModel extends ViewModel {
    private MutableLiveData<String> subredditName = new MutableLiveData<>();
    private LiveData<PagedList<RedditPost>> repoResult = Transformations.switchMap(subredditName, name -> {
        return RedditgApp.getInstance().getRepository().postsOfSubreddit(name, 30);
    });

    public boolean showSubreddit(String subreddit){
        if (subreddit == null)
            return false;

        if (TextUtils.equals(subredditName.getValue(),subreddit)) {
            return false;
        }
        subredditName.setValue(subreddit);

        return true;
    }

    public LiveData<PagedList<RedditPost>> getRepoResult() {
        return repoResult;
    }
}
