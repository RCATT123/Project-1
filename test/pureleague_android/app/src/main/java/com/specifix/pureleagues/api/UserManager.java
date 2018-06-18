package com.specifix.pureleagues.api;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.parse.LogInCallback;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;
import com.specifix.pureleagues.R;
import com.specifix.pureleagues.model.Team;
import com.specifix.pureleagues.model.User;
import com.specifix.pureleagues.util.Login;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserManager {
    public static final int MIN_ALLOWED_AGE = 13;

    public static final String USER_ID = "object_id";
    public static final String NAME_KEY = "name";
    public static final String PASSWORD_KEY = "password";
    public static final String DATE_OF_BIRTH_KEY = "date_of_birth";
    public static final String GENDER_KEY = "gender";
    public static final String USER_TYPE_KEY = "user_type";
    public static final String HEIGHT_KEY = "height";
    public static final String WEIGHT_KEY = "weight";
    public static final String POSITION_KEY = "position";
    public static final String ABOUT_ME_KEY = "about_me";
    public static final String FOOTBALL_ASSOCIATION_KEY = "football_association";
    public static final String TEAMS_KEY = "teams";
    public static final String CLUB_NAME_KEY = "club";
    public static final String DIVISION_KEY = "division";
    public static final String TEAM_COLOURS = "team_colours";
    public static final String PHOTO_URL = "photo_file";
    private static UserManager instance;
    private static long currentTeamId;
    private String[] mTeamColorNames = {
            "Color 1", "Color 2", "Color 3", "Color 4", "Color 5", "Color 6", "Color 7",
            "Color 8", "Color 9", "Color 10", "Color 11", "Color 12", "Color 13", "Color 14",
            "Color 15", "Color 16", "Color 17", "Color 18", "Color 19", "Color 20"
    };
    private int[] mTeamColors = {
            R.drawable.t_shirt_1, R.drawable.t_shirt_2, R.drawable.t_shirt_3,
            R.drawable.t_shirt_4, R.drawable.t_shirt_5, R.drawable.t_shirt_6,
            R.drawable.t_shirt_7, R.drawable.t_shirt_8, R.drawable.t_shirt_9,
            R.drawable.t_shirt_10, R.drawable.t_shirt_11, R.drawable.t_shirt_12,
            R.drawable.t_shirt_13, R.drawable.t_shirt_14, R.drawable.t_shirt_15,
            R.drawable.t_shirt_16, R.drawable.t_shirt_17, R.drawable.t_shirt_18,
            R.drawable.t_shirt_19, R.drawable.t_shirt_20
    };

    private byte[] userPhotoData;
    private String userPhotoUrl;

    private UserManager() {

    }

    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    public void registerUser(User user, final UserListener listener) {
        ParseUser parseUser = new ParseUser();
        parseUser.put(NAME_KEY, user.getName());
        parseUser.setUsername(user.getUsername());
        parseUser.setPassword(user.getPassword());
        parseUser.setEmail(user.getEmail());
        parseUser.put(DATE_OF_BIRTH_KEY, user.getDateOfBirth());
        parseUser.put(GENDER_KEY, user.getGender());
        parseUser.put(USER_TYPE_KEY, user.getType());
//        if (user.getType().equals("Player")) {
//            parseUser.put(HEIGHT_KEY, user.getHeight());
//            parseUser.put(WEIGHT_KEY, user.getWeight());
//            parseUser.put(POSITION_KEY, user.getPosition());
//            parseUser.put(ABOUT_ME_KEY, user.getProfile());
//        }
        parseUser.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    listener.onSuccess();
                } else {
                    switch (e.getCode()) {
                        case ParseException.USERNAME_TAKEN:
                            listener.onError("This user already exist");
                            break;
                        case ParseException.EMAIL_TAKEN:
                            listener.onError("This email already taken");
                            break;
                        default:
                            listener.onError("Connection error");
                    }
                }
            }
        });
    }

    public void loginUser(String username, String password) {
        /*ParseUser.logInInBackground(
                username,
                password,
                new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (e != null) {
                            switch (e.getCode()) {
                                case ParseException.OBJECT_NOT_FOUND:
                                    listener.onError(((Context) listener).getString(R.string.invalid_credentials_error_message));
                                    break;
                                default:
                                    listener.onError("Connection error");
                            }
                        } else {
                            listener.onUserLogin(user);
                        }
                    }
                });*/
        //System.out.println("UserManager.loginUser");
        ApiInterface mApiService = this.getInterfaceService();
        Call<Login> mService = mApiService.authenticate(username, password);
        mService.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {

                Login mLoginObject = response.body();
                //Log.w(" Full json res ", new Gson().toJson(response));
                String returnedResponse = mLoginObject.isLogin;
                //Toast.makeText(UserManager.this, "Returned " + returnedResponse, Toast.LENGTH_LONG).show();
                System.out.println("UserManager.onResponse "+ returnedResponse);

                //showProgress(false);
                //if(returnedResponse.trim().equals("1")){
                    // redirect to Main Activity page
                    /*Intent loginIntent = new Intent(LoginActivity.this, MainActivity.class);
                    loginIntent.putExtra("EMAIL", email);
                    startActivity(loginIntent);*/
                //}
                //if(returnedResponse.trim().equals("0")){
                    // use the registration button to register
                    /*failedLoginMessage.setText(getResources().getString(R.string.registration_message));
                    mPasswordView.requestFocus();*/
                //}
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                call.cancel();
                System.out.println("UserManager.onFailure");
                //Toast.makeText(LoginActivity.this, "Please check your network connection and internet permission", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void updateUserProfile(User userProfile, final UserListener listener) {
        final ParseUser user = ParseUser.getCurrentUser();
        user.put(NAME_KEY, userProfile.getName());
        user.setUsername(user.getUsername());
        user.put(DATE_OF_BIRTH_KEY, userProfile.getDateOfBirth());
        user.put(GENDER_KEY, userProfile.getGender());
        user.put(USER_TYPE_KEY, userProfile.getType());
        user.put(HEIGHT_KEY, userProfile.getHeight());
        user.put(WEIGHT_KEY, userProfile.getWeight());
        user.put(POSITION_KEY, userProfile.getPosition());
        user.put(ABOUT_ME_KEY, userProfile.getProfile());

        final byte[] imageToSave = userProfile.getPhotoByteArray();
        if ((imageToSave != null) && !Arrays.equals(imageToSave, userPhotoData)) {
            final ParseFile file = new ParseFile("image.PNG", imageToSave);
            file.saveInBackground(
                    new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            user.put(PHOTO_URL, file);
                            userPhotoData = imageToSave;
                            userPhotoUrl = file.getUrl();
                            user.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if (e == null) {
                                        listener.onSuccess();
                                    } else {
                                        listener.onError("Connection error");
                                    }
                                }
                            });
                        }
                    });
        } else {
            user.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        listener.onSuccess();
                    } else {
                        listener.onError("Connection error");
                    }
                }
            });
        }
    }

    public void logoutUser(final LogoutListener listener) {
        ParseUser.logOutInBackground(new LogOutCallback() {
            @Override
            public void done(ParseException e) {
                listener.onLogout();
            }
        });
    }

    public User getCurrentUser() {
        ParseUser parseUser = ParseUser.getCurrentUser();
        if (parseUser == null) {
            return null;
        }
        User user = new User(
                parseUser.getObjectId(),
                parseUser.getString(NAME_KEY),
                parseUser.getUsername(),
                parseUser.getString(PASSWORD_KEY),
                parseUser.getEmail(),
                parseUser.getString(DATE_OF_BIRTH_KEY),
                parseUser.getString(GENDER_KEY),
                parseUser.getString(USER_TYPE_KEY));
        user.setHeight(parseUser.getString(HEIGHT_KEY));
        user.setWeight(parseUser.getString(WEIGHT_KEY));
        user.setPosition(parseUser.getString(POSITION_KEY));
        user.setProfile(parseUser.getString(ABOUT_ME_KEY));

        ParseFile file = parseUser.getParseFile(PHOTO_URL);
        user.setPhotoUrl(file == null ? null : file.getUrl());

        if ((!TextUtils.isEmpty(user.getPhotoUrl())) && (user.getPhotoUrl().equals(userPhotoUrl))) {
            user.setPhotoByteArray(userPhotoData);
        }

        String jsonArray = parseUser.getString(TEAMS_KEY);
        List<Team> teams;
        Type listType = new TypeToken<List<Team>>() {
        }.getType();
        if (jsonArray == null) {
            teams = new ArrayList<>();
        } else {
            teams = new Gson().fromJson(jsonArray, listType);
        }
        user.setTeams(teams);

        return user;
    }

    public int getUserAge() {
        if (getCurrentUser().getDateOfBirth() == null) {
            return 0;
        }
        DateFormat sourceFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        Date todayDate = new Date();
        long interval = 0;
        try {
            interval = todayDate.getTime() - sourceFormat.parse(getCurrentUser().getDateOfBirth()).getTime();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        int age = (int) (interval / DateUtils.YEAR_IN_MILLIS);

        return age;
    }

    public void resetPassword(String email, final UserListener listener) {
        ParseUser.requestPasswordResetInBackground(email, new RequestPasswordResetCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    listener.onSuccess();
                } else {
                    listener.onError(e.getLocalizedMessage());
                }
            }
        });
    }

    public boolean isUserCompleteRegistered() {
        ParseUser user = ParseUser.getCurrentUser();
        String jsonArray = user.getString(TEAMS_KEY);
        List<Team> teams;
        Type listType = new TypeToken<List<Team>>() {
        }.getType();
        if (jsonArray == null) {
            teams = new ArrayList<>();
        } else {
            teams = new Gson().fromJson(jsonArray, listType);
        }
        return teams.size() > 0;
    }

    public void addTeam(final Team team, final UserListener listener) {
        final ParseUser user = ParseUser.getCurrentUser();
        String jsonArray = user.getString(TEAMS_KEY);
        final List<Team> teams;
        final Type listType = new TypeToken<List<Team>>() {
        }.getType();
        if (jsonArray == null) {
            teams = new ArrayList<>();
        } else {
            teams = new Gson().fromJson(jsonArray, listType);
        }
        teams.add(team);
        user.put(TEAMS_KEY, new Gson().toJson(teams, listType));
        user.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    listener.onSuccess();
                } else {
                    listener.onError("Connection error");
                    teams.remove(team);
                    user.put(TEAMS_KEY, new Gson().toJson(teams, listType));
                    e.printStackTrace();
                }
            }
        });
    }

    public void updateTeam(Team team) {
        ParseUser user = ParseUser.getCurrentUser();
        String jsonArray = user.getString(TEAMS_KEY);
        List<Team> teams;
        Type listType = new TypeToken<List<Team>>() {
        }.getType();
        if (jsonArray == null) {
            teams = new ArrayList<>();
        } else {
            teams = new Gson().fromJson(jsonArray, listType);
        }
        Team userTeam = teams.get(teams.indexOf(team));
        userTeam.setAllowNotifications(team.isAllowNotifications());
        userTeam.setColor(team.getColor());
        user.put(TEAMS_KEY, new Gson().toJson(teams, listType));
        user.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
            }
        });
    }

    public void deleteTeam(final Team team, final UserListener listener) {
        final ParseUser user = ParseUser.getCurrentUser();
        String jsonArray = user.getString(TEAMS_KEY);
        final List<Team> teams;
        final Type listType = new TypeToken<List<Team>>() {
        }.getType();
        if (jsonArray == null) {
            teams = new ArrayList<>();
        } else {
            teams = new Gson().fromJson(jsonArray, listType);
        }
        teams.remove(team);
        user.put(TEAMS_KEY, new Gson().toJson(teams, listType));
        user.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    listener.onSuccess();
                } else {
                    listener.onError("Connection error");
                    teams.add(team);
                    user.put(TEAMS_KEY, new Gson().toJson(teams, listType));
                    e.printStackTrace();
                }
            }
        });
    }

    public String[] getTeamColorNames() {
        return mTeamColorNames;
    }

    public int[] getTeamColors() {
        return mTeamColors;
    }

    public int getTeamColorRes(String color) {
        int teamColor = 0;
        for (int i = 0; i < mTeamColorNames.length; i++) {
            if (mTeamColorNames[i].equals(color)) {
                teamColor = mTeamColors[i];
            }
        }
        return teamColor;
    }

    public long getCurrentTeamId() {
        return currentTeamId;
    }

    public void setCurrentTeamId(long teamId) {
        currentTeamId = teamId;
    }

    public interface UserListener {

        void onSuccess();

        void onUserLogin(ParseUser user);

        void onError(String message);
    }

    public interface LogoutListener {

        void onLogout();
    }

    private ApiInterface getInterfaceService() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.10.11.54:8080/pureleague/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final ApiInterface mInterfaceService = retrofit.create(ApiInterface.class);
        return mInterfaceService;
    }

}
