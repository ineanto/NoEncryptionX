package xyz.ineanto.noencryptionx.config;

import lombok.RequiredArgsConstructor;
import xyz.ineanto.noencryptionx.NoEncryptionX;

@RequiredArgsConstructor
public class Configuration {
    private final NoEncryptionX instance;

    private boolean loginProtectionMessageShown = false;
    private boolean bannerDisabled = false;
    private boolean doAutoUpdates = false;
    private boolean bStatsEnabled = false;
}