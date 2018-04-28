package it.hueic.kenhoang.fgshopapp.view.login.signin;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import it.hueic.kenhoang.fgshopapp.R;
import it.hueic.kenhoang.fgshopapp.common.Common;
import it.hueic.kenhoang.fgshopapp.custom.EmailEditTextCustom;
import it.hueic.kenhoang.fgshopapp.custom.PasswordEditTextCustom;
import it.hueic.kenhoang.fgshopapp.model.signin.ModelLogin;
import it.hueic.kenhoang.fgshopapp.object.User;
import it.hueic.kenhoang.fgshopapp.presenter.login.fragment.signin.PresenterLogicSignIn;
import it.hueic.kenhoang.fgshopapp.utils.Utils;
import it.hueic.kenhoang.fgshopapp.view.home.HomeActivity;

/**
 * Created by kenhoang on 02/03/2018.
 */

public class FragmentSignIn extends Fragment implements View.OnClickListener,
        View.OnFocusChangeListener,
        IViewSignIn {
    private static final String TAG = FragmentSignIn.class.getSimpleName();
    private static final int RC_SIGN_IN = 9001;
    Button btnFacebook, btnGoogle, btnLogin;
    TextInputLayout inputEmail, inputPass;
    EmailEditTextCustom edEmail;
    PasswordEditTextCustom edPass;
    CallbackManager callbackManager;
    private GoogleSignInClient mGoogleSignInClient;
    ModelLogin modelLogin = new ModelLogin();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        //Init Facebook
        FacebookSdk.sdkInitialize(getContext().getApplicationContext());
        AppEventsLogger.activateApp(getContext());
        //Init Google
        mGoogleSignInClient = modelLogin.getGoogleSignInClient(getContext());
        //Facebook
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Intent homeIntent = new Intent(getActivity(), HomeActivity.class);
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "onCancel: ");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "onError: ");
            }
        });
        //InitView
        initView(view);
        //InitEvent
        initEvent();
        //hashKeyFacebook();
        return view;
    }

    private void initEvent() {
        btnFacebook.setOnClickListener(this);
        btnGoogle.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

        edEmail.setOnFocusChangeListener(this);
    }

    private void initView(View view) {
        btnFacebook = view.findViewById(R.id.btnFacebook);
        btnGoogle = view.findViewById(R.id.btnGoogle);
        btnLogin = view.findViewById(R.id.btnLogin);

        inputEmail = view.findViewById(R.id.inputEmail);
        inputPass = view.findViewById(R.id.inputPass);

        edEmail = view.findViewById(R.id.edEmail);
        edPass = view.findViewById(R.id.edPassword);
    }

    private void hashKeyFacebook() {
        // Add code to print out the key hash
        try {
            PackageInfo info = getActivity().getPackageManager().getPackageInfo(
                    "it.hueic.kenhoang.lazada_clone_app",
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

    // [START onClick]
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btnFacebook:
                LoginManager.getInstance().logInWithReadPermissions(FragmentSignIn.this, Arrays.asList("public_profile", "email"));
                break;
            case R.id.btnGoogle:
                signInGoogle();
                break;
            case R.id.btnLogin:
                handleLoginWithText();
                break;
        }
    }

    private void handleLoginWithText() {
        String email = edEmail.getText().toString();
        String pass = edPass.getText().toString();
        PresenterLogicSignIn presenterLogicSignIn = new PresenterLogicSignIn(this);
        if (!email.trim().equals("") && !pass.trim().equals(""))
            presenterLogicSignIn.validateLogin(email, pass);
        else
            Utils.showSnackBarShort(getView().findViewById(R.id.layoutMainSignIn), "Please fill full information");
    }
    // [END onClick]

    // [START onActivityResult]
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            modelLogin.handleSignInResult(task, getActivity(), TAG);
        }
    }
    // [END onActivityResult]

    // [START signIn]
    private void signInGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.edEmail:
                if (!hasFocus) {
                    String data = ((EmailEditTextCustom)v).getText().toString();
                    Boolean isEmail = Patterns.EMAIL_ADDRESS.matcher(data).matches();
                    if (data.trim().equals("") || data == null) {
                        inputEmail.setErrorEnabled(true);
                        inputEmail.setError("Please fill your email");
                    } else {
                        if (isEmail) {
                            inputEmail.setErrorEnabled(false);
                            inputEmail.setError("");
                        } else {
                            inputEmail.setErrorEnabled(true);
                            inputEmail.setError("Not an email");
                        }

                    }
                }
                break;
        }
    }

    @Override
    public void loginSuccess(User user) {
        Common.CURRENT_USER = user;
      /*  Gson gson = new Gson();
        String json = gson.toJson(user);*/

       /* SharedPreferences cacheUser = getContext().getSharedPreferences(Common.CHECK_LOGIN_API, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = cacheUser.edit();
        editor.putString("USER", json);
        editor.commit();*/

        Intent homeIntent = new Intent(getContext(), HomeActivity.class);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }

    @Override
    public void loginFailed(String msg) {
        Utils.showSnackBarShort(getView().findViewById(R.id.layoutMainSignIn), msg);
    }
    // [END signIn]
}
