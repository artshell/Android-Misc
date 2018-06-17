package com.artshell.misc;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.drakeet.multitype.ItemViewBinder;
import me.drakeet.multitype.MultiTypeAdapter;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private List<Object> mItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setProperty();
    }

    private void setProperty() {
        RecyclerView container = findViewById(R.id.layout_container);
        MultiTypeAdapter adapter = new MultiTypeAdapter(mItems);
        adapter.register(String.class, new ActivityBinder<>(getPackageManager()));
        adapter.register(ResolveInfo.class, new ActivityBinder<>(getPackageManager()));
        container.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        container.setAdapter(adapter);
        findCorrectActivity();
        adapter.notifyDataSetChanged();
    }

    /**
     * 查找所有Activity
     * https://stackoverflow.com/questions/23671165/get-all-activities-by-using-package-name
     */
    private void findAllActivity() {
        try {
            PackageInfo pi = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_ACTIVITIES);
            ActivityInfo[] infos = pi.activities;
            // ...
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查找指定Activity
     * https://stackoverflow.com/questions/23671165/get-all-activities-by-using-package-name
     */
    private void findCorrectActivity() {
        Intent correct = new Intent(Intent.ACTION_MAIN, null);
        correct.addCategory(Intent.CATEGORY_DEFAULT);
        List<ResolveInfo> resolves = getPackageManager().queryIntentActivities(correct, PackageManager.GET_RESOLVED_FILTER);
        int descActivity = -1;
        int descService = -1;
        for (ResolveInfo info : resolves) {
            int descRes = info.activityInfo.descriptionRes;
            if (descRes == 0) continue; /* no set, so skip */
            if (descRes == R.string.activity && descActivity == -1) {
                mItems.add(getString(R.string.activity));
                descActivity = 0; /* found */
            }
            if (descRes == R.string.service && descService == -1) {
                mItems.add(getString(R.string.service));
                descService = 0; /* found */
            }
            mItems.add(info);
        }
    }

    private static class ActivityBinder<T> extends ItemViewBinder<T, ActivityBinder.NameViewHolder> {
        private PackageManager mManager;

        ActivityBinder(PackageManager manager) {
            mManager = manager;
        }

        @NonNull
        @Override
        protected NameViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
            return new NameViewHolder(inflater.inflate(R.layout.item_main_activity_name, parent, false));
        }

        @Override
        protected void onBindViewHolder(@NonNull NameViewHolder holder, @NonNull T item) {
            if (item instanceof String) {
                holder.name.setText((String) item);
            } else if (item instanceof ResolveInfo){
                ResolveInfo info = (ResolveInfo) item;
                CharSequence sequence = info.activityInfo.loadLabel(mManager);
                holder.name.setText(sequence);
            }
        }

        static class NameViewHolder extends RecyclerView.ViewHolder {
            TextView name;
            NameViewHolder(View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.tv_name);
            }
        }
    }
}
