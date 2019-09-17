package jp.co.cyberagent.android.tabanimation

import android.animation.TypeEvaluator

internal object FloatArrayEvaluator : TypeEvaluator<FloatArray> {
  override fun evaluate(fraction: Float, startValue: FloatArray, endValue: FloatArray): FloatArray {
    val array = FloatArray(startValue.size)
    for (i in array.indices) {
      val start = startValue[i]
      val end = endValue[i]
      array[i] = start + fraction * (end - start)
    }
    return array
  }
}
