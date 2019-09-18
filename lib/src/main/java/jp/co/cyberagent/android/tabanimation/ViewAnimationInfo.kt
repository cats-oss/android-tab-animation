package jp.co.cyberagent.android.tabanimation

import android.animation.Keyframe
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.TimeInterpolator
import android.os.Build
import android.view.View
import androidx.core.view.OneShotPreDrawListener
import androidx.core.view.ViewCompat
import androidx.core.view.doOnPreDraw
import java.lang.ref.WeakReference

internal class ViewAnimationInfo<V : View, T>(
  root: View,
  view: V,
  private val animationInfo: AnimationInfo<V, T>,
  private val duration: Long,
  private val interpolator: TimeInterpolator
) {

  private val root = WeakReference(root)
  private val view = WeakReference(view)
  private val reverseInterpolator =
    ReverseInterpolator(interpolator)
  private val animator by lazy(LazyThreadSafetyMode.NONE) {
    createAnimator()
  }
  private var preDrawListener: OneShotPreDrawListener? = null

  fun updateProperty(fraction: Float) {
    root.get()?.ensureLaidOut {
      if (animator.isRunning) animator.end()
      jumpTo(fraction)
    }
  }

  fun animate(forward: Boolean) {
    root.get()?.ensureLaidOut {
      if (forward) {
        animator.interpolator = interpolator
        animator.start()
      } else {
        animator.interpolator = reverseInterpolator
        animator.reverse()
      }
    }
  }

  private fun View.ensureLaidOut(action: () -> Unit) {
    preDrawListener?.removeListener()
    if (ViewCompat.isLaidOut(this) && !isLayoutRequested) {
      action()
    } else {
      preDrawListener = doOnPreDraw { action() }
    }
  }

  private fun jumpTo(fraction: Float) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
      animator.setCurrentFraction(fraction)
    } else {
      val time = animator.duration * fraction
      animator.currentPlayTime = time.toLong()
    }
  }

  private fun createAnimator(): ObjectAnimator = with(animationInfo) {
    val root = root.get() ?: return@with ObjectAnimator()
    val view = view.get() ?: return@with ObjectAnimator()
    val startFrame = Keyframe.ofObject(0f, start(root))
    val endFrame = Keyframe.ofObject(1f, end(root))
    val keyFrames = keyFrames.map { (fraction, viewValue) ->
      Keyframe.ofObject(fraction, viewValue(root))
    }
    val holder = PropertyValuesHolder.ofKeyframe(
      property,
      startFrame,
      *keyFrames.toTypedArray(),
      endFrame
    )
    val animator = ObjectAnimator.ofPropertyValuesHolder(view, holder)
    animator.setEvaluator(evaluator)
    animator.duration = duration
    return animator
  }
}
