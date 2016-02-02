package com.wassim.androidmvpbase.data.local.database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Movies")
public class DBMovie extends Model {
    @Column
    private int mRemoteId;
    @Column
    private String mTitle;
    @Column
    private String mImage;
    @Column
    private float mRating;
    @Column
    private int mReleaseYear;
    @Column
    private String mGenre;
    @Column
    private String mSynopsis;

    public DBMovie() {
    }

    public int getRemoteId() {
        return mRemoteId;
    }

    public void setRemoteId(int remoteId) {
        this.mRemoteId = remoteId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        this.mImage = image;
    }

    public float getRating() {
        return mRating;
    }

    public void setRating(float rating) {
        this.mRating = rating;
    }

    public int getReleaseYear() {
        return mReleaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.mReleaseYear = releaseYear;
    }

    public String getGenre() {
        return mGenre;
    }

    public void setGenre(String genre) {
        this.mGenre = genre;
    }

    public String getSynopsis() {
        return mSynopsis;
    }

    public void setSynopsis(String synopsis) {
        this.mSynopsis = synopsis;
    }
}
