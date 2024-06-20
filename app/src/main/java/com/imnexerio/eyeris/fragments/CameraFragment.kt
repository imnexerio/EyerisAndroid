package com.imnexerio.eyeris.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.transition.TransitionManager

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import android.content.Intent
import android.content.Context
import android.widget.Toast
import androidx.camera.view.PreviewView
import androidx.navigation.Navigation
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.imnexerio.eyeris.CameraUtils
import com.imnexerio.eyeris.FaceLandmarkerService
import com.imnexerio.eyeris.OverlayManager
import com.imnexerio.eyeris.OverlayView
import com.imnexerio.eyeris.R
//
//
//class CameraFragment : Fragment() {
//
//    private lateinit var backgroundExecutor: ExecutorService
//    private var cameraFacing = CameraSelector.LENS_FACING_FRONT
//    private lateinit var previewView: PreviewView
//    private lateinit var overlayView: OverlayView
//    private var cameraProvider: ProcessCameraProvider? = null
//    private var isPreviewOn: Boolean = false
//    private var isOverlayOn: Boolean = false
//    private var isButtonsVisible: Boolean = false
//    private var isfloatingActionFaceLandmarkOn: Boolean = false
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
//    ): View? {
//        return inflater.inflate(R.layout.fragment_camera, container, false)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        previewView = view.findViewById(R.id.view_finder)
//        overlayView = view.findViewById(R.id.overlay)
//        backgroundExecutor = Executors.newSingleThreadExecutor()
//
//        val floatingActionOptions = view.findViewById<FloatingActionButton>(R.id.floatingActionOptions)
//        val previewButton = view.findViewById<FloatingActionButton>(R.id.PreviewButton)
//        val overlayButton = view.findViewById<FloatingActionButton>(R.id.OverlayButton)
//        val constraintLayout = view.findViewById<ConstraintLayout>(R.id.constraint_layout)
//        val floatingActionFaceLandmark = view.findViewById<FloatingActionButton>(R.id.FloatingActionFaceLandmark)
//
//        floatingActionFaceLandmark.setOnClickListener {
//            isfloatingActionFaceLandmarkOn = !isfloatingActionFaceLandmarkOn
//            if (isfloatingActionFaceLandmarkOn) {
//                floatingActionFaceLandmark.setImageResource(R.drawable.baseline_pause_24)
//                startFaceLandmarkCamera()
//            } else {
//                floatingActionFaceLandmark.setImageResource(R.drawable.baseline_play_arrow_24)
//                stopFaceLandmarkCamera()
//            }
//        }
//
//        floatingActionOptions.setOnClickListener {
//            isButtonsVisible = !isButtonsVisible
//            if (isButtonsVisible) {
//                floatingActionOptions.setImageResource(R.drawable.baseline_expand_more_24)
//                showPreviewButton(constraintLayout, previewButton)
//                showOverlayButton(constraintLayout, overlayButton)
//            } else {
//                floatingActionOptions.setImageResource(R.drawable.baseline_expand_less_24)
//                hidePreviewButton(constraintLayout, previewButton)
//                hideOverlayButton(constraintLayout, overlayButton)
//            }
//        }
//
//
//
//        previewButton.setOnClickListener {
//            isPreviewOn = !isPreviewOn
//            if (isPreviewOn) {
//                previewButton.setImageResource(R.drawable.baseline_preview_24)
//                startCameraPreview()
//            } else {
//                previewButton.setImageResource(R.drawable.baseline_camera_enhance_24)
//                stopCameraPreview()
//            }
//        }
//
//        overlayButton.setOnClickListener {
//            isOverlayOn =!isOverlayOn
//            if (isOverlayOn) {
//                overlayButton.setImageResource(R.drawable.baseline_ac_unit_24)
//                startOverlayPreview()
//            }else {
//                overlayButton.setImageResource(R.drawable.baseline_face_retouching_off_24)
//                stopOverlayPreview()
//            }
//        }
//    }
//
//    private fun stopFaceLandmarkCamera() {
//
//        Toast.makeText(requireContext(), "Face Landmarking Stopped", Toast.LENGTH_SHORT).show()
////        TODO("Not yet implemented")
//        stopCameraPreview()
//        stopFaceLandmarkerService()
//        stopOverlayPreview()
//    }
//
//    private fun startFaceLandmarkCamera() {
//        Toast.makeText(requireContext(), "Face Landmarking Started", Toast.LENGTH_SHORT).show()
////        TODO("Not yet implemented")
//        startFaceLandmarkerService()
////        startCameraPreview()
//    }
//
//
//    private fun startOverlayPreview() {
//        Toast.makeText(requireContext(), "Overlay Started", Toast.LENGTH_SHORT).show()
//        overlayView.visibility = View.VISIBLE
//        OverlayManager.setOverlayView(overlayView)
//        setUpCamera()
//    }
//
//    private fun stopOverlayPreview() {
//        Toast.makeText(requireContext(), "Overlay Stopped", Toast.LENGTH_SHORT).show()
//        overlayView.visibility = View.GONE
//        OverlayManager.clearOverlayView()
////        setUpCamera()  // Re-setup the camera without the overlay
//        stopCameraPreview()
//    }
//
//    private fun setUpCamera() {
//        CameraUtils.setUpCamera(
//            context = requireContext(),
//            lifecycleOwner = viewLifecycleOwner,
//            backgroundExecutor = backgroundExecutor,
//            cameraFacing = cameraFacing,
//            previewView = previewView,
//            analyzer = FaceLandmarkerService::analyzeImage,
//            cameraProviderCallback = { provider -> cameraProvider = provider }
//        )
//    }
//
//    override fun onResume() {
//        super.onResume()
////        startFaceLandmarkerService()
//        if (!PermissionsFragment.hasPermissions(requireContext())) {
//            Navigation.findNavController(
//                requireActivity(), R.id.fragment_container
//            ).navigate(R.id.action_camera_to_permissions)
//        } else {
//            startFaceLandmarkerService()
//        }
//    }
//
//    override fun onPause() {
//        super.onPause()
//        // Ensure that image processing continues even when the preview is off
//        cameraProvider?.unbindAll()
//        startImageAnalysisOnly(requireContext())
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        stopFaceLandmarkerService()
//        stopCameraPreview()
//        backgroundExecutor.shutdown()
//    }
//
//    private fun startFaceLandmarkerService() {
//        val serviceIntent = Intent(requireContext(), FaceLandmarkerService::class.java)
//        ContextCompat.startForegroundService(requireContext(), serviceIntent)
//    }
//
//    private fun stopFaceLandmarkerService() {
//        val serviceIntent = Intent(requireContext(), FaceLandmarkerService::class.java)
//        requireContext().stopService(serviceIntent)
//    }
//
//    private fun startCameraPreview() {
//        CameraUtils.setUpCamera(
//            context = requireContext(),
//            lifecycleOwner = viewLifecycleOwner,
//            backgroundExecutor = backgroundExecutor,
//            cameraFacing = cameraFacing,
//            previewView = previewView,
//            analyzer = FaceLandmarkerService::analyzeImage,
//            cameraProviderCallback = { provider -> cameraProvider = provider }
//        )
//    }
//
//    private fun stopCameraPreview() {
//        cameraProvider?.unbindAll()
//        startImageAnalysisOnly(requireContext())
//    }
//
//    private fun startImageAnalysisOnly(context: Context) {
//        cameraProvider?.let {
//            CameraUtils.bindImageAnalysisOnly(
//                context = context,
//                cameraProvider = it,
//                lifecycleOwner = viewLifecycleOwner,
//                backgroundExecutor = backgroundExecutor,
//                cameraFacing = cameraFacing,
//                analyzer = FaceLandmarkerService::analyzeImage
//            )
//        }
//    }
//
//    private fun showPreviewButton(constraintLayout: ConstraintLayout, previewButton: FloatingActionButton) {
//        val constraintSet = ConstraintSet()
//        constraintSet.clone(constraintLayout)
//        constraintSet.setVisibility(R.id.PreviewButton, View.VISIBLE)
//        TransitionManager.beginDelayedTransition(constraintLayout)
//        constraintSet.applyTo(constraintLayout)
//    }
//
//    private fun hidePreviewButton(constraintLayout: ConstraintLayout, previewButton: FloatingActionButton) {
//        val constraintSet = ConstraintSet()
//        constraintSet.clone(constraintLayout)
//        constraintSet.setVisibility(R.id.PreviewButton, View.GONE)
//        TransitionManager.beginDelayedTransition(constraintLayout)
//        constraintSet.applyTo(constraintLayout)
//    }
//
//    private fun showOverlayButton(constraintLayout: ConstraintLayout, overlayButton: FloatingActionButton) {
//        val constraintSet = ConstraintSet()
//        constraintSet.clone(constraintLayout)
//        constraintSet.setVisibility(R.id.OverlayButton, View.VISIBLE)
//        TransitionManager.beginDelayedTransition(constraintLayout)
//        constraintSet.applyTo(constraintLayout)
//    }
//
//    private fun hideOverlayButton(constraintLayout: ConstraintLayout, overlayButton: FloatingActionButton) {
//        val constraintSet = ConstraintSet()
//        constraintSet.clone(constraintLayout)
//        constraintSet.setVisibility(R.id.OverlayButton, View.GONE)
//        TransitionManager.beginDelayedTransition(constraintLayout)
//        constraintSet.applyTo(constraintLayout)
//    }
//
//}


