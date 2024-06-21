package com.imnexerio.eyeris

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.ContentValues
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
import java.util.Timer
import java.util.TimerTask



//class FaceLandmarkerService : Service(), FaceLandmarkerHelper.LandmarkerListener {
//
//    companion object {
//        private const val TAG = "FaceLandmarkerService"
//        private const val CHANNEL_ID = "FaceLandmarkerServiceChannel"
//        private const val NOTIFICATION_ID = 1
//
//        lateinit var faceLandmarkerHelper: FaceLandmarkerHelper
//        private var cameraFacing = CameraSelector.LENS_FACING_FRONT
//
//        fun analyzeImage(imageProxy: ImageProxy) {
//            faceLandmarkerHelper.detectLiveStream(
//                imageProxy = imageProxy,
//                isFrontCamera = cameraFacing == CameraSelector.LENS_FACING_FRONT
//            )
//        }
//    }
//
//    private lateinit var backgroundExecutor: ExecutorService
//    private var imageAnalyzer: ImageAnalysis? = null
//    private var cameraProvider: ProcessCameraProvider? = null
//
//    private lateinit var lifecycleOwner: ServiceLifecycleOwner
//
//    @RequiresApi(Build.VERSION_CODES.O)
//    override fun onCreate() {
//        super.onCreate()
//        createNotificationChannel()
//        startForegroundService()
//
//        // Initialize our background executor
//        backgroundExecutor = Executors.newSingleThreadExecutor()
//
//        // Initialize the FaceLandmarkerHelper
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
//            setUpCamera()
//        }
//
//        lifecycleOwner = ServiceLifecycleOwner()
//        lifecycleOwner.lifecycleRegistry.currentState = Lifecycle.State.STARTED
//    }
//
//    @RequiresApi(Build.VERSION_CODES.O)
//    private fun createNotificationChannel() {
//        val serviceChannel = NotificationChannel(
//            CHANNEL_ID,
//            "Face Landmarker Service Channel",
//            NotificationManager.IMPORTANCE_DEFAULT
//        )
//        val manager = getSystemService(NotificationManager::class.java)
//        manager.createNotificationChannel(serviceChannel)
//    }
//
//    private fun startForegroundService() {
//        val notification: Notification = NotificationCompat.Builder(this, CHANNEL_ID)
//            .setContentTitle("Face Landmarker Service")
//            .setContentText("Running face detection in the background")
//            .setSmallIcon(R.drawable.baseline_analytics_24)
//            .build()
//        startForeground(NOTIFICATION_ID, notification)
//    }
//
//    private fun setUpCamera() {
//        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
//        cameraProviderFuture.addListener(
//            {
//                cameraProvider = cameraProviderFuture.get()
//
//                // Select the camera
//                val cameraSelector = CameraSelector.Builder()
//                    .requireLensFacing(cameraFacing)
//                    .build()
//
//                // Build the image analysis use case
//                imageAnalyzer = ImageAnalysis.Builder()
//                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
//                    .setOutputImageFormat(ImageAnalysis.OUTPUT_IMAGE_FORMAT_RGBA_8888)
//                    .build()
//                    .also {
//                        it.setAnalyzer(backgroundExecutor, FaceLandmarkerService::analyzeImage)
//                    }
//
//                // Unbind use cases before rebinding
//                cameraProvider?.unbindAll()
//
//                try {
//                    cameraProvider?.bindToLifecycle(
//                        lifecycleOwner,
//                        cameraSelector,
//                        imageAnalyzer
//                    )
//                } catch (exc: Exception) {
//                    Log.e(TAG, "Use case binding failed", exc)
//                }
//            },
//            ContextCompat.getMainExecutor(this)
//        )
//    }
//
//    override fun onBind(intent: Intent?): IBinder? {
//        return null
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        backgroundExecutor.shutdown()
//    }
//
//    override fun onError(error: String, errorCode: Int) {
//        Log.e(TAG, "Error: $error (Code: $errorCode)")
//    }
//
//    private var categories: MutableList<Category?> = MutableList(2) { null }
//
//    fun updateResults(faceLandmarkerResult: FaceLandmarkerResult? = null) {
//        categories = MutableList(2) { null }
//        if (faceLandmarkerResult != null) {
//            val sortedCategories = faceLandmarkerResult.faceBlendshapes().get()[0]
//
//            categories[0]=sortedCategories[9]
//            categories[1]=sortedCategories[10]
//        }
//    }
//
//
//    // ...
//
//    override fun onResults(resultBundle: FaceLandmarkerHelper.ResultBundle) {
////        Log.i(TAG, "FaceLandmarkerService: Results: ${resultBundle.result}")
//        // Update the overlay view if it's active
//        OverlayManager.updateOverlay(
//            resultBundle.result,
//            resultBundle.inputImageHeight,
//            resultBundle.inputImageWidth,
//            RunningMode.LIVE_STREAM
//        )
//
//        // Update the categories with the result
//        updateResults(resultBundle.result)
//        Log.i(TAG, "Blink : ${categories}")
//
//    }
//
//
//
//    class ServiceLifecycleOwner : LifecycleOwner {
//        val lifecycleRegistry = LifecycleRegistry(this)
//
//        override fun getLifecycle(): Lifecycle {
//            return lifecycleRegistry
//        }
//    }
//
//}


