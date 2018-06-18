package com.specifix.pureleagues.manager;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationManager {

    public static void findLocation(final Context context, String url, final LocationReceiver listener) {
        if (context == null)
            return;

        AsyncTask<String, Void, LatLng> task = new AsyncTask<String, Void, LatLng>() {
            String errorMessage;

            @Override
            protected LatLng doInBackground(String... params) {
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                List<Address> addresses = null;

                String name = params[0];
                try {
                    addresses = geocoder.getFromLocationName(name, 1);
                } catch (IOException e) {
                    Log.e("GEO_POINT", "Service not available", e);
                    errorMessage = "Service not available";
                    return null;
                }

                Address address = null;
                if (addresses != null && addresses.size() > 0) {
                    address = addresses.get(0);
                } else {
                    errorMessage = "Wrong address";
                    return null;
                }

                return new LatLng(address.getLatitude(), address.getLongitude());
            }

            @Override
            protected void onPostExecute(LatLng location) {
                super.onPostExecute(location);

                listener.onLocationReceived(location, errorMessage);
            }
        }.execute(url);
    }

    public static void startLocationIntent(Context context, double latitude, double longitude) {
        Uri uri = Uri.parse("http://maps.google.com/maps?q=loc:" + latitude + "," + longitude);
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
    }

    public interface LocationReceiver {
        void onLocationReceived(LatLng location, String errorMessage);
    }
}