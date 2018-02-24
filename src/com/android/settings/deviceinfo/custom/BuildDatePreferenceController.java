/*
 * Copyright (C) 2018 Citrus-CAF Project
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
package com.android.settings.deviceinfo.custom;

import android.content.Context;
import android.os.SystemProperties;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceScreen;
import android.text.TextUtils;

import com.android.settings.R;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.core.AbstractPreferenceController;

public class BuildDatePreferenceController extends AbstractPreferenceController implements
        PreferenceControllerMixin {

    private static final String KEY_BUILD_DATE_PREF = "build_date";
    private static final String KEY_BUILD_DATE = "ro.build.date";

    public BuildDatePreferenceController(Context context) {
        super(context);
    }

    @Override
    public String getPreferenceKey() {
        return KEY_BUILD_DATE_PREF;
    }

    @Override
    public boolean isAvailable() {
        return !TextUtils.isEmpty(SystemProperties.get(KEY_BUILD_DATE));
    }

    @Override
    public void displayPreference(PreferenceScreen screen) {
        super.displayPreference(screen);
        final Preference pref = screen.findPreference(KEY_BUILD_DATE_PREF);
        if (pref != null) {
            final String summary = SystemProperties.get(KEY_BUILD_DATE,
                    mContext.getResources().getString(R.string.summary_placeholder));
            pref.setSummary(summary);
        }
    }
}