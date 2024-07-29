package com.imnexerio.eyeris.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.os.Build
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.camera.view.PreviewView
import androidx.navigation.Navigation
import com.google.android.material.floatingactionbutton.FloatingActionButton
//import com.imnexerio.eyeris.CameraUtils
import com.imnexerio.eyeris.helpers.OverlayManager
import com.imnexerio.eyeris.OverlayView
import com.imnexerio.eyeris.R


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


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onResume() {
        super.onResume()
        if (!PermissionsFragment.hasPermissions(requireContext())) {
            Navigation.findNavController(requireActivity(), R.id.fragment_container)
                .navigate(R.id.Analytics_fragment)
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
