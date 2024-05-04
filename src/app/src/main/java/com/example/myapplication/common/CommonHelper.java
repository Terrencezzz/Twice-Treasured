package com.example.myapplication.common;

import android.content.Context;
import android.widget.Toast;

public class CommonHelper {

    public static void showToast(Context context,String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
