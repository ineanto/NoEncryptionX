package xyz.ineanto.noencryptionx.updater;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.json.JSONObject;
import xyz.ineanto.noencryptionx.NoEncryptionX;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class PluginUpdater {
    @Getter
    private final URL apiUrl = URI.create("https://api.github.com/repos/ineanto/NoEncrypionX/releases/latest").toURL();
    @Getter
    private final URL updateUrl = URI.create("https://github.com/ineanto/NoEncrypionX/releases/latest").toURL();

    public PluginUpdater() throws MalformedURLException { }

    public void findUpdates(Runnable callbackIfOlder, Runnable callbackIfNewer, Runnable callbackIfError) {
        Bukkit.getScheduler().runTaskAsynchronously(NoEncryptionX.getInstance(), () -> {
            try {
                final PluginVersion latest = getLatestVersion();
                final PluginVersion current = new PluginVersion().getCurrent();

                if (latest == null) {
                    callbackIfError.run();
                    return;
                }

                final int compare = current.compareTo(latest);

                Bukkit.getScheduler().runTask(NoEncryptionX.getInstance(), () -> {
                    if (compare == 0) {
                        NoEncryptionX.getInstance().getLogger().info("You are running the latest version (" + current + ").");
                    } else if (compare > 0) {
                        callbackIfNewer.run();
                    } else {
                        callbackIfOlder.run();
                    }
                });
            } catch (IOException e) {
                NoEncryptionX.getInstance().getLogger().severe("Could not check for updates: " + e.getMessage());
                callbackIfError.run();
            }
        });
    }

    private String readJson(URL url) throws IOException {
        final HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

        connection.setDoInput(true);
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:56.0) Gecko/20100101 Firefox/56.0");
        connection.setRequestMethod("GET");

        if (connection.getResponseCode() == 200) {
            final BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            final StringBuilder builder = new StringBuilder();
            String line;
            while ((line = input.readLine()) != null) {
                builder.append(line);
            }

            return builder.toString();
        }

        return null;
    }

    private PluginVersion getLatestVersion() throws IOException {
        final String json = readJson(apiUrl);

        if (json == null) {
            return null;
        }

        final JSONObject parser = new JSONObject();
        final String tagName = String.valueOf(parser.get("tag_name"));
        final String[] tagNameSplit = tagName.split("\\.");

        return new PluginVersion(
                Integer.parseInt(tagNameSplit[0]),
                Integer.parseInt(tagNameSplit[1]),
                Integer.parseInt(tagNameSplit[2])
        );
    }
}
