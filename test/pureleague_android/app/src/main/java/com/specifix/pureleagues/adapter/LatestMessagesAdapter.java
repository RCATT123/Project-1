package com.specifix.pureleagues.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.specifix.pureleagues.R;
import com.specifix.pureleagues.model.ChatMessage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class LatestMessagesAdapter extends BaseAdapter {
    private Context mContext;
    private List<ChatMessage> mMessages;
    private Typeface gothamBold;
    private Typeface gothamBook;

    public LatestMessagesAdapter(Context context) {
        mContext = context;
        mMessages = new ArrayList<>();

        gothamBold = Typeface.createFromAsset(mContext.getAssets(), "GothamBold.otf");
        gothamBook = Typeface.createFromAsset(mContext.getAssets(), "GothamBook.otf");
    }

    @Override
    public int getCount() {
        return mMessages.size();
    }

    @Override
    public ChatMessage getItem(int position) {
        return mMessages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_latets_messages, parent, false);

            holder = new ViewHolder();

            holder.author = (TextView) convertView.findViewById(R.id.list_item_latest_messages_name);
            holder.message = (TextView) convertView.findViewById(R.id.list_item_latest_messages_message);
            holder.date = (TextView) convertView.findViewById(R.id.list_item_latest_messages_date);
            //holder.isRead = (ImageView) convertView.findViewById(R.id.list_item_latest_messages_status);

            holder.author.setTypeface(gothamBold);
            holder.message.setTypeface(gothamBook);
            holder.date.setTypeface(gothamBook);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.author.setText(mMessages.get(position).getAuthor());
        holder.message.setText(mMessages.get(position).getMessage());
        holder.date.setText(getFormattedDate(mMessages.get(position).getDate()));
        holder.isRead.setImageResource(
                mMessages.get(position).isRead() ? R.drawable.readded_message_icon : R.drawable.unread_message_icon);
        return convertView;
    }

    private String getFormattedDate(long date) {
        String formattedDate;
        DateFormat defaultDateFormat = new SimpleDateFormat("dd.MM.yyyy - HH:mm", Locale.getDefault());
        DateFormat timeDateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

        Calendar today = new GregorianCalendar();
        Calendar messageDate = new GregorianCalendar();

        defaultDateFormat.setTimeZone(today.getTimeZone());
        timeDateFormat.setTimeZone(today.getTimeZone());

        messageDate.setTime(new Date(date));
        if (today.get(Calendar.DAY_OF_YEAR) - messageDate.get(Calendar.DAY_OF_YEAR) == 1) {
            formattedDate = mContext.getString(R.string.yesterday_label)
                    + " - "
                    + timeDateFormat.format(messageDate.getTime());
        } else if (today.get(Calendar.DAY_OF_YEAR) - messageDate.get(Calendar.DAY_OF_YEAR) == 0){
            formattedDate = mContext.getString(R.string.today_label)
                    + " - "
                    + timeDateFormat.format(messageDate.getTime());
        } else {
            formattedDate = defaultDateFormat.format(messageDate.getTime());
        }
        return formattedDate;
    }

    public void setMessages(List<ChatMessage> messages) {
        mMessages = messages;
        notifyDataSetChanged();
    }

    private static class ViewHolder {
        TextView author;
        TextView message;
        TextView date;
        ImageView isRead;
    }
}
