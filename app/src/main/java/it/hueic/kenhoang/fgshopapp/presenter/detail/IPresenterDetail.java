package it.hueic.kenhoang.fgshopapp.presenter.detail;

public interface IPresenterDetail {
    void store(String token, int id_product, int id_user, String content, float stars, String time_rate);
}
