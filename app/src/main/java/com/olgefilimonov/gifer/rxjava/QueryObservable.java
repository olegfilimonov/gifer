package com.olgefilimonov.gifer.rxjava;

import com.arlib.floatingsearchview.FloatingSearchView;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;

final public class QueryObservable extends Observable<CharSequence> {

  private final FloatingSearchView view;

  private final int minQueryLength;

  public QueryObservable(FloatingSearchView view) {
    this(view, 1);
  }

  public QueryObservable(FloatingSearchView view, int minQueryLength) {
    this.view = view;
    this.minQueryLength = minQueryLength;
  }

  @Override protected void subscribeActual(Observer<? super CharSequence> observer) {

    Listener listener = new Listener(view, observer, minQueryLength);
    observer.onSubscribe(listener);
    view.setOnQueryChangeListener(listener);
  }

  final static class Listener extends MainThreadDisposable implements FloatingSearchView.OnQueryChangeListener {

    private final FloatingSearchView view;
    private final Observer<? super CharSequence> observer;
    private final int minQueryLength;

    public Listener(FloatingSearchView view, Observer<? super CharSequence> observer, int minQueryLength) {
      this.view = view;
      this.observer = observer;
      this.minQueryLength = minQueryLength;
    }

    @Override public void onSearchTextChanged(String oldQuery, String newQuery) {
      if (!isDisposed() && newQuery != null && newQuery.length() >= minQueryLength) {
        observer.onNext(newQuery);
      }
    }

    @Override protected void onDispose() {
      view.setOnQueryChangeListener(null);
    }
  }
}