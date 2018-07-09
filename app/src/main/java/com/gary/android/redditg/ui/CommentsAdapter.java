package com.gary.android.redditg.ui;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gary.android.redditg.R;
import com.gary.android.redditg.model.RedditPost;

public class CommentsAdapter extends PagedListAdapter<RedditPost,CommentViewHolder>{

    public CommentsAdapter(){
        super(new DiffUtil.ItemCallback<RedditPost>() {
            @Override
            public boolean areItemsTheSame(RedditPost oldItem, RedditPost newItem) {
                return oldItem == newItem;
            }

            @Override
            public boolean areContentsTheSame(RedditPost oldItem, RedditPost newItem) {
                return TextUtils.equals(oldItem.getId(), newItem.getId());
            }
        });
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_comment_item,parent,false);
        return new CommentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        RedditPost comment = getItem(position);
        holder.commentTitle.setText(comment.getTitle());
    }


}
