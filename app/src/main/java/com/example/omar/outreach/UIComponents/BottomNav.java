package com.example.omar.outreach.UIComponents;

import android.content.Context;
import android.graphics.Canvas;
import android.support.design.widget.BottomNavigationView;
import android.util.AttributeSet;

import com.example.omar.outreach.Interfaces.BottomNavObserver;
import java.util.ArrayList;
import java.util.List;

public class BottomNav extends BottomNavigationView {

    List<BottomNavObserver> observerList;

    public BottomNav(Context context){
        super(context);
        this.observerList = new ArrayList<>();
    }

    public BottomNav(Context context, AttributeSet attrs, List<BottomNavObserver> observerList) {
        super(context, attrs);
        this.observerList = observerList;
    }

    public BottomNav(Context context, AttributeSet attrs, int defStyleAttr, List<BottomNavObserver> observerList) {
        super(context, attrs, defStyleAttr);
        this.observerList = observerList;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public void registerObserver(BottomNavObserver observer){
        observerList.add(observer);
    }

    public void notifyObserversAboutTheClick(int id){
        for(BottomNavObserver observer:observerList){
            observer.bottomNavClicked(id);
        }
    }

}
