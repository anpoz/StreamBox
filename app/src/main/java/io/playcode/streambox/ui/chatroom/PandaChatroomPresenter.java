package io.playcode.streambox.ui.chatroom;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;

import com.blankj.aloglibrary.ALog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import io.playcode.streambox.data.bean.PandaDanmuEntity;
import io.playcode.streambox.event.PandaDanmuEvent;

/**
 * Created by anpoz on 2017/4/15.
 */

public class PandaChatroomPresenter implements PandaChatroomContract.Presenter {
    private PandaChatroomContract.View mView;
    private List<CharSequence> mCharSequenceList;
    private final ForegroundColorSpan mSpanRoleSupervisor;//超管
    private final ForegroundColorSpan mSpanRoleManager;//房管
    private final ForegroundColorSpan mSpanRoleAnchor;//主播
    private final ForegroundColorSpan mSpanRoleaudience;//观众
    private final ForegroundColorSpan mSpanNickname;//名称

    PandaDanmuEvent mPandaDanmuEvent;

    public PandaChatroomPresenter(PandaChatroomContract.View view) {
        mView = view;
        mView.setPresenter(this);
        mCharSequenceList = new ArrayList<>();

        mSpanRoleSupervisor = new ForegroundColorSpan(Color.RED);
        mSpanRoleManager = new ForegroundColorSpan(Color.BLUE);
        mSpanRoleAnchor = new ForegroundColorSpan(Color.YELLOW);
        mSpanRoleaudience = new ForegroundColorSpan(Color.GREEN);
        mSpanNickname = new ForegroundColorSpan(Color.BLACK);
    }

    @Override
    public void subscribe() {
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
    }

    @Override
    public void unSubscribe() {
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPandaDanmuEvent(PandaDanmuEvent pandaDanmuEvent) {
        if (pandaDanmuEvent == mPandaDanmuEvent) {
            return;
        }
        mPandaDanmuEvent = pandaDanmuEvent;

        String DANMU_TYPE = "1";
        String BAMBOO_TYPE = "206";
        String AUDIENCE_TYPE = "207";
        String TU_HAO_TYPE = "306";
        String MANAGER = "60";
        String SP_MANAGER = "120";
        String HOSTER = "90";

        PandaDanmuEntity danmu = pandaDanmuEvent.getPandaDanmuEntity();
        String nickname = danmu.getData().getFrom().getNickName();
        String identity = danmu.getData().getFrom().getIdentity();
        String content = danmu.getData().getContent();
        SpannableStringBuilder builder = new SpannableStringBuilder();
        if (TextUtils.equals(danmu.getType(), DANMU_TYPE)) {//弹幕
            if (TextUtils.equals(danmu.getData().getFrom().getSp_identity(), SP_MANAGER)) {//超管
                builder.append("[超管]");
                builder.append(nickname);
                builder.append(":");
                builder.append(content);
                builder.setSpan(mSpanRoleSupervisor, 0, 4, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                builder.setSpan(mSpanNickname, 4, nickname.length() + 4, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                mCharSequenceList.add(builder);
            } else if (TextUtils.equals(identity, MANAGER)) {//房管
                builder.append("[房管]");
                builder.append(nickname);
                builder.append(":");
                builder.append(content);
                builder.setSpan(mSpanRoleManager, 0, 4, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                builder.setSpan(mSpanNickname, 4, nickname.length() + 4, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                mCharSequenceList.add(builder);
            } else if (TextUtils.equals(identity, HOSTER)) {//主播
                builder.append("[主播]");
                builder.append(nickname);
                builder.append(":");
                builder.append(content);
                builder.setSpan(mSpanRoleAnchor, 0, 4, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                builder.setSpan(mSpanNickname, 4, nickname.length() + 4, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                mCharSequenceList.add(builder);
            } else {
                builder.append("[观众]");
                builder.append(nickname);
                builder.append(":");
                builder.append(content);
                builder.setSpan(mSpanRoleaudience, 0, 4, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                builder.setSpan(mSpanNickname, 4, nickname.length() + 4, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                mCharSequenceList.add(builder);
            }
            mView.addCommit(mCharSequenceList);
        } else if (TextUtils.equals(danmu.getType(), BAMBOO_TYPE)) {//竹子
            ALog.d(nickname + "赠送给主播[" + danmu.getData().getContent() + "]竹子");
        } else if (TextUtils.equals(danmu.getType(), TU_HAO_TYPE)) {//礼物
            ALog.d(nickname + "送了一波礼物");
        } else if (TextUtils.equals(danmu.getType(), AUDIENCE_TYPE)) {//人数更新
            ALog.d("实时人数：" + content);
        }
    }
}
