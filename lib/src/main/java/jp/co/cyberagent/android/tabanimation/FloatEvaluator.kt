package jp.co.cyberagent.android.tabanimation

import android.animation.FloatEvaluator
import android.animation.TypeEvaluator

internal object FloatEvaluator : TypeEvaluator<Float> {

  private val evaluator = FloatEvaluator()

  override fun evaluate(fraction: Float, startValue: Float?, endValue: Float?): Float {
    return evaluator.evaluate(fraction, startValue, endValue)
  }
}
