package it.hueic.kenhoang.fgshopapp.common;

import it.hueic.kenhoang.fgshopapp.object.User;

public class Common {
    //Current
    public static User CURRENT_USER;
    //Local of genymotion 10.0.3.2
    //Local of Android emulator 10.0.2.2
    //Local of Android real (You need connect common wifi) - You use local of desktop
    //http://localhost/mvc/FGShop/api.php
    public static final String SERVER_NAME = "http://10.0.2.2/";
    public static final String URL = SERVER_NAME + "mvc/FGShop/";
    public static final String URL_API = URL + "api.php";
    //APIs
    //controller
    public static final String USER = "User";
    public static final String GROUP_PRODUCT_TYPE = "GroupProductType";
    public static final String BANNER = "Banner";
    //action
    public static final String INDEX = "index";
    public static final String LOGIN = "login";
    public static final String LOGOUT = "logout";
    public static final String REGISTER = "register";

    //DEFAULT DATA
    public static final String ADMIN = "ADMIN";
    public static final String PARTNER = "PARTNER";
    public static final String CLIENT = "CLIENT";

    public static final String BIRTHDATE_DEFAULT = "01/01/1990";

    //DEFAULT ARRAY
    public static String[] TITLE_FRAGMENT_LOGIN = {
            "Sign In",
            "Sign Up"
    };
}
