package it.hueic.kenhoang.fgshopapp.view.login.signup;

import it.hueic.kenhoang.fgshopapp.object.User;

/**
 * Created by kenhoang on 05/03/2018.
 */

public interface IViewSignUp {
    void registerFailed(String message);
    void registerSuccess(User user);
}
