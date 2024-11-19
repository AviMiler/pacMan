package view;

import model.Map;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Screen {

    private static final int originalTileSize = 29;
    private static final int scale = 1;
    private static int maxRow = Map.getTilesHeight();
    private static int maxCol = Map.getTilesWidth();
    public static Font customFont;

    static {
        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File("C:\\Users\\User\\OneDrive\\מסמכים\\לימודים\\java\\IdeaProjects\\PacMan\\res\\font\\ARCADE_N.TTF")).deriveFont(24f);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
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
