package com.example.timer.Sequence.Timer;

import com.example.timer.Sequence.Timer.Category.Category;

public class Timer {
    private int id = -1;
    private Category category;
    private int duration;

    public Timer(int id, Category category, int duration){
        this.id = id;
        this.category = category;
        this.duration = duration;
    }
    public Timer(Category category, int duration){
        this.category = category;
        this.duration = duration;
    }
    public Category getCategory() {
        return category;
    }

    public int getDuration() {
        return duration;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString(){
        return category.getName();
    }
}
