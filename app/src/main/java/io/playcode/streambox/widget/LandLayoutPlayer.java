package io.playcode.streambox.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import io.playcode.streambox.R;

/**
 * Created by anpoz on 2017/4/23.
 */

public class LandLayoutPlayer extends StandardGSYVideoPlayer {
    public LandLayoutPlayer(Context context, Boolean fullFlag) {
        super(context, fullFlag);
    }

    public LandLayoutPlayer(Context context) {
        super(context);
    }

    public LandLayoutPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public int getLayoutId() {
        if (mIfCurrentIsFullscreen) {
            return R.layout.widget_video_land;
        }
        return R.layout.widget_video;
    }
}
