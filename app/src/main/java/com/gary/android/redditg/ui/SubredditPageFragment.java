package com.gary.android.redditg.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gary.android.redditg.R;
import com.gary.android.redditg.reddit.RedditManager;
import com.gary.android.redditg.viewmodel.SubmissionDetailViewModel;
import com.gary.android.redditg.viewmodel.SubredditPageViewModel;

import net.dean.jraw.models.Listing;
import net.dean.jraw.models.Submission;
import net.dean.jraw.pagination.DefaultPaginator;

import java.net.SocketTimeoutException;
import java.util.ArrayList;

public class SubredditPageFragment extends Fragment {
    private static final String TAG = SubredditPageFragment.class.getSimpleName();

    private static final String ARG_SUB_NAME = "arg_sub_name";

    private String mSubName;
    private DefaultPaginator<Submission> mPaginator;
    private ArrayList<Submission> mSubList = new ArrayList<>();
    private SubredditRecyclerViewAdapter mAdapter;
    private Handler mMainHandler = new Handler();
    private SubredditPageViewModel mSubredditPageViewModel;
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
            Log.d(TAG,"SubName:" + mSubName);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subreddit_list, container, false);

        mAdapter = new SubredditRecyclerViewAdapter(getContext(), mSubList);
        Context context = view.getContext();
        RecyclerView recyclerView = (RecyclerView) view;
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mAdapter);

        // init ViewModel and load data
        mSubredditPageViewModel = ViewModelProviders.of(this).get(SubredditPageViewModel.class);

        mSubredditPageViewModel.getPaginatorData().observe(this,(DefaultPaginator<Submission> paginator)->{
            mPaginator = paginator;
            // second load list data
            mSubredditPageViewModel.loadSubmissionList(mPaginator);
        });

        mSubredditPageViewModel.getSubmissionList().observe(this,((Listing<Submission> result)->{
            // third update UI
            if (result != null) {
                mSubList.addAll(result);
                mAdapter.notifyDataSetChanged();
            }
        }));
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // first create Paginator
        mSubredditPageViewModel.createPaginator(mSubName);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMainHandler.removeCallbacksAndMessages(null);
    }
}
