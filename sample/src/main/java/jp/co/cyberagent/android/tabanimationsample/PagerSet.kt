package jp.co.cyberagent.android.tabanimationsample

data class Pager(val icon: Int, val text: String)

val pagerSet = listOf(
  Pager(
    R.drawable.ic_home_black_24dp,
    "Page1"
  ),
  Pager(
    R.drawable.ic_favorite_black_24dp,
    "Page2"
  ),
  Pager(
    R.drawable.ic_local_offer_black_24dp,
    "Pager3"
  ),
  Pager(
    R.drawable.ic_access_time_black_24dp,
    "Page4"
  )
)
