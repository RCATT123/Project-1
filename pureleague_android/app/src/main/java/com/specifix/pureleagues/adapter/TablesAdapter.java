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
import android.widget.TextView;

import com.specifix.pureleagues.R;
import com.specifix.pureleagues.api.UserManager;
import com.specifix.pureleagues.model.ClubStats;
import com.specifix.pureleagues.model.Team;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TablesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int HEADER_ITEM_TYPE = 0;
    private static final int REGULAR_ITEM_TYPE = 1;
    private static final int FOOTER_VIEW = 2;
    private Context mContext;
    private List<ClubStats> mStatsList;
    private Typeface gothamBold;
    private Typeface gothamBook;

    public TablesAdapter(List<ClubStats> list, Context context) {
//        int size = list.size();
//        for (int i = 0; list.size() < 10; i++) {
//            list.add(list.get(i));
//        }
        mStatsList = list;
        mContext = context;
        if (mContext != null) {
            gothamBold = Typeface.createFromAsset(mContext.getAssets(), "GothamBold.otf");
            gothamBook = Typeface.createFromAsset(mContext.getAssets(), "GothamBook.otf");
        }
        addHeaderListItem();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEADER_ITEM_TYPE;
        }
        if (position < 15) {
            return REGULAR_ITEM_TYPE;
        }
        if (position == mStatsList.size()) {
            return FOOTER_VIEW;
        }
        return REGULAR_ITEM_TYPE;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder holder;
        switch (viewType) {
            case HEADER_ITEM_TYPE: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_tables_header, parent, false);
                holder = new DataViewHolder(view);
                break;
            }
//            case FOOTER_VIEW: {
//                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_layout, parent, false);
//                holder = new FooterViewHolder(view);
//                break;
//            }
            default: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_tables, parent, false);
                holder = new DataViewHolder(view);
            }
        }


        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DataViewHolder) {
            DataViewHolder dataHolder = (DataViewHolder) holder;
            if (position == 0) {
                setSelected(dataHolder, true);
                return;
            }
            dataHolder.mNumber.setText(String.valueOf(position));
            dataHolder.mClub.setText(mStatsList.get(position).getClub());
            dataHolder.mGames.setText(String.valueOf(mStatsList.get(position).getGames()));
            dataHolder.mWon.setText(String.valueOf(mStatsList.get(position).getWins()));
            dataHolder.mDrawn.setText(String.valueOf(mStatsList.get(position).getDraws()));
            dataHolder.mLost.setText(String.valueOf(mStatsList.get(position).getLost()));
            dataHolder.mGoalsFor.setText(String.valueOf(mStatsList.get(position).getGoalsFor()));
            dataHolder.mGoalsAgainst.setText(String.valueOf(mStatsList.get(position).getGoalsAgainst()));
            dataHolder.mDifference.setText(String.valueOf(mStatsList.get(position).getGoalsDifference()));
            dataHolder.mPoints.setText(String.valueOf(mStatsList.get(position).getPoints()));

            boolean isUserTeam = false;
            for (Team userTeam : UserManager.getInstance().getCurrentUser().getTeams()) {
                if (mStatsList.get(position).getClubId() == userTeam.getTeamId()) {
                    isUserTeam = true;
                    setSelected(dataHolder, true);
                }
            }
            if (!isUserTeam) {
                setSelected(dataHolder, false);
            }
        }
    }

    private void setSelected(DataViewHolder holder, boolean isSelected) {
        Typeface typeface = isSelected ? gothamBold : gothamBook;

        holder.mNumber.setTypeface(gothamBold);
        holder.mClub.setTypeface(typeface);
        holder.mGames.setTypeface(typeface);
        holder.mWon.setTypeface(typeface);
        holder.mDrawn.setTypeface(typeface);
        holder.mLost.setTypeface(typeface);
        holder.mGoalsFor.setTypeface(typeface);
        holder.mGoalsAgainst.setTypeface(typeface);
        holder.mDifference.setTypeface(typeface);
        holder.mPoints.setTypeface(typeface);

        /*holder.mNumber.setTypeface(gothamBold);
        holder.mClub.setTypeface(gothamBook);
        holder.mGames.setTypeface(gothamBold);
        holder.mWon.setTypeface(gothamBold);
        holder.mDrawn.setTypeface(gothamBold);
        holder.mLost.setTypeface(gothamBold);
        holder.mGoalsFor.setTypeface(gothamBold);
        holder.mGoalsAgainst.setTypeface(gothamBold);
        holder.mDifference.setTypeface(gothamBold);
        holder.mPoints.setTypeface(gothamBold);*/
    }

    @Override
    public int getItemCount() {
        if (mStatsList == null) {
            return 0;
        }
        return mStatsList.size();
//        if (mStatsList == null) {
//            return 0;
//        }
//
//        if (mStatsList.size() == 0) {
//            return 1;
//        }
//        if (mStatsList.size() < 15) {
//            return mStatsList.size();
//        }
//
//        return mStatsList.size() + 1;
    }

    private void addHeaderListItem() {
        if (mStatsList.size() == 0 || mStatsList.get(0).getClubId() != -1) {
            ClubStats header = new ClubStats(-1, "", 0, 0, 0, 0, 0, 0, 0, -1);
            mStatsList.add(0, header);
        }
    }

    static class DataViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.list_item_tables_number)
        TextView mNumber;
        @BindView(R.id.list_item_tables_club_name)
        TextView mClub;
        @BindView(R.id.list_item_tables_total_games)
        TextView mGames;
        @BindView(R.id.list_item_tables_won)
        TextView mWon;
        @BindView(R.id.list_item_tables_drawn)
        TextView mDrawn;
        @BindView(R.id.list_item_tables_lost)
        TextView mLost;
        @BindView(R.id.list_item_tables_goals_for)
        TextView mGoalsFor;
        @BindView(R.id.list_item_tables_goals_against)
        TextView mGoalsAgainst;
        @BindView(R.id.list_item_tables_goals_difference)
        TextView mDifference;
        @BindView(R.id.list_item_tables_points)
        TextView mPoints;

        DataViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
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
}
