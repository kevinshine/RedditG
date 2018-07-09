package com.gary.android.redditg.ui;

import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.content.Intent;
import android.support.v7.util.DiffUtil;
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
import com.gary.android.redditg.model.RedditPost;

public class SubredditRecyclerViewAdapter extends PagedListAdapter<RedditPost, SubredditRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = SubredditRecyclerViewAdapter.class.getSimpleName();

    private Context mContext;

    public SubredditRecyclerViewAdapter(Context context) {
        super(new Callback());
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
        RedditPost item = getItem(position);

        holder.mItem = item;
        holder.titleView.setText(item.getTitle());
        holder.mView.setOnClickListener(v -> {
            String id = item.getId();
            if (!TextUtils.isEmpty(id)) {
                if (Constants.REDDIT_DOMAIN.equals(item.getDomain())) {
                    Intent intent = new Intent(mContext, SubmissionDetailActivity.class);
                    intent.putExtra(Constants.EXTRA_SUBMISSION_ID, id);
                    intent.putExtra(Constants.EXTRA_SUBREDDIT, item.getSubreddit());
                    mContext.startActivity(intent);
                } else {
                    Intent intent = new Intent(mContext, SubmissionDetailWebViewActivity.class);
                    intent.putExtra(Constants.EXTRA_SUBMISSION_ID, id);
                    intent.putExtra(Constants.EXTRA_SUBREDDIT, item.getSubreddit());
                    intent.putExtra(Constants.EXTRA_SUBMISSION_URL, item.getUrl());
                    mContext.startActivity(intent);
                }
            }
        });

        Log.d(TAG, "thumb uri:" + item.getThumbnail());
        Glide.with(holder.thumbView).load(item.getThumbnail()).into(holder.thumbView);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView titleView;
        public final ImageView thumbView;
        public RedditPost mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            titleView = view.findViewById(R.id.item_title);
            thumbView = view.findViewById(R.id.item_thumbnail);
        }
    }

    private static class Callback extends DiffUtil.ItemCallback<RedditPost> {

        @Override
        public boolean areItemsTheSame(RedditPost oldItem, RedditPost newItem) {
            return TextUtils.equals(oldItem.getName(), newItem.getName());
        }

        @Override
        public boolean areContentsTheSame(RedditPost oldItem, RedditPost newItem) {
            return oldItem == newItem;
        }
    }
}
