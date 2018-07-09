package com.gary.android.redditg.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.gary.android.redditg.R;

public class CommentViewHolder extends RecyclerView.ViewHolder {

    public TextView commentTitle;

    public CommentViewHolder(View itemView) {
        super(itemView);

        commentTitle = itemView.findViewById(R.id.comment_title);
    }

}