//class FaceLandmarkerService : Service(), FaceLandmarkerHelper.LandmarkerListener {
//    companion object {
//        private const val TAG = "Eyeris Service"
//        private const val CHANNEL_ID = "Eyeris Service Channel"
//        private const val NOTIFICATION_ID = 1
//
//        lateinit var faceLandmarkerHelper: FaceLandmarkerHelper
//        private var cameraFacing = CameraSelector.LENS_FACING_FRONT
//
//        fun analyzeImage(imageProxy: ImageProxy) {
//            faceLandmarkerHelper.detectLiveStream(
//                imageProxy = imageProxy,
//                isFrontCamera = cameraFacing == CameraSelector.LENS_FACING_FRONT
//            )
//        }
//    }
//
//    private lateinit var backgroundExecutor: ExecutorService
//    private var imageAnalyzer: ImageAnalysis? = null
//    private var cameraProvider: ProcessCameraProvider? = null
//    private lateinit var lifecycleOwner: ServiceLifecycleOwner
//
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
//
//    override fun onCreate() {
//        super.onCreate()
//        createNotificationChannel()
//        startForegroundService()
//
//        backgroundExecutor = Executors.newSingleThreadExecutor()
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
//            setUpCamera()
//        }
//
//        lifecycleOwner = ServiceLifecycleOwner()
//        lifecycleOwner.lifecycleRegistry.currentState = Lifecycle.State.STARTED
//    }
//
//    private fun createNotificationChannel() {
//        val serviceChannel = NotificationChannel(
//            CHANNEL_ID,
//            "Eyeris Service Channel",
//            NotificationManager.IMPORTANCE_DEFAULT
//        )
//        val manager = getSystemService(NotificationManager::class.java)
//        manager.createNotificationChannel(serviceChannel)
//    }
//
//    private fun startForegroundService() {
//        val notification: Notification = createNotification(true)
//        startForeground(NOTIFICATION_ID, notification)
//    }
//
//    private fun createNotification(isCameraActive: Boolean): Notification {
//        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
//            .setContentTitle("Eyeris Service")
//            .setContentText("Detecting blinks in background 😊")
//            .setSmallIcon(R.mipmap.ic_launcher_round)
//
//        if (isCameraActive) {
//            builder.addAction(stopCameraAction)
//        } else {
//            builder.addAction(startCameraAction)
//        }
//
//        return builder.build()
//    }
//
//    private fun updateNotification(isCameraActive: Boolean) {
//        val notificationManager = getSystemService(NotificationManager::class.java) as NotificationManager
//        val notification = createNotification(isCameraActive)
//        notificationManager.notify(NOTIFICATION_ID, notification)
////        TODO("connecting notification click to camera fragment click")
//    }
//
//    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
//        when (intent?.action) {
//            "ACTION_STOP_CAMERA" -> {
//                stopCamera()
//                updateNotification(false)
//            }
//            "ACTION_START_CAMERA" -> {
//                startCamera()
//                updateNotification(true)
//            }
//        }
//        return START_NOT_STICKY
//    }
//
//    private fun startCamera() {
//        if (!::backgroundExecutor.isInitialized || backgroundExecutor.isShutdown) {
//            backgroundExecutor = Executors.newSingleThreadExecutor()
//        }
//        setUpCamera()
//    }
//
//    private fun stopCamera() {
//        cameraProvider?.unbindAll()
//        if (::backgroundExecutor.isInitialized) {
//            backgroundExecutor.shutdownNow()
//        }
//        stopSelf() // Stop the service
//    }
//
//    private fun setUpCamera() {
//        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
//        cameraProviderFuture.addListener(
//            {
//                cameraProvider = cameraProviderFuture.get()
//                val cameraSelector = CameraSelector.Builder()
//                    .requireLensFacing(cameraFacing)
//                    .build()
//
//                imageAnalyzer = ImageAnalysis.Builder()
//                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
//                    .setOutputImageFormat(ImageAnalysis.OUTPUT_IMAGE_FORMAT_RGBA_8888)
//                    .build()
//                    .also {
//                        it.setAnalyzer(backgroundExecutor, FaceLandmarkerService::analyzeImage)
//                    }
//
//                cameraProvider?.unbindAll()
//                try {
//                    cameraProvider?.bindToLifecycle(
//                        lifecycleOwner,
//                        cameraSelector,
//                        imageAnalyzer
//                    )
//                } catch (exc: Exception) {
//                    Log.e(TAG, "Use case binding failed", exc)
//                }
//            },
//            ContextCompat.getMainExecutor(this)
//        )
//    }
//
//    override fun onBind(intent: Intent?): IBinder? {
//        return null
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        stopCamera()
//    }
//
//    override fun onError(error: String, errorCode: Int) {
//        Log.e(TAG, "Error: $error (Code: $errorCode)")
//    }
//
//    private var categories: MutableList<Category?> = MutableList(2) { null }
//
//    fun updateResults(faceLandmarkerResult: FaceLandmarkerResult? = null) {
//        categories = MutableList(2) { null }
//        if (faceLandmarkerResult != null) {
//            val sortedCategories = faceLandmarkerResult.faceBlendshapes().get()[0]
//            categories[0] = sortedCategories[9]
//            categories[1] = sortedCategories[10]
//        }
//    }
//
//
//    override fun onResults(resultBundle: FaceLandmarkerHelper.ResultBundle) {
//        OverlayManager.updateOverlay(
//            resultBundle.result,
//            resultBundle.inputImageHeight,
//            resultBundle.inputImageWidth,
//            RunningMode.LIVE_STREAM
//        )
//        updateResults(resultBundle.result)
//        Log.i(TAG, "Blink : ${categories}")
//    }
//
//    class ServiceLifecycleOwner : LifecycleOwner {
//        val lifecycleRegistry = LifecycleRegistry(this)
//
//        override fun getLifecycle(): Lifecycle {
//            return lifecycleRegistry
//        }
//    }
//}


