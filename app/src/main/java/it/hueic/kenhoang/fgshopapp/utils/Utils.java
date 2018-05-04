package it.hueic.kenhoang.fgshopapp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.design.widget.Snackbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import com.valdesekamdem.library.mdtoast.MDToast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import it.hueic.kenhoang.fgshopapp.common.Common;
import it.hueic.kenhoang.fgshopapp.view.login.LoginActivity;

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

    /** Key Hash **/
    public static void hashKeyFacebook(Activity activity) {
        // Add code to print out the key hash
        try {
            PackageInfo info = activity.getPackageManager().getPackageInfo(
                    "it.hueic.kenhoang.fgshopapp",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }

    /** Check User Login **/
    public static boolean isLogin() {
        if (Common.CURRENT_USER != null) return true;
        return false;
    }

    /** Open Activity Login **/
    public static void openLogin(Activity activity) {
        Intent loginIntent = new Intent(activity, LoginActivity.class);
        activity.startActivity(loginIntent);
    }
}
