package com.gary.android.redditg.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.text.TextUtils;
import android.util.Log;

import com.gary.android.redditg.RedditgApp;
import com.gary.android.redditg.api.RedditApi;
import com.gary.android.redditg.model.Comment;
import com.gary.android.redditg.model.RedditPost;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.gary.android.redditg.Constants.KIND_COMMENT;

public class SubmissionDetailViewModel extends ViewModel {
    private static final String TAG = SubmissionDetailViewModel.class.getSimpleName();

    private MutableLiveData<RedditPost> mSubmission;
    private MutableLiveData<List<Comment>> mComments;

    public MutableLiveData<RedditPost> getRedditPostDetail() {
        if (mSubmission == null) {
            mSubmission = new MutableLiveData<>();
        }
        return mSubmission;
    }

    public MutableLiveData<List<Comment>> getComments() {
        if (mComments == null) {
            mComments = new MutableLiveData<>();
        }
        return mComments;
    }

    public void loadContent(String subreddit, String id) {
        RedditgApp.getInstance().getRepository().getPostDetail(subreddit, id, new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                Gson gson = new GsonBuilder().create();
                JsonArray jsonArray = response.body();
                JsonElement element;

                // first item, post content
                element = jsonArray.get(0);
                RedditApi.ListingResponse detail = gson.fromJson(element, RedditApi.ListingResponse.class);
                if (detail.data.children.size() > 0) {
                    RedditApi.RedditChildrenResponse child = detail.data.children.get(0);
                    mSubmission.setValue(child.data);
                }

                // second item,post comments
                element = jsonArray.get(1);
                RedditApi.CommentListingResponse repliesResponse = gson.fromJson(element, RedditApi.CommentListingResponse.class);
                if (repliesResponse.data.children.size() > 0) {
                    List<Comment> comments = new ArrayList<>(repliesResponse.data.children.size());
                    for (RedditApi.CommentChildrenResponse childrenResponse : repliesResponse.data.children) {
                        if (TextUtils.equals(KIND_COMMENT, childrenResponse.kind) && childrenResponse.data != null) {
                            comments.add(childrenResponse.data);
                            JsonElement replies = childrenResponse.data.getReplies();

                        }
                    }
                    mComments.postValue(comments);
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Log.d(TAG, "getPostDetail failed" + t.getMessage());
            }
        });
    }
}