//class CameraFragment : Fragment() {
//
//    private lateinit var backgroundExecutor: ExecutorService
//    private var cameraFacing = CameraSelector.LENS_FACING_FRONT
//    private lateinit var previewView: PreviewView
//    private lateinit var overlayView: OverlayView
//    private var cameraProvider: ProcessCameraProvider? = null
//    private var isPreviewOn = false
//    private var isOverlayOn = false
//    private var isButtonsVisible = false
//    private var isFaceLandmarkOn = true
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
//    ): View? = inflater.inflate(R.layout.fragment_camera, container, false)
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        previewView = view.findViewById(R.id.view_finder)
//        overlayView = view.findViewById(R.id.overlay)
//        backgroundExecutor = Executors.newSingleThreadExecutor()
//
//        val floatingActionOptions = view.findViewById<FloatingActionButton>(R.id.floatingActionOptions)
//        val previewButton = view.findViewById<FloatingActionButton>(R.id.PreviewButton)
//        val overlayButton = view.findViewById<FloatingActionButton>(R.id.OverlayButton)
//        val constraintLayout = view.findViewById<ConstraintLayout>(R.id.constraint_layout)
//        val floatingActionFaceLandmark = view.findViewById<FloatingActionButton>(R.id.FloatingActionFaceLandmark)
//
//        floatingActionFaceLandmark.setOnClickListener {
//            isFaceLandmarkOn = !isFaceLandmarkOn
//            floatingActionFaceLandmark.setImageResource(
//                if (isFaceLandmarkOn) R.drawable.baseline_pause_24 else R.drawable.baseline_play_arrow_24
//            )
//            if (isFaceLandmarkOn) startFaceLandmarkCamera() else stopFaceLandmarkCamera()
//        }
//
//        floatingActionOptions.setOnClickListener {
//            isButtonsVisible = !isButtonsVisible
//            floatingActionOptions.setImageResource(
//                if (isButtonsVisible) R.drawable.baseline_expand_more_24 else R.drawable.baseline_expand_less_24
//            )
//            if (isButtonsVisible) {
//                showButton(constraintLayout, previewButton)
//                showButton(constraintLayout, overlayButton)
//            } else {
//                hideButton(constraintLayout, previewButton)
//                hideButton(constraintLayout, overlayButton)
//            }
//        }
//
//        previewButton.setOnClickListener {
//            isPreviewOn = !isPreviewOn
//            previewButton.setImageResource(
//                if (isPreviewOn) R.drawable.baseline_preview_24 else R.drawable.baseline_camera_enhance_24
//            )
//            if (isPreviewOn) startCameraPreview() else stopCameraPreview()
//        }
//
//        overlayButton.setOnClickListener {
//            isOverlayOn = !isOverlayOn
//            overlayButton.setImageResource(
//                if (isOverlayOn) R.drawable.baseline_ac_unit_24 else R.drawable.baseline_face_retouching_off_24
//            )
//            if (isOverlayOn) startOverlayPreview() else stopOverlayPreview()
//        }
//    }
//
//
//    override fun onResume() {
//        super.onResume()
//        if (!PermissionsFragment.hasPermissions(requireContext())) {
//            Navigation.findNavController(requireActivity(), R.id.fragment_container)
//                .navigate(R.id.action_camera_to_permissions)
//        } else {
//            startFaceLandmarkerService()
//        }
//    }
//
//    override fun onPause() {
//        super.onPause()
//        cameraProvider?.unbindAll()
//        startImageAnalysisOnly(requireContext())
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        stopFaceLandmarkerService()
//        stopCameraPreview()
//        backgroundExecutor.shutdown()
//    }
//
//    private fun startFaceLandmarkCamera() {
//        showToast("Face Landmarking Started")
//        startFaceLandmarkerService()
//    }
//
//    private fun stopFaceLandmarkCamera() {
//        showToast("Face Landmarking Stopped")
//        stopCameraPreview()
//        stopFaceLandmarkerService()
//        stopOverlayPreview()
//
//    }
//
//    private fun startOverlayPreview() {
//        showToast("Overlay Started")
//        overlayView.visibility = View.VISIBLE
//        OverlayManager.setOverlayView(overlayView)
//        setUpCamera()
//    }
//
//    private fun stopOverlayPreview() {
//        showToast("Overlay Stopped")
//        overlayView.visibility = View.GONE
//        OverlayManager.clearOverlayView()
//        stopCameraPreview()
//    }
//
//    private fun startFaceLandmarkerService() {
//        val serviceIntent = Intent(requireContext(), FaceLandmarkerService::class.java)
//        ContextCompat.startForegroundService(requireContext(), serviceIntent)
//    }
//
//    private fun stopFaceLandmarkerService() {
//        val serviceIntent = Intent(requireContext(), FaceLandmarkerService::class.java)
//        requireContext().stopService(serviceIntent)
//    }
//
////  this starts the camera preview and starts analyzing the image in background
//    private fun startCameraPreview() {
//        setUpCamera()
//    }
//
////    it stops camera preview and starts analyzing the image in background
//    private fun stopCameraPreview() {
//        cameraProvider?.unbindAll()
//        startImageAnalysisOnly(requireContext())
//    }
//
//    private fun setUpCamera() {
//        CameraUtils.setUpCamera(
//            context = requireContext(),
//            lifecycleOwner = viewLifecycleOwner,
//            backgroundExecutor = backgroundExecutor,
//            cameraFacing = cameraFacing,
//            previewView = previewView,
//            analyzer = FaceLandmarkerService::analyzeImage,
//            cameraProviderCallback = { provider -> cameraProvider = provider }
//        )
//    }
//
//    //this makes the camera only analyze the image in background too
//    private fun startImageAnalysisOnly(context: Context) {
//        cameraProvider?.let {
//            CameraUtils.bindImageAnalysisOnly(
//                context = context,
//                cameraProvider = it,
//                lifecycleOwner = viewLifecycleOwner,
//                backgroundExecutor = backgroundExecutor,
//                cameraFacing = cameraFacing,
//                analyzer = FaceLandmarkerService::analyzeImage
//            )
//        }
//    }
//
//    private fun showButton(constraintLayout: ConstraintLayout, button: FloatingActionButton) {
//        changeButtonVisibility(constraintLayout, button, View.VISIBLE)
//    }
//
//    private fun hideButton(constraintLayout: ConstraintLayout, button: FloatingActionButton) {
//        changeButtonVisibility(constraintLayout, button, View.GONE)
//    }
//
//    private fun changeButtonVisibility(constraintLayout: ConstraintLayout, button: FloatingActionButton, visibility: Int) {
//        val constraintSet = ConstraintSet().apply {
//            clone(constraintLayout)
//            setVisibility(button.id, visibility)
//        }
//        TransitionManager.beginDelayedTransition(constraintLayout)
//        constraintSet.applyTo(constraintLayout)
//    }
//
//    private fun showToast(message: String) {
//        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
//    }
//}

