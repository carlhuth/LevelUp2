package io.github.ynagarjuna1995.levelup2.ui.search;

import io.github.ynagarjuna1995.levelup2.data.models.AddressPredictions;
import io.github.ynagarjuna1995.levelup2.di.scopes.PerActivity;
import io.github.ynagarjuna1995.levelup2.ui.base.MvpPresenter;

@PerActivity
public interface ISearchPresenter<V extends ISearchMVPView> extends MvpPresenter<V> {
    void processSelectedAddress(AddressPredictions prediction);
}
