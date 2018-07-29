package com.artshell.misc.rv.official_paging_example.ui;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.artshell.misc.R;
import com.artshell.misc.rv.official_paging_example.db.RedditPost;
import com.artshell.misc.rv.official_paging_example.repository.GlideRequests;

/**
 * A RecyclerView ViewHolder that displays a reddit post.
 */
public class RedditPostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private GlideRequests glide;
    private TextView title, subtitle, score;
    private ImageView  thumbnail;
    @Nullable private RedditPost mPost;

    public RedditPostViewHolder(View itemView, GlideRequests glideRequest) {
        super(itemView);
        glide = glideRequest;
        title = itemView.findViewById(R.id.title);
        subtitle = itemView.findViewById(R.id.subtitle);
        score = itemView.findViewById(R.id.score);
        thumbnail = itemView.findViewById(R.id.thumbnail);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (mPost != null && mPost.url != null) {
            Intent tent = new Intent(Intent.ACTION_VIEW, Uri.parse(mPost.url));
            v.getContext().startActivity(tent);
        }
    }

    public void bind(@Nullable RedditPost post) {
        mPost = post;
        if (post != null) {
            title.setText(TextUtils.isEmpty(post.title) ? "loading" : post.title);
            String text = subtitle
                    .getResources()
                    .getString(R.string.post_subtitle, TextUtils.isEmpty(post.author) ? "unknown" : post.author);
            subtitle.setText(text);
            score.setText(String.valueOf(post.score));

            if (!TextUtils.isEmpty(post.thumbnail) && post.thumbnail.startsWith("http")) {
                thumbnail.setVisibility(View.VISIBLE);
                glide.load(post.thumbnail)
                        .centerCrop()
                        .placeholder(R.drawable.ic_launcher_background)
                        .into(thumbnail);
            } else {
                thumbnail.setVisibility(View.GONE);
                glide.clear(thumbnail);
            }
        }
    }

    public void updateScore(RedditPost item) {
        mPost = item;
        if (mPost != null) {
            score.setText(mPost.score);
        }
    }

    public static RedditPostViewHolder create(ViewGroup parent, GlideRequests glide) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reddit_post_item, parent, false);
        return new RedditPostViewHolder(view, glide);
    }
}
