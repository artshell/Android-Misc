package com.artshell.misc.rv;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.recyclerview.extensions.AsyncListDiffer;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.artshell.misc.R;

import java.util.ArrayList;
import java.util.List;


public class AsyncListDifferTest extends AppCompatActivity {

    private Toolbar      mToolbar;
    private TextView     mTitle;
    private TextView     mDelete;
    private RecyclerView mContainer;
    private DifferAdapter mAdapter = new DifferAdapter();

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
            List<String> items = mAdapter.getItems();

            // 删除内容
            if (items.size() > 10) {
                List<String> newList = new ArrayList<>(items);
                newList.remove(1);
                newList.remove(2);
                mAdapter.submitList(newList);
            }
        });
    }

    private void fillData() {
        mContainer.setLayoutManager(new LinearLayoutManager(this));
        mContainer.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mContainer.setItemAnimator(new DefaultItemAnimator());
        mContainer.setAdapter(mAdapter);
        mAdapter.submitList(Cheese.DATAS_FULL);
    }

    /**
     * @see android.support.v7.recyclerview.extensions.ListAdapter
     */
    public static class DifferAdapter extends RecyclerView.Adapter<NameViewBinder.NameHolder> {

        @NonNull
        @Override
        public NameViewBinder.NameHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new NameViewBinder.NameHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_name_view, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull NameViewBinder.NameHolder holder, int position) {
            holder.tv.setText(getItem(position));
        }

        public List<String> getItems() {
            return differ.getCurrentList();
        }

        @Override
        public int getItemCount() {
            return differ.getCurrentList().size();
        }

        public String getItem(int position) {
            return getItems().get(position);
        }

        public void submitList(List<String> newList) {
            differ.submitList(newList);
        }

        public final AsyncListDiffer<String> differ = new AsyncListDiffer<>(this, DIFF_CALLBACK);

        public static final DiffUtil.ItemCallback<String> DIFF_CALLBACK = new DiffUtil.ItemCallback<String>() {
            @Override
            public boolean areItemsTheSame(String oldItem, String newItem) {
                return oldItem == newItem;
            }

            @Override
            public boolean areContentsTheSame(String oldItem, String newItem) {
                return oldItem.equals(newItem);
            }
        };
    }
}
