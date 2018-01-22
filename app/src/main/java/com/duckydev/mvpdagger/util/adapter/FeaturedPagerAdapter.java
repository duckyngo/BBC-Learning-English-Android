package com.duckydev.mvpdagger.util.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.duckydev.mvpdagger.R;
import com.duckydev.mvpdagger.data.Episode;
import com.duckydev.mvpdagger.episodedetail.EpisodeDetailActivity;
import com.duckydev.mvpdagger.features.FeaturesActivity;
import com.duckydev.mvpdagger.util.EpisodeType;
import com.duckydev.mvpdagger.util.TxtUtils;
import com.duckydev.mvpdagger.util.subview.AspectRatioImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.duckydev.mvpdagger.features.FeaturesActivity.EXTRA_FEATURE;

/**
 * Created by duckyng on 1/3/2018.
 */

public class FeaturedPagerAdapter extends PagerAdapter implements ViewPager.OnPageChangeListener {

    public static final int LOOP_COUNT = 50;
    private static int e = 2;
    SparseArray<View> mViewSparseArray = new SparseArray();
    private final Activity mActivity;
    private LayoutInflater mLayoutInflater;
    public List<Episode> mEpisodeList;
    private int mInitialPos = -1;
    private int h;

    public FeaturedPagerAdapter(Activity activity, List<Episode> episodeList) {
        mActivity = activity;
        mEpisodeList = new ArrayList<>();
        mEpisodeList.addAll(episodeList);
        this.mLayoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return mEpisodeList.size() * LOOP_COUNT;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View inflate = mLayoutInflater.inflate(R.layout.feature_pager_view_item, container, false);
        if (mActivity == null) {
            return inflate;
        }

        final Episode episode = (Episode) mEpisodeList.get(position % mEpisodeList.size());
        inflate.findViewById(R.id.gradient).setVisibility(isRestart(position) ? View.INVISIBLE : View.VISIBLE);

        ImageView imageView = (AspectRatioImageView) inflate.findViewById(R.id.feature_img);
        TextView textView = (TextView) inflate.findViewById(R.id.course_name);

        if (TxtUtils.isNotBlank(episode.getTitle())) {
            textView.setText(episode.getTitle());
        } else {
            textView.setVisibility(View.INVISIBLE);
        }

        Picasso.with(mActivity).load(episode.getThumbImageUrl()).into(imageView);

        inflate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity.getApplicationContext(), EpisodeDetailActivity.class);
                intent.putExtra(EpisodeDetailActivity.EXTRA_EPISODE_ID, episode.get_id());
                mActivity.startActivity(intent);
            }
        });
        View findViewId = inflate.findViewById(R.id.course_details_wrapper);
        inflate.setTag(findViewId);
        mViewSparseArray.put(position, inflate);
        container.addView(inflate);
        return inflate;
    }

    public void setInitialSelectedPosition(int i) {
        this.mInitialPos = i;
    }

    public void setInitialSelectedPosition() {
        int count = getCount() / 2;
        this.mInitialPos = count - (count % (getCount() / LOOP_COUNT));
    }

    public int getInitialSelectedPosition() {
        return this.mInitialPos;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
        mViewSparseArray.remove(position);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (this.mViewSparseArray.get(position) != null && this.mViewSparseArray.get(position + 1) != null) {
            float f2 = positionOffset * positionOffset;
            if (this.h == position) {
                ((View) ((View) this.mViewSparseArray.get(position)).getTag()).setAlpha(1.0f - f2);
                ((View) ((View) this.mViewSparseArray.get(position + 1)).getTag()).setAlpha(f2);
                return;
            }
            ((View) ((View) this.mViewSparseArray.get(position + 1)).getTag()).setAlpha(f2);
            ((View) ((View) this.mViewSparseArray.get(position)).getTag()).setAlpha(1.0f - f2);
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private boolean isRestart(int pos) {
        return pos % mEpisodeList.size() == 0;
    }
}
