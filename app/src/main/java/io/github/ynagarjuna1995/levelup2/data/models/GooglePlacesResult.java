package io.github.ynagarjuna1995.levelup2.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GooglePlacesResult implements Parcelable {

    @SerializedName("address_components")
    @Expose
    public List<AddressComponent> addressComponents = null;
    @SerializedName("adr_address")
    @Expose
    public String adrAddress;
    @SerializedName("formatted_address")
    @Expose
    public String formattedAddress;
    @SerializedName("geometry")
    @Expose
    public Geometry geometry;
    @SerializedName("icon")
    @Expose
    public String icon;
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("place_id")
    @Expose
    public String placeId;
    @SerializedName("reference")
    @Expose
    public String reference;

    @SerializedName("types")
    @Expose
    public List<String> types = null;
    public final static Parcelable.Creator<GooglePlacesResult> CREATOR = new Creator<GooglePlacesResult>() {

        @SuppressWarnings({
                "unchecked"
        })
        public GooglePlacesResult createFromParcel(Parcel in) {
            return new GooglePlacesResult(in);
        }

        public GooglePlacesResult[] newArray(int size) {
            return (new GooglePlacesResult[size]);
        }
    };

    protected GooglePlacesResult(Parcel in) {
        in.readList(this.addressComponents, (AddressComponent.class.getClassLoader()));
        this.adrAddress = ((String) in.readValue((String.class.getClassLoader())));
        this.formattedAddress = ((String) in.readValue((String.class.getClassLoader())));
        this.geometry = ((Geometry) in.readValue((Geometry.class.getClassLoader())));
        this.icon = ((String) in.readValue((String.class.getClassLoader())));
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.placeId = ((String) in.readValue((String.class.getClassLoader())));
        this.reference = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.types, (java.lang.String.class.getClassLoader()));
    }

    public GooglePlacesResult() {
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(addressComponents);
        dest.writeValue(adrAddress);
        dest.writeValue(formattedAddress);
        dest.writeValue(geometry);
        dest.writeValue(icon);
        dest.writeValue(id);
        dest.writeValue(name);
        dest.writeValue(placeId);
        dest.writeValue(reference);
        dest.writeList(types);

    }

    public int describeContents() {
        return 0;
    }
}
