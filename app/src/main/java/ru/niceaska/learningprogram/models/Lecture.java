package ru.niceaska.learningprogram.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Lecture implements Parcelable {

    @Expose
    private static final int LECTURES_PER_WEEK = 3;


    @SerializedName("number")
    private final String mNumber;
    @SerializedName("date")
    private final String mDate;
    @SerializedName("theme")
    private final String mSubject;
    @SerializedName("lector")
    private final String mLector;
    @SerializedName("subtopics")
    private final List<String> description;
    private final int weekIndex;


    public Lecture(@NonNull String mNumber, @NonNull String mDate,
                   @NonNull String mSubject, @NonNull String mLector, @NonNull List<String> description) {
        this.mNumber = mNumber;
        this.mDate = mDate;
        this.mSubject = mSubject;
        this.mLector = mLector;
        this.description = description;
        this.weekIndex = (Integer.parseInt(mNumber) - 1) / LECTURES_PER_WEEK;
    }


    protected Lecture(Parcel in) {
        mNumber = in.readString();
        mDate = in.readString();
        mSubject = in.readString();
        mLector = in.readString();
        description = in.createStringArrayList();
        weekIndex = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mNumber);
        dest.writeString(mDate);
        dest.writeString(mSubject);
        dest.writeString(mLector);
        dest.writeStringList(description);
        dest.writeInt(weekIndex);
    }

    public static final Creator<Lecture> CREATOR = new Creator<Lecture>() {
        @Override
        public Lecture createFromParcel(Parcel in) {
            return new Lecture(in);
        }

        @Override
        public Lecture[] newArray(int size) {
            return new Lecture[size];
        }
    };

    public String getmNumber() {
        return mNumber;
    }

    public String getmDate() {
        return mDate;
    }

    public String getmSubject() {
        return mSubject;
    }

    public String getLector() {
        return mLector;
    }

    public List<String> getDescription() {
        return description;
    }

    public int getWeekIndex() {
        return weekIndex;
    }

    @Override
    public int describeContents() {
        return 0;
    }

}

