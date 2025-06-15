package com.project.organizer.mediator;

import com.project.organizer.model.Literature;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;

public class Scientist extends Colleague {
    private List<Literature> literatureList;

    public Scientist(Mediator mediator) {
        super(mediator);
        this.literatureList = new ArrayList<>();
    }

    @Override
    public void notifyMediator(String event) {
        if (mediator != null) {
            mediator.notify(this, event);
        }
    }

    public void processLiterature(List<Literature> literature) {
        this.literatureList = literature;
        // Process literature data
        for (Literature item : literature) {
            System.out.println("Processing: " + item.getTitle() + " by " + item.getAuthor());
        }
    }

    public List<Literature> getLiteratureList() {
        return literatureList;
    }
}
