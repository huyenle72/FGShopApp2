package it.hueic.kenhoang.fgshopapp.presenter.login.fragment.signin;

import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;

import it.hueic.kenhoang.fgshopapp.helper.ParseHelper;
import it.hueic.kenhoang.fgshopapp.model.signin.ModelLogin;
import it.hueic.kenhoang.fgshopapp.object.User;
import it.hueic.kenhoang.fgshopapp.view.login.signin.IViewSignIn;
/**
 * Created by kenhoang on 06/03/2018.
 */

public class PresenterLogicSignIn implements IPresenterSignIn {
    private static final String TAG = PresenterLogicSignIn.class.getSimpleName();
    IViewSignIn IViewSignIn;
    ModelLogin modelLogin;

    public PresenterLogicSignIn(IViewSignIn IViewSignIn) {
        this.IViewSignIn = IViewSignIn;
        modelLogin = new ModelLogin();
    }

    @Override
    public void validateLogin(String email, String pass) {
        User user = modelLogin.validateLogin(email, pass);
        if (user == null) IViewSignIn.loginFailed("Login failed!");
        else IViewSignIn.loginSuccess(user);
    }
}
