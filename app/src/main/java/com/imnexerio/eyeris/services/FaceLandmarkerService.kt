package com.imnexerio.eyeris.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
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
import com.imnexerio.eyeris.MainActivity
import com.imnexerio.eyeris.helpers.BlinkDatabaseHelper
import com.imnexerio.eyeris.helpers.FaceLandmarkerHelper
import com.imnexerio.eyeris.helpers.OverlayManager
import com.imnexerio.eyeris.R
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.Timer
import java.util.TimerTask



class FaceLandmarkerService : Service(), FaceLandmarkerHelper.LandmarkerListener {

    companion object {
        private const val TAG = "EyerisService"
        private const val CHANNEL_ID = "EyerisServiceChannel"
        private const val NOTIFICATION_ID = 1
        private const val BLINK_RATE_THRESHOLD = 10 // Example threshold value

        lateinit var faceLandmarkerHelper: FaceLandmarkerHelper
        private var cameraFacing = CameraSelector.LENS_FACING_FRONT

        fun analyzeImage(imageProxy: ImageProxy) {
            faceLandmarkerHelper.detectLiveStream(
                imageProxy = imageProxy,
                isFrontCamera = cameraFacing == CameraSelector.LENS_FACING_FRONT
            )
        }
    }
//    inner class LocalBinder : Binder() {
//        fun getService(): FaceLandmarkerService = this@FaceLandmarkerService
//    }
//
//    private val binder = LocalBinder()

//    private var dataUpdateListener: DataUpdateListener? = null

    private lateinit var backgroundExecutor: ExecutorService
    private var imageAnalyzer: ImageAnalysis? = null
    private var cameraProvider: ProcessCameraProvider? = null
    private lateinit var lifecycleOwner: ServiceLifecycleOwner

    private lateinit var databaseHelper: BlinkDatabaseHelper
    private var timer: Timer? = null
    private var lefteyeopencount = 0
    private var righteyeopencount = 0
    private var lefteyeclosedcount = 0
    private var righteyeclosedcount = 0

    override fun onCreate() {
        super.onCreate()
        databaseHelper = BlinkDatabaseHelper(this)
        createNotificationChannel()
        startForegroundService()
        startDataCollectionTimer()

        backgroundExecutor = Executors.newSingleThreadExecutor()

        backgroundExecutor.execute {
            val minFaceDetectionConfidence = getStoredValue("detection_threshold",
                FaceLandmarkerHelper.DEFAULT_FACE_DETECTION_CONFIDENCE
            )
            val minFaceTrackingConfidence = getStoredValue("tracking_threshold",
                FaceLandmarkerHelper.DEFAULT_FACE_TRACKING_CONFIDENCE
            )
            val minFacePresenceConfidence = getStoredValue("presence_threshold",
                FaceLandmarkerHelper.DEFAULT_FACE_PRESENCE_CONFIDENCE
            )
            val currentDelegate = getStoredIntValue("spinner_delegate",
                FaceLandmarkerHelper.DELEGATE_CPU
            )

            faceLandmarkerHelper = FaceLandmarkerHelper(
                context = this,
                runningMode = RunningMode.LIVE_STREAM,
                minFaceDetectionConfidence = minFaceDetectionConfidence,
                minFaceTrackingConfidence = minFaceTrackingConfidence,
                minFacePresenceConfidence = minFacePresenceConfidence,
                maxNumFaces = 1,
                currentDelegate = currentDelegate,
                faceLandmarkerHelperListener = this
            )

//            Log.i(TAG, "FaceLandmarkerHelper created, minFaceDetectionConfidence: $minFaceDetectionConfidence, minFaceTrackingConfidence: $minFaceTrackingConfidence, minFacePresenceConfidence: $minFacePresenceConfidence")
//        backgroundExecutor.execute {
//            faceLandmarkerHelper = FaceLandmarkerHelper(
//                context = this,
//                runningMode = RunningMode.LIVE_STREAM,
//                minFaceDetectionConfidence = 0.5f,
//                minFaceTrackingConfidence = 0.5f,
//                minFacePresenceConfidence = 0.5f,
//                maxNumFaces = 1,
//                currentDelegate = FaceLandmarkerHelper.DELEGATE_CPU,
//                faceLandmarkerHelperListener = this
//            )
            setUpCamera()
        }

        lifecycleOwner = ServiceLifecycleOwner()
        lifecycleOwner.setCurrentState(Lifecycle.State.STARTED)

//        lifecycleOwner = ServiceLifecycleOwner()
//        lifecycleOwner.lifecycleRegistry.currentState = Lifecycle.State.STARTED
    }

