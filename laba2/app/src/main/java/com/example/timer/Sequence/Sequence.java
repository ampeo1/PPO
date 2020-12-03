package com.example.timer.Sequence;


import android.os.Parcel;
import android.os.Parcelable;

public class Sequence implements Parcelable {
    private int id;
    private String name;
    private String color;

    public Sequence(int id, String name, String color){
        this.id = id;
        this.name = name;
        this.color = color;
    }

    public Sequence( String name, String color){
        this.name = name;
        this.color = color;
    }

    protected Sequence(Parcel in) {
        id = in.readInt();
        name = in.readString();
        color = in.readString();
    }

    public static final Creator<Sequence> CREATOR = new Creator<Sequence>() {
        @Override
        public Sequence createFromParcel(Parcel in) {
            String name = in.readString();
            int id = in.readInt();
            String color = in.readString();
            return new Sequence(id, name, color);
        }

        @Override
        public Sequence[] newArray(int size) {
            return new Sequence[size];
        }
    };

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getColor(){
        if(color.charAt(0) != '#'){
            color = "#" + color;
        }
        return color; }

    public void setColor(String color){this.color = color;}

    public int getId(){ return id; }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(name);
        parcel.writeInt(id);
        parcel.writeString(color);
    }

    @Override
    public String toString() {
        return name;
    }
}
