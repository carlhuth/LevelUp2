package io.github.ynagarjuna1995.levelup2.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GoogleDirections implements Parcelable {

    public String status;

    @SerializedName("routes")
    public ArrayList<DirectionsRoutes> Routes;

    protected GoogleDirections(Parcel in) {
        status = in.readString();
        Routes = in.createTypedArrayList(DirectionsRoutes.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(status);
        dest.writeTypedList(Routes);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GoogleDirections> CREATOR = new Creator<GoogleDirections>() {
        @Override
        public GoogleDirections createFromParcel(Parcel in) {
            return new GoogleDirections(in);
        }

        @Override
        public GoogleDirections[] newArray(int size) {
            return new GoogleDirections[size];
        }
    };
}
