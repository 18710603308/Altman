package com.ai.agent.services.baidu;

import com.ai.agent.mcp.server.MCPServer;
import com.ai.agent.mcp.server.MCPServer.MCPServiceHandler;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 百度地图MCP服务实现
 * 提供地理编码、逆地理编码、路线规划等功能
 */
public class BaiduMapMCPService {
    private static final Logger logger = LoggerFactory.getLogger(BaiduMapMCPService.class);
    
    private static final String GEOCODER_URL = "https://api.map.baidu.com/geocoding/v3/";
    private static final String REVERSE_GEOCODER_URL = "https://api.map.baidu.com/reverse_geocoding/v3/";
    private static final String DIRECTION_URL = "https://api.map.baidu.com/directionlite/v1/";
    
    private final String apiKey;
    private final CloseableHttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final MCPServer mcpServer;
    
    public BaiduMapMCPService(String apiKey, MCPServer mcpServer) {
        this.apiKey = apiKey;
        this.mcpServer = mcpServer;
        this.httpClient = HttpClients.createDefault();
        this.objectMapper = new ObjectMapper();
        
        // 注册百度地图相关的MCP服务
        registerServices();
    }
    
    /**
     * 注册百度地图相关的MCP服务
     */
    private void registerServices() {
        // 地理编码服务 - 将地址转换为经纬度
        mcpServer.registerService("baidu.geocoder", new GeocoderHandler());
        
        // 逆地理编码服务 - 将经纬度转换为地址
        mcpServer.registerService("baidu.reverse_geocoder", new ReverseGeocoderHandler());
        
        // 路径规划服务
        mcpServer.registerService("baidu.direction", new DirectionHandler());
        
        logger.info("Registered Baidu Map MCP services");
    }
    
    /**
     * 地理编码处理器 - 将地址转换为经纬度
     */
    private class GeocoderHandler implements MCPServiceHandler {
        @Override
        public Object handle(Object params) throws Exception {
            JsonNode paramNode = objectMapper.valueToTree(params);
            String address = paramNode.get("address").asText();
            
            String url = GEOCODER_URL + "?address=" + URLEncoder.encode(address, StandardCharsets.UTF_8.name()) 
                       + "&output=json&ak=" + apiKey;
            
            HttpGet request = new HttpGet(url);
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                String jsonResponse = EntityUtils.toString(response.getEntity());
                JsonNode result = objectMapper.readTree(jsonResponse);
                
                if (result.has("status") && result.get("status").asInt() == 0) {
                    JsonNode location = result.get("result").get("location");
                    Map<String, Object> responseMap = new HashMap<>();
                    responseMap.put("longitude", location.get("lng").asDouble());
                    responseMap.put("latitude", location.get("lat").asDouble());
                    responseMap.put("address", address);
                    return responseMap;
                } else {
                    throw new RuntimeException("Geocoding failed: " + result.get("message").asText());
                }
            }
        }
    }
    
    /**
     * 逆地理编码处理器 - 将经纬度转换为地址
     */
    private class ReverseGeocoderHandler implements MCPServiceHandler {
        @Override
        public Object handle(Object params) throws Exception {
            JsonNode paramNode = objectMapper.valueToTree(params);
            double latitude = paramNode.get("latitude").asDouble();
            double longitude = paramNode.get("longitude").asDouble();
            
            String url = REVERSE_GEOCODER_URL + "?coordtype=wgs84ll&location=" + latitude + "," + longitude
                       + "&output=json&ak=" + apiKey;
            
            HttpGet request = new HttpGet(url);
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                String jsonResponse = EntityUtils.toString(response.getEntity());
                JsonNode result = objectMapper.readTree(jsonResponse);
                
                if (result.has("status") && result.get("status").asInt() == 0) {
                    JsonNode formattedAddress = result.get("result").get("formatted_address");
                    JsonNode pois = result.get("result").get("pois");
                    
                    Map<String, Object> responseMap = new HashMap<>();
                    responseMap.put("address", formattedAddress.asText());
                    responseMap.put("latitude", latitude);
                    responseMap.put("longitude", longitude);
                    
                    // 添加兴趣点信息（如果有的话）
                    if (pois != null && pois.isArray() && pois.size() > 0) {
                        responseMap.put("nearby_pois", pois);
                    }
                    
                    return responseMap;
                } else {
                    throw new RuntimeException("Reverse geocoding failed: " + result.get("message").asText());
                }
            }
        }
    }
    
    /**
     * 路径规划处理器
     */
    private class DirectionHandler implements MCPServiceHandler {
        @Override
        public Object handle(Object params) throws Exception {
            JsonNode paramNode = objectMapper.valueToTree(params);
            String mode = paramNode.has("mode") ? paramNode.get("mode").asText() : "driving"; // driving, riding, walking
            double originLat = paramNode.get("origin_latitude").asDouble();
            double originLng = paramNode.get("origin_longitude").asDouble();
            double destLat = paramNode.get("destination_latitude").asDouble();
            double destLng = paramNode.get("destination_longitude").asDouble();
            
            String url = DIRECTION_URL + mode + "?origin=" + originLat + "," + originLng
                       + "&destination=" + destLat + "," + destLng
                       + "&ak=" + apiKey + "&output=json";
            
            HttpGet request = new HttpGet(url);
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                String jsonResponse = EntityUtils.toString(response.getEntity());
                JsonNode result = objectMapper.readTree(jsonResponse);
                
                if (result.has("status") && result.get("status").asInt() == 0) {
                    JsonNode routes = result.get("result").get("routes");
                    if (routes != null && routes.isArray() && routes.size() > 0) {
                        JsonNode route = routes.get(0);
                        Map<String, Object> responseMap = new HashMap<>();
                        responseMap.put("distance", route.get("distance").asInt());
                        responseMap.put("duration", route.get("duration").asInt());
                        responseMap.put("steps", route.get("steps"));
                        return responseMap;
                    } else {
                        throw new RuntimeException("No routes found");
                    }
                } else {
                    throw new RuntimeException("Direction calculation failed: " + result.get("message").asText());
                }
            }
        }
    }
    
    /**
     * 关闭资源
     */
    public void close() throws IOException {
        httpClient.close();
    }
}