package org.oxycblt.auxio

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import org.oxycblt.auxio.theme.accent

class MainActivity : AppCompatActivity() {

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        setTheme(accent.second)

        return super.onCreateView(name, context, attrs)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        Log.d(this::class.simpleName, "Activity Created.")
    }
}
