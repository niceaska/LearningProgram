package ru.niceaska.learningprogram;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import ru.niceaska.learningprogram.models.Lecture;

public class LecturesListFragment extends Fragment implements DescriptionShowApp {

    private static final int POSITION_ALL = 0;
    private static final String LIST_STATE_KEY = "listState";
    private static final String LIST_LECTURES_KEY = "lecturesState";

    private Parcelable listState;
    private LearningProgramAdapter learningProgramAdapter;
    private ProviderLerningProgram providerLerningProgram;
    private LinearLayoutManager layoutManager;
    private Spinner modesSpinner;
    private Spinner spinnerLectors;
    private RecyclerView recyclerView;

    public static LecturesListFragment newInstance() {
        Bundle args = new Bundle();
        LecturesListFragment fragment = new LecturesListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        providerLerningProgram = new ProviderLerningProgram();
        return inflater.inflate(R.layout.list_fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        init(view);
        if (savedInstanceState == null) {
            new LecturesLoaderTask(this).execute();
        }
    }

    private void init(View view) {
        modesSpinner = view.findViewById(R.id.display_mode_spinner);
        spinnerLectors = view.findViewById(R.id.spinner_lectors);
        recyclerView = view.findViewById(R.id.learn);
        layoutManager = new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        learningProgramAdapter = new LearningProgramAdapter(new DetatiledShowHolder() {
            @Override
            public void showDetailed(Lecture lecture) {
                showMoreInfo(lecture);
            }
        }, requireContext());
        DividerItemDecoration deviderItemDecoration = new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(deviderItemDecoration);
        recyclerView.setAdapter(learningProgramAdapter);
    }

    private void initSpinnerMods() {
        modesSpinner.setAdapter(
                new DisplayModeAdapter(requireContext(), Arrays.asList(getResources().getStringArray(R.array.modes)))
        );
        modesSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                learningProgramAdapter.setMode(position);
                learningProgramAdapter.setLectures(providerLerningProgram.mLectures);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initSpinnerLectors() {
        final List<String> lectors = providerLerningProgram.provideLectors();
        Collections.sort(lectors);
        lectors.add(POSITION_ALL, getResources().getString(R.string.all));
        spinnerLectors.setAdapter(new LectorSpinnerAdapter(lectors));
        spinnerLectors.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    learningProgramAdapter
                            .setLectures(providerLerningProgram.mLectures);
                } else {
                    learningProgramAdapter
                            .setLectures(providerLerningProgram.filterBy(lectors.get(position)));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initRecycleViewContent() {
        learningProgramAdapter.setLectures(providerLerningProgram.mLectures);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle state) {
        super.onSaveInstanceState(state);
        listState = layoutManager.onSaveInstanceState();
        state.putParcelableArrayList(LIST_LECTURES_KEY, (ArrayList<? extends Parcelable>) providerLerningProgram.mLectures);
        state.putParcelable(LIST_STATE_KEY, listState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            listState = savedInstanceState.getParcelable(LIST_STATE_KEY);
            providerLerningProgram.mLectures = savedInstanceState.<Lecture>getParcelableArrayList(LIST_LECTURES_KEY);
            initRecycleViewContent();
            initSpinnerMods();
            initSpinnerLectors();
        }
        if (listState != null) {
            layoutManager.onRestoreInstanceState(listState);
        }

    }

    @Override
    public void showMoreInfo(Lecture lecture) {
        if (getActivity() instanceof ShowDetailed) {
            ((ShowDetailed) getActivity()).showDetailedInfo(lecture);
        }
    }

    private static class LecturesLoaderTask extends AsyncTask<Void, Void, List<Lecture>> {

        WeakReference<LecturesListFragment> fragmentWeakReference;

        LecturesLoaderTask(LecturesListFragment fragmnt) {
            fragmentWeakReference = new WeakReference<>(fragmnt);
        }

        @Override
        protected List<Lecture> doInBackground(Void... voids) {
            return fragmentWeakReference.get()
                    .providerLerningProgram
                    .loadLectures();
        }

        @Override
        protected void onPostExecute(List<Lecture> lectures) {
            LecturesListFragment fragment = fragmentWeakReference.get();

            if (fragment != null) {
                if (lectures != null) {
                    fragment.initRecycleViewContent();
                    fragment.initSpinnerMods();
                    fragment.initSpinnerLectors();
                    Log.d("post", "onPostExecute: ");
                } else {
                    Toast.makeText(fragment.requireContext(), R.string.load_error, Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
