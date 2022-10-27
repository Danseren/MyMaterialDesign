package madengineer.android.mymaterialdesign

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import madengineer.android.mymaterialdesign.databinding.MainActivityBinding
import madengineer.android.mymaterialdesign.ui.main.view.PictureOfTheDayFragment
import madengineer.android.mymaterialdesign.ui.main.view.SettingsFragment
import madengineer.android.mymaterialdesign.ui.main.view.navigation.MarsFragment
import madengineer.android.mymaterialdesign.ui.main.view.navigation.RecyclerFragment
import madengineer.android.mymaterialdesign.ui.main.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {


    val mainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigation.apply {
            setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.action_bottom_navigation_constraint -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.container, SettingsFragment()).commit()
                        true
                    }
                    R.id.action_bottom_navigation_coordinator -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.container, RecyclerFragment()).commit()
                        true
                    }
                    R.id.action_bottom_navigation_motion -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.container, MarsFragment()).commit()
                        true
                    }
                    else -> {
                        true
                    }
                }
            }
            selectedItemId = R.id.action_bottom_navigation_constraint
        }
    }
}