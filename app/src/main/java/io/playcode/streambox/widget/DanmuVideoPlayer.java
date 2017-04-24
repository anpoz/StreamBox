package io.playcode.streambox.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import io.playcode.streambox.R;

/**
 * Created by anpoz on 2017/4/23.
 */

public class DanmuVideoPlayer extends StandardGSYVideoPlayer {
    public DanmuVideoPlayer(Context context, Boolean fullFlag) {
        super(context, fullFlag);
    }

    public DanmuVideoPlayer(Context context) {
        super(context);
    }

    public DanmuVideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public int getLayoutId() {
        if (mIfCurrentIsFullscreen) {
            return R.layout.widget_video_land;
        }
        return super.getLayoutId();
    }
}
