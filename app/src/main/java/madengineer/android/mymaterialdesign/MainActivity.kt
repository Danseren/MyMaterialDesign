package madengineer.android.mymaterialdesign

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import madengineer.android.mymaterialdesign.databinding.MainActivityBinding
import madengineer.android.mymaterialdesign.ui.main.view.PictureOfTheDayFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PictureOfTheDayFragment.newInstance())
                .commitNow()
        }
    }
}