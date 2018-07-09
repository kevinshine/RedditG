package com.gary.android.redditg.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "posts")
public class RedditPost {
    @PrimaryKey
    @ColumnInfo(name = "id")
    String id;
    @ColumnInfo(name = "full_name")
    String name;
    @ColumnInfo(name = "title")
    String title;
    @ColumnInfo(name = "score")
    int score;
    @ColumnInfo(name = "author")
    String author;
    @ColumnInfo(name = "subreddit")
    String subreddit;
    @ColumnInfo(name = "num_comments")
    int num_comments;
    @ColumnInfo(name = "created_utc")
    long created_utc;
    @ColumnInfo(name = "thumbnail")
    String thumbnail;
    @ColumnInfo(name = "url")
    String url;
    @ColumnInfo(name = "domain")
    String domain;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSubreddit() {
        return subreddit;
    }

    public void setSubreddit(String subreddit) {
        this.subreddit = subreddit;
    }

    public int getNum_comments() {
        return num_comments;
    }

    public void setNum_comments(int num_comments) {
        this.num_comments = num_comments;
    }

    public long getCreated_utc() {
        return created_utc;
    }

    public void setCreated_utc(long created_utc) {
        this.created_utc = created_utc;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }
}
