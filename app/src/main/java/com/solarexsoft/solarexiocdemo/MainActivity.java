package com.solarexsoft.solarexiocdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.solarexsoft.solarexioc.InjectUtils;
import com.solarexsoft.solarexioc.annotation.ContentView;
import com.solarexsoft.solarexioc.annotation.OnClick;
import com.solarexsoft.solarexioc.annotation.ViewInject;

@ContentView(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Solarex";
    @ViewInject(R.id.tv)
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InjectUtils.inject(this);
        Log.d(TAG, "onCreate: tv = " + tv);
    }

    @OnClick({R.id.btn1, R.id.btn2})
    public void clickTest(View view) {
        int id = view.getId();
        if (id == R.id.btn1) {
            Log.d(TAG, "clickTest: btn1 clicked");
        } else if (id == R.id.btn2) {
            Log.d(TAG, "clickTest: btn2 clicked");
        }
    }
}
