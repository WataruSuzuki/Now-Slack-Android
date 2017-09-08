/*
 * Copyright (C) The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package jp.co.devjchankchan.now_slack_android.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.Region;

import java.util.Collection;

import jp.co.devjchankchan.now_slack_android.R;
import jp.co.devjchankchan.now_slack_android.barcodereader.BarcodeCaptureActivity;
import jp.co.devjchankchan.slackapilibrary.LeaveSeatMonitoringListener;
import jp.co.devjchankchan.slackapilibrary.PhysicalBotService;

public class BeaconManagerFragment extends Fragment implements View.OnClickListener {

    private LeaveSeatMonitoringListener beaconListener;
    // use a compound button so either checkbox or switch widgets work.
    private CompoundButton autoFocus;
    private CompoundButton useFlash;
    private TextView statusMessage;
    private TextView barcodeValue;

    private static final int RC_BARCODE_CAPTURE = 9001;
    private static final String TAG = "BeaconManagerFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_beacon_manager, container, false);

        statusMessage = (TextView)rootView.findViewById(R.id.status_message);
        barcodeValue = (TextView)rootView.findViewById(R.id.barcode_value);

        autoFocus = (CompoundButton) rootView.findViewById(R.id.auto_focus);
        useFlash = (CompoundButton) rootView.findViewById(R.id.use_flash);

        rootView.findViewById(R.id.read_barcode).setOnClickListener(this);
        setupBeaconListener();

        return rootView;
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.read_barcode) {
            // launch barcode activity.
            Intent intent = new Intent(getActivity(), BarcodeCaptureActivity.class);
            intent.putExtra(BarcodeCaptureActivity.AutoFocus, autoFocus.isChecked());
            intent.putExtra(BarcodeCaptureActivity.UseFlash, useFlash.isChecked());

            startActivityForResult(intent, RC_BARCODE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_BARCODE_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                    statusMessage.setText(R.string.barcode_success);
                    barcodeValue.setText(barcode.displayValue);
                    Log.d(TAG, "Barcode read: " + barcode.displayValue);
                } else {
                    statusMessage.setText(R.string.barcode_failure);
                    Log.d(TAG, "No barcode captured, intent data is null");
                }
            } else {
                statusMessage.setText(String.format(getString(R.string.barcode_error),
                        CommonStatusCodes.getStatusCodeString(resultCode)));
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void setupBeaconListener() {
        final Activity activity = getActivity();
        PhysicalBotService service = PhysicalBotService.Companion.getSharedInstance();
        if (service != null) {
            beaconListener = new LeaveSeatMonitoringListener() {
                @Override
                public void onUpdateRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                    for (Beacon beacon: beacons) {
                        final String result = "Distance:" + beacon.getDistance();
                        Log.d(TAG, result);
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                statusMessage.setText(result);
                            }
                        });
                    }
                }
            };
            service.setMyLeaveSeatMonitoringListener(beaconListener);
        } else {
            Toast.makeText(activity, "Beacon service is not start yet...", Toast.LENGTH_LONG).show();
        }
    }
}