//class FaceLandmarkerService : Service(), FaceLandmarkerHelper.LandmarkerListener {
//
//    companion object {
//        private const val TAG = "EyerisService"
//        private const val CHANNEL_ID = "EyerisServiceChannel"
//        private const val NOTIFICATION_ID = 1
//
//        lateinit var faceLandmarkerHelper: FaceLandmarkerHelper
//        private var cameraFacing = CameraSelector.LENS_FACING_FRONT
//
//        fun analyzeImage(imageProxy: ImageProxy) {
//            faceLandmarkerHelper.detectLiveStream(
//                imageProxy = imageProxy,
//                isFrontCamera = cameraFacing == CameraSelector.LENS_FACING_FRONT
//            )
//        }
//    }
//
//    private lateinit var backgroundExecutor: ExecutorService
//    private var imageAnalyzer: ImageAnalysis? = null
//    private var cameraProvider: ProcessCameraProvider? = null
//    private lateinit var lifecycleOwner: ServiceLifecycleOwner
//
//    private lateinit var databaseHelper: BlinkDatabaseHelper
//    private val blinkDataList = mutableListOf<Pair<Long, List<Category?>>>()
//    private var timer: Timer? = null
//
//    override fun onCreate() {
//        super.onCreate()
//        databaseHelper = BlinkDatabaseHelper(this)
//        createNotificationChannel()
//        startForegroundService()
//        startDataCollectionTimer()
//
//        backgroundExecutor = Executors.newSingleThreadExecutor()
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
//            setUpCamera()
//        }
//
//        lifecycleOwner = ServiceLifecycleOwner()
//        lifecycleOwner.lifecycleRegistry.currentState = Lifecycle.State.STARTED
//    }
//
//    private fun createNotificationChannel() {
//        val serviceChannel = NotificationChannel(
//            CHANNEL_ID,
//            "Eyeris Service Channel",
//            NotificationManager.IMPORTANCE_DEFAULT
//        )
//        val manager = getSystemService(NotificationManager::class.java)
//        manager.createNotificationChannel(serviceChannel)
//    }
//
//    private fun startForegroundService() {
//        val notification: Notification = createNotification(true)
//        startForeground(NOTIFICATION_ID, notification)
//    }
//
//    private fun createNotification(isCameraActive: Boolean): Notification {
//        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
//            .setContentTitle("Eyeris Service")
//            .setContentText("Detecting blinks in background 😊")
//            .setSmallIcon(R.mipmap.ic_launcher_round)
//
//        if (isCameraActive) {
//            builder.addAction(stopCameraAction)
//        } else {
//            builder.addAction(startCameraAction)
//        }
//
//        return builder.build()
//    }
//
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
//
//    private fun updateNotification(isCameraActive: Boolean) {
//        val notificationManager = getSystemService(NotificationManager::class.java) as NotificationManager
//        val notification = createNotification(isCameraActive)
//        notificationManager.notify(NOTIFICATION_ID, notification)
//    }
//
//    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
//        when (intent?.action) {
//            "ACTION_STOP_CAMERA" -> {
//                stopCamera()
//                updateNotification(false)
//            }
//            "ACTION_START_CAMERA" -> {
//                startCamera()
//                updateNotification(true)
//            }
//        }
//        return START_NOT_STICKY
//    }
//
//    private fun startCamera() {
//        if (!::backgroundExecutor.isInitialized || backgroundExecutor.isShutdown) {
//            backgroundExecutor = Executors.newSingleThreadExecutor()
//        }
//        setUpCamera()
//    }
//
//    private fun stopCamera() {
//        cameraProvider?.unbindAll()
//        if (::backgroundExecutor.isInitialized) {
//            backgroundExecutor.shutdownNow()
//        }
//        stopSelf()
//    }
//
//    private fun setUpCamera() {
//        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
//        cameraProviderFuture.addListener({
//            cameraProvider = cameraProviderFuture.get()
//            val cameraSelector = CameraSelector.Builder()
//                .requireLensFacing(cameraFacing)
//                .build()
//
//            imageAnalyzer = ImageAnalysis.Builder()
//                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
//                .setOutputImageFormat(ImageAnalysis.OUTPUT_IMAGE_FORMAT_RGBA_8888)
//                .build()
//                .also {
//                    it.setAnalyzer(backgroundExecutor, FaceLandmarkerService::analyzeImage)
//                }
//
//            cameraProvider?.unbindAll()
//            try {
//                cameraProvider?.bindToLifecycle(
//                    lifecycleOwner,
//                    cameraSelector,
//                    imageAnalyzer
//                )
//            } catch (exc: Exception) {
//                Log.e(TAG, "Use case binding failed", exc)
//            }
//        }, ContextCompat.getMainExecutor(this))
//    }
//
//    private fun startDataCollectionTimer() {
//        timer = Timer()
//        timer?.schedule(object : TimerTask() {
//            override fun run() {
//                storeBlinkData()
//            }
//        }, 6, 60) // 1 minute interval
//    }
//
//    private fun storeBlinkData() {
//        if (blinkDataList.isNotEmpty()) {
//            val db = databaseHelper.writableDatabase
//            db.beginTransaction()
//            try {
//                for (data in blinkDataList) {
//                    val timestamp = data.first
//                    val categories = data.second
//                    val leftBlinkScore = categories[0]?.score() ?: 0.0f
//                    val rightBlinkScore = categories[1]?.score() ?: 0.0f
//
//                    val contentValues = ContentValues().apply {
//                        put("timestamp", timestamp)
//                        put("left_blink_score", leftBlinkScore)
//                        put("right_blink_score", rightBlinkScore)
//                    }
//
//                    db.insert("blink_data", null, contentValues)
//                }
//                db.setTransactionSuccessful()
//            } finally {
//                Log.i(TAG, "Blink : some error occurred while storing data")
//                Log.i(TAG, "Blink : $blinkDataList")
//                db.endTransaction()
//            }
//            Log.i(TAG, "Blink : $blinkDataList")
//            blinkDataList.clear()
//        }
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        timer?.cancel()
//        stopCamera()
//        storeBlinkData()
//    }
//
//    override fun onResults(resultBundle: FaceLandmarkerHelper.ResultBundle) {
//        val currentTime = System.currentTimeMillis()
//        val faceBlendshapes = resultBundle.result.faceBlendshapes()?.get()?.get(0)
//        val categories = listOf(faceBlendshapes?.get(9), faceBlendshapes?.get(10))
//
//        blinkDataList.add(Pair(currentTime, categories))
//        OverlayManager.updateOverlay(
//            resultBundle.result,
//            resultBundle.inputImageHeight,
//            resultBundle.inputImageWidth,
//            RunningMode.LIVE_STREAM
//        )
////        Log.i(TAG, "Blink : $categories")
//    }
//
//    override fun onBind(intent: Intent?): IBinder? {
//        return null
//    }
//
//    override fun onError(error: String, errorCode: Int) {
//        Log.e(TAG, "Error: $error (Code: $errorCode)")
//    }
//
//    class ServiceLifecycleOwner : LifecycleOwner {
//        val lifecycleRegistry = LifecycleRegistry(this)
//        override fun getLifecycle(): Lifecycle {
//            return lifecycleRegistry
//        }
//    }
//}
//

