package com.mzy.copyProject;

import org.apache.commons.io.input.ReaderInputStream;

import java.io.*;
import java.util.zip.InflaterInputStream;

/**
 * @author mzy
 * @date 2021/7/31 13:32
 */
public class CopyProject {


    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis());
        final File file = new File("E:\\bladex");
        write(file.listFiles());
        System.out.println(System.currentTimeMillis());
    }

    private static void write(File[] files){
        // 遍历文件夹
        for (File file : files) {
            final char c = file.getName().charAt(0);
            if(c == '.'){
                continue;
            }
            // 判断是否还是文件夹
            if(file.isDirectory()){
                // 创建文件夹
                final String path = file.getPath();
                // 暂时固定替换盘符
                String newPath = path.replaceFirst("E:", "D:");
                final String replaceAll = newPath.replaceAll("blade", "agora");
                final String all = replaceAll.replaceAll("Blade", "Agora");
                // 创建新文件夹
                final File newFile = new File(all);
                if(!newFile.exists()){
                    newFile.mkdirs();
                    write(file.listFiles());
                }
            }else{
                replaceWrite(file);
            }
        }
    }

    private static void replaceWrite(File file){
        final String path = file.getPath();
        String newPath = path.replace("E:", "D:");
        final String replaceAll = newPath.replaceAll("blade", "agora");
        final String all = replaceAll.replaceAll("Blade", "Agora");

        try(
                final InputStreamReader streamReader = new InputStreamReader(new FileInputStream(file));
                final OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(all));
                final BufferedReader bufferedReader = new BufferedReader(streamReader)
        ){
            while (bufferedReader.ready()){
                final String str = bufferedReader.readLine();
                final String str1 = str.replaceAll("blade", "agora");
                final String str2 = str1.replaceAll("Blade", "Agora");
                writer.write( str2+"\n");
                writer.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
