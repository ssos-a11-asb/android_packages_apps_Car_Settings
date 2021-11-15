/*
 * Copyright (C) 2021 The Android Open Source Project
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
package com.android.car.settings.enterprise;

import android.car.drivingstate.CarUxRestrictions;
import android.content.Context;

import androidx.preference.Preference;

import com.android.car.settings.common.FragmentController;

import java.util.Date;

/**
* Controller to show whether the device owner took bugreports.
*/
public final class BugReportsPreferenceController
        extends BaseAdminActionReporterPreferenceController<Preference> {

    public BugReportsPreferenceController(Context context, String preferenceKey,
            FragmentController fragmentController, CarUxRestrictions uxRestrictions) {
        super(context, preferenceKey, fragmentController, uxRestrictions);
    }

    // TODO(b/206155845, b/186905050): add unit tests
    @Override
    protected Date getAdminActionTimestamp() {
        return mEnterprisePrivacyFeatureProvider.getLastBugReportRequestTime();
    }
}
