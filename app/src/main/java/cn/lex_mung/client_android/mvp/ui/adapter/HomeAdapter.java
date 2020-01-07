package cn.lex_mung.client_android.mvp.ui.adapter;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.zl.mvp.http.imageloader.glide.ImageConfigImpl;

import java.util.ArrayList;
import java.util.List;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.mvp.model.entity.LawyerEntity2;
import cn.lex_mung.client_android.mvp.model.entity.home.HomeChildEntity;
import cn.lex_mung.client_android.mvp.model.entity.home.HomeEntity;
import cn.lex_mung.client_android.mvp.ui.widget.home.Home3ButtonView;
import cn.lex_mung.client_android.mvp.ui.widget.home.Home4ButtonView;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.zl.mvp.utils.AppUtils;
import me.zl.mvp.utils.StringUtils;

public class HomeAdapter extends BaseQuickAdapter<HomeEntity, BaseViewHolder> {
    ImageLoader mImageLoader;


    public HomeAdapter(ImageLoader mImageLoader) {
        super(null);
        this.mImageLoader = mImageLoader;
        setMultiTypeDelegate(new MultiTypeDelegate<HomeEntity>() {
            @Override
            protected int getItemType(HomeEntity homeEntity) {
                return homeEntity.getTypeId();
            }
        });
        getMultiTypeDelegate()
                .registerItemType(0, R.layout.layout_home_empty)
                .registerItemType(1, R.layout.layout_home_3button)
                .registerItemType(2, R.layout.layout_home_banner)
                .registerItemType(3, R.layout.layout_home_4button)
                .registerItemType(4, R.layout.layout_home_3card)
                .registerItemType(5, R.layout.layout_home_4hot)
                .registerItemType(6, R.layout.layout_home_4hot)
                .registerItemType(7, R.layout.layout_home_only_img)
                .registerItemType(20, R.layout.item_home20_lawyer)
                .registerItemType(21, R.layout.layout_home20_lawyer_title);

    }

