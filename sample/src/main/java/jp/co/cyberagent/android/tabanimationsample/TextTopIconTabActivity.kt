package jp.co.cyberagent.android.tabanimationsample

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import jp.co.cyberagent.android.tabanimation.SimpleTab
import jp.co.cyberagent.android.tabanimation.TAB_ROOT_ID
import jp.co.cyberagent.android.tabanimation.ViewIdAnimationInfoSet
import jp.co.cyberagent.android.tabanimation.property
import jp.co.cyberagent.android.tabanimation.viewIdAnimationInfo

class TextTopIconTabActivity : BaseActivity() {

  companion object {
    private const val TAB_SELECTED_SCALE = 1f
    private const val TAB_UNSELECTED_SCALE = 0.9f
  }

  override val animationInfo: ViewIdAnimationInfoSet =
    viewIdAnimationInfo {
      animate<View, Float>(TAB_ROOT_ID) {
        property(View.SCALE_X)
        startValue(TAB_UNSELECTED_SCALE)
        endValue(TAB_SELECTED_SCALE)
      }
      animate<View, Float>(TAB_ROOT_ID) {
        property(View.SCALE_Y)
        startValue(TAB_UNSELECTED_SCALE)
        endValue(TAB_SELECTED_SCALE)
      }
      animateText<Int> {
        property(
          getter = { currentTextColor },
          setter = { setTextColor(it) }
        )
        startValue(Color.GRAY)
        endValue { ContextCompat.getColor(it.context, R.color.colorAccent) }
      }
      animateIcon<ColorStateList> {
        property(
          getter = { ImageViewCompat.getImageTintList(this)!! },
          setter = { ImageViewCompat.setImageTintList(this, it) }
        )
        startValue(ColorStateList.valueOf(Color.GRAY))
        endValue { ColorStateList.valueOf(ContextCompat.getColor(it.context,
          R.color.colorAccent
        )) }
      }
    }

  override val tabType: SimpleTab = SimpleTab.TEXT_ICON
}
