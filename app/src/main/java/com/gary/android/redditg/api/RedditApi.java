package com.gary.android.redditg.api;

import com.gary.android.redditg.model.Comment;
import com.gary.android.redditg.model.RedditPost;
import com.google.gson.JsonArray;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RedditApi {
    @GET("/r/{subreddit}/hot.json")
    Call<ListingResponse> getTop(
            @Path("subreddit") String subreddit,
            @Query("limit") int limit);

    @GET("/r/{subreddit}/hot.json")
    Call<ListingResponse> getTopAfter(
            @Path("subreddit") String subreddit,
            @Query("after") String after,
            @Query("limit") int limit);


    @GET("/r/{subreddit}/comments/{id}.json")
    Call<JsonArray> getPostDetail(
            @Path("subreddit") String subreddit,
            @Path("id") String id,
            @Query("limit") int limit);

    class ListingResponse {
        public ListingData data;
    }

    class ListingData {
        public List<RedditChildrenResponse> children;
    }

    class RedditChildrenResponse {
        public RedditPost data;
    }

    class CommentListingResponse {
        public CommentListingData data;
    }

    class CommentListingData {
        public List<CommentChildrenResponse> children;
    }

    class CommentChildrenResponse {
        public String kind;

        public Comment data;
    }
}
