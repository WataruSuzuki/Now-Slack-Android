package jp.co.devjchankchan.slackapilibrary

import android.app.Notification
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.os.RemoteException
import android.support.annotation.RequiresApi
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.util.Log
import org.altbeacon.beacon.*
import org.altbeacon.beacon.MonitorNotifier
import org.altbeacon.beacon.Beacon
import org.altbeacon.beacon.RangeNotifier

class PhysicalBotService : Service(), BeaconConsumer {

    private val CHANNEL_ID = javaClass.toString()
    private val LOG_TAG = javaClass.simpleName
    private val KEY_BEACON_UUID = "beacon_uuid"

    /* NOTE: beacon layouts
        https://stackoverflow.com/questions/32513423/android-altbeacon-library-how-to-find-the-beacon-layout
    */
    private val iBeaconFormat = "m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"

    var iBeaconUUID : String// = "1E21BCE0-7655-4647-B492-A3F8DE2F9A02"
        get() {
            val data = applicationContext.getSharedPreferences(javaClass.simpleName, Context.MODE_PRIVATE)
            return data.getString(KEY_BEACON_UUID, "")
        }
        set(value) {
            val data = applicationContext.getSharedPreferences(javaClass.simpleName, Context.MODE_PRIVATE)
            val editor = data.edit()
            editor.putString(KEY_BEACON_UUID, value)
            editor.apply()
        }
    public var myLeaveSeatMonitoringListener: LeaveSeatMonitoringListener? = null
    public var distanceMeasurement = 2.0

    private lateinit var currentIdentifier: Identifier
    private lateinit var beaconManager: BeaconManager
    private var currentDistance = distanceMeasurement

    override fun onBind(intent: Intent): IBinder? {
        // TODO: Return the communication channel to the service.
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int = Service.START_REDELIVER_INTENT

    override fun onCreate() {
        super.onCreate()

        setupBeaconManager()
        sharedInstance = this
        notifyMessage(RegionNotification.START, NotificationId.REGION)
    }

    override fun onDestroy() {
        super.onDestroy()
        notifyMessage(RegionNotification.STOP, NotificationId.REGION)
        beaconManager.unbind(this)
        sharedInstance = null
    }

    private val leaveSeatMonitorNotifier = object : MonitorNotifier {

        override fun didEnterRegion(region: Region) {
            Log.d(LOG_TAG, "didEnterRegion")

            try {
                beaconManager.startRangingBeaconsInRegion(Region("unique-id-001", currentIdentifier, null, null))
                notifyMessage(RegionNotification.DID_ENTER_REGION, NotificationId.REGION)
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
        }

        override fun didExitRegion(region: Region) {
            Log.d(LOG_TAG, "didExitRegion")
            try {
                beaconManager.stopRangingBeaconsInRegion(Region("unique-id-001", currentIdentifier, null, null))
                notifyMessage(RegionNotification.DID_EXIT_REGION, NotificationId.REGION)
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
        }

        override fun didDetermineStateForRegion(i: Int, region: Region) {
            Log.d(LOG_TAG, "didDetermineStateForRegion")
            notifyMessage(RegionNotification.DID_DETERMINE_STATE_FOR_REGION, NotificationId.REGION)
        }
    }

    private val rangeNotifier = object : RangeNotifier {
        override fun didRangeBeaconsInRegion(beacons: Collection<Beacon>, region: Region) {
            myLeaveSeatMonitoringListener?.onUpdateRangeBeaconsInRegion(beacons, region)
            for (beacon in beacons) {
                Log.i(LOG_TAG, "UUID:" + beacon.id1 + ", major:" + beacon.id2 + ", minor:" + beacon.id3 + ", Distance:" + beacon.distance + ",RSSI" + beacon.rssi + ", TxPower" + beacon.txPower)
                if (iBeaconUUID.equals(beacon.id1.toString(), true)) {
                    if (currentDistance == distanceMeasurement) {
                        //Do nothing
                    } else if (currentDistance > beacon.distance
                            && beacon.distance > distanceMeasurement
                            && sittingNow) {
                        sittingNow = false
                        notifyMessage(RegionNotification.DID_LEAVE_SEAT, NotificationId.REGION)
                    } else if (currentDistance < beacon.distance
                            && beacon.distance < distanceMeasurement
                            && !sittingNow) {
                        sittingNow = true
                        notifyMessage(RegionNotification.DID_ARRIVE_SEAT, NotificationId.REGION)
                    }
                    currentDistance = beacon.distance
                }
            }
        }
    }

    override fun onBeaconServiceConnect() {
        beaconManager.addMonitorNotifier(leaveSeatMonitorNotifier)
        beaconManager.addRangeNotifier(rangeNotifier)
        beaconManager.startMonitoringBeaconsInRegion(Region("unique-id-001", currentIdentifier, null, null))
    }

    private fun setupBeaconManager() {
        currentIdentifier = Identifier.parse(iBeaconUUID)
        beaconManager = BeaconManager.getInstanceForApplication(applicationContext)
        beaconManager.beaconParsers.add(BeaconParser().setBeaconLayout(iBeaconFormat))
        beaconManager.bind(this);
    }

    private fun notifyMessage(msg: RegionNotification, id: NotificationId) {
        val manager = NotificationManagerCompat.from(applicationContext)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            manager.notify(id.ordinal, legacyNotificationBuilder(getString(msg.resourceId)).build())
        } else {
            manager.notify(id.ordinal, notificationBuilder(getString(msg.resourceId)).build())
        }
    }

    private fun legacyNotificationBuilder(title: String): NotificationCompat.Builder {
        val builder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.sym_def_app_icon)
                .setContentTitle(title)

        return builder
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun notificationBuilder(title: String): Notification.Builder {
        val builder = Notification.Builder(applicationContext, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.sym_def_app_icon)
                .setContentTitle(title)
        return builder
    }

    companion object {
        var sharedInstance: PhysicalBotService? = null
        var sittingNow = false
    }

    enum class RegionNotification(val resourceId: Int) {
        START(R.string.notify_start_service),
        DID_DETERMINE_STATE_FOR_REGION(R.string.notify_did_determine_state_region),
        DID_ENTER_REGION(R.string.notify_did_enter_region),
        DID_ARRIVE_SEAT(R.string.notify_arrive_seat),
        DID_LEAVE_SEAT(R.string.notify_leave_seat),
        DID_EXIT_REGION(R.string.notify_did_exit_region),
        STOP(R.string.notify_start_stop)
    }

    enum class NotificationId {
        REGION,
        OTHER
    }
}
