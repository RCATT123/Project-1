package com.specifix.pureleagues.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.specifix.pureleagues.R;
import com.specifix.pureleagues.api.UserManager;
import com.specifix.pureleagues.model.Division;
import com.specifix.pureleagues.model.FootballAssociation;
import com.specifix.pureleagues.model.Team;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.specifix.pureleagues.activity.RegisterPickerActivity.DATA_ASSOCIATION_TYPE;
import static com.specifix.pureleagues.activity.RegisterPickerActivity.DATA_CLUB_TYPE;
import static com.specifix.pureleagues.activity.RegisterPickerActivity.DATA_DIVISION_TYPE;
import static com.specifix.pureleagues.activity.RegisterPickerActivity.DATA_SIMPLE_TYPE;
import static com.specifix.pureleagues.activity.RegisterPickerActivity.DATA_TEAM_COLOURS_TYPE;

public class RegisterPickerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private RegisterPickerListener mListener;
    private int mDataType;
    private List<?> mData;
    private List<FootballAssociation> mFilteredData;
    private Typeface mGothamBold;
    private int[] mTeamColors;

    public RegisterPickerAdapter(List<?> list, int dataType, RegisterPickerListener listener) {
        mDataType = dataType;
        mData = list;
        if (dataType == DATA_ASSOCIATION_TYPE) {
            mFilteredData = new ArrayList<>((Collection<? extends FootballAssociation>) list);
        }
        if (dataType == DATA_TEAM_COLOURS_TYPE) {
            //mTeamColors = UserManager.getInstance().getTeamColors();
        }
        mListener = listener;
        mGothamBold = Typeface.createFromAsset(((Context)listener).getAssets(), "GothamBold.otf");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;

        if (viewType == DATA_ASSOCIATION_TYPE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_association, parent, false);
            final RecyclerView.ViewHolder holder = new AssociationViewHolder(view);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onAssociationSelected(mFilteredData.get(holder.getAdapterPosition()));
                }
            });
            viewHolder = holder;
        }
        if (viewType == DATA_TEAM_COLOURS_TYPE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_team_color, parent, false);
            final RecyclerView.ViewHolder holder = new TeamColoursViewHolder(view);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemSelected((String)mData.get(holder.getAdapterPosition()));
                }
            });
            viewHolder = holder;
        }
        if (viewType == DATA_DIVISION_TYPE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_simple, parent, false);
            final RecyclerView.ViewHolder holder = new SimpleViewHolder(view);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onDivisionSelected((Division) mData.get(holder.getAdapterPosition()));
                }
            });
            viewHolder = holder;
        }
        if (viewType == DATA_CLUB_TYPE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_simple, parent, false);
            final RecyclerView.ViewHolder holder = new SimpleViewHolder(view);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onTeamSelected(((Team) mData.get(holder.getAdapterPosition())));
                }
            });
            viewHolder = holder;
        }
        if (viewType == DATA_SIMPLE_TYPE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_simple, parent, false);
            final RecyclerView.ViewHolder holder = new SimpleViewHolder(view);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemSelected(((String) mData.get(holder.getAdapterPosition())));
                }
            });
            viewHolder = holder;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case DATA_ASSOCIATION_TYPE: {
                AssociationViewHolder viewHolder = (AssociationViewHolder) holder;
                viewHolder.mLogo.setImageResource(R.mipmap.ic_launcher);
                viewHolder.mName.setText((mFilteredData.get(position)).getName());
                viewHolder.mName.setTypeface(mGothamBold);
                break;
            }
            case DATA_DIVISION_TYPE: {
                SimpleViewHolder viewHolder = (SimpleViewHolder) holder;
                viewHolder.mName.setText(((Division)mData.get(position)).getName());
                viewHolder.mName.setTypeface(mGothamBold);
                break;
            }
            case DATA_CLUB_TYPE: {
                SimpleViewHolder viewHolder = (SimpleViewHolder) holder;
                viewHolder.mName.setText(((Team)mData.get(position)).getClub());
                viewHolder.mName.setTypeface(mGothamBold);
                break;
            }
            case DATA_TEAM_COLOURS_TYPE: {
                TeamColoursViewHolder viewHolder = (TeamColoursViewHolder) holder;
                viewHolder.mImage.setImageResource(mTeamColors[position]);
                break;
            }
            case DATA_SIMPLE_TYPE: {
                SimpleViewHolder viewHolder = (SimpleViewHolder) holder;
                viewHolder.mName.setText(((String)mData.get(position)));
                viewHolder.mName.setTypeface(mGothamBold);
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mDataType == DATA_ASSOCIATION_TYPE) {
            return mFilteredData.size();
        }
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mDataType;
    }

    public void filterList(String pattern) {
        mFilteredData = new ArrayList<>();
        for (int i = 0; i < mData.size(); i++) {
            FootballAssociation fa = (FootballAssociation) mData.get(i);
            if (fa.getName().toLowerCase().contains(pattern)) {
                mFilteredData.add(fa);
            }
        }
        notifyDataSetChanged();
    }

    public void setData(List<?> list) {
        mData = list;
        mFilteredData = new ArrayList<>((Collection<? extends FootballAssociation>) list);
        notifyDataSetChanged();
    }

    static class SimpleViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.simple_list_item_textview)
        TextView mName;

        SimpleViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    static class AssociationViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.association_image)
        ImageView mLogo;
        @BindView(R.id.association_name)
        TextView mName;

        AssociationViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    static class TeamColoursViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.team_color_image)
        ImageView mImage;

        TeamColoursViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    public interface RegisterPickerListener {

        void onAssociationSelected(FootballAssociation association);

        void onDivisionSelected(Division division);

        void onTeamSelected(Team team);

        void onItemSelected(String item);
    }
}
