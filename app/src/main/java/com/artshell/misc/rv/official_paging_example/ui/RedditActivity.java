package com.artshell.misc.rv.official_paging_example.ui;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.artshell.misc.R;
import com.artshell.misc.rv.official_paging_example.ServiceLocator;
import com.artshell.misc.rv.official_paging_example.repository.GlideApp;
import com.artshell.misc.rv.official_paging_example.repository.GlideRequests;
import com.artshell.misc.rv.official_paging_example.repository.NetworkState;
import com.artshell.misc.rv.official_paging_example.repository.RedditPostRepository;

/**
 * A list activity that shows reddit posts in the given sub-reddit.
 * <p>
 * The intent arguments can be modified to make it use a different repository (see MainActivity).
 */
public class RedditActivity extends AppCompatActivity {
    public static String KEY_SUBREDDIT       = "subreddit";
    public static String DEFAULT_SUBREDDIT   = "androiddev";
    public static String KEY_REPOSITORY_TYPE = "repository_type";

    private LinearLayout mRoot;
    private TextInputLayout mInputLayout;
    private TextView mInput;
    private SwipeRefreshLayout mSwipeRefresh;
    private RecyclerView mList;
    private SubRedditViewModel model;

    public static Intent intentFor(Context context, RedditPostRepository.Type type) {
        Intent tent = new Intent(context, RedditActivity.class);
        tent.putExtra(KEY_REPOSITORY_TYPE, type.ordinal());
        return tent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reddit);
        findViews();
        model = getViewModel();
        initAdapter();
        initSwipeToRefresh();
        initSearch();

        if (savedInstanceState != null) {
            String subreddit = savedInstanceState.getString(KEY_SUBREDDIT, DEFAULT_SUBREDDIT);
            model.showSubreddit(subreddit);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_SUBREDDIT, model.currentSubreddit());
    }

    private SubRedditViewModel getViewModel() {
        return ViewModelProviders.of(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            @SuppressWarnings("unchecked")
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                Intent intent = getIntent();
                int repoTypeParam = intent.getIntExtra(KEY_REPOSITORY_TYPE, 0);
                RedditPostRepository.Type repoType = RedditPostRepository.Type.values()[repoTypeParam];
                RedditPostRepository repo = ServiceLocator.instance(RedditActivity.this).getRepository(repoType);
                return (T) new SubRedditViewModel(repo);
            }
        }).get(SubRedditViewModel.class);
    }

    private void findViews() {
        mRoot = findViewById(R.id.root);
        mInputLayout = findViewById(R.id.input_layout);
        mInput = findViewById(R.id.input);
        mSwipeRefresh = findViewById(R.id.swipe_refresh);
        mList = findViewById(R.id.list);
    }

    private void initAdapter() {
        GlideRequests requests = GlideApp.with(this);
        PostsAdapter adapter = new PostsAdapter(PostsAdapter.getCallback(), requests, () -> model.retry());
        mList.setAdapter(adapter);
        model.posts.observe(this, adapter::submitList);
        model.networkState.observe(this, adapter::setNetworkState);
    }

    private void initSwipeToRefresh() {
        model.refreshState.observe(this, state -> mSwipeRefresh.setRefreshing(state == NetworkState.LOADING));
        mSwipeRefresh.setOnRefreshListener(() -> model.refresh());
    }

    private void initSearch() {
        mInput.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_GO) {
                updatedSubredditFromInput();
                return true;
            }
            return false;
        });

        mInput.setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                updatedSubredditFromInput();
                return true;
            }
            return false;
        });
    }

    private void updatedSubredditFromInput() {
        String keyword = mInput.getText().toString().trim();
        if (!TextUtils.isEmpty(keyword)) {
            model.showSubreddit(keyword);
            mList.scrollToPosition(0);
            RecyclerView.Adapter adapter = mList.getAdapter();
            if (adapter != null) {
                ((PostsAdapter) adapter).submitList(null);
            }
        }
    }
}
