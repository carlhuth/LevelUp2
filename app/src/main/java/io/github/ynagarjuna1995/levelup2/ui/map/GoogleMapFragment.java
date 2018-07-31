package io.github.ynagarjuna1995.levelup2.ui.map;

import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.SquareCap;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.ynagarjuna1995.levelup2.R;
import io.github.ynagarjuna1995.levelup2.data.models.LandMarkLocations;
import io.github.ynagarjuna1995.levelup2.di.components.ActivityComponent;
import io.github.ynagarjuna1995.levelup2.ui.base.BaseFragment;
import io.github.ynagarjuna1995.levelup2.ui.base.BaseSupportMapFragment;

import static com.google.android.gms.maps.model.JointType.ROUND;


public class GoogleMapFragment extends BaseFragment implements
        MapMVPView,
        OnMapReadyCallback,
        BaseSupportMapFragment.onMapFragmentReadyListener{

    public static final String TAG ="googleMapFragment" ;

    private static final String LANDMARK_LIST = "landmarkList";
    private ArrayList<LandMarkLocations> mLandMarkList;

    GoogleMap mGoogleMap;
    BaseSupportMapFragment mSupportMapFragment;
    private Polyline transparentPOlyline, greenPolyLine;
    private List<LatLng> positions;

    @Inject
    GoogleMapPresenter<MapMVPView> mGoogleMapPresenter;

    private ArrayList<Marker> markers;

    public GoogleMapFragment() {
        // Required empty public constructor
    }

    public static GoogleMapFragment newInstance(ArrayList<LandMarkLocations> mLandMarkList) {
        GoogleMapFragment fragment = new GoogleMapFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(LANDMARK_LIST, mLandMarkList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mLandMarkList = getArguments().getParcelableArrayList(LANDMARK_LIST);
        }

       markers = new ArrayList<Marker>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_google_map, container, false);
        ButterKnife.bind(this,view);
        initInjector(view);
        inflateGoogleMap();
        return view;
    }

    private void inflateGoogleMap() {
        if (mSupportMapFragment == null) {
            mSupportMapFragment = BaseSupportMapFragment.newInstance();
            getChildFragmentManager().beginTransaction().replace(R.id.mapFragContainer, mSupportMapFragment).commit();
        }
    }

    private void initInjector(View view) {
        ActivityComponent component = getActivityComponent();
        if (component !=null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            mGoogleMapPresenter.onAttach(this);
        }
    }

    @OnClick(R.id.nav_back_btn)
    void onNavBackClick() {
        getBaseActivity().onFragmentDetached(TAG);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        if (mGoogleMap !=null) {
            mGoogleMap.setIndoorEnabled(false);
            boolean state = mGoogleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            getActivity(), R.raw.dark_map_style));

            if (!state) {
                Toast.makeText(getActivity(), "Style not applied", Toast.LENGTH_SHORT).show();
            }
        }


        mGoogleMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                if (positions !=null)
                    animatePolyLine();
            }
        });
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void showLocationsOnMap() {
        if(mGoogleMap!=null) {
            try {

            if (markers != null && markers.size() > 0) {
                for (int i = 0; i < markers.size(); i++) {
                    markers.get(i).remove();
                }
                markers.clear();
            }

            for (int i = 0; i < mLandMarkList.size(); i++) {
                Double lat = mLandMarkList.get(i).lat;
                Double lon = mLandMarkList.get(i).lng;
                String title = mLandMarkList.get(i).description;
                if (lat != null && lon != null) {
                    LatLng ll = new LatLng(lat, lon);

                    LinearLayout tv = (LinearLayout) this.getLayoutInflater().inflate(R.layout.marker_layout, null, false);

                    tv.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

                    tv.layout(0, 0, tv.getMeasuredWidth(), tv.getMeasuredHeight());
                    TextView textView = tv.findViewById(R.id.location_description);
                    textView.setText(title);
                    tv.setDrawingCacheEnabled(true);
                    tv.buildDrawingCache();

                    Bitmap img = tv.getDrawingCache();
                    markers.add(mGoogleMap.addMarker(new MarkerOptions()
                            .position(ll)
                            .icon(BitmapDescriptorFactory.fromBitmap(img))));
                }
            }

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onMapFragmentReady() {
        mSupportMapFragment.getMapAsync(this);
    }


    @Override
    public void drawRouteOnMap(List<LatLng> positions) {

        this.positions = positions;
        PolylineOptions  transparentoptions = new PolylineOptions();
        transparentoptions.addAll(this.positions);


        transparentoptions.width(10);
        transparentoptions.color(Color.TRANSPARENT);
        transparentoptions.startCap(new SquareCap());
        transparentoptions.endCap(new SquareCap());
        transparentoptions.jointType(ROUND);
        transparentPOlyline = mGoogleMap.addPolyline(transparentoptions);


        PolylineOptions greenOptions = new PolylineOptions();
        greenOptions.width(10);
        greenOptions.color(Color.GREEN);
        greenOptions.startCap(new SquareCap());
        greenOptions.endCap(new SquareCap());
        greenOptions.jointType(ROUND);
        greenPolyLine = mGoogleMap.addPolyline(greenOptions);


        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(transparentPOlyline.getPoints().get(0));
        builder.include(transparentPOlyline.getPoints().get(transparentPOlyline.getPoints().size()-1));
        for (int i = 0; i <mLandMarkList.size() ; i++) {
            builder.include(mLandMarkList.get(i).latLng);
        }

        LatLngBounds bounds = builder.build();

        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds,160));

    }

    @Override
    public void viewCreated() {
        mGoogleMapPresenter.getEncodedPath(mLandMarkList);
    }

    private void animatePolyLine() {

        ValueAnimator animator = ValueAnimator.ofInt(0, 100);
        animator.setDuration(1600);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {

                List<LatLng> latLngList = greenPolyLine.getPoints();
                int initialPointSize = latLngList.size();
                int animatedValue = (int) animator.getAnimatedValue();
                int newPoints = (animatedValue * positions.size()) / 100;

                if (initialPointSize < newPoints ) {
                    latLngList.addAll(positions.subList(initialPointSize, newPoints));
                    greenPolyLine.setPoints(latLngList);
                }


            }
        });

        animator.setStartDelay(800);
        animator.start();
    }

