package net.myscloud.open.yuna.common.kits;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public final class StringKits extends StringUtils {

    // \b 是单词边界(连着的两个(字母字符 与 非字母字符) 之间的逻辑上的间隔),
    // 字符串在编译时会被转码一次,所以是 "\\b"
    // \B 是单词内部逻辑间隔(连着的两个字母字符之间的逻辑上的间隔)
    private static final String PHONE_REG = "\\b(ip(hone|od)|android|opera m(ob|in)i"
            + "|windows (phone|ce)|blackberry"
            + "|s(ymbian|eries60|amsung)|p(laybook|alm|rofile/midp"
            + "|laystation portable)|nokia|fennec|htc[-_]"
            + "|mobile|up.browser|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";
    private static final String PAD_REG = "\\b(ipad|tablet|(Nexus 7)|up.browser"
            + "|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";
    //移动设备正则匹配：手机端、平板
    private static final Pattern PHONE_PAT = Pattern.compile(PHONE_REG, Pattern.CASE_INSENSITIVE);
    private static final Pattern PAD_PAT = Pattern.compile(PAD_REG, Pattern.CASE_INSENSITIVE);

    public static String buildUUID() {
        return UUID.randomUUID().toString();
    }

    public static String buildShortUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String getFileExtName(String fileName) {
        int lastIndexOfDot = fileName.lastIndexOf('.');
        if (lastIndexOfDot < 0) {
            return fileName;
        }
        int fileNameLength = fileName.length();
        return fileName.substring(lastIndexOfDot + 1, fileNameLength);
    }

    /**
     * 填充简易模板
     *
     * @param template eg. test{test} + test:1 => test1
     * @param params
     * @param <T>
     * @return
     */
    public static <T> String fillTemplate(final String template, final Map<String, T> params) {
        String temp = template;
        for (Map.Entry<String, T> entry : params.entrySet()) {
            temp = temp.replaceAll("\\{" + entry.getKey() + "\\}", entry.getValue().toString());
        }
        return temp;
    }

    /**
     * 过滤特殊字符
     *
     * @param value
     * @return
     */
    public static String htmlEscape(String value) {
        if (value == null) {
            return null;
        }
        StringBuilder escaped = new StringBuilder(value.length() * 2);
        for (int i = 0; i < value.length(); i++) {
            char character = value.charAt(i);
            String reference = convertToReference(character);
            if (reference != null) {
                escaped.append(reference);
            } else {
                escaped.append(character);
            }
        }
        return escaped.toString();
    }

    public static String convertToReference(char character) {
        switch (character) {
            case '<':
                return "&lt;";
            case '>':
                return "&gt;";
            case '"':
                return "&quot;";
            case '&':
                return "&amp;";
            case '\'':
                return "&#39;";
        }
        return null;
    }

    /**
     * URLEncoder
     *
     * @param value
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String urlEncode(String value) throws UnsupportedEncodingException {
        return URLEncoder.encode(value, "UTF-8");
    }

    /**
     * URLDecoder
     *
     * @param value
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String urlDecode(String value) throws UnsupportedEncodingException {
        return URLDecoder.decode(value, "UTF-8");
    }

    /**
     * 检测是否是移动设备访问
     *
     * @param userAgent 浏览器标识
     * @return true:移动设备接入，false:pc端接入
     */
    public static boolean isMobile(String userAgent) {
        if (null == userAgent) {
            userAgent = "";
        }
        // 匹配
        Matcher matcherPhone = PHONE_PAT.matcher(userAgent);
        Matcher matcherTable = PAD_PAT.matcher(userAgent);
        return matcherPhone.find() || matcherTable.find();
    }

    /**
     * 将驼峰式命名的字符串转换为下划线大写方式。如果转换前的驼峰式命名的字符串为空，则返回空字符串。</br>
     * 例如：HelloWorld->HELLO_WORLD
     *
     * @param name 转换前的驼峰式命名的字符串
     * @return 转换后下划线大写方式命名的字符串
     */
    public static String underscoreName(String name) {
        StringBuilder result = new StringBuilder();
        if (StringKits.isNotEmpty(name)) {
            for (Character c : name.toCharArray()) {
                if (c.compareTo('a') < 0 && !Character.isDigit(c)) {
                    result.append("_").append(c.toString().toLowerCase());
                } else {
                    result.append(c);
                }
            }
        }
        return result.toString().toLowerCase();
    }

    /**
     * 判断某字符串与一组字符串是否都相等
     *
     * @param main
     * @param others
     * @return
     */
    public static boolean equals(CharSequence main, CharSequence... others) {
        for (CharSequence sequence : others) {
            if (!main.equals(sequence)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotNumeric(CharSequence cs) {
        return !isNumeric(cs);
    }

    public static boolean isEmail(CharSequence cs) {
        if (null == cs || "".equals(cs)) return false;
        Pattern p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配
        Matcher m = p.matcher(cs);
        return m.matches();
    }
}
