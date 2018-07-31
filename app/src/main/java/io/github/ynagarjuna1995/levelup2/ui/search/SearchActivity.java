package io.github.ynagarjuna1995.levelup2.ui.search;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;

import com.jakewharton.rxbinding2.widget.RxSearchView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.ynagarjuna1995.MyApplication;
import io.github.ynagarjuna1995.levelup2.ClearableEditText;
import io.github.ynagarjuna1995.levelup2.R;
import io.github.ynagarjuna1995.levelup2.data.models.AddressPredictions;
import io.github.ynagarjuna1995.levelup2.data.models.LandMarkLocations;
import io.github.ynagarjuna1995.levelup2.di.components.ActivityComponent;
import io.github.ynagarjuna1995.levelup2.di.components.DaggerActivityComponent;
import io.github.ynagarjuna1995.levelup2.di.modules.ActivityModule;
import io.github.ynagarjuna1995.levelup2.ui.base.BaseActivity;

public class SearchActivity extends BaseActivity implements ISearchMVPView,AddressPredictionsAdapter.AdapterClickListner {

    @Inject
    SearchPresenter<ISearchMVPView> mSearchPresenter;

    @Inject
    AddressPredictionsAdapter mAddressPredictionsAdapter;

    @BindView(R.id.address_predictions_rv)
    RecyclerView mAddressPredictionsRV;

    ActivityComponent mActivityComponent;

    @BindView(R.id.custom_search_view)
    ClearableEditText mSearchEditText;

    // outofscope of butterknife
    Button clearBtn;

    private LinearLayoutManager mLinearLayoutManager;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, SearchActivity.class);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        injectActivityComponent();
        mActivityComponent.inject(this);
        setUnBinder(ButterKnife.bind(this));
        setupAdddressPredictionRecycler();
        mSearchPresenter.onAttach(this);

        clearBtn = mSearchEditText.getClearBtn();

        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAddressPredictionsAdapter.clear();
            }
        });

        mSearchPresenter.rxSearchBoxEvent(RxTextView.textChanges(mSearchEditText.getEditText()));
    }

    private void setupAdddressPredictionRecycler() {
        mAddressPredictionsRV.setAdapter(mAddressPredictionsAdapter);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mAddressPredictionsRV.setLayoutManager(mLinearLayoutManager);
        mAddressPredictionsRV.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    private void injectActivityComponent() {
        if (mActivityComponent == null) {
            mActivityComponent = DaggerActivityComponent.builder()
                    .activityModule(new ActivityModule(this))
                    .applicationComponent(((MyApplication)getApplication()).getComponent())
                    .build();
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void selectedAddress(AddressPredictions prediction) {
        mSearchPresenter.processSelectedAddress(prediction);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSearchPresenter.onDetach();
    }

    @Override
    public void showAddressPredictions(ArrayList<AddressPredictions> addressPredictions) {
        mAddressPredictionsAdapter.add(addressPredictions);
    }

    @Override
    public void sendAddressAsResult(AddressPredictions predictions) {
        Intent intent = new Intent();
        intent.putExtra("address", predictions);
        setResult(600,intent);
        finish();
    }
}

