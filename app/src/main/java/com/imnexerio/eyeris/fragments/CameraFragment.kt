package com.imnexerio.eyeris.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.fragment.app.Fragment
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import android.content.Context
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import com.google.android.material.floatingactionbutton.FloatingActionButton
//import com.imnexerio.eyeris.CameraUtils
import com.imnexerio.eyeris.FaceLandmarkerService
import com.imnexerio.eyeris.OverlayManager
import com.imnexerio.eyeris.OverlayView
import com.imnexerio.eyeris.R

//class CameraFragment : Fragment() {
//
//    private lateinit var backgroundExecutor: ExecutorService
//    private var cameraFacing = CameraSelector.LENS_FACING_FRONT
//    private lateinit var previewView: PreviewView
//    private lateinit var overlayView: OverlayView
//    private var cameraProvider: ProcessCameraProvider? = null
//    private var isOverlayOn = false
//    private lateinit var appLogo: ImageView
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
//        appLogo = view.findViewById(R.id.app_logo)
//
//
//        val overlayButton = view.findViewById<FloatingActionButton>(R.id.OverlayButton)
//
//
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
//    private fun startOverlayPreview() {
//        showToast("Overlay Started")
//        overlayView.visibility = View.VISIBLE
//        appLogo.visibility = View.GONE  // Hide the app logo
//        OverlayManager.setOverlayView(overlayView)
//        setUpCamera()
//    }
//
//
//    private fun stopOverlayPreview() {
//        showToast("Overlay Stopped")
//        OverlayManager.clearOverlayView()
//        stopCameraPreview()
//        overlayView.visibility = View.GONE
//        appLogo.visibility = View.VISIBLE  // Show the app logo
//
//    }
//
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
//
//    override fun onResume() {
//        super.onResume()
//        if (!PermissionsFragment.hasPermissions(requireContext())) {
//            Navigation.findNavController(requireActivity(), R.id.fragment_container)
//                .navigate(R.id.action_camera_to_permissions)
//        }
//
//    }
//
//    override fun onPause() {
//        super.onPause()
//        cameraProvider?.unbindAll()
//        startImageAnalysisOnly(requireContext())
//    }
//
//
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
//
//    private fun showToast(message: String) {
//        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
//    }
//}


class CameraFragment : Fragment() {

    private lateinit var previewView: PreviewView
    private lateinit var overlayView: OverlayView
    private var isOverlayOn = false
    private lateinit var appLogo: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_camera, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        previewView = view.findViewById(R.id.view_finder)
        overlayView = view.findViewById(R.id.overlay)
        appLogo = view.findViewById(R.id.app_logo)
        OverlayManager.setOverlayView(overlayView)

        val overlayButton = view.findViewById<FloatingActionButton>(R.id.OverlayButton)

        overlayButton.setOnClickListener {
            isOverlayOn = !isOverlayOn
            overlayButton.setImageResource(
                if (isOverlayOn) R.drawable.baseline_ac_unit_24 else R.drawable.baseline_face_retouching_off_24
            )
            if (isOverlayOn) startOverlayPreview() else stopOverlayPreview()
        }

    }

    private fun startOverlayPreview() {
        showToast("Overlay Started")
        overlayView.visibility = View.VISIBLE
        appLogo.visibility = View.GONE  // Hide the app logo
    }

    private fun stopOverlayPreview() {
        showToast("Overlay Stopped")
        overlayView.visibility = View.GONE
        appLogo.visibility = View.VISIBLE  // Show the app logo
    }


    override fun onResume() {
        super.onResume()
        if (!PermissionsFragment.hasPermissions(requireContext())) {
            Navigation.findNavController(requireActivity(), R.id.fragment_container)
                .navigate(R.id.action_camera_to_permissions)
        }
    }

    override fun onPause() {
        super.onPause()
        overlayView.visibility = View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
