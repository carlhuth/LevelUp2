package io.github.ynagarjuna1995.levelup2.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class GoogleAddressPredictions implements Parcelable{

    public ArrayList<AddressPredictions> predictions;
    public String status;

    protected GoogleAddressPredictions(Parcel in) {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GoogleAddressPredictions> CREATOR = new Creator<GoogleAddressPredictions>() {
        @Override
        public GoogleAddressPredictions createFromParcel(Parcel in) {
            return new GoogleAddressPredictions(in);
        }

        @Override
        public GoogleAddressPredictions[] newArray(int size) {
            return new GoogleAddressPredictions[size];
        }
    };
}
