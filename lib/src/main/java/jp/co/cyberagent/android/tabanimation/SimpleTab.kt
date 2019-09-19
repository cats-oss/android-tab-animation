package jp.co.cyberagent.android.tabanimation

import android.view.View
import android.widget.ImageView
import android.widget.TextView

enum class SimpleTab {
  TEXT,
  ICON,
  TEXT_ICON;

  companion object {

    fun icon(root: View): ImageView? = root.findViewById(android.R.id.icon)

    fun text(root: View): TextView? = root.findViewById(android.R.id.text1)
  }
}
