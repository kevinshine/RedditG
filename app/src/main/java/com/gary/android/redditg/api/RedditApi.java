package com.gary.android.redditg.api;

import com.gary.android.redditg.model.RedditPost;

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

    // for after/before param, either get from RedditDataResponse.after/before,
    // or pass RedditNewsDataResponse.name (though this is technically incorrect)
    @GET("/r/{subreddit}/hot.json")
    Call<ListingResponse> getTopAfter(
            @Path("subreddit") String subreddit,
            @Query("after") String after,
            @Query("limit") int limit);


    @GET("/r/{subreddit}/comments/{id}.json")
    Call<List<ListingResponse>> getPostDetail(
            @Path("subreddit") String subreddit,
            @Path("id") String id);

    class ListingResponse {
        public ListingData data;
    }

    class ListingData {
        public List<RedditChildrenResponse> children;
    }

    class RedditChildrenResponse {
        public RedditPost data;
    }
}
