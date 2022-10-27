package madengineer.android.mymaterialdesign.ui.main.view.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import madengineer.android.mymaterialdesign.databinding.FragmentRecyclerBinding
import madengineer.android.mymaterialdesign.ui.main.model.Data
import madengineer.android.mymaterialdesign.ui.main.model.TYPE_HEADER
import madengineer.android.mymaterialdesign.ui.main.model.TYPE_NOTE

class RecyclerFragment : Fragment() {

    private var _binding: FragmentRecyclerBinding? = null
    private val binding: FragmentRecyclerBinding
        get() {
            return _binding!!
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecyclerBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val data = arrayListOf(
            Data("Заголовок", type = TYPE_HEADER),
            Data("Note", type = TYPE_NOTE),
            Data("Note", type = TYPE_NOTE),
            Data("Note", type = TYPE_NOTE),
            Data("Note", type = TYPE_NOTE)
        )
        binding.recyclerView.adapter = RecyclerAdapter(data)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}