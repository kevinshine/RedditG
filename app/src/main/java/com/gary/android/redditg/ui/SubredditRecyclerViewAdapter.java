package com.gary.android.redditg.ui;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gary.android.redditg.Constants;
import com.gary.android.redditg.R;
import com.gary.android.redditg.RedditgApp;

import net.dean.jraw.models.Submission;

import java.util.List;

public class SubredditRecyclerViewAdapter extends RecyclerView.Adapter<SubredditRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = SubredditRecyclerViewAdapter.class.getSimpleName();

    private Context mContext;
    private final List<Submission> mValues;

    public SubredditRecyclerViewAdapter(Context context, List<Submission> items) {
        mValues = items;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_subreddit_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Submission item = mValues.get(position);

        holder.mItem = item;
        holder.titleView.setText(item.getTitle());

        holder.mView.setOnClickListener(v -> {
            String id = item.getId();
            if (!TextUtils.isEmpty(id)){
                if (Constants.REDDIT_DOMAIN.equals(item.getDomain())){
                    Intent intent = new Intent(mContext,SubmissionDetailActivity.class);
                    intent.putExtra(Constants.EXTRA_SUBMISSION_ID,id);
                    mContext.startActivity(intent);
                }else {
                    Intent intent = new Intent(mContext,SubmissionDetailWebViewActivity.class);
                    intent.putExtra(Constants.EXTRA_SUBMISSION_ID,id);
                    intent.putExtra(Constants.EXTRA_SUBMISSION_URL,item.getUrl());
                    mContext.startActivity(intent);
                }
            }
        });

        Log.d(TAG, "thumb uri:" + item.getThumbnail());
        Glide.with(holder.thumbView).load(item.getThumbnail()).into(holder.thumbView);

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView titleView;
        public final ImageView thumbView;
        public Submission mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            titleView = view.findViewById(R.id.item_title);
            thumbView = view.findViewById(R.id.item_thumbnail);
        }

    }
}
