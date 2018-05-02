package it.hueic.kenhoang.fgshopapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import it.hueic.kenhoang.fgshopapp.adapter.viewholder.ProductHolder;
import it.hueic.kenhoang.fgshopapp.common.Common;
import it.hueic.kenhoang.fgshopapp.handle.click.IClickItemListener;
import it.hueic.kenhoang.fgshopapp.object.Product;
import it.hueic.kenhoang.fgshopapp.view.product.ProductActivity;

public class ProductAdapter extends RecyclerView.Adapter<ProductHolder> {
    Context context;
    List<Product> list;
    int resource;

    public ProductAdapter(Context context, List<Product> list, int resource) {
        this.context = context;
        this.list = list;
        this.resource = resource;
    }

    @Override
    public ProductHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(resource, parent, false);
        return new ProductHolder(view);
    }

    @Override
    public void onBindViewHolder(final ProductHolder holder, int position) {
        final Product object = list.get(position);
        holder.name.setText(object.getName_product());
        NumberFormat numberFormat = new DecimalFormat("###,###");
        holder.price.setText(String.valueOf(numberFormat.format(Integer.valueOf(object.getPrice())) + " VND"));
        holder.ratingBar.setRating(object.getRate());
        holder.rate_number.setText("(" + String.valueOf(object.getNum_people_rates()) + ")");
        holder.like_number.setText(String.valueOf(object.getNum_likes()));
        Picasso.with(context)
                .load(Common.URL + object.getImage())
                .into(holder.image, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.progress.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {

                    }
                });
        holder.btn_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //handle after
            }
        });

        holder.setiClickItemListener(new IClickItemListener() {
            @Override
            public void itemClickListener(View view, int position, boolean isLongClick) {
                //Start new Activity
                /*Intent detailIntent = new Intent(context, DetailActivity.class);
                detailIntent.putExtra("id_product", list.get(position).getId());
                context.startActivity(detailIntent);*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
