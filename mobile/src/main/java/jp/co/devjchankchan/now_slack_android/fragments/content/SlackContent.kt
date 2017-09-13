package jp.co.devjchankchan.now_slack_android.fragments.content

import android.content.Context
import java.util.ArrayList
import java.util.HashMap

class SlackContent(context: Context) {

    private var myContext = context
    val ITEMS: MutableList<SlackMenuItem> = ArrayList()
    val ITEM_MAP: MutableMap<SlackMenus, SlackMenuItem> = HashMap()

    private val COUNT = SlackMenus.values().size

    init {
        for (i in 0 until COUNT) {
            addItem(createItem(i))
        }
    }

    private fun addItem(item: SlackMenuItem) {
        ITEMS.add(item)
        ITEM_MAP.put(item.id, item)
    }

    private fun createItem(position: Int): SlackMenuItem = SlackMenuItem(SlackMenus.values().get(position), makeDetails(position))

    private fun makeDetails(position: Int): String {
        val menu = SlackMenus.values().get(position)
        return myContext.getString(menu.stringId)
    }

    class SlackMenuItem(val id: SlackMenus, val content: String) {
        override fun toString(): String = content
    }

    enum class SlackMenus(val stringId: Int) {
        AUTH(jp.co.devjchankchan.physicalbotlibrary.R.string.authentication),
        EMOJI_LIST(jp.co.devjchankchan.physicalbotlibrary.R.string.emoji_list),
        SET_EMOJI(jp.co.devjchankchan.physicalbotlibrary.R.string.set_emoji),
        LOG_OUT(jp.co.devjchankchan.physicalbotlibrary.R.string.log_out)
    }
}
