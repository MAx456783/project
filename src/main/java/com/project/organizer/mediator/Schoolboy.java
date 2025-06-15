package com.project.organizer.mediator;

public class Schoolboy extends Colleague {
    private String name;

    public Schoolboy(Mediator mediator, String name) {
        super(mediator);
        this.name = name;
    }

    @Override
    public void notifyMediator(String event) {
        if (mediator != null) {
            mediator.notify(this, event);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
