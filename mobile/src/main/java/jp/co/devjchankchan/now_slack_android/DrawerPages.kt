package jp.co.devjchankchan.now_slack_android

import android.app.Fragment

enum class DrawerPages(val menuId: Int) {
    BEACON_MANAGER(R.id.nav_camera) {
        override fun createFragment(): Fragment {
            return BeaconManagerFragment()
        }
    };

    abstract fun createFragment(): Fragment
}