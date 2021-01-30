package io.github.kimmking.gateway.router;

import java.util.List;
import java.util.Random;

public class RandomHttpEndpointRouter extends AbstractHttpEndpointRouter {

    public RandomHttpEndpointRouter(List<String> uris) {
       super(uris);
    }

    @Override
    public BackendServer route() {
        int size = serverList.size();
        Random random = new Random(System.currentTimeMillis());
        return serverList.get(random.nextInt(size));
    }
}
