package com.example.amit.yadavfoundation;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;

public class Network_Frag extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,LocationListener,ClusterManager.OnClusterItemClickListener,
        GoogleMap.OnInfoWindowClickListener{
    private static final String ARG_PARAM1 = "param1",ARG_PARAM2 = "param2";
    private String mParam1,mParam2;

    GoogleMap mGoogleMap_obj;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    GoogleApiClient mGoogleApiClient_obj;
    UiSettings uisettings_obj;
    double originlat,originlng;
    public LocationManager locationManager_obj;
    public  static final int REQUEST_LOCATION=1;
    LocationRequest mLocationRequest;
    ClusterManager<MyItem> clusterManager_obj;
    MapView mMapView;

    private OnFragmentInteractionListener mListener;
    public Network_Frag() {}
    public static Network_Frag newInstance(String param1, String param2) {
        Network_Frag fragment = new Network_Frag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment; }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2); } }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View network_view=inflater.inflate(R.layout.fragment_network_, container, false);
        // Obtain the mapView when the map is ready to be used.
        mMapView =network_view.findViewById(R.id.networkmapid);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume(); // needed to get the map to display immediately
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mMapView.getMapAsync(this);

//        SupportMapFragment mapFragment = (SupportMapFragment)getActivity().getSupportFragmentManager().findFragmentById(R.id.networkmapid);
//        mapFragment.getMapAsync(this);
        return network_view;
    }//end of onCreateView method

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri); } }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
          //  throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        } }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null; }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap_obj = googleMap;
        mGoogleMap_obj.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        //Google map UI setting work
        uisettings_obj = mGoogleMap_obj.getUiSettings();
        uisettings_obj.setZoomControlsEnabled(true);
        uisettings_obj.setAllGesturesEnabled(true);
        uisettings_obj.setCompassEnabled(true);
        uisettings_obj.setMapToolbarEnabled(false);
        uisettings_obj.setMyLocationButtonEnabled(true);
        uisettings_obj.setCompassEnabled(true);
        uisettings_obj.isCompassEnabled();
        uisettings_obj.setIndoorLevelPickerEnabled(true);
        //Initialize Google Play Services
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                getCurrentLocation();
                buildGoogleApiClient();
                mGoogleMap_obj.setMyLocationEnabled(true);
                markeroperations_method();
            } else {
                //Request Location Permission
                checkLocationPermission();
            }
        }
    }//end of onMapReady method

    private void markeroperations_method() {
        // Position the map.
        mGoogleMap_obj.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(originlat, originlng), 10));
        mGoogleMap_obj.animateCamera(CameraUpdateFactory.zoomTo(15));
        //Marker options initialize
        MarkerOptions destmarkerOption_obj = new MarkerOptions();
        //marker icon set
        destmarkerOption_obj.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
      //  destlat=alllocations_list.get(0);
        //Add markers
        mGoogleMap_obj.addMarker(destmarkerOption_obj.position(new LatLng(30.7105, 76.7128)).title("User 1 Name").snippet("View Profile"));
        mGoogleMap_obj.addMarker(destmarkerOption_obj.position(new LatLng(30.7196, 76.6961)).title("User 2 Name").snippet("View Profile"));
        mGoogleMap_obj.addMarker(destmarkerOption_obj.position(new LatLng(30.7223, 76.7032)).title("User 3 Name").snippet("View Profile"));
        mGoogleMap_obj.addMarker(destmarkerOption_obj.position(new LatLng(30.67995, 76.72211)).title("User 4 Name").snippet("View Profile"));
        mGoogleMap_obj.addMarker(destmarkerOption_obj.position(new LatLng(30.7145, 76.7149)).title("User 5 Name").snippet("View Profile"));
        mGoogleMap_obj.addMarker(destmarkerOption_obj.position(new LatLng(30.7241, 76.7174)).title("User 6 Name").snippet("View Profile"));
        mGoogleMap_obj.setOnInfoWindowClickListener(this);

