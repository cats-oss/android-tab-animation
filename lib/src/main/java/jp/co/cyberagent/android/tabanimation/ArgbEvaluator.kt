package jp.co.cyberagent.android.tabanimation

import android.animation.ArgbEvaluator
import android.animation.TypeEvaluator

internal object ArgbEvaluator : TypeEvaluator<Int> {

  private val evaluator = ArgbEvaluator()

  override fun evaluate(fraction: Float, startValue: Int?, endValue: Int?): Int {
    return evaluator.evaluate(fraction, startValue, endValue) as Int
  }
}
