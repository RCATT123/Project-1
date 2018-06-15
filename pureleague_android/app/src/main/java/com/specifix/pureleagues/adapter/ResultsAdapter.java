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
import android.widget.TextView;

import com.specifix.pureleagues.R;
import com.specifix.pureleagues.api.UserManager;
import com.specifix.pureleagues.model.Result;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ResultsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int FOOTER_VIEW = 1;
    private Context mContext;
    private ResultAdapterListener mResultListener;
    private List<Result> mResultsList;
    private Typeface gothamBold;
    private Typeface gothamBook;

    public ResultsAdapter(Context context, List<Result> list, ResultAdapterListener listener) {
        mContext = context;
        mResultsList = list;
        mResultListener = listener;

        gothamBold = Typeface.createFromAsset(mContext.getAssets(), "GothamBold.otf");
        gothamBook = Typeface.createFromAsset(mContext.getAssets(), "GothamBook.otf");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

//        if (viewType == FOOTER_VIEW) {
//            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_layout, parent, false);
//            FixturesAdapter.FooterViewHolder vh = new FixturesAdapter.FooterViewHolder(view);
//
//            return vh;
//        }

        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_results, parent, false);

        final DataViewHolder viewHolder = new DataViewHolder(view);

        viewHolder.mClubScoreOne.setTypeface(gothamBold);
        viewHolder.mClubScoreTwo.setTypeface(gothamBold);

        viewHolder.mMessages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mResultListener.onMessagesClick(mResultsList.get(viewHolder.getAdapterPosition()));
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DataViewHolder) {
            DataViewHolder normalHolder = (DataViewHolder) holder;
            normalHolder.mFirstClub.setText(String.valueOf(mResultsList.get(position).getClubOneName()));
            normalHolder.mSecondClub.setText(String.valueOf(mResultsList.get(position).getClubTwoName()));
            //holder.mClubScoreOne.setText(mResultsList.get(position).getScore());
            normalHolder.mClubScoreOne.setText(mResultsList.get(position).getScoreOne());
            normalHolder.mClubScoreTwo.setText(mResultsList.get(position).getScoreTwo());
            normalHolder.mDate.setText(mResultsList.get(position).getFormatDate());

            long teamId = UserManager.getInstance().getCurrentTeamId();
            if (mResultsList.get(position).getClubOneId() == teamId
                    || mResultsList.get(position).getClubTwoId() == teamId) {
                setSelected(normalHolder, true);
                normalHolder.mMessages.setVisibility(View.VISIBLE);
            } else {
                setSelected(normalHolder, false);
                normalHolder.mMessages.setVisibility(View.INVISIBLE);
            }
        }
        /*
        boolean isUserTeam = false;
        for (Team userTeam : UserManager.getInstance().getCurrentUser().getTeams()) {
            if (mResultsList.get(position).getClubOneId() == userTeam.getTeamId()
                    || mResultsList.get(position).getClubTwoId() == userTeam.getTeamId() ) {
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

        holder.mDate.setTypeface(typeface);

        holder.mDate.setTypeface(typeface);
        holder.mFirstClub.setTypeface(typeface);
        holder.mSecondClub.setTypeface(typeface);
    }

    @Override
    public int getItemCount() {
        if (mResultsList == null) {
            return 0;
        }
        return mResultsList.size();
//        if (mResultsList == null) {
//            return 0;
//        }
//
//        if (mResultsList.size() == 0) {
//            return 1;
//        }
//        if (mResultsList.size() < 7) {
//            return mResultsList.size();
//        }
//        return mResultsList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (mResultsList.size() < 7) {
            return super.getItemViewType(position);
        }
        if (position == mResultsList.size()) {
            return FOOTER_VIEW;
        }

        return super.getItemViewType(position);
    }

    static class DataViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.results_club_one_score)
        TextView mClubScoreOne;
        @BindView(R.id.results_list_item_team_one_title)
        TextView mFirstClub;
        @BindView(R.id.results_club_two_score)
        TextView mClubScoreTwo;
        @BindView(R.id.results_list_item_team_two_title)
        TextView mSecondClub;
        @BindView(R.id.results_list_item_date)
        TextView mDate;
        @BindView(R.id.results_list_item_messages)
        ImageView mMessages;

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

    public interface ResultAdapterListener {
        void onMessagesClick(Result result);
    }
}
