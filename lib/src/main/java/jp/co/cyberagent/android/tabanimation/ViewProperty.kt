package jp.co.cyberagent.android.tabanimation

import android.util.Property
import android.view.View

@Suppress("FunctionName")
inline fun <reified T : View, reified V> ViewProperty(
  crossinline getter: T.() -> V,
  crossinline setter: T.(V) -> Unit
): Property<T, V> {
  val name = T::class.java.simpleName + ":" + V::class.java.simpleName
  return object : Property<T, V>(V::class.java, name) {
    override fun get(view: T): V {
      return view.getter()
    }

    override fun set(view: T, value: V) {
      return view.setter(value)
    }
  }
}
