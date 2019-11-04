package ru.niceaska.learningprogram.data.repository;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ru.niceaska.learningprogram.data.models.Lecture;

public class ProviderLerningProgram {

    public List<Lecture> mLectures;

    private static String LECTURES_URL = "http://landsovet.ru/learning_program.json";

    public List<String> provideLectors() {
        Set<String> set = new HashSet<>();

        for (Lecture lect : mLectures) {
            set.add(lect.getLector());
        }
        return new ArrayList<>(set);
    }

    public String provideDesription(Lecture lecture) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : lecture.getDescription()) {
            stringBuilder.append(s).append("\n");
        }
        return new String(stringBuilder);
    }

    public List<Lecture> filterBy(String lector) {
         List<Lecture> lectures = new ArrayList<>();
         for (Lecture l : mLectures) {
             String name = l.getLector();
             if (name.equals(lector)) {
                 lectures.add(l);
             }
         }
        return lectures;
    }

    private List<Lecture> getLectures() {
        try (Reader reader = new InputStreamReader(
                new URL(LECTURES_URL).openConnection().getInputStream()
        );) {
            Gson gson = new Gson();
            Lecture[] lectures = gson.fromJson(reader, Lecture[].class);
            mLectures = Arrays.asList(lectures);
            setWeekIndex(mLectures);
            return new ArrayList<>(mLectures);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void loadLectures(IOnDataLoadFinish listener) {
        new LecturesLoaderTask(listener, this).execute();

    }

    private void setWeekIndex(List<Lecture> lectures) {
        for (Lecture lecture : lectures) {
            lecture.setWeekIndex();
        }
    }

    private static class LecturesLoaderTask extends AsyncTask<Void, Void, List<Lecture>> {

        IOnDataLoadFinish listener;
        WeakReference<ProviderLerningProgram> providerLerningProgramWeakReference;


        public LecturesLoaderTask(IOnDataLoadFinish listener,
                                  ProviderLerningProgram providerLerningProgram) {
            this.listener = listener;
            this.providerLerningProgramWeakReference = new WeakReference<>(providerLerningProgram);
        }

        @Override
        protected List<Lecture> doInBackground(Void... voids) {
            if (providerLerningProgramWeakReference.get() == null) return null;
            return providerLerningProgramWeakReference.get().getLectures();
        }

        @Override
        protected void onPostExecute(List<Lecture> lectures) {
            listener.onDtataLoaded(lectures);
        }
    }
}

