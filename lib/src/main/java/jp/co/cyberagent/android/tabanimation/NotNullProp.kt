package jp.co.cyberagent.android.tabanimation

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

internal class NotNullProp<T : Any>(
  private val message: String
) : ReadWriteProperty<Any?, T> {
  private var value: T? = null

  override fun getValue(thisRef: Any?, property: KProperty<*>): T {
    return value ?: throw IllegalStateException("Property ${property.name} $message")
  }

  override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
    this.value = value
  }
}
