package it.hueic.kenhoang.fgshopapp.helper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import it.hueic.kenhoang.fgshopapp.common.Common;
import it.hueic.kenhoang.fgshopapp.object.Banner;
import it.hueic.kenhoang.fgshopapp.object.GroupProductType;
import it.hueic.kenhoang.fgshopapp.object.User;

public class ParseHelper {
    /**
     * Parse JSON User (Login|Register)
     * @param data
     * @param status
     * @return
     */
    public static User parseUser(String data, int status) {
        User user = new User();

            try {
                JSONObject object = new JSONObject(data);
                JSONArray array = object.getJSONArray(Common.USER);
                if(status == 200) {
                    int length = array.length();
                    for (int i = 0; i < length; i++) {
                        JSONObject value = array.getJSONObject(i);
                        user.setToken(value.getString("token"));
                        user.setRole(value.getString("role"));

                        JSONObject data_user = value.getJSONObject("data");
                        user.setId(data_user.getInt("id"));
                        user.setName(data_user.getString("name"));
                        user.setBirthdate(data_user.getString("birthdate"));
                        user.setPhone(data_user.getString("phone"));
                        user.setGender(data_user.getString("gender"));
                        user.setIdentify_number(data_user.getString("identify_number"));
                        user.setWallet(data_user.getInt("wallet"));
                        user.setIs_social(data_user.getString("is_social"));
                        user.setStatus(data_user.getString("status"));

                        JSONObject image = data_user.getJSONObject("image");
                        user.setAvatar(image.getString("avatar"));
                        user.setCover(image.getString("cover"));
                    }
                } else if (status == 401) {
                    /* If Login => Unauthorized */
                    /* If Register => User is exists */
                    return null;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        return user;
    }

    /**
     * Parse JSON GroupProductType
     * @param data
     * @param status
     * @return
     */

    public static List<GroupProductType> parseGroupProductTypes(String data, int status) {
        ArrayList<GroupProductType> list = new ArrayList<>();
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(data);
            JSONArray array = jsonObject.getJSONArray(Common.GROUP_PRODUCT_TYPE);

            if (status == 200) {
                int length = array.length();
                for (int i = 0; i < length; i++) {
                    JSONObject value = (JSONObject) array.get(i);

                    GroupProductType object = new GroupProductType();
                    object.setId(value.getInt("id"));
                    object.setName_group(value.getString("name_group"));
                    object.setImage(value.getString("image"));

                    list.add(object);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Parse JSON Banner
     * @param data
     * @param status
     * @return
     */

    public static List<Banner> parseBanners(String data, int status) {
        ArrayList<Banner> list = new ArrayList<>();
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(data);
            JSONArray array = jsonObject.getJSONArray(Common.BANNER);

            if (status == 200) {
                int length = array.length();
                for (int i = 0; i < length; i++) {
                    JSONObject value = (JSONObject) array.get(i);

                    Banner object = new Banner();
                    object.setId(value.getInt("id"));
                    object.setId_product(value.getInt("id_product"));
                    object.setName_product(value.getString("name_product"));
                    object.setImage(value.getString("image"));

                    list.add(object);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}
