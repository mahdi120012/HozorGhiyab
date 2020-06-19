package hozorghiyab.customClasses;

public class EnglishNumberToPersian {

    public String convert(String text){

        String matn;
        matn = text.replace("0","۰")
                .replace("1","١")
                .replace("2","٢")
                .replace("3","٣")
                .replace("4","۴")
                .replace("5","۵")
                .replace("6","۶")
                .replace("7","۷")
                .replace("8","۸")
                .replace("9","۹")
                .replace("10","۱۰");
        return matn;
    }
}