//        clusterManager_obj = new ClusterManager<MyItem>(getActivity(),mGoogleMap_obj);
//        // Point the map's listeners at the listeners implemented by the cluster manager.
//        mGoogleMap_obj.setOnMarkerClickListener(clusterManager_obj);
//        mGoogleMap_obj.setOnInfoWindowClickListener(clusterManager_obj);
//        clusterManager_obj.setOnClusterClickListener(new ClusterManager.OnClusterClickListener<MyItem>() {
//            @Override
//            public boolean onClusterClick(Cluster<MyItem> cluster) {
//                mGoogleMap_obj.animateCamera(CameraUpdateFactory.newLatLngZoom(cluster.getPosition(),(float) Math.floor(mGoogleMap_obj.getCameraPosition().zoom+2)), 500, null);
//                return true;
//            }//end of onClusterClick method
//        });        clusterManager_obj.setOnClusterItemClickListener(this);
//        mGoogleMap_obj.setOnInfoWindowClickListener(this);
//        // Add cluster items (markers) to the cluster manager.
//        addItems();
    }//end of marker operations method

    private void addItems() {
        MyItem offsetItem1 = new MyItem(30.7105, 76.7128,"User 1 Name","View Profile");
        MyItem offsetItem2 = new MyItem(30.7196, 76.6961,"User 2 Name","View Profile");
        MyItem offsetItem3 = new MyItem(30.7223, 76.7032,"User 3 Name","View Profile");
        MyItem offsetItem4 = new MyItem(30.67995, 76.72211,"User 4 Name","View Profile");
        MyItem offsetItem5 = new MyItem(30.7145, 76.7149,"User 5 Name","View Profile");
        MyItem offsetItem6 = new MyItem(30.7241, 76.7174,"User 6 Name","View Profile");
        clusterManager_obj.addItem(offsetItem1);
        clusterManager_obj.addItem(offsetItem2);
        clusterManager_obj.addItem(offsetItem3);
        clusterManager_obj.addItem(offsetItem4);
        clusterManager_obj.addItem(offsetItem5);
        clusterManager_obj.addItem(offsetItem6);
    }//end of addItems Method

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient_obj = new GoogleApiClient.Builder(getActivity()).addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();
        mGoogleApiClient_obj.connect();
    }//end of buildGoogleApiClient method
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(10000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient_obj, mLocationRequest, this);
        }
    }//end of onConnected method

    @Override
    public void onConnectionSuspended(int i) {}
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) { }

    @Override
    public void onLocationChanged(Location location) {
        mGoogleApiClient_obj.connect();
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient_obj, mLocationRequest, this);
        }
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mGoogleMap_obj.setMyLocationEnabled(true);
    }//end of onLocationChanged

    public void getCurrentLocation(){
        if(ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_LOCATION);
        }else {
            locationManager_obj = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            if(locationManager_obj!=null) {
                Location gpsLocation_obj = locationManager_obj.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                Location netLocation_obj = locationManager_obj.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (netLocation_obj == null) {
                    if (gpsLocation_obj != null) {
                        originlat = gpsLocation_obj.getLatitude();
                        originlng = gpsLocation_obj.getLongitude();
                        markeroperations_method();
                        Toast.makeText(getActivity(), "gps location is " + originlat + " " + originlng, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "Unable to find current location " + originlat + " " + originlng, Toast.LENGTH_SHORT).show();
                    }
                }//end of if condition
                else {
                    originlat = netLocation_obj.getLatitude();
                    originlng = netLocation_obj.getLongitude();
                    markeroperations_method();
                    Toast.makeText(getActivity(), "network location is " + originlat + " " + originlng, Toast.LENGTH_SHORT).show();
                }
            }//end of inner main if condition
            else {
                Toast.makeText(getActivity(), "Unable to find current location " + originlat + " " + originlng, Toast.LENGTH_SHORT).show();
            }
        }//end of outer main else condition
    }//end of method


    //  Here give the permission to access the map location
    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            // Should we show an explanation
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)&& ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)) {
                new AlertDialog.Builder(getActivity()).setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
                                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        }).create().show();
            } else
            {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }//end of checkLocationPermission method

    @Override
    public boolean onClusterItemClick(ClusterItem clusterItem) {
        return true;
    }//end of onClusterItemClick method

    @Override
    public void onInfoWindowClick(Marker marker) {
        Intent intent_obj=new Intent(getActivity(),UserProfile.class);
        startActivity(intent_obj);
    }//end of onInfoWindowClick method

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri); }
}//end of main class