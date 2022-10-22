package madengineer.android.mymaterialdesign.ui.main.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnticipateOvershootInterpolator
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import coil.load
import com.google.android.material.snackbar.Snackbar
import madengineer.android.mymaterialdesign.MainActivity
import madengineer.android.mymaterialdesign.R
import madengineer.android.mymaterialdesign.databinding.FragmentSettingsBinding
import madengineer.android.mymaterialdesign.ui.main.viewmodel.AppState
import madengineer.android.mymaterialdesign.ui.main.viewmodel.MainViewModel

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    lateinit var mainViewModel: MainViewModel
    var isFlag = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel.getData().observe(viewLifecycleOwner, {renderData(it)})
        mainViewModel.getPOD(0)

        val constraintSet = ConstraintSet()
        constraintSet.clone(requireContext(), R.layout.fragment_settings)

        binding.btnFirst.setOnClickListener {
            binding.textViewDescription.textSize = 8F
            isFlag = !isFlag
            val chageBounds = ChangeBounds()
            chageBounds.duration = 2000L
            chageBounds.interpolator = AnticipateOvershootInterpolator(5.0f)
            TransitionManager.beginDelayedTransition(binding.constraintContainer, chageBounds)
            if (isFlag) {
                constraintSet.apply {
                    connect(R.id.textViewConstraint, ConstraintSet.END, R.id.constraint_container, ConstraintSet.END)
                    connect(R.id.btnFirst, ConstraintSet.TOP, R.id.textViewConstraint, ConstraintSet.BOTTOM)
                    connect(R.id.btnFirst, ConstraintSet.END, R.id.constraint_container, ConstraintSet.END)
                    connect(R.id.btnSecond, ConstraintSet.BOTTOM, R.id.constraint_container, ConstraintSet.BOTTOM)
                    connect(R.id.btnThird, ConstraintSet.TOP, R.id.btnSecond, ConstraintSet.BOTTOM)
                }
            } else {
                constraintSet.apply {
                    clear(R.id.textViewConstraint, ConstraintSet.END)
                    clear(R.id.btnFirst, ConstraintSet.END)
                    connect(R.id.btnFirst, ConstraintSet.TOP, R.id.guideline, ConstraintSet.BOTTOM)
                    clear(R.id.btnSecond, ConstraintSet.BOTTOM)
                    clear(R.id.btnThird, ConstraintSet.TOP)
                }
            }
            constraintSet.applyTo(binding.constraintContainer)
        }
        binding.btnSecond.setOnClickListener {
            binding.textViewDescription.textSize = 12F
        }
        binding.btnThird.setOnClickListener {
            binding.textViewDescription.textSize = 16F
        }
    }

    private fun renderData(appState: AppState?) {
        when(appState) {
            is AppState.Error ->
                Snackbar.make(binding.root, appState.error.toString(), Snackbar.LENGTH_SHORT).show()
            is AppState.Loading -> {
                binding.imageView.load(R.drawable.progress_animation)
            }
            is AppState.SuccessPOD -> {
                val url = appState.serverResponseData.hdurl
                binding.imageView.load(url)
                binding.textViewDescription.text = appState.serverResponseData.explanation
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainViewModel = (context as MainActivity).mainViewModel
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}