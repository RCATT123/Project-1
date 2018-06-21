// Generated code from Butter Knife. Do not modify!
package com.specifix.pureleagues.adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.specifix.pureleagues.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ResultsAdapter$DataViewHolder_ViewBinding implements Unbinder {
  private ResultsAdapter.DataViewHolder target;

  @UiThread
  public ResultsAdapter$DataViewHolder_ViewBinding(ResultsAdapter.DataViewHolder target,
      View source) {
    this.target = target;

    target.mClubScoreOne = Utils.findRequiredViewAsType(source, R.id.results_club_one_score, "field 'mClubScoreOne'", TextView.class);
    target.mFirstClub = Utils.findRequiredViewAsType(source, R.id.results_list_item_team_one_title, "field 'mFirstClub'", TextView.class);
    target.mClubScoreTwo = Utils.findRequiredViewAsType(source, R.id.results_club_two_score, "field 'mClubScoreTwo'", TextView.class);
    target.mSecondClub = Utils.findRequiredViewAsType(source, R.id.results_list_item_team_two_title, "field 'mSecondClub'", TextView.class);
    target.mDate = Utils.findRequiredViewAsType(source, R.id.results_list_item_date, "field 'mDate'", TextView.class);
    target.mMessages = Utils.findRequiredViewAsType(source, R.id.results_list_item_messages, "field 'mMessages'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ResultsAdapter.DataViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mClubScoreOne = null;
    target.mFirstClub = null;
    target.mClubScoreTwo = null;
    target.mSecondClub = null;
    target.mDate = null;
    target.mMessages = null;
  }
}
