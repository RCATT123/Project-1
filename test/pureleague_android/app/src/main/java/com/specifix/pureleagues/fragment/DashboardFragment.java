package com.specifix.pureleagues.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.os.ResultReceiver;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseLiveQueryClient;
import com.parse.ParseUser;
import com.specifix.pureleagues.R;
import com.specifix.pureleagues.adapter.DashboardFixturesAdapter;
import com.specifix.pureleagues.api.DataManager;
import com.specifix.pureleagues.api.UserManager;
import com.specifix.pureleagues.api.WeatherApiClient;
import com.specifix.pureleagues.api.WeatherApiInterface;
import com.specifix.pureleagues.api.model.TemperatureResponse;
import com.specifix.pureleagues.api.model.WeatherResponse;
import com.specifix.pureleagues.manager.LocationManager;
import com.specifix.pureleagues.manager.PrefsManager;
import com.specifix.pureleagues.model.ChatMessage;
import com.specifix.pureleagues.model.Fixture;
import com.specifix.pureleagues.model.Result;
import com.specifix.pureleagues.model.Team;
import com.specifix.pureleagues.service.LocationService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.grantland.widget.AutofitTextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.specifix.pureleagues.fragment.EditProfileFragment.TEAM_ID;
import static com.specifix.pureleagues.fragment.MessagesFragment.EVENT_TYPE_FIXTURES;
import static com.specifix.pureleagues.fragment.MessagesFragment.EVENT_TYPE_RESULTS;
import static com.specifix.pureleagues.receiver.PushReceiver.EVENT_TYPE;
import static com.specifix.pureleagues.receiver.PushReceiver.FROM_PUSH;
import static com.specifix.pureleagues.receiver.PushReceiver.LIST_POSITION;

public class DashboardFragment extends Fragment {
    private static final String EXTRA_SHOW_ALL = "show_all";

    //    @BindView(R.id.dashboard_arrow_left)
//    ImageView mArrowLeft;
//    @BindView(R.id.dashboard_arrow_right)
//    ImageView mArrowRight;
    @BindView(R.id.dashboard_view_pager)
    ViewPager mViewPager;
    @BindView(R.id.dashboard_route_button)
    LinearLayout mRouteBtn;
    @BindView(R.id.dashboard_weather_button)
    LinearLayout mWeatherBtn;
    @BindView(R.id.dashboard_weather_temperature)
    AutofitTextView mLocationTemperature;
    @BindView(R.id.dashboard_messages_button)
    LinearLayout mMessagesBtn;
    @BindView(R.id.dashboard_messages)
    AutofitTextView mLocationMessages;
    @BindView(R.id.dashboard_messages_badge)
    TextView mUnreadMessages;
    @BindView(R.id.dashboard_fixture_route_label)
    TextView mRouteLabel;
    @BindView(R.id.dashboard_fixture_weather_label)
    TextView mWeatherLabel;
    @BindView(R.id.dashboard_messages_label)
    TextView mMessagesLabel;
    @BindView(R.id.dashboard_latest_messages_label)
    TextView mLatestMessagesLabel;
    @BindView(R.id.dashboard_unread_messages)
    TextView mUnreadMessagesLabel;
    @BindView(R.id.dashboard_all_messages_button)
    Button mAllMessagesButton;
    @BindView(R.id.dashboard_latest_messages_container)
    LinearLayout mLatestMessagesContainer;
    @BindView(R.id.dashboard_no_data_text_view)
    TextView mNoDataTextView;
    private List<Object> mData;
    private DashboardFixturesAdapter mFixturesAdapter;
    private AddressResultReceiver mResultReceiver;
    private LatLng mFixtureCoords;
    private DashboardFragmentListener mCallback;
    private Typeface gothamBold;
    private Typeface gothamBook;
    private int currentPosition = 0;
    private WeatherApiInterface mWeatherApiService;
    private String mWeatherUrl;
    ParseLiveQueryClient mParseLiveQueryClient;
    ProgressDialog progressDialog;

