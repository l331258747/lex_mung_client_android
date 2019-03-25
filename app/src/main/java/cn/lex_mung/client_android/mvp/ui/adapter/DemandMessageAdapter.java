package cn.lex_mung.client_android.mvp.ui.adapter;

import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.app.TimeFormat;
import cn.lex_mung.client_android.mvp.model.api.Api;
import cn.lex_mung.client_android.mvp.model.entity.DemandMessageEntity;
import com.zl.mvp.http.imageloader.glide.ImageConfigImpl;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.zl.mvp.utils.AppUtils;

public class DemandMessageAdapter extends BaseQuickAdapter<DemandMessageEntity.RequirementBean, BaseViewHolder> {
    private ImageLoader mImageLoader;
    private int count1 = 0;

    public DemandMessageAdapter(ImageLoader imageLoader) {
        super(R.layout.item_demand_message);
        this.mImageLoader = imageLoader;
    }

    public void setCount1(int count1) {
        this.count1 = count1;
    }

    @Override
    protected void convert(BaseViewHolder helper, DemandMessageEntity.RequirementBean item) {
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
        helper.setText(R.id.item_tv_title, item.getMemberName());
        Conversation conversation = JMessageClient.getSingleConversation("lex" + item.getLawyerMemberId(), Api.J_PUSH);
        if (conversation != null) {
            Message lastMsg = conversation.getLatestMessage();
            if (lastMsg != null) {
                helper.setText(R.id.item_tv_time, TimeFormat.getTime(lastMsg.getCreateTime()));
                switch (lastMsg.getContentType()) {
                    case voice:
                        helper.setText(R.id.item_tv_content, R.string.text_message_voice);
                        break;
                    case text:
                        helper.setText(R.id.item_tv_content, ((TextContent) lastMsg.getContent()).getText());
                        break;
                    case location:
                        helper.setText(R.id.item_tv_content, R.string.text_message_location);
                        break;
                    case custom:
                        helper.setText(R.id.item_tv_content, R.string.text_message_system);
                        break;
                    case image:
                        helper.setText(R.id.item_tv_content, R.string.text_message_image);
                        break;
                }
            } else {
                helper.setText(R.id.item_tv_content, R.string.text_message_tip);
            }
        }
        switch (item.getIsReceipt()) {
            case 0:
                if (item.getIsRead() == 0) {
                    helper.getView(R.id.item_tv_num).setVisibility(View.VISIBLE);
                    helper.setText(R.id.item_tv_num, "1");
                } else {
                    helper.getView(R.id.item_tv_num).setVisibility(View.GONE);
                }

                helper.setText(R.id.item_tv_time, R.string.text_wait_order);
                helper.setTextColor(R.id.item_tv_time, AppUtils.getColor(mContext, R.color.c_ea5514));
                helper.getView(R.id.item_iv_icon).setVisibility(View.VISIBLE);
                helper.setText(R.id.item_tv_content, R.string.text_message_tip_1);
                break;
            case 1:
                helper.getView(R.id.item_iv_icon).setVisibility(View.GONE);
                helper.setTextColor(R.id.item_tv_time, AppUtils.getColor(mContext, R.color.c_b5b5b5));

                if (conversation != null) {
                    Message lastMsg = conversation.getLatestMessage();
                    if (lastMsg != null) {
                        helper.setText(R.id.item_tv_time, TimeFormat.getTime(lastMsg.getCreateTime()));
                    } else {
                        helper.setText(R.id.item_tv_time, R.string.text_already_order);
                    }
                } else {
                    helper.setText(R.id.item_tv_time, R.string.text_already_order);
                }
                if (conversation != null
                        && conversation.getUnReadMsgCnt() > 0) {
                    helper.getView(R.id.item_tv_num).setVisibility(View.VISIBLE);
                    helper.setText(R.id.item_tv_num, conversation.getUnReadMsgCnt() + "");
                } else {
                    helper.getView(R.id.item_tv_num).setVisibility(View.GONE);
                }
                break;
            case 2:
                helper.getView(R.id.item_tv_num).setVisibility(View.GONE);
                helper.setText(R.id.item_tv_time, R.string.text_already_refuse);
                helper.setTextColor(R.id.item_tv_time, AppUtils.getColor(mContext, R.color.c_b5b5b5));
                helper.getView(R.id.item_iv_icon).setVisibility(View.GONE);
                break;
            case 3:
                helper.getView(R.id.item_tv_num).setVisibility(View.GONE);
                helper.setText(R.id.item_tv_time, R.string.text_already_close);
                helper.setTextColor(R.id.item_tv_time, AppUtils.getColor(mContext, R.color.c_b5b5b5));
                helper.getView(R.id.item_iv_icon).setVisibility(View.GONE);
                break;
        }
    }

    /**
     * 收到消息后将会话置顶
     *
     * @param mConversation 要置顶的会话
     */
    public void setToTop(Conversation mConversation) {
        int oldCount = 0;
        DemandMessageEntity.RequirementBean bean = null;
        for (int i = 0; i < mData.size(); i++) {
            UserInfo userInfo = (UserInfo) mConversation.getTargetInfo();
            int id = Integer.valueOf(userInfo.getUserName().replace("lex", ""));
            if (id == mData.get(i).getSourceMemberId()) {
                oldCount = i;
                bean = mData.get(i);
                break;
            }
        }
        if (bean != null) {
            mData.remove(oldCount);
            mData.add(count1, bean);
            notifyDataSetChanged();
        }
        notifyDataSetChanged();
    }
}
