package jp.co.cyberagent.android.tabanimationsample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import jp.co.cyberagent.android.tabanimation.SimpleTab
import jp.co.cyberagent.android.tabanimation.ViewIdAnimationInfoSet
import jp.co.cyberagent.android.tabanimation.setupAnimationTabWithViewPager
import kotlinx.android.synthetic.main.activity_tab_view_pager.*
import kotlinx.android.synthetic.main.include_tab_view_pager.*

abstract class BaseActivity : AppCompatActivity() {

  abstract val animationInfo: ViewIdAnimationInfoSet

  abstract val tabType: SimpleTab

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_tab_view_pager)
    setSupportActionBar(toolbar)
    setUpTabViewPager()
  }

  protected open fun setUpTabViewPager() {
    val adapter = Adapter(supportFragmentManager)
    view_pager.adapter = adapter
    tab_layout.setupAnimationTabWithViewPager(
      view_pager,
      animationInfo,
      tabType
    ) { tab, _, pos ->
      if (tabType == SimpleTab.ICON || tabType == SimpleTab.TEXT_ICON) {
        tab.setIcon(pagerSet[pos].icon)
      }
    }
  }

  inner class Adapter(
    fragmentManager: FragmentManager
  ) : FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getPageTitle(position: Int): CharSequence? {
      return if (tabType == SimpleTab.ICON) {
        null
      } else {
        pagerSet[position].text
      }
    }

    override fun getItem(position: Int): Fragment {
      return PagerFragment.newInstance(pagerSet[position].text)
    }

    override fun getCount(): Int {
      return pagerSet.size
    }
  }
}
