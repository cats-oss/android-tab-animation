package jp.co.cyberagent.android.tabanimation

import android.database.DataSetObserver
import android.view.View
import androidx.annotation.LayoutRes
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import java.lang.ref.WeakReference

class AnimationTabManager internal constructor(
  private val tabViewIdAnimationInfoSet: ViewIdAnimationInfoSet,
  @LayoutRes private val layoutRes: Int,
  private val binder: (TabLayout.Tab, view: View, position: Int) -> Unit = { _, _, _ -> Unit }
) {

  private var tabLayout: WeakReference<TabLayout>? = null
  private var viewPager: WeakReference<ViewPager>? = null

  private var tabViewAnimationInfoSet = emptyList<ViewAnimationInfoSet>()

  private val adapterDataObserver =
    ViewPagerAdapterDataSetObserver(this)
  private val adapterChangeListenerForSetup =
    ViewPagerOnAdapterChangeSetupListener(this)
  private val adapterChangeListenerForDataObserver =
    ViewPagerOnAdapterChangeForDataObserverListener(this)
  private val pageChangeListener =
    ViewPagerOnPageChangeListener(this)
  private val tabSelectedListener =
    OnTabSelectedListener(this)

  fun attach(tabLayout: TabLayout, viewPager: ViewPager) {
    detach()

    this.tabLayout = WeakReference(tabLayout)
    this.viewPager = WeakReference(viewPager)
    viewPager.run {
      addOnAdapterChangeListener(adapterChangeListenerForDataObserver)
      adapter?.registerDataSetObserver(adapterDataObserver)
    }
    tabLayout.run {
      setupWithViewPager(viewPager)
      addOnTabSelectedListener(tabSelectedListener)
    }
    viewPager.run {
      addOnAdapterChangeListener(adapterChangeListenerForSetup)
      addOnPageChangeListener(pageChangeListener)
    }
    setUpTabs()
  }

  fun detach() {
    tabLayout?.get()?.run {
      removeOnTabSelectedListener(tabSelectedListener)
      setupWithViewPager(null)
    }
    viewPager?.get()?.run {
      removeOnAdapterChangeListener(adapterChangeListenerForSetup)
      removeOnAdapterChangeListener(adapterChangeListenerForDataObserver)
      removeOnPageChangeListener(pageChangeListener)
      adapter?.unregisterDataSetObserver(adapterDataObserver)
    }
    tabLayout?.clear()
    tabLayout = null
    viewPager?.clear()
    viewPager = null
    tabViewAnimationInfoSet = emptyList()
  }

  private fun setUpTabs() {
    val tabLayout = tabLayout?.get() ?: return
    tabLayout.setUpCustomView(layoutRes)
    val selectedPosition = tabLayout.selectedTabPosition
    tabViewAnimationInfoSet = tabLayout.customTabViews.mapIndexed { pos, (t, v) ->
      val tab = requireNotNull(t)
      val view = requireNotNull(v)
      binder(tab, view, pos)
      tabViewIdAnimationInfoSet.bindTo(view)
    }
    repeat(tabViewAnimationInfoSet.size) { position ->
      val fraction = if (position == selectedPosition) 1f else 0f
      updateProperty(position, fraction)
    }
  }

  private fun updateProperty(position: Int, fraction: Float) {
    tabViewAnimationInfoSet.getOrNull(position)?.updateProperty(fraction)
  }

  private fun animateProperty(position: Int, selected: Boolean) {
    tabViewAnimationInfoSet.getOrNull(position)?.startAnimation(selected)
  }

  private class ViewPagerAdapterDataSetObserver(
    private val manager: AnimationTabManager
  ) : DataSetObserver() {

    override fun onChanged() {
      manager.setUpTabs()
    }

    override fun onInvalidated() {
      manager.setUpTabs()
    }
  }

  /**
   * This listener should be added after setupWithViewPager
   * We need tabs that populated from ViewPager
   */
  private class ViewPagerOnAdapterChangeSetupListener(
    private val manager: AnimationTabManager
  ) : ViewPager.OnAdapterChangeListener {

    override fun onAdapterChanged(
      viewPager: ViewPager,
      oldAdapter: PagerAdapter?,
      newAdapter: PagerAdapter?
    ) {
      manager.setUpTabs()
    }
  }

  /**
   * This listener should be added before setupWithViewPager
   * Because dataSetObserver runs in reverse order while other listeners run in normal order
   */
  private class ViewPagerOnAdapterChangeForDataObserverListener(
    private val manager: AnimationTabManager
  ) : ViewPager.OnAdapterChangeListener {
    override fun onAdapterChanged(
      viewPager: ViewPager,
      oldAdapter: PagerAdapter?,
      newAdapter: PagerAdapter?
    ) {
      oldAdapter?.unregisterDataSetObserver(manager.adapterDataObserver)
      newAdapter?.registerDataSetObserver(manager.adapterDataObserver)
    }
  }

  private class ViewPagerOnPageChangeListener(
    private val manager: AnimationTabManager
  ) : ViewPager.SimpleOnPageChangeListener() {

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
    private val manager: AnimationTabManager
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
