package ru.niceaska.learningprogram;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import ru.niceaska.learningprogram.models.Lecture;

public class MainActivity extends AppCompatActivity implements ShowDetailed {

    static final String LIST_FRAGMENT = "listFragment";
    private Fragment listFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            this.listFragment = getSupportFragmentManager().getFragment(savedInstanceState, LIST_FRAGMENT);
        } else {
            listFragment = LecturesListFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.list_fragment, listFragment)
                    .commit();
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, LIST_FRAGMENT, listFragment);
    }


    @Override
    public void showDetailedInfo(Lecture lecture) {
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.list_fragment, DetailedLectureFragment.newInstance(lecture))
                .commit();
    }
}
