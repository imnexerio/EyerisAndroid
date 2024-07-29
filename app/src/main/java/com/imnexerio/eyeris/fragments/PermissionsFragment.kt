package com.imnexerio.eyeris.fragments

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.imnexerio.eyeris.R
//
//private val PERMISSIONS_REQUIRED = arrayOf(Manifest.permission.CAMERA)
//
//
//class PermissionsFragment : Fragment() {
//
//    private val requestPermissionLauncher =
//        registerForActivityResult(
//            ActivityResultContracts.RequestPermission()
//        ) { isGranted: Boolean ->
//            if (isGranted) {
//                Toast.makeText(
//                    context,
//                    "Permission request granted",
//                    Toast.LENGTH_LONG
//                ).show()
//                navigateToCamera()
//            } else {
//                Toast.makeText(
//                    context,
//                    "Permission request denied",
//                    Toast.LENGTH_LONG
//                ).show()
//            }
//        }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        when (PackageManager.PERMISSION_GRANTED) {
//            ContextCompat.checkSelfPermission(
//                requireContext(),
//                Manifest.permission.CAMERA
//            ) -> {
//                navigateToCamera()
//            }
//            else -> {
//                requestPermissionLauncher.launch(
//                    Manifest.permission.CAMERA
//                )
//            }
//        }
//    }
//
//    private fun navigateToCamera() {
//        lifecycleScope.launchWhenStarted {
//            Navigation.findNavController(
//                requireActivity(),
//                R.id.fragment_container
//            ).navigate(
//                R.id.action_permissions_to_camera
//            )
//        }
//    }
//
//    companion object {
//
//        /** Convenience method used to check if all permissions required by this app are granted */
//        fun hasPermissions(context: Context) = PERMISSIONS_REQUIRED.all {
//            ContextCompat.checkSelfPermission(
//                context,
//                it
//            ) == PackageManager.PERMISSION_GRANTED
//        }
//    }
//}


//

import com.imnexerio.eyeris.services.FaceLandmarkerService


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
private val PERMISSIONS_REQUIRED = arrayOf(
    Manifest.permission.CAMERA,
    Manifest.permission.POST_NOTIFICATIONS
)

class PermissionsFragment : Fragment() {

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions: Map<String, Boolean> ->
            if (permissions.all { it.value }) {
                Toast.makeText(
                    context,
                    "All permissions granted",
                    Toast.LENGTH_LONG
                ).show()
                startFaceLandmarkerService()
                navigateToCamera()
            } else {
                Toast.makeText(
                    context,
                    "Some permissions denied",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (hasPermissions(requireContext())) {
            startFaceLandmarkerService()
            navigateToCamera()
        } else {
            requestPermissionLauncher.launch(PERMISSIONS_REQUIRED)
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun navigateToCamera() {
        if (hasPermissions(requireContext())) {
            lifecycleScope.launchWhenStarted {
                Navigation.findNavController(
                    requireActivity(),
                    R.id.fragment_container
                ).navigate(
                    R.id.Analytics_fragment
                )
            }
        } else {
            Toast.makeText(
                context,
                "Please grant all permissions",
                Toast.LENGTH_LONG
            ).show()
            requestPermissionLauncher.launch(PERMISSIONS_REQUIRED)
        }
    }

    private fun startFaceLandmarkerService() {
        context?.startForegroundService(Intent(context, FaceLandmarkerService::class.java))
    }

    companion object {
        /** Convenience method used to check if all permissions required by this app are granted */
        @RequiresApi(Build.VERSION_CODES.TIRAMISU)
        fun hasPermissions(context: Context) = PERMISSIONS_REQUIRED.all {
            ContextCompat.checkSelfPermission(
                context,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }
}
