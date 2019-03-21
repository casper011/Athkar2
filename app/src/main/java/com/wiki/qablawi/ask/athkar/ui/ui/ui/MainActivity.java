package com.wiki.qablawi.ask.athkar.ui.ui.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.google.android.material.navigation.NavigationView;
import com.wiki.qablawi.ask.athkar.R;
import com.wiki.qablawi.ask.athkar.ui.ui.animationutils.AnimationUtils;
import com.wiki.qablawi.ask.athkar.ui.ui.database.DatabaseHelper;
import com.wiki.qablawi.ask.athkar.ui.ui.viewmodel.ViewModel;

import androidx.appcompat.widget.PopupMenu;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
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
import butterknife.OnClick;
import butterknife.OnTouch;
import butterknife.Optional;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    //region BindingViews
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
    @BindView(R.id.back_image_button)
    ImageButton backImageButton;
    @BindView(R.id.next_image_button)
    ImageButton nextImageButton;
    @BindView(R.id.play_image_button)
    ImageButton playImageButton;
    @BindView(R.id.share_image_button)
    ImageButton shareImageButton;
    @BindView(R.id.content_constraint_layout)
    ConstraintLayout constraintLayout;
    //endregion

    //region VariablesNeeded
    private DatabaseHelper athkarDatabaseHelper;
    private ArrayList<String> items;
    private DrawerLayout drawer;
    private ViewModel viewModel;
    private int repeatCount = 0;
    private int count = 0;
    private final String PREFS_NAME = "UserPerfsFile";

    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();

    }

    private void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        final SharedPreferences preferences = getSharedPreferences(PREFS_NAME, 0);
        athkarDatabaseHelper = new DatabaseHelper(this);
        setSupportActionBar(toolbar);
        viewModel = ViewModelProviders.of(this).get(ViewModel.class);
        if (preferences.getBoolean("my_first_time", true)) {
            viewModel.getDataList().observe(this, new Observer<ArrayList<String>>() {
                @Override
                public void onChanged(ArrayList<String> strings) {
                    for (int i = 0; i < strings.size(); i++)
                        athkarDatabaseHelper.insertData(strings.get(i));
                    preferences.edit().putBoolean("my_first_time", false).commit();
                    viewAll();
                    contentTextView.setText(items.get(count));
                    settingUpTexts(items.size());
                }
            });
        }
        if (!preferences.getBoolean("my_first_time", true)) {
            viewAll();
            settingUpTexts(items.size());
            contentTextView.setText(items.get(count));
        }

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
        repeat.setText(String.valueOf(repeatCount));
        arrayCountTextView.setText("/" + String.valueOf(arraySize));
        itemCountTextView.setText(String.valueOf(1));
    }

    private void viewAll() {
        Cursor result = athkarDatabaseHelper.getAllData();
        items = new ArrayList<>();
        if (result.getCount() == 0) {
            Toast.makeText(this, "there is no data", Toast.LENGTH_SHORT).show();
        } else {
            StringBuffer buffer = new StringBuffer();
            while (result.moveToNext()) {
                items.add(result.getString(1));
            }
        }
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
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Optional
    @OnTouch({R.id.back_image_button, R.id.next_image_button, R.id.play_image_button, R.id.share_image_button})
    boolean onButtonsTouched(View view, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN)
            switch (view.getId()) {
                case R.id.back_image_button:
                    AnimationUtils.leftArrowButtonAnimation(backImageButton);
                    AnimationUtils.buttonAnimation(backImageButton);
                    if (count == 0) {
                        count = items.size() - 1;
                        contentTextView.setText(items.get(count));
                        itemCountTextView.setText(String.valueOf(count + 1));
                    } else {
                        contentTextView.setText(items.get(--count));
                        itemCountTextView.setText(String.valueOf(count + 1));
                    }
                    repeatCount = 0;
                    repeat.setText(String.valueOf(repeatCount));
                    break;
                case R.id.next_image_button:
                    AnimationUtils.rightArrowAnimation(nextImageButton);
                    AnimationUtils.buttonAnimation(nextImageButton);
                    if (count == items.size() - 1) {
                        count = 0;
                        contentTextView.setText(items.get(count));
                        itemCountTextView.setText(String.valueOf(count + 1));
                    } else {
                        contentTextView.setText(items.get(++count));
                        itemCountTextView.setText(String.valueOf(count + 1));
                    }
                    repeatCount = 0;
                    repeat.setText(String.valueOf(repeatCount));
                    break;
                case R.id.play_image_button:
                    Toast.makeText(this, "play button is clicked", Toast.LENGTH_SHORT).show();
                    AnimationUtils.buttonAnimation(playImageButton);
                    break;
                case R.id.share_image_button:
                    showPopup();
                    AnimationUtils.buttonAnimation(shareImageButton);
                    break;
            }
        else if (event.getAction() == MotionEvent.ACTION_UP) {
            switch (view.getId()) {
                case R.id.next_image_button:
                    AnimationUtils.removeRightArrowAnimation(nextImageButton);
                    AnimationUtils.removeButtonAnimation(nextImageButton);
                    break;
                case R.id.back_image_button:
                    AnimationUtils.removeLeftArrowButtonAnimation(backImageButton);
                    AnimationUtils.removeButtonAnimation(backImageButton);
                    break;
                case R.id.share_image_button:
                    AnimationUtils.removeButtonAnimation(shareImageButton);
                    break;
                case R.id.play_image_button:
                    AnimationUtils.removeButtonAnimation(playImageButton);
                    break;
            }
        }
        return true;
    }

    @Optional
    @OnClick(R.id.content_constraint_layout)
    void onClick(View view) {
        if (view.getId() == R.id.content_constraint_layout) {
            repeatCount++;
            repeat.setText(String.valueOf(repeatCount));
        }
    }

    public void showPopup() {
        PopupMenu popupMenu = new PopupMenu(getSupportActionBar().getThemedContext(), shareImageButton);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.facebook:
                        Toast.makeText(getApplicationContext(),"shared on facebook", Toast.LENGTH_SHORT ).show();
                        break;
                    case R.id.google:
                        Toast.makeText(getApplicationContext(), "shared on google", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.github:
                        Toast.makeText(getApplicationContext(), "shared on github", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
        popupMenu.getMenuInflater().inflate(R.menu.share_menu, popupMenu.getMenu());
        popupMenu.show();
    }
}