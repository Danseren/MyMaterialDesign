package madengineer.android.mymaterialdesign.ui.main.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.BlurMaskFilter
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.text.SpannableString
import android.text.style.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnticipateOvershootInterpolator
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.transition.ChangeBounds
import androidx.transition.ChangeImageTransform
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import coil.load
import com.google.android.material.snackbar.Snackbar
import madengineer.android.mymaterialdesign.MainActivity
import madengineer.android.mymaterialdesign.R
import madengineer.android.mymaterialdesign.R.color.colorAccent
import madengineer.android.mymaterialdesign.R.color.sun_colorSecondary
import madengineer.android.mymaterialdesign.databinding.FragmentSettingsBinding
import madengineer.android.mymaterialdesign.ui.main.viewmodel.AppState
import madengineer.android.mymaterialdesign.ui.main.viewmodel.MainViewModel

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    lateinit var mainViewModel: MainViewModel
    var isFlag = false
    var isFlagForImage = false
    var duration = 2000L

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
        mainViewModel.getData().observe(viewLifecycleOwner, { renderData(it) })
        mainViewModel.getPOD(0)

        val constraintSet = ConstraintSet()
        constraintSet.clone(requireContext(), R.layout.fragment_settings)

        binding.btnFirst.setOnClickListener {
            binding.textViewDescription.textSize = 8F
            isFlag = !isFlag
            val chageBounds = ChangeBounds()
            chageBounds.duration = duration
            chageBounds.interpolator = AnticipateOvershootInterpolator(5.0f)
            TransitionManager.beginDelayedTransition(binding.constraintContainer, chageBounds)
            if (isFlag) {
                constraintSet.apply {
                    connect(
                        R.id.textViewConstraint,
                        ConstraintSet.END,
                        R.id.constraint_container,
                        ConstraintSet.END
                    )
                    connect(
                        R.id.btnFirst,
                        ConstraintSet.TOP,
                        R.id.textViewConstraint,
                        ConstraintSet.BOTTOM
                    )
                    connect(
                        R.id.btnFirst,
                        ConstraintSet.END,
                        R.id.constraint_container,
                        ConstraintSet.END
                    )
                    connect(
                        R.id.btnSecond,
                        ConstraintSet.BOTTOM,
                        R.id.constraint_container,
                        ConstraintSet.BOTTOM
                    )
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
        binding.imageView.setOnClickListener {
            isFlagForImage = !isFlagForImage
            val params = it.layoutParams as ConstraintLayout.LayoutParams

            val transitionSet = TransitionSet()
            val changeImageTransform = ChangeImageTransform()
            val changeBounds = ChangeBounds()
            changeBounds.duration = duration
            changeImageTransform.duration = duration
            transitionSet.ordering = TransitionSet.ORDERING_TOGETHER
            transitionSet.addTransition(changeBounds)
            transitionSet.addTransition(changeImageTransform)

            TransitionManager.beginDelayedTransition(binding.root, transitionSet)
            if (isFlagForImage) {
                params.height = ConstraintLayout.LayoutParams.MATCH_PARENT
                binding.imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            } else {
                params.height = ConstraintLayout.LayoutParams.WRAP_CONTENT
                binding.imageView.scaleType = ImageView.ScaleType.CENTER_INSIDE
            }
            binding.imageView.layoutParams = params
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    @SuppressLint("ResourceAsColor")
    private fun renderData(appState: AppState?) {
        when (appState) {
            is AppState.Error ->
                Snackbar.make(binding.root, appState.error.toString(), Snackbar.LENGTH_SHORT).show()
            is AppState.Loading -> {
                binding.imageView.load(R.drawable.progress_animation)
            }
            is AppState.SuccessPOD -> {
                val url = appState.serverResponseData.hdurl
                binding.imageView.load(url)

                val text = appState.serverResponseData.explanation?.split(" ")
                val spannableString = SpannableString(appState.serverResponseData.explanation)

                if (text != null) {

                    if (text.size > 24) {
                        val divisor = text.toString().length / 10

                        spannableString.setSpan(
                            LineBackgroundSpan.Standard(
                                ContextCompat.getColor(requireContext(),sun_colorSecondary)),
                            0,
                            divisor,
                            0
                        )
                        spannableString.setSpan(
                            ForegroundColorSpan(ContextCompat.getColor(requireContext(),colorAccent)),
                            divisor,
                            divisor * 2,
                            0
                        )
                        spannableString.setSpan(
                            StyleSpan(Typeface.BOLD),divisor * 2,divisor * 3,0
                        )
                        spannableString.setSpan(
                            UnderlineSpan(), divisor * 3, divisor * 4, 0
                        )
                        spannableString.setSpan(
                            StrikethroughSpan(), divisor * 4, divisor * 5, 0
                        )
                        spannableString.setSpan(
                            TextAppearanceSpan(requireContext(), R.style.SpanTextAppearance),
                            divisor * 5,
                            divisor * 6,
                            0
                        )
                        spannableString.setSpan(
                            MaskFilterSpan(BlurMaskFilter(5f, BlurMaskFilter.Blur.SOLID)),
                            divisor * 6,
                            divisor * 7,
                            0
                        )
                    }
                }

                binding.textViewDescription.text = spannableString

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