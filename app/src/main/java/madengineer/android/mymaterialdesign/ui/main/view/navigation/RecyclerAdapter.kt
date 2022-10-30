package madengineer.android.mymaterialdesign.ui.main.view.navigation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import madengineer.android.mymaterialdesign.databinding.ActivityRecyclerItemHeaderBinding
import madengineer.android.mymaterialdesign.databinding.ActivityRecyclerItemNoteBinding
import madengineer.android.mymaterialdesign.ui.main.model.Data
import madengineer.android.mymaterialdesign.ui.main.model.TYPE_NOTE

class RecyclerAdapter(
    private var listData: List<Data>,
    val callbackAdd: AddItem,
    val callbackRemove: RemoveItem
) :
    RecyclerView.Adapter<RecyclerAdapter.BaseViewHolder>() {

    fun setListDataRemove(listDataView: List<Data>, position: Int) {
        listData = listDataView
        notifyItemRemoved(position)
    }

    fun setListDataAdd(listDataNew: List<Data>, position: Int) {
        listData = listDataNew
        notifyItemInserted(position)
    }

    override fun getItemViewType(position: Int): Int {
        return listData[position].type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
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

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    inner class NoteViewHolder(val binding: ActivityRecyclerItemNoteBinding) :
        BaseViewHolder(binding.root) {
        override fun bind(data: Data) {
            binding.title.text = data.title
            binding.addItemImageView.setOnClickListener {
                callbackAdd.add(layoutPosition)
            }
            binding.removeItemImageView.setOnClickListener {
                callbackRemove.remove(layoutPosition)
            }
        }
    }

    class HeaderViewHolder(val binding: ActivityRecyclerItemHeaderBinding) :
        BaseViewHolder(binding.root) {
        override fun bind(data: Data) {
            binding.title.text = data.title
        }
    }

    abstract class BaseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(data: Data)
    }

}