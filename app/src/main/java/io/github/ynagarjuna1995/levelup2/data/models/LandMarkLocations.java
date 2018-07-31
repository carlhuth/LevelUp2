package io.github.ynagarjuna1995.levelup2.data.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.android.gms.common.util.MapUtils;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;
import com.google.maps.android.projection.Point;
import com.google.maps.android.projection.SphericalMercatorProjection;

import java.util.Comparator;

public class LandMarkLocations implements Parcelable, Comparable<LandMarkLocations> {

    public String description;
    public LatLng latLng;
    public Double lat;
    public Double lng;
    public Double distance;
    public Point point;

    public LandMarkLocations(String addressDescription, Double lat, Double lng, Double distance,LatLng latLng) {
        this.description = addressDescription;
        this.lat = lat;
        this.lng = lng;
        this.distance = null;
        this.latLng = latLng;
    }

    public LandMarkLocations(Parcel in) {
        description = in.readString();
        latLng = in.readParcelable(LatLng.class.getClassLoader());
        if (in.readByte() == 0) {
            lat = null;
        } else {
            lat = in.readDouble();
        }
        if (in.readByte() == 0) {
            lng = null;
        } else {
            lng = in.readDouble();
        }
        if (in.readByte() == 0) {
            distance = null;
        } else {
            distance = in.readDouble();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(description);
        dest.writeParcelable(latLng, flags);
        if (lat == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(lat);
        }
        if (lng == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(lng);
        }
        if (distance == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(distance);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LandMarkLocations> CREATOR = new Creator<LandMarkLocations>() {
        @Override
        public LandMarkLocations createFromParcel(Parcel in) {
            return new LandMarkLocations(in);
        }

        @Override
        public LandMarkLocations[] newArray(int size) {
            return new LandMarkLocations[size];
        }
    };

    @Override
    public int compareTo(@NonNull LandMarkLocations landMarkLocations) {
        return 0;
    }
}
