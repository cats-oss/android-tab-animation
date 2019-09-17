package jp.co.cyberagent.android.tabanimation

import android.animation.TypeEvaluator
import android.content.res.ColorStateList
import android.graphics.PointF
import android.graphics.Rect

internal val commonEvaluator = mapOf<String, TypeEvaluator<*>>(
  Int::class.java.canonicalName to ArgbEvaluator,
  Int::class.javaObjectType.canonicalName to ArgbEvaluator,
  Float::class.java.canonicalName to FloatEvaluator,
  Float::class.javaObjectType.canonicalName to FloatEvaluator,
  IntArray::class.java.canonicalName to IntArrayEvaluator,
  IntArray::class.javaObjectType.canonicalName to IntArrayEvaluator,
  FloatArray::class.java.canonicalName to FloatArrayEvaluator,
  FloatArray::class.javaObjectType.canonicalName to FloatArrayEvaluator,
  PointF::class.java.canonicalName to PointFEvaluator,
  Rect::class.java.canonicalName to RectEvaluator,
  ColorStateList::class.java.canonicalName to ColorStateListEvaluator
)

internal val commonEvaluatorTypeNames = commonEvaluator.keys.joinToString {
  if (it == "int" || it == "java.lang.Integer") {
    "$it (ColorInt)"
  } else {
    it
  }
}
