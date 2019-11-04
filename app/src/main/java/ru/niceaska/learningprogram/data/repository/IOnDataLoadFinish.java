package ru.niceaska.learningprogram.data.repository;

import java.util.List;

import ru.niceaska.learningprogram.data.models.Lecture;

public interface IOnDataLoadFinish {
    void onDtataLoaded(List<Lecture> lectureList);
}
