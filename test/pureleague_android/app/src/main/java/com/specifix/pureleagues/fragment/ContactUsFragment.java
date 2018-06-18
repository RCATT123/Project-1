package com.specifix.pureleagues.fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.specifix.pureleagues.R;

import net.sargue.mailgun.Configuration;
import net.sargue.mailgun.Mail;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContactUsFragment extends Fragment {
    @BindView(R.id.contact_us_title)
    TextView mTitle;
    @BindView(R.id.contact_us_name)
    EditText mName;
    @BindView(R.id.contact_us_reason_edit_text)
    EditText mReason;
    @BindView(R.id.contact_us_phone)
    EditText mPhone;
    @BindView(R.id.contact_us_email)
    EditText mEmail;
    @BindView(R.id.contact_us_message)
    EditText mMessage;
    @BindView(R.id.contact_us_submit_button)
    Button mSubmitBtn;

    public static ContactUsFragment newInstance() {
        ContactUsFragment fragment = new ContactUsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_contact_us, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Typeface gothamBold = Typeface.createFromAsset(getContext().getAssets(), "GothamBold.otf");
        Typeface gothamBook = Typeface.createFromAsset(getContext().getAssets(), "GothamBook.otf");
        mTitle.setTypeface(gothamBold);
        mName.setTypeface(gothamBook);
        mReason.setTypeface(gothamBook);
        mPhone.setTypeface(gothamBook);
        mEmail.setTypeface(gothamBook);
        mMessage.setTypeface(gothamBook);
        mSubmitBtn.setTypeface(gothamBold);
    }

    @OnClick(R.id.contact_us_submit_button)
    public void onSubmitClick() {
        if (!checkForm()) {
            return;
        }

        mSubmitBtn.setEnabled(false);
        mSubmitBtn.setText(getString(R.string.submitting_text));

        final String emailMessage = mMessage.getText().toString().trim() + "\n\n"
                + "User " + mName.getText().toString().trim() + "\n"
                + "Email " + mEmail.getText().toString().trim() + "\n"
                + "Phone " + mPhone.getText().toString().trim();

        final Configuration configuration = new Configuration()
                .domain(getString(R.string.mailgun_domain))
                .apiKey(getString(R.string.mailgun_api_key))
                .from(mName.getText().toString(), mEmail.getText().toString());

        Mail.using(configuration)
                .to(getString(R.string.admin_email))
                .subject(mReason.getText().toString())
                .text(emailMessage)
                .build()
                .sendAsync();
        mName.setText("");
        mReason.setText("");
        mPhone.setText("");
        mEmail.setText("");
        mMessage.setText("");
        Toast.makeText(getContext(), getString(R.string.form_submitted_message), Toast.LENGTH_SHORT).show();
        mSubmitBtn.setEnabled(true);
        mSubmitBtn.setText(getString(R.string.submit_button_text));
//                .sendAsync(new MailRequestCallback() {
//                    @Override
//                    public void completed(Response response) {
//                        mName.setText("");
//                        mReason.setText("");
//                        mPhone.setText("");
//                        mEmail.setText("");
//                        mMessage.setText("");
//                        Toast.makeText(getContext(), getString(R.string.form_submitted_message), Toast.LENGTH_SHORT).show();
//                        mSubmitBtn.setEnabled(true);
//                        mSubmitBtn.setText(getString(R.string.submit_button_text));
//                    }
//
//                    @Override
//                    public void failed(Throwable throwable) {
//                        mSubmitBtn.setEnabled(true);
//                        mSubmitBtn.setText(getString(R.string.submit_button_text));
//                        Toast.makeText(getContext(), getString(R.string.form_submitted_error_message), Toast.LENGTH_SHORT).show();
//                    }
//                });
    }

    private boolean checkForm() {
        if (mName.getText().toString().equals("")) {
            mName.setError(getString(R.string.field_empty_error_message));
            return false;
        }
        mName.setError(null);
        if (mReason.getText().toString().equals("")) {
            mReason.setError(getString(R.string.field_empty_error_message));
            return false;
        }
        mReason.setError(null);
        if (mPhone.getText().toString().equals("")) {
            mPhone.setError(getString(R.string.field_empty_error_message));
            return false;
        }
        mPhone.setError(null);
        if (mEmail.getText().toString().equals("")) {
            mEmail.setError(getString(R.string.field_empty_error_message));
            return false;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(mEmail.getText().toString()).matches()) {
            mEmail.setError(getString(R.string.email_error_message));
            return false;
        }
        mEmail.setError(null);
        if (mMessage.getText().toString().equals("")) {
            mMessage.setError(getString(R.string.field_empty_error_message));
            return false;
        }
        mMessage.setError(null);

        return true;
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
    }
}
