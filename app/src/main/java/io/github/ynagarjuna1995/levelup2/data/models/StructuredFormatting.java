package io.github.ynagarjuna1995.levelup2.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StructuredFormatting implements Parcelable {

    @SerializedName("main_text")
    @Expose
    public String mainText;
    @SerializedName("main_text_matched_substrings")
    @Expose
    public List<MainTextMatchedSubstring> mainTextMatchedSubstrings = null;
    @SerializedName("secondary_text")
    @Expose
    public String secondaryText;
    public final static Parcelable.Creator<StructuredFormatting> CREATOR = new Creator<StructuredFormatting>() {


        @SuppressWarnings({
                "unchecked"
        })
        public StructuredFormatting createFromParcel(Parcel in) {
            return new StructuredFormatting(in);
        }

        public StructuredFormatting[] newArray(int size) {
            return (new StructuredFormatting[size]);
        }

    }
            ;

    protected StructuredFormatting(Parcel in) {
        this.mainText = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.mainTextMatchedSubstrings, (MainTextMatchedSubstring.class.getClassLoader()));
        this.secondaryText = ((String) in.readValue((String.class.getClassLoader())));
    }

    public StructuredFormatting() {
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(mainText);
        dest.writeList(mainTextMatchedSubstrings);
        dest.writeValue(secondaryText);
    }

    public int describeContents() {
        return 0;
    }
}
