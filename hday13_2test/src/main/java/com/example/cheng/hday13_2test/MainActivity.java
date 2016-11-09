package com.example.cheng.hday13_2test;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mButAdd;
    private Button mButRemove;
    private WindowManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initView();

        manager = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
    }

    private void initView() {
        mButAdd = (Button) findViewById(R.id.but_add);
        mButRemove = (Button) findViewById(R.id.but_remove);

        mButAdd.setOnClickListener(this);
        mButRemove.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.but_add:

                break;
            case R.id.but_remove:

                break;
        }
    }
}
