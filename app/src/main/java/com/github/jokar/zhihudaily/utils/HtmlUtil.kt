package com.github.jokar.zhihudaily.utils

/**
 * Created by JokAr on 2017/6/30.
 */
object HtmlUtil {

    //css样式,隐藏header
    private val HIDE_HEADER_STYLE = "<style>div.headline{display:none;}</style>"

    //css style tag,需要格式化
    private val NEEDED_FORMAT_CSS_TAG = "<link rel=\"stylesheet\" type=\"text/css\" href=\"%s\"/>"

    // js script tag,需要格式化
    private val NEEDED_FORMAT_JS_TAG = "<script src=\"%s\"></script>"

    val MIME_TYPE = "text/html; charset=utf-8"
    val ENCODING = "utf-8"

    /**
     * 根据css链接生成Link标签

     * @param url String
     * *
     * @return String
     */
    fun createCssTag(url: String): String {
        return String.format(NEEDED_FORMAT_CSS_TAG, url)
    }

    /**
     * 根据多个css链接生成Link标签

     * @param urls List<String>
     * *
     * @return String
    </String> */
    fun createCssTag(urls: Array<String>?): String {
        val sb = StringBuilder()
        if (urls != null)
            for (url in urls) {
                sb.append(createCssTag(url))
            }
        return sb.toString()
    }

    /**
     * 根据js链接生成Script标签

     * @param url String
     * *
     * @return String
     */
    fun createJsTag(url: String): String {
        return String.format(NEEDED_FORMAT_JS_TAG, url)
    }

    /**
     * 根据多个js链接生成Script标签

     * @param urls List<String>
     * *
     * @return String
    </String> */
    fun createJsTag(urls: Array<String>?): String {
        val sb = StringBuilder()
        if (urls != null)
            for (url in urls) {
                sb.append(createJsTag(url))
            }
        return sb.toString()
    }

    /**
     * 根据样式标签,html字符串,js标签
     * 生成完整的HTML文档

     * @param html string
     * *
     * @param css  string
     * *
     * @param js   string
     * *
     * @return string
     */
    private fun createHtmlData(html: String, css: String, js: String): String {
        return css + HIDE_HEADER_STYLE + html + js
    }

    /**
     * 根据News
     * 生成完整的HTML文档

     * @param news news
     * *
     * @return String
     */
    fun createHtmlData(cssArray: Array<String>?,
                       jsArray: Array<String>?,
                       body: String): String {
        val css = HtmlUtil.createCssTag(cssArray)
        val js = HtmlUtil.createJsTag(jsArray)
        return createHtmlData(body, css, js)
    }
}