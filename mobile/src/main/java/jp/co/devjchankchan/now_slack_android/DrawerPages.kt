package jp.co.devjchankchan.now_slack_android

import android.app.Fragment
import jp.co.devjchankchan.now_slack_android.fragments.SlackManagerFragment
import jp.co.devjchankchan.now_slack_android.fragments.BeaconManagerFragment
import jp.co.devjchankchan.now_slack_android.fragments.SummaryFragment

enum class DrawerPages(val menuId: Int) {
    ACCOUNT_MANAGER(R.id.nav_account) {
        override fun createFragment(): Fragment = SlackManagerFragment.newInstance(1)
    },
    BEACON_MANAGER(R.id.nav_beacon) {
        override fun createFragment(): Fragment = BeaconManagerFragment()
    },
    SUMMARY(R.id.nav_summary) {
        override fun createFragment(): Fragment = SummaryFragment()
    };

    abstract fun createFragment(): Fragment
}