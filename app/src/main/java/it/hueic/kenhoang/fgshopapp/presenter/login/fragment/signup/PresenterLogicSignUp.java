package it.hueic.kenhoang.fgshopapp.presenter.login.fragment.signup;

import it.hueic.kenhoang.fgshopapp.model.signup.ModelRegister;
import it.hueic.kenhoang.fgshopapp.object.User;
import it.hueic.kenhoang.fgshopapp.view.login.signup.IViewSignUp;

/**
 * Created by kenhoang on 26/04/2018.
 */

public class PresenterLogicSignUp implements IPresenterSignUp {
    IViewSignUp IViewSignUp;
    ModelRegister modelRegister;

    public PresenterLogicSignUp(IViewSignUp IViewSignUp) {
        this.IViewSignUp = IViewSignUp;
        this.modelRegister = new ModelRegister();
    }

    @Override
    public void registerUser(User user) {
        User userOriginal = modelRegister.register(user);
        if (userOriginal == null) IViewSignUp.registerFailed("User is exists");
        else IViewSignUp.registerSuccess(userOriginal);
    }
}
