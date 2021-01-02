package com.example.battleship.game.map;

public class Field {
    private int id;
    private StatusField status;

    public Field(int id, StatusField status) {
        this.id = id;
        this.status = status;
    }

    public Field(int id) {
        this.id = id;
        this.status = StatusField.EMPTY;
    }

    public int getId() {
        return id;
    }

    public StatusField getStatus() {
        return status;
    }

    public void setStatus(StatusField status) {
        this.status = status;
    }
}
