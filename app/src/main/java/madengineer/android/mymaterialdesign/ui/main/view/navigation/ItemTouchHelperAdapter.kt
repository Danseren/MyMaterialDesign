package madengineer.android.mymaterialdesign.ui.main.view.navigation

interface ItemTouchHelperAdapter {
    fun onItemMove(fromPosition: Int, toPosition: Int)
    fun onItemDismiss(position: Int)
}