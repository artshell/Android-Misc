package com.artshell.misc.arch_mvp.fragment_lifecycle_state;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.artshell.misc.R;

public class FragmentLifecycleStateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_lifecycle_state);

        ViewPager container = findViewById(R.id.layout_container);
        TabLayout layout = findViewById(R.id.tab_container);
        container.setAdapter(new StatePager(getSupportFragmentManager()));
        layout.setupWithViewPager(container);
    }
}
