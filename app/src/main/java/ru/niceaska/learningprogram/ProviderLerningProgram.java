package ru.niceaska.learningprogram;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ru.niceaska.learningprogram.models.Lecture;

public class ProviderLerningProgram {
    List<Lecture> mLectures = Arrays.asList(
            new Lecture("1",	"24.09.2019","Вводное занятие", "Соколов",
                    "История Android, особенности работы, базовые принципы и компоненты в AndroidManifest.xml\n" +
                    "Пробный первый проект, знакомство с инструментарием, настройка IDE"),
            new Lecture("2",	"26.09.2019","View, Layouts", "Соколов",
                    "Базовые вью (TextView, ImageView), базовые лэйауты (linear, frame, relative)\n" +
                    "Ресурсы и квалификаторы, базовое понятие стилей\n" +
                    "Обработка View в ЖЦ Activity"),
            new Lecture("3","28.09.2019","Drawables",  "Соколов",
                    "Теория: Drawable, существующие типы, levels, states"),
            new Lecture("4","01.10.2019","Activity", "Сафарян",
                    "Жизненный цикл Activity подробно, сохранение кастомных состояний\n" +
                    "Интенты, фильтры, категории (BROWSABLE). Bundle, Parcelable." ),
            new Lecture("5","03.10.2019","Адаптеры", "Чумак",
                    "AdapterView: как было раньше. ListView, Spinner\n" +
                    " RecyclerView: как стало принято теперь"),
            new Lecture("6","05.10.2019","UI: практика", "Кудрявцев", "Практическое занятие"),
            new Lecture("7","08.10.2019","Custom View", "Кудрявцев",
                "Теоретические основы написания кастомного View, основные методы, атрибуты в XML\n" +
                            "Теория рисования на Canvas"),
            new Lecture("8","10.10.2019","Touch events", "Бильчук",
                    "Теория onTouchEvent, ACTION_CANCEL\n"),
            new Lecture("9","12.10.2019",	"Сложные жесты", "Соколов", "Мультитач, GestureDetector"),
            new Lecture("10","15.10.2019","Layout & Measurement", "Кудрявцев",
                    "onMeasure и все вопросы, связанные с измерением вьюхи для лэйаута\n"),
            new Lecture("11","17.10.2019","Custom ViewGroup", "Кудрявцев",
                    "Отличия от простого View\n" +
                    "Роутинг событий, перехват событий onInterceptTouchEvent"),
            new Lecture("12","19.10.2019","Анимации", "Чумак",
                    "Старые анимации вью (олдскул с 1-2 андроидов), новые аниматоры свойств"),
            new Lecture("13","22.10.2019","Практика View", "Соколов",
                    "Большое самостоятельное практическое занятие с заданием на написание какой-нибудь интересной вью или группы"),
            new Lecture("14","24.10.2019","Фрагменты: база", "Бильчук",""),
            new Lecture("15","26.10.2019","Фрагменты: практика", "Соколов", ""),
            new Lecture("16","29.10.2019","Фоновая работа", "Чумак", ""),
            new Lecture("17","31.10.2019","Абстракции фон/UI", "Леонидов", ""),
            new Lecture("18","5.11.2019", "Фон: практика", "Чумак", ""),
            new Lecture("19","7.11.2019", "BroadcastReceiver", "Бильчук", ""),
            new Lecture("20","9.11.2019", "Runtime permissions", "Кудрявцев", ""),
            new Lecture("21","12.11.2019","Service", "Леонидов", ""),
            new Lecture("22","14.11.2019","Service: практика", "Леонидов", ""),
            new Lecture("23","16.11.2019","Service: биндинг", "Леонидов", ""),
            new Lecture("24","19.11.2019","Preferences", "Сафарян", ""),
            new Lecture("25","21.11.2019","SQLite", "Бильчук", ""),
            new Lecture("26","23.11.2019","SQLite: Room", "Соколов", ""),
            new Lecture("27","26.11.2019","ContentProvider", "Сафарян", ""),
            new Lecture("28","28.11.2019","FileProvider", "Соколов", ""),
            new Lecture("29","30.11.2019","Геолокация", "Леонидов", ""),
            new Lecture("30","3.12.2019", "Material", "Чумак", ""),
            new Lecture("31","5.12.2019", "UI-тесты","Сафарян", ""),
            new Lecture("32","7.12.2019", "Финал","Соколов", "")
    );



     List<String> provideLectors() {
        Set<String> set = new HashSet<>();

        for (Lecture lect : mLectures) {
            set.add(lect.getmLector());
        }
        return new ArrayList<>(set);
    }

    List <Lecture> filterBy(String lector) {
         List<Lecture> lectures = new ArrayList<>();
         for (Lecture l : mLectures) {
             String name = l.getmLector();
             if (name.equals(lector)) {
                 lectures.add(l);
             }
         }
        return lectures;
    }

}

