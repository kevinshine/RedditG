package com.gary.android.redditg.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;

import com.gary.android.redditg.reddit.RedditManager;

import net.dean.jraw.RedditClient;
import net.dean.jraw.models.Submission;
import net.dean.jraw.tree.RootCommentNode;

public class SubmissionDetailViewModel extends ViewModel {
    private MutableLiveData<RootCommentNode> mCommentsListLiveData;
    private MutableLiveData<Submission> mSubmission;

    public MutableLiveData<Submission> getSubmission() {
        if (mSubmission == null) {
            mSubmission = new MutableLiveData<>();
        }
        return mSubmission;
    }

    public LiveData<RootCommentNode> getComments() {
        if (mCommentsListLiveData == null) {
            mCommentsListLiveData = new MutableLiveData<>();
        }
        return mCommentsListLiveData;
    }

    public void loadContent(String submissionId){
        AsyncTask.THREAD_POOL_EXECUTOR.execute(()->{
            RedditClient redditClient = RedditManager.getInstance().getRedditClient();
            if (redditClient != null){
                Submission submission = redditClient.submission(submissionId).inspect();
                mSubmission.postValue(submission);

                RootCommentNode rootCommentNode = redditClient.submission(submissionId).comments();
                mCommentsListLiveData.postValue(rootCommentNode);
            }
        });
    }
}
