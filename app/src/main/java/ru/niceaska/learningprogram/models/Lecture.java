package ru.niceaska.learningprogram.models;

import androidx.annotation.NonNull;

public class Lecture {

    private static final int LECTURES_PER_WEEK = 3;


    private final String mNumber;
    private final String mDate;
    private final String mSubject;
    private final String mLector;
    private final String description;
    private final int weekIndex;


    public Lecture(@NonNull  String mNumber, @NonNull String mDate,
                   @NonNull String mSubject, @NonNull String mLector, @NonNull String description) {
        this.mNumber = mNumber;
        this.mDate = mDate;
        this.mSubject = mSubject;
        this.mLector = mLector;
        this.description = description;
        this.weekIndex = (Integer.parseInt(mNumber) - 1) / LECTURES_PER_WEEK;
    }

    public String getmNumber() {
        return mNumber;
    }

    public String getmDate() {
        return mDate;
    }

    public String getmSubject() {
        return mSubject;
    }

    public String getmLector() {
        return mLector;
    }

    public String getDescription() {
        return description;
    }

    public int getWeekIndex() {
        return weekIndex;
    }
}
