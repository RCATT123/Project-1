package com.specifix.pureleagues.api;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Log;

import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseLiveQueryClient;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ProgressCallback;
import com.parse.SaveCallback;
import com.parse.SubscriptionHandling;
import com.specifix.pureleagues.fragment.MessagesFragment;
import com.specifix.pureleagues.model.ChatMessage;
import com.specifix.pureleagues.model.ClubStats;
import com.specifix.pureleagues.model.Division;
import com.specifix.pureleagues.model.Fixture;
import com.specifix.pureleagues.model.FootballAssociation;
import com.specifix.pureleagues.model.Result;
import com.specifix.pureleagues.model.Scorer;
import com.specifix.pureleagues.model.Team;
import com.specifix.pureleagues.model.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import static com.specifix.pureleagues.fragment.MessagesFragment.EVENT_TYPE_FIXTURES;

public class DataManager {
    private static final String ASSOCIATION_PARSE_CLASS_NAME = "League";
    private static final String DIVISION_PARSE_CLASS_NAME = "Division";
    private static final String TEAM_PARSE_CLASS_NAME = "Team";
    // private static final String EVENT_PARSE_CLASS_NAME = "Event";
    private static final String EVENT_MESSAGE_PARSE_CLASS_NAME = "Message";
    private static final String EVENT_ADDRESS = "address";
    private static final String EVENT_CLUB_ONE = "club_one";
    private static final String EVENT_CLUB_TWO = "club_two";
    private static final String EVENT_CLUB_ONE_SCORE = "club_one_score";
    private static final String EVENT_CLUB_TWO_SCORE = "club_two_score";
    private static final String EVENT_DATE = "date";
    private static final String EVENT_TIME = "time";
    private static final String EVENT_MESSAGE_AUTHOR = "author";
    private static final String EVENT_MESSAGE_AUTHOR_OBJECT_ID = "author_object_id";
    private static final String EVENT_MESSAGE_CONTENT = "message";
    private static final String EVENT_MESSAGE_DATE = "date";
    private static final String EVENT_MESSAGE_CREATED_AT = "createdAt";
    private static final String EVENT_MESSAGE_IS_READ = "isRead";
    //  private static final String EVENT_MESSAGE_PARENT = "parent";
    //   private static final String EVENT_MESSAGE_PARENT_FIXTURE = "parentFixture";
//    private static final String EVENT_MESSAGE_PARENT_RESULT = "parentResult";
    private static final String EVENT_MESSAGE_PARENT = "parentEvent";
    //private static final String EVENT_MESSAGE_IMAGE_FILE = "image_file";
    private static final String EVENT_MESSAGE_IMAGE_URL = "image_url";
    private static final String EVENT_MESSAGE_TEAM_ID = "team_id";
    private static final String ASSOCIATION_ID = "leagueid";
    private static final String ASSOCIATION_NAME = "name";
    private static final String DIVISION_ID = "divisionid";
    private static final String DIVSEASON_ID = "divseasid";
    private static final String DIVISION_NAME = "divname";
    private static final String TEAM_ID = "teamid";
    private static final String TEAM_NAME = "teamname";
    private static final String RESULTS_TABLE_TABLE = "ResultsTable";
    private static final String RESULTS_TABLE_TOTAL_WIN = "totalwin";
    private static final String RESULTS_TABLE_POSITION = "position";
    private static final String RESULTS_TABLE_CONCEDE = "concede";
    private static final String RESULTS_TABLE_TOTAL_LOSS = "totalloss";
    private static final String RESULTS_TABLE_TOTAL_DRAW = "totaldraw";
    private static final String RESULTS_TABLE_PTS = "pts";
    private static final String RESULTS_TABLE_SCORED = "scored";
    private static final String RESULTS_TABLE_PLAYED = "played";
    private static final String RESULTS_TABLE_GD = "gd";
    private static final String RESULTS_TABLE_DIVISION_ID = "divisionid";
    private static final String RESULTS_TABLE_LEAGUE_ID = "leagueid";
    private static final String RESULTS_TABLE_TEAMID = "teamid";
    private static final String SCORER_TABLE = "Scorer";
    private static final String SCORER_GOALS = "goals";
    private static final String SCORER_POSITION = "position";
    private static final String SCORER_PLAYER_NAME = "playername";
    private static final String SCORER_TEAM_ID = "teamid";
    private static final String SCORER_DIVISION_ID = "divisionid";
    private static final String FIXTURE_TABLE = "Fixture";
    private static final String FIXTURE_TEAM_AWAY_ID = "teamawayid";
    private static final String FIXTURE_TEAM_HOME_ID = "teamhomeid";
    private static final String FIXTURE_DIVISION_ID = "divisionid";
    private static final String FIXTURE_MATCH_ID = "matchid";
    private static final String FIXTURE_MATCH_DATE = "matchdate";
    private static final String FIXTURE_VENUE = "venue";
    private static final String RESULTS_TABLE = "Result";
    private static final String RESULTS_MATCH_ID = "matchid";
    private static final String RESULTS_AWAY_TEAM_ID = "awayteamid";
    private static final String RESULTS_DIVISION_ID = "divisionid";
    private static final String RESULTS_SCORE = "score";
    private static final String RESULTS_MATCH_DATE = "matchdate";
    private static final String RESULTS_HOME_TEAM_ID = "hometeamid";
    private static final String OBJECT_ID = "objectId";

    private static DataManager instance;
    private List<ParseObject> mEvents;
    private List<Fixture> mFixtures;
    private List<Result> mResults;
    private List<ChatMessage> mGalleryMessages;
    private List<Team> mUserDivisionsTeams;
    private List<ClubStats> mClubStats;
    private List<Scorer> mScores;
    private List<Fixture> mClubFixtures;
    private List<Result> mClubResults;
    //private List<Team> mTeams;
    //private List<Division> mDivisions;

    private DataManager() {
    }

    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    public List<Fixture> getFixtures() {
        return mFixtures;
    }

    public List<Result> getResults() {
        return mResults;
    }

    public List<ClubStats> getClubStats() {
        return mClubStats;
    }

    public List<ChatMessage> getGalleryMessages() {
        return mGalleryMessages;
    }

