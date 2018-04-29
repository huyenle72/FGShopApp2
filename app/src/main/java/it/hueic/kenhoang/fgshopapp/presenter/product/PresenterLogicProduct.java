package it.hueic.kenhoang.fgshopapp.presenter.product;

import java.util.List;

import it.hueic.kenhoang.fgshopapp.model.product.ModelProduct;
import it.hueic.kenhoang.fgshopapp.object.ProductType;
import it.hueic.kenhoang.fgshopapp.view.product.IViewProduct;

public class PresenterLogicProduct implements IPresenterProduct {
    IViewProduct view;
    ModelProduct model;

    public PresenterLogicProduct(IViewProduct view) {
        this.view = view;
        model = new ModelProduct();
    }

    @Override
    public void menus(int id_group) {
        List<ProductType> productTypes = model.productTypes(id_group);
        if (productTypes != null && !productTypes.isEmpty())
            view.menus(productTypes);
    }
}
