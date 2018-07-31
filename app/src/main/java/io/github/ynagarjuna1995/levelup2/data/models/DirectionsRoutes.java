package io.github.ynagarjuna1995.levelup2.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class DirectionsRoutes implements Parcelable{


    @SerializedName("overview_polyline")
    public OverviewPolylines overview_polyline;


    protected DirectionsRoutes(Parcel in) {
        overview_polyline = in.readParcelable(OverviewPolylines.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(overview_polyline, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DirectionsRoutes> CREATOR = new Creator<DirectionsRoutes>() {
        @Override
        public DirectionsRoutes createFromParcel(Parcel in) {
            return new DirectionsRoutes(in);
        }

        @Override
        public DirectionsRoutes[] newArray(int size) {
            return new DirectionsRoutes[size];
        }
    };
}
