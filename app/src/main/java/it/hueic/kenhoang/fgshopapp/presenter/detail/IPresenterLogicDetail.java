package it.hueic.kenhoang.fgshopapp.presenter.detail;

import it.hueic.kenhoang.fgshopapp.model.detail.ModelDetail;
import it.hueic.kenhoang.fgshopapp.object.Product;
import it.hueic.kenhoang.fgshopapp.view.detail.IViewDetail;

public class IPresenterLogicDetail implements IPresenterDetail{
    IViewDetail view;
    ModelDetail model;

    public IPresenterLogicDetail(IViewDetail view) {
        this.view = view;
        model = new ModelDetail();
    }

    @Override
    public void fillData(int id_product) {
        Product product = model.findById(id_product);
        if (product != null) view.fillData(product);
        else view.showError("Error 404");
    }
}
