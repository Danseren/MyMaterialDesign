package madengineer.android.mymaterialdesign.ui.main.view.navigation

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import madengineer.android.mymaterialdesign.ui.main.view.PictureOfTheDayFragment

class ViewPager2AdapterPOD(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private val fragments = arrayOf(PictureOfTheDayFragment.newInstance(), PictureOfTheDayFragment.newInstance(), PictureOfTheDayFragment.newInstance())

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}