package jp.co.cyberagent.android.tabanimation

import android.animation.TimeInterpolator

internal class ReverseInterpolator(
  private val interpolator: TimeInterpolator
) : TimeInterpolator {

  override fun getInterpolation(input: Float): Float {
    return 1f - interpolator.getInterpolation(1f - input)
  }
}
