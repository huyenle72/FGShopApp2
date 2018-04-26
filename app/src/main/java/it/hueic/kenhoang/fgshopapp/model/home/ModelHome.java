package it.hueic.kenhoang.fgshopapp.model.home;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import it.hueic.kenhoang.fgshopapp.common.Common;
import it.hueic.kenhoang.fgshopapp.connect.ConnectAPI;
import it.hueic.kenhoang.fgshopapp.object.Banner;
import it.hueic.kenhoang.fgshopapp.object.GroupProductType;

public class ModelHome {
    /**
     * controller: GroupProductType
     * action: index
     * @return
     */
    public List<GroupProductType> groupProductTypes() {
        String dataJSON = "";

        List<GroupProductType> list = new ArrayList<>();

        List<HashMap<String, String>> attrs = new ArrayList<>();

        HashMap<String, String> attr = new HashMap<>();

        attr.put("controller", Common.GROUP_PRODUCT_TYPE);
        attrs.add(attr);

        attr = new HashMap<>();
        attr.put("action", Common.INDEX);
        attrs.add(attr);

        ConnectAPI connect = new ConnectAPI(Common.URL_API, attrs);
        connect.execute();

        try {
            dataJSON = connect.get();

            JSONObject jsonObject = new JSONObject(dataJSON);
            JSONArray jsonArrayProduct = jsonObject.getJSONArray(Common.GROUP_PRODUCT_TYPE);

            int length = jsonArrayProduct.length();

            for (int i = 0; i < length; i++) {

                JSONObject value = (JSONObject) jsonArrayProduct.get(i);

                GroupProductType object = new GroupProductType();
                object.setId(value.getInt("id"));
                object.setName_group(value.getString("name_group"));
                object.setImage(value.getString("image"));

                list.add(object);
            }

            //Call View
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * controller: Banner
     * action: index
     * @return
     */
    public List<Banner> banners() {
        String dataJSON = "";

        List<Banner> list = new ArrayList<>();

        List<HashMap<String, String>> attrs = new ArrayList<>();

        HashMap<String, String> attr = new HashMap<>();

        attr.put("controller", Common.BANNER);
        attrs.add(attr);

        attr = new HashMap<>();
        attr.put("action", Common.INDEX);
        attrs.add(attr);

        ConnectAPI connect = new ConnectAPI(Common.URL_API, attrs);
        connect.execute();

        try {
            dataJSON = connect.get();

            JSONObject jsonObject = new JSONObject(dataJSON);
            JSONArray jsonArrayProduct = jsonObject.getJSONArray(Common.BANNER);

            int length = jsonArrayProduct.length();

            for (int i = 0; i < length; i++) {

                JSONObject value = (JSONObject) jsonArrayProduct.get(i);

                Banner object = new Banner();
                object.setId(value.getInt("id"));
                object.setId_product(value.getInt("id_product"));
                object.setName_product(value.getString("name_product"));
                object.setImage(value.getString("image"));

                list.add(object);
            }

            //Call View
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}