class CameraFragment : Fragment() {

    private lateinit var backgroundExecutor: ExecutorService
    private var cameraFacing = CameraSelector.LENS_FACING_FRONT
    private lateinit var previewView: PreviewView
    private lateinit var overlayView: OverlayView
    private var cameraProvider: ProcessCameraProvider? = null
    private var isPreviewOn = false
    private var isOverlayOn = false
    private var isButtonsVisible = false
    private var isFaceLandmarkOn = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_camera, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        previewView = view.findViewById(R.id.view_finder)
        overlayView = view.findViewById(R.id.overlay)
        backgroundExecutor = Executors.newSingleThreadExecutor()

        val floatingActionOptions = view.findViewById<FloatingActionButton>(R.id.floatingActionOptions)
        val previewButton = view.findViewById<FloatingActionButton>(R.id.PreviewButton)
        val overlayButton = view.findViewById<FloatingActionButton>(R.id.OverlayButton)
        val constraintLayout = view.findViewById<ConstraintLayout>(R.id.constraint_layout)
        val floatingActionFaceLandmark = view.findViewById<FloatingActionButton>(R.id.FloatingActionFaceLandmark)

        floatingActionFaceLandmark.setOnClickListener {
            isFaceLandmarkOn = !isFaceLandmarkOn
            floatingActionFaceLandmark.setImageResource(
                if (isFaceLandmarkOn) R.drawable.baseline_pause_24 else R.drawable.baseline_play_arrow_24
            )
            if (isFaceLandmarkOn) startFaceLandmarkCamera() else stopFaceLandmarkCamera()
        }

