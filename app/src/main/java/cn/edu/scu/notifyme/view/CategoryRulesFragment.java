package cn.edu.scu.notifyme.view;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.edu.scu.notifyme.R;

public class CategoryRulesFragment extends Fragment {

    private Unbinder unbinder;

    @BindView(R.id.tb_base)
    Toolbar tbBase;
    @BindView(R.id.vp_main)
    ViewPager vpMain;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_rules, container, false);
        unbinder = ButterKnife.bind(this, view);

        tbBase.inflateMenu(R.menu.base_toolbar_menu);
        tbBase.getOverflowIcon().setColorFilter(
                getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        List<View> views = new ArrayList<>();
        //TODO: 修改为真实分类
        views.add(View.inflate(this.getContext(), R.layout.fragment_dummy, null));
        views.add(View.inflate(this.getContext(), R.layout.fragment_dummy, null));
        views.add(View.inflate(this.getContext(), R.layout.fragment_dummy, null));
        views.add(View.inflate(this.getContext(), R.layout.fragment_dummy, null));
        views.add(View.inflate(this.getContext(), R.layout.fragment_dummy, null));
        views.add(View.inflate(this.getContext(), R.layout.fragment_dummy, null));
        views.add(View.inflate(this.getContext(), R.layout.fragment_dummy, null));
        views.add(View.inflate(this.getContext(), R.layout.fragment_dummy, null));
        vpMain.setAdapter(new MainViewPagerAdapter(views));

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private class MainViewPagerAdapter extends PagerAdapter {

        private List<View> views;
        private String[] titles = {"未分类", "STEAM", "直播", "BILIBILI", "5", "6", "7", "8"};

        public MainViewPagerAdapter(List<View> views) {
            this.views = views;
        }

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View view = views.get(position);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView(views.get(position));
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return this.titles[position];
        }
    }
}
