package com.sweet.auto_language.xml;

import com.sweet.auto_language.SweetLanguageConfig;
import com.sweet.auto_language.xml.bean.XlsReadBean;

import java.io.File;


/**
 * @author zhengshaorui 2018/6/24
 */
public class ReadXlsToXml {

    private static String ROOT_FILE = "language.xlsx"; // excel 的名字
    private static String FILE_NAME = "outL";  //生成的文件夹的子
    public static String STRING_NAME = "strings.xml"; //要生成的 strings 的名字
    public static String ARRAY_NAME = "arrays.xml"; //要生成的 array 的名字
    private static String ROOT_PATH; // 当前路径
    private static int IGNORE_ROW = 1; //要忽略的行,根据你们的 xls 来

    public static void main() {
        File file = new File(SweetLanguageConfig.Companion.getConfig().getFilePath());
        ROOT_PATH = file.getParent();
        ROOT_FILE = file.getName();
        System.out.println("RootPath:" + ROOT_PATH + "\n"
                + "RootFile:" + ROOT_FILE);
        XlsReadBean bean = new XlsReadBean.Builder()
                .setRootPath(ROOT_PATH) //根路径
                .setXlsFile(ROOT_FILE) //xls文件
                .setFileFloderName(FILE_NAME) //要生成的文件夹名字
                .setIgnoreRow(IGNORE_ROW) //需要忽略的行，即不需要转换的
                .setStringName(STRING_NAME) //strings.xml 的名字，可以客制化
                .setArrayName(ARRAY_NAME) //array.xml 的名字，可以客制化
                .builder();

        ReadXlsManager.getInstance().readXls(bean.getBuilder());
        System.out.println("在 " + ROOT_PATH + File.separator + FILE_NAME + " 生成文件啦!!");
    }


}