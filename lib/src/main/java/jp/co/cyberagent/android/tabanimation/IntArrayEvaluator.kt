package jp.co.cyberagent.android.tabanimation

import android.animation.TypeEvaluator

internal object IntArrayEvaluator : TypeEvaluator<IntArray> {
  override fun evaluate(fraction: Float, startValue: IntArray, endValue: IntArray): IntArray {
    val array = IntArray(startValue.size)
    for (i in array.indices) {
      val start = startValue[i]
      val end = endValue[i]
      array[i] = (start + fraction * (end - start)).toInt()
    }
    return array
  }
}
