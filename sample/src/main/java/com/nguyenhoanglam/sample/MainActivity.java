package com.nguyenhoanglam.sample;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.nguyenhoanglam.progresslayout.ProgressLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Progress Layout");

        ProgressLayout progressLayout = (ProgressLayout) findViewById(R.id.progressLayout);

        // Show progress layout and keep main views visible
        // skipIds is a list of view's ids which you want to show with ProgressLayout (in this case is the Toobar)
        List<Integer> skipIds = new ArrayList<>();
        skipIds.add(R.id.toolbar);

        progressLayout.showLoading(skipIds);
        progressLayout.showEmpty(ContextCompat.getDrawable(this, R.drawable.ic_empty), "Empty data",skipIds);
        progressLayout.showError(ContextCompat.getDrawable(this, R.drawable.ic_no_connection), "No connection", "RETRY", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Reloading...", Toast.LENGTH_SHORT).show();
            }
        },skipIds);


          // Show progress layout, hide all main views
//        progressLayout.showLoading();
//        progressLayout.showEmpty(ContextCompat.getDrawable(this, R.drawable.ic_empty), "Empty data");
//        progressLayout.showError(ContextCompat.getDrawable(this, R.drawable.ic_no_connection), "No connection", "RETRY", new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(MainActivity.this, "Reloading...", Toast.LENGTH_SHORT).show();
//            }
//        });

    }
}
