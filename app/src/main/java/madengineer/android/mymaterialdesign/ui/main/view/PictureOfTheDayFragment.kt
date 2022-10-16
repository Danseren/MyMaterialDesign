package madengineer.android.mymaterialdesign.ui.main.view

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import coil.load
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import madengineer.android.mymaterialdesign.MainActivity
import madengineer.android.mymaterialdesign.R
import madengineer.android.mymaterialdesign.databinding.FragmentPictureOfTheDayBinding
import madengineer.android.mymaterialdesign.ui.main.model.PODServerResponseData
import madengineer.android.mymaterialdesign.ui.main.util.WIKIPEDIA_URL
import madengineer.android.mymaterialdesign.ui.main.viewmodel.AppState
import madengineer.android.mymaterialdesign.ui.main.viewmodel.MainViewModel

class PictureOfTheDayFragment : Fragment() {

    private var _binding: FragmentPictureOfTheDayBinding? = null
    private val binding get() = _binding!!

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    lateinit var mainViewModel: MainViewModel

    companion object {
        fun newInstance(fragmentNumber: Int): PictureOfTheDayFragment {
            val arg = Bundle()
            arg.putInt(INT_NUMBER, fragmentNumber)
            val fragment = PictureOfTheDayFragment()
            fragment.arguments = arg
            return fragment
        }
        private var isMain = true
        private const val TODAY = 0
        private const val YESTERDAY = 1
        private const val BEFORE_YESTERDAY = 2
        private const val INT_NUMBER = "int_number"
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainViewModel = (context as MainActivity).mainViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPictureOfTheDayBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel.getData().observe(viewLifecycleOwner, {renderData(it)})
        setBottomSheetBehavior(view.findViewById(R.id.bottom_sheet_container))
        binding.inputLayout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data =
                    Uri.parse("$WIKIPEDIA_URL${binding.inputEditText.text.toString()}")
            })
        }
        val args = arguments
        if (args?.getInt(INT_NUMBER) == TODAY) {
            mainViewModel.getPOD(TODAY)
        } else if (args?.getInt(INT_NUMBER) == YESTERDAY) {
            mainViewModel.getPOD(YESTERDAY)
        } else {
            mainViewModel.getPOD(BEFORE_YESTERDAY)
        }
    }

    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED

    }

    private fun createBottomSheet(serverResponseData: PODServerResponseData) {
        (view?.findViewById(R.id.bottom_sheet_description_header) as TextView).let {
            it.text = "${serverResponseData.title}"
        }
        (view?.findViewById(R.id.bottom_sheet_description) as TextView).let {
            it.text = "${serverResponseData.explanation}"
        }
    }

    private fun renderData(appState: AppState) {
        when(appState) {
            is AppState.Error ->
                Snackbar.make(binding.root, appState.error.toString(), Snackbar.LENGTH_SHORT).show()
            is AppState.Loading -> {
                binding.imageView.load(R.drawable.progress_animation)
            }
            is AppState.SuccessPOD -> {
                setData(appState)
                createBottomSheet(appState.serverResponseData)
            }
        }
    }

    private fun setData(data: AppState.SuccessPOD) {
        val url = data.serverResponseData.hdurl
        binding.imageView.load(url)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}