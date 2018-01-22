package com.duckydev.mvpdagger.util.wiget;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.duckydev.mvpdagger.R;
import com.duckydev.mvpdagger.util.FeedbackUtils;
import com.duckydev.mvpdagger.util.GooglePlayUtils;
import com.duckydev.mvpdagger.util.SharePreferenceUtils;


public class RateManager {
    private final int UPDATE_VIEW = 1;
    private DialogManager dialogManager;
    private int mAniTime = 0;
    private Activity mContext;
    private ImageView mStart1;
    private ImageView mStart2;
    private ImageView mStart3;
    private ImageView mStart4;
    private ImageView mStart5;
    private int mValue;
    private LinearLayout mView1;
    private View rootView;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    RateManager.this.updateView();
                    return;
                default:
                    return;
            }
        }
    };

    public RateManager(Activity mContext, DialogManager dialogManager) {
        this.mContext = mContext;
        this.dialogManager = dialogManager;
    }

    private void updateView() {
        this.mView1.setVisibility(View.GONE);
        updateTitleView();
        disableStars();
    }

    public void showRate() {
        try {
//            GoogleAnalyticsUtils.sendEvent(this.mContext, "AppRate", "Show", "");
            AlertDialog builder = new Builder(this.mContext).create();
            this.rootView = LayoutInflater.from(this.mContext).inflate(R.layout.dialog_rate, null);
            initTitleView();
            initStarViews(this.rootView);
            initRateButtons(this.rootView);
            builder.setView(this.rootView);
            builder.setCanceledOnTouchOutside(false);
            this.dialogManager.showDialog(builder);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initTitleView() {
        ((TextView) this.rootView.findViewById(R.id.rate_main_tip)).setText(R.string.rate_main_tip);
    }

    private void initReviewButtons(View view) {
        TextView reviewButton = (TextView) view.findViewById(R.id.rate_review_rate);
        reviewButton.setText(this.mContext.getString(R.string.rate_on_google).toUpperCase());
        reviewButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                GooglePlayUtils.getInstance().goToAppGooglePlay(RateManager.this.mContext);
                RateManager.this.disableRate();
                RateManager.this.dialogManager.dismissDialog();
//                GoogleAnalyticsUtils.sendEvent(RateManager.this.mContext, "HasFinger", "RateOnPlayStore", "");
            }
        });
    }

    private void initFeedbackButtons(View view) {
        EditText editText = (EditText) view.findViewById(R.id.edit_text);
        TextView feedbackButton = (TextView) view.findViewById(R.id.rate_feedback_send);
        TextView cancelButton = (TextView) view.findViewById(R.id.rate_feedback_cancel);
        feedbackButton.setText(this.mContext.getString(R.string.send_review).toUpperCase());
        cancelButton.setText(this.mContext.getString(R.string.cancel).toUpperCase());
        feedbackButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                FeedbackUtils.sendFeedback(RateManager.this.mContext);
                RateManager.this.disableRate();
                RateManager.this.dialogManager.dismissDialog();
//                GoogleAnalyticsUtils.sendEvent(RateManager.this.mContext, "HasFinger", "Feedback", "");
            }
        });
        cancelButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                RateManager.this.dialogManager.dismissDialog();
            }
        });
    }

    private void initRateButtons(View view) {
        TextView view1Rate = (TextView) view.findViewById(R.id.rate_rate);
        TextView view1Close = (TextView) view.findViewById(R.id.rate_cancel);
        view1Rate.setText(this.mContext.getString(R.string.rate).toUpperCase());
        view1Close.setText(this.mContext.getString(R.string.cancel).toUpperCase());
        view1Rate.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
//                GoogleAnalyticsUtils.sendEvent(RateManager.this.mContext, "HasFinger", "Rate", "");
                if (RateManager.this.mValue < 4) {
                    RateManager.this.showFeedback();
                } else {
                    RateManager.this.showReview();
                }
                RateManager.this.mHandler.sendEmptyMessageDelayed(1, (long) RateManager.this.mAniTime);
            }
        });
        view1Close.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                RateManager.this.dialogManager.dismissDialog();
            }
        });