    public static DashboardFragment newInstance() {
        DashboardFragment fragment = new DashboardFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public static DashboardFragment newInstance(boolean showAll) {
        DashboardFragment fragment = new DashboardFragment();
        Bundle args = new Bundle();
        args.putBoolean(EXTRA_SHOW_ALL, showAll);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (DashboardFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement DashboardFragmentListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getContext());
        List<Team> teams = UserManager.getInstance().getCurrentUser().getTeams();
        if (teams.size() == 0) {
            return;
        }
        long teamId = sp.getLong(TEAM_ID, 0);
        if (teamId == 0) {
            sp.edit().putLong(TEAM_ID, teams.get(0).getTeamId()).apply();
            teamId = teams.get(0).getTeamId();
            UserManager.getInstance().setCurrentTeamId(teamId);
        }
        Team currentTeam = null;
        for (Team team : teams) {
            if (team.getTeamId() == teamId) {
                currentTeam = team;
            }
        }
        mData = new ArrayList<Object>(DataManager.getInstance().sortClubFixturesAndGet(currentTeam));
        if (getArguments() != null && getArguments().getBoolean(EXTRA_SHOW_ALL)) {
            mData.addAll(DataManager.getInstance().sortClubResultsAndGet(currentTeam));
        }

        mResultReceiver = new AddressResultReceiver(new Handler());

        gothamBold = Typeface.createFromAsset(getContext().getAssets(), "GothamBold.otf");
        gothamBook = Typeface.createFromAsset(getContext().getAssets(), "GothamBook.otf");

        DataManager.getInstance().downloadMessages(getContext(), new DataManager.DataManagerUpdateListener() {
            @Override
            public void onMessageDownloaded(String eventType, int i) {
                DashboardFragment.this.onMessageDownloaded(eventType, i);
            }
        });
        DataManager.getInstance().updateGallery(getContext(), null);

        /*List<Team> teams = UserManager.getInstance().getCurrentUser().getTeams();
        if (teams != null) {
            DataManager.getInstance().getClubs(new DataManager.DataManagerPickListener() {
                @Override
                public void onListReady(List<?> list) {
                    DataManager.getInstance().getClubStats(null);
                }
            }, teams.get(0).getDivisionId());
        }*/

        subscribeToNewMessages();

        mWeatherApiService = WeatherApiClient.getClient().create(WeatherApiInterface.class);
    }

    private void subscribeToNewMessages() {
        if (mParseLiveQueryClient != null) {
            return;
        }
        mParseLiveQueryClient = ParseLiveQueryClient.Factory.getClient();
        DataManager.getInstance().subscribeToNewMessages(
                mParseLiveQueryClient,
                null,
                -1,
                new DataManager.DataManagerNewMessageEvent() {
                    @Override
                    public void onFixturesReady(final ChatMessage message) {
                        if (DashboardFragment.this.getActivity() != null) {
                            DashboardFragment.this.getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    onNewMessageDownloaded(message);
                                }
                            });
                        }
                    }

                    @Override
                    public void onResultsReady(final ChatMessage message) {
                        DashboardFragment.this.getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                onNewMessageDownloaded(message);
                            }
                        });
                    }
                }
        );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRouteLabel.setTypeface(gothamBold);
        mWeatherLabel.setTypeface(gothamBold);
        mLocationTemperature.setTypeface(gothamBold);
        mMessagesLabel.setTypeface(gothamBold);
        mLocationMessages.setTypeface(gothamBold);
        mUnreadMessages.setTypeface(gothamBold);
        mLatestMessagesLabel.setTypeface(gothamBold);
        mUnreadMessagesLabel.setTypeface(gothamBold);
        mAllMessagesButton.setTypeface(gothamBold);

        mFixturesAdapter = new DashboardFixturesAdapter(getChildFragmentManager(), mData);
        mViewPager.setAdapter(mFixturesAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                setFixtureInfoButtons(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        if (mData.size() == 0) {
//            mArrowLeft.setVisibility(View.INVISIBLE);
//            mArrowRight.setVisibility(View.INVISIBLE);

            long teamId = UserManager.getInstance().getCurrentTeamId();
            String teamName = "";
            for (Team team : DataManager.getInstance().getUserDivisionsTeams()) {
                if (team.getTeamId() == teamId) {
                    teamName = team.getClub();
                    break;
                }
            }
            mViewPager.setVisibility(View.INVISIBLE);
            mNoDataTextView.setVisibility(View.VISIBLE);
            mNoDataTextView.setText(String.format(getString(R.string.no_fixtures_available), teamName));
            mMessagesBtn.setEnabled(false);
            mWeatherBtn.setEnabled(false);
            mRouteBtn.setEnabled(false);
        } else {
            setFixtureInfoButtons(0);

//            mArrowLeft.setVisibility(View.VISIBLE);
//            mArrowRight.setVisibility(View.VISIBLE);
            mViewPager.setVisibility(View.VISIBLE);
            mNoDataTextView.setVisibility(View.INVISIBLE);
            mMessagesBtn.setEnabled(true);
            mWeatherBtn.setEnabled(true);
            mRouteBtn.setEnabled(true);
        }
    }

    private void setFixtureInfoButtons(int position) {
        Object item = mData.get(position);
        String eventId = null;

        List<ChatMessage> chatMessages = null;
        if (item instanceof Fixture) {
            chatMessages = ((Fixture) item).getMessages();
            eventId = ((Fixture) item).getId();
        } else if (item instanceof Result) {
            chatMessages = ((Result) item).getMessages();
            eventId = ((Result) item).getId();
        }

        if (chatMessages == null)
            return;

        currentPosition = position;

        if (position == 0) {
//            mArrowLeft.setVisibility(View.GONE);
        } else {
//            mArrowLeft.setVisibility(View.VISIBLE);
        }

        if (position == mData.size() - 1) {
//            mArrowRight.setVisibility(View.GONE);
        } else {
//            mArrowRight.setVisibility(View.VISIBLE);
        }

        addLatestMessages(getLatestMessages(position));
        mLocationMessages.setText(getMessageFormattedCount(chatMessages.size()));

        PrefsManager manager = new PrefsManager();
        String lastMessageId = manager.getLastMessageId(eventId);

        if (UserManager.getInstance().getUserAge() < UserManager.MIN_ALLOWED_AGE) {
            mUnreadMessages.setVisibility(View.GONE);
            mLatestMessagesContainer.setVisibility(View.GONE);
            mLatestMessagesLabel.setVisibility(View.GONE);
        } else {
            if (TextUtils.isEmpty(lastMessageId)) {
                if (chatMessages.size() == 0) {
                    mUnreadMessages.setVisibility(View.GONE);
                } else {
                    mUnreadMessages.setVisibility(View.VISIBLE);
                    mUnreadMessages.setText(getMessageFormattedCount(chatMessages.size()));
                }
            } else {
                int count = 0;
                String userId = UserManager.getInstance().getCurrentUser().getObjectId();
                for (int i = 0; i < chatMessages.size(); i++) {
                    ChatMessage message = chatMessages.get(i);
                    if (lastMessageId.equals(message.getObjectId())) {
                        break;
                    }
                    if (!userId.equals(message.getAuthorId())) {
                        count++;
                    }
                }

                if (count == 0) {
                    mUnreadMessages.setVisibility(View.GONE);
                } else {
                    mUnreadMessages.setVisibility(View.VISIBLE);
                    mUnreadMessages.setText(getMessageFormattedCount(count));
                }
            }
        }

//        Fixture fixture = mData.get(position);
//        int unreadMessagesCount = 0;
//        for (ChatMessage message : fixture.getMessages()) {
//            if (!message.isRead()) {
//                unreadMessagesCount++;
//            }
//        }
//        if (unreadMessagesCount > 0) {
//            mUnreadMessages.setVisibility(View.VISIBLE);
//            mUnreadMessages.setText(String.valueOf(unreadMessagesCount));
//        } else {
//            mUnreadMessages.setVisibility(View.GONE);
//        }
//        mUnreadMessagesLabel.setText(
//                String.format(getString(R.string.unread_messages_pattern),
//                        String.valueOf(unreadMessagesCount)));
//        mLocationTemperature.setText(" °C");

        mFixtureCoords = null;
        mWeatherUrl = null;

        if (mData.get(position) instanceof Fixture) {
            final Fixture fixture = (Fixture) (mData.get(position));

//            if (!TextUtils.isEmpty(fixture.getWeather())) {
//                mLocationTemperature.setText(fixture.getWeather());
//            }

//            if (!TextUtils.isEmpty(fixture.getWeatherUrl())) {
//                mWeatherUrl = fixture.getWeatherUrl();
//            }

//            mFixtureCoords = fixture.getLocation();

            if (TextUtils.isEmpty(fixture.getWeather())) {
//            if (fixture.getLocation() == null) {
                final int locationPosition = position;
                LocationManager.findLocation(getContext(), fixture.getAddress(), new LocationManager.LocationReceiver() {
                    @Override
                    public void onLocationReceived(LatLng location, String errorMessage) {

                        if (location != null) {
//                            fixture.setLocation(location);

                            getWeather(location, new WeatherListener() {
                                @Override
                                public void onWeatherResponse(Response<List<TemperatureResponse>> response) {

                                    if (response != null && response.body() != null) {
                                        fixture.setWeather(response.body().get(0)
                                                .getTemperature()
                                                .getMetric()
                                                .getValue());
                                        fixture.setWeatherUrl(response.body().get(0).getMobileLink());

                                        if (locationPosition == currentPosition) {
                                            mLocationTemperature.setText(fixture.getWeather());
//                                            mWeatherUrl = fixture.getWeatherUrl();
                                        }
                                    }
                                }
                            });

//                            if (locationPosition == currentPosition) {
//                                mFixtureCoords = location;
//                            }
                        }
                    }
                });
            } else {
                mLocationTemperature.setText(fixture.getWeather());
            }
        } else if (mData.get(position) instanceof Result) {
            final Result result = (Result) (mData.get(position));

            if (!TextUtils.isEmpty(result.getWeather())) {
                mLocationTemperature.setText(result.getWeather());
            }
        }
        //startIntentService(position);
    }

    private void addLatestMessages(List<ChatMessage> messages) {
        mLatestMessagesContainer.removeAllViews();
        for (ChatMessage message : messages) {
//            if (this.isVisible()) {
            if (getContext() != null) {
                View view = View.inflate(getContext(), R.layout.list_item_latets_messages, null);
                //ImageView status = (ImageView) view.findViewById(R.id.list_item_latest_messages_status);
                TextView author = (TextView) view.findViewById(R.id.list_item_latest_messages_name);
                TextView content = (TextView) view.findViewById(R.id.list_item_latest_messages_message);
                TextView date = (TextView) view.findViewById(R.id.list_item_latest_messages_date);

                author.setTypeface(gothamBold);
                content.setTypeface(gothamBook);
                date.setTypeface(gothamBook);

                //status.setImageResource(message.isRead() ? R.drawable.readded_message_icon : R.drawable.unread_message_icon);
                author.setText(message.getAuthor());
                content.setText((message.getMessage().equals("") ? "[New image]" : message.getMessage()));
                date.setText(getFormattedDate(message.getDate()));
                mLatestMessagesContainer.addView(view);
            }

//            }
        }
    }

    private List<ChatMessage> getLatestMessages(int fixtureNumber) {
        Object item = mData.get(fixtureNumber);

        List<ChatMessage> messages = null;
        if (item instanceof Fixture) {
            messages = ((Fixture) item).getMessages();
        } else if (item instanceof Result) {
            messages = ((Result) item).getMessages();
        }

        if (messages == null) {
            return new ArrayList<>();
        }

        if (messages.size() <= 3) {
            return messages;
        }
        return messages.subList(0, 3);
    }

    private String getFormattedDate(long date) {
        String formattedDate;
        DateFormat defaultDateFormat = new SimpleDateFormat("dd.MM.yyyy - HH:mm", Locale.getDefault());
        DateFormat timeDateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

        Calendar today = new GregorianCalendar();
        Calendar messageDate = new GregorianCalendar();

        defaultDateFormat.setTimeZone(today.getTimeZone());
        timeDateFormat.setTimeZone(today.getTimeZone());

        messageDate.setTime(new Date(date));
        if (today.get(Calendar.DAY_OF_YEAR) - messageDate.get(Calendar.DAY_OF_YEAR) == 1) {
            formattedDate = getString(R.string.yesterday_label)
                    + " - "
                    + timeDateFormat.format(messageDate.getTime());
        } else if (today.get(Calendar.DAY_OF_YEAR) - messageDate.get(Calendar.DAY_OF_YEAR) == 0) {
            formattedDate = getString(R.string.today_label)
                    + " - "
                    + timeDateFormat.format(messageDate.getTime());
        } else {
            formattedDate = defaultDateFormat.format(messageDate.getTime());
        }
        return formattedDate;
    }

//    @OnClick(R.id.dashboard_arrow_right)
//    public void onNextFixtureClick() {
//        if (mViewPager.getCurrentItem() != mData.size() - 1) {
//            mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
//        }
//    }
//
//    @OnClick(R.id.dashboard_arrow_left)
//    public void onPrevFixtureClick() {
//        if (mViewPager.getCurrentItem() != 0) {
//            mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
//        }
//    }

    @OnClick(R.id.dashboard_route_button)
    public void onRouteClick() {
        if (ParseAnonymousUtils.isLinked(ParseUser.getCurrentUser())) {
            Toast.makeText(getContext(), R.string.register_error_message, Toast.LENGTH_SHORT).show();
            return;
        }
        if (mFixtureCoords != null) {
            Intent intent = new Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("google.navigation:q=" + mFixtureCoords.latitude + "," + mFixtureCoords.longitude));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
//            Toast.makeText(getContext(), "Venue not available to display route", Toast.LENGTH_SHORT).show();
            if (mData != null && mData.size() != 0) {
                showRouteDialog(mData.get(currentPosition));
            }
        }
    }

    private void showRouteDialog(final Object match) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View dialogView = inflater.inflate(R.layout.dialog_input_address, null);
        final EditText townInput = (EditText) dialogView.findViewById(R.id.address_dialog_town);
        final EditText venueInput = (EditText) dialogView.findViewById(R.id.address_dialog_venue);
        TextView townLabel = (TextView) dialogView.findViewById(R.id.address_dialog_town_label);
        TextView venueLabel = (TextView) dialogView.findViewById(R.id.address_dialog_venue_label);

        townLabel.setTypeface(gothamBold);
        venueLabel.setTypeface(gothamBold);
        townInput.setTypeface(gothamBook);
        venueInput.setTypeface(gothamBook);

        Dialog dialog = new AlertDialog.Builder(getContext())
                .setTitle(R.string.route_dialog_title)
                .setView(dialogView)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String townName = townInput.getText().toString();
                        String venueName = venueInput.getText().toString();

                        if (getContext() != null) {
                            showProgress();

                            LocationManager.findLocation(getContext(), townName + ", " + venueName, new LocationManager.LocationReceiver() {
                                @Override
                                public void onLocationReceived(LatLng location, String errorMessage) {
                                    hideProgress();

                                    if (location == null) {
                                        if (getContext() != null) {
                                            Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
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
                                            startActivity(intent);
                                        } catch (ActivityNotFoundException ex) {
                                            LocationManager.startLocationIntent(getContext(), location.latitude, location.longitude);
                                        }
                                    }
                                }
                            });
                        }
                    }
                }).create();
        dialog.show();
    }

    private void showWeatherDialog(final Object match) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View dialogView = inflater.inflate(R.layout.dialog_input_address, null);
        final EditText townInput = (EditText) dialogView.findViewById(R.id.address_dialog_town);
        final EditText venueInput = (EditText) dialogView.findViewById(R.id.address_dialog_venue);
        TextView townLabel = (TextView) dialogView.findViewById(R.id.address_dialog_town_label);
        TextView venueLabel = (TextView) dialogView.findViewById(R.id.address_dialog_venue_label);

        townLabel.setTypeface(gothamBold);
        townInput.setTypeface(gothamBook);

        venueLabel.setVisibility(View.GONE);
        venueInput.setVisibility(View.GONE);

        Dialog dialog = new AlertDialog.Builder(getContext())
                .setTitle(R.string.route_dialog_title)
                .setView(dialogView)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String townName = townInput.getText().toString();

                        if (getContext() != null) {
                            showProgress();

                            LocationManager.findLocation(getContext(), townName, new LocationManager.LocationReceiver() {
                                @Override
                                public void onLocationReceived(LatLng location, String errorMessage) {

                                    if (location == null) {
                                        hideProgress();

                                        if (getContext() != null) {
                                            Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                                        }

                                        showWeatherDialog(match);
                                    } else {
                                        if (isVisible()) {
                                            getWeather(location, new WeatherListener() {
                                                @Override
                                                public void onWeatherResponse(Response<List<TemperatureResponse>> response) {
                                                    hideProgress();

                                                    if (response != null && response.body() != null) {
                                                        String weather = response.body().get(0)
                                                                .getTemperature()
                                                                .getMetric()
                                                                .getValue();

                                                        if (mData != null && mData.size() != 0) {
                                                            if (mData.get(currentPosition) instanceof Fixture) {
                                                                ((Fixture) mData.get(currentPosition)).setWeather(weather);
                                                            } else if (mData.get(currentPosition) instanceof Result) {
                                                                ((Result) mData.get(currentPosition)).setWeather(weather);
                                                            }
                                                        }

                                                        mLocationTemperature.setText(weather);
                                                        mWeatherUrl = response.body().get(0).getMobileLink();

//                                                        onWeatherClick();
                                                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mWeatherUrl));
                                                        startActivity(browserIntent);

                                                    } else {
                                                        Toast.makeText(getContext(), "Weather service error", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                        } else {
                                            hideProgress();
                                        }
                                    }
                                }
                            });
                        }
                    }
                }).create();
        dialog.show();
    }

    private void getWeather(LatLng location, final WeatherListener listener) {
        if (getContext() == null)
            return;

        mWeatherApiService = WeatherApiClient.getClient().create(WeatherApiInterface.class);

        Call<WeatherResponse> keyCall = mWeatherApiService.getWeatherKey(
                location.latitude + "," + location.longitude,
                getString(R.string.accuweather_api_key)
        );
        keyCall.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.body() != null && DashboardFragment.this.isAdded()) {
                    Call<List<TemperatureResponse>> weatherCall = mWeatherApiService.getTemperature(
                            response.body().getKey(),
                            getString(R.string.accuweather_api_key));
                    weatherCall.enqueue(new Callback<List<TemperatureResponse>>() {
                        @Override
                        public void onResponse(Call<List<TemperatureResponse>> call, Response<List<TemperatureResponse>> response) {
                            listener.onWeatherResponse(response);
                        }

                        @Override
                        public void onFailure(Call<List<TemperatureResponse>> call, Throwable t) {
                            listener.onWeatherResponse(null);
                        }
                    });
                } else {
                    listener.onWeatherResponse(null);
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                listener.onWeatherResponse(null);
            }
        });
    }

    private interface WeatherListener {
        void onWeatherResponse(Response<List<TemperatureResponse>> response);
    }

    @OnClick(R.id.dashboard_messages_button)
    public void onAllMessagesClick() {
        if (mData != null && mData.size() > 0) {
            if (mData.get(mViewPager.getCurrentItem()) instanceof Fixture) {
                mCallback.onAllMessagesClick(EVENT_TYPE_FIXTURES, mData.get(mViewPager.getCurrentItem()));
            } else {
                mCallback.onAllMessagesClick(EVENT_TYPE_RESULTS, mData.get(mViewPager.getCurrentItem()));
            }
        }
    }

    @OnClick(R.id.dashboard_all_messages_button)
    void onShareClick() {
        Uri imageUri = Uri.parse("android.resource://" + getContext().getPackageName()
                + "/drawable/" + "logo");
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.branch_share_link));
        sendIntent.setType("image/*");
        sendIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));
    }

    @OnClick(R.id.dashboard_weather_button)
    public void onWeatherClick() {
//        if (mWeatherUrl != null && !mWeatherUrl.isEmpty()) {
//            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mWeatherUrl));
//            startActivity(browserIntent);
//        } else {
//            showWeatherDialog(mData.get(currentPosition));
//        }
        if (mData != null && mData.size() != 0) {
            showWeatherDialog(mData.get(currentPosition));
        }

//        if (mFixtureCoords == null) {
//            Toast.makeText(getContext(), "Venue not available to display weather", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        Geocoder coder = new Geocoder(getContext(), Locale.US);
//        List<Address> addresses;
//        try {
//            addresses = coder.getFromLocation(mFixtureCoords.latitude, mFixtureCoords.longitude, 1);
//
//            if (addresses == null || addresses.size() == 0) {
//
//            } else {
//                Address address = addresses.get(0);
//                Intent browserIntent = new Intent(Intent.ACTION_VIEW,
//                        Uri.parse("https://openweathermap.org/find?q=" + address.getLocality() +
//                                "&appid=" + getString(R.string.weather_api_key)));
//                startActivity(browserIntent);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    protected void startIntentService(int position) {
//        if (getContext() != null && !getArguments().getBoolean(EXTRA_SHOW_ALL)) {
        if (getContext() != null) {
            Intent intent = new Intent(getContext(), LocationService.class);
            intent.putExtra(LocationService.RECEIVER, mResultReceiver);
            if (mData.get(position) instanceof Fixture) {
                intent.putExtra(LocationService.LOCATION_DATA_EXTRA, ((Fixture) (mData.get(position))).getAddress());
            } else {
                showRouteDialog(mData.get(position));
            }
            getContext().startService(intent);
        }
    }

    public void showProgress() {
        if (progressDialog == null) {
            progressDialog = ProgressDialog.show(getContext(), null, getString(R.string.loading), true, false);
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

    private void onNewMessageDownloaded(ChatMessage message) {
        long messageEventId = message.getParentId();
        long matchId;
//        String objectId = null;

        if (mData == null)
            return;
        Log.d("PUSH FOR DASHBOARD", "PUSH RECEIVED");

        for (int i = 0; i < mData.size(); i++) {
            Object item = mData.get(i);
            if (item instanceof Fixture) {
                matchId = ((Fixture) item).getMatch_id();
//                objectId = ((Fixture) item).getId();
                if (messageEventId == matchId) {
                    ((Fixture) item).addMessage(message);

                    if (i == currentPosition) {
                        Log.d("PUSH FOR DASHBOARD", message.getMessage());
                        setFixtureInfoButtons(currentPosition);
                    }
                    break;
                }
            } else if (item instanceof Result) {
                matchId = ((Result) item).getMatch_id();
//                objectId = ((Result) item).getId();
                if (messageEventId == matchId) {
                    ((Result) item).addMessage(message);

                    if (i == currentPosition) {
                        Log.d("PUSH FOR DASHBOARD", message.getMessage());
                        setFixtureInfoButtons(currentPosition);
                    }
                    break;
                }
            }
        }
    }

    private void onMessageDownloaded(String eventType, int position) {
        if (position == currentPosition && !eventType.equals(EVENT_TYPE_RESULTS)) {
            setFixtureInfoButtons(position);

            Object item = mData.get(position);

            List<ChatMessage> chatMessages = null;
            if (item instanceof Fixture) {
                chatMessages = ((Fixture) item).getMessages();
            } else if (item instanceof Result) {
                chatMessages = ((Result) item).getMessages();
            }

            if (chatMessages == null) {
                chatMessages = new ArrayList<>();
            }

            mLocationMessages.setText(getMessageFormattedCount(chatMessages.size()));
        }

        if (getActivity() != null && getActivity().getIntent() != null && getActivity().getIntent().getBooleanExtra(FROM_PUSH, false)) {
            String type = getActivity().getIntent().getStringExtra(EVENT_TYPE);
            if (type.equals(EVENT_TYPE_FIXTURES)) {
                if (DataManager.getInstance().getFixtures().size() == 0) {
                    return;
                }
                String fixtureObjectId = getActivity().getIntent().getStringExtra(LIST_POSITION);
                for (Fixture fixture : DataManager.getInstance().getFixtures()) {
                    if (fixture.getId().equals(fixtureObjectId)) {
                        mCallback.onAllMessagesClick(type, fixture);
                        break;
                    }
                }

//                mCallback.onAllMessagesClick(
//                        type,
//                        DataManager.getInstance().getFixtures().get(Integer.parseInt(getActivity().getIntent().getStringExtra(LIST_POSITION))));
            } else {
                if (DataManager.getInstance().getResults().size() == 0) {
                    return;
                }
                String resultObjectId = getActivity().getIntent().getStringExtra(LIST_POSITION);
                for (Result result : DataManager.getInstance().getResults()) {
                    if (result.getId().equals(resultObjectId)) {
                        mCallback.onAllMessagesClick(type, result);
                        break;
                    }
                }
//                mCallback.onAllMessagesClick(
//                        type,
//                        DataManager.getInstance().getResults().get(Integer.parseInt(getActivity().getIntent().getStringExtra(LIST_POSITION))));
            }
            getActivity().getIntent().putExtra(FROM_PUSH, false);
        }
    }

    public void setFixture(Object fixture) {
        if (!mData.contains(fixture)) {
            mData.add(fixture);
            mFixturesAdapter.addItem(fixture);
        }
        setActiveFixture(fixture);
    }

    private void setActiveFixture(Object fixture) {
        mViewPager.setCurrentItem(mData.indexOf(fixture));
    }

    private class AddressResultReceiver extends ResultReceiver {

        AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            double latitude = resultData.getDouble(LocationService.LOCATION_LATITUDE);
            double longitude = resultData.getDouble(LocationService.LOCATION_LONGITUDE);
            mFixtureCoords = new LatLng(latitude, longitude);
            if (isVisible()) {
                mWeatherApiService = WeatherApiClient.getClient().create(WeatherApiInterface.class);

                Call<WeatherResponse> keyCall = mWeatherApiService.getWeatherKey(
                        latitude + "," + longitude,
                        getString(R.string.accuweather_api_key)
                );
                keyCall.enqueue(new Callback<WeatherResponse>() {
                    @Override
                    public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                        if (response.body() != null && DashboardFragment.this.isAdded()) {
                            Call<List<TemperatureResponse>> weatherCall = mWeatherApiService.getTemperature(
                                    response.body().getKey(),
                                    getString(R.string.accuweather_api_key));
                            weatherCall.enqueue(new Callback<List<TemperatureResponse>>() {
                                @Override
                                public void onResponse(Call<List<TemperatureResponse>> call, Response<List<TemperatureResponse>> response) {
                                    if (response.body() != null) {
                                        mLocationTemperature.setText(
                                                response.body().get(0)
                                                        .getTemperature()
                                                        .getMetric()
                                                        .getValue());
                                        mWeatherUrl = response.body().get(0).getMobileLink();
                                    }
                                }

                                @Override
                                public void onFailure(Call<List<TemperatureResponse>> call, Throwable t) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<WeatherResponse> call, Throwable t) {

                    }
                });
//                new WeatherManager(getString(R.string.weather_api_key)).getCurrentWeatherByCoordinates(
//                        latitude,
//                        longitude,
//                        new WeatherManager.CurrentWeatherHandler() {
//                            @Override
//                            public void onReceivedCurrentWeather(WeatherManager manager, Weather weather) {
//                                int temperature = (int) weather.getTemperature().getCurrent().getValue(TemperatureUnit.CELCIUS);
//                                mLocationTemperature.setText(String.format("%s°C", temperature));
//                            }
//
//                            @Override
//                            public void onFailedToReceiveCurrentWeather(WeatherManager manager) {
//                                mLocationTemperature.setText(" °C");
//                            }
//                        }
//                );
            }
        }
    }

    private String getMessageFormattedCount(int size) {
        return size < 100 ? String.valueOf(size) : "99+";
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
                url = getString(R.string.facebook_url);
                break;
            }
            case R.id.footer_twitter_icon: {
                url = getString(R.string.twitter_url);
                break;
            }
            case R.id.footer_instagram_icon: {
                url = getString(R.string.instagram_url);
                break;
            }
            case R.id.footer_youtube_icon: {
                url = getString(R.string.youtube_url);
                break;
            }
            case R.id.footer_snapchat_icon: {
                url = getString(R.string.snapchat_url);
                break;
            }
        }

        try {
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(i);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    public interface DashboardFragmentListener {

        void onAllMessagesClick(String eventType, Object object);
    }
}
