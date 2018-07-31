package io.github.ynagarjuna1995.levelup2.ui.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.SupportMapFragment;

public class BaseSupportMapFragment extends SupportMapFragment{

    public View mOriginalContentView;

    public static interface onMapFragmentReadyListener{
        void onMapFragmentReady();
        void viewCreated();
    }

    public BaseSupportMapFragment() {
        super();
        // Required empty public constructor
    }

    public static BaseSupportMapFragment newInstance() {
        return new BaseSupportMapFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        mOriginalContentView = super.onCreateView(inflater,container,savedInstanceState);
        Fragment fragment = getParentFragment();
        if(fragment !=null && fragment instanceof onMapFragmentReadyListener){
            ((onMapFragmentReadyListener) fragment).onMapFragmentReady();

        }
        return mOriginalContentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Fragment fragment = getParentFragment();
        if(fragment !=null && fragment instanceof onMapFragmentReadyListener){
            ((onMapFragmentReadyListener) fragment).viewCreated();

        }
    }

    @Nullable
    @Override
    public View getView() {
        return mOriginalContentView;
    }
}
