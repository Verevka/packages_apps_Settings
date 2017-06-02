/*
 * Copyright (C) 2017 The Android Open Source Project
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
 * limitations under the License
 */

package com.android.settings.password;

import static com.google.common.truth.Truth.assertThat;

import static org.robolectric.RuntimeEnvironment.application;

import android.content.Intent;

import com.android.settings.SettingsRobolectricTestRunner;
import com.android.settings.TestConfig;
import com.android.settings.password.ChooseLockPattern;
import com.android.settings.password.ChooseLockPattern.IntentBuilder;
import com.android.settings.password.ChooseLockSettingsHelper;
import com.android.settings.testutils.shadow.SettingsShadowResources;
import com.android.settings.testutils.shadow.ShadowDynamicIndexableContentMonitor;
import com.android.settings.testutils.shadow.ShadowEventLogWriter;
import com.android.settings.testutils.shadow.ShadowUtils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.annotation.Config;

@RunWith(SettingsRobolectricTestRunner.class)
@Config(
        manifest = TestConfig.MANIFEST_PATH,
        sdk = TestConfig.SDK_VERSION,
        shadows = {
                SettingsShadowResources.class,
                SettingsShadowResources.SettingsShadowTheme.class,
                ShadowDynamicIndexableContentMonitor.class,
                ShadowEventLogWriter.class,
                ShadowUtils.class
        })
public class ChooseLockPatternTest {

    @Test
    public void activityCreationTest() {
        // Basic sanity test for activity created without crashing
        Robolectric.buildActivity(ChooseLockPattern.class, new IntentBuilder(application).build())
                .setup().get();
    }

    @Test
    public void intentBuilder_setPattern_shouldAddExtras() {
        Intent intent = new IntentBuilder(application)
                .setPattern("pattern")
                .setUserId(123)
                .build();

        assertThat(intent
                .getBooleanExtra(ChooseLockSettingsHelper.EXTRA_KEY_HAS_CHALLENGE, true))
                .named("EXTRA_KEY_HAS_CHALLENGE")
                .isFalse();
        assertThat(intent
                .getStringExtra(ChooseLockSettingsHelper.EXTRA_KEY_PASSWORD))
                .named("EXTRA_KEY_PASSWORD")
                .isEqualTo("pattern");
        assertThat(intent.getIntExtra(Intent.EXTRA_USER_ID, 0))
                .named("EXTRA_USER_ID")
                .isEqualTo(123);
    }

    @Test
    public void intentBuilder_setChallenge_shouldAddExtras() {
        Intent intent = new IntentBuilder(application)
                .setChallenge(12345L)
                .setUserId(123)
                .build();

        assertThat(intent
                .getBooleanExtra(ChooseLockSettingsHelper.EXTRA_KEY_HAS_CHALLENGE, false))
                .named("EXTRA_KEY_HAS_CHALLENGE")
                .isTrue();
        assertThat(intent
                .getLongExtra(ChooseLockSettingsHelper.EXTRA_KEY_CHALLENGE, 0L))
                .named("EXTRA_KEY_CHALLENGE")
                .isEqualTo(12345L);
        assertThat(intent
                .getIntExtra(Intent.EXTRA_USER_ID, 0))
                .named("EXTRA_USER_ID")
                .isEqualTo(123);
    }
}