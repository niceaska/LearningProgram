package ru.niceaska.learningprogram.presentation.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.niceaska.learningprogram.R;
import ru.niceaska.learningprogram.data.models.Lecture;
import ru.niceaska.learningprogram.data.repository.ProviderLerningProgram;
import ru.niceaska.learningprogram.presentation.presenter.ListFragmentPresenter;
import ru.niceaska.learningprogram.presentation.view.activity.ShowDetailed;
import ru.niceaska.learningprogram.presentation.view.adapters.DetatiledShowHolder;
import ru.niceaska.learningprogram.presentation.view.adapters.DisplayModeAdapter;
import ru.niceaska.learningprogram.presentation.view.adapters.LearningProgramAdapter;
import ru.niceaska.learningprogram.presentation.view.adapters.LectorSpinnerAdapter;

public class LecturesListFragment extends Fragment implements IListFragment {

    private LearningProgramAdapter learningProgramAdapter;
    private ListFragmentPresenter listFragmentPresenter;
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listFragmentPresenter = new ListFragmentPresenter(this, new ProviderLerningProgram());
        listFragmentPresenter.loadData();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        init(view);
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
        initSpinnersListeners();
    }

    private void initSpinnersListeners() {
        modesSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                listFragmentPresenter.selectMode(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerLectors.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                listFragmentPresenter.selectLector(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void showMoreInfo(Lecture lecture) {
        if (getActivity() instanceof ShowDetailed) {
            ((ShowDetailed) getActivity()).showDetailedInfo(lecture);
        }
    }

    @Override
    public void showLectors(List<String> lectors) {
        spinnerLectors.setAdapter(new LectorSpinnerAdapter(lectors));
    }

    @Override
    public void showLectures(List<Lecture> lectures) {
        learningProgramAdapter.setLectures(lectures);
    }

    @Override
    public void showModes(List<String> modes) {
        modesSpinner.setAdapter(
                new DisplayModeAdapter(requireContext(), modes));
    }

    @Override
    public void changeAdapterMode(int position) {
        learningProgramAdapter.setMode(position);
    }
}
