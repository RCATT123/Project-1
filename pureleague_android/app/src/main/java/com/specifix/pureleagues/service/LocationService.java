package com.specifix.pureleagues.service;

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.os.ResultReceiver;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationService extends IntentService {
    public static final int SUCCESS_RESULT = 0;
    public static final int FAILURE_RESULT = 1;
    public static final String PACKAGE_NAME = "com.specifix.pureleagues.service";
    public static final String RECEIVER = PACKAGE_NAME + ".RECEIVER";
    public static final String LOCATION_DATA_EXTRA = "location_data";
    public static final String LOCATION_LATITUDE = "latitude";
    public static final String LOCATION_LONGITUDE = "longitude";
    protected ResultReceiver mReceiver;

    public LocationService() {
        super("");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        mReceiver = intent.getParcelableExtra(LocationService.RECEIVER);
        String strAddress = intent.getStringExtra(LOCATION_DATA_EXTRA);
        if (strAddress == null) return;

        Geocoder coder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = coder.getFromLocationName(strAddress, 1);

            if (addresses == null || addresses.size() == 0) {

            } else {
                Address address = addresses.get(0);
                deliverResultToReceiver(address.getLatitude(),address.getLongitude());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deliverResultToReceiver(double latitude, double longitude) {
        Bundle bundle = new Bundle();
        bundle.putDouble(LOCATION_LATITUDE, latitude);
        bundle.putDouble(LOCATION_LONGITUDE, longitude);
        mReceiver.send(SUCCESS_RESULT, bundle);
    }
}
