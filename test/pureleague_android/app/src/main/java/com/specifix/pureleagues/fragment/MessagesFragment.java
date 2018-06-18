package com.specifix.pureleagues.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.Glide;
import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseLiveQueryClient;
import com.specifix.pureleagues.R;
import com.specifix.pureleagues.activity.PhotoPickerFragment;
import com.specifix.pureleagues.adapter.MessagesAdapter;
import com.specifix.pureleagues.api.DataManager;
import com.specifix.pureleagues.api.UserManager;
import com.specifix.pureleagues.manager.PrefsManager;
import com.specifix.pureleagues.model.ChatMessage;
import com.specifix.pureleagues.model.Team;
import com.specifix.pureleagues.util.ImageHelper;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import pl.aprilapps.easyphotopicker.EasyImageConfig;
import pl.tajchert.nammu.Nammu;
import pl.tajchert.nammu.PermissionCallback;

import static com.specifix.pureleagues.fragment.EditProfileFragment.TEAM_ID;

public class MessagesFragment extends PhotoPickerFragment {
    private static final String EVENT_TYPE_KEY = "event_type";
    private static final String EVENT_LIST_POSITION_KEY = "event_list_position";
    public static final String EVENT_TYPE_FIXTURES = "event_type_fixtures";
    public static final String EVENT_TYPE_RESULTS = "event_type_results";
    private static final int PICK_IMAGE_REQUEST = 212;

    @BindView(R.id.messages_fragment_label)
    TextView mFragmentLabel;
    @BindView(R.id.messages_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.messages_content_edit_text)
    EditText mNewMessageEditText;
    @BindView(R.id.messages_new_image)
    ImageView mMessageImage;
    @BindView(R.id.messages_send_button)
    View mSendBtn;
    /* @BindView(R.id.messages_scroll_view)
     ScrollView mScrollView;*/
//    @BindView(R.id.footer)
//    View mFooter;
    @BindView(R.id.messages_picker_progress_bar)
    ProgressBar mProgressBar;
    private String mEventType;
    private int mEventListPosition;
    private MessagesAdapter mAdapter;
    private List<ChatMessage> mMessagesList;
    //private Bitmap pickedImage;
    Uri mImageUri;
    ParseLiveQueryClient mParseLiveQueryClient;

    public static MessagesFragment newInstance(String eventType, int position) {
        MessagesFragment fragment = new MessagesFragment();
        Bundle args = new Bundle();
        args.putString(EVENT_TYPE_KEY, eventType);
        args.putInt(EVENT_LIST_POSITION_KEY, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Nammu.init(getContext());

        Bundle args = getArguments();
        mEventType = args.getString(EVENT_TYPE_KEY);
        mEventListPosition = args.getInt(EVENT_LIST_POSITION_KEY);

        mParseLiveQueryClient = ParseLiveQueryClient.Factory.getClient();
        DataManager.getInstance().subscribeToNewMessages(
                mParseLiveQueryClient,
                getArguments().getString(EVENT_TYPE_KEY),
                getArguments().getInt(EVENT_LIST_POSITION_KEY),
                new DataManager.DataManagerNewMessageEvent() {
                    @Override
                    public void onFixturesReady(ChatMessage message) {
                        updateMessages(message);
                    }

                    @Override
                    public void onResultsReady(ChatMessage message) {
                        updateMessages(message);
                    }
                }
        );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_messages, container, false);
        ButterKnife.bind(this, view);

        mNewMessageEditText.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "GothamBook.otf"));
