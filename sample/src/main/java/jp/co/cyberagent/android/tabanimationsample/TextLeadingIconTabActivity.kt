package jp.co.cyberagent.android.tabanimationsample

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import com.google.android.material.tabs.TabLayout
import jp.co.cyberagent.android.tabanimation.SimpleTab
import jp.co.cyberagent.android.tabanimation.ViewIdAnimationInfoSet
import jp.co.cyberagent.android.tabanimation.property
import jp.co.cyberagent.android.tabanimation.viewIdAnimationInfo
import kotlinx.android.synthetic.main.include_tab_view_pager.*

class TextLeadingIconTabActivity : BaseActivity() {

  companion object {
    private const val TAB_SELECTED_SCALE = 0.9f
    private const val TAB_UNSELECTED_SCALE = 1f
  }

  override val animationInfo: ViewIdAnimationInfoSet =
    viewIdAnimationInfo {
      animateIcon<Float> {
        property(View.SCALE_X)
        startValue { TAB_UNSELECTED_SCALE }
        endValue { TAB_SELECTED_SCALE }
      }
      animateIcon<Float> {
        property(View.SCALE_Y)
        startValue { TAB_UNSELECTED_SCALE }
        endValue { TAB_SELECTED_SCALE }
      }
      animateText<Int> {
        property(
          getter = { currentTextColor },
          setter = { setTextColor(it) }
        )
        startValue { Color.GRAY }
        endValue { ContextCompat.getColor(it.context, R.color.colorAccent) }
      }
      animateIcon<Int> {
        property(
          getter = { ImageViewCompat.getImageTintList(this)!!.defaultColor },
          setter = { ImageViewCompat.setImageTintList(this, ColorStateList.valueOf(it)) }
        )
        startValue { Color.GRAY }
        endValue { ContextCompat.getColor(it.context, R.color.colorAccent) }
      }
      animateText<Float> {
        property(View.ALPHA)
        startValue { 0f }
        endValue { 1f }
        keyFrame(0.8f) { 0.2f }
      }
      animateIcon<Float> {
        property(View.TRANSLATION_X)
        startValue { root ->
          val icon = SimpleTab.icon(root)!!
          root.width / 2f - icon.width / 2f
        }
        endValue { 0f }
      }
    }

  override val tabType: SimpleTab = SimpleTab.TEXT_ICON

  override fun setUpTabViewPager() {
    tab_layout.isInlineLabel = true
    tab_layout.tabMode = TabLayout.MODE_SCROLLABLE
    super.setUpTabViewPager()
  }
}
