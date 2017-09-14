package jp.co.devjchankchan.now_slack_android

import android.app.Fragment
import jp.co.devjchankchan.now_slack_android.fragments.BeaconManagerFragment
import jp.co.devjchankchan.now_slack_android.fragments.SummaryFragment

enum class DrawerPages(val menuId: Int) {
    BEACON_MANAGER(R.id.nav_camera) {
        override fun createFragment(): Fragment = BeaconManagerFragment()
    },
    SUMMARY(R.id.nav_gallery) {
        override fun createFragment(): Fragment = SummaryFragment()
    };

    abstract fun createFragment(): Fragment
}