package jp.co.cyberagent.android.tabanimation

import android.animation.TimeInterpolator
import android.view.View
import androidx.annotation.IdRes

class ViewIdAnimationInfo<T : View, V> internal constructor(
  @IdRes private val id: Int,
  private val animationInfo: AnimationInfo<T, V>
) {
  internal fun bindTo(
    root: View,
    duration: Long,
    interpolator: TimeInterpolator
  ): ViewAnimationInfo<T, V> {
    return ViewAnimationInfo(
      root,
      root.findViewById(id),
      animationInfo,
      duration,
      interpolator
    )
  }
}
