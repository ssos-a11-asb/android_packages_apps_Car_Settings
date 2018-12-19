/*
 * Copyright (C) 2018 The Android Open Source Project
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

package com.android.car.settings.applications;

import static com.google.common.truth.Truth.assertThat;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertThrows;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import androidx.lifecycle.Lifecycle;
import androidx.preference.Preference;

import com.android.car.settings.CarSettingsRobolectricTestRunner;
import com.android.car.settings.common.PreferenceControllerTestHelper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RuntimeEnvironment;

@RunWith(CarSettingsRobolectricTestRunner.class)
public class ApplicationPreferenceControllerTest {
    private static final String PACKAGE_NAME = "Test Package Name";

    private Preference mPreference;
    private PreferenceControllerTestHelper<ApplicationPreferenceController>
            mPreferenceControllerHelper;
    private ApplicationPreferenceController mController;
    @Mock
    private ResolveInfo mResolveInfo;
    @Mock
    private PackageManager mPackageManager;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        Context context = spy(RuntimeEnvironment.application);
        when(context.getPackageManager()).thenReturn(mPackageManager);
        when(mResolveInfo.loadLabel(mPackageManager)).thenReturn(PACKAGE_NAME);

        mPreference = new Preference(context);
        mPreferenceControllerHelper = new PreferenceControllerTestHelper<>(context,
                ApplicationPreferenceController.class);
        mController = mPreferenceControllerHelper.getController();
    }

    @Test
    public void testCheckInitialized_noResolveInfo_throwException() {
        assertThrows(IllegalStateException.class,
                () -> mPreferenceControllerHelper.setPreference(mPreference));
    }

    @Test
    public void testRefreshUi_hasResolveInfo_setTitle() {
        mController.setResolveInfo(mResolveInfo);
        mPreferenceControllerHelper.setPreference(mPreference);
        mPreferenceControllerHelper.sendLifecycleEvent(Lifecycle.Event.ON_CREATE);
        mController.refreshUi();
        assertThat(mPreference.getTitle()).isEqualTo(PACKAGE_NAME);
    }
}
