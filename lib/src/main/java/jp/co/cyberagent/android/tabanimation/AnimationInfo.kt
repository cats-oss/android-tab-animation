package jp.co.cyberagent.android.tabanimation

import android.animation.TypeEvaluator
import android.util.Property
import android.view.View

internal class AnimationInfo<T : View, V>(
  val property: Property<in T, V>,
  val evaluator: TypeEvaluator<V>,
  val start: ViewValue<V>,
  val end: ViewValue<V>,
  val keyFrames: List<Pair<Float, ViewValue<V>>>
)
