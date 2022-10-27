package madengineer.android.mymaterialdesign.ui.main.view.navigation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import madengineer.android.mymaterialdesign.databinding.ActivityRecyclerItemHeaderBinding
import madengineer.android.mymaterialdesign.databinding.ActivityRecyclerItemNoteBinding
import madengineer.android.mymaterialdesign.ui.main.model.Data
import madengineer.android.mymaterialdesign.ui.main.model.TYPE_NOTE

class RecyclerAdapter(private val listData: List<Data>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return listData[position].type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_NOTE -> {
                val binding =
                    ActivityRecyclerItemNoteBinding.inflate(LayoutInflater.from(parent.context))
                NoteViewHolder(binding)
            }
            else -> {
                val binding =
                    ActivityRecyclerItemHeaderBinding.inflate(LayoutInflater.from(parent.context))
                HeaderViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    class NoteViewHolder(val binding: ActivityRecyclerItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    class HeaderViewHolder(val binding: ActivityRecyclerItemHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

}