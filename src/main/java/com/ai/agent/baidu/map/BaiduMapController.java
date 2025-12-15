package com.ai.agent.baidu.map;

import com.ai.agent.mcp.server.model.MCPResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 * 百度地图MCP控制器
 * 提供REST API接口来调用百度地图相关功能
 */
@RestController
@RequestMapping("/api/baidu-map")
public class BaiduMapController {

    private final BaiduMapMCPClient baiduMapMCPClient;

    public BaiduMapController(BaiduMapMCPClient baiduMapMCPClient) {
        this.baiduMapMCPClient = baiduMapMCPClient;
    }

    /**
     * 地理编码：将地址转换为经纬度坐标
     */
    @GetMapping("/geocode")
    public Mono<ResponseEntity<Object>> geocode(
            @RequestParam String address,
            @RequestParam(required = false) String city) {
        
        return baiduMapMCPClient.geocode(address, city)
                .map(response -> ResponseEntity.ok((Object) response))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    /**
     * 逆地理编码：将经纬度坐标转换为地址
     */
    @GetMapping("/reverse-geocode")
    public Mono<ResponseEntity<Object>> reverseGeocode(
            @RequestParam Double lat,
            @RequestParam Double lng) {
        
        return baiduMapMCPClient.reverseGeocode(lat, lng)
                .map(response -> ResponseEntity.ok((Object) response))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    /**
     * 获取路线规划信息
     */
    @GetMapping("/route")
    public Mono<ResponseEntity<Object>> getRoute(
            @RequestParam String origin,
            @RequestParam String destination,
            @RequestParam(defaultValue = "driving") String mode) {
        
        return baiduMapMCPClient.getRoute(origin, destination, mode)
                .map(response -> ResponseEntity.ok((Object) response))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    /**
     * 搜索周边POI
     */
    @GetMapping("/search-nearby")
    public Mono<ResponseEntity<Object>> searchNearby(
            @RequestParam String keyword,
            @RequestParam String location,
            @RequestParam(defaultValue = "1000") Integer radius) {
        
        return baiduMapMCPClient.searchNearby(keyword, location, radius)
                .map(response -> ResponseEntity.ok((Object) response))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    /**
     * 搜索城市内的POI
     */
    @GetMapping("/search-city")
    public Mono<ResponseEntity<Object>> searchInCity(
            @RequestParam String keyword,
            @RequestParam String city) {
        
        return baiduMapMCPClient.searchInCity(keyword, city)
                .map(response -> ResponseEntity.ok((Object) response))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    /**
     * 获取天气信息
     */
    @GetMapping("/weather")
    public Mono<ResponseEntity<Object>> getWeather(
            @RequestParam String location) {
        
        return baiduMapMCPClient.getWeather(location)
                .map(response -> ResponseEntity.ok((Object) response))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    /**
     * 通用MCP调用接口
     */
    @PostMapping("/invoke")
    public Mono<ResponseEntity<Object>> invoke(
            @RequestBody Map<String, Object> requestBody) {
        
        String method = (String) requestBody.get("method");
        @SuppressWarnings("unchecked")
        Map<String, Object> params = (Map<String, Object>) requestBody.getOrDefault("params", new HashMap<>());
        
        return baiduMapMCPClient.invoke(method, params)
                .map(response -> ResponseEntity.ok((Object) response))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }
}