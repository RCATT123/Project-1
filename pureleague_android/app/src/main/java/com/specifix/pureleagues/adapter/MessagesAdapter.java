package com.specifix.pureleagues.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
//import com.parse.FunctionCallback;
//import com.parse.ParseCloud;
//import com.parse.ParseException;
import com.specifix.pureleagues.R;
import com.specifix.pureleagues.api.UserManager;
import com.specifix.pureleagues.model.ChatMessage;
import com.specifix.pureleagues.model.Converter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ViewHolder>{
    private Context mContext;
    private MessageAdapterListener mListener;
    public List<ChatMessage> mList;
    private Typeface gothamBold;
    private Typeface gothamBook;

    public MessagesAdapter(Context context, List<ChatMessage> list, MessageAdapterListener listener) {
        mContext = context;
        mListener = listener;
        mList = list;
        gothamBold = Typeface.createFromAsset(mContext.getAssets(), "GothamBold.otf");
        gothamBook = Typeface.createFromAsset(mContext.getAssets(), "GothamBook.otf");
    }

    @Override
    public MessagesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_message, parent, false);
        ViewHolder holder = new ViewHolder(view);

        holder.mName.setTypeface(gothamBold);
        holder.mMessage.setTypeface(gothamBook);
        holder.mDay.setTypeface(gothamBook);
        holder.mTime.setTypeface(gothamBook);

        return holder;
    }

    @Override
    public void onBindViewHolder(final MessagesAdapter.ViewHolder holder, int position) {
//        holder.mStatus.setImageResource(mList.get(position).isRead() ? R.drawable.readded_message_icon : R.drawable.unread_message_icon);
        holder.mName.setText(mList.get(position).getAuthor());
        holder.mDay.setText(getDayFromMillis(mList.get(position).getDate()));
        holder.mTime.setText(getTimeFromMillis(mList.get(position).getDate()));

        String message = mList.get(position).getMessage();
        if (message == null || message.equals("")) {
            holder.mMessage.setVisibility(View.GONE);
        } else {
            holder.mMessage.setVisibility(View.VISIBLE);
            holder.mMessage.setText(message);
        }

        final String imageUrl = mList.get(position).getImageUrl();
        final String localImageUrl = mList.get(position).getLocalImageUrl();
        if (imageUrl != null) {
            holder.mImage.setVisibility(View.VISIBLE);

            Glide.with(mContext)
                    .load(imageUrl)
//                    .placeholder(holder.mImage.getDrawable())
                    .override(Converter.dpToPx(mContext, 180), Converter.dpToPx(mContext, 180))
                    .fitCenter()
//                    .dontTransform()
                    .into(holder.mImage);

            /*Picasso.with(mContext)
                    .load(imageUrl)
                    .placeholder(holder.mImage.getDrawable())
//                    .resize(Converter.dpToPx(mContext, 180), Converter.dpToPx(mContext, 180))
                    .resizeDimen(R.dimen.message_image_size, R.dimen.message_image_size)
                    .centerInside()
                    //.fit()
                    .into(holder.mImage);*/
        } else if (localImageUrl != null)  {
            holder.mImage.setVisibility(View.VISIBLE);

            Glide.with(mContext)
                    .load(localImageUrl)
//                    .placeholder(holder.mImage.getDrawable())
                    .override(Converter.dpToPx(mContext, 180), Converter.dpToPx(mContext, 180))
                    .fitCenter()
//                    .dontTransform()
                    .into(holder.mImage);

           /* Picasso.with(mContext)
                    .load(localImageUrl)
//                    .resize(Converter.dpToPx(mContext, 180), Converter.dpToPx(mContext, 180))
                    .resizeDimen(R.dimen.message_image_size, R.dimen.message_image_size)
                    .centerInside()
                    .into(holder.mImage);*/
        } else {
            holder.mImage.setVisibility(View.GONE);
//            holder.mImage.setImageBitmap(null);
        }

        /*if (mList.get(position).getAuthorId()
                .equals(UserManager.getInstance().getCurrentUser().getObjectId()) || mList.get(position).isHidden()) {
            holder.mAlert.setVisibility(View.INVISIBLE);
        } else {
            holder.mAlert.setVisibility(View.VISIBLE);
        }*/

        holder.mAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 new AlertDialog.Builder(MessagesAdapter.this.mContext)
                         .setTitle(R.string.report_abuse)
                         .setMessage(String.format(mContext.getString(R.string.report_dialog_message),
                                 mList.get(holder.getAdapterPosition()).getAuthor()))
                         .setNegativeButton(R.string.cancel_button, null)
                         .setPositiveButton(R.string.ok_button, new DialogInterface.OnClickListener() {
                             @Override
                             public void onClick(DialogInterface dialog, int which) {
                                 mList.get(holder.getAdapterPosition()).setHidden(true);
                                 notifyItemChanged(holder.getAdapterPosition());

                                 Map<String, String> params = new HashMap<>();
                                 //params.put("fromEmail", UserManager.getInstance().getCurrentUser().getEmail());
                                 params.put("toEmail", holder.mName.getContext().getString(R.string.admin_email));
                                 params.put("subject", mContext.getString(R.string.report_abuse));
                                 params.put("emailText", mContext.getString(R.string.url_to_report_begin)
                                         + mList.get(holder.getAdapterPosition()).getObjectId()
                                         + mContext.getString(R.string.url_to_report_end));
                                 /*ParseCloud.callFunctionInBackground("report_abuse", params, new FunctionCallback<Object>() {
                                     @Override
                                     public void done(Object object, ParseException e) {

                                     }
                                 });*/

//                                 final Configuration configuration = new Configuration()
//                                         .domain(mContext.getString(R.string.mailgun_domain))
//                                         .apiKey(mContext.getString(R.string.mailgun_api_key))
//                                         .from(UserManager.getInstance().getCurrentUser().getUsername(),
//                                                 UserManager.getInstance().getCurrentUser().getEmail());
//
//                                 Mail.using(configuration)
//                                         .to(holder.mName.getContext().getString(R.string.admin_email))
//                                         .subject(mContext.getString(R.string.report_abuse))
//                                         .text(mContext.getString(R.string.url_to_report_begin)
//                                                 + mList.get(position).getObjectId()
//                                                 + mContext.getString(R.string.url_to_report_end))
//                                         .build()
//                                         .sendAsync();
                                 Toast.makeText(
                                         mContext,
                                         mContext.getString(R.string.report_submitted_message),
                                         Toast.LENGTH_SHORT
                                 ).show();
//                                         .sendAsync(new MailRequestCallback() {
//                                             @Override
//                                             public void completed(Response response) {
//
//                                                 Toast.makeText(
//                                                         mContext,
//                                                         mContext.getString(R.string.report_submitted_message),
//                                                         Toast.LENGTH_SHORT
//                                                 ).show();
//                                             }
//
//                                             @Override
//                                             public void failed(Throwable throwable) {
//                                                 Toast.makeText(
//                                                         mContext,
//                                                         mContext.getString(R.string.form_submitted_error_message),
//                                                         Toast.LENGTH_SHORT
//                                                 ).show();
//                                             }
//                                         });
                             }
                         })
                         .show();
            }
        });

        holder.mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageUrl != null && mListener != null) {
                    mListener.onImageClick(imageUrl);
                } else if (localImageUrl != null && mListener != null) {
                    mListener.onImageClick(localImageUrl.toString());
                }
            }
        });

        if (mList.get(position).isLocal()) {
            holder.mLayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.sub_color_4));
        } else {
            holder.mLayout.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    private String getTimeFromMillis(long millis) {
        DateFormat df = new SimpleDateFormat("HH:mm", Locale.getDefault());
        df.setTimeZone(Calendar.getInstance().getTimeZone());
        return df.format(new Date(millis));
    }

    private String getDayFromMillis(long millis) {
//        Calendar calendar = new GregorianCalendar();
//        calendar.setTimeInMillis(millis);
//        return calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());\
        String formattedDate;
        DateFormat defaultDateFormat = new SimpleDateFormat("dd.MM.yy", Locale.getDefault());

        Calendar today = new GregorianCalendar();
//        defaultDateFormat.setTimeZone(today.getTimeZone());
        Calendar messageDate = new GregorianCalendar();
        messageDate.setTime(new Date(millis));

        if (today.get(Calendar.DAY_OF_YEAR) - messageDate.get(Calendar.DAY_OF_YEAR) == 1) {
            formattedDate = mContext.getString(R.string.yesterday_label);
        } else if (today.get(Calendar.DAY_OF_YEAR) - messageDate.get(Calendar.DAY_OF_YEAR) == 0){
            formattedDate = mContext.getString(R.string.today_label);
        } else {
            formattedDate = defaultDateFormat.format(messageDate.getTime());
        }
        return formattedDate;
    }

    @Override
    public int getItemCount() {
        if (mList == null)
            return 0;
        return mList.size();
    }

    public interface MessageAdapterListener {
        void onImageClick(String imageUrl);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
//        @BindView(R.id.list_item_message_status)
//        ImageView mStatus;
        @BindView(R.id.list_item_message_name)
        TextView mName;
        @BindView(R.id.list_item_message_day)
        TextView mDay;
        @BindView(R.id.list_item_message_message)
        TextView mMessage;
        @BindView(R.id.list_item_message_time)
        TextView mTime;
        @BindView(R.id.list_item_message_alert)
        ImageView mAlert;
        @BindView(R.id.list_item_message_image)
        ImageView mImage;
        @BindView(R.id.list_item_message_layout)
        LinearLayout mLayout;


        ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}
