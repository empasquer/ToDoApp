package com.example.todoapp.models;

public class Todo {
    private int id;
    private String content;
    private boolean completed;

    public Todo() {
        this.completed = false;
    }

    public Todo(String content) {
        this.content = content;
        this.completed = false;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
