package io.github.ynagarjuna1995.levelup2.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GooglePlaceLocation implements Parcelable {

    @SerializedName("lat")
    @Expose
    public Double lat;
    @SerializedName("lng")
    @Expose
    public Double lng;
    public final static Parcelable.Creator<GooglePlaceLocation> CREATOR = new Creator<GooglePlaceLocation>() {


        @SuppressWarnings({
                "unchecked"
        })
        public GooglePlaceLocation createFromParcel(Parcel in) {
            return new GooglePlaceLocation(in);
        }

        public GooglePlaceLocation[] newArray(int size) {
            return (new GooglePlaceLocation[size]);
        }

    }
            ;

    protected GooglePlaceLocation(Parcel in) {
        this.lat = ((Double) in.readValue((Double.class.getClassLoader())));
        this.lng = ((Double) in.readValue((Double.class.getClassLoader())));
    }

    public GooglePlaceLocation() {
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(lat);
        dest.writeValue(lng);
    }

    public int describeContents() {
        return 0;
    }
}
