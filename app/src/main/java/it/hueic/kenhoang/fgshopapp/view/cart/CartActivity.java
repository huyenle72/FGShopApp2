package it.hueic.kenhoang.fgshopapp.view.cart;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.valdesekamdem.library.mdtoast.MDToast;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import it.hueic.kenhoang.fgshopapp.R;
import it.hueic.kenhoang.fgshopapp.adapter.CartAdapter;
import it.hueic.kenhoang.fgshopapp.common.Common;
import it.hueic.kenhoang.fgshopapp.object.Cart;
import it.hueic.kenhoang.fgshopapp.presenter.cart.PresenterLogicCart;
import it.hueic.kenhoang.fgshopapp.utils.Utils;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class CartActivity extends AppCompatActivity implements
IViewCart{
    private static final String TAG = CartActivity.class.getSimpleName();

    RecyclerView recycler_cart;
    RecyclerView.LayoutManager mLayoutManager;
    LinearLayout main_layout;
    SwipeRefreshLayout swipeRefreshLayout;
    TextView tvTotal;
    Button btnCheckout;
    PresenterLogicCart presenterLogicCart;
    CartAdapter adapter;
    //Need call this function after you init database
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Notes : add this code before setContentView
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/font_main.otf")
                .setFontAttrId(R.attr.fontPath)
                .build());
        setContentView(R.layout.activity_cart);
        //InitView
        initView();
        //InitPresenter
        presenterLogicCart = new PresenterLogicCart(this, this);
        //Load Data
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenterLogicCart.carts(Common.CURRENT_USER.getId());
            }
        });
        //Default, load for first time
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                presenterLogicCart.carts(Common.CURRENT_USER.getId());
            }
        });
    }

    private void initView() {
        setUpToolbar();
        recycler_cart = findViewById(R.id.recycler_cart);
        recycler_cart.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        recycler_cart.setLayoutManager(mLayoutManager);
        main_layout = findViewById(R.id.main_layout);
        tvTotal = findViewById(R.id.total);
        btnCheckout = findViewById(R.id.btnCheckout);
        //SwipeRefresh Layout
        swipeRefreshLayout = findViewById(R.id.swipe_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark
        );
    }

    /**
     * Set up toolbar
     */
    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_close_white_24dp);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("FGShop Cart");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void carts(List<Cart> carts) {
        adapter = new CartAdapter(this, carts, R.layout.item_cart, presenterLogicCart);
        recycler_cart.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void total(int total) {
        NumberFormat numberFormat = new DecimalFormat("###,###");
        tvTotal.setText(String.valueOf(numberFormat.format(Integer.valueOf(total)) + " VND"));
    }

    @Override
    public void error(String msg) {
        Utils.showSnackBarShort(main_layout, msg);
    }
}
