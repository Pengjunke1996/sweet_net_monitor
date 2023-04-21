package com.example.sweet_language_lib

import com.alibaba.excel.EasyExcel
import com.alibaba.excel.context.AnalysisContext
import com.alibaba.excel.read.listener.ReadListener
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.util.regex.Pattern


fun main() {
    Excel2Xml().simpleRead()
}


class Excel2Xml {
    val pattern = Pattern.compile("%[^sd]")


    fun simpleRead() {

        var fileName = "resource/TT.xlsx"
        println(File(fileName).exists())

        val fileEn = File("resource/src/main/res/values/strings.xml")
        val fileAr = File("resource/src/main/res/values-ar/strings.xml")
        val fileZh = File("resource/src/main/res/values-zh/strings.xml")
        val fileTr = File("resource/src/main/res/values-tr/strings.xml")

        if (!fileEn.exists()) {
            fileEn.createNewFile();
        }
        if (!fileAr.exists()) {
            fileAr.createNewFile();
        }
        if (!fileZh.exists()) {
            fileZh.createNewFile();
        }
        if (!fileTr.exists()) {
            fileTr.createNewFile();
        }

        val bwEn = BufferedWriter(FileWriter(fileEn))
        val bwAr = BufferedWriter(FileWriter(fileAr))
        val bwZh = BufferedWriter(FileWriter(fileZh))
        val bwTr = BufferedWriter(FileWriter(fileTr))


        val excelReaderBuilder = EasyExcel.read(fileName)
        val excelReader = excelReaderBuilder
            .build()

        val sheets = excelReader.excelExecutor().sheetList()

        bwEn.write(
            "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                    "<resources>"
        )
        bwAr.write(
            "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                    "<resources>"
        )
        bwZh.write(
            "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                    "<resources>"
        )
        bwTr.write(
            "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                    "<resources>"
        )

        for (sheet in sheets) {
            println(sheet.sheetName)
            bwEn.write("<!-- ⬇⬇${sheet.sheetName}⬇⬇ -->\n")
            bwAr.write("<!-- ⬇⬇${sheet.sheetName}⬇⬇ -->\n")
            bwZh.write("<!-- ⬇⬇${sheet.sheetName}⬇⬇ -->\n")
            bwTr.write("<!-- ⬇⬇${sheet.sheetName}⬇⬇ -->\n")

            val doReadSync =
                EasyExcel.read(fileName).head(DemoData::class.java).sheet(sheet.sheetName)
                    .doReadSync<DemoData>()
            val quChongKey = HashSet<String>()
            doReadSync.forEach { data ->
                val key = data.key
                if (key != null && !quChongKey.contains(key)) {
                    quChongKey.add(key)
                    addKeyValue(data.en, bwEn, key)
                    addKeyValue(data.ar, bwAr, key)
                    addKeyValue(data.zh, bwZh, key)
                    addKeyValue(data.tr, bwTr, key)
                }
            }

            bwEn.write("<!-- ⬆⬆${sheet.sheetName}⬆⬆ -->\n")
            bwAr.write("<!-- ⬆⬆${sheet.sheetName}⬆⬆ -->\n")
            bwZh.write("<!-- ⬆⬆${sheet.sheetName}⬆⬆ -->\n")
            bwTr.write("<!-- ⬆⬆${sheet.sheetName}⬆⬆ -->\n")
            bwEn.flush()
            bwAr.flush()
            bwZh.flush()
            bwTr.flush()
        }

        bwEn.write(" </resources>")
        bwAr.write(" </resources>")
        bwZh.write(" </resources>")
        bwTr.write(" </resources>")
        bwEn.flush()
        bwAr.flush()
        bwZh.flush()
        bwTr.flush()
        bwEn.close()
        bwAr.close()
        bwZh.close()
        bwTr.close()
    }

    private fun addKeyValue(
        data: String?,
        bwEn: BufferedWriter,
        key: String?,
    ) {
        if (data?.isNotBlank() == true) {
            bwEn.write("  <string name=\"$key\">${str(key, data)}</string>\n")
        }
    }

    fun key(str: String?): String {
        return str ?: "null_${a++}"
    }

    val exKeys = arrayOf(
        "me_profile_edit9",
        "register12",
        "register13",

        )

    fun str(key: String?, str: String?): String? {
        if (exKeys.contains(key)) {
            return str
        }
        if (str == null) {
            return str
        }
        val s = str
            .replace(" ", " ")
            .replace("%d", "%s")
            .replace("{%s}", "%s")
            .replace("'", "\\'")
            .replace("@", "\\@")
            .replace("\"", "\\\"")
            .replace("...", "…")
            .replace("{%d}", "%s")
            .replace("{% d}", "%s")
            .replace("{٪ d}", "%s")
            .replace("٪ s", "%s")
            .replace("&", "&amp;")
        //检测 s中的 %

//        println(matches.find())
//        println(matches.group())
        val matches = pattern.matcher(s)
        if (matches.find()) {
            System.err.println("$key > $s");
        }
        return s
    }

    companion object {
        var a = 0
    }
}

class DemoDataListener : ReadListener<DemoData> {
    private lateinit var bwEn: BufferedWriter
    private lateinit var bwAr: BufferedWriter
    private lateinit var bwZh: BufferedWriter

    constructor(bwEn: BufferedWriter, bwAr: BufferedWriter, bwZh: BufferedWriter) {
        // 这里是demo，所以随便new一个
        this.bwEn = bwEn
        this.bwAr = bwAr
        this.bwZh = bwZh
    }


    val list = arrayListOf<DemoData>()

    /**
     * 这个每一条数据解析都会来调用
     *
     * @param data    one row value. Is is same as [AnalysisContext.readRowHolder]
     * @param context
     */


    override fun invoke(data: DemoData, context: AnalysisContext) {
//        println("解析到一条数据:{}" + data.key + "/ " + data.en)
        list.add(data)
    }


    /**
     * 所有数据解析完成了 都会来调用
     *
     * @param context
     */


    override fun doAfterAllAnalysed(context: AnalysisContext) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库


    }

    companion object {
        /**
         * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
         */


        private const val BATCH_COUNT = 100
    }
}

