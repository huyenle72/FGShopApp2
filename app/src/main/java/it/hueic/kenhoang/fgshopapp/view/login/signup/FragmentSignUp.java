package it.hueic.kenhoang.fgshopapp.view.login.signup;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import dmax.dialog.SpotsDialog;
import io.paperdb.Paper;
import it.hueic.kenhoang.fgshopapp.R;
import it.hueic.kenhoang.fgshopapp.common.Common;
import it.hueic.kenhoang.fgshopapp.custom.EmailEditTextCustom;
import it.hueic.kenhoang.fgshopapp.custom.PasswordEditTextCustom;
import it.hueic.kenhoang.fgshopapp.object.User;
import it.hueic.kenhoang.fgshopapp.presenter.login.fragment.signin.PresenterLogicSignIn;
import it.hueic.kenhoang.fgshopapp.presenter.login.fragment.signup.PresenterLogicSignUp;
import it.hueic.kenhoang.fgshopapp.utils.Utils;
import it.hueic.kenhoang.fgshopapp.view.home.HomeActivity;
import it.hueic.kenhoang.fgshopapp.view.login.signin.FragmentSignIn;

/**
 * Created by kenhoang on 02/03/2018.
 */

public class FragmentSignUp extends Fragment implements IViewSignUp,
        View.OnClickListener,
        View.OnFocusChangeListener{
    private static final String TAG = FragmentSignUp.class.getSimpleName();
    PresenterLogicSignUp presenterLogicSignUp;
    EditText edFullname;
    EmailEditTextCustom edEmail;
    PasswordEditTextCustom edPass, edRepass;
    TextInputLayout inputFullName, inputEmail, inputPass, inputRePass;
    Button btnRegister;
    boolean isValidateRegister = false;

    //Alert Dialog
    AlertDialog waitingDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        //Init Paper
        Paper.init(getContext());
        //Init View
        edFullname = view.findViewById(R.id.edFullName);
        edEmail = view.findViewById(R.id.edEmailAdress);
        edPass = view.findViewById(R.id.edPassword);
        edRepass = view.findViewById(R.id.edRepeatPassword);
        inputFullName = view.findViewById(R.id.inputFullname);
        inputEmail = view.findViewById(R.id.inputEmail);
        inputPass = view.findViewById(R.id.inputPass);
        inputRePass = view.findViewById(R.id.inputRePass);
        btnRegister = view.findViewById(R.id.btnRegister);

        presenterLogicSignUp = new PresenterLogicSignUp(this);

        btnRegister.setOnClickListener(this);

        edFullname.setOnFocusChangeListener(this);
        edEmail.setOnFocusChangeListener(this);
        edRepass.setOnFocusChangeListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnRegister:
                handleRegister();
                break;
        }
    }

    private void handleRegister() {
        showDialog();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String name = edFullname.getText().toString();
                String email = edEmail.getText().toString();
                String pass = edPass.getText().toString();
                String rePass = edRepass.getText().toString();

                if (isValidateRegister) {
                    if (!rePass.equals(pass)) {
                        inputRePass.setError("Re-password not same password");
                        inputRePass.setErrorEnabled(true);
                    } else {
                        User user = new User();

                        user.setName(name);
                        user.setUsername(email);
                        user.setPassword(pass);
                        user.setBirthdate(Common.BIRTHDATE_DEFAULT);
                        user.setPhone("");
                        user.setGender("MALE");
                        user.setIdentify_number("");
                        user.setWallet(0);
                        user.setIs_social("NO");
                        user.setStatus("ACTIVE");

                        presenterLogicSignUp.registerUser(user);
                    }
                } else {
                    Log.d(TAG, "handleRegister: Failed" );
                }
            }
        }, Common.DELAY_TIME);
    }

    private void showDialog() {
        waitingDialog = new SpotsDialog(getActivity());

        waitingDialog.setCancelable(false);
        waitingDialog.show();
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        int id = v.getId();
        switch (id) {
            case R.id.edFullName:
                if (!hasFocus) {
                    String data = ((EditText)v).getText().toString();
                    if (data.trim().equals("") || data == null) {
                        inputFullName.setErrorEnabled(true);
                        inputFullName.setError("Please fill your name");
                        isValidateRegister = false;
                    } else {
                        inputFullName.setErrorEnabled(false);
                        inputFullName.setError("");
                        isValidateRegister = true;
                    }
                }
                break;
            case R.id.edEmailAdress:
                if (!hasFocus) {
                    String data = ((EmailEditTextCustom)v).getText().toString();
                    Boolean isEmail = Patterns.EMAIL_ADDRESS.matcher(data).matches();
                    if (data.trim().equals("") || data == null) {
                        inputEmail.setErrorEnabled(true);
                        inputEmail.setError("Please fill your email");
                        isValidateRegister = false;
                    } else {
                        if (isEmail) {
                            inputEmail.setErrorEnabled(false);
                            inputEmail.setError("");
                            isValidateRegister = true;
                        } else {
                            inputEmail.setErrorEnabled(true);
                            inputEmail.setError("Not an email");
                            isValidateRegister = false;
                        }

                    }
                }
                break;
            case R.id.edRepeatPassword:
                if (!hasFocus) {
                    String data = ((EditText) v).getText().toString();
                    if (data.trim().equals("") || data == null) {
                        inputRePass.setErrorEnabled(true);
                        inputRePass.setError("Please fill your re-password");
                        isValidateRegister = false;
                    } else {
                        if (!data.equals(edPass.getText().toString())) {
                            inputRePass.setErrorEnabled(true);
                            inputRePass.setError("Re-password not same password");
                            isValidateRegister = false;
                        } else {
                            inputRePass.setErrorEnabled(false);
                            inputRePass.setError("");
                            isValidateRegister = true;
                        }
                    }
                }
                break;
        }
    }

    @Override
    public void registerFailed(String message) {
        Utils.showSnackBarShort(getView().findViewById(R.id.signUpLayoutMain), message);
    }

    @Override
    public void registerSuccess(User user) {
        Common.CURRENT_USER = user;
        Paper.book().write(Common.USERNAME_KEY, edEmail.getText().toString());
        Paper.book().write(Common.PASSWORD_KEY, edPass.getText().toString());
        Intent homeIntent = new Intent(getContext(), HomeActivity.class);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }
}
