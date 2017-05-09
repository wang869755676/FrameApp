package com.company.ui.activty;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.EdgeEffectCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.company.MyApplication;
import com.company.R;
import com.company.base.BaseFragmentActivity;
import com.company.ui.activty.presenter.FirstActivityPresenter;
import com.company.ui.activty.view.FirstActivityView;

import java.lang.reflect.Field;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FirstActivity extends BaseFragmentActivity<FirstActivityPresenter, FirstActivityView> implements FirstActivityView {
    @BindView(R.id.vp_first)
    ViewPager vpFirst;
    @BindView(R.id.ll_pointer)
    LinearLayout llPointer;

    private EdgeEffectCompat leftEdge;
    private EdgeEffectCompat rightEdge;


    private ArrayList<View> views;
    private ImageView[] ivs_indicator;


    @Override
    protected FirstActivityPresenter initPresenter() {
        return new FirstActivityPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    protected void initEvent() {
       vpFirst.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (rightEdge != null && !rightEdge.isFinished()) {//到了最后一张并且还继续拖动，出现蓝色限制边条了
                    MyApplication.getInstance().mPreferenceUtil.setIsFirst(true);
                    Intent intent = new Intent(FirstActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onPageSelected(int position) {
                if (ivs_indicator != null) {
                    for (int i = 0; i < ivs_indicator.length; i++) {
                        if (i == position) {
                           // ivs_indicator[i].setImageResource(R.mipmap.dot_white);
                        } else {
                           // ivs_indicator[i].setImageResource(R.mipmap.dot_light);
                        }
                    }
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Override
    public void initView() {
        views = new ArrayList<>();
        views.add(getLayoutInflater().inflate(R.layout.viewpager_page1_1, null));
        views.add(getLayoutInflater().inflate(R.layout.viewpager_page1_2, null));
        views.add(getLayoutInflater().inflate(R.layout.viewpager_page1_3, null));

        // 处理最后一页的情况
        View v = getLayoutInflater().inflate(R.layout.viewpager_page1_4, null);
        views.add(v);
        initEdhe();

        ivs_indicator = new ImageView[4];
    /*    for (int i = 0; i < llPointer.getChildCount(); i++) {
            ivs_indicator[i] = (ImageView) llPointer.getChildAt(i);
        }
*/
        vpFirst.setAdapter(new GuidePagerAdapter());

    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_first;
    }


    public void initEdhe() {
        try {
            Field leftEdgeField = vpFirst.getClass().getDeclaredField("mLeftEdge");
            Field rightEdgeField = vpFirst.getClass().getDeclaredField("mRightEdge");
            if (leftEdgeField != null && rightEdgeField != null) {
                leftEdgeField.setAccessible(true);
                rightEdgeField.setAccessible(true);
                leftEdge = (EdgeEffectCompat) leftEdgeField.get(vpFirst);
                rightEdge = (EdgeEffectCompat) rightEdgeField.get(vpFirst);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class GuidePagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(views.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(views.get(position));

            return views.get(position);
        }
    }

}


