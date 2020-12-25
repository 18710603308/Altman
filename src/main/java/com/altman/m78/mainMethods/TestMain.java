package com.altman.m78.mainMethods;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.altman.m78.model.Demo01;
import com.altman.m78.model.StandardInvoiceRespDTO;
import com.altman.m78.model.WorkerScheduleCompensateExtraInfoDTO;

import java.util.*;

import static java.lang.Thread.sleep;

public class TestMain{


    public static void main1(String[] args) {

        String jsonStr = "{'invoiceList':[{'invoiceAmount':'3809.37','invoiceCode':'031001700211','invoiceNo':'80385438','mobiles':'13983390370','plateNo':'渝AW8366','url':'castle/bizinvoice/C308732987.pdf'},{'invoiceAmount':'665','invoiceCode':'050001900111','invoiceNo':'41759699','mobiles':'13983390370','plateNo':'渝AW8366','url':'castle/compelinvoice/C308732987.pdf'}]}";

        //将json字符串转换成map
        Map<String, JSONArray> map = (Map)JSON.parse(jsonStr);
        System.out.println(map);
        //key写死  永远都是这个字段
        String key = "invoiceList";

        //取出map的值
        JSONArray jsonArray = map.get(key);

        if(jsonArray.size() <= 0 || jsonArray.isEmpty()) {
            System.out.println("数据库中没有有效的发票信息,请核实后在进行补偿.");
        }
        //map值转list  可能有交强险也可能有商业险，只需要商业险数据进行补单
        StandardInvoiceRespDTO standardInvoiceRespDTO = null;
        for (Object o : jsonArray) {
            standardInvoiceRespDTO = JSON.toJavaObject((JSON) o, StandardInvoiceRespDTO.class);
            if (standardInvoiceRespDTO.getUrl().contains("bizinvoice")) {
                System.out.println(standardInvoiceRespDTO);
                break;
            }
        }

        System.out.println(standardInvoiceRespDTO.toString());
        //map值转list  可能有交强险也可能有商业险，只需要商业险数据进行补单
//        StandardInvoiceRespDTO standardInvoiceRespDTO = JSON.parseObject(list, StandardInvoiceRespDTO.class);
//        System.out.println(standardInvoiceRespDTO.toString());


        //JSON转数组
//        JSONArray objects = JSON.parseArray(jsonStr);
    }


    public static void main2(String[] args) {

        double invoiceAmount = 100.12d;
        String extraInfo = "{" +
                "\t\"businessType\": \"1\"," +
                "\t\"endorseFailEmailFlag\": \"0\"," +
                "\t\"endorseMap\": {}," +
                "\t\"invoiceAmount\": \"" + invoiceAmount + "\"," +
                "\t\"invoiceCode\": \"031001700211\"," +
                "\t\"invoiceNo\": \"94532927\"," +
                "\t\"invoiceStatus\": \"1\"," +
                "\t\"invoiceType\": 0," +
                "\t\"orderNo\": \"C418450677\"," +
                "\t\"policyNo\": \"10591003980112748085\"," +
                "\t\"success\": \"0\"," +
                "\t\"regenerateEPolicy\": \"24|25\"" +
                "}";
        System.out.println(extraInfo);

    }


    public static void main3(String[] args) {

        String jsonContent = "{" +
                "    \"paramBizContentList\":[" +
                "        {" +
                "            \"creator\":\"system\"," +
                "            \"gmtModified\":1606816055000," +
                "            \"bizDefId\":1000008," +
                "            \"isDeleted\":\"N\"," +
                "            \"gmtCreated\":1606816055000," +
                "            \"modifier\":\"system\"," +
                "            \"id\":1000003," +
                "            \"content\":\"[{\"PHONE\":\"21\",\"password\":\"21\"}]\"" +
                "        }" +
                "    ]," +
                "    \"creator\":\"wb_hufangmiao\"," +
                "    \"gmtModified\":1606816055000," +
                "    \"isDeleted\":\"N\"," +
                "    \"gmtCreated\":1606816055000," +
                "    \"modifier\":\"system\"," +
                "    \"campaignDefId\":\"1000290004\"," +
                "    \"id\":1000008," +
                "    \"partnerId\":\"21\"," +
                "    \"insurePlaceCode\":\"11\"," +
                "    \"paramId\":1000000" +
                "}";



    }


