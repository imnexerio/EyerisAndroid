package com.imnexerio.eyeris.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.airbnb.lottie.LottieAnimationView
import com.imnexerio.eyeris.R

class ExercisesFragment : Fragment() {

    private var lottieAnimationView: LottieAnimationView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_exercises, container, false)
        lottieAnimationView = view.findViewById(R.id.lottieAnimationView)
        return view
    }

    override fun onResume() {
        super.onResume()
        lottieAnimationView?.playAnimation()
    }

    override fun onPause() {
        super.onPause()
        lottieAnimationView?.pauseAnimation()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        lottieAnimationView = null
    }
}