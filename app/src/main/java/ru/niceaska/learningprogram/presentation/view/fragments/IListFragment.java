package ru.niceaska.learningprogram.presentation.view.fragments;

import java.util.List;

import ru.niceaska.learningprogram.data.models.Lecture;

public interface IListFragment {
    void showMoreInfo(Lecture lecture);

    void showLectors(List<String> lectors);

    void showLectures(List<Lecture> lectures);

    void showModes(List<String> modes);

    void changeAdapterMode(int postion);
}
