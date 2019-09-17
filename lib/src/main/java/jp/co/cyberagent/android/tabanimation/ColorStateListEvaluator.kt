package jp.co.cyberagent.android.tabanimation

import android.animation.TypeEvaluator
import android.content.res.ColorStateList

internal object ColorStateListEvaluator :
  TypeEvaluator<ColorStateList> {
  override fun evaluate(
    fraction: Float,
    startValue: ColorStateList,
    endValue: ColorStateList
  ): ColorStateList {
    val color = ArgbEvaluator.evaluate(
      fraction,
      startValue.defaultColor,
      endValue.defaultColor
    )
    return ColorStateList.valueOf(color)
  }
}
