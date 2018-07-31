package io.github.ynagarjuna1995.levelup2.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GooglePlacesGeocoding implements Parcelable {

    @SerializedName("results")
    @Expose
    public ArrayList<GooglePlacesResult> results = null;

    @SerializedName("status")
    @Expose
    public String status;

    protected GooglePlacesGeocoding(Parcel in) {
        results = in.createTypedArrayList(GooglePlacesResult.CREATOR);
        status = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(results);
        dest.writeString(status);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GooglePlacesGeocoding> CREATOR = new Creator<GooglePlacesGeocoding>() {
        @Override
        public GooglePlacesGeocoding createFromParcel(Parcel in) {
            return new GooglePlacesGeocoding(in);
        }

        @Override
        public GooglePlacesGeocoding[] newArray(int size) {
            return new GooglePlacesGeocoding[size];
        }
    };
}