import java.util.concurrent.CopyOnWriteArrayList
import android.content.Context

class FaceLandmarkerService : Service(), FaceLandmarkerHelper.LandmarkerListener {

    companion object {
        private const val TAG = "EyerisService"
        private const val CHANNEL_ID = "EyerisServiceChannel"
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

    private lateinit var databaseHelper: BlinkDatabaseHelper
    private val blinkDataList = CopyOnWriteArrayList<Pair<Long, List<Category?>>>()
    private var timer: Timer? = null

    override fun onCreate() {
        super.onCreate()
        databaseHelper = BlinkDatabaseHelper(this)
        createNotificationChannel()
        startForegroundService()
        startDataCollectionTimer()

        backgroundExecutor = Executors.newSingleThreadExecutor()
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

    private fun createNotificationChannel() {
        val serviceChannel = NotificationChannel(
            CHANNEL_ID,
            "Eyeris Service Channel",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(serviceChannel)
    }

    private fun startForegroundService() {
        val notification: Notification = createNotification(true)
        startForeground(NOTIFICATION_ID, notification)
    }

    private fun createNotification(isCameraActive: Boolean): Notification {
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Eyeris Service")
            .setContentText("Detecting blinks in background 😊")
            .setSmallIcon(R.mipmap.ic_launcher_round)

        if (isCameraActive) {
            builder.addAction(stopCameraAction)
        } else {
            builder.addAction(startCameraAction)
        }

        return builder.build()
    }

    private val stopCameraAction: NotificationCompat.Action
        get() {
            val intent = Intent(this, FaceLandmarkerService::class.java).apply {
                action = "ACTION_STOP_CAMERA"
            }
            val pendingIntent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
            return NotificationCompat.Action.Builder(R.drawable.baseline_pause_24, "Stop", pendingIntent).build()
        }

    private val startCameraAction: NotificationCompat.Action
        get() {
            val intent = Intent(this, FaceLandmarkerService::class.java).apply {
                action = "ACTION_START_CAMERA"
            }
            val pendingIntent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
            return NotificationCompat.Action.Builder(R.drawable.baseline_play_arrow_24, "Start", pendingIntent).build()
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
                    it.setAnalyzer(backgroundExecutor, FaceLandmarkerService::analyzeImage)
                }

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
        }, ContextCompat.getMainExecutor(this))
    }

