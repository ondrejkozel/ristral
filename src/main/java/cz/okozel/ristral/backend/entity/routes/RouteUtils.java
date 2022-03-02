package cz.okozel.ristral.backend.entity.routes;

import cz.okozel.ristral.backend.entity.lines.LineRouteLinkData;

import java.time.Duration;

public class RouteUtils {

    public static Duration computeRouteDuration(Route<?, LineRouteLinkData> route) {
        Duration duration = Duration.ZERO;
        for (var link : route.links()) {
            duration = duration.plus(link.edge().getDuration());
        }
        return duration;
    }

    public static String durationToString(Duration duration) {
        StringBuilder stringBuilder = new StringBuilder();
        long s = duration.toSeconds();
        long h = s / 3600;
        if (h > 0) stringBuilder.append(h).append(" h");
        long min = (s % 3600) / 60;
        if (min > 0) stringBuilder.append(" ").append(min).append(" min");
        s = s % 60;
        if (s > 0) stringBuilder.append(" ").append(s).append(" s");
        return stringBuilder.toString().trim();
    }
}
