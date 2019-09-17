package jp.co.cyberagent.android.tabanimation

import android.view.View
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.TextView

@AnimationDsl
class ViewIdAnimationInfoSetBuilder internal constructor() {

  companion object {
    const val DEFAULT_DURATION_MILLIS = 300L
    val DEFAULT_INTERPOLATOR = LinearInterpolator()
  }

  private val infoSet = mutableListOf<ViewIdAnimationInfo<*, *>>()

  var duration: Long =
    DEFAULT_DURATION_MILLIS
  var interpolator: Interpolator =
    DEFAULT_INTERPOLATOR

  fun <T : View, V> animate(
    id: Int,
    builder: AnimationInfoBuilder<T, V>.() -> Unit
  ) {
    val info = AnimationInfoBuilder<T, V>().apply(builder).build()
    infoSet.add(ViewIdAnimationInfo(id, info))
  }

  fun <V> animateText(
    builder: AnimationInfoBuilder<TextView, V>.() -> Unit
  ) {
    animate(android.R.id.text1, builder)
  }

  fun <V> animateIcon(
    builder: AnimationInfoBuilder<ImageView, V>.() -> Unit
  ) {
    animate(android.R.id.icon, builder)
  }

  internal fun build(): ViewIdAnimationInfoSet {
    return ViewIdAnimationInfoSet(
      infoSet,
      duration,
      interpolator
    )
  }
}

fun viewIdAnimationInfo(
  builder: ViewIdAnimationInfoSetBuilder.() -> Unit
): ViewIdAnimationInfoSet {
  return ViewIdAnimationInfoSetBuilder().apply(builder).build()
}
