package io.github.ynagarjuna1995.levelup2.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MainTextMatchedSubstring implements Parcelable {


    @SerializedName("length")
    @Expose
    public int length;
    @SerializedName("offset")
    @Expose
    public int offset;
    public final static Parcelable.Creator<MainTextMatchedSubstring> CREATOR = new Creator<MainTextMatchedSubstring>() {

        @SuppressWarnings({
                "unchecked"
        })
        public MainTextMatchedSubstring createFromParcel(Parcel in) {
            return new MainTextMatchedSubstring(in);
        }

        public MainTextMatchedSubstring[] newArray(int size) {
            return (new MainTextMatchedSubstring[size]);
        }

    }
            ;

    protected MainTextMatchedSubstring(Parcel in) {
        this.length = ((int) in.readValue((int.class.getClassLoader())));
        this.offset = ((int) in.readValue((int.class.getClassLoader())));
    }

    public MainTextMatchedSubstring() {
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(length);
        dest.writeValue(offset);
    }

    public int describeContents() {
        return 0;
    }
}