//        GoogleAnalyticsUtils.sendEvent(this.mContext, "HasFinger", "Cancel", "");
    }

    private void showReview() {
        initReviewButtons(this.rootView);
        LinearLayout view2 = (LinearLayout) this.rootView.findViewById(R.id.rate_layout_review);
        ((RelativeLayout) this.rootView.findViewById(R.id.rate_star_layout)).startAnimation(outAni());
        view2.setVisibility(View.VISIBLE);
        this.mHandler.postDelayed(new Runnable() {
            public void run() {
                ((RelativeLayout) RateManager.this.rootView.findViewById(R.id.rate_star_layout)).setVisibility(View.GONE);
            }
        }, (long) this.mAniTime);
    }

    private void showFeedback() {
        initFeedbackButtons(this.rootView);
        ((LinearLayout) this.rootView.findViewById(R.id.rate_layout_feedback)).setVisibility(View.VISIBLE);
    }

    private void initStarViews(View view) {
        this.mView1 = (LinearLayout) view.findViewById(R.id.rate_layout_main_buttons);
        this.mView1.setVisibility(View.VISIBLE);
        this.mStart1 = (ImageView) view.findViewById(R.id.rate_1_value_1);
        this.mStart2 = (ImageView) view.findViewById(R.id.rate_1_value_2);
        this.mStart3 = (ImageView) view.findViewById(R.id.rate_1_value_3);
        this.mStart4 = (ImageView) view.findViewById(R.id.rate_1_value_4);
        this.mStart5 = (ImageView) view.findViewById(R.id.rate_1_value_5);
        updateStars();
        this.mStart1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                int i = 1;
                RateManager rateManager = RateManager.this;
                if (RateManager.this.mValue == 1) {
                    i = 0;
                }
                rateManager.mValue = i;
                RateManager.this.updateStars();
            }
        });
        this.mStart2.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                int i = 2;
                RateManager rateManager = RateManager.this;
                if (RateManager.this.mValue == 2) {
                    i = 0;
                }
                rateManager.mValue = i;
                RateManager.this.updateStars();
            }
        });
        this.mStart3.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                int i = 3;
                RateManager rateManager = RateManager.this;
                if (RateManager.this.mValue == 3) {
                    i = 0;
                }
                rateManager.mValue = i;
                RateManager.this.updateStars();
            }
        });
        this.mStart4.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                int i = 4;
                RateManager rateManager = RateManager.this;
                if (RateManager.this.mValue == 4) {
                    i = 0;
                }
                rateManager.mValue = i;
                RateManager.this.updateStars();
            }
        });
        this.mStart5.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                int i = 5;
                RateManager rateManager = RateManager.this;
                if (RateManager.this.mValue == 5) {
                    i = 0;
                }
                rateManager.mValue = i;
                RateManager.this.updateStars();
            }
        });
        showFinger();
    }

    private void showFinger() {
        ImageView fingerView = (ImageView) this.rootView.findViewById(R.id.rate_finger);
        if (!fingerView.isShown()) {
            fingerView.setVisibility(View.VISIBLE);
            ((AnimationDrawable) fingerView.getDrawable()).start();
            fingerView.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    int i = 5;
                    RateManager rateManager = RateManager.this;
                    if (RateManager.this.mValue == 5) {
                        i = 0;
                    }
                    rateManager.mValue = i;
                    RateManager.this.updateStars();
                }
            });
        }
    }

    private void hideFinger() {
        ImageView fingerView = (ImageView) this.rootView.findViewById(R.id.rate_finger);
        if (fingerView.isShown()) {
            ((AnimationDrawable) fingerView.getDrawable()).stop();
            fingerView.setVisibility(View.GONE);
        }
    }

    private AnimationSet outAni() {
        TranslateAnimation animation = new TranslateAnimation(1, 0.0f, 1, -1.0f, 1, 0.0f, 1, 0.0f);
        animation.setDuration((long) this.mAniTime);
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(animation);
        return animationSet;
    }

    private AnimationSet inAni() {
        TranslateAnimation animation = new TranslateAnimation(1, 1.0f, 1, 0.0f, 1, 0.0f, 1, 0.0f);
        animation.setDuration((long) this.mAniTime);
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(animation);
        return animationSet;
    }

    private void disableStars() {
        this.mStart1.setEnabled(false);
        this.mStart2.setEnabled(false);
        this.mStart3.setEnabled(false);
        this.mStart4.setEnabled(false);
        this.mStart5.setEnabled(false);
    }

    private void updateStars() {
        switch (this.mValue) {
            case 1:
                this.mStart1.setImageResource(R.drawable.ic_rate_on);
                this.mStart2.setImageResource(R.drawable.ic_rate_off);
                this.mStart3.setImageResource(R.drawable.ic_rate_off);
                this.mStart4.setImageResource(R.drawable.ic_rate_off);
                this.mStart5.setImageResource(R.drawable.ic_rate_off);
                break;
            case 2:
                this.mStart1.setImageResource(R.drawable.ic_rate_on);
                this.mStart2.setImageResource(R.drawable.ic_rate_on);
                this.mStart3.setImageResource(R.drawable.ic_rate_off);
                this.mStart4.setImageResource(R.drawable.ic_rate_off);
                this.mStart5.setImageResource(R.drawable.ic_rate_off);
                break;
            case 3:
                this.mStart1.setImageResource(R.drawable.ic_rate_on);
                this.mStart2.setImageResource(R.drawable.ic_rate_on);
                this.mStart3.setImageResource(R.drawable.ic_rate_on);
                this.mStart4.setImageResource(R.drawable.ic_rate_off);
                this.mStart5.setImageResource(R.drawable.ic_rate_off);
                break;
            case 4:
                this.mStart1.setImageResource(R.drawable.ic_rate_on);
                this.mStart2.setImageResource(R.drawable.ic_rate_on);
                this.mStart3.setImageResource(R.drawable.ic_rate_on);
                this.mStart4.setImageResource(R.drawable.ic_rate_on);
                this.mStart5.setImageResource(R.drawable.ic_rate_off);
                break;
            case 5:
                this.mStart1.setImageResource(R.drawable.ic_rate_on);
                this.mStart2.setImageResource(R.drawable.ic_rate_on);
                this.mStart3.setImageResource(R.drawable.ic_rate_on);
                this.mStart4.setImageResource(R.drawable.ic_rate_on);
                this.mStart5.setImageResource(R.drawable.ic_rate_on);
                break;
            default:
                this.mStart1.setImageResource(R.drawable.ic_rate_off);
                this.mStart2.setImageResource(R.drawable.ic_rate_off);
                this.mStart3.setImageResource(R.drawable.ic_rate_off);
                this.mStart4.setImageResource(R.drawable.ic_rate_off);
                this.mStart5.setImageResource(R.drawable.ic_rate_off);
                break;
        }
        TextView rateButton = (TextView) this.rootView.findViewById(R.id.rate_rate);
        if (this.mValue == 0) {
            rateButton.setEnabled(false);
            rateButton.setTextColor(this.mContext.getResources().getColor(R.color.disable_color));
            return;
        }
        rateButton.setEnabled(true);
        rateButton.setTextColor(this.mContext.getResources().getColor(R.color.colorPrimaryDark));
        hideFinger();
    }

    private void updateTitleView() {
        TextView titleTextView = (TextView) this.rootView.findViewById(R.id.rate_main_tip);
        switch (this.mValue) {
            case 1:
                titleTextView.setText(this.mContext.getString(R.string.rate_hate_it));
                break;
            case 2:
                titleTextView.setText(this.mContext.getString(R.string.rate_dislike));
                break;
            case 3:
                titleTextView.setText(this.mContext.getString(R.string.rate_it_is_ok));
                break;
            case 4:
                titleTextView.setText(this.mContext.getString(R.string.rate_very_like));
                break;
            case 5:
                titleTextView.setText(this.mContext.getString(R.string.rate_very_like));
                break;
            default:
                titleTextView.setText(this.mContext.getString(R.string.rate_main_message));
                break;
        }
        if (this.mValue == 0) {
            titleTextView.setTextColor(this.mContext.getResources().getColor(R.color.md_text_gray));
        } else {
            titleTextView.setTextColor(this.mContext.getResources().getColor(R.color.rate_desc_color));
        }
    }

    private void disableRate() {
        new SharePreferenceUtils(this.mContext).setRateCount(11);
    }
}