        floatingActionOptions.setOnClickListener {
            isButtonsVisible = !isButtonsVisible
            floatingActionOptions.setImageResource(
                if (isButtonsVisible) R.drawable.baseline_expand_more_24 else R.drawable.baseline_expand_less_24
            )
            if (isButtonsVisible) {
                showButton(constraintLayout, previewButton)
                showButton(constraintLayout, overlayButton)
            } else {
                hideButton(constraintLayout, previewButton)
                hideButton(constraintLayout, overlayButton)
            }
        }

        previewButton.setOnClickListener {
            isPreviewOn = !isPreviewOn
            previewButton.setImageResource(
                if (isPreviewOn) R.drawable.baseline_preview_24 else R.drawable.baseline_camera_enhance_24
            )
            if (isPreviewOn) startCameraPreview() else stopCameraPreview()
        }

        overlayButton.setOnClickListener {
            isOverlayOn = !isOverlayOn
            overlayButton.setImageResource(
                if (isOverlayOn) R.drawable.baseline_ac_unit_24 else R.drawable.baseline_face_retouching_off_24
            )
            if (isOverlayOn) startOverlayPreview() else stopOverlayPreview()
        }
    }

    private fun startFaceLandmarkCamera() {
        showToast("Face Landmarking Started")
        startFaceLandmarkerService()
    }

    private fun stopFaceLandmarkCamera() {
        cameraProvider?.unbindAll()
        showToast("Face Landmarking Stopped")
        stopCameraUsage()
        stopFaceLandmarkerService()
        stopOverlayPreview()
//        TODO("camera is still running in the background, need to stop it too but when service is stopped from notification camera also stopps but when camera to stopped from notification app is still active in backgroiund running")
    }

    private fun startOverlayPreview() {
        showToast("Overlay Started")
        overlayView.visibility = View.VISIBLE
        OverlayManager.setOverlayView(overlayView)
        setUpCamera()
    }

    private fun stopOverlayPreview() {
        showToast("Overlay Stopped")
        overlayView.visibility = View.GONE
        OverlayManager.clearOverlayView()
        stopCameraPreview()
    }

    private fun setUpCamera() {
        CameraUtils.setUpCamera(
            context = requireContext(),
            lifecycleOwner = viewLifecycleOwner,
            backgroundExecutor = backgroundExecutor,
            cameraFacing = cameraFacing,
            previewView = previewView,
            analyzer = FaceLandmarkerService::analyzeImage,
            cameraProviderCallback = { provider -> cameraProvider = provider }
        )
    }

    private fun stopCameraUsage() {
        CameraUtils.releaseCamera(cameraProvider)
        cameraProvider = null
    }

    override fun onResume() {
        super.onResume()
        if (!PermissionsFragment.hasPermissions(requireContext())) {
            Navigation.findNavController(requireActivity(), R.id.fragment_container)
                .navigate(R.id.action_camera_to_permissions)
        } else {
            startFaceLandmarkerService()
        }
    }

    override fun onPause() {
        super.onPause()
        cameraProvider?.unbindAll()
        startImageAnalysisOnly(requireContext())
    }

    override fun onDestroy() {
        super.onDestroy()
        stopFaceLandmarkerService()
        stopCameraPreview()
        backgroundExecutor.shutdown()
    }

    private fun startFaceLandmarkerService() {
        val serviceIntent = Intent(requireContext(), FaceLandmarkerService::class.java)
        ContextCompat.startForegroundService(requireContext(), serviceIntent)
    }

    private fun stopFaceLandmarkerService() {
        val serviceIntent = Intent(requireContext(), FaceLandmarkerService::class.java)
        requireContext().stopService(serviceIntent)
    }

    private fun startCameraPreview() {
        setUpCamera()
    }

    private fun stopCameraPreview() {
        cameraProvider?.unbindAll()
        startImageAnalysisOnly(requireContext())
    }

    private fun startImageAnalysisOnly(context: Context) {
        cameraProvider?.let {
            CameraUtils.bindImageAnalysisOnly(
                context = context,
                cameraProvider = it,
                lifecycleOwner = viewLifecycleOwner,
                backgroundExecutor = backgroundExecutor,
                cameraFacing = cameraFacing,
                analyzer = FaceLandmarkerService::analyzeImage
            )
        }
    }

    private fun showButton(constraintLayout: ConstraintLayout, button: FloatingActionButton) {
        changeButtonVisibility(constraintLayout, button, View.VISIBLE)
    }

    private fun hideButton(constraintLayout: ConstraintLayout, button: FloatingActionButton) {
        changeButtonVisibility(constraintLayout, button, View.GONE)
    }

    private fun changeButtonVisibility(constraintLayout: ConstraintLayout, button: FloatingActionButton, visibility: Int) {
        val constraintSet = ConstraintSet().apply {
            clone(constraintLayout)
            setVisibility(button.id, visibility)
        }
        TransitionManager.beginDelayedTransition(constraintLayout)
        constraintSet.applyTo(constraintLayout)
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
