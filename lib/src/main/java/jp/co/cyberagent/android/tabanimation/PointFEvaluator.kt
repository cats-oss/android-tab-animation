package jp.co.cyberagent.android.tabanimation

import android.animation.TypeEvaluator
import android.graphics.PointF

internal object PointFEvaluator : TypeEvaluator<PointF> {
  override fun evaluate(fraction: Float, startValue: PointF, endValue: PointF): PointF {
    val x = startValue.x + fraction * (endValue.x - startValue.x)
    val y = startValue.y + fraction * (endValue.y - startValue.y)
    return PointF(x, y)
  }
}
