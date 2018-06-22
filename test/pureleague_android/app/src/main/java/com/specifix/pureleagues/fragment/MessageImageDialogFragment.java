package com.specifix.pureleagues.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.specifix.pureleagues.R;
import com.specifix.pureleagues.model.Converter;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MessageImageDialogFragment extends DialogFragment{
    private static final String IMAGE_URL = "image_url";
    @BindView (R.id.messages_dialog_image)
    ImageView mImage;

    public static MessageImageDialogFragment newInstance(String url) {
        MessageImageDialogFragment dialog = new MessageImageDialogFragment();
        Bundle extras = new Bundle();
        extras.putString(IMAGE_URL, url);
        dialog.setArguments(extras);
        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String url = getArguments().getString(IMAGE_URL);
        View view = LinearLayout.inflate(getContext(), R.layout.fragment_message_image_preview, null);
        ButterKnife.bind(this, view);

//        Picasso.with(getContext())
//                .load(url)
//                .centerInside()
//                .fit()
//                .into(mImage);
        Glide
                .with(getContext())
                .load(url)
                .fitCenter()
                .into(mImage);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final Dialog dialog = builder.setView(view).create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        return dialog;
    }

    @OnClick(R.id.messages_dialog_close)
    public void onCloseDialogClick() {
        dismiss();
    }
}
