package it.hueic.kenhoang.fgshopapp.model.signup;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import it.hueic.kenhoang.fgshopapp.common.Common;
import it.hueic.kenhoang.fgshopapp.connect.ConnectAPI;
import it.hueic.kenhoang.fgshopapp.helper.ParseHelper;
import it.hueic.kenhoang.fgshopapp.object.User;

/**
 * Created by kenhoang on 05/03/2018.
 */

public class ModelRegister {

    private static final String TAG = ModelRegister.class.getSimpleName();

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

            userOrigin = ParseHelper.parseUser(data, status);

            Log.d(TAG, "status: " + status + " register: " + data);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return userOrigin;
    }
}
