package com.imnexerio.eyeris

import android.content.Context
import android.util.Log
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.imnexerio.eyeris.databinding.FragmentCameraBinding

import java.util.concurrent.ExecutorService

object CameraUtils {
    private var _fragmentCameraBinding: FragmentCameraBinding? = null
    private val fragmentCameraBinding
        get() = _fragmentCameraBinding!!


    fun setUpCamera(
        context: Context,
        lifecycleOwner: LifecycleOwner,
        backgroundExecutor: ExecutorService,
        cameraFacing: Int,
        previewView: PreviewView,
        analyzer: ImageAnalysis.Analyzer,
        cameraProviderCallback: (ProcessCameraProvider) -> Unit
    ) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
        cameraProviderFuture.addListener(
            {
                val cameraProvider = cameraProviderFuture.get()
                cameraProviderCallback(cameraProvider)

                val preview = Preview.Builder().build().also {
                    it.setSurfaceProvider(previewView.surfaceProvider)
                }

                val cameraSelector = CameraSelector.Builder()
                    .requireLensFacing(cameraFacing)
                    .build()

                val imageAnalyzer = ImageAnalysis.Builder()
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .setOutputImageFormat(ImageAnalysis.OUTPUT_IMAGE_FORMAT_RGBA_8888)
                    .build()
                    .also {
                        it.setAnalyzer(backgroundExecutor, analyzer)
                    }

                try {
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        lifecycleOwner, cameraSelector, preview, imageAnalyzer
                    )
                } catch (exc: Exception) {
                    Log.e("CameraUtils", "Use case binding failed", exc)
                }
            }, ContextCompat.getMainExecutor(context)
        )
    }


    fun bindPreview(
        context: Context,
        cameraProvider: ProcessCameraProvider,
        lifecycleOwner: LifecycleOwner,
        cameraFacing: Int,
        previewView: PreviewView
    ) {
        val preview = Preview.Builder().build().also {
            it.setSurfaceProvider(previewView.surfaceProvider)
        }

        val cameraSelector = CameraSelector.Builder()
            .requireLensFacing(cameraFacing)
            .build()

        try {
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                lifecycleOwner, cameraSelector, preview
            )
        } catch (exc: Exception) {
            Log.e("CameraUtils", "Use case binding failed", exc)
        }
    }

    fun unbindPreview(cameraProvider: ProcessCameraProvider?) {
        cameraProvider?.unbindAll()
    }

    fun bindImageAnalysisOnly(
        context: Context,
        cameraProvider: ProcessCameraProvider,
        lifecycleOwner: LifecycleOwner,
        backgroundExecutor: ExecutorService,
        cameraFacing: Int,
        analyzer: ImageAnalysis.Analyzer
    ) {
        val cameraSelector = CameraSelector.Builder()
            .requireLensFacing(cameraFacing)
            .build()

        val imageAnalyzer = ImageAnalysis.Builder()
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .setOutputImageFormat(ImageAnalysis.OUTPUT_IMAGE_FORMAT_RGBA_8888)
            .build()
            .also {
                it.setAnalyzer(backgroundExecutor, analyzer)
            }

        try {
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                lifecycleOwner, cameraSelector, imageAnalyzer
            )
        } catch (exc: Exception) {
            Log.e("CameraUtils", "Use case binding failed", exc)
        }
    }
}
