package madengineer.android.mymaterialdesign.ui.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import madengineer.android.mymaterialdesign.R
import madengineer.android.mymaterialdesign.databinding.BottomNavigationLayoutBinding
import madengineer.android.mymaterialdesign.ui.main.util.NAVIGATION_ONE_TOAST
import madengineer.android.mymaterialdesign.ui.main.util.NAVIGATION_TWO_TOAST
import madengineer.android.mymaterialdesign.ui.main.util.toast

class BottomNavigationDrawerFragment : BottomSheetDialogFragment() {

    private var _binding: BottomNavigationLayoutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomNavigationLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_one -> toast(NAVIGATION_ONE_TOAST)
                R.id.navigation_two -> toast(NAVIGATION_TWO_TOAST)
            }
            true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}