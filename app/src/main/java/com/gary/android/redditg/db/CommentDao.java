package com.gary.android.redditg.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.gary.android.redditg.model.CommentEntity;

import java.util.List;

@Dao
public interface CommentDao {
    @Query("SELECT * FROM comment")
    List<CommentEntity> getAll();

    @Query("SELECT * FROM comment WHERE id IN (:ids)")
    List<CommentEntity> loadAllByIds(int[] ids);

    @Insert
    void insertAll(CommentEntity... comments);

    @Delete
    void delete(CommentEntity comment);
}
