package com.ai.agent.baidu.map.handler;

import com.ai.agent.baidu.map.service.BaiduMapApiService;
import com.ai.agent.mcp.server.model.MCPRequest;
import com.ai.agent.mcp.server.model.MCPResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * 百度地图MCP处理器
 * 处理来自MCP客户端的百度地图相关请求
 */
@Component
public class BaiduMapMCPHandler {

    private final BaiduMapApiService baiduMapApiService;

    public BaiduMapMCPHandler(BaiduMapApiService baiduMapApiService) {
        this.baiduMapApiService = baiduMapApiService;
    }

    /**
     * 处理MCP请求
     *
     * @param request MCP请求
     * @return MCP响应
     */
    public Mono<MCPResponse> handle(MCPRequest request) {
        String method = request.getMethod();
        @SuppressWarnings("unchecked")
        Map<String, Object> params = (Map<String, Object>) request.getParams();

        switch (method) {
            case "baidu/map/geocode":
                return handleGeocode(params);
            case "baidu/map/reverse_geocode":
                return handleReverseGeocode(params);
            case "baidu/map/route":
                return handleRoute(params);
            case "baidu/map/search_nearby":
                return handleSearchNearby(params);
            case "baidu/map/search_city":
                return handleSearchInCity(params);
            case "baidu/map/weather":
                return handleWeather(params);
            default:
                return Mono.just(createErrorResponse(request.getId(), "Unknown method: " + method));
        }
    }

    private Mono<MCPResponse> handleGeocode(Map<String, Object> params) {
        String address = (String) params.get("address");
        String city = (String) params.get("city");

        return baiduMapApiService.geocode(address, city)
                .map(apiResponse -> {
                    MCPResponse response = new MCPResponse();
                    // ID会在上层设置，这里不需要设置
                    response.setResult(apiResponse);
                    return response;
                })
                .onErrorReturn(createErrorResponse(null, "Failed to perform geocoding"));
    }

    private Mono<MCPResponse> handleReverseGeocode(Map<String, Object> params) {
        Double lat = (Double) params.get("lat");
        Double lng = (Double) params.get("lng");

        return baiduMapApiService.reverseGeocode(lat, lng)
                .map(apiResponse -> {
                    MCPResponse response = new MCPResponse();
                    // ID会在上层设置，这里不需要设置
                    response.setResult(apiResponse);
                    return response;
                })
                .onErrorReturn(createErrorResponse(null, "Failed to perform reverse geocoding"));
    }

    private Mono<MCPResponse> handleRoute(Map<String, Object> params) {
        String origin = (String) params.get("origin");
        String destination = (String) params.get("destination");
        String mode = (String) params.get("mode");

        return baiduMapApiService.getRoute(origin, destination, mode)
                .map(apiResponse -> {
                    MCPResponse response = new MCPResponse();
                    // ID会在上层设置，这里不需要设置
                    response.setResult(apiResponse);
                    return response;
                })
                .onErrorReturn(createErrorResponse(null, "Failed to get route information"));
    }

    private Mono<MCPResponse> handleSearchNearby(Map<String, Object> params) {
        String keyword = (String) params.get("keyword");
        String location = (String) params.get("location");
        Integer radius = (Integer) params.get("radius");

        return baiduMapApiService.searchNearby(keyword, location, radius)
                .map(apiResponse -> {
                    MCPResponse response = new MCPResponse();
                    // ID会在上层设置，这里不需要设置
                    response.setResult(apiResponse);
                    return response;
                })
                .onErrorReturn(createErrorResponse(null, "Failed to search nearby POIs"));
    }

    private Mono<MCPResponse> handleSearchInCity(Map<String, Object> params) {
        String keyword = (String) params.get("keyword");
        String city = (String) params.get("city");

        return baiduMapApiService.searchInCity(keyword, city)
                .map(apiResponse -> {
                    MCPResponse response = new MCPResponse();
                    // ID会在上层设置，这里不需要设置
                    response.setResult(apiResponse);
                    return response;
                })
                .onErrorReturn(createErrorResponse(null, "Failed to search POIs in city"));
    }

    private Mono<MCPResponse> handleWeather(Map<String, Object> params) {
        String location = (String) params.get("location");

        return baiduMapApiService.getWeather(location)
                .map(apiResponse -> {
                    MCPResponse response = new MCPResponse();
                    // ID会在上层设置，这里不需要设置
                    response.setResult(apiResponse);
                    return response;
                })
                .onErrorReturn(createErrorResponse(null, "Failed to get weather information"));
    }

    private MCPResponse createErrorResponse(String id, String message) {
        MCPResponse response = new MCPResponse();
        response.setId(id);
        MCPResponse.ErrorDetails error = new MCPResponse.ErrorDetails();
        error.setCode("500");
        error.setMessage(message);
        response.setError(error);
        return response;
    }
}