    public static void main4(String[] args) {

        String content = "{" +
                "\"paramBizContentList\":[" +
                "{" +
                "\"creator\":\"system\"," +
                "\"gmtModified\":1606816055000," +
                "\"bizDefId\":1000008," +
                "\"isDeleted\":\"N\"," +
                "\"gmtCreated\":1606816055000," +
                "\"modifier\":\"system\"," +
                "\"id\":1000003," +
                "\"content\":\"[{\"PHONE\":\"21\",\"password\":\"21\"}]\"" +
                "}" +
                "]," +
                "\"creator\":\"wb_hufangmiao\"," +
                "\"gmtModified\":1606816055000," +
                "\"isDeleted\":\"N\"," +
                "\"gmtCreated\":1606816055000," +
                "\"modifier\":\"system\"," +
                "\"campaignDefId\":\"1000290004\"," +
                "\"id\":1000008," +
                "\"partnerId\":\"21\"," +
                "\"insurePlaceCode\":\"11\"," +
                "\"paramId\":1000000" +
                "}";


        System.out.println(content);
        Object parse = JSONObject.parse(content);
        System.out.println(parse);



    }


    public static void main5(String[] args) {

        String extraInfoStr = "{" +
                "\"businessType\": \"1\"," +
                "\"endorseFailEmailFlag\": \"0\"," +
                "\"endorseMap\": {}," +
                "\"invoiceAmount\": \"" + 1 + "\"," +
                "\"invoiceCode\": \"" + 2 + "\"," +
                "\"invoiceNo\": \"" + 3 + "\"," +
                "\"invoiceStatus\": \"1\"," +
                "\"invoiceType\": 0," +
                "\"orderNo\": \"" + 4 + "\"," +
                "\"policyNo\": \"" + 5 + "\"," +
                "\"success\": \"0\"," +
                "\"regenerateEPolicy\": \"24|25\"" +
                "}";
        System.out.println(extraInfoStr);
        WorkerScheduleCompensateExtraInfoDTO extraInfo = JSON.parseObject(extraInfoStr,
                WorkerScheduleCompensateExtraInfoDTO.class);
        System.out.println(extraInfo.toString());


    }


    public static void main6(String[] args) {

        String jsonStr = "{\"creator\":\"system\",\"gmtModified\":1606894907000,\"bizDefId\":1000010,\"isDeleted\":\"N\",\"gmtCreated\":1606894907000,\"modifier\":\"system\",\"id\":1000005,\"content\":\"{\\\"PHONE\\\":\\\"3232\\\",\\\"password\\\":\\\"3232\\\"}\"}";
        System.out.println(jsonStr);

        Map<String,String> map = (Map) JSONObject.parse(jsonStr);
        System.out.println(map);

        String content = map.get("content");
        System.out.println(content);

        Map<String,String> result = (Map) JSONObject.parse(content);
        String phone = result.get("PHONE");
        String password = result.get("password");
        System.out.println("PHONE:" + phone + "password:" + password );

    }


    public static void main7(String[] args) {


        List<String> list = new ArrayList<String>(10);
        list.add("0");
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list.add("6");
        list.add("7");
        list.add("8");
        list.add("9");
    s1 : for (String o : list) {
            int i = 0;
            for (String s : list) {
                if(i == 2){
                    break s1;
                }
                i++;
                System.out.println("第"+i+"次");
            }
            System.out.println(o);
        }



    }

    public static void main8(String[] args) throws InterruptedException {


        List<Demo01> list = new ArrayList<Demo01>(10);
//        List<String> list = new ArrayList(10);
//        list.add("0");
//        list.add("1");
//        list.add("2");
//        list.add("3");
//        list.add("4");
//        list.add("5");
//        list.add("6");
//        list.add("7");
//        list.add("8");
//        list.add("9");

        for(int i = 0 ; i < 10; i++){
            Demo01 demo01 = new Demo01();
            demo01.setDateTime(new Date());
            list.add(demo01);
            sleep(1000);
            System.out.println(new Date());
        }

        System.out.println(list);
//        List<String> collect = list.stream().collect(Collectors.<String>toList());
        list.sort(Comparator.comparing(Demo01::getDateTime).reversed());
//        .sort(Comparator.comparing((User::getCreatedOn).reversed())
//        .filter(l -> l.equals("1"))
//        System.out.println(stringStream);
        System.out.println(list);

    }
    

}
