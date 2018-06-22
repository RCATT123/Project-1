package com.specifix.pureleagues.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.specifix.pureleagues.R;
import com.specifix.pureleagues.model.ChatMessage;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GalleryPreviewFragment extends Fragment{
    public static final String EVENT_MESSAGE_AUTHOR = "author";
    public static final String EVENT_MESSAGE_AUTHOR_OBJECT_ID = "author_object_id";
    public static final String EVENT_MESSAGE_PARENT = "parentEvent";
    public static final String EVENT_MESSAGE_CONTENT = "message";
    public static final String EVENT_MESSAGE_DATE = "date";
    public static final String EVENT_MESSAGE_LOCAL_DATE = "localDate";
    public static final String EVENT_MESSAGE_IS_READ = "isRead";
    public static final String EVENT_MESSAGE_IMAGE_FILE = "image_file";
    public static final String EVENT_MESSAGE_TEAM_ID = "team_id";
    @BindView(R.id.gallery_preview_image)
    ImageView mImageView;
    @BindView(R.id.gallery_preview_message)
    TextView mMessageContent;

    private ChatMessage mMessage;

    public static GalleryPreviewFragment newInstance(ChatMessage message) {
        GalleryPreviewFragment fragment = new GalleryPreviewFragment();
        Bundle args = new Bundle();

        args.putString(EVENT_MESSAGE_AUTHOR, message.getAuthor());
        args.putString(EVENT_MESSAGE_AUTHOR_OBJECT_ID, message.getAuthorId());
        args.putLong(EVENT_MESSAGE_PARENT, message.getParentId());
        args.putString(EVENT_MESSAGE_CONTENT, message.getMessage());
        args.putLong(EVENT_MESSAGE_DATE, message.getDate());
        args.putLong(EVENT_MESSAGE_LOCAL_DATE, message.getLocalDate());
        args.putBoolean(EVENT_MESSAGE_IS_READ, message.isRead());
        args.putString(EVENT_MESSAGE_IMAGE_FILE, message.getImageUrl());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        mMessage = new ChatMessage(
                null,
                bundle.getString(EVENT_MESSAGE_AUTHOR),
                bundle.getString(EVENT_MESSAGE_AUTHOR_OBJECT_ID),
                bundle.getLong(EVENT_MESSAGE_PARENT),
                bundle.getString(EVENT_MESSAGE_CONTENT),
                bundle.getString(EVENT_MESSAGE_IMAGE_FILE),
                bundle.getLong(EVENT_MESSAGE_DATE),
                bundle.getLong(EVENT_MESSAGE_LOCAL_DATE),
                bundle.getLong(EVENT_MESSAGE_TEAM_ID),
                bundle.getBoolean(EVENT_MESSAGE_IS_READ)
                );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gallery_preview_fragment, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Typeface gothamBold = Typeface.createFromAsset(getContext().getAssets(), "GothamBold.otf");
        Typeface gothamBook = Typeface.createFromAsset(getContext().getAssets(), "GothamBook.otf");

        mMessageContent.setTypeface(gothamBook);

        mMessageContent.setText(mMessage.getMessage());
        if (mMessage.getMessage() == null || mMessage.getMessage().equals("")) {
            mMessageContent.setVisibility(View.GONE);
        } else {
            mMessageContent.setVisibility(View.VISIBLE);
        }

        Picasso.with(getContext()).load(mMessage.getImageUrl())
                .fit()
                .centerInside()
                .into(mImageView);
    }

}
