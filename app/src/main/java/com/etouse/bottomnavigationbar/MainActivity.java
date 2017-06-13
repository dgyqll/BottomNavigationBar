package com.etouse.bottomnavigationbar;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import com.ashokvarma.bottomnavigation.BadgeItem;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener {

    private BottomNavigationBar bottomNavigationBar;
    private FrameLayout flContent;
    private List<String> titles = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();
    private Fragment conversationFragment;
    private Fragment contactFragment;
    private Fragment pluginFragment;
    private BottomNavigationItem conversationItem;
    private BottomNavigationItem contactItem;
    private BottomNavigationItem pluginItem;
    private BadgeItem conversationBadgeItem;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        flContent = (FrameLayout) findViewById(R.id.fl_content);
        initData();
        initNavigationBar();
    }

    private void initData() {
        titles.add("会话");
        titles.add("联系人");
        titles.add("动态");

        conversationFragment = new ConversationFragment();
        contactFragment = new ContactFragment();
        pluginFragment = new PluginFragment();

        fragments.add(conversationFragment);
        fragments.add(contactFragment);
        fragments.add(pluginFragment);

    }

    private void initNavigationBar() {
        //初始化导航栏条目
        conversationItem = new BottomNavigationItem(R.mipmap.conversation_selected_2, titles.get(0));
        contactItem = new BottomNavigationItem(R.mipmap.contact_selected_2, titles.get(1));
        pluginItem = new BottomNavigationItem(R.mipmap.plugin_selected_2, titles.get(2));

        //初始化边缘数字条目
        conversationBadgeItem = new BadgeItem();
        conversationBadgeItem.setGravity(Gravity.RIGHT | Gravity.TOP);
        conversationBadgeItem.setText("3");
        conversationItem.setBadgeItem(conversationBadgeItem);
        conversationBadgeItem.show();

        bottomNavigationBar.addItem(conversationItem);
        bottomNavigationBar.addItem(contactItem);
        bottomNavigationBar.addItem(pluginItem);

        bottomNavigationBar.setActiveColor(R.color.active);
        bottomNavigationBar.setInActiveColor(R.color.inactive);
        bottomNavigationBar.initialise();

        bottomNavigationBar.setTabSelectedListener(this);

        //初始化第一个显示的Fragment
        if (conversationFragment.isAdded()) {
            getSupportFragmentManager().beginTransaction().remove(fragments.get(0)).commit();
        }
        getSupportFragmentManager().beginTransaction().add(R.id.fl_content,fragments.get(0),"0").commit();
    }

    /**
     * 底部导航栏选中
     * @param position
     */
    @Override
    public void onTabSelected(int position) {
        if (fragments.get(position).isAdded()) {
            getSupportFragmentManager().beginTransaction().show(fragments.get(position)).commit();
        } else {
            getSupportFragmentManager().beginTransaction().add(R.id.fl_content,fragments.get(position),position + "").commit();
        }

    }

    /**
     * 取消选中
     * @param position
     */
    @Override
    public void onTabUnselected(int position) {
        getSupportFragmentManager().beginTransaction().hide(fragments.get(position)).commit();
    }

    @Override
    public void onTabReselected(int position) {

    }
}
