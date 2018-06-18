package com.specifix.pureleagues.adapter;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.specifix.pureleagues.R;
import com.specifix.pureleagues.model.ChatMessage;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GalleryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int IMAGE_TYPE = 0;
    public static final int FOOTER_TYPE = 1;
    private GalleryAdapterListener mListener;
    private Context mContext;
    private List<ChatMessage> mMessageList;
    private Typeface gothamBold;
    private Typeface gothamBook;

    public GalleryAdapter(Context context, List<ChatMessage> list, GalleryAdapterListener listener) {
        mContext = context;
        mMessageList = list;
        mListener = listener;
        gothamBold = Typeface.createFromAsset(mContext.getAssets(), "GothamBold.otf");
        gothamBook = Typeface.createFromAsset(mContext.getAssets(), "GothamBook.otf");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        if (viewType == FOOTER_TYPE) {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_layout, parent, false);
//            FooterViewHolder vh = new FooterViewHolder(view);
//
//            return vh;
//        }

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_gallery, parent, false);
        final DataViewHolder holder = new DataViewHolder(view);

        holder.mMessageImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onMessagesClick(mMessageList.get(holder.getAdapterPosition()), holder.getAdapterPosition());
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DataViewHolder) {
            final DataViewHolder dataViewHolder = (DataViewHolder) holder;
            Picasso.with(mContext)
                    .load(mMessageList.get(position).getImageUrl())
                    //.resize(imageWidth, imageWidth)
                    .centerCrop()
                    .fit()
                    .into(dataViewHolder.mMessageImage, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError() {
                            // Try again online if cache failed
                            Picasso.with(mContext)
                                    .load(mMessageList.get(dataViewHolder.getAdapterPosition()).getImageUrl())
                                    .fit()
                                    .into(dataViewHolder.mMessageImage);
                        }
                    });
        }
    }

    @Override
    public int getItemCount() {
        if (mMessageList == null) {
            return 0;
        }

        return mMessageList.size();
//
//        if (mMessageList.size() == 0) {
//            return 1;
//        }
//        if (mMessageList.size() < 7) {
//            return mMessageList.size();
//        }
//
//        return mMessageList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
//        if (position >= 7 && position == mMessageList.size()) {
//            return FOOTER_TYPE;
//        }

        return IMAGE_TYPE;
    }

    static class DataViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.list_item_gallery_image)
        ImageView mMessageImage;

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

    public interface GalleryAdapterListener {

        void onMessagesClick(ChatMessage message, int position);
    }
}
