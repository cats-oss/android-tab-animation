package jp.co.cyberagent.android.tabanimation

import android.util.SparseArray
import android.view.View
import androidx.annotation.LayoutRes
import androidx.core.util.set
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.lang.ref.WeakReference

class AnimationTabManager2 internal constructor(
  private val tabViewIdAnimationInfoSet: ViewIdAnimationInfoSet,
  @LayoutRes private val layoutRes: Int,
  binder: (TabLayout.Tab, view: View, position: Int) -> Unit = { _, _, _ -> Unit }
) {

  private var tabLayout: WeakReference<TabLayout>? = null
  private var viewPager: WeakReference<ViewPager2>? = null
  private var tabLayoutMediator: TabLayoutMediator? = null

  private var tabViewAnimationInfoSet = SparseArray<ViewAnimationInfoSet>()

  private val pageChangeListener =
    ViewPagerOnPageChangeListener(this)
  private val tabSelectedListener =
    OnTabSelectedListener(this)
  private val tabConfigStrategy =
    TabConfigStrategy(
      this,
      layoutRes,
      binder
    )

  fun attach(tabLayout: TabLayout, viewPager: ViewPager2) {
    detach()

    this.tabLayout = WeakReference(tabLayout)
    this.viewPager = WeakReference(viewPager)
    tabLayoutMediator = TabLayoutMediator(tabLayout, viewPager, tabConfigStrategy)
    tabLayoutMediator?.attach()
    viewPager.registerOnPageChangeCallback(pageChangeListener)
    tabLayout.addOnTabSelectedListener(tabSelectedListener)
  }

  fun detach() {
    tabLayout?.get()?.removeOnTabSelectedListener(tabSelectedListener)
    tabLayout?.clear()
    viewPager?.get()?.unregisterOnPageChangeCallback(pageChangeListener)
    viewPager?.clear()
    tabLayoutMediator?.detach()
    tabLayoutMediator = null
    tabViewAnimationInfoSet.clear()
  }

  private fun updateProperty(position: Int, fraction: Float) {
    tabViewAnimationInfoSet[position]?.updateProperty(fraction)
  }

  private fun animateProperty(position: Int, selected: Boolean) {
    tabViewAnimationInfoSet[position]?.startAnimation(selected)
  }

  private class TabConfigStrategy(
    private val manager: AnimationTabManager2,
    @LayoutRes private val layoutRes: Int,
    private val binder: (TabLayout.Tab, view: View, position: Int) -> Unit = { _, _, _ -> Unit }
  ) : TabLayoutMediator.TabConfigurationStrategy {

    override fun onConfigureTab(tab: TabLayout.Tab, position: Int) {
      tab.setCustomView(layoutRes)
      val customView = requireNotNull(tab.customView)
      binder(tab, customView, position)
      val animationInfo = manager.tabViewIdAnimationInfoSet.bindTo(customView)
      manager.tabViewAnimationInfoSet[position] = animationInfo
      manager.updateProperty(position, 0f)
    }
  }

  private class ViewPagerOnPageChangeListener(
    private val manager: AnimationTabManager2
  ) : ViewPager2.OnPageChangeCallback() {

    private var previousScrollState: Int = 0
    private var scrollState: Int = 0

    override fun onPageScrollStateChanged(state: Int) {
      previousScrollState = scrollState
      scrollState = state
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
      val update = scrollState != ViewPager.SCROLL_STATE_SETTLING ||
        previousScrollState == ViewPager.SCROLL_STATE_DRAGGING
      if (update) {
        manager.updateProperty(position, 1 - positionOffset)
        if (positionOffset == 0f) {
          manager.updateProperty(position - 1, positionOffset)
        } else {
          manager.updateProperty(position + 1, positionOffset)
        }
      }
    }
  }

  private class OnTabSelectedListener(
    private val manager: AnimationTabManager2
  ) : TabLayout.OnTabSelectedListener {

    override fun onTabSelected(tab: TabLayout.Tab?) {
      val position = tab?.position ?: return
      manager.animateProperty(position, true)
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
      val position = tab?.position ?: return
      manager.animateProperty(position, false)
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
      // no op
    }
  }
}