//
//    private void animateMarkers() {
//        ValueAnimator animator = ValueAnimator.ofInt(0, 100);
//        animator.setDuration(3800);
//        animator.setInterpolator(new LinearInterpolator());
//
//        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animator) {
////                for (int i = 0; i < mLandMarkList.size(); i++) {
////                    Double lat = mLandMarkList.get(i).lat;
////                    Double lon = mLandMarkList.get(i).lng;
////                    String title = mLandMarkList.get(i).description;
////                    if (lat != null && lon != null) {
////                        LatLng ll = new LatLng(lat, lon);
////                        Bitmap img = BitmapFactory.decodeResource(getResources(), R.drawable.circle);
////                        markers.add(mGoogleMap.addMarker(new MarkerOptions()
////                                .position(ll)
////                                .title(title)
////                                .infoWindowAnchor(1f,0f)
////                                .icon(BitmapDescriptorFactory.fromBitmap(img))));
////                    }
////                }
//            }
//        });
//
//        animator.addListener(markerAnimationListner);
//        animator.start();
//    }

//
//    Animator.AnimatorListener markerAnimationListner = new Animator.AnimatorListener() {
//
//        int totalCount = mLandMarkList.size();
//
//        int count = 0;
//
//        @Override
//        public void onAnimationStart(Animator animator) {
//
//        }
//
//        @Override
//        public void onAnimationEnd(Animator animator) {
//            if (count <= totalCount)
//                showMarkers(count);
//        }
//
//        @Override
//        public void onAnimationCancel(Animator animator) {
//
//        }
//
//        @Override
//        public void onAnimationRepeat(Animator animator) {
//
//
//        }
//    };

//    private void showMarkers(int count) {
//        Double lat = mLandMarkList.get(count).lat;
//        Double lon = mLandMarkList.get(count).lng;
//        String title = mLandMarkList.get(count).description;
//        if (lat != null && lon != null) {
//            LatLng ll = new LatLng(lat, lon);
//            Bitmap img = BitmapFactory.decodeResource(getResources(), R.drawable.circle);
//            markers.add(mGoogleMap.addMarker(new MarkerOptions()
//                    .position(ll)
//                    .title(title)
//                    .infoWindowAnchor(1f,0f)
//                    .icon(BitmapDescriptorFactory.fromBitmap(img))));
//        }
//    }


//    Animator.AnimatorListener polyLineAnimationListener = new Animator.AnimatorListener() {
//        @Override
//        public void onAnimationStart(Animator animator) {
//            animateMarkers();
//        }
//
//        @Override
//        public void onAnimationEnd(Animator animator) {
//
//        }
//
//        @Override
//        public void onAnimationCancel(Animator animator) {
//
//        }
//
//        @Override
//        public void onAnimationRepeat(Animator animator) {
//
//
//        }
//    };
}
