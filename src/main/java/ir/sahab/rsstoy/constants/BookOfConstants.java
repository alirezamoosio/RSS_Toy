package ir.sahab.rsstoy.constants;

import java.util.HashMap;
import java.util.Map;

public class BookOfConstants {
    private static Map<Constant, String> book = new HashMap<>();

    public static void loadBook() {
        // TODO: 7/10/18 Implement
    }

    public static String getConstant(Constant constant) {
        return book.get(constant);
    }
}
