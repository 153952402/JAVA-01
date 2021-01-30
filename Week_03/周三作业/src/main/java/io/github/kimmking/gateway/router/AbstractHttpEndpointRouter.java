package io.github.kimmking.gateway.router;

import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractHttpEndpointRouter implements HttpEndpointRouter {
    protected List<BackendServer> serverList;

    AbstractHttpEndpointRouter(List<String> endpoints) {
        serverList = init(endpoints);
    }

    private List<BackendServer> init(List<String> endpoints) {
        return endpoints.stream().map(url -> {
            return url.split(";");
        }).map(serverInfo -> {
            String url = serverInfo[0];
            String weight = "1";
            if(serverInfo.length > 1) {
                weight = serverInfo[1];
            }
            return new BackendServer(url, Integer.valueOf(weight));
        }).collect(Collectors.toList());
    };
}
