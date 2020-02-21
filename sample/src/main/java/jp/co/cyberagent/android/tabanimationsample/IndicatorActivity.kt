package jp.co.cyberagent.android.tabanimationsample

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.RecyclerView
import jp.co.cyberagent.android.tabanimation.property
import jp.co.cyberagent.android.tabanimation.setupAnimationTabWithViewPager
import jp.co.cyberagent.android.tabanimation.viewIdAnimationInfo
import kotlinx.android.synthetic.main.activity_indicator.*
import kotlinx.android.synthetic.main.fragment_pager.view.*

class IndicatorActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_indicator)

    view_pager.adapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

      override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
          LayoutInflater.from(parent.context).inflate(R.layout.fragment_pager, parent, false)
        return object : RecyclerView.ViewHolder(view) {}
      }

      override fun getItemCount(): Int = 5

      override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        with(holder.itemView) {
          setBackgroundColor(Color.parseColor("#141418"))
          text_view.text = "Page ${position + 1}"
          text_view.setTextColor(Color.WHITE)
        }
    }

    val animationInfo = viewIdAnimationInfo {
      animate<ImageView, Int>(R.id.image_indicator) {
        property(
          getter = { ImageViewCompat.getImageTintList(this)!!.defaultColor },
          setter = { ImageViewCompat.setImageTintList(this, ColorStateList.valueOf(it)) }
        )
        startValue(Color.parseColor("#37373D"))
        endValue(Color.parseColor("#979797"))
      }
      animate<ImageView, Float>(R.id.image_indicator) {
        property(View.SCALE_X)
        startValue(1f)
        endValue(1.4f)
      }
      animate<ImageView, Float>(R.id.image_indicator) {
        property(View.SCALE_Y)
        startValue(1f)
        endValue(1.4f)
      }
    }
    tab_indicator.setupAnimationTabWithViewPager(
      view_pager,
      animationInfo,
      R.layout.layout_indicator
    )
  }
}
