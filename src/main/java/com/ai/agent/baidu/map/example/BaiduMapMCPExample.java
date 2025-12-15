package com.ai.agent.baidu.map.example;

import com.ai.agent.baidu.map.BaiduMapMCPClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * 百度地图MCP客户端使用示例
 * 演示如何使用BaiduMapMCPClient调用百度地图功能
 */
@Component
public class BaiduMapMCPExample implements CommandLineRunner {

    private final BaiduMapMCPClient baiduMapMCPClient;

    public BaiduMapMCPExample(BaiduMapMCPClient baiduMapMCPClient) {
        this.baiduMapMCPClient = baiduMapMCPClient;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("=== 百度地图MCP客户端使用示例 ===");

        // 示例1: 地理编码
        System.out.println("\n1. 地理编码示例：");
        baiduMapMCPClient.geocode("北京市天安门", "北京")
                .subscribe(
                    response -> {
                        System.out.println("地理编码结果: " + response.getResult());
                        if (response.getError() != null) {
                            System.out.println("错误: " + response.getError().getMessage());
                        }
                    },
                    error -> System.out.println("请求失败: " + error.getMessage())
                );

        // 示例2: 逆地理编码
        System.out.println("\n2. 逆地理编码示例：");
        baiduMapMCPClient.reverseGeocode(39.908722, 116.397499)
                .subscribe(
                    response -> {
                        System.out.println("逆地理编码结果: " + response.getResult());
                        if (response.getError() != null) {
                            System.out.println("错误: " + response.getError().getMessage());
                        }
                    },
                    error -> System.out.println("请求失败: " + error.getMessage())
                );

        // 示例3: 路线规划
        System.out.println("\n3. 路线规划示例：");
        baiduMapMCPClient.getRoute("39.908722,116.397499", "31.230393,121.473702", "driving")
                .subscribe(
                    response -> {
                        System.out.println("路线规划结果: " + response.getResult());
                        if (response.getError() != null) {
                            System.out.println("错误: " + response.getError().getMessage());
                        }
                    },
                    error -> System.out.println("请求失败: " + error.getMessage())
                );

        // 示例4: 搜索周边POI
        System.out.println("\n4. 搜索周边POI示例：");
        baiduMapMCPClient.searchNearby("餐厅", "39.908722,116.397499", 1000)
                .subscribe(
                    response -> {
                        System.out.println("周边搜索结果: " + response.getResult());
                        if (response.getError() != null) {
                            System.out.println("错误: " + response.getError().getMessage());
                        }
                    },
                    error -> System.out.println("请求失败: " + error.getMessage())
                );

        // 示例5: 城市内搜索POI
        System.out.println("\n5. 城市内搜索POI示例：");
        baiduMapMCPClient.searchInCity("银行", "上海")
                .subscribe(
                    response -> {
                        System.out.println("城市搜索结果: " + response.getResult());
                        if (response.getError() != null) {
                            System.out.println("错误: " + response.getError().getMessage());
                        }
                    },
                    error -> System.out.println("请求失败: " + error.getMessage())
                );
    }
}