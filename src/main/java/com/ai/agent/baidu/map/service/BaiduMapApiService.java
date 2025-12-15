package com.ai.agent.baidu.map.service;

import com.ai.agent.baidu.map.config.BaiduMapConfig;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * 百度地图API服务
 * 直接调用百度地图API接口
 */
@Service
public class BaiduMapApiService {

    private final WebClient webClient;
    private final BaiduMapConfig config;

    public BaiduMapApiService(BaiduMapConfig config) {
        this.config = config;
        this.webClient = WebClient.builder()
                .baseUrl(config.getBaseUrl())
                .build();
    }

    /**
     * 地理编码：将地址转换为经纬度坐标
     *
     * @param address 地址
     * @param city 城市（可选）
     * @return API响应
     */
    public Mono<Map<String, Object>> geocode(String address, String city) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(config.getBaseUrl() + "/geocoding/v3/")
                .queryParam("address", address)
                .queryParam("output", "json")
                .queryParam("ak", config.getApiKey());

        if (city != null && !city.isEmpty()) {
            uriBuilder.queryParam("city", city);
        }

        URI uri = uriBuilder.build().encode().toUri();

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(Map.class);
    }

    /**
     * 逆地理编码：将经纬度坐标转换为地址
     *
     * @param lat 纬度
     * @param lng 经度
     * @return API响应
     */
    public Mono<Map<String, Object>> reverseGeocode(Double lat, Double lng) {
        String location = lat + "," + lng;
        
        URI uri = UriComponentsBuilder.fromHttpUrl(config.getBaseUrl() + "/reverse_geocoding/v3/")
                .queryParam("location", location)
                .queryParam("output", "json")
                .queryParam("ak", config.getApiKey())
                .build().encode().toUri();

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(Map.class);
    }

    /**
     * 获取路线规划信息
     *
     * @param origin 起点坐标，格式为 "纬度,经度"
     * @param destination 终点坐标，格式为 "纬度,经度"
     * @param mode 交通方式：driving(驾车)、walking(步行)、transit(公交)
     * @return API响应
     */
    public Mono<Map<String, Object>> getRoute(String origin, String destination, String mode) {
        String endpoint;
        switch (mode) {
            case "walking":
                endpoint = "/directionlite/v1/walking";
                break;
            case "transit":
                endpoint = "/directionlite/v1/transit";
                break;
            default:
                endpoint = "/directionlite/v1/driving";
        }

        URI uri = UriComponentsBuilder.fromHttpUrl(config.getBaseUrl() + endpoint)
                .queryParam("origin", origin)
                .queryParam("destination", destination)
                .queryParam("output", "json")
                .queryParam("ak", config.getApiKey())
                .build().encode().toUri();

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(Map.class);
    }

    /**
     * 搜索周边POI
     *
     * @param keyword 关键词
     * @param location 中心位置，格式为 "纬度,经度"
     * @param radius 搜索半径（米）
     * @return API响应
     */
    public Mono<Map<String, Object>> searchNearby(String keyword, String location, Integer radius) {
        URI uri = UriComponentsBuilder.fromHttpUrl(config.getBaseUrl() + "/place/v2/search")
                .queryParam("query", keyword)
                .queryParam("location", location)
                .queryParam("radius", radius)
                .queryParam("output", "json")
                .queryParam("ak", config.getApiKey())
                .queryParam("page_size", 10)
                .build().encode().toUri();

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(Map.class);
    }

    /**
     * 搜索城市内的POI
     *
     * @param keyword 关键词
     * @param city 城市名称
     * @return API响应
     */
    public Mono<Map<String, Object>> searchInCity(String keyword, String city) {
        URI uri = UriComponentsBuilder.fromHttpUrl(config.getBaseUrl() + "/place/v2/search")
                .queryParam("query", keyword)
                .queryParam("region", city)
                .queryParam("output", "json")
                .queryParam("ak", config.getApiKey())
                .queryParam("page_size", 10)
                .build().encode().toUri();

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(Map.class);
    }
    
    /**
     * 获取天气信息
     *
     * @param location 位置坐标，格式为 "纬度,经度"
     * @return API响应
     */
    public Mono<Map<String, Object>> getWeather(String location) {
        // 百度地图API没有直接的天气接口，这里使用高德地图的天气接口作为示例
        // 实际应用中可能需要集成第三方天气API
        Map<String, Object> mockResult = new HashMap<>();
        mockResult.put("status", "0");
        mockResult.put("message", "百度地图天气服务暂未实现，需要集成第三方天气API");
        mockResult.put("result", new HashMap<>());
        return Mono.just(mockResult);
    }
}