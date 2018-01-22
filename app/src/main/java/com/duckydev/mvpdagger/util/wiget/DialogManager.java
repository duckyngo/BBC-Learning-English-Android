package com.duckydev.mvpdagger.util.wiget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.provider.Settings;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.duckydev.mvpdagger.R;
import com.duckydev.mvpdagger.util.GooglePlayUtils;


public class DialogManager {
    private Dialog mCurrentDialog;
    private Dialog mNoInternetDialog;
    private Dialog mShowPromoteAppDialog;
    private Dialog mProgressDialog = null;
    private Context mContext;


    public void reloadDialog() {
        if (this.mCurrentDialog != null && !this.mCurrentDialog.isShowing()) {
            try {
                this.mCurrentDialog.show();
            } catch (Exception e) {
            }
        }
    }

    public void dismissDialog() {
        if (this.mCurrentDialog == null || !this.mCurrentDialog.isShowing()) {
            this.mCurrentDialog = null;
            return;
        }
        try {
            this.mCurrentDialog.dismiss();
        } catch (Exception e) {
        }
    }

    public void showDialog(Dialog dialog) {
        dismissDialog();
        try {
            dialog.show();
            this.mCurrentDialog = dialog;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean hasDialog() {
        return this.mCurrentDialog != null;
    }


    public void makeProgressDialog(final Context mContext) {
        if (mContext == null || ((Activity) mContext).isFinishing()) {
            return;
        }
        if (mProgressDialog == null) {
            mProgressDialog = new Dialog(mContext);
            mProgressDialog.setCancelable(false);
            mProgressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            mProgressDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            mProgressDialog.setContentView(R.layout.progress_dialog);
            mProgressDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            mProgressDialog.show();
        } else mProgressDialog.show();
        mProgressDialog.setOnKeyListener(null);
    }

    public void cancelProgressDialog() {
        try {
            if (mProgressDialog != null && mProgressDialog.isShowing())
                mProgressDialog.dismiss();
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    public void showNoInternetConnection(final Context context) {

        if (mNoInternetDialog == null) {
            mNoInternetDialog = new Dialog(context);
            mNoInternetDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            mNoInternetDialog.setTitle(R.string.no_internet_dialog_lesson_title);
            mNoInternetDialog.setContentView(R.layout.no_internet_dialog);
            TextView textView1 = (TextView) mNoInternetDialog.findViewById(R.id.btCancle);
            TextView textview = (TextView) mNoInternetDialog.findViewById(R.id.btSetting);
            textView1.setOnClickListener(new View.OnClickListener() {

                public void onClick(View view) {
                    mNoInternetDialog.dismiss();
                }

            });
            textview.setOnClickListener(new View.OnClickListener() {

                public void onClick(View view) {
                    mNoInternetDialog.dismiss();
                    context.startActivity(new Intent(Settings.ACTION_SETTINGS));
                }
            });

            mNoInternetDialog.show();

        } else {
            mNoInternetDialog.show();
        }

    }

    public void cancelNoInternetConnectionDialog() {
        try {
            if (mNoInternetDialog != null && mNoInternetDialog.isShowing())
                mNoInternetDialog.dismiss();
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    public void showPromoteAppDialog(final Context context) {

        if (mShowPromoteAppDialog == null) {
            mShowPromoteAppDialog = new Dialog(context);
            mContext = context;
            mShowPromoteAppDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            mShowPromoteAppDialog.setTitle(R.string.promote_app_dialog_title);
            mShowPromoteAppDialog.setContentView(R.layout.promote_app_dialog);
            TextView textView1 = (TextView) mShowPromoteAppDialog.findViewById(R.id.btCancle);
            TextView textview = (TextView) mShowPromoteAppDialog.findViewById(R.id.btSetting);
            textView1.setOnClickListener(new View.OnClickListener() {

                public void onClick(View view) {
                    mShowPromoteAppDialog.dismiss();
                }

            });
            textview.setOnClickListener(new View.OnClickListener() {

                public void onClick(View view) {
                    mShowPromoteAppDialog.dismiss();
                    GooglePlayUtils.getInstance().goToGooglePlay(context, GooglePlayUtils.MY_PROMOTE_APP_MARKTE_URL);
                }
            });

            mShowPromoteAppDialog.show();

        } else {
            mShowPromoteAppDialog.show();
        }

    }

    public void cancelPromoteAppDialog() {
        try {
            if (mShowPromoteAppDialog != null && mShowPromoteAppDialog.isShowing())
                mShowPromoteAppDialog.dismiss();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
