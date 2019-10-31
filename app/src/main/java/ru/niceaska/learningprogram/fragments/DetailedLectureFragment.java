package ru.niceaska.learningprogram.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ru.niceaska.learningprogram.R;
import ru.niceaska.learningprogram.models.Lecture;
import ru.niceaska.learningprogram.providers.ProviderLerningProgram;

public class DetailedLectureFragment extends Fragment {

    private static final String LECTURE_TAG = "lectureTag";

    public static DetailedLectureFragment newInstance(Lecture lecture) {

        Bundle args = new Bundle();
        args.putParcelable(LECTURE_TAG, lecture);
        DetailedLectureFragment fragment = new DetailedLectureFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.detailed_info_fragment_layout, container, false);
        ProviderLerningProgram providerLerningProgram = new ProviderLerningProgram();
        Lecture lecture = getArguments().getParcelable(LECTURE_TAG);
        ((TextView) v.findViewById(R.id.number)).setText(lecture.getmNumber());
        ((TextView) v.findViewById(R.id.subject)).setText(lecture.getmSubject());
        ((TextView) v.findViewById(R.id.lector)).setText(lecture.getLector());
        ((TextView) v.findViewById(R.id.description)).setText(providerLerningProgram.provideDesription(lecture));
        ((TextView) v.findViewById(R.id.data)).setText(lecture.getmDate());
        return v;
    }
}
