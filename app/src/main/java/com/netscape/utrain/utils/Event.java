package com.netscape.utrain.utils;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;

/**
 * Created by Hugo Andrade on 25/03/2018.
 */

public class Event implements Parcelable {

    private String mID;
    private String mTitle;
    private String mType;
    private Calendar mDate;
    private int mColor;


    public Event(String id, String title, Calendar date, int color, String type) {
        mID = id;
        mTitle = title;
        mType = type;
        mDate = date;
        mColor = color;

    }

    public String getmType() {
        return mType;
    }

    public void setmType(String mType) {
        this.mType = mType;
    }

    public String getID() {
        return mID;
    }

    public String getTitle() {
        return mTitle;
    }

    public Calendar getDate() {
        return mDate;
    }

    public int getColor() {
        return mColor;
    }



    protected Event(Parcel in) {
        mID = in.readString();
        mTitle = in.readString();
        mType = in.readString();
        mColor = in.readInt();
        mDate = (Calendar) in.readSerializable();

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mID);
        dest.writeString(mTitle);
        dest.writeString(mType);
        dest.writeInt(mColor);
        dest.writeSerializable(mDate);

    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };
}
