package jp.co.devjchankchan.slackapilibrary;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.Region;

import java.util.Collection;

public interface LeaveSeatMonitoringListener {
    public void onUpdateRangeBeaconsInRegion(Collection<Beacon> beacons, Region region);
}
