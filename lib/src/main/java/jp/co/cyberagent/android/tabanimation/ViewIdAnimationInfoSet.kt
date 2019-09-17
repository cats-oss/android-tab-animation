package jp.co.cyberagent.android.tabanimation

import android.view.View
import android.view.animation.Interpolator
import androidx.interpolator.view.animation.FastOutSlowInInterpolator

class ViewIdAnimationInfoSet internal constructor(
  private val infoSet: List<ViewIdAnimationInfo<*, *>>,
  private val duration: Long,
  private val interpolator: Interpolator = FastOutSlowInInterpolator()
) {

  internal fun bindTo(root: View): ViewAnimationInfoSet {
    return ViewAnimationInfoSet(
      infoSet.map { it.bindTo(root, duration, interpolator) }
    )
  }
}
