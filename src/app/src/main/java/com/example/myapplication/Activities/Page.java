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
        goToActivity(Post.class);
    }

    /**
     * You can move to Favorite page with this function.
     */
    public void goFavorite() {
        goToActivity(FavoritePage.class);
    }

    /**
     * You can move to Private Message Menu with this function
     */
    public void goPrivateMenu() { goToActivity(PrivateMenuActivity.class);}

    /**
     * You can move to Login Page with this function
     * */
    public void goLoginPage(){ goToActivity(LoginPage.class);}

    /**
     *  You can move to Register Page with this function
     * */
    public void goRegisterPage(){ goToActivity(RegisterActivity.class);}

    /**
     *  You can move to UserDetail Page with this function
     * */
    public void goUserDetailPage(){goToActivity(UserDetailPage.class);}

    /**
     *  You can move to Notification Page with this function
     * */
    public void goNotification(){goToActivity(Notification.class);}

    /**
     *  You can move to Intro Page with this function
     * */
    public void goIntroPage(){goToActivity(IntroPage.class);}
}
