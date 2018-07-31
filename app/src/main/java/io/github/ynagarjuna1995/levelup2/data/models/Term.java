package io.github.ynagarjuna1995.levelup2.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Term implements Parcelable {

    @SerializedName("offset")
    @Expose
    public int offset;
    @SerializedName("value")
    @Expose
    public String value;
    public final static Parcelable.Creator<Term> CREATOR = new Parcelable.Creator<Term>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Term createFromParcel(Parcel in) {
            return new Term(in);
        }

        public Term[] newArray(int size) {
            return (new Term[size]);
        }

    }
            ;

    protected Term(Parcel in) {
        this.offset = ((int) in.readValue((int.class.getClassLoader())));
        this.value = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Term() {
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(offset);
        dest.writeValue(value);
    }

    public int describeContents() {
        return 0;
    }
}
