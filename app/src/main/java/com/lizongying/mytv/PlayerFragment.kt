package com.lizongying.mytv

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.annotation.OptIn
import androidx.fragment.app.Fragment
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.VideoSize
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.DefaultRenderersFactory
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.google.android.exoplayer2.SimpleExoPlayer
import com.lizongying.mytv.databinding.PlayerBinding
import com.lizongying.mytv.models.TVViewModel

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;
import com.lizongying.mytv.IjkUtil;

class PlayerFragment : Fragment(), SurfaceHolder.Callback {

    private var _binding: PlayerBinding? = null
    private var tvViewModel: TVViewModel? = null
    private val aspectRatio = 16f / 9f


    private lateinit var surfaceView: SurfaceView
    private lateinit var surfaceHolder: SurfaceHolder
    private var ijkUtil: IjkUtil? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PlayerBinding.inflate(inflater, container, false)

        surfaceView = _binding!!.surfaceView
        surfaceHolder = surfaceView.holder
        surfaceHolder.addCallback(this)

        ijkUtil = IjkUtil.getInstance();
        ijkUtil?.setOnErrorListener(TAG) { what, extra ->
            Log.e(TAG, "PlaybackException what=" + what + " extra=" + extra)
            val err = "播放错误"
            tvViewModel?.setErrInfo(err)
            tvViewModel?.changed("retry")
        }

        ijkUtil?.setOnPreparedListener(TAG) {
            tvViewModel?.setErrInfo("")
        }
 
        (activity as MainActivity).fragmentReady(TAG)
        return _binding!!.root
    }

    @OptIn(UnstableApi::class)
    fun play(tvViewModel: TVViewModel) {
        this.tvViewModel = tvViewModel
        ijkUtil?.reset()
        ijkUtil?.setDisplay(surfaceHolder)
        ijkUtil?.setDataSource(tvViewModel.getVideoUrlCurrent())
        ijkUtil?.prepareAsync()
    }

    override fun onStart() {
        Log.i(TAG, "onStart")
        super.onStart()
    }

    override fun onResume() {
        Log.i(TAG, "onResume")
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        if (ijkUtil?.isPlaying() == true) {
            ijkUtil?.stop()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        ijkUtil?.release()
        _binding = null
    }

    companion object {
        private const val TAG = "PlayerFragment"
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        Log.i(TAG, "surfaceCreated")
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
    }
}