//        mSendBtn.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "GothamBold.otf"));

        /*mNewMessageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int lines = mNewMessageEditText.getLineCount();
                if (lines > 2) {
                    String s = editable.toString();
                    int start = mNewMessageEditText.getSelectionStart();
                    int end = mNewMessageEditText.getSelectionEnd();

                    if (start == end && start < s.length() && start > 1) {
                        s = s.substring(0, start - 1) + s.substring(start);
                    } else {
                        s = s.substring(0, s.length() - 1);
                    }

                    mNewMessageEditText.setText(s);
                    mNewMessageEditText.setSelection(mNewMessageEditText.getText().length());
                }
            }
        });*/

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = getActivity().getCurrentFocus();
        if (view == null) {
            view = new View(getActivity());
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    //    private void scrollToButton() {
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mScrollView.smoothScrollTo(0, mSendBtn.getBottom());
//            }
//        }, 200);
//    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setReverseLayout(true);
//        layoutManager.setStackFromEnd(true);

        mRecyclerView.setLayoutManager(layoutManager);
        //List<ChatMessage> messagesList;
        if (mEventType.equals(EVENT_TYPE_FIXTURES)) {
            mMessagesList = DataManager.getInstance().getFixtures().get(mEventListPosition).getMessages();
            if (mMessagesList == null || mMessagesList.size() == 0) {
                DataManager.getInstance().updateFixtures(
                        DataManager.getInstance().getFixtures().get(mEventListPosition).getId(),
                        new DataManager.DataManagerUpdateEvent() {
                            @Override
                            public void onFixturesReady() {
                                mProgressBar.setVisibility(View.GONE);
                                mMessagesList = DataManager.getInstance().getFixtures().get(mEventListPosition).getMessages();
                                mAdapter = new MessagesAdapter(getContext(),
                                        mMessagesList == null ? new ArrayList<ChatMessage>() : mMessagesList,
                                        new MessagesAdapter.MessageAdapterListener() {
                                            @Override
                                            public void onImageClick(String imageUrl) {
                                                MessageImageDialogFragment dialog = MessageImageDialogFragment.newInstance(imageUrl);
                                                dialog.show(getFragmentManager(), "message_image_dialog");
                                            }
                                        });
                                mRecyclerView.setAdapter(mAdapter);

                                if (mMessagesList != null && mMessagesList.size() != 0) {
                                    saveLastMessageId(mMessagesList.get(0).getObjectId());
                                }
                            }

                            @Override
                            public void onResultsReady() {

                            }
                        });
            } else {
                mProgressBar.setVisibility(View.GONE);
                mAdapter = new MessagesAdapter(getContext(),
                        mMessagesList == null ? new ArrayList<ChatMessage>() : mMessagesList,
                        new MessagesAdapter.MessageAdapterListener() {
                            @Override
                            public void onImageClick(String imageUrl) {
                                MessageImageDialogFragment dialog = MessageImageDialogFragment.newInstance(imageUrl);
                                dialog.show(getFragmentManager(), "message_image_dialog");
                            }
                        });
                mRecyclerView.setAdapter(mAdapter);

                if (mMessagesList != null && mMessagesList.size() != 0) {
                    saveLastMessageId(mMessagesList.get(0).getObjectId());
                }
            }
        } else {
            mMessagesList = DataManager.getInstance().getResults().get(mEventListPosition).getMessages();
            if (mMessagesList == null || mMessagesList.size() == 0) {
                DataManager.getInstance().updateResults(
                        DataManager.getInstance().getResults().get(mEventListPosition).getId(),
                        new DataManager.DataManagerUpdateEvent() {
                            @Override
                            public void onFixturesReady() {

                            }

                            @Override
                            public void onResultsReady() {
                                mProgressBar.setVisibility(View.GONE);
                                mMessagesList = DataManager.getInstance().getResults().get(mEventListPosition).getMessages();
                                mAdapter = new MessagesAdapter(getContext(),
                                        mMessagesList == null ? new ArrayList<ChatMessage>() : mMessagesList,
                                        new MessagesAdapter.MessageAdapterListener() {
                                            @Override
                                            public void onImageClick(String imageUrl) {
                                                MessageImageDialogFragment dialog = MessageImageDialogFragment.newInstance(imageUrl);
                                                dialog.show(getFragmentManager(), "message_image_dialog");
                                            }
                                        });
                                mRecyclerView.setAdapter(mAdapter);

                                if (mMessagesList != null && mMessagesList.size() != 0) {
                                    saveLastMessageId(mMessagesList.get(0).getObjectId());
                                }
                            }
                        });
            } else {
                mProgressBar.setVisibility(View.GONE);
                mAdapter = new MessagesAdapter(getContext(),
                        mMessagesList == null ? new ArrayList<ChatMessage>() : mMessagesList,
                        new MessagesAdapter.MessageAdapterListener() {
                            @Override
                            public void onImageClick(String imageUrl) {
                                MessageImageDialogFragment dialog = MessageImageDialogFragment.newInstance(imageUrl);
                                dialog.show(getFragmentManager(), "message_image_dialog");
                            }
                        });
                mRecyclerView.setAdapter(mAdapter);

                if (mMessagesList != null && mMessagesList.size() != 0) {
                    saveLastMessageId(mMessagesList.get(0).getObjectId());
                }
            }
        }

//        mAdapter = new MessagesAdapter(getContext(),
//                mMessagesList == null ? new ArrayList<ChatMessage>() : mMessagesList,
//                new MessagesAdapter.MessageAdapterListener() {
//                    @Override
//                    public void onImageClick(String imageUrl) {
//                        MessageImageDialogFragment dialog = MessageImageDialogFragment.newInstance(imageUrl);
//                        dialog.show(getFragmentManager(), "message_image_dialog");
//                    }
//                });
//        mRecyclerView.setAdapter(mAdapter);

        if (mRecyclerView != null)
            mRecyclerView.scrollToPosition(0);
    }

    @OnClick(R.id.messages_send_button)
    public void onSendButtonClick() {
        if (mAdapter == null) {
            return;
        }
        final String messageText = mNewMessageEditText.getText().toString();
        final long currentDate = new Date().getTime();
        final long currentTeamId = UserManager.getInstance().getCurrentTeamId();
        if (messageText.equals("") && mImageUri == null) {
            return;
        }
        if (!DataManager.getInstance().isConnectionAvailable(getContext())) {
            Toast.makeText(getContext(), getString(R.string.no_connection_message), Toast.LENGTH_SHORT).show();
            return;
        }
        mNewMessageEditText.setText("");
        final Uri uriToSave = mImageUri;
        mImageUri = null;
        if (uriToSave != null) {
            mMessageImage.setImageResource(R.drawable.messages_default_image);

            byte[] byteArray = null;
            try {
                byteArray = ImageHelper.getCorrectRotatedBitmap(getContext(), uriToSave);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Failed to upload photo", Toast.LENGTH_SHORT).show();
            }

            DataManager.getInstance().sendMessage(
                    getArguments().getString(EVENT_TYPE_KEY),
                    getArguments().getInt(EVENT_LIST_POSITION_KEY),
                    messageText, byteArray, currentDate, currentTeamId, null
            );

//            new Thread() {
//                DataManager.DataManagerUpdateEvent listener;
//
//                Thread addListener(DataManager.DataManagerUpdateEvent listener) {
//                    this.listener = listener;
//                    return this;
//                }
//
//                @Override
//                public void run() {
//                    try {
//                        int imageSize = (int) getResources().getDimension(R.dimen.size_of_new_image);
//                        Bitmap pickedImage = null;
//
//                        pickedImage = Glide
//                                .with(getContext())
//                                .load(uriToSave)
//                                .asBitmap()
//                                .override(imageSize, imageSize)
//                                .atMost()
//                                .dontTransform()
//                                .into(imageSize, imageSize)
//                                .get();
//
//                       /* try {
//                            pickedImage = Glide
//                                    .with(getContext())
//                                    .load(uriToSave)
//                                    .asBitmap()
//                                    .override(imageSize, imageSize)
//                                    .fitCenter()
//                                    .into(imageSize, imageSize)
//                                    .get();
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                            Toast.makeText(getContext(), "Error on photo upload", Toast.LENGTH_SHORT).show();
//                        } catch (ExecutionException e) {
//                            e.printStackTrace();
//                            Toast.makeText(getContext(), "Error on photo upload", Toast.LENGTH_SHORT).show();
//                        }*/
//
//
//                        /*Bitmap pickedImage = Picasso.with(getContext())
//                                .load(uriToSave)
//                                .resize(imageSize, imageSize)
//                                .centerInside()
//                                .onlyScaleDown()
//                                //.fit()
//                                //.resize(mMessageImage.getWidth(), mMessageImage.getHeight())
//                                .get();*/
//                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                        pickedImage.compress(Bitmap.CompressFormat.JPEG, 65, stream);
//                        byte[] byteArray = stream.toByteArray();
//
//                        //pickedImage.recycle();
//
//                        DataManager.getInstance().sendMessage(
//                                getArguments().getString(EVENT_TYPE_KEY),
//                                getArguments().getInt(EVENT_LIST_POSITION_KEY),
//                                messageText, byteArray, currentDate, currentTeamId, null
//                        );
//                    /*
//                    ByteArrayInputStream bytes = new ByteArrayInputStream(byteArray);
//                    BitmapDrawable bmd = new BitmapDrawable(bytes);
//                    Bitmap back = bmd.getBitmap();
//
//                    pickedImage.recycle();*/
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        Log.d("ERROR", e.getMessage());
//                    }/*catch (IOException e) {
//                        e.printStackTrace();
//                        Log.d("ERROR", e.getMessage());
//                    }*/
//                }
//            }.start();
//            }.addListener(this).start();
        } else {
            DataManager.getInstance().sendMessage(
                    getArguments().getString(EVENT_TYPE_KEY),
                    getArguments().getInt(EVENT_LIST_POSITION_KEY),
//                    messageText, null, currentDate, currentTeamId, this
                    messageText, null, currentDate, currentTeamId, null
            );
        }
        //Display message immediately


        mMessagesList.add(0, new ChatMessage(
                messageText,
                uriToSave == null ? null : uriToSave.toString(),
                currentDate
        ));
        mAdapter.notifyItemInserted(0);
        mRecyclerView.scrollToPosition(0);

        String teamName = "";
        for (Team team :
                UserManager.getInstance().getCurrentUser().getTeams()) {
            if (team.getTeamId() == currentTeamId) {
                teamName = team.getClub();
            }
        }
        ParseInstallation installation = ParseInstallation.getCurrentInstallation();

        String instId = installation.getObjectId();

        Map<String, String> params = new HashMap<>();
        params.put("channelName", "ch_" + String.valueOf(UserManager.getInstance().getCurrentTeamId()));
        params.put("installationId", instId);
        params.put("message", messageText.equals("") ? "[New image]" : messageText);
        params.put("title", teamName);
        params.put("eventType", mEventType);
        params.put("senderId", UserManager.getInstance().getCurrentUser().getObjectId());

        String objectId;
        if (mEventType.equals(EVENT_TYPE_FIXTURES)) {
            objectId = DataManager.getInstance().getFixtures().get(mEventListPosition).getId();
        } else {
            objectId = DataManager.getInstance().getResults().get(mEventListPosition).getId();
        }
        params.put("position", objectId);
//        params.put("position", String.valueOf(mEventListPosition));
        params.put("teamId", String.valueOf(PreferenceManager.getDefaultSharedPreferences(getContext()).getLong(TEAM_ID, -1)));
        ParseCloud.callFunctionInBackground("push", params, new FunctionCallback<Object>() {
            @Override
            public void done(Object object, ParseException e) {

            }
        });
    }

    @OnClick(R.id.messages_new_image)
    public void selectImage() {
        getPhoto(false);
//        int permissionCheck = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
//        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
//            Nammu.askForPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE, new PermissionCallback() {
//                @Override
//                public void permissionGranted() {
//                    EasyImage.openGallery(MessagesFragment.this, EasyImageConfig.REQ_SOURCE_CHOOSER);
//                }
//
//                @Override
//                public void permissionRefused() {
//                }
//            });
//        } else {
//            EasyImage.openGallery(MessagesFragment.this, EasyImageConfig.REQ_SOURCE_CHOOSER);
//        }

//        Intent intent = new Intent();
//
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(Intent.createChooser(intent, getString(R.string.pick_image_dialog_text)),
//                PICK_IMAGE_REQUEST);
    }

    @Override
    protected void openImage(Uri data) {
        mImageUri = data;
        Glide
                .with(getContext())
                .load(mImageUri)
                .centerCrop()
                .into(mMessageImage);
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        Nammu.onRequestPermissionsResult(requestCode, permissions, grantResults);
//    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        EasyImage.handleActivityResult(requestCode, resultCode, data, getActivity(), new DefaultCallback() {
//            @Override
//            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {
//                mImageUri = imageFile.getPath();
//
//                Glide
//                        .with(getContext())
//                        .load(mImageUri)
//                        .centerCrop()
//                        .into(mMessageImage);
//            }
//
//            @Override
//            public void onCanceled(EasyImage.ImageSource source, int type) {
//                if (source == EasyImage.ImageSource.CAMERA) {
//                    File photoFile = EasyImage.lastlyTakenButCanceledPhoto(getContext());
//                    if (photoFile != null) photoFile.delete();
//                }
//            }
//        });
//
////        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
////            mImageUri = data.getData();
////            Glide
////                    .with(getContext())
////                    .load(mImageUri)
////                    .centerCrop()
////                    .into(mMessageImage);
////            /*Picasso.with(getContext())
////                    .load(mImageUri)
////                    .centerCrop()
////                    .resizeDimen(R.dimen.send_message_image_size, R.dimen.send_message_image_size)
////                    .into(mMessageImage);*/
////        }
//    }

    long lastMessageDate = -1;

    public void updateMessages(final ChatMessage newMessage) {
        FragmentActivity activity = getActivity();
        if (activity == null)
            return;

        if (lastMessageDate == -1 || newMessage.getDate() > lastMessageDate) {
            lastMessageDate = newMessage.getDate();
            saveLastMessageId(newMessage.getObjectId());
        }

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                synchronized (mMessagesList) {
                    //delete element with indexes more ore equal 100
                    if (mMessagesList.size() >= 100) {
                        int oldSize = mMessagesList.size();
                        while (mMessagesList.size() >= 100) {
                            mMessagesList.remove(99);
                        }
                        mAdapter.notifyItemRangeRemoved(mMessagesList.size(), oldSize - mMessagesList.size());
                    }

                    for (int i = 0; i < mMessagesList.size(); i++) {
                        ChatMessage message = mMessagesList.get(i);
                        if (message.getLocalDate() == newMessage.getLocalDate() &&
                                message.getAuthorId().equals(newMessage.getAuthorId())) {
                            message.setLocal(false);
                            message.setDate(newMessage.getDate());
//                            message.setImageUrl(newMessage.getImageUrl());
                            mAdapter.notifyItemChanged(i);
                            return;
                        }
                    }

                    for (int i = 0; i < mMessagesList.size(); i++) {
                        ChatMessage message = mMessagesList.get(i);
                        if (message.getDate() < newMessage.getDate()) {
                            mMessagesList.add(i, newMessage);
                            mAdapter.notifyItemInserted(i);
                            //mAdapter.notifyDataSetChanged();
                            mRecyclerView.scrollToPosition(0);
                            return;
                        }
                    }
                    mMessagesList.add(newMessage);
                    //mAdapter.notifyDataSetChanged();
                    mAdapter.notifyItemInserted(0);
                    mRecyclerView.scrollToPosition(0);
                }
            }
        });

        /*synchronized (mMessagesList) {
            //delete element with indexes more ore equal 100
            if (mMessagesList.size() >= 100) {
                int oldSize = mMessagesList.size();
                while (mMessagesList.size() >= 100) {
                    mMessagesList.remove(99);
                }
                mAdapter.notifyItemRangeRemoved(mMessagesList.size(), oldSize - mMessagesList.size());
            }

            for (int i = 0; i < mMessagesList.size(); i++) {
                ChatMessage message = mMessagesList.get(i);
                if (message.isLocal() && message.getDate() == newMessage.getDate() &&
                        message.getAuthorId().equals(newMessage.getAuthorId())) {
                    message.setLocal(false);
                    message.setImageUrl(newMessage.getImageUrl());
                    mAdapter.notifyItemChanged(i);
                    return;
                }
            }

            for (int i = 0; i < mMessagesList.size(); i++) {
                ChatMessage message = mMessagesList.get(i);
                if (message.getDate() < newMessage.getDate()) {
                    mMessagesList.add(i, newMessage);
                    mAdapter.notifyItemInserted(i);
                    mAdapter.notifyDataSetChanged();
                    mRecyclerView.scrollToPosition(0);
                    return;
                }
            }
            mMessagesList.add(newMessage);
            mAdapter.notifyDataSetChanged();
            mAdapter.notifyItemInserted(0);
            mRecyclerView.scrollToPosition(0);
        }*/

        /*List<ChatMessage> messagesTempList;
        if (message == null) {
            if (mEventType.equals(EVENT_TYPE_FIXTURES)) {
                messagesTempList = DataManager.getInstance().getFixtures().get(mEventListPosition).getMessages();
            } else {
                messagesTempList = DataManager.getInstance().getResults().get(mEventListPosition).getMessages();
            }
            if (messagesTempList == null || messagesTempList.size() == 0)
                return;
        } else {
            messagesTempList = new ArrayList<>();
            messagesTempList.add(message);
        }

        ChatMessage lastNewElement = messagesTempList.get(messagesTempList.size() - 1);
        for (int i = mMessagesList.size() - 1; i >= 0; i--) {
            if (lastNewElement.getDate() == mMessagesList.get(i).getDate()) {
                int oldSize = mMessagesList.size();
                while (mMessagesList.size() > i + 1) {
                    mMessagesList.remove(i + 1);
                }
                if (i + 1 != oldSize) {
                    mAdapter.notifyItemRangeRemoved(i + 1, oldSize - (i + 1));
                }
                break;
            }
        }

        int messagesListPosition = -1;
        int messagesTempListPosition = -1;
        for (int i = 0; i < mMessagesList.size(); i++) {
            if (mMessagesList.get(mMessagesList.size() - i - 1).isLocal() ||
                    mMessagesList.get(mMessagesList.size() - i - 1).getDate()
                    != messagesTempList.get(messagesTempList.size() - i - 1).getDate()) {
                messagesListPosition = mMessagesList.size() - i - 1;
                messagesTempListPosition = messagesTempList.size() - i - 1;
                break;
            }
        }
        if (messagesTempListPosition != -1) {
            List<ChatMessage> localMessages = new ArrayList<>();

            for (int i = 0; i <= messagesListPosition; i++) {
                if (mMessagesList.get(i).isLocal()) {
                    ChatMessage currentMessage = mMessagesList.get(i);
                    boolean isUpdated = false;
                    for (int j = 0; j <= messagesTempListPosition && j < messagesTempList.size(); j++) {
                        if (messagesTempList.get(j).getDate() == currentMessage.getDate()) {
                            isUpdated = true;
                            break;
                        }
                    }
                    if (!isUpdated) {
                        localMessages.add(mMessagesList.get(i));
                    }
                }
            }

            if (messagesListPosition != mMessagesList.size() - 1) {
                for (int j = 0; j < messagesListPosition + 1; j++) {
                    mMessagesList.remove(0);
                }
                mAdapter.notifyItemRangeRemoved(0, messagesListPosition + 1);
            } else {
                int oldSize = mMessagesList.size();
                for (int j = 0; j < mMessagesList.size(); j++) {
                    mMessagesList.remove(0);
                }
                mAdapter.notifyItemRangeRemoved(0, oldSize);
            }
            messagesTempList = messagesTempList.subList(0, messagesTempListPosition + 1);
            messagesTempList.addAll(0, localMessages);
        } else {
            int insertedItemCount = messagesTempList.size() - mMessagesList.size();
            if (insertedItemCount > 0 && insertedItemCount <= messagesTempList.size()) {
                messagesTempList = messagesTempList.subList(0, insertedItemCount);
            } else {
                messagesTempList = new ArrayList<>();
            }
        }
        mMessagesList.addAll(0, messagesTempList);

        mAdapter.notifyItemRangeInserted(0, messagesTempList.size());
        mRecyclerView.scrollToPosition(0);*/
    }

    private void saveLastMessageId(String messageId) {
        String eventId = mEventType.equals(EVENT_TYPE_FIXTURES)
                ? DataManager.getInstance().getFixtures().get(mEventListPosition).getId()
                : DataManager.getInstance().getResults().get(mEventListPosition).getId();

        PrefsManager pm = new PrefsManager();
        pm.setLastMessageId(eventId, messageId);
    }

    /*@OnClick({R.id.footer_facebook_icon,
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
    }*/

}
