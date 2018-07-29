package com.artshell.misc.rv.official_paging_example.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.artshell.misc.R;
import com.artshell.misc.rv.official_paging_example.repository.RedditPostRepository;

/**
 * chooser activity for the demo.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reddit_main);
        findViewById(R.id.withDatabase).setOnClickListener(v -> show(RedditPostRepository.Type.DB));
        findViewById(R.id.networkOnly).setOnClickListener(v -> show(RedditPostRepository.Type.IN_MEMORY_BY_ITEM));
        findViewById(R.id.networkOnlyWithPageKeys).setOnClickListener(v -> show(RedditPostRepository.Type.IN_MEMORY_BY_PAGE));
    }

    private void show(RedditPostRepository.Type type) {
        Intent tent = RedditActivity.intentFor(this, type);
        startActivity(tent);
    }
}
