package ru.niceaska.learningprogram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int POSITION_ALL = 0;

    private LearningProgramAdapter learningProgramAdapter;
    private ProviderLerningProgram providerLerningProgram = new ProviderLerningProgram();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initRecycleView();
        initSpinner();
        initModeSpinner();
    }

    private void initModeSpinner() {
        Spinner modes = findViewById(R.id.display_mode_spinner);
        modes.setAdapter(
                new DisplayModeAdapter(this, Arrays.asList(getResources().getStringArray(R.array.modes)))
        );
        modes.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                learningProgramAdapter.setMode(position);
                learningProgramAdapter.setmLectures(providerLerningProgram.mLectures);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void initSpinner() {
        Spinner spinner = findViewById(R.id.spinner_lectors);
        final List<String> lectors = providerLerningProgram.provideLectors();
        Collections.sort(lectors);
        lectors.add(POSITION_ALL, getResources().getString(R.string.all));
        spinner.setAdapter(new LectorSpinnerAdapter(lectors));
        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    learningProgramAdapter
                            .setmLectures(providerLerningProgram.mLectures);
                } else {
                    learningProgramAdapter
                            .setmLectures(providerLerningProgram.filterBy(lectors.get(position)));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initRecycleView() {
        RecyclerView recyclerView = findViewById(R.id.learn);
        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        learningProgramAdapter = new LearningProgramAdapter(this);
        learningProgramAdapter.setmLectures(providerLerningProgram.mLectures);
        DividerItemDecoration deviderItemDecoration = new DividerItemDecoration(this,  DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(deviderItemDecoration);
        recyclerView.setAdapter(learningProgramAdapter);
    }


}
