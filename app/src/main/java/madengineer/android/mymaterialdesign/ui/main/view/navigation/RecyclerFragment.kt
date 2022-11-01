package madengineer.android.mymaterialdesign.ui.main.view.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
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

    private val data = arrayListOf(
        Pair(Data("Заголовок", type = TYPE_HEADER), false),
        Pair(Data("Note", type = TYPE_NOTE), false),
        Pair(Data("Note", type = TYPE_NOTE), false),
        Pair(Data("Note", type = TYPE_NOTE), false),
        Pair(Data("Note", type = TYPE_NOTE), false),
    )
    lateinit var adapter: RecyclerAdapter

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

        adapter = RecyclerAdapter(data, callbackAdd, callbackRemove)
        binding.recyclerView.adapter = adapter
        ItemTouchHelper(ItemTouchHelperCallback(adapter)).attachToRecyclerView(binding.recyclerView)
    }

    private val callbackAdd = object : AddItem {
        override fun add(position: Int) {
            data.add(position, Pair(Data("Note (NEW)", type = TYPE_NOTE), false))
            adapter.setListDataAdd(data, position)
        }
    }

    private val callbackRemove = object : RemoveItem {
        override fun remove(position: Int) {
            data.removeAt(position)
            adapter.setListDataRemove(data, position)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}