    @Override
    protected void convert(BaseViewHolder helper, HomeEntity item) {
        switch (helper.getItemViewType()) {
            case 0:
                return;
            case 1:

                if(item.getBtns() == null || item.getBtns().size() < 3)
                    return;

                Home3ButtonView home3ButtonView1 = helper.getView(R.id.view_home_3button1);
                Home3ButtonView home3ButtonView2 = helper.getView(R.id.view_home_3button2);
                Home3ButtonView home3ButtonView3 = helper.getView(R.id.view_home_3button3);
                setHome3ButtonViewData(home3ButtonView1, item.getBtns().get(0));
                setHome3ButtonViewData(home3ButtonView2, item.getBtns().get(1));
                setHome3ButtonViewData(home3ButtonView3, item.getBtns().get(2));

                setOnclick(home3ButtonView1,item.getBtns().get(0));
                setOnclick(home3ButtonView2,item.getBtns().get(1));
                setOnclick(home3ButtonView3,item.getBtns().get(2));

                break;
            case 2:
                if(item.getBtns() == null || item.getBtns().size() == 0)
                    return;

                Banner banner = helper.getView(R.id.banner);
                banner.setImageLoader(new com.youth.banner.loader.ImageLoader() {
                    @Override
                    public void displayImage(Context context, Object path, ImageView imageView) {
                        mImageLoader.loadImage(context
                                , ImageConfigImpl
                                        .builder()
                                        .url(path.toString())
                                        .imageRadius(AppUtils.dip2px(mContext, 10))
                                        .imageView(imageView)
                                        .build());
                    }
                });

                banner.setOnBannerListener(position -> {
                    if (onBannerClickListener != null) {
                        onBannerClickListener.onBannerClick(item.getBtns().get(position));
                    }
                });

                banner.setPageMargin(AppUtils.dip2px(mContext, AppUtils.getXmlDef(mContext, R.dimen.qb_px_20)));
                banner.setOffscreenPageLimit(3);
                banner.setBannerStyle(BannerConfig.NOT_INDICATOR);
                banner.setPageTransformer(true, new ViewPager.PageTransformer() {
                    private static final float MIN_SCALE = 0.85f;

                    @Override
                    public void transformPage(View view, float position) {
                        if (position >= -1 || position <= 1) {
                            final float height = view.getHeight();
                            final float width = view.getWidth();
                            final float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                            final float vertMargin = height * (1 - scaleFactor) / 2;
                            final float horzMargin = width * (1 - scaleFactor) / 2;
                            view.setPivotY(0.5f * height);
                            view.setPivotX(0.5f * width);
                            if (position < 0) {
                                view.setTranslationX(horzMargin - vertMargin / 2);
                            } else {
                                view.setTranslationX(-horzMargin + vertMargin / 2);
                            }
                            view.setScaleX(scaleFactor);
                            view.setScaleY(scaleFactor);
                        }
                    }
                });
                banner.setDelayTime(3000);

                List<String> imageList = new ArrayList<>();
                for (HomeChildEntity bean : item.getBtns()) {
                    imageList.add(bean.getIcon());
                }
                banner.setImages(imageList);
                banner.start();
                break;

            case 3:
                if(item.getBtns() == null || item.getBtns().size() < 4)
                    return;

                Home4ButtonView home4ButtonView1 = helper.getView(R.id.view_home_4button1);
                Home4ButtonView home4ButtonView2 = helper.getView(R.id.view_home_4button2);
                Home4ButtonView home4ButtonView3 = helper.getView(R.id.view_home_4button3);
                Home4ButtonView home4ButtonView4 = helper.getView(R.id.view_home_4button4);

                setHome4ButtonViewData(home4ButtonView1, item.getBtns().get(0));
                setHome4ButtonViewData(home4ButtonView2, item.getBtns().get(1));
                setHome4ButtonViewData(home4ButtonView3, item.getBtns().get(2));
                setHome4ButtonViewData(home4ButtonView4, item.getBtns().get(3));

                helper.addOnClickListener(R.id.view_home_4button1);
                helper.addOnClickListener(R.id.view_home_4button2);
                helper.addOnClickListener(R.id.view_home_4button3);
                helper.addOnClickListener(R.id.view_home_4button4);

                setOnclick(home4ButtonView1,item.getBtns().get(0));
                setOnclick(home4ButtonView2,item.getBtns().get(1));
                setOnclick(home4ButtonView3,item.getBtns().get(2));
                setOnclick(home4ButtonView4,item.getBtns().get(3));

                break;
            case 4:
                if(item.getBtns() == null || item.getBtns().size() < 3)
                    return;

                TextView tvTitle = helper.getView(R.id.tv_title);
                View viewCard1 = helper.getView(R.id.view_card1);
                View viewCard2 = helper.getView(R.id.view_card2);
                View viewCard3 = helper.getView(R.id.view_card3);
                ImageView ivCard1 = helper.getView(R.id.iv_card1);
                ImageView ivCard2 = helper.getView(R.id.iv_card2);
                ImageView ivCard3 = helper.getView(R.id.iv_card3);
                TextView tvCard1Title = helper.getView(R.id.tv_card1_title);
                TextView tvCard2Title = helper.getView(R.id.tv_card2_title);
                TextView tvCard3Title = helper.getView(R.id.tv_card3_title);
                TextView tvCard1Content1 = helper.getView(R.id.tv_card1_content1);
                TextView tvCard2Content1 = helper.getView(R.id.tv_card2_content1);
                TextView tvCard3Content1 = helper.getView(R.id.tv_card3_content1);
                TextView tvCard1Content2 = helper.getView(R.id.tv_card1_content2);
                ImageView tvCard2Tag = helper.getView(R.id.tv_card2_tag);
                ImageView tvCard3Tag = helper.getView(R.id.tv_card3_tag);

                tvTitle.setText(item.getHeadline());
                setHome3CardViewData(ivCard1, tvCard1Title, tvCard1Content1, tvCard1Content2, null, item.getBtns().get(0));
                setHome3CardViewData(ivCard2, tvCard2Title, tvCard2Content1, null, tvCard2Tag, item.getBtns().get(1));
                setHome3CardViewData(ivCard3, tvCard3Title, tvCard3Content1, null, tvCard3Tag, item.getBtns().get(2));

                setOnclick(viewCard1,item.getBtns().get(0));
                setOnclick(viewCard2,item.getBtns().get(1));
                setOnclick(viewCard3,item.getBtns().get(2));

                break;
            case 5:
            case 6:
                if(item.getBtns() == null || item.getBtns().size() == 0)
                    return;

                TextView tvTitle5 = helper.getView(R.id.tv_title);
                TextView tvTitleContent5 = helper.getView(R.id.tv_title_content);
                RecyclerView recyclerView5 = helper.getView(R.id.recycler_view);
                recyclerView5.setNestedScrollingEnabled(false);

                tvTitle5.setText(item.getHeadline());
                if (!TextUtils.isEmpty(item.getSubheadline()))
                    tvTitleContent5.setText(item.getSubheadline());

                Home4hotAdapter adapter5 = new Home4hotAdapter(mImageLoader);
                adapter5.setOnItemClickListener((adapter1, view, position) -> {
                    if (onBannerClickListener != null) {
                        onBannerClickListener.onBannerClick(item.getBtns().get(position));
                    }
                });

                AppUtils.configRecyclerView(recyclerView5, new GridLayoutManager(mContext, 4));
                recyclerView5.setAdapter(adapter5);
                adapter5.setNewData(item.getBtns());

                break;
            case 7:
                if(item.getBtns() == null || item.getBtns().size() == 0)
                    return;

                ImageView iv_img = helper.getView(R.id.iv_img);
                if (!TextUtils.isEmpty(item.getBtns().get(0).getIcon())) {
                    mImageLoader.loadImage(mContext
                            , ImageConfigImpl
                                    .builder()
                                    .isCenterCrop(false)
                                    .url(item.getBtns().get(0).getIcon())
                                    .imageView(iv_img)
                                    .build());
                }
                setOnclick(iv_img,item.getBtns().get(0));

                break;
            case 20:
                setLawyerView(helper,item.getLawyerEntity2());
                break;
            case 21:
                helper.getView(R.id.tv_content).setOnClickListener(v -> {
                    if (onBannerClickListener != null) {
                        onBannerClickListener.onLawyerTitleClick();
                    }
                });
                break;
        }

    }

