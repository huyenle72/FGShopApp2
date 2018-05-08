package it.hueic.kenhoang.fgshopapp.presenter.cart;

import android.content.Context;

import java.util.List;

import it.hueic.kenhoang.fgshopapp.model.cart.ModelCart;
import it.hueic.kenhoang.fgshopapp.object.Cart;
import it.hueic.kenhoang.fgshopapp.view.cart.IViewCart;

public class PresenterLogicCart implements IPresenterCart {
    IViewCart view;
    ModelCart model;

    public PresenterLogicCart(Context context, IViewCart view) {
        this.view = view;
        model = new ModelCart(context);
    }

    @Override
    public void carts(int id_user) {
        List<Cart> carts = model.carts(id_user);
        view.carts(carts);
    }

    @Override
    public void total(int id_user) {
        int total = 0;
        List<Cart> carts = model.carts(id_user);
        for (Cart cart: carts)
            total += cart.getTotal();
        view.total(total);
    }
}
