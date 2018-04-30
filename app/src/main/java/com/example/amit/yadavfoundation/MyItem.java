package com.example.amit.yadavfoundation;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/**
 * Created by Amit on 16-01-2018.
 */

public class MyItem implements ClusterItem {

    private final LatLng mPosition;
    private String mTitle = null;
    private String mSnippet=null;

    public MyItem(double lat, double lng) {
        mPosition = new LatLng(lat, lng);
    }

    public MyItem(double lat, double lng, String title, String snippet) {
        mPosition = new LatLng(lat, lng);
        mTitle = title;
        mSnippet = snippet;
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }
    public String getTitle() {return mTitle;}
    public String getSnippet() {
        return mSnippet;
    }
}//end of MyItem class
