package com.gary.android.redditg.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gary.android.redditg.R;
import com.gary.android.redditg.viewmodel.PostsViewModel;

public class SubredditPageFragment extends Fragment {
    private static final String TAG = SubredditPageFragment.class.getSimpleName();

    private static final String ARG_SUB_NAME = "arg_sub_name";

    private String mSubName;
    private SubredditRecyclerViewAdapter mAdapter;
    private PostsViewModel mPostsViewModel;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SubredditPageFragment() {
    }

    public static SubredditPageFragment newInstance(String subName) {
        SubredditPageFragment fragment = new SubredditPageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SUB_NAME, subName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mSubName = getArguments().getString(ARG_SUB_NAME);
            Log.d(TAG, "SubName:" + mSubName);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subreddit_list, container, false);

        mAdapter = new SubredditRecyclerViewAdapter(getContext());

        RecyclerView recyclerView = view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter);

        // init ViewModel and load data
        mPostsViewModel = ViewModelProviders.of(this).get(PostsViewModel.class);
        mPostsViewModel.getRepoResult().observe(this, pagedListLiveData -> {
            mAdapter.submitList(pagedListLiveData);
        });

        mPostsViewModel.showSubreddit(mSubName);
        return view;
    }
}
