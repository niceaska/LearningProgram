package ru.niceaska.learningprogram;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ru.niceaska.learningprogram.models.Lecture;

public class LearningProgramAdapter extends RecyclerView.Adapter<LearningProgramAdapter.BaseViewHolder> {

    static private final int NO_GROUPED = 0;
    static private final int GROUPED_BY_WEEK = 1;

    private List<Lecture> mLectures;
    private List<Object> mObjects;
    private Context mContext;
    private int mode = NO_GROUPED;


    LearningProgramAdapter(Context context) {
        mContext = context;
    }


    public void setMode(int mode) {
        this.mode = mode;
    }

    public void setmLectures(List<Lecture> Lectures) {
        if (mLectures == null) {
            this.mLectures = new ArrayList<>();
            this.mObjects = new ArrayList<>();
        } else {
            this.mLectures = new ArrayList<Lecture>(Lectures);
            switch (mode) {
                case GROUPED_BY_WEEK:
                    groupLecturesByWeek(this.mLectures);
                    break;
                case NO_GROUPED:
                default:
                    mObjects = new ArrayList<Object>(Lectures);
                    break;
            }
        }
        notifyDataSetChanged();
    }

    private void groupLecturesByWeek(List<Lecture> Lectures) {
        mObjects = new ArrayList<>();
        int currWeek = Lectures.get(0).getWeekIndex();
        String week = mContext.getResources().getString(R.string.week) + " ";

        for (int i = 0; i < Lectures.size(); i++) {
            if (mLectures.get(i).getWeekIndex() >= currWeek) {
                currWeek = mLectures.get(i).getWeekIndex()  + 1;
                mObjects.add(week + currWeek);
            }
            mObjects.add(Lectures.get(i));
        }

    }

    @Override
    public int getItemViewType(int position) {
        Object item = mObjects.get(position);
        int mode;
        if (item instanceof Lecture) {
            mode =  NO_GROUPED;
        } else if (item instanceof String) {
            mode =  GROUPED_BY_WEEK;
        } else {
            throw new IllegalArgumentException();
        }
        return mode;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == GROUPED_BY_WEEK) {
            View view =  LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.week_item, parent, false);
            return new WeekHolder(view);
        }
        View view =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout, parent, false);
        return new LectureHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        if (holder instanceof LectureHolder) {
            ((LectureHolder) holder).bindView((Lecture) mObjects.get(position));
        } else if (holder instanceof  WeekHolder) {
            ((WeekHolder) holder).bindView((String) mObjects.get(position));
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public int getItemCount() {
        return mObjects == null ? 0 : mObjects.size();
    }


    static abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {

        BaseViewHolder(@NonNull View itemView) {
            super(itemView);
        }
        abstract void bindView(T item);
    }

    static class LectureHolder extends BaseViewHolder<Lecture> {

        private final TextView mNumber;
        private final TextView mDate;
        private final TextView mSubject;
        private final TextView mLector;

        LectureHolder(@NonNull View itemView) {
            super(itemView);
            mNumber = itemView.findViewById(R.id.number);
            mDate = itemView.findViewById(R.id.data);
            mLector = itemView.findViewById(R.id.lector);
            mSubject = itemView.findViewById(R.id.subject);
        }

        void bindView(Lecture lecture) {
            mNumber.setText(String.valueOf(lecture.getmNumber()));
            mDate.setText(lecture.getmDate());
            mSubject.setText(lecture.getmSubject());
            mLector.setText(lecture.getmLector());
        }
    }

    private static class WeekHolder extends BaseViewHolder<String> {
        private final TextView mWeek;

        private WeekHolder(@NonNull View itemView) {
            super(itemView);
            mWeek = itemView.findViewById(R.id.week);
        }

        void bindView(String item) {
            mWeek.setText(item);
        }
    }
}