    private fun createNotificationChannel() {
        val serviceChannel = NotificationChannel(
            CHANNEL_ID,
            "Eyeris Service Channel",
            NotificationManager.IMPORTANCE_MIN
        )
        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(serviceChannel)
    }

    private fun startForegroundService() {
        val notification: Notification = createNotification(true)
        startForeground(NOTIFICATION_ID, notification)
    }

//    private fun createNotification(isCameraActive: Boolean): Notification {
//        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
//            .setContentTitle("Eyeris Service")
//            .setContentText("Detecting blinks in background 😊")
//            .setSmallIcon(R.mipmap.ic_launcher_round)
//
//
//        if (isCameraActive) {
//            builder.addAction(stopCameraAction)
//        } else {
//            builder.addAction(startCameraAction)
//        }
//
//        return builder.build()
//    }

    // In `FaceLandmarkerService.kt`

    private fun createNotification(isCameraActive: Boolean): Notification {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("navigateTo", "AnalyticsFragment")
        }
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Eyeris Service")
            .setContentText("Detecting blinks in background 😊")
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentIntent(pendingIntent)  // Set the pending intent to the notification
            .setAutoCancel(true)

        if (isCameraActive) {
            builder.addAction(stopCameraAction)
        } else {
            builder.addAction(startCameraAction)
        }

        return builder.build()
    }

//    private val stopCameraAction: NotificationCompat.Action
//        get() {
//            val intent = Intent(this, FaceLandmarkerService::class.java).apply {
//                action = "ACTION_STOP_CAMERA"
//            }
//            val pendingIntent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
//            return NotificationCompat.Action.Builder(R.drawable.baseline_pause_24, "Stop", pendingIntent).build()
//        }
//
//    private val startCameraAction: NotificationCompat.Action
//        get() {
//            val intent = Intent(this, FaceLandmarkerService::class.java).apply {
//                action = "ACTION_START_CAMERA"
//            }
//            val pendingIntent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
//            return NotificationCompat.Action.Builder(R.drawable.baseline_play_arrow_24, "Start", pendingIntent).build()
//        }

    private val stopCameraAction: NotificationCompat.Action
    get() {
        val intent = Intent(this, FaceLandmarkerService::class.java).apply {
            action = "ACTION_STOP_CAMERA"
        }
        val pendingIntent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        return NotificationCompat.Action.Builder(0, "Stop", pendingIntent).build()
    }

