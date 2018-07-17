package com.gary.android.redditg.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gary.android.redditg.R;
import com.gary.android.redditg.model.Comment;

import java.util.ArrayList;
import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentViewHolder> {
    private List<Comment> mComments = new ArrayList<>();

    public CommentsAdapter() {
    }

    public void setData(List<Comment> comments) {
        mComments = comments;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_comment_item, parent, false);
        return new CommentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment comment = mComments.get(position);
        holder.commentTitle.setText(comment.getBody());
    }

    @Override
    public int getItemCount() {
        return mComments.size();
    }
}
