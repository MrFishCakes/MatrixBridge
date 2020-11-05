/*
 * Copyright 2020 MrFishCakes
 * Copyright 2020 Other Contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.mrfishcakes.matrixbridge.version;

import com.google.common.collect.ImmutableSet;
import org.bukkit.Bukkit;

import java.util.Set;

public final class VersionChecker {

    private static final Set<String> INCOMP_VERSIONS = ImmutableSet.of("1_9_R1", "1_9_R2", "1_10_R1", "1_11_R1");
    private static final Set<String> COMP_VERSIONS = ImmutableSet.of("1_8_R1", "1_8_R2", "1_8_R3", "1_12_R1",
            "1_13_R1", "1_13_R2", "1_14_R1", "1_15_R1", "1_16_R1", "1_16_R2", "1_16_R3");

    private VersionChecker() {
        throw new UnsupportedOperationException(getClass().getName() + " cannot be initialised");
    }

    public static VersionType checkType() {
        String nms = Bukkit.getServer().getClass().getPackage().getName();
        nms = nms.substring(nms.lastIndexOf('.') + 1);

        if (INCOMP_VERSIONS.contains(nms)) return VersionType.INVALID;
        if (COMP_VERSIONS.contains(nms)) return VersionType.VALID;

        return VersionType.UNKOWN;
    }

}