    public void setOnclick(View view,HomeChildEntity item){
        view.setOnClickListener(v -> {
            if (onBannerClickListener != null) {
                onBannerClickListener.onBannerClick(item);
            }
        });
    }

    //设置律师数据
    public void setLawyerView(BaseViewHolder helper,LawyerEntity2 item){
        if (!TextUtils.isEmpty(item.getIconImage())) {
            mImageLoader.loadImage(mContext
                    , ImageConfigImpl
                            .builder()
                            .url(item.getIconImage())
                            .imageView(helper.getView(R.id.item_iv_avatar))
                            .isCircle(true)
                            .build());
        } else {
            helper.setImageResource(R.id.item_iv_avatar, R.drawable.ic_lawyer_avatar);
        }
        helper.setText(R.id.item_tv_name, item.getMemberName());
        helper.setText(R.id.item_tv_job, item.getMemberPositionNameStr());
        helper.setText(R.id.item_tv_area, item.getReginInstitutionName());
        StringUtils.setHtml(helper.getView(R.id.item_tv_field),item.getDescriptionStr());
        helper.setText(R.id.item_tv_practice_num, item.getPractice());

        helper.getView(R.id.cl_parent).setOnClickListener(v -> {
            if (onBannerClickListener != null) {
                onBannerClickListener.onLawyerClick(item);
            }
        });
    }

    public void setHome3CardViewData(ImageView ivCard, TextView tvCardTitle, TextView tvCardContent1, TextView tvCardContent2, ImageView tvCardTag, HomeChildEntity item) {
        if (!TextUtils.isEmpty(item.getIcon())) {
            mImageLoader.loadImage(mContext
                    , ImageConfigImpl
                            .builder()
                            .url(item.getIcon())
                            .imageRadius(AppUtils.dip2px(mContext, 10))
                            .imageView(ivCard)
                            .build());
        }
        tvCardTitle.setText(item.getTitle());
        if (!TextUtils.isEmpty(item.getDesc1()) && tvCardContent1 != null)
            tvCardContent1.setText(item.getDesc1());
        if (!TextUtils.isEmpty(item.getDesc2()) && tvCardContent2 != null)
            tvCardContent2.setText(item.getDesc2());
        if (!TextUtils.isEmpty(item.getTag()) && tvCardTag != null) {
            mImageLoader.loadImage(mContext
                    , ImageConfigImpl
                            .builder()
                            .url(item.getIcon())
                            .isCenterCrop(false)
                            .imageView(tvCardTag)
                            .build());
        }

    }

    public void setHome3ButtonViewData(Home3ButtonView view, HomeChildEntity item) {
        if (!TextUtils.isEmpty(item.getIcon())) {
            mImageLoader.loadImage(mContext
                    , ImageConfigImpl
                            .builder()
                            .isCenterCrop(false)
                            .url(item.getIcon())
                            .imageView(view.getIv_img())
                            .build());
        }

        if (!TextUtils.isEmpty(item.getTag())) {
            mImageLoader.loadImage(mContext
                    , ImageConfigImpl
                            .builder()
                            .url(item.getTag())
                            .isCenterCrop(false)
                            .imageView(view.getIv_tag())
                            .build());
        }

        view.getTv_title().setText(item.getTitle());
        view.getTv_content1().setText(item.getDesc1());
        view.getTv_content2().setText(item.getDesc2());
    }

    public void setHome4ButtonViewData(Home4ButtonView view, HomeChildEntity item) {
        if (!TextUtils.isEmpty(item.getIcon())) {
            mImageLoader.loadImage(mContext
                    , ImageConfigImpl
                            .builder()
                            .url(item.getIcon())
                            .isCenterCrop(false)
                            .imageView(view.getIv_img())
                            .build());
        }

        if (!TextUtils.isEmpty(item.getTag())) {
            mImageLoader.loadImage(mContext
                    , ImageConfigImpl
                            .builder()
                            .url(item.getTag())
                            .isCenterCrop(false)
                            .imageView(view.getIv_tag())
                            .build());
        }

        view.getTv_title().setText(item.getTitle());

        view.getTv_content1().setVisibility(View.GONE);
        view.getTv_content2().setVisibility(View.GONE);
        view.getTv_content1().setText(item.getDesc1());
        view.getTv_content2().setText(item.getDesc2());

        if (!TextUtils.isEmpty(item.getDesc1()))
            view.getTv_content1().setVisibility(View.VISIBLE);
        if (!TextUtils.isEmpty(item.getDesc2()))
            view.getTv_content2().setVisibility(View.VISIBLE);

    }

    OnBannerClickListener onBannerClickListener;

    public void setOnBannerClickListener(OnBannerClickListener onBannerClickListener) {
        this.onBannerClickListener = onBannerClickListener;
    }

    public interface OnBannerClickListener {
        void onBannerClick(HomeChildEntity entity);
        void onLawyerClick(LawyerEntity2 entity2);
        void onLawyerTitleClick();
    }

}
