package it.hueic.kenhoang.fgshopapp.model.detail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import it.hueic.kenhoang.fgshopapp.common.Common;
import it.hueic.kenhoang.fgshopapp.connect.ConnectAPI;
import it.hueic.kenhoang.fgshopapp.helper.ParseHelper;
import it.hueic.kenhoang.fgshopapp.object.Product;

public class ModelDetail {
    /**
     * Product with id
     * @param id_product
     * @return
     */
    public Product findById(int id_product) {
        Product product = new Product();
        String data = "";
        int status = 0;

        List<HashMap<String,String>> attrs = new ArrayList<>();

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("controller", Common.PRODUCT);
        attrs.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("action", Common.DETAIL);
        attrs.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("id_product", String.valueOf(id_product));
        attrs.add(hashMap);

        ConnectAPI connect = new ConnectAPI(Common.URL_API, attrs);
        connect.execute();
        try {
            data = connect.get().get(0);
            status = Integer.parseInt(connect.get().get(1));
            product = ParseHelper.parseProduct(data, status);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return product;
    }
}
