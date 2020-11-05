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
