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
import io.playcode.streambox.event.StreamInfoEvent;

/**
 * Created by anpoz on 2017/4/15.
 */

public class ChatroomPresenter implements ChatroomContract.Presenter {
    private ChatroomContract.View mView;
    private List<CharSequence> mCharSequenceList;
    private final ForegroundColorSpan mSpanRoleSupervisor;//超管
    private final ForegroundColorSpan mSpanRoleManager;//房管
    private final ForegroundColorSpan mSpanRoleAnchor;//主播
    private final ForegroundColorSpan mSpanNickname;//名称
    private final ForegroundColorSpan mSpanBamboo;//名称

    private String live_id;

    private String last_content;
    private static final String DANMU_TYPE = "1";
    private static final String BAMBOO_TYPE = "206";
    private static final String AUDIENCE_TYPE = "207";
    private static final String TU_HAO_TYPE = "306";
    private static final String MANAGER = "60";
    private static final String SP_MANAGER = "120";
    private static final String HOSTER = "90";

    public ChatroomPresenter(ChatroomContract.View view) {
        mView = view;
        mView.setPresenter(this);
        mCharSequenceList = new ArrayList<>();

        mSpanRoleSupervisor = new ForegroundColorSpan(Color.parseColor("#E57373"));
        mSpanRoleManager = new ForegroundColorSpan(Color.parseColor("#FF8A65"));
        mSpanRoleAnchor = new ForegroundColorSpan(Color.parseColor("#F06292"));
        mSpanNickname = new ForegroundColorSpan(Color.parseColor("#607D8B"));
        mSpanBamboo = new ForegroundColorSpan(Color.parseColor("#BA68C8"));
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

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onStreamInfoEvent(StreamInfoEvent event) {
        live_id = event.getStreamInfoEntity().getLive_id();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPandaDanmuEvent(PandaDanmuEvent pandaDanmuEvent) {
        if (TextUtils.equals(pandaDanmuEvent.getPandaDanmuEntity().getData().getContent(), last_content) &&
                TextUtils.equals(pandaDanmuEvent.getPandaDanmuEntity().getType(), DANMU_TYPE)) {
            return;
        }
        last_content = pandaDanmuEvent.getPandaDanmuEntity().getData().getContent();

        //避免串房
        if (!TextUtils.equals(live_id, pandaDanmuEvent.getLive_id())) {
            return;
        }

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
                builder.append(nickname);
                builder.append(":");
                builder.append(content);
                builder.setSpan(mSpanNickname, 0, nickname.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                mCharSequenceList.add(builder);
            }
        } else if (TextUtils.equals(danmu.getType(), BAMBOO_TYPE)) {//竹子
            builder.append(nickname);
            builder.append("赠送给主播【");
            builder.append(content);
            builder.append("】个竹子");
            builder.setSpan(mSpanNickname, 0, nickname.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            builder.setSpan(mSpanBamboo, nickname.length() + 6, content.length() + nickname.length() + 6, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            mCharSequenceList.add(builder);
        } else if (TextUtils.equals(danmu.getType(), TU_HAO_TYPE)) {//礼物
            ALog.d(nickname + "送了一波礼物");
        } else if (TextUtils.equals(danmu.getType(), AUDIENCE_TYPE)) {//人数更新
            builder.append("实时人数更新[");
            builder.append(content);
            builder.append("]人");
            builder.setSpan(mSpanNickname, 7, content.length() + 7, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            mCharSequenceList.add(builder);
        }
        mView.addCommit(mCharSequenceList);
    }
}
