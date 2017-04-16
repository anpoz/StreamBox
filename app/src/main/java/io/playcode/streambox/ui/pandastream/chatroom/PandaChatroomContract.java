package io.playcode.streambox.ui.pandastream.chatroom;

import android.text.Spanned;

import java.util.List;

import io.playcode.streambox.ui.base.BasePresenter;
import io.playcode.streambox.ui.base.BaseView;

/**
 * Created by anpoz on 2017/4/15.
 */

public interface PandaChatroomContract {
    interface Presenter extends BasePresenter {

    }

    interface View extends BaseView<Presenter> {
        void addDanmu();
        void addCommit(List<CharSequence> charSequenceList);
    }
}
