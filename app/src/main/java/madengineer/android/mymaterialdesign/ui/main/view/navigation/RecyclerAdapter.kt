package madengineer.android.mymaterialdesign.ui.main.view.navigation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import madengineer.android.mymaterialdesign.R
import madengineer.android.mymaterialdesign.databinding.ActivityRecyclerItemHeaderBinding
import madengineer.android.mymaterialdesign.databinding.ActivityRecyclerItemNoteBinding
import madengineer.android.mymaterialdesign.ui.main.model.Data
import madengineer.android.mymaterialdesign.ui.main.model.TYPE_NOTE

class RecyclerAdapter(
    private var listData: MutableList<Pair<Data, Boolean>>,
    val callbackAdd: AddItem,
    val callbackRemove: RemoveItem
) :
    RecyclerView.Adapter<RecyclerAdapter.BaseViewHolder>(), ItemTouchHelperAdapter {

    fun setListDataRemove(listDataView: MutableList<Pair<Data, Boolean>>, position: Int) {
        listData = listDataView
        notifyItemRemoved(position)
    }

    fun setListDataAdd(listDataNew: MutableList<Pair<Data, Boolean>>, position: Int) {
        listData = listDataNew
        notifyItemInserted(position)
    }

    override fun getItemViewType(position: Int): Int {
        return listData[position].first.type
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
        override fun bind(data: Pair<Data, Boolean>) {
            binding.title.text = data.first.title
            binding.addItemImageView.setOnClickListener {
                callbackAdd.add(layoutPosition)
            }
            binding.removeItemImageView.setOnClickListener {
                callbackRemove.remove(layoutPosition)
            }
            binding.moveItemUp.setOnClickListener {
                if (layoutPosition > 1) {
                    listData.removeAt(layoutPosition).apply {
                        listData.add(layoutPosition - 1, this)
                    }
                    notifyItemMoved(layoutPosition, layoutPosition - 1)
                }
            }
            binding.moveItemDown.setOnClickListener {
                if (layoutPosition != listData.size - 1) {
                    listData.removeAt(layoutPosition).apply {
                        listData.add(layoutPosition + 1, this)
                    }
                    notifyItemMoved(layoutPosition, layoutPosition + 1)
                }
            }
            binding.editTextTextPersonName.visibility =
                if (listData[layoutPosition].second) View.VISIBLE else View.GONE
            binding.noteBody.setOnClickListener {
                listData[layoutPosition] = listData[layoutPosition].let {
                    it.first to !it.second
                }
                notifyItemChanged(layoutPosition)
            }
        }
    }

    class HeaderViewHolder(val binding: ActivityRecyclerItemHeaderBinding) :
        BaseViewHolder(binding.root) {
        override fun bind(data: Pair<Data, Boolean>) {
            binding.title.text = data.first.title
        }
    }

    abstract class BaseViewHolder(view: View) : RecyclerView.ViewHolder(view),
        ItemTouchHelperViewHolder {
        abstract fun bind(data: Pair<Data, Boolean>)
        override fun onItemSelect() {
            itemView.setBackgroundColor(
                ContextCompat.getColor(itemView.context, R.color.color_gray)
            )
        }

        override fun onItemClear() {
            itemView.setBackgroundColor(0)
        }
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        listData.removeAt(fromPosition).apply {
            listData.add(toPosition, this)
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemDismiss(position: Int) {
        callbackRemove.remove(position)
    }
}