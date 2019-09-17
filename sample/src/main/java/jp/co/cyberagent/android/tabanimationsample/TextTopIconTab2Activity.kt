package jp.co.cyberagent.android.tabanimationsample

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import jp.co.cyberagent.android.tabanimation.SimpleTab
import jp.co.cyberagent.android.tabanimation.TAB_ROOT_ID
import jp.co.cyberagent.android.tabanimation.ViewIdAnimationInfoSet
import jp.co.cyberagent.android.tabanimation.property
import jp.co.cyberagent.android.tabanimation.setupAnimationTabWithViewPager
import jp.co.cyberagent.android.tabanimation.viewIdAnimationInfo
import kotlinx.android.synthetic.main.activity_tab_view_pager2.*
import kotlinx.android.synthetic.main.include_tab_view_pager2.*

class TextTopIconTab2Activity : AppCompatActivity() {

  companion object {
    private const val TAB_SELECTED_SCALE = 1f
    private const val TAB_UNSELECTED_SCALE = 0.9f
  }

  private val animationInfo: ViewIdAnimationInfoSet =
    viewIdAnimationInfo {
      animate<View, Float>(TAB_ROOT_ID) {
        property(View.SCALE_X)
        startValue(TAB_UNSELECTED_SCALE)
        endValue(TAB_SELECTED_SCALE)
      }
      animate<View, Float>(TAB_ROOT_ID) {
        property(View.SCALE_Y)
        startValue(TAB_UNSELECTED_SCALE)
        endValue(TAB_SELECTED_SCALE)
      }
      animateText<Int> {
        property(
          getter = { currentTextColor },
          setter = { setTextColor(it) }
        )
        startValue(Color.GRAY)
        endValue { ContextCompat.getColor(it.context, R.color.colorAccent) }
      }
      animateIcon<ColorStateList> {
        property(
          getter = { ImageViewCompat.getImageTintList(this)!! },
          setter = { ImageViewCompat.setImageTintList(this, it) }
        )
        startValue(ColorStateList.valueOf(Color.GRAY))
        endValue {
          ColorStateList.valueOf(
            ContextCompat.getColor(
              it.context,
              R.color.colorAccent
            )
          )
        }
      }
    }

  private val tabType: SimpleTab = SimpleTab.TEXT_ICON

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_tab_view_pager2)
    setSupportActionBar(toolbar)
    setUpTabViewPager()
  }

  private fun setUpTabViewPager() {
    val adapter = Adapter(this)
    view_pager.adapter = adapter
    tab_layout.setupAnimationTabWithViewPager(
      view_pager,
      animationInfo,
      tabType
    ) { tab, _, pos ->
      if (tabType == SimpleTab.ICON || tabType == SimpleTab.TEXT_ICON) {
        tab.setIcon(pagerSet[pos].icon)
      }
      if (tabType == SimpleTab.TEXT_ICON || tabType == SimpleTab.TEXT) {
        tab.text = pagerSet[pos].text
      }
    }
  }

  inner class Adapter(
    fragmentActivity: FragmentActivity
  ) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
      return pagerSet.size
    }

    override fun createFragment(position: Int): Fragment {
      return PagerFragment.newInstance(pagerSet[position].text)
    }
  }
}
