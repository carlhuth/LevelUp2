package io.github.ynagarjuna1995.levelup2.ui.search;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.github.ynagarjuna1995.levelup2.common.Constants;
import io.github.ynagarjuna1995.levelup2.data.AppDataManager;
import io.github.ynagarjuna1995.levelup2.data.models.AddressPredictions;
import io.github.ynagarjuna1995.levelup2.data.models.GoogleAddressPredictions;
import io.github.ynagarjuna1995.levelup2.ui.base.BasePresenter;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class SearchPresenter<V extends ISearchMVPView> extends BasePresenter<V>
        implements ISearchPresenter<V>{

    private Map<String, String> googlePredictionsParmas;

    @Inject
    public SearchPresenter(AppDataManager dataManager,
                           CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);

        googlePredictionsParmas = new HashMap<>();
        googlePredictionsParmas.put("sensor", "true");
    }

    public void rxSearchBoxEvent(Observable<CharSequence> observable) {
        checkViewAttached();
        getCompositeDisposable().add(observable
                        .filter(charSequence -> !TextUtils.isEmpty(charSequence))
                        .throttleLast(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                        .map(charSequence -> charSequence.toString())
                        .switchMap(this::sendRequestToApiObservable)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(results -> {
                            getMvpView().showAddressPredictions(results.predictions);
                        }, error->{
                            getMvpView().showMessage(error.getMessage());
                        }));

    }

    private Observable<GoogleAddressPredictions> sendRequestToApiObservable(String charsequence) {
        googlePredictionsParmas.put("input",charsequence);

        return getAppDataManager()
                .getApiHelper()
                .getGoogleAddressPredictions(googlePredictionsParmas, Constants.GOOGLE_API_KEY).subscribeOn(Schedulers.io());
    }


    @Override
    public void processSelectedAddress(AddressPredictions prediction) {
        getMvpView().sendAddressAsResult(prediction);
    }
}
