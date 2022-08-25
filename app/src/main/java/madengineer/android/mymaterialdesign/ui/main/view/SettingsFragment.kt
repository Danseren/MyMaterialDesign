package madengineer.android.mymaterialdesign.ui.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.chip.Chip
import madengineer.android.mymaterialdesign.databinding.FragmentSettingsBinding
import madengineer.android.mymaterialdesign.ui.main.util.toast

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
        binding.chipGroup.setOnCheckedStateChangeListener { chipGroup, checkedIds ->
            when (chipGroup.checkedChipId) {
                binding.firstChip.id -> toast("first заглушка")
                binding.secondChip.id -> toast("second заглушка")
                binding.thirdChip.id -> toast("third заглушка")
            }
        }
    }
}