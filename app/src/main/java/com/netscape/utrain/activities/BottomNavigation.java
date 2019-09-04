package com.netscape.utrain.activities;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.netscape.utrain.R;
import com.netscape.utrain.fragments.ChatsFragment;
import com.netscape.utrain.fragments.EditorFragment;
import com.netscape.utrain.fragments.HomeFragment;
import com.netscape.utrain.fragments.NotificationFragment;
import com.netscape.utrain.fragments.StardFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.MenuItem;
import android.widget.TextView;

public class BottomNavigation extends AppCompatActivity {
    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
//                    mTextMessage.setText(R.string.title_home);
                    HomeFragment home=new HomeFragment();
                    loadFragment(home);
                    return true;
                case R.id.navigation_stard:
//                    mTextMessage.setText(R.string.title_dashboard);
                    StardFragment stard=new StardFragment();
                    loadFragment(stard);
                    return true;
                case R.id.navigation_edit:
//                    mTextMessage.setText(R.string.title_notifications);
                    EditorFragment edit=new EditorFragment();
                    loadFragment(edit);
                    return true;
                case R.id.navigation_chat:
//                    mTextMessage.setText(R.string.title_notifications);
                    ChatsFragment chat=new ChatsFragment();
                    loadFragment(chat);
                    return true;
                case R.id.navigation_notifications:
//                    mTextMessage.setText(R.string.title_notifications);
                    NotificationFragment notification =new NotificationFragment();
                    loadFragment(notification);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction  transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
