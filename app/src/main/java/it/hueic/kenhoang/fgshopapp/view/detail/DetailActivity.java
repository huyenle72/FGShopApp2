package it.hueic.kenhoang.fgshopapp.view.detail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.valdesekamdem.library.mdtoast.MDToast;

import it.hueic.kenhoang.fgshopapp.R;
import it.hueic.kenhoang.fgshopapp.object.Product;
import it.hueic.kenhoang.fgshopapp.utils.Utils;

public class DetailActivity extends AppCompatActivity implements
    IViewDetail{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
    }

    @Override
    public void fillData(Product product) {

    }

    @Override
    public void showError(String message) {
        Utils.showToastShort(this, message, MDToast.TYPE_ERROR);
    }
}
