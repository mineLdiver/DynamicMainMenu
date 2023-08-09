package net.mine_diver.dynamicmainmenu;

import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.registry.ModID;

public class DynamicMainMenu {

    public static final ModID NAMESPACE = ModID.of("dynamicmainmenu");

    // Identifiers
    public static final Identifier MODULAR = NAMESPACE.id("mainmenu.modular");

    // Config
    public static boolean
            coloredTransition,
            flowingBackground = true,
            glowingBackground = true;
    public static int
            transitionColorRed,
            transitionColorGreen,
            transitionColorBlue;

    // Utility fields
    public static boolean captureSoundId;
    public static String musicId;
    public static long musicStartTimestamp;

//    @EventListener
//    private static void init(InitEvent event) {
//        CONFIG.load();
//
//        Category general = CONFIG.getCategory("General");
//        Property property = general.getProperty("ColoredTransition", false);
//        coloredTransition = property.getBooleanValue();
//        property.setComment("Instead of directly transitioning the background into the world, first transitions into a solid color (black by default) and then into the world.");
//        property = general.getProperty("FlowingBackground", true);
//        flowingBackground = property.getBooleanValue();
//        property.setComment("Makes the dirt background move like in indev versions.");
//        property = general.getProperty("GlowingBackground", true);
//        glowingBackground = property.getBooleanValue();
//        property.setComment("Makes the dirt background change its lighting over time.");
//
//        Category transitionColor = CONFIG.getCategory("Transition Color");
//        String comment = "Defines %color% from 0 to 255 (0 - no %color%, 255 - full %color%)";
//        property = transitionColor.getProperty("Red", 0);
//        transitionColorRed = property.getIntValue();
//        property.setComment(comment.replace("%color%", "red"));
//        property = transitionColor.getProperty("Green", 0);
//        transitionColorGreen = property.getIntValue();
//        property.setComment(comment.replace("%color%", "green"));
//        property = transitionColor.getProperty("Blue", 0);
//        transitionColorBlue = property.getIntValue();
//        property.setComment(comment.replace("%color%", "blue"));
//
//        CONFIG.save();
//    }
}
