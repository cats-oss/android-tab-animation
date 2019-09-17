package jp.co.cyberagent.android.tabanimation

import android.view.View
import androidx.annotation.LayoutRes
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout

fun TabLayout.setupAnimationTabWithViewPager(
  viewPager: ViewPager,
  viewIdAnimationInfoSet: ViewIdAnimationInfoSet,
  simpleTab: SimpleTab,
  binder: (TabLayout.Tab, view: View, position: Int) -> Unit = { _, _, _ -> Unit }
): AnimationTabManager = setupAnimationTabWithViewPager(
  viewPager,
  viewIdAnimationInfoSet,
  mapSimpleTabLayoutRes(simpleTab),
  binder
)

fun TabLayout.setupAnimationTabWithViewPager(
  viewPager: ViewPager,
  viewIdAnimationInfoSet: ViewIdAnimationInfoSet,
  @LayoutRes layoutRes: Int,
  binder: (TabLayout.Tab, view: View, position: Int) -> Unit = { _, _, _ -> Unit }
): AnimationTabManager = AnimationTabManager(
  viewIdAnimationInfoSet,
  layoutRes,
  binder
).also {
  it.attach(this, viewPager)
}

fun TabLayout.setupAnimationTabWithViewPager(
  viewPager: ViewPager2,
  viewIdAnimationInfoSet: ViewIdAnimationInfoSet,
  simpleTab: SimpleTab,
  binder: (TabLayout.Tab, view: View, position: Int) -> Unit = { _, _, _ -> Unit }
): AnimationTabManager2 = setupAnimationTabWithViewPager(
  viewPager,
  viewIdAnimationInfoSet,
  mapSimpleTabLayoutRes(simpleTab),
  binder
)

fun TabLayout.setupAnimationTabWithViewPager(
  viewPager: ViewPager2,
  viewIdAnimationInfoSet: ViewIdAnimationInfoSet,
  @LayoutRes layoutRes: Int,
  binder: (TabLayout.Tab, view: View, position: Int) -> Unit = { _, _, _ -> Unit }
): AnimationTabManager2 = AnimationTabManager2(
  viewIdAnimationInfoSet,
  layoutRes,
  binder
).also {
  it.attach(this, viewPager)
}

internal fun TabLayout.setUpCustomView(@LayoutRes layoutRes: Int) {
  (0 until tabCount).forEach { position ->
    val tab = requireNotNull(getTabAt(position))
    tab.setCustomView(layoutRes)
  }
}

internal val TabLayout.customTabViews
  get() = (0 until tabCount).map { position ->
    val tab = getTabAt(position)
    tab to tab?.customView
  }

private fun TabLayout.mapSimpleTabLayoutRes(simpleTab: SimpleTab) = when (simpleTab) {
  SimpleTab.TEXT -> R.layout.layout_text_tab
  SimpleTab.ICON -> R.layout.layout_icon_tab
  SimpleTab.TEXT_ICON -> if (isInlineLabel) {
    R.layout.layout_text_leading_icon_tab
  } else {
    R.layout.layout_text_top_icon_tab
  }
}
