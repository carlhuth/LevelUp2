package io.github.ynagarjuna1995.levelup2.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class AddressPredictions implements Parcelable {

    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("matched_substrings")
    @Expose
    public ArrayList<MatchedSubstring> matchedSubstrings = null;
    @SerializedName("place_id")
    @Expose
    public String placeId;
    @SerializedName("reference")
    @Expose
    public String reference;
//    @SerializedName("structured_formatting")
//    @Expose
//    public StructuredFormatting structuredFormatting;
//    @SerializedName("terms")
//    @Expose
//    public ArrayList<Term> terms = null;
//    @SerializedName("types")
//    @Expose
//    public ArrayList<String> types = null;

    protected AddressPredictions(Parcel in) {
        description = in.readString();
        id = in.readString();
        matchedSubstrings = in.createTypedArrayList(MatchedSubstring.CREATOR);
        placeId = in.readString();
        reference = in.readString();
//        structuredFormatting = in.readParcelable(StructuredFormatting.class.getClassLoader());
//        terms = in.createTypedArrayList(Term.CREATOR);
//        types = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(description);
        dest.writeString(id);
        dest.writeTypedList(matchedSubstrings);
        dest.writeString(placeId);
        dest.writeString(reference);
//        dest.writeParcelable(structuredFormatting, flags);
//        dest.writeTypedList(terms);
//        dest.writeStringList(types);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AddressPredictions> CREATOR = new Creator<AddressPredictions>() {
        @Override
        public AddressPredictions createFromParcel(Parcel in) {
            return new AddressPredictions(in);
        }

        @Override
        public AddressPredictions[] newArray(int size) {
            return new AddressPredictions[size];
        }
    };
}