    private fun startDataCollectionTimer() {
        timer = Timer()
        timer?.schedule(object : TimerTask() {
            override fun run() {
                storeBlinkData()
            }
        }, 6, 6) // 1 minute interval
    }

    private fun storeBlinkData() {
        if (blinkDataList.isNotEmpty()) {
            val db = databaseHelper.writableDatabase
            db.beginTransaction()
            try {
                for (data in blinkDataList) {
                    val timestamp = data.first
                    val categories = data.second
                    val leftBlinkScore = categories[0]?.score() ?: 0.0f
                    val rightBlinkScore = categories[1]?.score() ?: 0.0f

                    val contentValues = ContentValues().apply {
                        put("timestamp", timestamp)
                        put("left_blink_score", leftBlinkScore)
                        put("right_blink_score", rightBlinkScore)
                    }

                    db.insert("blink_data", null, contentValues)
                }
                db.setTransactionSuccessful()
            } catch (e: Exception) {
                Log.i(TAG, "Blink : some error occurred while storing data")
                Log.i(TAG, "Blink : $blinkDataList")
            } finally {
//                Log.i(TAG, "Blink : successfully stored data")
                db.endTransaction()
            }
            Log.i(TAG, "Blink : $blinkDataList")
            blinkDataList.clear()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
        stopCamera()
        storeBlinkData()
//        Log.i(TAG, "Blink : successfully stored datadfghjk")
    }

    override fun onResults(resultBundle: FaceLandmarkerHelper.ResultBundle) {
        val currentTime = System.currentTimeMillis()
        val faceBlendshapes = resultBundle.result.faceBlendshapes()?.get()?.get(0)
        val categories = listOf(faceBlendshapes?.get(9), faceBlendshapes?.get(10))

        blinkDataList.add(Pair(currentTime, categories))
        OverlayManager.updateOverlay(
            resultBundle.result,
            resultBundle.inputImageHeight,
            resultBundle.inputImageWidth,
            RunningMode.LIVE_STREAM
        )
//        Log.i(TAG, "Blink : $categories")
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onError(error: String, errorCode: Int) {
        Log.e(TAG, "Error: $error (Code: $errorCode)")
    }

    class ServiceLifecycleOwner : LifecycleOwner {
        val lifecycleRegistry = LifecycleRegistry(this)
        override fun getLifecycle(): Lifecycle {
            return lifecycleRegistry
        }
    }
}