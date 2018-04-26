package it.hueic.kenhoang.fgshopapp.utils;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.valdesekamdem.library.mdtoast.MDToast;

public class Utils {
    /** Show MDToast **/
    public static void showToastShort(Context context, String msg, int type) {
        MDToast.makeText(context, msg, MDToast.LENGTH_SHORT, type).show();
    }

    /** Show MDToast **/
    public static void showToastLong(Context context, String msg, int type) {
        MDToast.makeText(context, msg, MDToast.LENGTH_LONG, type).show();
    }

    /** Show snack bar **/
    public static void showSnackBarShort(View view, String msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show();
    }

    /** Show snack bar **/
    public static void showSnackBarLong(View view, String msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG).show();
    }
}
