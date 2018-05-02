package it.hueic.kenhoang.fgshopapp.view.detail;

import it.hueic.kenhoang.fgshopapp.object.Product;

public interface IViewDetail {
    void fillData(Product product);
    void showError(String message);
}
