package madengineer.android.mymaterialdesign.ui.main.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import madengineer.android.mymaterialdesign.MainActivity
import madengineer.android.mymaterialdesign.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val handler = Handler(Looper.getMainLooper())
    private val delay = 3000L
    private val rotation = 1000f
    private val duration = 5000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        splashAnimation()
    }

    private fun splashAnimation() {
        binding.iconSplash
            .animate()
            .rotationBy(rotation)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .duration = duration

        handler.postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, delay)
    }

    override fun onDestroy() {
        handler.removeCallbacksAndMessages(null)
        super.onDestroy()
    }
}