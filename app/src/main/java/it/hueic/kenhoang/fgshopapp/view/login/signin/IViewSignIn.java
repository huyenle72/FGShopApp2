package it.hueic.kenhoang.fgshopapp.view.login.signin;

import it.hueic.kenhoang.fgshopapp.object.User;

/**
 * Created by kenhoang on 06/03/2018.
 */

public interface IViewSignIn {
    void loginSuccess(User user);
    void loginFailed(String msg);
}
