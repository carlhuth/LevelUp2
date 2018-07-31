package io.github.ynagarjuna1995.levelup2.data.models;

import android.os.Parcel;
import android.os.Parcelable;

public class OverviewPolylines implements Parcelable{

    public String points;

    protected OverviewPolylines(Parcel in) {
        points = in.readString();
    }

    public static final Creator<OverviewPolylines> CREATOR = new Creator<OverviewPolylines>() {
        @Override
        public OverviewPolylines createFromParcel(Parcel in) {
            return new OverviewPolylines(in);
        }

        @Override
        public OverviewPolylines[] newArray(int size) {
            return new OverviewPolylines[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(points);
    }
}
