package it.hueic.kenhoang.fgshopapp.view.detail;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.stepstone.apprating.AppRatingDialog;
import com.stepstone.apprating.listener.RatingDialogListener;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.util.Arrays;

import it.hueic.kenhoang.fgshopapp.R;
import it.hueic.kenhoang.fgshopapp.adapter.ViewPagerAdapterDetail;
import it.hueic.kenhoang.fgshopapp.adapter.ViewPagerAdapterLogin;
import it.hueic.kenhoang.fgshopapp.object.Product;
import it.hueic.kenhoang.fgshopapp.utils.Utils;
import it.hueic.kenhoang.fgshopapp.view.login.LoginActivity;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class DetailActivity extends AppCompatActivity implements
        IViewDetail,
        RatingDialogListener{
    private static final String TAG = DetailActivity.class.getSimpleName();
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;

    //Need call this function after you init database firebase
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/font_main.otf")
                .setFontAttrId(R.attr.fontPath)
                .build());
        setContentView(R.layout.activity_detail);//Notes : add this code before setContentView

        //InitView
        initView();
    }

    private void initView() {
        setUpToolbar();//Setup toolbar
        tabLayout = findViewById(R.id.tab);
        viewPager = findViewById(R.id.viewpager);

        ViewPagerAdapterDetail viewPagerAdapterDetail = new ViewPagerAdapterDetail(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapterDetail);
        viewPagerAdapterDetail.notifyDataSetChanged();

        tabLayout.setupWithViewPager(viewPager);
    }

    /**
     * Set up toolbar
     */
    private void setUpToolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_close_white_24dp);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("FGShop");
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.action_rating:
                if (Utils.isLogin()) {
                    new AppRatingDialog.Builder()
                            .setPositiveButtonText("Submit")
                            .setNegativeButtonText("Cancel")
                            .setNeutralButtonText("Later")
                            .setNoteDescriptions(Arrays.asList("Very Bad", "Not good", "Quite ok", "Very Good", "Excellent !!!"))
                            .setDefaultRating(2)
                            .setTitle("Rate this product")
                            .setDescription("Please select some stars and give your feedback")
                            .setStarColor(R.color.starColor)
                            .setNoteDescriptionTextColor(R.color.noteDescriptionTextColor)
                            .setTitleTextColor(R.color.titleTextColor)
                            .setDescriptionTextColor(R.color.descriptionTextColor)
                            .setHint("Please write your comment here ...")
                            .setHintTextColor(R.color.hintTextColor)
                            .setCommentTextColor(R.color.commentTextColor)
                            .setCommentBackgroundColor(R.color.colorGray)
                            .setWindowAnimation(R.style.MyDialogFadeAnimation)
                            .create(DetailActivity.this)
                            .show();
                } else {
                    Utils.openLogin(this);
                }
                break;
            case R.id.action_cart:
                //handle after
                break;
            case R.id.action_search:
                //handle after
                break;
        }
        //noinspection SimplifiableIfStatement
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPositiveButtonClicked(int i, String s) {

    }

    @Override
    public void onNegativeButtonClicked() {

    }

    @Override
    public void onNeutralButtonClicked() {

    }
}
