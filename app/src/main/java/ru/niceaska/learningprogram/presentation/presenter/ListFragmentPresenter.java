package ru.niceaska.learningprogram.presentation.presenter;

import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import ru.niceaska.learningprogram.R;
import ru.niceaska.learningprogram.data.models.Lecture;
import ru.niceaska.learningprogram.data.repository.IOnDataLoadFinish;
import ru.niceaska.learningprogram.data.repository.ProviderLerningProgram;
import ru.niceaska.learningprogram.presentation.view.fragments.LecturesListFragment;

public class ListFragmentPresenter {

    private static final int POSITION_ALL = 0;
    private WeakReference<LecturesListFragment> lecturesListFragmentWeakReference;
    private ProviderLerningProgram providerLerningProgram;
    private List<String> lectors;

    public ListFragmentPresenter(LecturesListFragment lecturesListFragment,
                                 ProviderLerningProgram providerLerningProgram) {
        this.lecturesListFragmentWeakReference = new WeakReference<>(lecturesListFragment);
        this.providerLerningProgram = providerLerningProgram;
    }


    public void loadData() {
        IOnDataLoadFinish listener = new IOnDataLoadFinish() {
            @Override
            public void onDtataLoaded(List<Lecture> lectureList) {
                if (lecturesListFragmentWeakReference.get() != null) {
                    lecturesListFragmentWeakReference.get().showLectures(lectureList);
                    initSpinnersContent();
                }
            }
        };
        providerLerningProgram.loadLectures(listener);
    }

    private void initSpinnersContent() {
        this.lectors = providerLerningProgram.provideLectors();
        Collections.sort(lectors);
        LecturesListFragment lecturesListFragment = lecturesListFragmentWeakReference.get();
        if (lecturesListFragment != null) {
            String all = lecturesListFragment.getResources().getString(R.string.all);
            lectors.add(POSITION_ALL, all);
            lecturesListFragment.showLectors(lectors);
            lecturesListFragment.showModes(Arrays.asList(lecturesListFragment
                    .getResources().getStringArray(R.array.modes)));
        }
    }

    public void selectMode(int position) {
        LecturesListFragment listFragment = lecturesListFragmentWeakReference.get();
        if (listFragment != null) {
            listFragment.changeAdapterMode(position);
            listFragment.showLectures(providerLerningProgram.mLectures);
        }
    }


    public void selectLector(int position) {
        LecturesListFragment listFragment = lecturesListFragmentWeakReference.get();
        if (listFragment != null) {
            if (position == 0) {
                listFragment.showLectures(providerLerningProgram.mLectures);
            } else {
                listFragment.showLectures(providerLerningProgram.filterBy(lectors.get(position)));
            }
        }
    }
}
