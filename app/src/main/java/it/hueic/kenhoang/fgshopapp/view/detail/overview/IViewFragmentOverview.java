package it.hueic.kenhoang.fgshopapp.view.detail.overview;

import it.hueic.kenhoang.fgshopapp.object.Product;

public interface IViewFragmentOverview {
    void fillData(Product product);
    void showError(String message);
}
