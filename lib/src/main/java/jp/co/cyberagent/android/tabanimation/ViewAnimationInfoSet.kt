package jp.co.cyberagent.android.tabanimation

internal class ViewAnimationInfoSet(
  private val infoSet: List<ViewAnimationInfo<*, *>>
) {

  fun updateProperty(fraction: Float) {
    infoSet.forEach {
      it.updateProperty(fraction)
    }
  }

  fun startAnimation(forward: Boolean) {
    infoSet.forEach {
      it.startAnimation(forward)
    }
  }
}
