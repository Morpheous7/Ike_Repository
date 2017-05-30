package com.example.ike.gameapp.utils;

import android.content.Context;
import android.util.TypedValue;
/**
 * Created by Ike on 5/29/2017.
 */

public class PixelHelper {

    public static int pixelsToDp(int px, Context context) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, px,
                context.getResources().getDisplayMetrics());
    }

}