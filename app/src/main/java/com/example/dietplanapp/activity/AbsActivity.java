package com.example.dietplanapp.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dietplanapp.AppContext;
import com.example.dietplanapp.R;

public abstract class AbsActivity extends AppCompatActivity {

    protected Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //setStatusBar();
        setContentView(getLayoutId());
        mContext = this;
        main(savedInstanceState);

    }

    protected abstract int getLayoutId();

    protected void main(Bundle savedInstanceState) {
        AppContext.sInstance.addActiviy(this);
        main();
    }



    protected void main() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppContext.sInstance.removeActivity(this);

    }

    protected void setTitle(String title) {
        TextView titleView = (TextView) findViewById(R.id.titleView);
        if (titleView != null) {
            titleView.setText(title);
        }
    }

    public void backClick(View v) {
        if (v.getId() == R.id.btn_back) {
            onBackPressed();
        }
    }


    /**
     * Set the transparent status bar
     */

    private void setStatusBar() {
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(0);
    }
}
