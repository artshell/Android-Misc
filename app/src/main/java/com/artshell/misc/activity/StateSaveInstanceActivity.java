package com.artshell.misc.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.artshell.misc.R;

/**
 * @see StateRetainObjectViewModelActivity
 */
public class StateSaveInstanceActivity extends AppCompatActivity implements TextWatcher {
    private static final String TAG = "SaveInstanceActivity";

    private EditText input;
    private TextView display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state_save_instance);
        setProperty(savedInstanceState);
    }

    private void setProperty(Bundle args) {
        input = findViewById(R.id.et_input);
        display = findViewById(R.id.tv_display);
        input.addTextChangedListener(this);
        if (args != null) {
            display.setText(args.getString(TAG, ""));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(TAG, display.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        display.setText(s);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
