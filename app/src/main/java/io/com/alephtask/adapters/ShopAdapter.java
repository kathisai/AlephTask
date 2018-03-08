package io.com.alephtask.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.com.alephtask.R;
import io.com.alephtask.models.Shop;

/**
 * Created by skathi on 3/8/2018.
 */

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ShopViewHolder> {

    private List<Shop> mList;
    private Context mContext;

    public ShopAdapter(List<Shop> list, Context mContext) {
        this.mList = list;
        this.mContext = mContext;
    }

    @Override
    public ShopViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View contactView = LayoutInflater.from(mContext).inflate(R.layout.expanded_item, parent, false);
        return new ShopViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(ShopViewHolder holder, int position) {
        Shop shop = mList.get(position);
        holder.shopeImage.setImageResource(shop.getImageResorce());
        holder.shopName.setText(shop.getName());
        holder.shopStatistics.setText(String.format("%d of %d",shop.getVisitedCount(),shop.getTotalCount()));
        holder.shopDistance.setText(shop.getDistance());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }



    class ShopViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_shop_image)
        ImageView shopeImage;
        @BindView(R.id.tv_shop_name)
        TextView shopName;
        @BindView(R.id.tv_shop_statistics)
        TextView shopStatistics;
        @BindView(R.id.tv_shop_distance)
        TextView shopDistance;

        public ShopViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

}
