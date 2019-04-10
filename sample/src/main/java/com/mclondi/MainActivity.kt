package com.mclondi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ticketView.setOnClickListener {
            Toast.makeText(this, "Click", Toast.LENGTH_SHORT).show()
            ticketView.backgroundDrawable?.perforationBackgroundDrawable = this.getDrawable(R.drawable.bg_gradient_content)
        }
    }
}
