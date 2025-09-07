package xyz.ineanto.noencryptionx.commands;

import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class TabUtils {
    public static List<String> match(String input, List<String> possibleMatches) {
        List<String> matches = new ArrayList<>();

        StringUtil.copyPartialMatches(input, possibleMatches, matches);
        return matches;
    }
}
