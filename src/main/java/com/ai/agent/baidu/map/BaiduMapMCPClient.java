package com.ai.agent.baidu.map;

import com.ai.agent.mcp.server.model.MCPRequest;
import com.ai.agent.mcp.server.model.MCPResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 百度地图 MCP 客户端服务
 * 提供与百度地图API集成的功能，通过MCP协议进行调用
 */
@Service
public class BaiduMapMCPClient {

    private final WebClient webClient;

    public BaiduMapMCPClient(@Value("${mcp.server.url:http://localhost:8080/mcp/v1}") String mcpServerUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(mcpServerUrl)
                .build();
    }

    /**
     * 调用百度地图相关的方法
     *
     * @param method 方法名
     * @param params 参数
     * @return MCP响应
     */
    public Mono<MCPResponse> invoke(String method, Map<String, Object> params) {
        String requestId = UUID.randomUUID().toString();

        MCPRequest request = new MCPRequest();
        request.setId(requestId);
        request.setMethod(method);
        request.setParams(params);

        return webClient.post()
                .uri("/invoke")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(MCPResponse.class)
                .onErrorResume(WebClientResponseException.class, ex -> {
                    MCPResponse errorResponse = new MCPResponse();
                    MCPResponse.ErrorDetails errorDetails = new MCPResponse.ErrorDetails();
                    errorDetails.setCode(String.valueOf(ex.getStatusCode().value()));
                    errorDetails.setMessage(ex.getResponseBodyAsString());
                    errorResponse.setError(errorDetails);
                    errorResponse.setId(requestId);
                    return Mono.just(errorResponse);
                });
    }

    /**
     * 地理编码：将地址转换为经纬度坐标
     *
     * @param address 地址
     * @param city 城市（可选）
     * @return MCP响应
     */
    public Mono<MCPResponse> geocode(String address, String city) {
        Map<String, Object> params = new HashMap<>();
        params.put("address", address);
        if (city != null && !city.isEmpty()) {
            params.put("city", city);
        }

        return invoke("baidu/map/geocode", params);
    }

    /**
     * 逆地理编码：将经纬度坐标转换为地址
     *
     * @param lat 纬度
     * @param lng 经度
     * @return MCP响应
     */
    public Mono<MCPResponse> reverseGeocode(Double lat, Double lng) {
        Map<String, Object> params = new HashMap<>();
        params.put("lat", lat);
        params.put("lng", lng);

        return invoke("baidu/map/reverse_geocode", params);
    }

    /**
     * 获取路线规划信息
     *
     * @param origin 起点坐标，格式为 "纬度,经度"
     * @param destination 终点坐标，格式为 "纬度,经度"
     * @param mode 交通方式：driving(驾车)、walking(步行)、transit(公交)
     * @return MCP响应
     */
    public Mono<MCPResponse> getRoute(String origin, String destination, String mode) {
        Map<String, Object> params = new HashMap<>();
        params.put("origin", origin);
        params.put("destination", destination);
        params.put("mode", mode != null ? mode : "driving");

        return invoke("baidu/map/route", params);
    }

    /**
     * 搜索周边POI
     *
     * @param keyword 关键词
     * @param location 中心位置，格式为 "纬度,经度"
     * @param radius 搜索半径（米）
     * @return MCP响应
     */
    public Mono<MCPResponse> searchNearby(String keyword, String location, Integer radius) {
        Map<String, Object> params = new HashMap<>();
        params.put("keyword", keyword);
        params.put("location", location);
        params.put("radius", radius != null ? radius : 1000); // 默认1000米

        return invoke("baidu/map/search_nearby", params);
    }

    /**
     * 搜索城市内的POI
     *
     * @param keyword 关键词
     * @param city 城市名称
     * @return MCP响应
     */
    public Mono<MCPResponse> searchInCity(String keyword, String city) {
        Map<String, Object> params = new HashMap<>();
        params.put("keyword", keyword);
        params.put("city", city);

        return invoke("baidu/map/search_city", params);
    }

    /**
     * 获取天气信息
     *
     * @param location 位置坐标，格式为 "纬度,经度"
     * @return MCP响应
     */
    public Mono<MCPResponse> getWeather(String location) {
        Map<String, Object> params = new HashMap<>();
        params.put("location", location);

        return invoke("baidu/map/weather", params);
    }
}