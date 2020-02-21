package jp.co.cyberagent.android.tabanimationsample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    button_sample_indicator.setOnClickListener {
      startActivity(Intent(this, IndicatorActivity::class.java))
    }

    button_sample_bottom_navigation.setOnClickListener {
      startActivity(Intent(this, BottomNavigationActivity::class.java))
    }

    text_tab.setOnClickListener {
      startActivity(Intent(this, TextTabActivity::class.java))
    }

    icon_tab.setOnClickListener {
      startActivity(Intent(this, IconTabActivity::class.java))
    }

    text_top_icon_tab.setOnClickListener {
      startActivity(Intent(this, TextTopIconTabActivity::class.java))
    }

    text_leading_icon_tab.setOnClickListener {
      startActivity(Intent(this, TextLeadingIconTabActivity::class.java))
    }

    text_top_icon_tab_2.setOnClickListener {
      startActivity(Intent(this, TextTopIconTab2Activity::class.java))
    }
  }
}
