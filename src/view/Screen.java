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

    public static Font loadCustomFont(String fontFilePath, float size) {
        try {
            // טוען את קובץ הפונט לפי נתיב ישיר
            File fontFile = new File(fontFilePath);
            if (!fontFile.exists()) {
                throw new RuntimeException("Font file not found: " + fontFilePath);
            }
            Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
            return font.deriveFont(size); // גודל הפונט
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException("Failed to load custom font", e);
        }
    }

    public static void updateScreen() {
        maxRow = Map.getTilesHeight() + 2;
        maxCol = Map.getTilesWidth();
        customFont=loadCustomFont("resources\\font\\ARCADE_N.TTF",24f);
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
