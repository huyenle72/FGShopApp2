package it.hueic.kenhoang.fgshopapp.model.cart;

import android.content.Context;

import java.util.List;

import it.hueic.kenhoang.fgshopapp.helper.DatabaseHelper;
import it.hueic.kenhoang.fgshopapp.object.Order;

public class ModelCart {
    Context context;

    public ModelCart(Context context) {
        this.context = context;
    }

    public boolean checkCart(int id_user, int id_product) {
        return new DatabaseHelper(context).checkCart(id_user, id_product);
    }

    public List<Order> allCart(int id_user) {
        return new DatabaseHelper(context).allCart(id_user);
    }

    public void saveCart(Order order) {
        new DatabaseHelper(context).saveCart(order);
    }

    public void updateCart(Order order) {
        new DatabaseHelper(context).updateCart(order);
    }

    public void removeAllCart(int id_user) {
        new DatabaseHelper(context).removeAllCart(id_user);
    }

    public void removeCart(int id_user, int id_product) {
        new DatabaseHelper(context).removeCart(id_user, id_product);
    }

    public void increaseCart(int id_user, int id_product) {
        new DatabaseHelper(context).increaseCart(id_user, id_product);
    }

    public int countCart(int id_user) {
        return new DatabaseHelper(context).countCart(id_user);
    }

}
