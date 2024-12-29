package view;

import model.Map;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class Screen {

    private static final int originalTileSize = 28;
    private static final int scale = 1;
    private static int maxRow = Map.getTilesHeight();
    private static int maxCol = Map.getTilesWidth();
    public static Font customFont;

    static {
        try (InputStream fontStream = Screen.class.getClassLoader().getResourceAsStream("font/ARCADE_N.TTF")) {
            if (fontStream == null) {
                throw new RuntimeException("Font file not found: font/ARCADE_N.TTF");
            }
            customFont = Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont(24f);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException("Failed to load custom font", e);
        }
    }

    public Screen() throws IOException, FontFormatException {
    }

    public static void updateScreen() {
        maxRow = Map.getTilesHeight() + 2;
        maxCol = Map.getTilesWidth();
    }

    public static int getTileSize(){
        return originalTileSize * scale;
    }

    public static int getHalfTileSize(){
        return getTileSize()/2;
    }

    public static int getScreenWidth() {
        return originalTileSize * scale * maxCol;
    }

    public static int getScreenHeight() {
        return originalTileSize * scale * maxRow;
    }

}
