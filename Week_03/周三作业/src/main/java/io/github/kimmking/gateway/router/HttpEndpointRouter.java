package io.github.kimmking.gateway.router;

import java.util.List;
import java.util.stream.Collectors;

public interface HttpEndpointRouter {
    
    BackendServer route();

    // Load Balance
    // Random
    // RoundRibbon 
    // Weight
    // - server01,20
    // - server02,30
    // - server03,50
    
}
