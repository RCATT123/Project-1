package com.roomorama.caldroid;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Customize the weekday gridview
 */
public class WeekdayArrayAdapter extends ArrayAdapter<String> {
    LayoutInflater localInflater;

    public WeekdayArrayAdapter(Context context, int textViewResourceId,
                               List<String> objects, int themeResource) {
        super(context, textViewResourceId, objects);
        localInflater = getLayoutInflater(getContext(), themeResource);
    }

    // To prevent cell highlighted when clicked
    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        TextView textView = (TextView) super.getView(position, convertView, parent);

        // Set content
        String item = getItem(position);
        textView.setText(item);

        return textView;
    }

    private LayoutInflater getLayoutInflater(Context context, int themeResource) {
        Context wrapped = new ContextThemeWrapper(context, themeResource);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return inflater.cloneInContext(wrapped);
    }

}
