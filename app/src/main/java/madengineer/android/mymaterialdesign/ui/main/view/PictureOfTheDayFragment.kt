package madengineer.android.mymaterialdesign.ui.main.view

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import madengineer.android.mymaterialdesign.databinding.FragmentPictureOfTheDayBinding
import madengineer.android.mymaterialdesign.ui.main.viewmodel.PictureOfTheDayViewModel
import madengineer.android.mymaterialdesign.ui.main.viewmodel.PictureOfTheDayData

class PictureOfTheDayFragment : Fragment() {

    private var _binding: FragmentPictureOfTheDayBinding? = null
    private val binding get() = _binding!!

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    companion object {
        fun newInstance() = PictureOfTheDayFragment()
        private var isMain = true
    }

    private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(PictureOfTheDayViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.getData()
            .observe(viewLifecycleOwner) { renderData(it) }
        _binding = FragmentPictureOfTheDayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //setBottomSheetBehavior(view.findViewById(R.id.bottom_sheet_container))
        binding.inputLayout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data =
                    Uri.parse("https://en.wikipedia.org/wiki/${binding.inputEditText.text.toString()}")
            })
        }
        //setBottomAppBar(view)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return super.onOptionsItemSelected(item)
    }

    private fun setBottomSheetBehavior(findViewById: Any) {

    }

    private fun setBottomAppBar(view: View) {

    }

    private fun renderData(data: PictureOfTheDayData) {

    }

    private fun Fragment.toast(string: String?) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).apply {
            setGravity(Gravity.BOTTOM, 0, 250)
            show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}