package madengineer.android.mymaterialdesign.ui.main.view.navigation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.load
import com.google.android.material.snackbar.Snackbar
import madengineer.android.mymaterialdesign.BuildConfig
import madengineer.android.mymaterialdesign.MainActivity
import madengineer.android.mymaterialdesign.R
import madengineer.android.mymaterialdesign.databinding.FragmentEarthBinding
import madengineer.android.mymaterialdesign.ui.main.util.EPIC_NASA
import madengineer.android.mymaterialdesign.ui.main.viewmodel.AppState
import madengineer.android.mymaterialdesign.ui.main.viewmodel.MainViewModel

class EarthFragment : Fragment() {

    private var _binding: FragmentEarthBinding? = null
    private val binding: FragmentEarthBinding
        get() {
            return _binding!!
        }

    lateinit var mainViewModel: MainViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainViewModel = (context as MainActivity).mainViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEarthBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel.getData().observe(viewLifecycleOwner, { render(it) })
        mainViewModel.getMarsPicture()
    }

    private fun render(appState: AppState) {
        when (appState) {
            is AppState.SuccessEarthEpic -> {
                val strDate = appState.serverResponseData.last().date.split(" ").first()
                val image = appState.serverResponseData.last().image
                binding.imageEarth.load(createEpicUrl(strDate, image))
            }
            is AppState.Error ->
                Snackbar.make(binding.root, appState.error.toString(), Snackbar.LENGTH_SHORT).show()
            is AppState.Loading -> {
                binding.imageEarth.load(R.drawable.progress_animation)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(): EarthFragment {
            return EarthFragment()
        }
    }

    private fun createEpicUrl (strDate: String, image: String): String {
        val url = EPIC_NASA +
                strDate.replace("-", "/", true) +
                "/png/" +
                "$image" +
                ".png?api_key=${BuildConfig.NASA_API_KEY}"
        return url
    }
}