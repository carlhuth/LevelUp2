package io.github.ynagarjuna1995.levelup2.ui.map;

import java.util.ArrayList;
import java.util.Map;

import io.github.ynagarjuna1995.levelup2.data.models.LandMarkLocations;
import io.github.ynagarjuna1995.levelup2.data.models.OverviewPolylines;
import io.github.ynagarjuna1995.levelup2.ui.base.MvpPresenter;
import io.github.ynagarjuna1995.levelup2.ui.base.MvpView;

public interface GoogleMVPPresneter<V extends MapMVPView> extends MvpPresenter<V> {

    Map<String,String > buildDirectionParameters(ArrayList<LandMarkLocations> markLocationsArrayList);

    void getEncodedPath(ArrayList<LandMarkLocations> markLocationsArrayList);

    void processDirectionsResult(OverviewPolylines overview_polyline, ArrayList<LandMarkLocations> markLocationsArrayList);
}
