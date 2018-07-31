package io.github.ynagarjuna1995.levelup2;


import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class CompositeListener implements View.OnClickListener {

    private List<View.OnClickListener> registeredListeners = new ArrayList<View.OnClickListener>();

    public void registerListener (View.OnClickListener listener) {
        registeredListeners.add(listener);
    }

    @Override
    public void onClick(View view) {
        for(View.OnClickListener listener:registeredListeners) {
            listener.onClick(view);
        }
    }
}