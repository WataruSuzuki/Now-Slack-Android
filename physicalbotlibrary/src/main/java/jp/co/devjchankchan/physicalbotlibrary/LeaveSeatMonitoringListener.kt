package jp.co.devjchankchan.physicalbotlibrary

import org.altbeacon.beacon.Beacon
import org.altbeacon.beacon.Region

interface LeaveSeatMonitoringListener {
    fun onUpdateRangeBeaconsInRegion(beacons: Collection<Beacon>, region: Region)
}
