package io.github.ynagarjuna1995.levelup2.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MatchedSubstring implements Parcelable {

    @SerializedName("length")
    @Expose
    public int length;
    @SerializedName("offset")
    @Expose
    public int offset;
    public final static Parcelable.Creator<MatchedSubstring> CREATOR = new Creator<MatchedSubstring>() {


        @SuppressWarnings({
                "unchecked"
        })
        public MatchedSubstring createFromParcel(Parcel in) {
            return new MatchedSubstring(in);
        }

        public MatchedSubstring[] newArray(int size) {
            return (new MatchedSubstring[size]);
        }

    }
            ;

    protected MatchedSubstring(Parcel in) {
        this.length = ((int) in.readValue((int.class.getClassLoader())));
        this.offset = ((int) in.readValue((int.class.getClassLoader())));
    }

    public MatchedSubstring() {
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(length);
        dest.writeValue(offset);
    }

    public int describeContents() {
        return 0;
    }
}
