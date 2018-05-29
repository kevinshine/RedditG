package com.gary.android.redditg.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.gary.android.redditg.R;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    public static final List<String> defaultSubs =
            Arrays.asList("frontpage", "pics", "videos", "blog", "creepy", "DIY", "Documentaries",
                    "EarthPorn", "food", "gaming", "GetMotivated",
                    "InternetIsBeautiful", "Jokes", "listentothis",
                    "mildlyinteresting", "movies", "Music", "news", "nottheonion",
                    "OldSchoolCool", "photoshopbattles",
                    "science", "space", "sports", "television",
                    "todayilearned", "UpliftingNews", "worldnews");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager subredditPager = findViewById(R.id.viewPager);
        FragmentStatePagerAdapter pagerAdapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                String subName = defaultSubs.get(position);
                SubredditPageFragment fragment = SubredditPageFragment.newInstance(subName);
                return fragment;
            }

            @Override
            public int getCount() {
                return defaultSubs.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return defaultSubs.get(position);
            }
        };
        subredditPager.setAdapter(pagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(subredditPager);
    }
}
