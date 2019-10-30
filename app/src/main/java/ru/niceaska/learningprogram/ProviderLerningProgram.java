package ru.niceaska.learningprogram;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ru.niceaska.learningprogram.models.Lecture;

public class ProviderLerningProgram {

    List<Lecture> mLectures;

    private static String LECTURES_URL = "http://landsovet.ru/learning_program.json";

    List<String> provideLectors() {
        Set<String> set = new HashSet<>();

        for (Lecture lect : mLectures) {
            set.add(lect.getLector());
        }
        return new ArrayList<>(set);
    }

    String provideDesription(Lecture lecture) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : lecture.getDescription()) {
            stringBuilder.append(s).append("\n");
        }
        return new String(stringBuilder);
    }

    List <Lecture> filterBy(String lector) {
         List<Lecture> lectures = new ArrayList<>();
         for (Lecture l : mLectures) {
             String name = l.getLector();
             if (name.equals(lector)) {
                 lectures.add(l);
             }
         }
        return lectures;
    }

    List<Lecture> loadLectures() {
        try (Reader reader = new InputStreamReader(
                new URL(LECTURES_URL).openConnection().getInputStream()
        );) {
            Gson gson = new Gson();
            Lecture[] lectures = gson.fromJson(reader, Lecture[].class);
            mLectures = new ArrayList<Lecture>(Arrays.asList(lectures));
            return mLectures;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}

