package com.specifix.pureleagues.adapter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
//import com.parse.ParseAnonymousUtils;
//import com.parse.ParseUser;
import com.specifix.pureleagues.R;
import com.specifix.pureleagues.api.UserManager;
import com.specifix.pureleagues.manager.LocationManager;
import com.specifix.pureleagues.model.Fixture;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.grantland.widget.AutofitTextView;

public class FixturesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int FOOTER_VIEW = 1;
    private FixturesAdapterListener mListener;
    private Context mContext;
    private List<Fixture> mFixturesList;
    private Typeface gothamBold;
    private Typeface gothamBook;
    private ProgressDialog progressDialog;

    public FixturesAdapter(Context context, List<Fixture> list, FixturesAdapterListener listener) {
        mContext = context;
        mFixturesList = list;//new CopyOnWriteArrayList<>(list);

//        mFixturesList = new CopyOnWriteArrayList<>(list);
//        int r = new Random(new Date().getTime()).nextInt(10);
//        for (int i = 0; mFixturesList.size() < r; i++) {
//            mFixturesList.add(mFixturesList.get(i));
//        }
        mListener = listener;
        gothamBold = Typeface.createFromAsset(mContext.getAssets(), "GothamBold.otf");
        gothamBook = Typeface.createFromAsset(mContext.getAssets(), "GothamBook.otf");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

//        if (viewType == FOOTER_VIEW) {
//            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_layout, parent, false);
//            FooterViewHolder vh = new FooterViewHolder(view);
//
//            return vh;
//        }

        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_fixtures, parent, false);
        final DataViewHolder holder = new DataViewHolder(view);

        holder.mRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRouteIntent(holder.getAdapterPosition());
            }
        });
        holder.mMessages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onMessagesClick(mFixturesList.get(holder.getAdapterPosition()));
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DataViewHolder) {
            DataViewHolder normalHolder = (DataViewHolder) holder;
            normalHolder.mFirstTeam.setText(String.valueOf(mFixturesList.get(position).getHome_name()));
            normalHolder.mSecondTeam.setText(String.valueOf(mFixturesList.get(position).getAway_name()));
            normalHolder.mTime.setText(mFixturesList.get(position).getTime());
            normalHolder.mDate.setText(mFixturesList.get(position).getFormatDate());

            /*long teamId = UserManager.getInstance().getCurrentTeamId();
            if (mFixturesList.get(position).getClubOneId() == teamId
                    || mFixturesList.get(position).getClubTwoId() == teamId) {
                setSelected(normalHolder, true);
                normalHolder.mMessages.setVisibility(View.VISIBLE);
            } else {
                setSelected(normalHolder, false);
                normalHolder.mMessages.setVisibility(View.INVISIBLE);
            }*/
        }
        /*boolean isUserTeam = false;
        for (Team userTeam : UserManager.getInstance().getCurrentUser().getTeams()) {
            if (mFixturesList.get(position).getClubOneId() == userTeam.getTeamId()
                    || mFixturesList.get(position).getClubTwoId() == userTeam.getTeamId() ) {
                isUserTeam = true;
                setSelected(holder, true);
            }
        }
        if (!isUserTeam) {
            setSelected(holder, false);
        }*/
    }

    private void setSelected(DataViewHolder holder, boolean isSelected) {
        Typeface typeface = isSelected ? gothamBold : gothamBook;

        holder.mTime.setTypeface(typeface);
        holder.mDate.setTypeface(typeface);
        holder.mFirstTeam.setTypeface(typeface);
        holder.mSecondTeam.setTypeface(typeface);
    }

    @Override
    public int getItemCount() {
        if (mFixturesList == null) {
            return 0;
        }
        return mFixturesList.size();
//        if (mFixturesList == null) {
//            return 0;
//        }
//        if (mFixturesList.size() == 0) {
//            return 1;
//        }
//        if (mFixturesList.size() < 7) {
//            return mFixturesList.size();
//        }
//        return mFixturesList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (mFixturesList.size() < 7) {
            return super.getItemViewType(position);
        }
        if (position == mFixturesList.size()) {
            return FOOTER_VIEW;
        }
        return super.getItemViewType(position);
    }

    private void startRouteIntent(int position) {
        if (mContext == null)
            return;

        /*if (ParseAnonymousUtils.isLinked(ParseUser.getCurrentUser())) {
            Toast.makeText(mContext, R.string.register_error_message, Toast.LENGTH_SHORT).show();
            return;
        }*/

        showRouteDialog(null);
//        if (mFixturesList.get(position).getAddress() == null
//                || mFixturesList.get(position).getAddress().equals("")) {
//            Toast.makeText(mFragment.getContext(), "Venue not available to display route", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        Intent intent = new Intent(
//                Intent.ACTION_VIEW,
//                Uri.parse("google.navigation:q=" + mFixturesList.get(position).getAddress()));
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        mFragment.getContext().startActivity(intent);
    }

    private void showRouteDialog(final Object match) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View dialogView = inflater.inflate(R.layout.dialog_input_address, null);
        final EditText townInput = (EditText) dialogView.findViewById(R.id.address_dialog_town);
        final EditText venueInput = (EditText) dialogView.findViewById(R.id.address_dialog_venue);
        TextView townLabel = (TextView) dialogView.findViewById(R.id.address_dialog_town_label);
        TextView venueLabel = (TextView) dialogView.findViewById(R.id.address_dialog_venue_label);

        townLabel.setTypeface(gothamBold);
        venueLabel.setTypeface(gothamBold);
        townInput.setTypeface(gothamBook);
        venueInput.setTypeface(gothamBook);

        Dialog dialog = new AlertDialog.Builder(mContext)
                .setTitle(R.string.route_dialog_title)
                .setView(dialogView)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String townName = townInput.getText().toString();
                        String venueName = venueInput.getText().toString();

                        if (mContext != null) {
                            showProgress();

                            LocationManager.findLocation(mContext, townName + ", " + venueName, new LocationManager.LocationReceiver() {
                                @Override
                                public void onLocationReceived(LatLng location, String message) {
                                    hideProgress();

                                    if (location == null) {
                                        if (mContext != null) {
                                            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                                        }

                                        showRouteDialog(match);
                                    } else {
//                                        mFixtureCoords = location;
//                                        onRouteClick();
                                        try {
                                            Intent intent = new Intent(
                                                    Intent.ACTION_VIEW,
                                                    Uri.parse("google.navigation:q=" + location.latitude + "," + location.longitude));
                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            mContext.startActivity(intent);
                                        } catch (ActivityNotFoundException ex) {
                                            LocationManager.startLocationIntent(mContext, location.latitude, location.longitude);
                                        }
                                    }
                                }
                            });
                        }
                    }
                }).create();
        dialog.show();
    }

    public void showProgress() {
        if (progressDialog == null) {
            progressDialog = ProgressDialog.show(mContext, null, mContext.getString(R.string.loading), true, false);
            progressDialog.show();
        } else if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    public void hideProgress() {
        if (progressDialog != null) {
            progressDialog.cancel();
        }
    }

    static class DataViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.fixture_list_item_team_one_title)
        TextView mFirstTeam;
        @BindView(R.id.fixture_list_item_team_two_title)
        TextView mSecondTeam;
        @BindView(R.id.fixture_list_item_time)
        TextView mTime;
        @BindView(R.id.fixture_list_item_date)
        AutofitTextView mDate;
        @BindView(R.id.fixture_list_item_messages)
        ImageView mMessages;
        @BindView(R.id.fixture_list_item_route)
        ImageView mRoute;

        DataViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    static class FooterViewHolder extends RecyclerView.ViewHolder {

        FooterViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

        @OnClick({R.id.footer_facebook_icon,
                R.id.footer_twitter_icon,
                R.id.footer_instagram_icon,
                R.id.footer_youtube_icon,
                R.id.footer_snapchat_icon})
        public void onSocialClick(View view) {
            String url = null;
            switch (view.getId()) {
                case R.id.footer_facebook_icon: {
                    url = view.getContext().getString(R.string.facebook_url);
                    break;
                }
                case R.id.footer_twitter_icon: {
                    url = view.getContext().getString(R.string.twitter_url);
                    break;
                }
                case R.id.footer_instagram_icon: {
                    url = view.getContext().getString(R.string.instagram_url);
                    break;
                }
                case R.id.footer_youtube_icon: {
                    url = view.getContext().getString(R.string.youtube_url);
                    break;
                }
                case R.id.footer_snapchat_icon: {
                    url = view.getContext().getString(R.string.snapchat_url);
                    break;
                }
            }

            try {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                view.getContext().startActivity(i);
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public interface FixturesAdapterListener {

        void onMessagesClick(Fixture fixture);
    }
}
