package com.example.myapplication.Activities;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

public abstract class Page extends AppCompatActivity {
    protected void goToActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
        finish();
    }

    public void goHomePage() {
        goToActivity(HomePage.class);
    }

    public void goUserPage() {
        goToActivity(UserPage.class);
    }

    public void goTradePage() {
        goToActivity(TradePlatform.class);
    }

    public void goFavorite() {
        goToActivity(Favorite.class);
    }

}
