package com.spilna.sprava.businesslogic.objects;

/**
 * Created by Ivanov on 10.11.15.
 */
public enum Oblast {
    KUIVSKA("kuyivska"),
    ODESSA("odesska"),
    VINNITSA("vinnitska"),
    LVIVSKA("lvivska"),
    IVANOFRANKIVSK("ivanofrankivska"),
    ZHITOMERSKA("zhitomerska"),
    HARKIVSKA("harkiv"),
    SUMSKA("sumska"),
    DONETSKA("donetska"),
    LUGANSKA("luhanska"),
    MIKOLAEVSKA("mikolaivska"),
    VOLINSKA("volinska"),
    CHERNIGIVSKA("chernigivska"),
    CHERKASKA("cherkaska"),
    ZAKARPATSKA("zakarpatska"),
    ZAPORIZHSKA("zaporizhska"),
    KIROVOGRADSKA("kirovogradska"),
    TERNOPILSKA("ternopilska"),
    HMELNITSKA("hmelnitska"),
    DNIPROPETROVSKA("dnipropetrovska"),
    POLTAVSKA("poltavska"),
    RIVNENSKA("rivnenska"),
    HERSONSKA("hersonska"),
    CHERNIVETSKA("chernivetska");

    private String value;

    Oblast(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Oblast parse(String valueStr) {
        for (Oblast value: values()) {
            if (valueStr != null && value.getValue().equals(valueStr)){
                return value;
            }
        }
        return null;
    }


}
