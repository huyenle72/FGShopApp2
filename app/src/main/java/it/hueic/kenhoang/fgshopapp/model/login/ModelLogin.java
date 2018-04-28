package it.hueic.kenhoang.fgshopapp.model.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import it.hueic.kenhoang.fgshopapp.common.Common;
import it.hueic.kenhoang.fgshopapp.connect.ConnectAPI;
import it.hueic.kenhoang.fgshopapp.helper.ParseHelper;
import it.hueic.kenhoang.fgshopapp.object.User;
import it.hueic.kenhoang.fgshopapp.view.home.HomeActivity;

/**
 * Created by kenhoang on 03/03/2018.
 */

public class ModelLogin {

    private static final String TAG = ModelLogin.class.getSimpleName();

    AccessToken accessToken;
    AccessTokenTracker accessTokenTracker;

    /**
     * validate_login
     * @param username
     * @param password
     * @return
     */
    public User validateLogin(String username, String password) {
        User user = new User();
        String data = "";
        int status = 0;

        List<HashMap<String,String>> attrs = new ArrayList<>();

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("controller", Common.USER);
        attrs.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("action", Common.LOGIN);
        attrs.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("username", username);
        attrs.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("password", password);
        attrs.add(hashMap);

        ConnectAPI connect = new ConnectAPI(Common.URL_API, attrs);
        connect.execute();
        try {
            data = connect.get().get(0);
            status = Integer.parseInt(connect.get().get(1));
            user = ParseHelper.parseUser(data, status, username, password);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return user;
    }

    public User register(User user) {
        User userOrigin = new User();
        String data = "";
        int status = 0;

        List<HashMap<String, String>> attrs = new ArrayList<>();

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("controller", Common.USER);
        attrs.add(hashMap);
        hashMap = new HashMap<>();
        hashMap.put("action", Common.REGISTER);
        attrs.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", user.getName());
        attrs.add(hashMap);
        hashMap = new HashMap<>();
        hashMap.put("username", user.getUsername());
        attrs.add(hashMap);
        hashMap = new HashMap<>();
        hashMap.put("password", user.getPassword());
        attrs.add(hashMap);
        hashMap = new HashMap<>();
        hashMap.put("birthdate", user.getBirthdate());
        attrs.add(hashMap);
        hashMap = new HashMap<>();
        hashMap.put("phone", user.getPhone());
        attrs.add(hashMap);
        hashMap = new HashMap<>();
        hashMap.put("gender", user.getGender());
        attrs.add(hashMap);
        hashMap = new HashMap<>();
        hashMap.put("identify_number", user.getIdentify_number());
        attrs.add(hashMap);
        hashMap = new HashMap<>();
        hashMap.put("wallet", String.valueOf(user.getWallet()));
        attrs.add(hashMap);
        hashMap = new HashMap<>();
        hashMap.put("is_social", user.getIs_social());
        attrs.add(hashMap);
        hashMap = new HashMap<>();
        hashMap.put("status", user.getStatus());
        attrs.add(hashMap);

        ConnectAPI connect = new ConnectAPI(Common.URL_API, attrs);
        connect.execute();
        try {
            data = connect.get().get(0);
            status = Integer.parseInt(connect.get().get(1));

            userOrigin = ParseHelper.parseUser(data, status, user.getUsername(), user.getPassword());

            Log.d(TAG, "status: " + status + " register: " + data);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return userOrigin;
    }

    public int isExists(String username) {
        int status = 0;

        List<HashMap<String,String>> attrs = new ArrayList<>();

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("controller", Common.USER);
        attrs.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("action", Common.EXISTS);
        attrs.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("username", username);
        attrs.add(hashMap);

        ConnectAPI connect = new ConnectAPI(Common.URL_API, attrs);
        connect.execute();
        try {
            status = Integer.parseInt(connect.get().get(1));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return status;
    }

    public AccessToken getCurrentTokenFacebook() {
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                accessToken = currentAccessToken;
            }
        };
        accessToken = AccessToken.getCurrentAccessToken();
        return accessToken;
    }

    public void destroyTokenTracker() {
        accessTokenTracker.stopTracking();
    }

    // [START getGoogleSignInClient]

    public GoogleSignInClient getGoogleSignInClient(Context context) {
        GoogleSignInClient mGoogleSignInClient;
        //Init Google SDK
        // [START configure_signin]
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // [END configure_signin]
        // [START build_client]
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(context, gso);
        // [END build_client]
        return mGoogleSignInClient;
    }
    // [END getGoogleSignInClient]

    public GoogleSignInAccount getAccountGoogle(Context context) {
        // [START on_start_sign_in]
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(context);
        // [END on_start_sign_in]
        return account;
    }

    // [START handleSignInResult]
    public void handleSignInResult(Task<GoogleSignInAccount> completedTask, Activity activity, String TAG) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            if (account != null) {
                Intent homeIntent = new Intent(activity, HomeActivity.class);
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                activity.startActivity(homeIntent);
            }
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
        }
    }
    // [END handleSignInResult]

    // [START signOut]
    public void signOutGoogle(GoogleSignInClient mGoogleSignInClient, Activity activity) {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(activity, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // [START_EXCLUDE]
                        updateUI(null);
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END signOut]

    public void updateUI(@Nullable GoogleSignInAccount account) {
        if (account != null) {

        } else {

        }
    }

    /**
     * Disconnect Google
     */
    // [START revokeAccess]
    public void revokeAccess(GoogleSignInClient mGoogleSignInClient, Activity activity) {
        mGoogleSignInClient.revokeAccess()
                .addOnCompleteListener(activity, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // [START_EXCLUDE]
                        updateUI(null);
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END revokeAccess]

    public User getCacheUserIsLogged(Context context) {
        /*Gson gson = new Gson();
        SharedPreferences cacheLogin = context.getSharedPreferences(Common.CHECK_LOGIN_API, Context.MODE_PRIVATE);
        String json = cacheLogin.getString("USER", "");*/
        User user = null;//gson.fromJson(json, User.class);
        return user;
    }
}
