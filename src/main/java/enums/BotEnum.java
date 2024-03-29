package enums;

import java.util.Arrays;

public enum BotEnum {
    RANDOM("Random Bot"),
    HARD("Hard Bot"),
    CUSTOM("Custom Bot"),
    RAND_AGGRO("Random/Aggro Bot");

    public final String label;

    BotEnum(String label) {
        this.label = label;
    }

    public static BotEnum valueOfLabel(String label) {
        return Arrays.stream(values())
                .filter(x -> label.equals(x.label))
                .findFirst()
                .orElse(null);
    }
}
