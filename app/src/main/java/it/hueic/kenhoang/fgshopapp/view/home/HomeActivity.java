package it.hueic.kenhoang.fgshopapp.view.home;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;

import com.andremion.counterfab.CounterFab;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import java.util.HashMap;
import java.util.List;

import it.hueic.kenhoang.fgshopapp.R;
import it.hueic.kenhoang.fgshopapp.adapter.GroupProductTypeAdapter;
import it.hueic.kenhoang.fgshopapp.common.Common;
import it.hueic.kenhoang.fgshopapp.object.Banner;
import it.hueic.kenhoang.fgshopapp.object.GroupProductType;
import it.hueic.kenhoang.fgshopapp.presenter.home.PresenterLogicHome;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class HomeActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        IViewHome
{

    private static final String TAG = HomeActivity.class.getSimpleName();
    //View
    TextView tvFullName, tvTitle;
    CounterFab fab;
    private RecyclerView recycler_group_product_type;
    private RecyclerView.LayoutManager mLayoutManger;
    private SwipeRefreshLayout swipeRefreshLayout;
    private boolean statusItemList = false;
    Menu menu;
    //Presenter
    PresenterLogicHome presenterLogicHome;
    //Slider
    HashMap<String, String> image_list;
    SliderLayout mSlider;

    //Need call this function after you init database firebase
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Notes : add this code before setContentView
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/NABILA.TTF")
                .setFontAttrId(R.attr.fontPath)
                .build());
        setContentView(R.layout.activity_home);
        //Init View
        initView();
        //Init Presenter
        presenterLogicHome = new PresenterLogicHome(this);
        presenterLogicHome.loadBanners();
        presenterLogicHome.loadGroupProductTypes();
        //Load Data
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenterLogicHome.loadGroupProductTypes();
            }
        });
        //Default, load for first time
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                presenterLogicHome.loadGroupProductTypes();
            }
        });

    }

    private void initView() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        tvTitle         = findViewById(R.id.tvTitle);
        tvTitle.setText("Menu");
        setSupportActionBar(toolbar);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //handle after
            }
        });

        //handle after
        fab.setCount(0);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //Set name for user
        View headerView = navigationView.getHeaderView(0);
        tvFullName      = headerView.findViewById(R.id.tvFullName);
        tvFullName.setText("Username");
        //recycler
        recycler_group_product_type    = findViewById(R.id.recycler_group_product_type);
        mLayoutManger   = new LinearLayoutManager(this);
        if (statusItemList) {
            recycler_group_product_type.setHasFixedSize(true);
            recycler_group_product_type.setLayoutManager(mLayoutManger);
        } else {
            recycler_group_product_type.setLayoutManager(new GridLayoutManager(this, 2));
        }
        //Add animation recycler
        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(recycler_group_product_type.getContext(),
                R.anim.layout_fall_down);
        recycler_group_product_type.setLayoutAnimation(controller);
        //SwipeRefresh Layout
        swipeRefreshLayout = findViewById(R.id.swipe_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark
        );
        //Slider
        mSlider = findViewById(R.id.slider);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        this.menu = menu;
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.action_view:
                statusItemList = !statusItemList;
                if (statusItemList) {
                    menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.icon_view_list));
                    recycler_group_product_type.setHasFixedSize(true);
                    recycler_group_product_type.setLayoutManager(mLayoutManger);
                } else {
                    menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.icon_view_grid));
                    recycler_group_product_type.setLayoutManager(new GridLayoutManager(this, 2));
                }
                presenterLogicHome.loadGroupProductTypes();
                break;
            case R.id.action_search:
                //startActivity(new Intent(HomeActivity.this, SearchFoodActivity.class));
                break;
        }
        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_menu:
                presenterLogicHome.loadGroupProductTypes();
                break;
            case R.id.nav_update_name:
                //handle after
                break;
            case R.id.nav_home_address:
                //handle after
                break;
            case R.id.nav_fav:
                //handle after
                break;
            case R.id.nav_cart:
                //handle after
                break;
            case R.id.nav_orders:
                //handle after
                break;
            case R.id.nav_setting:
                //handle after
                break;
            case R.id.nav_change_pass:
                //Change password

                break;
            case R.id.nav_log_out:

                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void showBanners(List<Banner> banners) {
        image_list = new HashMap<>();
        for (Banner banner: banners) {
            //Create Slider
            final TextSliderView textSliderView = new TextSliderView(getBaseContext());
            textSliderView
                    .description(banner.getName_product())
                    .image(Common.URL + banner.getImage())
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                        @Override
                        public void onSliderClick(BaseSliderView slider) {
                            //handle after
                        }
                    });
            //Add extra bundle
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle().putInt("ID", banner.getId_product()); //handle after
            mSlider.addSlider(textSliderView);
        }

        mSlider.setPresetTransformer(SliderLayout.Transformer.Background2Foreground);
        mSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mSlider.setCustomAnimation(new DescriptionAnimation());
        mSlider.setDuration(5000);
    }

    @Override
    public void showGroupProductTypes(List<GroupProductType> groupProductTypes) {
        GroupProductTypeAdapter adapter = new GroupProductTypeAdapter(this,
                groupProductTypes,
                statusItemList ? R.layout.item_group_product_type_list : R.layout.item_group_product_type_grid);
        //Animation
        recycler_group_product_type.scheduleLayoutAnimation();
        recycler_group_product_type.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showError() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        //fab.setCount(new Database(this).getCountCart(Common.currentUser.getPhone()));
        if (mSlider != null) mSlider.startAutoCycle();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mSlider != null) mSlider.stopAutoCycle();
    }
}
