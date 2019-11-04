package ru.niceaska.learningprogram.presentation.view.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import ru.niceaska.learningprogram.R;
import ru.niceaska.learningprogram.data.models.Lecture;
import ru.niceaska.learningprogram.presentation.view.fragments.DetailedLectureFragment;
import ru.niceaska.learningprogram.presentation.view.fragments.LecturesListFragment;

public class MainActivity extends AppCompatActivity implements ShowDetailed {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.list_fragment, LecturesListFragment.newInstance())
                    .commit();

    }


    @Override
    public void showDetailedInfo(Lecture lecture) {
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .add(R.id.list_fragment, DetailedLectureFragment.newInstance(lecture))
                .commit();
    }
}
