package com.duckydev.mvpdagger.episodedetail;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.duckydev.mvpdagger.Constants;
import com.duckydev.mvpdagger.R;
import com.duckydev.mvpdagger.data.Episode;
import com.duckydev.mvpdagger.util.TxtUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerFragment;


public class EpisodeDetailFragment extends DaggerFragment implements EpisodeDetailContract.View {

    @Inject
    EpisodeDetailContract.Presenter mPresenter;
    private Activity mActivity;

    @Inject
    public EpisodeDetailFragment() {
    }

    public int mEpisodeId = -1;

    @BindView(R.id.transcription_title_tv)
    TextView mTranscriptionTitleTv;

    @BindView(R.id.transcription_tv)
    TextView mTranscriptTv;

    @BindView(R.id.summary_title_tv)
    TextView mSummaryTitleTv;

    @BindView(R.id.summary_tv)
    TextView mSummaryTv;

    @BindView(R.id.title_tv)
    TextView mTitleTv;

    @BindView(R.id.description_tv)
    TextView mDescriptionTv;

    SeekBar mProgressSeekbar;

    TextView mCurrentTimeTv;

    TextView mDurationTimeTv;

    ImageView mPlayPauseIv;

    ProgressBar mProgressPlayPauseBar;

    FloatingActionButton mFloatingActionButton;

    private TextView mPlaySpeedTv;

    public AlertDialog alertDialog;


    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        mActivity = getActivity();

        if (mActivity != null) {
            mEpisodeId = getActivity().getIntent().getIntExtra(EpisodeDetailActivity.EXTRA_EPISODE_ID, -1);
        }

        mPresenter.initAudioPlayer(getActivity());

        mProgressSeekbar = getActivity().findViewById(R.id.progressSb);
        mCurrentTimeTv = getActivity().findViewById(R.id.current_timeTv);
        mDurationTimeTv = getActivity().findViewById(R.id.durationTv);
        mPlayPauseIv = getActivity().findViewById(R.id.play_pause_btn_iv);
        mFloatingActionButton = getActivity().findViewById(R.id.play_pause_floating_button);
        mProgressPlayPauseBar = getActivity().findViewById(R.id.progress_play_pause_bar);
        mPlaySpeedTv = getActivity().findViewById(R.id.playSpeedTv);

        mPlayPauseIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.toggle();
            }
        });

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.toggle();
            }
        });

        mProgressSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mCurrentTimeTv.setText(TxtUtils.ConvertMilistoMinuteSecondString(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mPresenter.pause();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mPresenter.seekTo(seekBar.getProgress());
            }
        });

        mPlaySpeedTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.selectPlaybackSpeedClick();
            }
        });

        showProgressBar();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.episodedetail_frag, container, false);
        ButterKnife.bind(this, rootView);
        mPresenter.getEpisode(mEpisodeId);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.takeView(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        mPresenter.cleanUp();
        mPresenter.dropView();
    }

    @Override
    public void showPlaybackSpeedDialog(int currentPlaybackSpeedItemIndex) {
        if (alertDialog != null) {
            alertDialog.dismiss();
            alertDialog = null;
        }
        alertDialog = new AlertDialog.Builder(mActivity)
                .setTitle(R.string.playback_speed_options)
                .setSingleChoiceItems(Constants.PLAYBACK_SPEED_STRING, currentPlaybackSpeedItemIndex, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.selectPlaybackSpeed(which);
                        dialog.dismiss();
                    }
                }).show();
    }

    @Override
    public void showEpisode(Episode episode) {
//        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(episode.getTitle());
        mTitleTv.setText(episode.getTitle());
        if (!TextUtils.isEmpty(episode.getTranscript())) {
            mTranscriptTv.setText(Html.fromHtml(episode.getTranscript()));
            mTranscriptionTitleTv.setVisibility(View.VISIBLE);
        } else {
            mTranscriptionTitleTv.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(episode.getSummary())) {
            mSummaryTv.setText(Html.fromHtml(episode.getSummary()));
            mSummaryTitleTv.setVisibility(View.VISIBLE);
        } else {
            mSummaryTitleTv.setVisibility(View.GONE);
        }
        mDescriptionTv.setText(episode.getDescription());

        if (getActivity() != null) {
            ((EpisodeDetailActivity) getActivity()).loadBackdrop(episode.getThumbImageUrl());
        }
        if (getActivity() != null) {
            ((EpisodeDetailActivity) getActivity()).loadToolbarTitle(episode.getTitle());
        }
    }

    @Override
    public void showProgressBar() {
//        mProgressSeekbar.setVisibility(View.VISIBLE);
        mProgressPlayPauseBar.setVisibility(View.VISIBLE);
        mPlayPauseIv.setVisibility(View.GONE);
    }

    @Override
    public void hideProgressBar() {
//        mProgressSeekbar.setVisibility(View.GONE);
//        mProgressSeekbar.setProgress(0);
        mProgressPlayPauseBar.setVisibility(View.GONE);
        mPlayPauseIv.setVisibility(View.VISIBLE);
    }

    @Override
    public void setCurrentProgressSeekbar(int position) {
        mProgressSeekbar.setProgress(position);
    }

    @Override
    public void setMaxProgressSeekbar(int maxProgress) {
        mProgressSeekbar.setMax(maxProgress);
    }

    @Override
    public void showPlayControlButton() {
        mPlayPauseIv.setImageResource(R.drawable.ic_action_playback_play);
        mFloatingActionButton.setImageResource(R.drawable.ic_action_playback_play);
    }

    @Override
    public void showPauseControlButton() {
        mPlayPauseIv.setImageResource(R.drawable.ic_action_playback_pause);
        mFloatingActionButton.setImageResource(R.drawable.ic_action_playback_pause);
    }

    @Override
    public void setCurrentTimeTextView(String text) {
        mCurrentTimeTv.setText(text);
    }

    @Override
    public void setDurationTimeTextView(String text) {
        mDurationTimeTv.setText(text);
    }

    @Override
    public void updateBackgroundSC(boolean enabled) {

    }

    @Override
    public void setPlaySpeedText(String text) {
        mPlaySpeedTv.setText(text);
    }

}