private val startCameraAction: NotificationCompat.Action
    get() {
        val intent = Intent(this, FaceLandmarkerService::class.java).apply {
            action = "ACTION_START_CAMERA"
        }
        val pendingIntent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        return NotificationCompat.Action.Builder(0, "Start", pendingIntent).build()
    }

    private fun updateNotification(isCameraActive: Boolean) {
        val notificationManager = getSystemService(NotificationManager::class.java) as NotificationManager
        val notification = createNotification(isCameraActive)
        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            "ACTION_STOP_CAMERA" -> {
                stopCamera()
                updateNotification(false)
            }
            "ACTION_START_CAMERA" -> {
                startCamera()
                updateNotification(true)
            }
        }
        return START_NOT_STICKY
    }

    private fun startCamera() {
        if (!::backgroundExecutor.isInitialized || backgroundExecutor.isShutdown) {
            backgroundExecutor = Executors.newSingleThreadExecutor()
        }
        setUpCamera()
    }

    private fun stopCamera() {
        cameraProvider?.unbindAll()
        if (::backgroundExecutor.isInitialized) {
            backgroundExecutor.shutdownNow()
        }
        stopSelf()
    }

    private fun setUpCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            cameraProvider = cameraProviderFuture.get()
            val cameraSelector = CameraSelector.Builder()
                .requireLensFacing(cameraFacing)
                .build()

            imageAnalyzer = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .setOutputImageFormat(ImageAnalysis.OUTPUT_IMAGE_FORMAT_RGBA_8888)
                .build()
                .also {
                    it.setAnalyzer(backgroundExecutor, Companion::analyzeImage)
                }

            cameraProvider?.unbindAll()
            try {
                cameraProvider?.bindToLifecycle(
                    lifecycleOwner,
                    cameraSelector,
                    imageAnalyzer
                )
            } catch (exc: Exception) {
//                Log.e(TAG, "Use case binding failed", exc)
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private fun startDataCollectionTimer() {
        timer = Timer()
        timer?.schedule(object : TimerTask() {
            override fun run() {
                storeBlinkData()
            }
        }, 60000, 60000) // 1 minute interval
    }

    private fun storeBlinkData() {
        val currentTime = System.currentTimeMillis()
        val db = databaseHelper.writableDatabase
        db.beginTransaction()
        try {
            val totalBlinks = lefteyeclosedcount + righteyeclosedcount
//            if (totalBlinks < BLINK_RATE_THRESHOLD) {
//                triggerBlinkRateNotification()
//                triggerVibration()
//            }

//            Log.i(TAG, "Blink : $lefteyeopencount, $lefteyeclosedcount, $righteyeopencount, $righteyeclosedcount")

            val contentValues = ContentValues().apply {
                put("timestamp", currentTime)
                put("left_open", lefteyeopencount)
                put("left_closed", lefteyeclosedcount)
                put("right_open", righteyeopencount)
                put("right_closed", righteyeclosedcount)
            }
            db.insert("blink_data", null, contentValues)
            db.setTransactionSuccessful()
        } catch (e: Exception) {
//            Log.i(TAG, "Blink : some error occurred while storing data")
//            Log.i(TAG, "Blink : $lefteyeopencount")
//            Log.i(TAG, "Blink : $lefteyeclosedcount")
//            Log.i(TAG, "Blink : $righteyeopencount")
//            Log.i(TAG, "Blink : $righteyeclosedcount")
        } finally {
            db.endTransaction()
//            Log.i(TAG, "Stored data")
            lefteyeopencount = 0
            lefteyeclosedcount = 0
            righteyeopencount = 0
            righteyeclosedcount = 0
        }
    }

//    private fun triggerBlinkRateNotification() {
//        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
//            .setContentTitle("Low Blink Rate Alert")
//            .setContentText("Your blink rate is below the normal threshold. Please take a break.")
//            .setSmallIcon(R.mipmap.ic_launcher_round)
//            .build()
//        notificationManager.notify(NOTIFICATION_ID + 1, notification)
//    }

//    private fun triggerVibration() {
//        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
//        vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
//    }

    override fun onDestroy() {
        super.onDestroy()
//        Log.i(TAG, "Service destroyed")
        timer?.cancel()
        stopCamera()
        storeBlinkData()
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
//        Log.i(TAG, "Task removed")
        onDestroy()
    }

    override fun onResults(resultBundle: FaceLandmarkerHelper.ResultBundle) {

        val faceBlendshapes = resultBundle.result.faceBlendshapes()?.get()?.get(0)
        val categories = listOf(faceBlendshapes?.get(9), faceBlendshapes?.get(10))
//        Log.i(TAG, "categories : $categories")
        val leftBlinkScore = categories.get(0)?.score() ?: 0.0f
        val rightBlinkScore = categories.get(1)?.score() ?: 0.0f

//        dataUpdateListener?.onDataUpdate(leftBlinkScore, rightBlinkScore)

        if(leftBlinkScore > 0.3) {
            lefteyeclosedcount++
        }
        else {
            lefteyeopencount++
        }
        if(rightBlinkScore > 0.3) {
            righteyeclosedcount++
        }
        else {
            righteyeopencount++
        }

        OverlayManager.updateOverlay(
            resultBundle.result,
            resultBundle.inputImageHeight,
            resultBundle.inputImageWidth,
            RunningMode.LIVE_STREAM
        )


    }


    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onError(error: String, errorCode: Int) {
//        Log.e(TAG, "Error: $error (Code: $errorCode)")
    }

//    class ServiceLifecycleOwner : LifecycleOwner {
//        val lifecycleRegistry = LifecycleRegistry(this)
//        override fun getLifecycle(): Lifecycle {
//            return lifecycleRegistry
//        }
//    }

//    class ServiceLifecycleOwner : LifecycleOwner {
//        private val lifecycleRegistry = LifecycleRegistry(this)
//
//        override val lifecycle: Lifecycle
//            get() = lifecycleRegistry
//    }

    class ServiceLifecycleOwner : LifecycleOwner {
        private val lifecycleRegistry = LifecycleRegistry(this)

        override val lifecycle: Lifecycle
            get() = lifecycleRegistry

        fun setCurrentState(state: Lifecycle.State) {
            lifecycleRegistry.currentState = state
        }
    }

    private fun getStoredValue(key: String, defaultValue: Float): Float {
        val sharedPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE)
        return sharedPreferences.getFloat(key, defaultValue)
    }

    private fun getStoredIntValue(key: String, defaultValue: Int): Int {
        val sharedPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE)
        return sharedPreferences.getInt(key, defaultValue)
    }
}
