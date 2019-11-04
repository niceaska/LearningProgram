package ru.niceaska.learningprogram.presentation.presenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ru.niceaska.learningprogram.data.models.Lecture;
import ru.niceaska.learningprogram.data.repository.IOnDataLoadFinish;
import ru.niceaska.learningprogram.data.repository.ProviderLerningProgram;
import ru.niceaska.learningprogram.presentation.view.fragments.LecturesListFragment;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ListFragmentPresenterTest {

    @Mock
    private LecturesListFragment listFragment;

    @Mock
    private ProviderLerningProgram providerLerningProgram;

    @Mock
    private IOnDataLoadFinish onDataLoadFinish;

    private ListFragmentPresenter listFragmentPresenter;

    private List<Lecture> lecturesDataSet = Arrays.asList(
            new Lecture("1", "01.02.2019", "Вводное занятие", "Соколов", new ArrayList<String>()),
            new Lecture("2", "02.02.2019", "View", "Леонидов", new ArrayList<String>()),
            new Lecture("3", "03.02.2019", "Activity", "Бильчук", new ArrayList<String>()),
            new Lecture("4", "04.02.2019", "Custom view", "Кудрявцев", new ArrayList<String>())
    );

    @Before
    public void setUp() throws Exception {
        listFragmentPresenter = new ListFragmentPresenter(listFragment, providerLerningProgram);
    }

    @Test
    public void testLoadData() {
        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                IOnDataLoadFinish onDataLoadFinishListener = (IOnDataLoadFinish) invocation.getArguments()[0];
                onDataLoadFinishListener.onDtataLoaded(lecturesDataSet);
                return null;
            }
        }).when(providerLerningProgram).loadLectures(Mockito.any(IOnDataLoadFinish.class));

        listFragmentPresenter.loadData();
        verify(listFragment).showLectures(lecturesDataSet);
        verifyNoMoreInteractions(listFragment);

    }

    @Test
    public void testSelectMode() {
        when(providerLerningProgram.getmLectures()).thenReturn(lecturesDataSet);
        listFragmentPresenter.selectMode(Mockito.anyInt());
        InOrder inOrder = Mockito.inOrder(listFragment);
        inOrder.verify(listFragment).changeAdapterMode(Mockito.anyInt());
        inOrder.verify(listFragment).showLectures(lecturesDataSet);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void testSelectLector() {
        when(providerLerningProgram.getmLectures()).thenReturn(lecturesDataSet);
        when(providerLerningProgram.filterBy(Mockito.anyString())).thenReturn(lecturesDataSet);
        listFragmentPresenter.selectLector(0);
        verify(listFragment).showLectures(lecturesDataSet);
        verifyNoMoreInteractions(listFragment);
    }


}