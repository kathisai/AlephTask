package io.com.alephtask.adapters;

import android.content.Context;
import android.os.Build;
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

public class ShopAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Shop> mList;
    private Context mContext;

    public ShopAdapter(List<Shop> list, Context mContext) {
        this.mList = list;
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case 0:
                View contactView = LayoutInflater.from(mContext).inflate(R.layout.expanded_item, parent, false);
                return new ShopViewHolder(contactView);
            case 1:
                View collapseview = LayoutInflater.from(mContext).inflate(R.layout.collapse_item, parent, false);
                return new ShopCollapseViewHolder(collapseview);
        }
        return null;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Shop shop = mList.get(position);
        if (holder instanceof ShopCollapseViewHolder) {
            ShopCollapseViewHolder collapseViewHolder = (ShopCollapseViewHolder) holder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                // the view being shared
                collapseViewHolder.shopName.setTransitionName("transition_shop" + position);
                collapseViewHolder.shopDistance.setTransitionName("transition_distance" + position);
                collapseViewHolder.rightArrow.setTransitionName("transition_arrow" + position);
                collapseViewHolder.shopImage.setTransitionName("transition_shop_image" + position);
            }
            collapseViewHolder.shopName.setText(shop.getName());
            collapseViewHolder.shopDistance.setText(shop.getDistance());
        } else if (holder instanceof ShopViewHolder) {
            ShopViewHolder shopViewHolder = (ShopViewHolder) holder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                // the view being shared
                shopViewHolder.shopName.setTransitionName("transition_shop" + position);
                shopViewHolder.shopDistance.setTransitionName("transition_distance" + position);
                shopViewHolder.rightArrow.setTransitionName("transition_arrow" + position);
                shopViewHolder.shopImage.setTransitionName("transition_shop_image" + position);
            }
            shopViewHolder.shopImage.setImageResource(shop.getImageResorce());
            shopViewHolder.shopName.setText(shop.getName());
            shopViewHolder.shopStatistics.setText(String.format(mContext.getString(R.string.shop_statistics), shop.getVisitedCount(), shop.getTotalCount()));
            shopViewHolder.shopDistance.setText(shop.getDistance());
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(final int position) {
        final Shop item = mList.get(position);
        return item.getType();
    }


    class ShopViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_shop_image)
        ImageView shopImage;
        @BindView(R.id.tv_shop_name)
        TextView shopName;
        @BindView(R.id.tv_shop_statistics)
        TextView shopStatistics;
        @BindView(R.id.tv_shop_distance)
        TextView shopDistance;
        @BindView(R.id.iv_right_arrow)
        ImageView rightArrow;

        public ShopViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }


    public class ShopCollapseViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_shop_name)
        TextView shopName;
        @BindView(R.id.tv_shop_distance)
        TextView shopDistance;
        @BindView(R.id.iv_right_arrow)
        ImageView rightArrow;
        @BindView(R.id.iv_shop_image)
        ImageView shopImage;

        public ShopCollapseViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public ImageView getShopImage() {
            return shopImage;
        }

        public void setShopImage(ImageView shopImage) {
            this.shopImage = shopImage;
        }

        public TextView getShopName() {
            return shopName;
        }


        public TextView getShopDistance() {
            return shopDistance;
        }

        public ImageView getRightArrow() {
            return rightArrow;
        }

        public void setRightArrow(ImageView rightArrow) {
            this.rightArrow = rightArrow;
        }
    }

}
