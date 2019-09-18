package jp.co.cyberagent.android.tabanimation

import android.animation.TypeEvaluator
import android.util.Property
import android.view.View
import androidx.annotation.FloatRange

@AnimationDsl
class AnimationInfoBuilder<T : View, V> internal constructor() {

  private var viewProperty by notNull<Property<in T, V>>()
  private var startViewValue by notNull<ViewValue<V>>()
  private var endViewValue by notNull<ViewValue<V>>()
  private var keyFrames: MutableList<Pair<Float, ViewValue<V>>> = mutableListOf()

  private var _evaluator: TypeEvaluator<V>? = null
  private val evaluator: TypeEvaluator<V>
    get() {
      return _evaluator
        ?: run {
          val propertyTypeName = viewProperty.type.canonicalName ?: ""
          @Suppress("UNCHECKED_CAST")
          commonEvaluator[propertyTypeName] as? TypeEvaluator<V>
        } ?: throw IllegalStateException(
          """
          Property evaluator has not been set on this builder. Implicit support types: $commonEvaluatorTypeNames.
          """.trimIndent()
        )
    }

  fun property(property: Property<in T, V>) {
    this.viewProperty = property
  }

  fun evaluator(evaluator: TypeEvaluator<V>) {
    this._evaluator = evaluator
  }

  fun startValue(value: V) {
    startValue { value }
  }

  fun startValue(value: (root: View) -> V) {
    startViewValue = value
  }

  fun endValue(value: V) {
    endValue { value }
  }

  fun endValue(value: (root: View) -> V) {
    endViewValue = value
  }

  fun keyFrame(@FloatRange(from = 0.0, to = 1.0) fraction: Float, value: V) {
    keyFrame(fraction) { value }
  }

  fun keyFrame(@FloatRange(from = 0.0, to = 1.0) fraction: Float, value: (root: View) -> V) {
    keyFrames.add(fraction to value)
  }

  internal fun build(): AnimationInfo<T, V> {
    return AnimationInfo(
      viewProperty,
      evaluator,
      startViewValue,
      endViewValue,
      keyFrames
    )
  }

  private fun <T : Any> notNull() =
    NotNullProp<T>("has not been set on this builder.")
}

inline fun <reified T : View, reified V> AnimationInfoBuilder<T, V>.property(
  crossinline getter: T.() -> V,
  crossinline setter: T.(V) -> Unit
) {
  property(ViewProperty(getter, setter))
}

inline fun <reified T : View, reified V> AnimationInfoBuilder<T, V>.evaluator(
  noinline evaluate: (fraction: Float, startValue: V, endValue: V) -> V
) {
  evaluator(TypeEvaluator(evaluate))
}
