package jp.co.cyberagent.android.tabanimationsample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import jp.co.cyberagent.android.tabanimation.setupAnimationTabWithViewPager
import jp.co.cyberagent.android.tabanimation.viewIdAnimationInfo
import kotlinx.android.synthetic.main.activity_bottom_navigation.*
import kotlinx.android.synthetic.main.fragment_pager.view.*
import kotlinx.android.synthetic.main.layout_bottom_navigation_item.view.*

class BottomNavigationActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_bottom_navigation)

    view_pager.adapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

      override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
          LayoutInflater.from(parent.context).inflate(R.layout.fragment_pager, parent, false)
        return object : RecyclerView.ViewHolder(view) {}
      }

      override fun getItemCount(): Int = 4

      override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        with(holder.itemView) {
          text_view.text = "Page ${position + 1}"
        }
    }

    val animationInfo = viewIdAnimationInfo {
      animate<ImageView, Float>(R.id.image) {
        property(View.TRANSLATION_Y)
        startValue { it.height.toFloat() }
        endValue { 0f }
      }
      animate<TextView, Float>(R.id.text) {
        property(View.TRANSLATION_Y)
        startValue { 0f }
        endValue { -it.height.toFloat() }
      }
    }
    tab_indicator.setupAnimationTabWithViewPager(
      view_pager,
      animationInfo,
      R.layout.layout_bottom_navigation_item
    ) { _, view, pos ->
      with(view) {
        text.text = pagerSet[pos].text
        image.setImageResource(pagerSet[pos].icon)
      }
    }
  }
}
