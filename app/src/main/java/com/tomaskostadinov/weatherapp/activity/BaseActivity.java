package com.tomaskostadinov.weatherapp.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.tomaskostadinov.weatherapp.R;
import com.tomaskostadinov.weatherapp.helper.NotificationHelper;
import com.tomaskostadinov.weatherapp.helper.WeatherHelper;

/**
 * Created by Tomas Kostadinov on 08.08.2015
 * BaseActivity extends AppCompatActivity
 * Use BaseActivity always when accessing API Data from Remote Server
 */
public class BaseActivity extends AppCompatActivity {


    // ==============================================
    /**
     * IMPORTANT: ADD YOUR OPENWEATHERMAPS API KEY HERE
     */
    public String ApiKey = "3d5c69a27e2610114292a4aea7add97b";

    // ==============================================
    public Toolbar mToolbar;
    public TextView currloc;
    public DrawerLayout drawerLayout;
    public NavigationView navView;
    public static final String PREFS_NAME = "weatherapp";

    public AlertDialog.Builder changelog;
    public SharedPreferences SharedPreferences;
    public NotificationHelper NotificationHelper;
    public WeatherHelper WeatherHelper;
    public int Theme;

    public void setupTheme(){
        SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        switch (Integer.parseInt(SharedPreferences.getString("theme", "3"))){
            case 1:
                Theme = R.style.GreenTheme;
                break;
            case 2:
                Theme = R.style.MyMaterialTheme;
                break;
            case 3:
                Theme = R.style.BlueTheme;
                break;
            case 4:
                Theme = R.style.BlueTheme;
                break;
        }
    }

    public void setupToolbar(){
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
    }

    public void setToolbarBackIcon(){
        mToolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void setupNavigationDrawer(){
        if (mToolbar != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            mToolbar.setNavigationIcon(R.drawable.ic_menu);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            });
        }
        currloc = (TextView)findViewById(R.id.current_location);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.home:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            }
                        }, 250);
                        return true;
                    case R.id.place:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(new Intent(getApplicationContext(), DailyForecastActivity.class));
                            }
                        }, 250);
                        return true;
                    case R.id.help:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent i = new Intent(Intent.ACTION_VIEW);
                                Uri data = Uri.parse("mailto:tomas.kostadinov@gmx.de?subject=I need help with your weather app!");
                                i.setData(data);
                                startActivity(i);
                            }
                        }, 250);
                        return true;
                    case R.id.about:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(new Intent(getApplicationContext(), AboutActivity.class));
                            }
                        }, 250);
                        return true;
                    case R.id.settings:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                            }
                        }, 250);
                        return true;/*
                    case R.id.beta:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(new Intent(getApplicationContext(), BetaSettingsActivity.class));
                            }
                        }, 250);
                        return true;
                    case R.id.tomorrow:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(new Intent(getApplicationContext(), ForecastActivity.class));
                            }
                        }, 250);*/
                    case R.id.changelog:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                showChangeLog();
                            }
                        }, 250);
                        return true;
                }
                return true;
            }
        });
    }
    public void showChangeLog(){
        SharedPreferences = getSharedPreferences(PREFS_NAME, 0);
        changelog = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);
        changelog.setTitle("Neu in " + getResources().getString(R.string.versionDesc));
        changelog.setMessage(Html.fromHtml(
                "- Wöchentliche Vorschau redesignt<br/>" +
                "- Home Screen: Farbe ändert sich mit Wetter<br/>" +
                "- Kleine Verbesserungen, Bugfixes<br/><br/>" +
                "<b>Bitte alle Bugs + Fehler melden!<br/> Feature Requests sind erwünscht!</b>"));
        changelog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences.Editor editor = SharedPreferences.edit();
                editor.putBoolean("updatenews4", true);
                editor.apply();
            }
        });
        changelog.show();
    }
}
