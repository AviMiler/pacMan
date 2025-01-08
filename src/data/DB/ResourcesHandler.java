package data.DB;

import services.Consts;
import services.Services;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class ResourcesHandler {

    private static final Map<String, Image> imageCache = new HashMap<>();

    public static void loadImages() {

    }

    public static Image getImage(String path) {
        if (!imageCache.containsKey(path)) {
            imageCache.put(path,Services.loadImage(path));
        }
        return imageCache.get(path);
    }
}
