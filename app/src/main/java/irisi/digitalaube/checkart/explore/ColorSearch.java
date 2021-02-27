package irisi.digitalaube.checkart.explore;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ColorSearch {

    static String COLOR_PREFIX = "#";

    static HashMap<String, String> map = getColors();

    public static HashMap<String, String> getColors() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("AliceBlue", "#F0F8FF");
        map.put("AntiqueWhite", "#FAEBD7");
        map.put("Aqua", "#00FFFF");
        map.put("Aquamarine", "#7FFFD4");
        map.put("Azure", "#F0FFFF");
        map.put("Beige", "#F5F5DC");
        map.put("Bisque", "#FFE4C4");
        map.put("Black", "#000000");
        map.put("BlanchedAlmond", "#FFEBCD");
        map.put("Blue", "#0000FF");
        map.put("BlueViolet", "#8A2BE2");
        map.put("Brown", "#A52A2A");
        map.put("BurlyWood", "#DEB887");
        map.put("CadetBlue", "#5F9EA0");
        map.put("Chartreuse", "#7FFF00");
        map.put("Chocolate", "#D2691E");
        map.put("Coral", "#FF7F50");
        map.put("CornflowerBlue", "#6495ED");
        map.put("Cornsilk", "#FFF8DC");
        map.put("Crimson", "#DC143C");
        map.put("Cyan", "#00FFFF");
        map.put("DarkBlue", "#00008B");
        map.put("DarkCyan", "#008B8B");
        map.put("DarkGoldenrod", "#B8860B");
        map.put("DarkGray", "#A9A9A9");
        map.put("DarkGreen", "#006400");
        map.put("DarkKhaki", "#BDB76B");
        map.put("DarkMagenta", "#8B008B");
        map.put("DarkOliveGreen", "#556B2F");
        map.put("DarkOrange", "#FF8C00");
        map.put("DarkOrchid", "#9932CC");
        map.put("DarkRed", "#8B0000");
        map.put("DarkSalmon", "#E9967A");
        map.put("DarkSeaGreen", "#8FBC8F");
        map.put("DarkSlateBlue", "#483D8B");
        map.put("DarkSlateGray", "#2F4F4F");
        map.put("DarkTurquoise", "#00CED1");
        map.put("DarkViolet", "#9400D3");
        map.put("DeepPink", "#FF1493");
        map.put("DeepSkyBlue", "#00BFFF");
        map.put("DimGray", "#696969");
        map.put("DodgerBlue", "#1E90FF");
        map.put("FireBrick", "#B22222");
        map.put("FloralWhite", "#FFFAF0");
        map.put("ForestGreen", "#228B22");
        map.put("Fuchsia", "#FF00FF");
        map.put("Gainsboro", "#DCDCDC");
        map.put("GhostWhite", "#F8F8FF");
        map.put("Gold", "#FFD700");
        map.put("Goldenrod", "#DAA520");
        map.put("Gray", "#808080");
        map.put("Green", "#008000");
        map.put("GreenYellow", "#ADFF2F");
        map.put("Honeydew", "#F0FFF0");
        map.put("HotPink", "#FF69B4");
        map.put("IndianRed", "#CD5C5C");
        map.put("Indigo", "#4B0082");
        map.put("Ivory", "#FFFFF0");
        map.put("Khaki", "#F0E68C");
        map.put("Lavender", "#E6E6FA");
        map.put("LavenderBlush", "#FFF0F5");
        map.put("LawnGreen", "#7CFC00");
        map.put("LemonChiffon", "#FFFACD");
        map.put("LightBlue", "#ADD8E6");
        map.put("LightCoral", "#F08080");
        map.put("LightCyan", "#E0FFFF");
        map.put("LightGoldenrodYellow", "#FAFAD2");
        map.put("LightGreen", "#90EE90");
        map.put("LightGrey", "#D3D3D3");
        map.put("LightPink", "#FFB6C1");
        map.put("LightSalmon", "#FFA07A");
        map.put("LightSeaGreen", "#20B2AA");
        map.put("LightSkyBlue", "#87CEFA");
        map.put("LightSlateGray", "#778899");
        map.put("LightSteelBlue", "#B0C4DE");
        map.put("LightYellow", "#FFFFE0");
        map.put("Lime", "#00FF00");
        map.put("LimeGreen", "#32CD32");
        map.put("Linen", "#FAF0E6");
        map.put("Magenta", "#FF00FF");
        map.put("Maroon", "#800000");
        map.put("MediumAquamarine", "#66CDAA");
        map.put("MediumBlue", "#0000CD");
        map.put("MediumOrchid", "#BA55D3");
        map.put("MediumPurple", "#9370DB");
        map.put("MediumSeaGreen", "#3CB371");
        map.put("MediumSlateBlue", "#7B68EE");
        map.put("MediumSpringGreen", "#00FA9A");
        map.put("MediumTurquoise", "#48D1CC");
        map.put("MediumVioletRed", "#C71585");
        map.put("MidnightBlue", "#191970");
        map.put("MintCream", "#F5FFFA");
        map.put("MistyRose", "#FFE4E1");
        map.put("Moccasin", "#FFE4B5");
        map.put("NavajoWhite", "#FFDEAD");
        map.put("Navy", "#000080");
        map.put("OldLace", "#FDF5E6");
        map.put("Olive", "#808000");
        map.put("OliveDrab", "#6B8E23");
        map.put("Orange", "#FFA500");
        map.put("OrangeRed", "#FF4500");
        map.put("Orchid", "#DA70D6");
        map.put("PaleGoldenrod", "#EEE8AA");
        map.put("PaleGreen", "#98FB98");
        map.put("PaleTurquoise", "#AFEEEE");
        map.put("PaleVioletRed", "#DB7093");
        map.put("PapayaWhip", "#FFEFD5");
        map.put("PeachPuff", "#FFDAB9");
        map.put("Peru", "#CD853F");
        map.put("Pink", "#FFC0CB");
        map.put("Plum", "#DDA0DD");
        map.put("PowderBlue", "#B0E0E6");
        map.put("Purple", "#800080");
        map.put("Red", "#FF0000");
        map.put("RosyBrown", "#BC8F8F");
        map.put("RoyalBlue", "#4169E1");
        map.put("SaddleBrown", "#8B4513");
        map.put("Salmon", "#FA8072");
        map.put("SandyBrown", "#F4A460");
        map.put("SeaGreen", "#2E8B57");
        map.put("Seashell", "#FFF5EE");
        map.put("Sienna", "#A0522D");
        map.put("Silver", "#C0C0C0");
        map.put("SkyBlue", "#87CEEB");
        map.put("SlateBlue", "#6A5ACD");
        map.put("SlateGray", "#708090");
        map.put("Snow", "#FFFAFA");
        map.put("SpringGreen", "#00FF7F");
        map.put("SteelBlue", "#4682B4");
        map.put("Tan", "#D2B48C");
        map.put("Teal", "#008080");
        map.put("Thistle", "#D8BFD8");
        map.put("Tomato", "#FF6347");
        map.put("Turquoise", "#40E0D0");
        map.put("Violet", "#EE82EE");
        map.put("Wheat", "#F5DEB3");
        map.put("White", "#FFFFFF");
        map.put("WhiteSmoke", "#F5F5F5");
        map.put("Yellow", "#FFFF00");
        map.put("YellowGreen", "#9ACD32");
        return map;
    }

    // Function to remove duplicates from an ArrayList
    public static <T> ArrayList<T> removeDuplicates(ArrayList<T> list)
    {
        Set<T> set = new LinkedHashSet<>(list);
        list.clear();
        list.addAll(set);
        return list;
    }

    public static double getOffset(String color1, String color2) {

        // Strip colors from # prefix
        if (color1.contains("#")) color1 = color1.substring(1);
        if (color2.contains("#")) color2 = color2.substring(1);

        // get red/green/blue int values of color 1
        int r1 = Integer.parseInt(color1.substring(0, 2), 16);
        int g1 = Integer.parseInt(color1.substring(2, 4), 16);
        int b1 = Integer.parseInt(color1.substring(4, 6), 16);

        // get red/green/blue int values of color 2
        int r2 = Integer.parseInt(color2.substring(0, 2), 16);
        int g2 = Integer.parseInt(color2.substring(2, 4), 16);
        int b2 = Integer.parseInt(color2.substring(4, 6), 16);

        double r = 255 - Math.abs(r1 - r2);
        double g = 255 - Math.abs(g1 - g2);
        double b = 255 - Math.abs(b1 - b2);

        r /= 255;
        g /= 255;
        b /= 255;

        return (r + g + b) / 3;
    }

    // Accuracy is between 0 to 1
    // less accuracy more precision
    // best Accuracy is 0.2
    public static String[] findSimilars (String color, Double accuracy) {

        ArrayList<String> similars = new ArrayList<String>() ;

            Set<?> set = map.entrySet();
            for (Object o : set) {
                Map.Entry<String, String> mapColor = (Map.Entry<String, String>) o;

                if (getOffset(color, mapColor.getValue()) >= (1 - accuracy))
                    similars.add(mapColor.getKey());
            }

        return removeDuplicates(similars).toArray(new String[0]);
    }

}
