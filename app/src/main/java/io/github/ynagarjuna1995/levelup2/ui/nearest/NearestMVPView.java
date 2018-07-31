package io.github.ynagarjuna1995.levelup2.ui.nearest;

import java.util.ArrayList;

import io.github.ynagarjuna1995.levelup2.data.models.GooglePlacesGeocoding;
import io.github.ynagarjuna1995.levelup2.data.models.LandMarkLocations;
import io.github.ynagarjuna1995.levelup2.ui.base.MvpView;

public interface NearestMVPView extends MvpView {

    void showNearestLocations(ArrayList<LandMarkLocations> landMarkLocations);
}
