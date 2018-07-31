package io.github.ynagarjuna1995.levelup2.ui.nearest;

import java.util.ArrayList;
import java.util.Map;

import io.github.ynagarjuna1995.levelup2.data.models.LandMarkLocations;
import io.github.ynagarjuna1995.levelup2.ui.base.MvpPresenter;

public interface NearestMVPPresenter<V extends NearestMVPView> extends MvpPresenter<V> {


    void sortLocations(ArrayList<LandMarkLocations> landMarkLocations);
}
