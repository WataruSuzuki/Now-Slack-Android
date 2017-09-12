package jp.co.devjchankchan.now_slack_android.fragments.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    public static final List<SlackMenuItem> ITEMS = new ArrayList<SlackMenuItem>();

    public static final Map<String, SlackMenuItem> ITEM_MAP = new HashMap<String, SlackMenuItem>();

    private static final int COUNT = SlackMenus.values().length;;

    static {
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    private static void addItem(SlackMenuItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static SlackMenuItem createDummyItem(int position) {
        return new SlackMenuItem(String.valueOf(position), "Item " + position, makeDetails(position));
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    public static class SlackMenuItem {
        public final String id;
        public final String content;
        public final String details;

        public SlackMenuItem(String id, String content, String details) {
            this.id = id;
            this.content = content;
            this.details = details;
        }

        @Override
        public String toString() {
            return content;
        }
    }

    public enum SlackMenus {
        AUTH,
        EMOJI_LIST,
        SET_EMOJI,
        LOG_OUT
    }
}
