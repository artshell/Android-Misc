package com.artshell.effects.drag_dismiss;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.artshell.effects.R;
import com.artshell.effects.drag_dismiss.ElasticDragDismissFrameLayout.ElasticDragDismissCallback;

public class ElasticDragDismissActivity extends AppCompatActivity
        implements ElasticDragDismissCallback {

    private ElasticDragDismissFrameLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elastic_drag_dismiss);

        layout = findViewById(R.id.layout_drag_dismiss);
        layout.addListener(this);
    }

    @Override
    public void onDrag(float elasticOffset, float elasticOffsetPixels,
                       float rawOffset, float rawOffsetPixels) {

    }

    @Override
    public void onDragDismissed() {
        layout.removeListener(this);
        finish();
    }
}
