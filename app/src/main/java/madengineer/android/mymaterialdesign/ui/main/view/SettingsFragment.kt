package madengineer.android.mymaterialdesign.ui.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import madengineer.android.mymaterialdesign.R
import madengineer.android.mymaterialdesign.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

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
        binding.chipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            when (group.checkedChipId) {
                binding.firstChip.id -> requireActivity().setTheme(R.style.Theme_MyMaterialDesign)
                binding.secondChip.id -> requireActivity().setTheme(R.style.SpaceTheme)
                binding.thirdChip.id -> requireActivity().setTheme(R.style.SunTheme)
            }
        }
    }
}