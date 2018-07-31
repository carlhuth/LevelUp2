package io.github.ynagarjuna1995.levelup2.ui.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.github.ynagarjuna1995.levelup2.common.Constants;
import io.github.ynagarjuna1995.levelup2.data.AppDataManager;
import io.github.ynagarjuna1995.levelup2.data.models.LandMarkLocations;
import io.github.ynagarjuna1995.levelup2.data.models.OverviewPolylines;
import io.github.ynagarjuna1995.levelup2.ui.PolylineEncoding;
import io.github.ynagarjuna1995.levelup2.ui.base.BasePresenter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class GoogleMapPresenter <V extends MapMVPView> extends BasePresenter<V> implements GoogleMVPPresneter<V> {

    private Map<String, String> mGoogleDirectionsParams;

    @Inject
    public GoogleMapPresenter(AppDataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
        mGoogleDirectionsParams = new HashMap<>();
        mGoogleDirectionsParams.put("sensor","false");
    }

    @Override
    public Map<String ,String> buildDirectionParameters(ArrayList<LandMarkLocations> markLocationsArrayList) {

        String waypoints = "optimize:true|";
        String origin = "";
        String destination = "";
        for (int i = 0; i < markLocationsArrayList.size(); i++) {
            if (i==0) {
                origin = markLocationsArrayList.get(i).lat+","+markLocationsArrayList.get(i).lng;
            }else if (i == markLocationsArrayList.size()-1) {
                destination = markLocationsArrayList.get(i).lat+","+markLocationsArrayList.get(i).lng;
            } else {
                waypoints = waypoints+ markLocationsArrayList.get(i).lat+","+markLocationsArrayList.get(i).lng + "|";
            }
        }

        mGoogleDirectionsParams.put("waypoints",waypoints);
        mGoogleDirectionsParams.put("origin",origin);
        mGoogleDirectionsParams.put("destination",destination);


        return mGoogleDirectionsParams;

    }

    @Override
    public void getEncodedPath(ArrayList<LandMarkLocations> markLocationsArrayList) {
        checkViewAttached();
        getMvpView().showLoading();
        getCompositeDisposable().add(
                getAppDataManager().getApiHelper().getEncodedPath(buildDirectionParameters(markLocationsArrayList), Constants.GOOGLE_API_KEY)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(results -> {
                            processDirectionsResult(results.Routes.get(0).overview_polyline,markLocationsArrayList);
                        }, error->{
                            getMvpView().onError(error.getMessage());
                        }));

    }

    @Override
    public void processDirectionsResult(OverviewPolylines overview_polyline, ArrayList<LandMarkLocations> markLocationsArrayList) {
        getMvpView().drawRouteOnMap(PolylineEncoding.decode(overview_polyline.points));
        getMvpView().showLocationsOnMap();
        getMvpView().hideLoading();
    }
}
