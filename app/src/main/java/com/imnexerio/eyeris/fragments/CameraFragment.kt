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
        OverlayManager.setOverlayView(overlayView)
        overlayView.visibility = View.VISIBLE
    }

    private fun stopOverlayPreview() {
        OverlayManager.clearOverlayView()
        overlayView.visibility = View.GONE

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
    }

}
