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
import com.specifix.pureleagues.model.Scorer;
import com.specifix.pureleagues.model.Team;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StatisticsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static final int HEADER_ITEM_TYPE = 0;
    private static final int REGULAR_ITEM_TYPE = 1;
    private Context mContext;
    private List<Scorer> mScorerList;
    private Typeface gothamBold;
    private Typeface gothamBook;

    public StatisticsAdapter(List<Scorer> list, Context context) {
        mScorerList = list;
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

        return REGULAR_ITEM_TYPE;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder holder;

        switch (viewType) {
            case HEADER_ITEM_TYPE: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_statistics_header, parent, false);
                holder = new StatisticsAdapter.DataViewHolder(view);
                break;
            }

            default: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_statistics, parent, false);
                holder = new StatisticsAdapter.DataViewHolder(view);
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
            dataHolder.mPlayer.setText(mScorerList.get(position).getPlayerName());
            String goals = mScorerList.get(position).getGoals();
            dataHolder.mGoals.setText(goals != null ? goals : "-");

            boolean isUserTeam = false;
            for (Team userTeam : UserManager.getInstance().getCurrentUser().getTeams()) {
                if (mScorerList.get(position).getTeamId() == userTeam.getTeamId()) {
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
        holder.mPlayer.setTypeface(typeface);
        holder.mGoals.setTypeface(typeface);
    }

    @Override
    public int getItemCount() {
        if (mScorerList == null) {
            return 0;
        }

        if (mScorerList.size() == 0) {
            return 1;
        }

        return mScorerList.size();
    }

    private void addHeaderListItem() {
        if (mScorerList.size() == 0 || mScorerList.get(0).getTeamId() != -1) {
            Scorer header = new Scorer("", -1, -1, -1, "");
            mScorerList.add(0, header);
        }
    }

    static class DataViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.list_item_statistics_number)
        TextView mNumber;
        @BindView(R.id.list_item_statistics_player)
        TextView mPlayer;
        @BindView(R.id.list_item_statistics_goals)
        TextView mGoals;

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
