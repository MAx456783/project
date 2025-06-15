package com.project.organizer.mediator;

import com.project.organizer.dao.LiteratureDao;
import com.project.organizer.model.Literature;
import javafx.scene.Node;

import java.util.ArrayList;

public interface Mediator {
    default void notifyColleague(Colleague colleague, LiteratureDao message) {} // метод вызова коллегии
    void setView(ArrayList<Literature> message); // установить view для студента, школьника
    void setView(Node message); //  установить view для научного сотрудника
    void notify(Colleague sender, String event);
}