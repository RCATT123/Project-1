package com.specifix.pureleagues.adapter;


import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.specifix.pureleagues.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StatisticsTeamAdapter extends ArrayAdapter<String>{
    @BindView(R.id.simple_list_item_textview)
    TextView listItem;

    public StatisticsTeamAdapter(@NonNull Context context, List<String> objects) {
        super(context, R.layout.list_item_statistics_spiner, objects);
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String item = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_statistics_spiner, parent, false);
        }
        ButterKnife.bind(this, convertView);

        listItem.setText(item);

        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String item = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_simple, parent, false);
        }
        ButterKnife.bind(this, convertView);

        listItem.setText(item);

        return convertView;
    }
}
