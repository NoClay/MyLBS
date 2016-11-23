package andfans.com.mylbs;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import andfans.com.mylbs.Fragments.Fragment_Locate;
import andfans.com.mylbs.Fragments.Fragment_Shouye;
import andfans.com.mylbs.Fragments.Fragment_me;

public class MainActivity extends FragmentActivity implements View.OnClickListener {
    private ViewPager viewPager;
    private List<Fragment> datas;
    private TextView tab01,tab02,tab03;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        viewPager.setCurrentItem(0);
        setTab(0);
    }

    private void initView() {
        tab01 = (TextView) findViewById(R.id.id_main_tab01);
        tab01.setOnClickListener(this);
        tab02 = (TextView) findViewById(R.id.id_main_tab02);
        tab02.setOnClickListener(this);
        tab03 = (TextView) findViewById(R.id.id_main_tab03);
        tab03.setOnClickListener(this);
        viewPager = (ViewPager) findViewById(R.id.id_main_vp);
        Fragment_Shouye fragment_shouye = new Fragment_Shouye();
        Fragment_Locate fragment_locate = new Fragment_Locate();
        Fragment_me fragment_me = new Fragment_me();
        datas = new ArrayList<>();
        datas.add(fragment_shouye);
        datas.add(fragment_locate);
        datas.add(fragment_me);
        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return datas.get(position);
            }

            @Override
            public int getCount() {
                return datas.size();
            }
        };

        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                resetView();
                setTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void resetView(){
        tab01.setTextColor(getResources().getColor(R.color.white));
        tab02.setTextColor(getResources().getColor(R.color.white));
        tab03.setTextColor(getResources().getColor(R.color.white));
    }
    @Override
    public void onClick(View view) {
        resetView();
        switch (view.getId()){
            case R.id.id_main_tab01:
                setTab(0);
                break;
            case R.id.id_main_tab02:
                setTab(1);
                break;
            case R.id.id_main_tab03:
                setTab(2);
                break;
            default:break;
        }
    }

    private void setTab(int i) {
        switch (i){
            case 0:
                tab01.setTextColor(getResources().getColor(R.color.lightseagreen));
                viewPager.setCurrentItem(0);
                break;
            case 1:
                tab02.setTextColor(getResources().getColor(R.color.lightseagreen));
                viewPager.setCurrentItem(1);
                break;
            case 2:
                tab03.setTextColor(getResources().getColor(R.color.lightseagreen));
                viewPager.setCurrentItem(2);
                break;
            default:break;
        }
    }
}
