package io.playcode.streambox.widget

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import com.shuyu.gsyvideoplayer.GSYVideoPlayer
import com.shuyu.gsyvideoplayer.video.GSYBaseVideoPlayer
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import io.playcode.streambox.R
import io.playcode.streambox.data.bean.BaseDanmu
import kotlinx.android.synthetic.main.view_danmu_video.view.*
import master.flame.danmaku.controller.DrawHandler
import master.flame.danmaku.danmaku.model.BaseDanmaku
import master.flame.danmaku.danmaku.model.DanmakuTimer
import master.flame.danmaku.danmaku.model.IDisplayer
import master.flame.danmaku.danmaku.model.android.DanmakuContext
import master.flame.danmaku.danmaku.model.android.Danmakus
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser

/**
 * Created by anpoz on 2017/4/23.
 */

class DanmakuVideoPlayer : StandardGSYVideoPlayer {
    private lateinit var paser: BaseDanmakuParser
    private lateinit var danmakuContext: DanmakuContext
    private var danmakuShow = true

    constructor(context: Context, fullFlag: Boolean?) : super(context, fullFlag!!)

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun getLayoutId(): Int {
        return R.layout.view_danmu_video
    }

    override fun init(context: Context?) {
        super.init(context)
        initDanmaku()
        switch_danmu.setOnCheckedChangeListener { buttonView, isChecked ->
            danmakuShow = isChecked
            resolveDanmakuShow()
        }
    }

    override fun startWindowFullscreen(context: Context?, actionBar: Boolean, statusBar: Boolean): GSYBaseVideoPlayer {
        val player = super.startWindowFullscreen(context, actionBar, statusBar)
        if (player != null && player is DanmakuVideoPlayer) {
            player.danmakuShow = danmakuShow
            player.danmaku_view.prepare(paser, danmakuContext)
        }
        return player
    }

    override fun resolveNormalVideoShow(oldF: View?, vp: ViewGroup?, gsyVideoPlayer: GSYVideoPlayer?) {
        if (gsyVideoPlayer is DanmakuVideoPlayer) {
            danmakuShow = gsyVideoPlayer.danmakuShow
            resolveDanmakuShow()
            gsyVideoPlayer.danmaku_view.release()
        }
    }

    override fun onPrepared() {
        super.onPrepared()
        onPrepareDanmaku()
    }

    override fun onVideoPause() {
        super.onVideoPause()
        danmaku_view.pause()
    }

    override fun onVideoResume() {
        super.onVideoResume()
        danmaku_view.resume()
    }

    override fun onCompletion() {
        super.onCompletion()
        danmaku_view.release()
    }

    private fun initDanmaku() {
        //设置最大显示行数
        var maxLinesPair = HashMap<Int, Int>()
        maxLinesPair.put(BaseDanmaku.TYPE_SCROLL_RL, 8)
        //设置是否禁止重叠
        var overlappingEnablePair = HashMap<Int, Boolean>()
        overlappingEnablePair.put(BaseDanmaku.TYPE_SCROLL_RL, true)
        overlappingEnablePair.put(BaseDanmaku.TYPE_FIX_TOP, true)

        paser = object : BaseDanmakuParser() {
            override fun parse(): Danmakus {
                return Danmakus()
            }
        }
        danmakuContext = DanmakuContext.create()
        danmakuContext.setDanmakuStyle(IDisplayer.DANMAKU_STYLE_STROKEN, 3f)
                .setDuplicateMergingEnabled(false)
                .setScrollSpeedFactor(1.2f)
                .setScaleTextSize(1.2f)
//                .setCacheStuffer(SimpleTextCacheStuffer(), TextSufferCache())
                .setMaximumLines(maxLinesPair)
                .preventOverlapping(overlappingEnablePair)

        danmaku_view.prepare(paser, danmakuContext)
        if (danmaku_view != null) {
            danmaku_view.setCallback(object : DrawHandler.Callback {
                override fun drawingFinished() {
                }

                override fun danmakuShown(danmaku: BaseDanmaku?) {
                }

                override fun prepared() {
                    danmaku_view.start()
                }

                override fun updateTimer(timer: DanmakuTimer?) {
                }
            })
            danmaku_view.enableDanmakuDrawingCache(true)
        }
    }

    /**
     * 弹幕开关
     */
    private fun resolveDanmakuShow() {
        post {
            if (danmakuShow) {
                if (!danmaku_view.isShown)
                    danmaku_view.show()
            } else {
                if (danmaku_view.isShown)
                    danmaku_view.hide()
            }
        }
    }

    private fun onPrepareDanmaku() {
        if (danmaku_view.isPrepared) {
            danmaku_view.prepare(paser, danmakuContext)
        }
    }

    fun addDanmaku(danmu: BaseDanmu) {
        val danmaku = danmakuContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL)
        if (danmaku_view == null || danmaku == null || !danmakuShow)
            return
        danmaku.text = danmu.text
        danmaku.padding = 5
        danmaku.priority = 8
        danmaku.isLive = true
        danmaku.time = danmaku_view.currentTime + 500
        danmaku.textSize = 16f * (paser.displayer.density - 0.6f)
        when (danmu.type) {
            1 -> danmaku.textColor = Color.WHITE
            2 -> danmaku.textColor = Color.parseColor("#F06292")
            3 -> danmaku.textColor = Color.parseColor("#FF8A65")
            4 -> danmaku.textColor = Color.parseColor("#E57373")
            else -> danmaku.textColor = Color.WHITE
        }
        danmaku_view.addDanmaku(danmaku)
    }
}
