package io.github.ynagarjuna1995.levelup2.data.api;

import java.util.Map;

import io.github.ynagarjuna1995.levelup2.data.models.GoogleAddressPredictions;
import io.github.ynagarjuna1995.levelup2.data.models.GoogleDirections;
import io.github.ynagarjuna1995.levelup2.data.models.GooglePlacesGeocoding;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface GoogleAPIHelper {

    @GET("maps/api/place/autocomplete/json")
    Observable<GoogleAddressPredictions> getGoogleAddressPredictions(
                                            @QueryMap Map<String,String> options,
                                            @Query("key") String apiKey);

    @GET("maps/api/geocode/json")
    Observable<GooglePlacesGeocoding> getLatLongFromPlaceId(
                                            @QueryMap Map<String,String> options,
                                            @Query("key") String apiKey);
//    Example call
//    https://maps.googleapis.com/maps/api/directions/json?
//                      origin=12.9083,77.64760
//                      &destination=12.9206,77.62981
//                      &waypoints=optimize:true|13.0091,77.711|12.959824,77.7016
//                      &sensor=false
//                      &api_key=key

    @GET("maps/api/directions/json")
    Observable<GoogleDirections> getEncodedPath(
                                             @QueryMap Map<String,String> options,
                                             @Query("api_key") String apiKey );
}
