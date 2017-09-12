package jp.co.devjchankchan.now_slack_android.fragments.dummy

import java.util.ArrayList
import java.util.HashMap

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 *
 *
 * TODO: Replace all uses of this class before publishing your app.
 */
object DummyContent {

    val ITEMS: MutableList<SlackMenuItem> = ArrayList()

    val ITEM_MAP: MutableMap<String, SlackMenuItem> = HashMap()

    private val COUNT = SlackMenus.values().size

    init {
        for (i in 1..COUNT) {
            addItem(createDummyItem(i))
        }
    }

    private fun addItem(item: SlackMenuItem) {
        ITEMS.add(item)
        ITEM_MAP.put(item.id, item)
    }

    private fun createDummyItem(position: Int): SlackMenuItem {
        return SlackMenuItem(position.toString(), "Item " + position, makeDetails(position))
    }

    private fun makeDetails(position: Int): String {
        val builder = StringBuilder()
        builder.append("Details about Item: ").append(position)
        for (i in 0 until position) {
            builder.append("\nMore details information here.")
        }
        return builder.toString()
    }

    class SlackMenuItem(val id: String, val content: String, val details: String) {

        override fun toString(): String {
            return content
        }
    }

    enum class SlackMenus {
        AUTH,
        EMOJI_LIST,
        SET_EMOJI,
        LOG_OUT
    }
}
