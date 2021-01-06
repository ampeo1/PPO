package com.example.battleship.game.map;

import android.os.Parcel;
import android.os.Parcelable;

public class Field implements Parcelable {
    private int id;
    private StatusField status;

    public Field(){}

    public Field(int id, StatusField status) {
        this.id = id;
        this.status = status;
    }

    public Field(int id) {
        this.id = id;
        this.status = StatusField.EMPTY;
    }

    protected Field(Parcel in) {
        id = in.readInt();
    }

    public static final Creator<Field> CREATOR = new Creator<Field>() {
        @Override
        public Field createFromParcel(Parcel in) {

            return new Field(in.readInt(), StatusField.valueOf(in.readString()));
        }

        @Override
        public Field[] newArray(int size) {
            return new Field[size];
        }
    };

    public int getId() {
        return id;
    }

    public StatusField getStatus() {
        return status;
    }

    public void setStatus(StatusField status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(status.name());
    }
}
