package com.artshell.misc.rv;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.artshell.misc.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import me.drakeet.multitype.MultiTypeAdapter;

public class DiffUtilTest extends AppCompatActivity {

    private Toolbar      mToolbar;
    private TextView     mTitle;
    private TextView     mDelete;
    private RecyclerView mContainer;
    private MultiTypeAdapter mAdapter = new MultiTypeAdapter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diff_util);

        mToolbar = findViewById(R.id.tv_bar);
        mTitle = findViewById(R.id.tv_title);
        mDelete = findViewById(R.id.tv_menu);
        mContainer = findViewById(R.id.rv_container);

        initView();
        fillData();
    }

    @SuppressWarnings("unchecked")
    private void initView() {
        mTitle.setText("DiffUtilTest");

        mDelete.setOnClickListener(v -> {
            List<String> oldList = new ArrayList<>((Collection<String>) mAdapter.getItems());

            // 模拟删除内容
            List<String> newList = new ArrayList<>(oldList);
            newList.remove(0);
            newList.remove(2);
            newList.remove(4);

            // 计算不同项(数据量较大时, 最好异步计算)
            // 参考 AsyncListDiffer, AsyncDifferConfig
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffCallback(oldList, newList));
            mAdapter.setItems(newList);
            diffResult.dispatchUpdatesTo(mAdapter);
        });
    }

    private void fillData() {
        mAdapter.register(String.class, new NameViewBinder());
        mAdapter.setItems(Cheese.DATAS_FULL);
        mContainer.setLayoutManager(new LinearLayoutManager(this));
        mContainer.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mContainer.setItemAnimator(new DefaultItemAnimator());
        mContainer.setAdapter(mAdapter);
    }

    public static class DiffCallback extends DiffUtil.Callback {
        private List<String> oldList;
        private List<String> newList;

        public DiffCallback(List<String> oldList, List<String> newList) {
            this.oldList = oldList;
            this.newList = newList;
        }

        @Override
        public int getOldListSize() {
            return oldList == null ? 0 : oldList.size();
        }

        @Override
        public int getNewListSize() {
            return newList == null ? 0 : newList.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return oldList.get(oldItemPosition) == newList.get(newItemPosition);
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
        }
    }
}
