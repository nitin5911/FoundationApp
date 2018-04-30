package com.example.amit.yadavfoundation;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;

public class UserCurrentLocation extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,LocationListener {
    GoogleMap mGoogleMap_obj;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public  static final int REQUEST_LOCATION=1;
    GoogleApiClient mGoogleApiClient_obj;
    LocationRequest mLocationRequest;
    UiSettings uisettings_obj;
    double originlat,originlng;
    public LocationManager locationManager_obj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_current_location);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.userlocationmapid);
        mapFragment.getMapAsync(this);
    }//end of onCreate method

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap_obj = googleMap;
        mGoogleMap_obj.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        uisettings_obj = mGoogleMap_obj.getUiSettings();
        uisettings_obj.setZoomControlsEnabled(true);
        uisettings_obj.setAllGesturesEnabled(true);
        uisettings_obj.setCompassEnabled(true);
        uisettings_obj.setMapToolbarEnabled(false);
        uisettings_obj.setMyLocationButtonEnabled(true);
        uisettings_obj.isCompassEnabled();
        uisettings_obj.setIndoorLevelPickerEnabled(true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            checkLocationPermission();
        else{
            mGoogleMap_obj.setMyLocationEnabled(true);
            buildGoogleApiClient();
            //get GPS setting work
            locationSettingOpen_method();
            getCurrentLocation();
        }
    }//end of onMapReady method

    //  Here give the permission to access the map location
    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            // Should we show an explanation
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)&& ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                new AlertDialog.Builder(this).setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(UserCurrentLocation.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
                                ActivityCompat.requestPermissions(UserCurrentLocation.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        }).create().show();
            } else
            {
                ActivityCompat.requestPermissions(UserCurrentLocation.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
                ActivityCompat.requestPermissions(UserCurrentLocation.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }//end of checkLocationPermission method

    public void getCurrentLocation(){
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_LOCATION);
        }else {
            locationManager_obj = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            if(locationManager_obj!=null) {
                Location gpsLocation_obj = locationManager_obj.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                Location netLocation_obj = locationManager_obj.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (netLocation_obj == null) {
                    if (gpsLocation_obj != null) {
                        originlat = gpsLocation_obj.getLatitude();
                        originlng = gpsLocation_obj.getLongitude();
                        mGoogleMap_obj.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(originlat, originlng)));
                        mGoogleMap_obj.animateCamera(CameraUpdateFactory.zoomTo(15));
                        markerinfoalertbox_method();
                        Toast.makeText(getApplicationContext(), "gps location is " + originlat + " " + originlng, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Unable to find current location please GPS ON" + originlat + " " + originlng, Toast.LENGTH_SHORT).show();
                    }
                }//end of if condition
                else {
                    originlat = netLocation_obj.getLatitude();
                    originlng = netLocation_obj.getLongitude();
                    mGoogleMap_obj.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(originlat, originlng)));
                    mGoogleMap_obj.animateCamera(CameraUpdateFactory.zoomTo(15));
                    markerinfoalertbox_method();
                    Toast.makeText(getApplicationContext(), "network location is " + originlat + " " + originlng, Toast.LENGTH_SHORT).show();
                }
            }//end of inner main if condition
            else {
                Toast.makeText(getApplicationContext(), "Unable to find current location please GPS ON " + originlat + " " + originlng, Toast.LENGTH_SHORT).show();
            }
    }//end of outer main else condition
    }//end of method

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient_obj = new GoogleApiClient.Builder(this).addApi(LocationServices.API)
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
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient_obj, mLocationRequest, this);
        }
    }//end of onConnected method

    @Override
    public void onConnectionSuspended(int i) {}
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) { }

    private void locationSettingOpen_method() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest);
        builder.setAlwaysShow(true); //this is the key ingredient
        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient_obj, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                final LocationSettingsStates state = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            status.startResolutionForResult(UserCurrentLocation.this, 1000);
                        } catch (IntentSender.SendIntentException e) {
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        break;
                }//end of switch case
            }//end of onResult method
        }); //end of callback work
    }//end of location Setting Open method

    private void markerinfoalertbox_method() {
        Button okbtn;
        final AlertDialog dialog_obj;
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(false);

        LayoutInflater inflater = getLayoutInflater();
        View view1 = inflater.inflate(R.layout.locationonalertbox, null);
        builder.setView(view1);
        okbtn=view1.findViewById(R.id.okbtnid);
        dialog_obj = builder.create();
        dialog_obj.show();
        okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_obj.dismiss();
                Intent intent=new Intent(getApplicationContext(),NetworkActivity.class);
                startActivity(intent);
            }
        });
    }//end of marker info alertbox method


    @Override
    public void onLocationChanged(Location location) {
        mGoogleApiClient_obj.connect();
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient_obj, mLocationRequest, this);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mGoogleMap_obj.setMyLocationEnabled(true);
        locationSettingOpen_method();
    }//end of onLocationChanged

}//end of main class
