package com.example.myapplication.Activities;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;


/**
 * All the page can extend this page(maybe except PrivateChat)).
 */
public abstract class Page extends AppCompatActivity {
    protected void goToActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
        finish();
    }

    /**
     * You can move to HomePage with this function.
     */
    public void goHomePage() {
        goToActivity(HomePage.class);
    }

    /**
     * You can move to UserPage with this function.
     */
    public void goUserPage() {
        goToActivity(UserPage.class);
    }

    /**
     * You can move to TradePlatform with this function.
     */
    public void goTradePage() {
        goToActivity(TradePlatform.class);
    }

    /**
     * You can move to Favorite page with this function.
     */
    public void goFavorite() {
        goToActivity(Favorite.class);
    }

}
