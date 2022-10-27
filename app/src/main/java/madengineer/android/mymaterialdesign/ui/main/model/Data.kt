package madengineer.android.mymaterialdesign.ui.main.model

const val TYPE_HEADER = 0
const val TYPE_NOTE = 1

data class Data(
    val title: String = "Title",
    val noteBody: String? = "Description",
    val type: Int = TYPE_NOTE
)
