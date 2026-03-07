package com.unfamoussoul.test.web.route;

import com.unfamoussoul.test.Test;
import io.javalin.http.Context;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class RemindersRoute {

    private final Test module;

    public RemindersRoute(Test module) {
        this.module = module;
    }

    public void getAll(@NotNull Context ctx) {
        Map<String, List<String>> phrases = new LinkedHashMap<>();
        module.getPlayerPhrasesSnapshot().forEach((uuid, list) -> phrases.put(uuid.toString(), list));

        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("count", phrases.size());
        payload.put("data", phrases);

        ctx.json(payload);
    }

    public void getByNickname(@NotNull Context ctx) {
        String nickname = ctx.pathParam("nickname");
        UUID uuid = resolveUuid(nickname);
        List<String> phrases = module.getPhrases(uuid);

        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("nickname", nickname);
        payload.put("uuid", uuid.toString());
        payload.put("count", phrases.size());
        payload.put("data", phrases);

        ctx.json(payload);
    }

    private @NotNull UUID resolveUuid(String nickname) {
        OfflinePlayer player = Bukkit.getOfflinePlayer(nickname);
        return player.getUniqueId();
    }
}

