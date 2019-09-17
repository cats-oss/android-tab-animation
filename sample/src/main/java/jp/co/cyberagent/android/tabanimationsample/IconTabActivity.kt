package jp.co.cyberagent.android.tabanimationsample

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import jp.co.cyberagent.android.tabanimation.SimpleTab
import jp.co.cyberagent.android.tabanimation.ViewIdAnimationInfoSet
import jp.co.cyberagent.android.tabanimation.property
import jp.co.cyberagent.android.tabanimation.viewIdAnimationInfo

class IconTabActivity : BaseActivity() {

  companion object {
    private const val TAB_SELECTED_SCALE = 1f
    private const val TAB_UNSELECTED_SCALE = 0.8f
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

  override val tabType: SimpleTab = SimpleTab.ICON
}
