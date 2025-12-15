# 百度地图MCP客户端

这是一个基于MCP（Model Context Protocol）协议的百度地图客户端实现，允许通过统一的接口调用百度地图的各种功能。

## 功能特性

- **地理编码**：将地址转换为经纬度坐标
- **逆地理编码**：将经纬度坐标转换为地址
- **路线规划**：获取驾车、步行、公交路线信息
- **POI搜索**：搜索周边或城市内的兴趣点
- **天气信息**：获取位置天气信息（需集成第三方天气API）

## 配置

在 `application.properties` 文件中配置百度地图API密钥：

```properties
# Baidu Map Configuration
baidu.map.api-key=YOUR_BAIDU_MAP_API_KEY
baidu.map.base-url=https://api.map.baidu.com
baidu.map.timeout=5000
```

## API端点

### REST API端点

- `GET /api/baidu-map/geocode` - 地理编码
- `GET /api/baidu-map/reverse-geocode` - 逆地理编码
- `GET /api/baidu-map/route` - 路线规划
- `GET /api/baidu-map/search-nearby` - 搜索周边POI
- `GET /api/baidu-map/search-city` - 搜索城市内POI
- `GET /api/baidu-map/weather` - 获取天气信息
- `POST /api/baidu-map/invoke` - 通用MCP调用

### MCP方法

- `baidu/map/geocode` - 地理编码
- `baidu/map/reverse_geocode` - 逆地理编码
- `baidu/map/route` - 路线规划
- `baidu/map/search_nearby` - 搜索周边POI
- `baidu/map/search_city` - 搜索城市内POI
- `baidu/map/weather` - 获取天气信息

## 使用示例

### Java客户端使用

```java
@Autowired
private BaiduMapMCPClient baiduMapMCPClient;

// 地理编码
Mono<MCPResponse> response = baiduMapMCPClient.geocode("北京市天安门", "北京");

// 逆地理编码
Mono<MCPResponse> response = baiduMapMCPClient.reverseGeocode(39.908722, 116.397499);

// 路线规划
Mono<MCPResponse> response = baiduMapMCPClient.getRoute("39.908722,116.397499", "31.230393,121.473702", "driving");

// 搜索周边POI
Mono<MCPResponse> response = baiduMapMCPClient.searchNearby("餐厅", "39.908722,116.397499", 1000);

// 搜索城市内POI
Mono<MCPResponse> response = baiduMapMCPClient.searchInCity("银行", "上海");
```

### HTTP API使用

```bash
# 地理编码
curl "http://localhost:8080/api/baidu-map/geocode?address=北京市天安门&city=北京"

# 逆地理编码
curl "http://localhost:8080/api/baidu-map/reverse-geocode?lat=39.908722&lng=116.397499"

# 路线规划
curl "http://localhost:8080/api/baidu-map/route?origin=39.908722,116.397499&destination=31.230393,121.473702&mode=driving"

# 搜索周边POI
curl "http://localhost:8080/api/baidu-map/search-nearby?keyword=餐厅&location=39.908722,116.397499&radius=1000"

# 搜索城市内POI
curl "http://localhost:8080/api/baidu-map/search-city?keyword=银行&city=上海"
```

### MCP协议调用

```json
{
  "id": "request-123",
  "method": "baidu/map/geocode",
  "params": {
    "address": "北京市天安门",
    "city": "北京"
  }
}
```

## 项目结构

```
src/main/java/com/ai/agent/baidu/map/
├── BaiduMapMCPClient.java          # MCP客户端主类
├── BaiduMapController.java         # REST API控制器
├── config/
│   └── BaiduMapConfig.java         # 配置类
├── service/
│   └── BaiduMapApiService.java     # 百度地图API服务
├── handler/
│   └── BaiduMapMCPHandler.java     # MCP请求处理器
└── example/
    └── BaiduMapMCPExample.java     # 使用示例
```

## 依赖

- Spring Boot Web
- Spring Boot WebFlux
- Spring Boot Configuration Processor

## 注意事项

1. 需要申请百度地图API密钥并配置到应用中
2. 百度地图API有调用频率限制，请合理使用
3. 部分功能可能需要额外的API权限
4. 天气信息功能需要集成第三方天气API