    public List<Scorer> getScorers() {
        return mScores;
    }

    public List<Team> getUserDivisionsTeams() {
        return mUserDivisionsTeams;
    }

    public List<Fixture> getClubFixtures() {
        return mClubFixtures;
    }

    public void setClubFixtures(List<Fixture> clubFixtures) {
        this.mClubFixtures = clubFixtures;
    }

    public List<Result> getClubResults() {
        return mClubResults;
    }

    public void setClubResults(List<Result> clubResults) {
        this.mClubResults = clubResults;
    }

    public void downloadEvents(final Team team, final Context context, final DataManagerUpdateEvent listener) {
        mFixtures = new ArrayList<>();
        mResults = new ArrayList<>();
        mGalleryMessages = new ArrayList<>();
        mClubFixtures = new ArrayList<>();
        mClubResults = new ArrayList<>();

        if (UserManager.getInstance().getCurrentUser() == null) {
            listener.onFixturesReady();
            listener.onResultsReady();
            return;
        }

        downloadUserDivisionsTeams(team, context, new DataManagerListener() {
            @Override
            public void onDataDownloaded() {
//                List<ParseQuery<ParseObject>> queriesList = new ArrayList<>();
//                for (Team team : userTeam) {
//                    queriesList.add(ParseQuery
//                            .getQuery(FIXTURE_TABLE)
//                            .whereEqualTo(FIXTURE_DIVISION_ID, team.getDivisionId())
//                    );
//                }
//                ParseQuery<ParseObject> fixturesParseQuery = ParseQuery.or(queriesList);
                ParseQuery<ParseObject> parseQuery = ParseQuery
                        .getQuery(FIXTURE_TABLE)
                        .orderByAscending(FIXTURE_MATCH_DATE)
                        .whereEqualTo(FIXTURE_DIVISION_ID, team.getDivisionId())
                        .whereNotEqualTo(FIXTURE_TEAM_AWAY_ID, 0)
                        .whereNotEqualTo(FIXTURE_TEAM_HOME_ID, 0)
                        .setLimit(2000);

                if (!isConnectionAvailable(context)) {
                    parseQuery.fromPin("Fixtures_" + team.getClub());
                }

                parseQuery.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(final List<ParseObject> objects, ParseException e) {
                        if (objects == null) {
                            listener.onFixturesReady();
                            return;
                        }
                        for (ParseObject object : objects) {
                            final Fixture fixture = new Fixture(
                                    object.getObjectId(),
                                    castLong(object.get(FIXTURE_MATCH_ID)),
                                    castLong(object.get(FIXTURE_DIVISION_ID)),
                                    //object.getObjectId(),
                                    castString(object.get(FIXTURE_VENUE)),
                                    castLong(object.get(FIXTURE_TEAM_HOME_ID)),
                                    castLong(object.get(FIXTURE_TEAM_AWAY_ID)),
                                    "",
                                    "",
                                    castString(object.get(FIXTURE_MATCH_DATE))
                            );
                            mFixtures.add(fixture);
                        }

                        List<Team> divisionTeams = getUserDivisionsTeams();
                        Iterator<Fixture> i = mFixtures.iterator();
                        while (i.hasNext()) {
                            Fixture fixture = i.next();
                            boolean foundClubOne = false, foundClubTwo = false;
                            for (Team team : divisionTeams) {
                                if (team.getTeamId() == fixture.getClubOneId()) {
                                    fixture.setClubOneName(team.getClub());
                                    foundClubOne = true;
                                }
                                if (team.getTeamId() == fixture.getClubTwoId()) {
                                    fixture.setClubTwoName(team.getClub());
                                    foundClubTwo = true;
                                }
                                if (foundClubOne && foundClubTwo) {
                                    break;
                                }
                            }
                            if (!foundClubOne || !foundClubTwo) {
                                i.remove();
                            }
                        }

                        ParseObject.unpinAllInBackground("Fixtures_" + team.getClub(), new DeleteCallback() {
                            @Override
                            public void done(ParseException e) {
                                ParseObject.pinAllInBackground("Fixtures_" + team.getClub(), objects);
                            }
                        });

                        listener.onFixturesReady();
                    }
                });

//                queriesList = new ArrayList<>();
//                for (Team team : userTeam) {
//                    queriesList.add(ParseQuery
//                            .getQuery(RESULTS_TABLE)
//                            .whereEqualTo(RESULTS_DIVISION_ID, team.getDivisionId())
//                    );
//                }
//                ParseQuery<ParseObject> resultsParseQuery = ParseQuery.or(queriesList);

                ParseQuery<ParseObject> resultsParseQuery = ParseQuery
                        .getQuery(RESULTS_TABLE)
                        .whereEqualTo(RESULTS_DIVISION_ID, team.getDivisionId())
                        .orderByAscending(RESULTS_MATCH_DATE)
                        .whereNotEqualTo(RESULTS_HOME_TEAM_ID, 0)
                        .whereNotEqualTo(RESULTS_AWAY_TEAM_ID, 0)
                        .setLimit(2000);

                if (!isConnectionAvailable(context)) {
                    resultsParseQuery.fromPin("Results_" + team.getClub());
                }
                resultsParseQuery.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(final List<ParseObject> objects, ParseException e) {
                        if (objects == null) {
                            listener.onResultsReady();
                            return;
                        }
                        for (ParseObject object : objects) {
                            final Result result = new Result(
                                    object.getObjectId(),
                                    castLong(object.get(RESULTS_MATCH_ID)),
                                    castLong(object.get(RESULTS_DIVISION_ID)),
                                    //object.getObjectId(),
                                    castLong(object.get(RESULTS_HOME_TEAM_ID)),
                                    castLong(object.get(RESULTS_AWAY_TEAM_ID)),
                                    "",
                                    "",
                                    castString(object.get(RESULTS_SCORE)),
                                    castString(object.get(RESULTS_MATCH_DATE))
                            );
                            mResults.add(result);
                        }
                        List<Team> divisionTeams = getUserDivisionsTeams();
                        Iterator<Result> i = mResults.iterator();
                        while (i.hasNext()) {
                            Result result = i.next();
                            boolean foundClubOne = false, foundClubTwo = false;
                            for (Team team : divisionTeams) {
                                if (team.getTeamId() == result.getClubOneId()) {
                                    result.setClubOneName(team.getClub());
                                    foundClubOne = true;
                                }
                                if (team.getTeamId() == result.getClubTwoId()) {
                                    result.setClubTwoName(team.getClub());
                                    foundClubTwo = true;
                                }
                                if (foundClubOne && foundClubTwo) {
                                    break;
                                }
                            }
                            if (!foundClubOne || !foundClubTwo) {
                                i.remove();
                            }
                        }

                        ParseObject.unpinAllInBackground("Results_" + team.getClub(), new DeleteCallback() {
                            @Override
                            public void done(ParseException e) {
                                ParseObject.pinAllInBackground("Results_" + team.getClub(), objects);
                            }
                        });

                        listener.onResultsReady();
                    }
                });
            }
        });
    }

    public void downloadUserDivisionsTeams(final Team team, Context context, final DataManagerListener listener) {
        final boolean isConnectionAvailable = isConnectionAvailable(context);

//        List<ParseQuery<ParseObject>> queriesList = new ArrayList<>();

//        final List<Team> userTeam = UserManager.getInstance().getCurrentUser().getTeams();

//        for (Team team : userTeam) {
//            queriesList.add(ParseQuery
//                    .getQuery(TEAM_PARSE_CLASS_NAME)
//                    .whereEqualTo(DIVISION_ID, team.getDivisionId())
//            );
//        }
        ParseQuery<ParseObject> parseQuery = ParseQuery
                .getQuery(TEAM_PARSE_CLASS_NAME)
                .whereEqualTo(DIVISION_ID, team.getDivisionId());

//        ParseQuery<ParseObject> teamsParseQuery = ParseQuery.or(queriesList);
        if (!isConnectionAvailable) {
            parseQuery.fromPin("Teams_" + team.getClub());
        }
        parseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(final List<ParseObject> objects, ParseException e) {
                mUserDivisionsTeams = new ArrayList<>();
                if (objects != null) {
                    for (ParseObject object : objects) {
                        Team team = new Team(
                                castLong(object.get(TEAM_ID)),
                                castLong(object.get(DIVISION_ID)),
                                castString(object.get(DIVISION_NAME)),
                                castString(object.get(TEAM_NAME)),
                                ""
                        );
                        mUserDivisionsTeams.add(team);
                    }
                }
                if (listener != null) listener.onDataDownloaded();

                ParseObject.unpinAllInBackground("Teams_" + team.getClub(), new DeleteCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (objects != null) {
                            ParseObject.pinAllInBackground("Teams_" + team.getClub(), objects);
                        }
                    }
                });
            }
        });
    }

    public void sendMessage(final String eventType, int listPosition, final String userMessage,
                            final byte[] imageToSave, final long currentDate, final long teamId, final DataManagerUpdateEvent listener) {
        final User user = UserManager.getInstance().getCurrentUser();
        final long matchId, divisionId;
        final String objectId, table;
//        ParseObject event;
        if (eventType.equals(EVENT_TYPE_FIXTURES)) {
            objectId = mFixtures.get(listPosition).getId();
            matchId = mFixtures.get(listPosition).getMatch_id();
            divisionId = mFixtures.get(listPosition).getDivision_id();
            table = FIXTURE_TABLE;
        } else {
            objectId = mResults.get(listPosition).getId();
            matchId = mResults.get(listPosition).getMatch_id();
            divisionId = mResults.get(listPosition).getDivision_id();
            table = RESULTS_TABLE;
        }

        //final long currentDate = new Date().getTime();
        ParseQuery<ParseObject> query = ParseQuery.getQuery(table);

        //query.whereEqualTo(OBJECT_ID, objectId);
        query.whereEqualTo(FIXTURE_MATCH_ID, matchId);
        query.whereEqualTo(DIVISION_ID, divisionId);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> list, ParseException e) {
                if (e != null) {
                    return;
                }
                ParseObject event = list.get(0);
                final ParseObject message = new ParseObject(EVENT_MESSAGE_PARSE_CLASS_NAME);
                message.put(EVENT_MESSAGE_AUTHOR, user.getUsername());
                message.put(EVENT_MESSAGE_AUTHOR_OBJECT_ID, user.getObjectId());
                message.put(EVENT_MESSAGE_CONTENT, userMessage);
                message.put(EVENT_MESSAGE_DATE, currentDate);
                message.put(EVENT_MESSAGE_TEAM_ID, teamId);
                message.put(EVENT_MESSAGE_IS_READ, false);
                /*if (eventType.equals(EVENT_TYPE_FIXTURES)) {
                    message.put(EVENT_MESSAGE_PARENT_FIXTURE, event);
                } else {
                    message.put(EVENT_MESSAGE_PARENT_RESULT, event);
                }*/
                message.put(EVENT_MESSAGE_PARENT, castLong(event.get(FIXTURE_MATCH_ID))); //matchId

                if (imageToSave != null) {
                    final ParseFile file = new ParseFile("image.PNG", imageToSave);
                    file.saveInBackground(
                            new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    Log.d("SAVE IMAGE", "Upload completed");
                                    Log.d("SAVE IMAGE", e != null ? e.getMessage() : "no error");
//                                    message.put(EVENT_MESSAGE_IMAGE_FILE, file);
                                    message.put(EVENT_MESSAGE_IMAGE_URL, file.getUrl());
                                    message.saveInBackground(new SaveCallback() {
                                        @Override
                                        public void done(ParseException e) {

                                            if (eventType.equals(EVENT_TYPE_FIXTURES)) {
                                                updateFixtures(objectId, listener);
                                            } else {
                                                updateResults(objectId, listener);
                                            }
                                        }
                                    });
//                                    if (eventType.equals(EVENT_TYPE_FIXTURES)) {
//                                        updateFixtures(objectId, listener);
//                                    } else {
//                                        updateResults(objectId, listener);
//                                    }
                                    //updateGallery(null);
                                }
                            },
                            new ProgressCallback() {
                                @Override
                                public void done(Integer percentDone) {
                                    Log.d("SAVE IMAGE", percentDone.toString());
                                }
                            });
                } else {
                    message.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {

                            if (eventType.equals(EVENT_TYPE_FIXTURES)) {
                                updateFixtures(objectId, listener);
                            } else {
                                updateResults(objectId, listener);
                            }
                        }
                    });

