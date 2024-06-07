package com.imnexerio.eyeris

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.google.mediapipe.tasks.vision.core.RunningMode
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import com.google.mediapipe.tasks.components.containers.Category
import com.google.mediapipe.tasks.vision.facelandmarker.FaceLandmarkerResult

class FaceLandmarkerService : Service(), FaceLandmarkerHelper.LandmarkerListener {

    companion object {
        private const val TAG = "FaceLandmarkerService"
        private const val CHANNEL_ID = "FaceLandmarkerServiceChannel"
        private const val NOTIFICATION_ID = 1

        lateinit var faceLandmarkerHelper: FaceLandmarkerHelper
        private var cameraFacing = CameraSelector.LENS_FACING_FRONT

        fun analyzeImage(imageProxy: ImageProxy) {
            faceLandmarkerHelper.detectLiveStream(
                imageProxy = imageProxy,
                isFrontCamera = cameraFacing == CameraSelector.LENS_FACING_FRONT
            )
        }
    }

    private lateinit var backgroundExecutor: ExecutorService
    private var imageAnalyzer: ImageAnalysis? = null
    private var cameraProvider: ProcessCameraProvider? = null

    private lateinit var lifecycleOwner: ServiceLifecycleOwner

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        startForegroundService()

        // Initialize our background executor
        backgroundExecutor = Executors.newSingleThreadExecutor()

        // Initialize the FaceLandmarkerHelper
        backgroundExecutor.execute {
            faceLandmarkerHelper = FaceLandmarkerHelper(
                context = this,
                runningMode = RunningMode.LIVE_STREAM,
                minFaceDetectionConfidence = 0.5f,
                minFaceTrackingConfidence = 0.5f,
                minFacePresenceConfidence = 0.5f,
                maxNumFaces = 1,
                currentDelegate = FaceLandmarkerHelper.DELEGATE_CPU,
                faceLandmarkerHelperListener = this
            )
            setUpCamera()
        }

        lifecycleOwner = ServiceLifecycleOwner()
        lifecycleOwner.lifecycleRegistry.currentState = Lifecycle.State.STARTED
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val serviceChannel = NotificationChannel(
            CHANNEL_ID,
            "Face Landmarker Service Channel",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(serviceChannel)
    }

    private fun startForegroundService() {
        val notification: Notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Face Landmarker Service")
            .setContentText("Running face detection in the background")
            .setSmallIcon(R.drawable.baseline_analytics_24)
            .build()
        startForeground(NOTIFICATION_ID, notification)
    }

    private fun setUpCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener(
            {
                cameraProvider = cameraProviderFuture.get()

                // Select the camera
                val cameraSelector = CameraSelector.Builder()
                    .requireLensFacing(cameraFacing)
                    .build()

                // Build the image analysis use case
                imageAnalyzer = ImageAnalysis.Builder()
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .setOutputImageFormat(ImageAnalysis.OUTPUT_IMAGE_FORMAT_RGBA_8888)
                    .build()
                    .also {
                        it.setAnalyzer(backgroundExecutor, FaceLandmarkerService::analyzeImage)
                    }

                // Unbind use cases before rebinding
                cameraProvider?.unbindAll()

                try {
                    cameraProvider?.bindToLifecycle(
                        lifecycleOwner,
                        cameraSelector,
                        imageAnalyzer
                    )
                } catch (exc: Exception) {
                    Log.e(TAG, "Use case binding failed", exc)
                }
            },
            ContextCompat.getMainExecutor(this)
        )
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        backgroundExecutor.shutdown()
    }

    override fun onError(error: String, errorCode: Int) {
        Log.e(TAG, "Error: $error (Code: $errorCode)")
    }

    private var categories: MutableList<Category?> = MutableList(2) { null }

    fun updateResults(faceLandmarkerResult: FaceLandmarkerResult? = null) {
        categories = MutableList(2) { null }
        if (faceLandmarkerResult != null) {
            val sortedCategories = faceLandmarkerResult.faceBlendshapes().get()[0]

            categories[0]=sortedCategories[9]
            categories[1]=sortedCategories[10]
        }
    }


    // ...

    override fun onResults(resultBundle: FaceLandmarkerHelper.ResultBundle) {
//        Log.i(TAG, "FaceLandmarkerService: Results: ${resultBundle.result}")
        // Update the overlay view if it's active
        OverlayManager.updateOverlay(
            resultBundle.result,
            resultBundle.inputImageHeight,
            resultBundle.inputImageWidth,
            RunningMode.LIVE_STREAM
        )

        // Update the categories with the result
        updateResults(resultBundle.result)
        Log.i(TAG, "Blink : ${categories}")

    }



    class ServiceLifecycleOwner : LifecycleOwner {
        val lifecycleRegistry = LifecycleRegistry(this)

        override fun getLifecycle(): Lifecycle {
            return lifecycleRegistry
        }
    }

}
