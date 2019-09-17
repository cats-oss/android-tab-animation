package jp.co.cyberagent.android.tabanimationsample

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_pager.*

class PagerFragment : Fragment(R.layout.fragment_pager) {

  companion object {
    private const val KEY_TEXT = "text"

    fun newInstance(text: String) = PagerFragment().apply {
      arguments = bundleOf(KEY_TEXT to text)
    }
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    text_view.text = arguments?.getString(KEY_TEXT)
  }
}
