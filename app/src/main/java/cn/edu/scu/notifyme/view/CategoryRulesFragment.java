package cn.edu.scu.notifyme.view;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.edu.scu.notifyme.CreateTask;
import cn.edu.scu.notifyme.R;
import cn.edu.scu.notifyme.model.Category;
import cn.edu.scu.notifyme.model.Rule;

public class CategoryRulesFragment extends Fragment {

    private Unbinder unbinder;

    @BindView(R.id.fab_add_rule)
    FloatingActionButton fabAddRule;
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

        //TODO: 修改为真实分类
        this.categories = new ArrayList<>();
        this.fragments = new ArrayList<>();
        this.createDummyFragment(this.createDummyRule("未分类"));
        for (int i = 0; i < 7; i++) {
            this.createDummyFragment(this.createDummyRule(String.valueOf(i)));
        }
        vpMain.setAdapter(new MainFragmentPagerAdapter(getChildFragmentManager()));

        return view;
    }

    private List<Fragment> fragments;
    private List<Category> categories;

    private void createDummyFragment(Category category) {
        RuleListFragment ruleListFragment = new RuleListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(RuleListFragment.PARAM_TEXT, category.getName());
        ruleListFragment.setArguments(bundle);
        this.fragments.add(ruleListFragment);
    }

    private Category createDummyRule(String name) {
        Category category = new Category();
        category.setName(name);
        this.categories.add(category);
        return category;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.fab_add_rule)
    public void onViewClicked() {
        Category category = categories.get(vpMain.getCurrentItem());
        Intent intent = new Intent(getContext(), CreateTask.class);
        intent.putExtra(CreateTask.PARAM_CATEGORY, category);
        startActivity(intent);
    }

    private class MainFragmentPagerAdapter extends FragmentPagerAdapter {

        public MainFragmentPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return categories.get(position).getName();
        }
    }
}
