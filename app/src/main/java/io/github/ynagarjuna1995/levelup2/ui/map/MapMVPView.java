package io.github.ynagarjuna1995.levelup2.ui.map;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import io.github.ynagarjuna1995.levelup2.ui.base.MvpView;

public interface MapMVPView extends MvpView{

    void drawRouteOnMap(List<LatLng> positions);

    void showLocationsOnMap();
}
