package com.ai.agent.controller;

import com.ai.agent.mcp.McpServer;
import com.ai.agent.mcp.model.McpRequest;
import com.ai.agent.mcp.model.McpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/baidu-maps")
public class McpBaiduMapsController {
    
    @Autowired
    private McpServer sseMcpServer;
    
    // Initialize MCP handlers for Baidu Maps service
    @javax.annotation.PostConstruct
    public void initBaiduMapsMcpHandlers() {
        // Handler for geocoding requests
        sseMcpServer.registerHandler("baidu/maps/geocode", request -> {
            Map<String, Object> params = (Map<String, Object>) request.getParams();
            String address = (String) params.get("address");
            return geocodeAddress(address);
        });
        
        // Handler for reverse geocoding requests
        sseMcpServer.registerHandler("baidu/maps/reverse-geocode", request -> {
            Map<String, Object> params = (Map<String, Object>) request.getParams();
            Double lat = ((Number) params.get("latitude")).doubleValue();
            Double lng = ((Number) params.get("longitude")).doubleValue();
            return reverseGeocode(lat, lng);
        });
        
        // Handler for place search
        sseMcpServer.registerHandler("baidu/maps/search-place", request -> {
            Map<String, Object> params = (Map<String, Object>) request.getParams();
            String query = (String) params.get("query");
            String city = (String) params.get("city");
            return searchPlace(query, city);
        });
    }
    
    @PostMapping("/geocode")
    public Mono<Map<String, Object>> geocode(@RequestBody Map<String, String> request) {
        String address = request.get("address");
        return Mono.just(geocodeAddress(address));
    }
    
    @PostMapping("/reverse-geocode")
    public Mono<Map<String, Object>> reverseGeocode(@RequestBody Map<String, Object> request) {
        Double lat = Double.valueOf(request.get("latitude").toString());
        Double lng = Double.valueOf(request.get("longitude").toString());
        return Mono.just(reverseGeocode(lat, lng));
    }
    
    @PostMapping("/search")
    public Mono<Map<String, Object>> searchPlace(@RequestBody Map<String, String> request) {
        String query = request.get("query");
        String city = request.get("city");
        return Mono.just(searchPlace(query, city));
    }
    
    // Mock implementations of Baidu Maps API calls
    private Map<String, Object> geocodeAddress(String address) {
        Map<String, Object> result = new HashMap<>();
        result.put("status", "success");
        result.put("address", address);
        result.put("location", Map.of(
            "lat", 39.9042,
            "lng", 116.4074
        ));
        result.put("formatted_address", "北京市东城区天安门广场");
        return result;
    }
    
    private Map<String, Object> reverseGeocode(Double lat, Double lng) {
        Map<String, Object> result = new HashMap<>();
        result.put("status", "success");
        result.put("latitude", lat);
        result.put("longitude", lng);
        result.put("address", "北京市东城区天安门广场");
        result.put("formatted_address", "北京市东城区天安门广场");
        return result;
    }
    
    private Map<String, Object> searchPlace(String query, String city) {
        Map<String, Object> result = new HashMap<>();
        result.put("status", "success");
        result.put("query", query);
        result.put("city", city);
        result.put("places", new Object[] {
            Map.of(
                "name", "天安门广场",
                "address", "北京市东城区天安门广场",
                "location", Map.of("lat", 39.9042, "lng", 116.4074)
            ),
            Map.of(
                "name", "故宫博物院",
                "address", "北京市东城区景山前街4号",
                "location", Map.of("lat", 39.9163, "lng", 116.3972)
            )
        });
        return result;
    }
}