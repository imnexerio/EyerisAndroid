package com.imnexerio.eyeris.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.Navigation
import com.imnexerio.eyeris.helpers.OverlayManager
import com.imnexerio.eyeris.views.OverlayView
import com.imnexerio.eyeris.R


class CameraFragment : Fragment() {

    private lateinit var overlayView: OverlayView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_camera, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        overlayView = view.findViewById(R.id.overlay)


    }

    private fun startOverlayPreview() {
//        showToast("Overlay Started")
        OverlayManager.setOverlayView(overlayView)
        overlayView.visibility = View.VISIBLE
//        appLogo.visibility = View.GONE  // Hide the app logo
    }

    private fun stopOverlayPreview() {
//        showToast("Overlay Stopped")
        OverlayManager.clearOverlayView()
        overlayView.visibility = View.GONE
//        appLogo.visibility = View.VISIBLE  // Show the app logo
    }


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onResume() {
        super.onResume()
        if (!PermissionsFragment.hasPermissions(requireContext())) {
            Navigation.findNavController(requireActivity(), R.id.fragment_container)
                .navigate(R.id.Analytics_fragment)
        }else{
            startOverlayPreview()
        }
    }

    override fun onPause() {
        super.onPause()
        stopOverlayPreview()
//        overlayView.visibility = View.GONE
    }

//    private fun showToast(message: String) {
//        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
//    }
}
