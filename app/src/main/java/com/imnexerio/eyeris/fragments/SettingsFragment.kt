package com.imnexerio.eyeris.fragments

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.camera.core.Preview
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Camera
import androidx.camera.core.AspectRatio
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2.SCROLL_STATE_DRAGGING
import androidx.viewpager2.widget.ViewPager2.SCROLL_STATE_IDLE
import androidx.viewpager2.widget.ViewPager2.SCROLL_STATE_SETTLING
import androidx.viewpager2.widget.ViewPager2.ScrollState
import com.google.mediapipe.tasks.vision.core.RunningMode
import com.imnexerio.eyeris.FaceLandmarkerHelper
import com.imnexerio.eyeris.MainViewModel
import com.imnexerio.eyeris.databinding.FragmentSettingsBinding
import java.util.Locale
import java.util.Optional
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.jvm.optionals.toList
import kotlin.math.roundToInt


class SettingsFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var fragmentCameraBinding: FragmentSettingsBinding
    private lateinit var faceLandmarkerHelper: FaceLandmarkerHelper
    private lateinit var backgroundExecutor: ExecutorService
    private val TAG = "SettingsFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentCameraBinding = FragmentSettingsBinding.inflate(inflater, container, false)
        return fragmentCameraBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBottomSheetControls()
    }

    private fun initBottomSheetControls() {
        // init bottom sheet settings
        fragmentCameraBinding.maxFacesValue.text =
            viewModel.currentMaxFaces.toString()
        fragmentCameraBinding.detectionThresholdValue.text =
            String.format(
                Locale.US, "%.2f", viewModel.currentMinFaceDetectionConfidence
            )
        fragmentCameraBinding.trackingThresholdValue.text =
            String.format(
                Locale.US, "%.2f", viewModel.currentMinFaceTrackingConfidence
            )
        fragmentCameraBinding.presenceThresholdValue.text =
            String.format(
                Locale.US, "%.2f", viewModel.currentMinFacePresenceConfidence
            )

        // When clicked, lower face detection score threshold floor
        fragmentCameraBinding.detectionThresholdMinus.setOnClickListener {
            if (faceLandmarkerHelper.minFaceDetectionConfidence >= 0.2) {
                faceLandmarkerHelper.minFaceDetectionConfidence -= 0.1f
                updateControlsUi()
            }
        }

        // When clicked, raise face detection score threshold floor
        fragmentCameraBinding.detectionThresholdPlus.setOnClickListener {
            if (faceLandmarkerHelper.minFaceDetectionConfidence <= 0.8) {
                faceLandmarkerHelper.minFaceDetectionConfidence += 0.1f
                updateControlsUi()
            }
        }

        // When clicked, lower face tracking score threshold floor
        fragmentCameraBinding.trackingThresholdMinus.setOnClickListener {
            if (faceLandmarkerHelper.minFaceTrackingConfidence >= 0.2) {
                faceLandmarkerHelper.minFaceTrackingConfidence -= 0.1f
                updateControlsUi()
            }
        }

        // When clicked, raise face tracking score threshold floor
        fragmentCameraBinding.trackingThresholdPlus.setOnClickListener {
            if (faceLandmarkerHelper.minFaceTrackingConfidence <= 0.8) {
                faceLandmarkerHelper.minFaceTrackingConfidence += 0.1f
                updateControlsUi()
            }
        }

        // When clicked, lower face presence score threshold floor
        fragmentCameraBinding.presenceThresholdMinus.setOnClickListener {
            if (faceLandmarkerHelper.minFacePresenceConfidence >= 0.2) {
                faceLandmarkerHelper.minFacePresenceConfidence -= 0.1f
                updateControlsUi()
            }
        }

        // When clicked, raise face presence score threshold floor
        fragmentCameraBinding.presenceThresholdPlus.setOnClickListener {
            if (faceLandmarkerHelper.minFacePresenceConfidence <= 0.8) {
                faceLandmarkerHelper.minFacePresenceConfidence += 0.1f
                updateControlsUi()
            }
        }

        // When clicked, reduce the number of faces that can be detected at a
        // time
        fragmentCameraBinding.maxFacesMinus.setOnClickListener {
            if (faceLandmarkerHelper.maxNumFaces > 1) {
                faceLandmarkerHelper.maxNumFaces--
                updateControlsUi()
            }
        }

        // When clicked, increase the number of faces that can be detected
        // at a time
        fragmentCameraBinding.maxFacesPlus.setOnClickListener {
            if (faceLandmarkerHelper.maxNumFaces < 2) {
                faceLandmarkerHelper.maxNumFaces++
                updateControlsUi()
            }
        }

        // When clicked, change the underlying hardware used for inference.
        // Current options are CPU and GPU
        fragmentCameraBinding.spinnerDelegate.setSelection(
            viewModel.currentDelegate, false
        )
        fragmentCameraBinding.spinnerDelegate.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long
                ) {
                    try {
                        faceLandmarkerHelper.currentDelegate = p2
                        updateControlsUi()
                    } catch(e: UninitializedPropertyAccessException) {
                        Log.e(TAG, "FaceLandmarkerHelper has not been initialized yet.")
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    /* no op */
                }
            }
    }

    private fun updateControlsUi() {
        fragmentCameraBinding.maxFacesValue.text =
            faceLandmarkerHelper.maxNumFaces.toString()
        fragmentCameraBinding.detectionThresholdValue.text =
            String.format(
                Locale.US,
                "%.2f",
                faceLandmarkerHelper.minFaceDetectionConfidence
            )
        fragmentCameraBinding.trackingThresholdValue.text =
            String.format(
                Locale.US,
                "%.2f",
                faceLandmarkerHelper.minFaceTrackingConfidence
            )
        fragmentCameraBinding.presenceThresholdValue.text =
            String.format(
                Locale.US,
                "%.2f",
                faceLandmarkerHelper.minFacePresenceConfidence
            )

        // Needs to be cleared instead of reinitialized because the GPU
        // delegate needs to be initialized on the thread using it when applicable
        backgroundExecutor.execute {
            faceLandmarkerHelper.clearFaceLandmarker()
            faceLandmarkerHelper.setupFaceLandmarker()
        }
//        fragmentCameraBinding.overlay.clear()
    }


}