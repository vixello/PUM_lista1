package pl.edu.uwr.zadanie_01

import android.content.Intent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
const val EXTRA_REPLY = "pl.edu.uwr.pum.explicitintentkotlin.REPLY"

class SecondActivityy : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_activityy)
        supportActionBar?.hide()

        val message = intent.getStringExtra(EXTRA_MESSAGE)
        findViewById<TextView>(R.id.cheat).apply {
            text = message
        }
    }

    fun returnMessage(view: View) {
        setResult(
            RESULT_OK,
            Intent().apply {
                putExtra(
                    EXTRA_REPLY,
                    findViewById<TextView>(R.id.cheat).text
                )
            })
        finish()
    }
}