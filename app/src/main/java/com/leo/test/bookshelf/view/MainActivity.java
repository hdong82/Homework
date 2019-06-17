package com.leo.test.bookshelf.view;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.leo.test.bookshelf.R;
import com.leo.test.bookshelf.adapter.ViewPagerAdapter;
import com.leo.test.bookshelf.utils.KeyboardUtil;
import com.leo.test.bookshelf.view.fragments.BookMarkFragment;
import com.leo.test.bookshelf.view.fragments.HistoryFragment;
import com.leo.test.bookshelf.view.fragments.NewBookFragment;
import com.leo.test.bookshelf.view.fragments.SearchFragment;

import timber.log.Timber;

public class MainActivity extends BaseActivity {

    private ViewPager viewPager;
    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.color_white));
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setTitle(R.string.app_name_uppercases);

        viewPager = findViewById(R.id.viewpager);
        if (viewPager != null) {
            adapter = new ViewPagerAdapter(getSupportFragmentManager());
            adapter.addFragment(new NewBookFragment(), getString(R.string.tab_title_newbook));
            adapter.addFragment(new SearchFragment(), getString(R.string.tab_title_search));
            adapter.addFragment(new BookMarkFragment(), getString(R.string.tab_title_bookmark));
            adapter.addFragment(new HistoryFragment(), getString(R.string.tab_title_history));
            viewPager.setOffscreenPageLimit(3);
            viewPager.setAdapter(adapter);
            viewPager.setCurrentItem(0);

            TabLayout tabLayout = findViewById(R.id.tabs);
            tabLayout.setupWithViewPager(viewPager);
            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    Timber.d(">> onTabSelected : %s", tab.getPosition());
                    if (tab.getPosition() == 1) {
                    }
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {
                    Timber.d(">> onTabUnselected : %s", tab.getPosition());
                    if (tab.getPosition() == 1) {
                        KeyboardUtil.hideSoftKeyboard(MainActivity.this);
                    }
                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {
                }
            });
        }
    }
}
