package com.unfamoussoul.test.web;

import com.unfamoussoul.sapi.api.web.WebListener;
import com.unfamoussoul.test.Test;
import com.unfamoussoul.test.web.route.RemindersRoute;
import com.unfamoussoul.test.web.route.ServerRoute;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

import java.util.logging.Level;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;

public class Web extends WebListener {

    private final Test module;
    private final ServerRoute serverRoute;
    private final RemindersRoute remindersRoute;

    public Web(Test module) {
        this.module = module;
        this.serverRoute = new ServerRoute(module);
        this.remindersRoute = new RemindersRoute(module);
        createInstance();
    }

    private void createInstance() {
        QueuedThreadPool threadPool = getThreadPool();

        threadPool.setMinThreads(module.getConfig().webThreadsMin);
        threadPool.setMaxThreads(module.getConfig().webThreadsMax);
        threadPool.setReservedThreads(module.getConfig().webThreadsQueued);
        threadPool.setIdleTimeout(module.getConfig().webThreadsIdle);
        threadPool.setMaxEvictCount(module.getConfig().webThreadsEvict);
        threadPool.setDetailedDump(true);

        create(config -> {
            config.jetty.modifyServer(server -> server.setHandler(new Server(threadPool)));
            config.requestLogger.http((ctx, ms) -> module.getLogger().log(
                    Level.CONFIG, "[TYPE: {}], [CONTEXT: {}], [IP: {}], took {}ms", new Object[]{ctx.method(), ctx.path(), ctx.ip(), String.format("%.2f", ms / 1000)}
            ));
        });

        getApp().unsafe.routes.apiBuilder(() -> {
            path("/server", () -> get("online", serverRoute::getOnline));
            path("/reminders", () -> {
                get("", remindersRoute::getAll);
                get("{nickname}", remindersRoute::getByNickname);
            });
        });
    }
}
