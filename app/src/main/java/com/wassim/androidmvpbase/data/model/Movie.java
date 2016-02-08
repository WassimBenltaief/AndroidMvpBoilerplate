package com.wassim.androidmvpbase.data.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Movie implements Parcelable {
    @SerializedName("id")
    private int mId;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("image")
    private String mImage;
    @SerializedName("rating")
    private float mRating;
    @SerializedName("releaseYear")
    private int mReleaseYear;
    @SerializedName("genre")
    private String mGenre;
    @SerializedName("synopsis")
    private String mSynopsis;

    public Movie() {
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mId);
        dest.writeString(this.mTitle);
        dest.writeString(this.mImage);
        dest.writeFloat(this.mRating);
        dest.writeInt(this.mReleaseYear);
        dest.writeString(this.mGenre);
        dest.writeString(this.mSynopsis);
    }

    protected Movie(Parcel in) {
        this.mId = in.readInt();
        this.mTitle = in.readString();
        this.mImage = in.readString();
        this.mRating = in.readFloat();
        this.mReleaseYear = in.readInt();
        this.mGenre = in.readString();
        this.mSynopsis = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };


}
