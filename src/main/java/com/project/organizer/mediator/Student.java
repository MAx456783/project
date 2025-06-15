package com.project.organizer.mediator;

public class Student extends Colleague {
    private String name;
    private String role;

    public Student(Mediator mediator, String name, String role) {
        super(mediator);
        this.name = name;
        this.role = role;
    }

    @Override
    public void notifyMediator(String event) {
        if (mediator != null) {
            mediator.notify(this, event);
        }
    }

    public void receiveNotification(String message) {
        System.out.println(name + " получил сообщение: " + message);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
