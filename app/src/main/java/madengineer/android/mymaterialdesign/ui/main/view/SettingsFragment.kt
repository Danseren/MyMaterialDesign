package madengineer.android.mymaterialdesign.ui.main.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.load
import com.google.android.material.snackbar.Snackbar
import madengineer.android.mymaterialdesign.MainActivity
import madengineer.android.mymaterialdesign.R
import madengineer.android.mymaterialdesign.databinding.FragmentSettingsBinding
import madengineer.android.mymaterialdesign.ui.main.model.PODServerResponseData
import madengineer.android.mymaterialdesign.ui.main.viewmodel.AppState
import madengineer.android.mymaterialdesign.ui.main.viewmodel.MainViewModel

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    lateinit var mainViewModel: MainViewModel

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
        binding.btnFirst.setOnClickListener {
            binding.textViewDescription.textSize = 8F
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