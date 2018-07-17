package com.gary.android.redditg.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gary.android.redditg.Constants;
import com.gary.android.redditg.R;
import com.gary.android.redditg.viewmodel.SubmissionDetailViewModel;


public class SubmissionDetailActivity extends AppCompatActivity {
    private static final String TAG = SubmissionDetailActivity.class.getSimpleName();

    private CommentsAdapter mCommentsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submission_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("DetailActivity");

        String id = getIntent().getStringExtra(Constants.EXTRA_SUBMISSION_ID);
        String subreddit = getIntent().getStringExtra(Constants.EXTRA_SUBREDDIT);

        // detail content
        ImageView subImg = findViewById(R.id.sub_image);
        TextView subTitle = findViewById(R.id.sub_title);
        SubmissionDetailViewModel submissionViewModel = ViewModelProviders.of(this).get(SubmissionDetailViewModel.class);

        submissionViewModel.getRedditPostDetail().observe(this, submission -> {
            if (submission == null)
                return;

            // set tittle
            subTitle.setText(submission.getTitle());
            //set image
            String url = submission.getUrl();
            Log.d(TAG, "url:" + url);
            if (!TextUtils.isEmpty(url)) {
                Glide.with(this).load(url).into(subImg);
            }
        });

        // comments
        RecyclerView commentList = findViewById(R.id.list_comments);
        mCommentsAdapter = new CommentsAdapter();
        commentList.setAdapter(mCommentsAdapter);
        commentList.setNestedScrollingEnabled(false);

        submissionViewModel.getComments().observe(this, comments -> {
            mCommentsAdapter.setData(comments);
        });

        // load post content
        submissionViewModel.loadContent(subreddit, id);
    }
}
