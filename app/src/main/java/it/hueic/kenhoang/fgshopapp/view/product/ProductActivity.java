package it.hueic.kenhoang.fgshopapp.view.product;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;
import it.hueic.kenhoang.fgshopapp.R;
import it.hueic.kenhoang.fgshopapp.adapter.ExpandAdapter;
import it.hueic.kenhoang.fgshopapp.common.Common;
import it.hueic.kenhoang.fgshopapp.object.ProductType;
import it.hueic.kenhoang.fgshopapp.presenter.product.PresenterLogicProduct;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ProductActivity extends AppCompatActivity implements
        IViewProduct,
        AppBarLayout.OnOffsetChangedListener {

    private static final String TAG = ProductActivity.class.getSimpleName();

    AppBarLayout appBarLayout;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    ExpandableListView expandableListView;
    PresenterLogicProduct presenterLogicProduct;
    int id_group;
    //View
    TextView tvFullName;
    CircleImageView profile_image;
    String title = "";

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
        setContentView(R.layout.activity_product);
        if (getIntent() != null) {
            id_group = getIntent().getIntExtra("id_group", 0);
            title = getIntent().getStringExtra("title");
        }
        //InitView
        initView();
        //InitEvent
        appBarLayout.addOnOffsetChangedListener(this);
        //InitPresenter
        presenterLogicProduct = new PresenterLogicProduct(this);
        presenterLogicProduct.menus(id_group);
    }

    private void initView() {
        setUpToolbar(); //Set toolbar
        drawerLayout = findViewById(R.id.drawerLayout);
        expandableListView = findViewById(R.id.epMenu);
        appBarLayout = findViewById(R.id.appbar);

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        profile_image = findViewById(R.id.profile_image);
        tvFullName      = findViewById(R.id.tvFullName);
        existUser();
    }

    private void existUser() {
        if (Common.CURRENT_USER != null) {
            if (Common.CURRENT_USER.getAvatar() != null && !Common.CURRENT_USER.getAvatar().equals("null")) {
                Picasso.with(this)
                        .load(Common.URL + Common.CURRENT_USER.getAvatar())
                        .into(profile_image);
            } else {
                profile_image.setImageResource(R.drawable.image_null);
            }
            tvFullName.setText(Common.CURRENT_USER.getName());
        } else {
            profile_image.setImageResource(R.drawable.image_null);
            tvFullName.setText(getString(R.string.anonymous));
        }
    }

    /**
     * Set up toolbar
     */
    private void setUpToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * Option Menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    /**
     * Handle event option menu
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return true;
    }

    @Override
    public void menus(List<ProductType> lists) {
        ExpandAdapter expandAdapter = new ExpandAdapter(this, lists, id_group);
        expandableListView.setAdapter(expandAdapter);
        expandAdapter.notifyDataSetChanged();
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

    }
}
