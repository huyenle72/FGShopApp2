package it.hueic.kenhoang.fgshopapp.presenter.home;

import java.util.ArrayList;
import java.util.List;

import it.hueic.kenhoang.fgshopapp.model.home.ModelHome;
import it.hueic.kenhoang.fgshopapp.object.Banner;
import it.hueic.kenhoang.fgshopapp.object.GroupProductType;
import it.hueic.kenhoang.fgshopapp.view.home.IViewHome;

public class PresenterLogicHome implements IPresenterHome {
    IViewHome view;
    ModelHome model;

    public PresenterLogicHome(IViewHome view) {
        this.view = view;
        model = new ModelHome();
    }

    @Override
    public void loadBanners() {
        List<Banner> banners = model.banners();

        if (!banners.isEmpty())
            view.showBanners(banners);

    }

    @Override
    public void loadGroupProductTypes() {
        List<GroupProductType> groupProductTypes = model.groupProductTypes();

        if (!groupProductTypes.isEmpty())
            view.showGroupProductTypes(groupProductTypes);
    }
}