package com.artshell.misc.rv.paging;

import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.AsyncPagedListDiffer;
import android.arch.paging.PagedList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.artshell.misc.R;
import com.artshell.misc.arch.entity.User;

public class AsyncPagedListDifferTest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paged_list_differ);

        TextView title = findViewById(R.id.tv_title);
        title.setText("AsyncPagedListDifferTest");

        RecyclerView rv = findViewById(R.id.rv_container);
        UserAdapter adapter = new UserAdapter();
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

        UserViewModel viewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        viewModel.getUserDatas().observe(this, pagedList -> adapter.submitList(pagedList));
    }

    /**
     * @see android.arch.paging.PagedListAdapter
     */
    class UserAdapter extends RecyclerView.Adapter<UserViewHolder> {
        private final AsyncPagedListDiffer<User> mDiffer = new AsyncPagedListDiffer<>(this, new UserDiffCallback());

        @NonNull
        @Override
        public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new UserViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_view, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
            User item = getItem(position);
            holder.name.setText(item.name);
            holder.lastName.setText(item.lastName);
        }

        public void submitList(PagedList<User> newPagedList) {
            mDiffer.submitList(newPagedList);
        }

        @Override
        public int getItemCount() {
            return mDiffer.getItemCount();
        }

        private User getItem(int pos) {
            return mDiffer.getItem(pos);
        }
    }
}
