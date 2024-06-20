package com.imnexerio.eyeris.fragments

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
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


private val PERMISSIONS_REQUIRED = arrayOf(Manifest.permission.CAMERA, Manifest.permission.POST_NOTIFICATIONS)

class PermissionsFragment : Fragment() {

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
                navigateToCamera()
            } else {
                Toast.makeText(
                    context,
                    "Some permissions denied",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    if (hasPermissions(requireContext())) {
        navigateToCamera()
    } else {
        requestPermissionLauncher.launch(PERMISSIONS_REQUIRED)
    }
}

    private fun navigateToCamera() {
        if (hasPermissions(requireContext())) {
            lifecycleScope.launchWhenStarted {
                Navigation.findNavController(
                    requireActivity(),
                    R.id.fragment_container
                ).navigate(
                    R.id.action_permissions_to_camera
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


    companion object {

        /** Convenience method used to check if all permissions required by this app are granted */
        fun hasPermissions(context: Context) = PERMISSIONS_REQUIRED.all {
            ContextCompat.checkSelfPermission(
                context,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }
}