package io.github.ynagarjuna1995.levelup2.ui.main;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.github.ynagarjuna1995.levelup2.common.Constants;
import io.github.ynagarjuna1995.levelup2.data.AppDataManager;
import io.github.ynagarjuna1995.levelup2.data.models.AddressPredictions;
import io.github.ynagarjuna1995.levelup2.data.models.GooglePlaceLocation;
import io.github.ynagarjuna1995.levelup2.data.models.GooglePlacesResult;
import io.github.ynagarjuna1995.levelup2.data.models.LandMarkLocations;
import io.github.ynagarjuna1995.levelup2.ui.base.BasePresenter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MainPresenter<V extends MainMvpView> extends BasePresenter<V>
        implements MainMvpPresenter<V> {

    private static final String TAG = "MainPresenter";

    private Map<String, String> googlePredictionsParmas;

    private ArrayList<LandMarkLocations> landMarkLocationsArrayList;

    @Inject
    public MainPresenter(AppDataManager appDataManager,
                         CompositeDisposable compositeDisposable) {
        super( appDataManager,compositeDisposable);
        googlePredictionsParmas = new HashMap<>();
        googlePredictionsParmas.put("place_id","");
        landMarkLocationsArrayList = new ArrayList<>();
    }

    @Override
    public void onLandMarkLimitReached() {

    }

    @Override
    public void removeLandMarkInList(int pos) {
        landMarkLocationsArrayList.remove(pos);
        getMvpView().notifyLandMarkDeletion(pos);
        if (landMarkLocationsArrayList.isEmpty()) {
            getMvpView().showUserActionInfoText();
        }
    }


    @Override
    public void openSearcActivityForResult() {
        if (landMarkLocationsArrayList.size() >=10) {
            getMvpView().onError("You can add up to 10 locations.");
        } else {
            getMvpView().openSearchActivity();
        }
    }

    @Override
    public void showNearestLandMarkFragment() {
        if (landMarkLocationsArrayList.size() > 2 )
            getMvpView().showNearestLandmarkFragment();
        else
            getMvpView().showMessage("Please add more than 2 locations to continue.");
    }

    @Override
    public void showGoogleMapFragment() {
        if (landMarkLocationsArrayList.size() >=2)
            getMvpView().showGoogleMapFragment();
        else
            getMvpView().showMessage("Please add atleast 2 locations to continue");
    }

    @Override
    public void onViewInitialized() {

    }

    @Override
    public void parseLocationFromPlaceId(AddressPredictions addressPredictions) {
        checkViewAttached();
        getMvpView().showLoading();
        googlePredictionsParmas.put("place_id",addressPredictions.placeId);
        getCompositeDisposable().add(
                        getAppDataManager().getApiHelper().getLatLongFromPlaceId(googlePredictionsParmas,Constants.GOOGLE_API_KEY)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(results -> {
                            processGeocodingResult(results.results,addressPredictions.description);
                        }, error ->{
                            getMvpView().showMessage("Error occured while fetching lat and long from placeid");
                        }));
    }

    public void processGeocodingResult(ArrayList<GooglePlacesResult> results,String description) {
        getMvpView().hideUserActionInfoText();
        GooglePlaceLocation googlePlacesLocation = results.get(0).geometry.location;

        LatLng latLng = new LatLng(googlePlacesLocation.lat,
                googlePlacesLocation.lng);

        LandMarkLocations landMarkLocations = new LandMarkLocations(
                                description,
                                googlePlacesLocation.lat,
                                googlePlacesLocation.lng,
                                null,
                                latLng);

        if (landMarkLocationsArrayList.size() >=10) {
            getMvpView().onError("You can add up to 10 locations.");
        } else {
            landMarkLocationsArrayList.add(landMarkLocations);
            getMvpView().showAddedLandMarkInList(landMarkLocations);
        }

        getMvpView().hideLoading();
    }

    public ArrayList<LandMarkLocations> getLandMarkLocationsArrayList() {
        return landMarkLocationsArrayList;
    }
}