//                    if (eventType.equals(EVENT_TYPE_FIXTURES)) {
//                        updateFixtures(objectId, listener);
//                    } else {
//                        updateResults(objectId, listener);
//                    }
                    //updateGallery(null);
                }
            }
        });

    }

    public void subscribeToNewMessages(final ParseLiveQueryClient mParseLiveQueryClient, final String eventType,
                                       int listPosition, final DataManagerNewMessageEvent listener) {
        final User user = UserManager.getInstance().getCurrentUser();
        final long matchId, divisionId;
        final String objectId, table;

        ParseQuery<ParseObject> queryToBind = ParseQuery.getQuery(EVENT_MESSAGE_PARSE_CLASS_NAME);
        queryToBind.whereEqualTo(EVENT_MESSAGE_TEAM_ID, UserManager.getInstance().getCurrentTeamId());

        if (listPosition > 0 && !TextUtils.isEmpty(eventType)) {
            if (eventType.equals(EVENT_TYPE_FIXTURES)) {
                objectId = mFixtures.get(listPosition).getId();
                matchId = mFixtures.get(listPosition).getMatch_id();
            } else {
                objectId = mResults.get(listPosition).getId();
                matchId = mResults.get(listPosition).getMatch_id();
            }
            queryToBind.whereEqualTo(EVENT_MESSAGE_PARENT, matchId);
        }

        SubscriptionHandling<ParseObject> subscriptionHandling = mParseLiveQueryClient.subscribe(queryToBind);
        subscriptionHandling.handleSubscribe(new SubscriptionHandling.HandleSubscribeCallback<ParseObject>() {
            @Override
            public void onSubscribe(ParseQuery<ParseObject> query) {
            }
        });

        subscriptionHandling.handleEvents(new SubscriptionHandling.HandleEventsCallback<ParseObject>() {
            @Override
            public void onEvents(ParseQuery<ParseObject> query, SubscriptionHandling.Event event, ParseObject message) {
                ChatMessage cm = new ChatMessage(
                        message.getObjectId(),
                        castString(message.get(EVENT_MESSAGE_AUTHOR)),
                        castString(message.get(EVENT_MESSAGE_AUTHOR_OBJECT_ID)),
                        castLong(message.get(EVENT_MESSAGE_PARENT)),
                        castString(message.get(EVENT_MESSAGE_CONTENT)),
                        castString(message.get(EVENT_MESSAGE_IMAGE_URL)),
                        message.getCreatedAt().getTime(),
                        castLong(message.get(EVENT_MESSAGE_DATE)),
                        castLong(message.get(EVENT_MESSAGE_TEAM_ID)),
                        message.getBoolean(EVENT_MESSAGE_IS_READ));

                if (listener != null) {
                    if (TextUtils.isEmpty(eventType) || eventType.equals(EVENT_TYPE_FIXTURES)) {
                        listener.onFixturesReady(cm);
                    } else {
                        listener.onResultsReady(cm);
                    }
                }
            }
        });

    }

    public void updateFixtures(final String objectId, final DataManagerUpdateEvent messagesUpdateListener) {
        List<Team> userTeam = UserManager.getInstance().getCurrentUser().getTeams();
        if (userTeam == null || userTeam.size() == 0) {
            if (messagesUpdateListener != null)
                messagesUpdateListener.onFixturesReady();
            return;
        }

        final long currentTeamId = UserManager.getInstance().getCurrentTeamId();

        List<ParseQuery<ParseObject>> queriesList = new ArrayList<>();
        for (Team team : userTeam) {
            queriesList.add(ParseQuery
                            .getQuery(FIXTURE_TABLE)
//                    .orderByAscending(FIXTURE_MATCH_DATE)
                            .whereEqualTo(FIXTURE_DIVISION_ID, team.getDivisionId())
            );
        }

        ParseQuery.or(queriesList)
                .orderByAscending(FIXTURE_MATCH_DATE)
                .setLimit(1000).findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e != null) {
                    messagesUpdateListener.onFixturesReady();
                    return;
                }

                for (ParseObject object : objects) {
                    if (!object.getObjectId().equals(objectId)) {
                        continue;
                    }

                    final Fixture fixture = new Fixture(
                            object.getObjectId(),
                            castLong(object.get(FIXTURE_MATCH_ID)),
                            castLong(object.get(FIXTURE_DIVISION_ID)),
                            castString(object.get(FIXTURE_VENUE)),
                            castLong(object.get(FIXTURE_TEAM_HOME_ID)),
                            castLong(object.get(FIXTURE_TEAM_AWAY_ID)),
                            "",
                            "",
                            castString(object.get(FIXTURE_MATCH_DATE))
                    );

                    ParseQuery<ParseObject> query = ParseQuery.getQuery(EVENT_MESSAGE_PARSE_CLASS_NAME);
//                    query.whereEqualTo(EVENT_MESSAGE_PARENT_FIXTURE, object);
                    query.whereEqualTo(EVENT_MESSAGE_PARENT, castLong(object.get(FIXTURE_MATCH_ID)));
                    query.whereEqualTo(EVENT_MESSAGE_TEAM_ID, currentTeamId);
                    query.orderByDescending(EVENT_MESSAGE_CREATED_AT);
                    query.findInBackground(new FindCallback<ParseObject>() {
                        public void done(List<ParseObject> messagesList, ParseException e) {
                            if (e != null) return;

                            List<ChatMessage> chatMessages = new ArrayList<>();

                            if (messagesList != null) {

                                for (ParseObject message : messagesList) {
//                                ParseFile file = message.getParseFile(EVENT_MESSAGE_IMAGE_FILE);

                                    ChatMessage cm = new ChatMessage(
                                            message.getObjectId(),
                                            castString(message.get(EVENT_MESSAGE_AUTHOR)),
                                            castString(message.get(EVENT_MESSAGE_AUTHOR_OBJECT_ID)),
                                            castLong(message.get(EVENT_MESSAGE_PARENT)),
                                            castString(message.get(EVENT_MESSAGE_CONTENT)),
                                            //file == null ? null : file.getUrl(),
                                            castString(message.get(EVENT_MESSAGE_IMAGE_URL)),
                                            message.getCreatedAt().getTime(),
                                            castLong(message.get(EVENT_MESSAGE_DATE)),
                                            castLong(message.get(EVENT_MESSAGE_TEAM_ID)),
                                            message.getBoolean(EVENT_MESSAGE_IS_READ));
                                    chatMessages.add(cm);
                                }
                            }
                            //Collections.reverse(chatMessages);
                            fixture.setMessages(chatMessages);

                            String fixtureId = fixture.getId();
                            boolean isOld = false;
                            for (int i = 0; i < mFixtures.size(); i++) {
                                Fixture oldFixture = mFixtures.get(i);
                                if (oldFixture.getId().equals(fixtureId)) {
                                    fixture.setClubOneName(oldFixture.getClubOneName());
                                    fixture.setClubTwoName(oldFixture.getClubTwoName());
//                            mFixtures.remove(i);
//                            mFixtures.add(i, fixture);
                                    mFixtures.get(i).setMessages(chatMessages);
                                    //
                                    isOld = true;
                                    break;
                                }
                            }
                            if (!isOld) {
                                List<Team> divisionTeams = getUserDivisionsTeams();

                                boolean foundClubOne = false, foundClubTwo = false;
                                for (Team team : divisionTeams) {
                                    if (team.getTeamId() == fixture.getClubOneId()) {
                                        fixture.setClubOneName(team.getClub());
                                        foundClubOne = true;
                                    }
                                    if (team.getTeamId() == fixture.getClubTwoId()) {
                                        fixture.setClubTwoName(team.getClub());
                                        foundClubTwo = true;
                                    }
                                    if (foundClubOne && foundClubTwo) {
                                        break;
                                    }
                                }
                                mFixtures.add(fixture);
                            }

                            if (messagesUpdateListener != null)
                                messagesUpdateListener.onFixturesReady();
                        }
                    });


                }
            }
        });
    }

    public void updateResults(final String objectId, final DataManagerUpdateEvent messagesUpdateListener) {
        List<Team> userTeam = UserManager.getInstance().getCurrentUser().getTeams();
        if (userTeam == null || userTeam.size() == 0) {
            if (messagesUpdateListener != null)
                messagesUpdateListener.onFixturesReady();
            return;
        }

        final long currentTeamId = UserManager.getInstance().getCurrentTeamId();

        List<ParseQuery<ParseObject>> queriesList = new ArrayList<>();
        for (Team team : userTeam) {
            queriesList.add(ParseQuery
                    .getQuery(RESULTS_TABLE)
                    .whereEqualTo(RESULTS_DIVISION_ID, team.getDivisionId())
            );
        }

        ParseQuery.or(queriesList).orderByAscending(RESULTS_MATCH_DATE)
                .setLimit(1000).findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e != null) {
                    messagesUpdateListener.onResultsReady();
                    return;
                }

                for (ParseObject object : objects) {
                    if (!object.getObjectId().equals(objectId)) {
                        continue;
                    }

                    final Result result = new Result(
                            object.getObjectId(),
                            castLong(object.get(RESULTS_MATCH_ID)),
                            castLong(object.get(RESULTS_DIVISION_ID)),
                            castLong(object.get(RESULTS_HOME_TEAM_ID)),
                            castLong(object.get(RESULTS_AWAY_TEAM_ID)),
                            "",
                            "",
                            castString(object.get(RESULTS_SCORE)),
                            castString(object.get(RESULTS_MATCH_DATE))
                    );

                    ParseQuery<ParseObject> query = ParseQuery.getQuery(EVENT_MESSAGE_PARSE_CLASS_NAME);
//                    query.whereEqualTo(EVENT_MESSAGE_PARENT_RESULT, object);
                    query.whereEqualTo(EVENT_MESSAGE_PARENT, castLong(object.get(FIXTURE_MATCH_ID)));
                    query.whereEqualTo(EVENT_MESSAGE_TEAM_ID, currentTeamId);
                    query.orderByDescending(EVENT_MESSAGE_CREATED_AT);
                    query.findInBackground(new FindCallback<ParseObject>() {
                        public void done(List<ParseObject> messagesList, ParseException e) {
                            List<ChatMessage> chatMessages = new ArrayList<>();
                            for (ParseObject message : messagesList) {
//                                ParseFile file = message.getParseFile(EVENT_MESSAGE_IMAGE_FILE);
                                ChatMessage cm = new ChatMessage(
                                        message.getObjectId(),
                                        castString(message.get(EVENT_MESSAGE_AUTHOR)),
                                        castString(message.get(EVENT_MESSAGE_AUTHOR_OBJECT_ID)),
                                        castLong(message.get(EVENT_MESSAGE_PARENT)),
                                        castString(message.get(EVENT_MESSAGE_CONTENT)),
                                        //file == null ? null : file.getUrl(),
                                        castString(message.get(EVENT_MESSAGE_IMAGE_URL)),
                                        message.getCreatedAt().getTime(),
                                        castLong(message.get(EVENT_MESSAGE_DATE)),
                                        castLong(message.get(EVENT_MESSAGE_TEAM_ID)),
                                        message.getBoolean(EVENT_MESSAGE_IS_READ));
                                chatMessages.add(cm);
                            }
                            //Collections.reverse(chatMessages);
                            result.setMessages(chatMessages);

                            String resultId = result.getId();
                            boolean isOld = false;
                            for (int i = 0; i < mResults.size(); i++) {

                                Result oldResult = mResults.get(i);
                                if (oldResult.getId().equals(resultId)) {
                                    result.setClubOneName(oldResult.getClubOneName());
                                    result.setClubTwoName(oldResult.getClubTwoName());
//                                    mResults.remove(i);
//                                    mResults.add(i, result);
                                    mResults.get(i).setMessages(chatMessages);
                                    isOld = true;
                                    break;
                                }
                            }
                            if (!isOld) {
                                List<Team> divisionTeams = getUserDivisionsTeams();

                                boolean foundClubOne = false, foundClubTwo = false;
                                for (Team team : divisionTeams) {
                                    if (team.getTeamId() == result.getClubOneId()) {
                                        result.setClubOneName(team.getClub());
                                        foundClubOne = true;
                                    }
                                    if (team.getTeamId() == result.getClubTwoId()) {
                                        result.setClubTwoName(team.getClub());
                                        foundClubTwo = true;
                                    }
                                    if (foundClubOne && foundClubTwo) {
                                        break;
                                    }
                                }
                                mResults.add(result);
                            }

                            if (messagesUpdateListener != null)
                                messagesUpdateListener.onResultsReady();
                        }
                    });


                }
            }
        });
    }

    public void downloadMessages(Context context, final DataManagerUpdateListener listener) {
        final long currentTeamId = UserManager.getInstance().getCurrentTeamId();

        for (int i = 0; i < mFixtures.size(); i++) {
            final Fixture fixture = mFixtures.get(i);
            final int position = i;

            ParseQuery<ParseObject> query = ParseQuery.getQuery(EVENT_MESSAGE_PARSE_CLASS_NAME);
//            query.whereContains(EVENT_MESSAGE_PARENT_FIXTURE, fixture.getId());
            query.whereEqualTo(EVENT_MESSAGE_PARENT, fixture.getMatch_id());
            query.whereEqualTo(EVENT_MESSAGE_TEAM_ID, currentTeamId);
//            query.orderByDescending(EVENT_DATE);
            query.orderByDescending(EVENT_MESSAGE_CREATED_AT);
            if (!isConnectionAvailable(context)) {
                query.fromLocalDatastore();
            }
            query.findInBackground(new FindCallback<ParseObject>() {
                public void done(final List<ParseObject> messagesList, ParseException e) {
                    if (messagesList == null)
                        return;

                    List<ChatMessage> chatMessages = new ArrayList<>();
                    for (ParseObject message : messagesList) {
//                        ParseFile file = message.getParseFile(EVENT_MESSAGE_IMAGE_FILE);
                        ChatMessage cm = new ChatMessage(
                                message.getObjectId(),
                                castString(message.get(EVENT_MESSAGE_AUTHOR)),
                                castString(message.get(EVENT_MESSAGE_AUTHOR_OBJECT_ID)),
                                castLong(message.get(EVENT_MESSAGE_PARENT)),
                                castString(message.get(EVENT_MESSAGE_CONTENT)),
                                //file == null ? null : file.getUrl(),
                                castString(message.get(EVENT_MESSAGE_IMAGE_URL)),
                                message.getCreatedAt().getTime(),
//                                message.getLong(EVENT_MESSAGE_DATE),
                                castLong(message.get(EVENT_MESSAGE_DATE)),
                                castLong(message.get(EVENT_MESSAGE_TEAM_ID)),
                                message.getBoolean(EVENT_MESSAGE_IS_READ));
                        chatMessages.add(cm);
                    }
                    //Collections.reverse(chatMessages);
                    fixture.setMessages(chatMessages);

                    ParseObject.unpinAllInBackground(messagesList, new DeleteCallback() {
                        @Override
                        public void done(ParseException e) {
                            ParseObject.pinAllInBackground(messagesList);
                        }
                    });

                    if (mClubFixtures.contains(fixture)) {
                        mClubFixtures.get(mClubFixtures.indexOf(fixture)).setMessages(chatMessages);
                        listener.onMessageDownloaded(MessagesFragment.EVENT_TYPE_FIXTURES, mClubFixtures.indexOf(fixture));
                    }
                }
            });
        }

        for (int i = 0; i < mResults.size(); i++) {
            final Result result = mResults.get(i);
            final int position = i;

            ParseQuery<ParseObject> query = ParseQuery.getQuery(EVENT_MESSAGE_PARSE_CLASS_NAME);
//            query.whereContains(EVENT_MESSAGE_PARENT_RESULT, result.getId());
            query.whereEqualTo(EVENT_MESSAGE_PARENT, result.getMatch_id());
            query.whereEqualTo(EVENT_MESSAGE_TEAM_ID, currentTeamId);
            query.orderByDescending(EVENT_MESSAGE_CREATED_AT);
            if (!isConnectionAvailable(context)) {
                query.fromLocalDatastore();
            }
            query.findInBackground(new FindCallback<ParseObject>() {
                public void done(final List<ParseObject> messagesList, ParseException e) {
                    if (messagesList == null) {
                        listener.onMessageDownloaded(MessagesFragment.EVENT_TYPE_RESULTS, position);
                        return;
                    }

                    List<ChatMessage> chatMessages = new ArrayList<>();
                    for (ParseObject message : messagesList) {
//                        ParseFile file = message.getParseFile(EVENT_MESSAGE_IMAGE_FILE);
                        ChatMessage cm = new ChatMessage(
                                message.getObjectId(),
                                castString(message.get(EVENT_MESSAGE_AUTHOR)),
                                castString(message.get(EVENT_MESSAGE_AUTHOR_OBJECT_ID)),
                                castLong(message.get(EVENT_MESSAGE_PARENT)),
                                castString(message.get(EVENT_MESSAGE_CONTENT)),
                                //file == null ? null : file.getUrl(),
                                castString(message.get(EVENT_MESSAGE_IMAGE_URL)),
                                message.getCreatedAt().getTime(),
                                castLong(message.get(EVENT_MESSAGE_DATE)),
                                castLong(message.get(EVENT_MESSAGE_TEAM_ID)),
                                message.getBoolean(EVENT_MESSAGE_IS_READ));
                        chatMessages.add(cm);
                    }
                    //Collections.reverse(chatMessages);
                    result.setMessages(chatMessages);

                    ParseObject.unpinAllInBackground(messagesList, new DeleteCallback() {
                        @Override
                        public void done(ParseException e) {
                            ParseObject.pinAllInBackground(messagesList);
                        }
                    });

                    listener.onMessageDownloaded(MessagesFragment.EVENT_TYPE_RESULTS, position);
                }
            });
        }
    }

    private long lastSelectedTeam = -1;

    public void updateGallery(Context context, final DataManagerListener listener) {
        final long currentTeamId = UserManager.getInstance().getCurrentTeamId();
        if (lastSelectedTeam != currentTeamId) {
            mGalleryMessages.clear();
            lastSelectedTeam = currentTeamId;
        }
        final int currentSize = mGalleryMessages.size();
        if (UserManager.getInstance().getCurrentUser() == null)
            return;

        ParseQuery<ParseObject> query = ParseQuery.getQuery(EVENT_MESSAGE_PARSE_CLASS_NAME);
        //query.whereExists(EVENT_MESSAGE_IMAGE_FILE);
        query.whereExists(EVENT_MESSAGE_IMAGE_URL);
//        query.whereEqualTo(EVENT_MESSAGE_AUTHOR_OBJECT_ID,
//                UserManager.getInstance().getCurrentUser().getObjectId());
        query.whereEqualTo(EVENT_MESSAGE_TEAM_ID, currentTeamId);
        query.orderByDescending(EVENT_MESSAGE_CREATED_AT);
        if (!isConnectionAvailable(context)) {
            query.fromPin("Gallery");
        }
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(final List<ParseObject> messagesList, ParseException e) {
                if (messagesList == null)
                    return;

                List<ChatMessage> chatMessages = new ArrayList<>();
                for (ParseObject message : messagesList) {
//                    ParseFile file = message.getParseFile(EVENT_MESSAGE_IMAGE_FILE);
                    ChatMessage cm = new ChatMessage(
                            message.getObjectId(),
                            castString(message.get(EVENT_MESSAGE_AUTHOR)),
                            castString(message.get(EVENT_MESSAGE_AUTHOR_OBJECT_ID)),
                            castLong(message.get(EVENT_MESSAGE_PARENT)),
                            castString(message.get(EVENT_MESSAGE_CONTENT)),
                            //file == null ? null : file.getUrl(),
                            castString(message.get(EVENT_MESSAGE_IMAGE_URL)),
                            message.getCreatedAt().getTime(),
                            castLong(message.get(EVENT_MESSAGE_DATE)),
                            castLong(message.get(EVENT_MESSAGE_TEAM_ID)),
                            message.getBoolean(EVENT_MESSAGE_IS_READ));
                    chatMessages.add(cm);
                }
                //Collections.reverse(chatMessages);

                int newSize = chatMessages.size() - currentSize;
                if (newSize > 0 && newSize <= chatMessages.size()) {
                    mGalleryMessages.addAll(0, chatMessages.subList(0, newSize));
                } else if (chatMessages.size() >= 100) {
                    mGalleryMessages.clear();
                    mGalleryMessages.addAll(chatMessages);
                }

                ParseObject.unpinAllInBackground("Gallery", new DeleteCallback() {
                    @Override
                    public void done(ParseException e) {
                        ParseObject.pinAllInBackground("Gallery", messagesList);
                    }
                });

                if (listener != null) {
                    listener.onDataDownloaded();
                }
            }
        });

    }

    public void downloadClubStats(final Team team, Context context, final List<Team> allTeams, final DataManagerPickListener listener) {

        ParseQuery<ParseObject> query = ParseQuery.getQuery(RESULTS_TABLE_TABLE);
        query.whereEqualTo(RESULTS_TABLE_DIVISION_ID, team.getDivisionId());
        if (!isConnectionAvailable(context)) {
            query.fromPin("ResultsTable_" + team.getClub());
        }
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(final List<ParseObject> objects, ParseException e) {
                mClubStats = new ArrayList<>();

                if (objects != null) {

                    for (ParseObject object : objects) {
                        String teamName = "";
                        long teamId = castLong(object.get(RESULTS_TABLE_TEAMID));
                        for (Team team : allTeams) {
                            if (team.getTeamId() == teamId) {
                                teamName = team.getClub();
                            }
                        }

                        ClubStats clubStats = new ClubStats(
                                teamId,
                                teamName,
                                castLong(object.get(RESULTS_TABLE_PLAYED)),
                                castLong(object.get(RESULTS_TABLE_TOTAL_WIN)),
                                castLong(object.get(RESULTS_TABLE_TOTAL_DRAW)),
                                castLong(object.get(RESULTS_TABLE_TOTAL_LOSS)),
                                castLong(object.get(RESULTS_TABLE_SCORED)),
                                castLong(object.get(RESULTS_TABLE_CONCEDE)),
                                castLong(object.get(RESULTS_TABLE_GD)),
                                castLong(object.get(RESULTS_TABLE_PTS))
                        );
                        mClubStats.add(clubStats);
                    }
                }

                ParseObject.unpinAllInBackground("ResultsTable_" + team.getClub(), new DeleteCallback() {
                    @Override
                    public void done(ParseException e) {
                        ParseObject.pinAllInBackground("ResultsTable_" + team.getClub(), objects);
                    }
                });

                if (listener != null)
                    listener.onListReady(mClubStats);
            }
        });
    }

    public void downloadScorers(Context context, final List<Team> allTeams, final long divisionId, long teamId, final DataManagerPickListener listener) {
        User user = UserManager.getInstance().getCurrentUser();
        final List<Team> teams = user.getTeams();
        if (teams == null) {
            return;
        }
        //Team currentTeam = teams.get(0);
        ParseQuery<ParseObject> query = ParseQuery.getQuery(SCORER_TABLE);
        query.whereEqualTo(SCORER_DIVISION_ID, divisionId);
        if (teamId != -1) {
            query.whereEqualTo(SCORER_TEAM_ID, teamId);
        }
        //query.whereNotEqualTo(SCORER_GOALS, "");

//        query.orderByDescending(SCORER_GOALS);
//        query.setLimit(20);
        query.setLimit(2000);
        if (!isConnectionAvailable(context)) {
            query.fromPin("Scorers");
        }
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(final List<ParseObject> objects, ParseException e) {
                mScores = new ArrayList<>();

                if (objects != null) {

                    for (ParseObject object : objects) {
                        String teamName = "";
                        long teamId = castLong(object.get(SCORER_TEAM_ID));
                        for (Team team : allTeams) {
                            if (team.getTeamId() == teamId) {
                                teamName = team.getClub();
                            }
                        }

                        Scorer scorer = new Scorer(
                                castString(object.get(SCORER_PLAYER_NAME)),
                                castLong(object.get(SCORER_DIVISION_ID)),
                                castLong(object.get(SCORER_TEAM_ID)),
                                castLong(object.get(SCORER_POSITION)),
                                castString(object.get(SCORER_GOALS))
                        );
                        mScores.add(scorer);
                    }
                }

                Collections.sort(mScores, new Comparator<Scorer>() {
                    @Override
                    public int compare(Scorer o1, Scorer o2) {
                        long p1 = o1.getPosition();
                        long p2 = o2.getPosition();
                        if (p1 < p2)
                            return -1;
                        if (p1 > p2)
                            return 1;
                        return 0;
                    }
                });
                mScores = mScores.subList(0, Math.min(20, mScores.size()));

                ParseObject.unpinAllInBackground("Scorers", new DeleteCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (objects != null) {
                            ParseObject.pinAllInBackground("Scorers", objects);
                        }
                    }
                });

                if (listener != null)
                    listener.onListReady(mScores);
            }
        });
    }


    public void getAssociations(final DataManagerPickListener listener, String pattern) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery(ASSOCIATION_PARSE_CLASS_NAME);
        if (!pattern.equals("")) {
            query.whereMatches(ASSOCIATION_NAME, "(" + pattern + ")", "i");
        }
        query.orderByAscending(ASSOCIATION_NAME);
        query.setLimit(2000);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    List<FootballAssociation> list = new ArrayList<>();
                    for (ParseObject object : objects) {
                        FootballAssociation association = new FootballAssociation(
                                castLong(object.get(ASSOCIATION_ID)),
                                "",
                                castString(object.get(ASSOCIATION_NAME))
                        );
                        list.add(association);
                    }
                    listener.onListReady(list);
                } else {
                    listener.onError("Connection error");
                }
            }
        });
    }

    public void getDivisions(final DataManagerPickListener listener, long associationId) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery(DIVISION_PARSE_CLASS_NAME);
        query.whereEqualTo(ASSOCIATION_ID, associationId);
        query.orderByAscending(DIVISION_NAME);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    List<Division> list = new ArrayList<>();
                    for (ParseObject object : objects) {
                        Division association = new Division(
                                castLong(object.get(DIVISION_ID)),
                                castLong(object.get(ASSOCIATION_ID)),
                                castLong(object.get(DIVSEASON_ID)),
                                castString(object.get(DIVISION_NAME))
                        );
                        list.add(association);
                    }
                    listener.onListReady(list);
                } else {
                    listener.onError("Connection error");
                }
            }
        });
    }

    public void getClubs(Context context, final DataManagerPickListener listener, final long divisionId) {
        /*if (mTeams != null && mTeams.size() != 0) {
            listener.onListReady(mTeams);
            return;
        }*/

        ParseQuery<ParseObject> query = ParseQuery.getQuery(TEAM_PARSE_CLASS_NAME);
        query.orderByAscending(TEAM_NAME);
        query.whereEqualTo(DIVISION_ID, divisionId);
        if (!isConnectionAvailable(context)) {
            query.fromPin("Team_" + divisionId);
        }
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(final List<ParseObject> objects, ParseException e) {
                ArrayList<Team> mTeams = new ArrayList<>();
                if (objects == null) {
                    listener.onListReady(mTeams);
                    return;
                }

                for (ParseObject object : objects) {
                    Team team = new Team(
                            castLong(object.get(TEAM_ID)),
                            castLong(object.get(DIVISION_ID)),
                            castString(object.get(DIVISION_NAME)),
                            castString(object.get(TEAM_NAME)),
                            ""
                    );
                    mTeams.add(team);
                }

                ParseObject.unpinAllInBackground("Team_" + divisionId, new DeleteCallback() {
                    @Override
                    public void done(ParseException e) {
                        ParseObject.pinAllInBackground("Team_" + divisionId, objects);
                    }
                });

                listener.onListReady(mTeams);
            }
        });
    }

    public boolean isConnectionAvailable(Context context) {
        if (context == null) {
            return false;
        }
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public List<Fixture> sortClubFixturesAndGet(Team team) {
        if (mClubFixtures != null) {
            mClubFixtures.clear();
        } else {
            mClubFixtures = new ArrayList<>();
        }
        if (team == null) {
            return mClubFixtures;
        }
        if (mFixtures == null) {
            mFixtures = new ArrayList<>();
        }
        for (Fixture fixture : mFixtures) {
            if (fixture.getClubOneId() == team.getTeamId()) {
                mClubFixtures.add(fixture);
            }
            if (fixture.getClubTwoId() == team.getTeamId()) {
                mClubFixtures.add(fixture);
            }
        }
        return mClubFixtures;
    }

    public List<Result> sortClubResultsAndGet(Team team) {
        if (mClubResults != null) {
            mClubResults.clear();
        } else {
            mClubResults = new ArrayList<>();
        }
        if (team == null) {
            return mClubResults;
        }
        if (mResults == null) {
            mResults = new ArrayList<>();
        }
        for (Result result : mResults) {
            if (result.getClubOneId() == team.getTeamId()) {
                mClubResults.add(result);
            }
            if (result.getClubTwoId() == team.getTeamId()) {
                mClubResults.add(result);
            }
        }
        return mClubResults;
    }

    private static long castLong(Object object) {
        if (object == null)
            return 0;
        return Long.parseLong(object.toString());
    }

    private static String castString(Object object) {
        if (object == null)
            return null;
        return object.toString();
    }

    public interface DataManagerUpdateEvent {
        void onFixturesReady();

        void onResultsReady();
    }

    public interface DataManagerNewMessageEvent {
        void onFixturesReady(ChatMessage message);

        void onResultsReady(ChatMessage message);
    }

    public interface DataManagerPickListener {
        void onListReady(List<?> list);

        void onError(String message);
    }

    public interface DataManagerListener {
        void onDataDownloaded();
    }


    public interface DataManagerUpdateListener {
        void onMessageDownloaded(String eventType, int i);
    }

    public interface DataManagerSendListener {
        void onMessageSend();
    }
}
