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

package com.android.car.settings.users;

import android.content.Context;
import android.content.Intent;
import android.content.pm.UserInfo;
import android.os.Bundle;

import com.android.car.settings.common.SettingsFragment;

/** Common logic shared for controlling the action bar which contains a button to delete a user. */
public abstract class UserDetailsBaseFragment extends SettingsFragment {
    private UserInfo mUserInfo;

    /** Adds user id to fragment arguments. */
    protected static UserDetailsBaseFragment addUserIdToFragmentArguments(
            UserDetailsBaseFragment fragment, int userId) {
        Bundle bundle = new Bundle();
        bundle.putInt(Intent.EXTRA_USER_ID, userId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        int userId = getArguments().getInt(Intent.EXTRA_USER_ID);
        mUserInfo = UserUtils.getUserInfo(getContext(), userId);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getToolbar().setTitle(getTitleText());
    }

    /** Make UserInfo available to subclasses. */
    protected UserInfo getUserInfo() {
        return mUserInfo;
    }

    /** Refresh UserInfo in case it becomes invalid. */
    protected void refreshUserInfo() {
        mUserInfo = UserUtils.getUserInfo(getContext(), mUserInfo.id);
    }

    /** Defines the text that should be shown in the action bar. */
    protected abstract String getTitleText();
}
