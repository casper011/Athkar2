package com.wiki.qablawi.ask.athkar.ui.ui.ui;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.google.android.material.navigation.NavigationView;
import com.wiki.qablawi.ask.athkar.R;
import com.wiki.qablawi.ask.athkar.ui.ui.viewmodel.ViewModel;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.dateTextView)
    TextView dateTextView;
    @BindView(R.id.dayTextView)
    TextView dayTextView;
    @BindView(R.id.array_count_text_view)
    TextView arrayCountTextView;
    @BindView(R.id.item_count_text_view)
    TextView itemCountTextView;
    @BindView(R.id.repeat)
    TextView repeat;
    @BindView(R.id.content)
    TextView contentTextView;
    @BindView(R.id.imageButton3)
    ImageButton imageButton;

    private DrawerLayout drawer;
    private ViewModel viewModel;

    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();

    }

    private void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        viewModel = ViewModelProviders.of(this).get(ViewModel.class);
        viewModel.getDataList().observe(this, new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {
                settingUpTexts(strings.size());
                contentTextView.setText(strings.get(count));
            }
        });
        dateTextView.setText(new SimpleDateFormat("dd-MMM-yyyy").format(Calendar.getInstance().getTime()));
        dayTextView.setText(getDayOfTheWeek(Calendar.getInstance().get(Calendar.DAY_OF_WEEK)));
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void settingUpTexts(int arraySize) {
        repeat.setText("0");
        arrayCountTextView.setText(String.valueOf(arraySize));
        itemCountTextView.setText(String.valueOf(1) + "/");
    }

    private String getDayOfTheWeek(int day) {
        String dayName = "somthing wrong happend";
        switch (day) {
            case 1:
                dayName = "الاحد";
                break;
            case 2:
                dayName = "االاثنين";
                break;
            case 3:
                dayName = "الثلاثاء";
                break;
            case 4:
                dayName = "الاربعاء";
                break;
            case 5:
                dayName = "الخميس";
                break;
            case 6:
                dayName = "الجمعه";
                break;
            case 7:
                dayName = "السبت";
                break;
        }
        return dayName;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(drawer)) {
            drawer.closeDrawer(drawer);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Toast.makeText(this, "asdasd", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public ImageButton getImageButton() {
        return imageButton;
    }
}
