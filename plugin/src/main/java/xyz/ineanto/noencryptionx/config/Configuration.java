package xyz.ineanto.noencryptionx.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import xyz.ineanto.noencryptionx.NoEncryptionX;

@RequiredArgsConstructor
public class Configuration {
    private final NoEncryptionX instance;

    @Getter
    private boolean loginProtectionMessageShown = false;
    @Getter
    private boolean bannerDisabled = false;
    @Getter
    private boolean doAutoUpdates = false;
    @Getter
    private boolean bStatsEnabled = false;
}