package com.unfamoussoul.test.web.route;

import com.unfamoussoul.test.Test;
import io.javalin.http.Context;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ServerRoute {

    private final Test module;

    public ServerRoute(Test module) {
        this.module = module;
    }

    public void getOnline(Context ctx) {
        List<String> names = new ArrayList<>();

        for (Player player : module.getPlugin().getServer().getOnlinePlayers()) {
            names.add(player.getName());
        }

        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("online", names.size());
        payload.put("max", module.getPlugin().getServer().getMaxPlayers());
        payload.put("names", names);

        ctx.json(payload);
    }
}
