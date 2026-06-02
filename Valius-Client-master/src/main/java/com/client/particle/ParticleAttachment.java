package com.client.particle;

import java.util.HashMap;
import java.util.Map;

public class ParticleAttachment {

    private static final Map<Integer, int[][]> attachments = new HashMap<>();

    public static int[][] getAttachments(int model) {
        return attachments.get(model);
    }

    static {
    	//max capes
       // attachments.put(29616, new int[][]{{272, 0}, {269, 0}, {49, 0}, {45, 0}, {37, 0}, {16, 0}, {17, 0}, {5, 0}, {41, 0}, {283, 0}, {310, 0}, {315, 0}});
       // attachments.put(29624, new int[][]{{49, 0}, {45, 0}, {37, 0}, {16, 0}, {17, 0}, {5, 0}, {41, 0}, {283, 0}, {310, 0}, {315, 0}});
        //infernal firecape
        attachments.put(33114, new int[][]{{188, 1}, {187, 1}, {40, 1}, {47, 1}, {39, 1}, {54, 1}, {17, 1}, {5, 1}, {19, 1}, {229, 1}, {232, 1}, {233, 1}, {242, 1}, {247, 1}});
        attachments.put(33102, new int[][]{{206, 1}, {202, 1}, {40, 1}, {17, 1}, {5, 1}, {36, 1}, {55, 1}, {18, 1}, {19, 1}, {255, 1}, {257, 1}, {266, 1},{271, 1}});
        //barrows sword
        attachments.put(50010, new int[][]{{93, 2}, {3, 2}, {57, 2}});
        //agility master cape
        attachments.put(50031, new int[][]{{151, 3}, {148, 3}, {147, 3}, {146, 3}, {145, 3}, {169, 3}, {168, 3}, {167, 3}, {166, 3}, {121, 3} });
        //attack master cape
        attachments.put(50033, new int[][]{{151, 4}, {148, 4}, {147, 4}, {146, 4}, {145, 4}, {169, 4}, {168, 4}, {167, 4}, {166, 4}, {121, 4} });
        //construction master cape
        attachments.put(50035, new int[][]{{151, 5}, {148, 5}, {147, 5}, {146, 5}, {145, 5}, {169, 5}, {168, 5}, {167, 5}, {166, 5}, {121, 5} });
      //cooking master cape
        attachments.put(50037, new int[][]{{151, 6}, {148, 6}, {147, 6}, {146, 6}, {145, 6}, {169, 6}, {168, 6}, {167, 6}, {166, 6}, {121, 6} });
      //crafting master cape
        attachments.put(50039, new int[][]{{151, 7}, {148, 7}, {147, 7}, {146, 7}, {145, 7}, {169, 7}, {168, 7}, {167, 7}, {166, 7}, {121, 7} });
      //defence master cape
        attachments.put(50041, new int[][]{{151, 8}, {148, 8}, {147, 8}, {146, 8}, {145, 8}, {169, 8}, {168, 8}, {167, 8}, {166, 8}, {121, 8} });
        //farming master cape
        attachments.put(50043, new int[][]{{151, 9}, {148, 9}, {147, 9}, {146, 9}, {145, 9}, {169, 9}, {168, 9}, {167, 9}, {166, 9}, {121, 9} });
        //firemaking master cape
        attachments.put(50045, new int[][]{{151, 10}, {148, 10}, {147, 10}, {146, 10}, {145, 10}, {169, 10}, {168, 10}, {167, 10}, {166, 10}, {121, 10} });
        //fishing master cape
        attachments.put(50047, new int[][]{{151, 11}, {148, 11}, {147, 11}, {146, 11}, {145, 11}, {169, 11}, {168, 11}, {167, 11}, {166, 11}, {121, 11} });
      //fletching master cape
        attachments.put(50049, new int[][]{{151, 12}, {148, 12}, {147, 12}, {146, 12}, {145, 12}, {169, 12}, {168, 12}, {167, 12}, {166, 12}, {121, 12} });
        //herblore master cape
        attachments.put(50051, new int[][]{{151, 13}, {148, 13}, {147, 13}, {146, 13}, {145, 13}, {169, 13}, {168, 13}, {167, 13}, {166, 13}, {121, 13} });
      //hitpoints master cape
        attachments.put(50053, new int[][]{{151, 14}, {148, 14}, {147, 14}, {146, 14}, {145, 14}, {169, 14}, {168, 14}, {167, 14}, {166, 14}, {121, 14} });
      //hunter master cape
        attachments.put(50055, new int[][]{{151, 15}, {148, 15}, {147, 15}, {146, 15}, {145, 15}, {169, 15}, {168, 15}, {167, 15}, {166, 15}, {121, 15} });
      //magic master cape
        attachments.put(50057, new int[][]{{151, 16}, {148, 16}, {147, 16}, {146, 16}, {145, 16}, {169, 16}, {168, 16}, {167, 16}, {166, 16}, {121, 16} });
      //mining master cape
        attachments.put(50059, new int[][]{{151, 17}, {148, 17}, {147, 17}, {146, 17}, {145, 17}, {169, 17}, {168, 17}, {167, 17}, {166, 17}, {121, 17} });
        //prayer master cape
        attachments.put(50061, new int[][]{{151, 18}, {148, 18}, {147, 18}, {146, 18}, {145, 18}, {169, 18}, {168, 18}, {167, 18}, {166, 18}, {121, 18} });
      //range master cape
        attachments.put(50063, new int[][]{{151, 19}, {148, 19}, {147, 19}, {146, 19}, {145, 19}, {169, 19}, {168, 19}, {167, 19}, {166, 19}, {121, 19} });
      //runecrafting master cape
        attachments.put(50065, new int[][]{{151, 20}, {148, 20}, {147, 20}, {146, 20}, {145, 20}, {169, 20}, {168, 20}, {167, 20}, {166, 20}, {121, 20} });
      //Slayer master cape
        attachments.put(50067, new int[][]{{151, 21}, {148, 21}, {147, 21}, {146, 21}, {145, 21}, {169, 21}, {168, 21}, {167, 21}, {166, 21}, {121, 21} });
      //Smithing master cape
        attachments.put(50069, new int[][]{{151, 22}, {148, 22}, {147, 22}, {146, 22}, {145, 22}, {169, 22}, {168, 22}, {167, 22}, {166, 22}, {121, 22} });
      //Strength master cape
        attachments.put(50071, new int[][]{{151, 23}, {148, 23}, {147, 23}, {146, 23}, {145, 23}, {169, 23}, {168, 23}, {167, 23}, {166, 23}, {121, 23} });
      //Thieving master cape
        attachments.put(50073, new int[][]{{151, 24}, {148, 24}, {147, 24}, {146, 24}, {145, 24}, {169, 24}, {168, 24}, {167, 24}, {166, 24}, {121, 24} });
      //Woodcutting master cape
        attachments.put(50075, new int[][]{{151, 25}, {148, 25}, {147, 25}, {146, 25}, {145, 25}, {169, 25}, {168, 25}, {167, 25}, {166, 25}, {121, 25} });
      //Abyssal scythe
        attachments.put(50080, new int[][]{{396, 26} });
        //goliath gloves
        attachments.put(50100, new int[][]{{18, 27}, {47, 27} });
        attachments.put(50101, new int[][]{{18, 27}, {47, 27} });
        attachments.put(50102, new int[][]{{18, 27}, {47, 27} });
        attachments.put(50103, new int[][]{{18, 27}, {47, 27} });
        attachments.put(50104, new int[][]{{18, 27}, {47, 27} });
        attachments.put(50105, new int[][]{{18, 27}, {47, 27} });
        attachments.put(50106, new int[][]{{18, 27}, {47, 27} });
        attachments.put(50107, new int[][]{{18, 27}, {47, 27} });
        //tekton longsword
        attachments.put(62681, new int[][]{{76, 27}, {77, 27}, {78, 27}, {79, 27}});
        //swift gloves
        attachments.put(62657, new int[][]{{18, 29}, {47, 29} });
        attachments.put(62658, new int[][]{{18, 29}, {47, 29} });
        attachments.put(62659, new int[][]{{18, 29}, {47, 29} });
        attachments.put(62660, new int[][]{{18, 29}, {47, 29} });
        attachments.put(62661, new int[][]{{18, 29}, {47, 29} });
        attachments.put(62662, new int[][]{{18, 29}, {47, 29} });
        attachments.put(62663, new int[][]{{18, 29}, {47, 29} });
        attachments.put(62664, new int[][]{{18, 29}, {47, 29} });
        //spellcaster gloves
        attachments.put(62665, new int[][]{{18, 30}, {47, 30} });
        attachments.put(62666, new int[][]{{18, 30}, {47, 30} });
        attachments.put(62667, new int[][]{{18, 30}, {47, 30} });
        attachments.put(62668, new int[][]{{18, 30}, {47, 30} });
        attachments.put(62669, new int[][]{{18, 30}, {47, 30} });
        attachments.put(62670, new int[][]{{18, 30}, {47, 30} });
        attachments.put(62671, new int[][]{{18, 30}, {47, 30} });
        attachments.put(62672, new int[][]{{18, 30}, {47, 30} });

     //White Longsword //141 134 //138, 137 //111, 108
        attachments.put(53092, new int[][]{{141, 28}, {134, 28} });
        
        //custom item npc
        attachments.put(60670, new int[][] { {1013, 31} });
        
        //tok-haar
        attachments.put(52072, new int[][] { {137, 32} });
        
        //blood helmet
        attachments.put(60926, new int[][] { {165, 33}, { 166, 33} });
        attachments.put(60923, new int[][] { {165, 33}, { 166, 33} });
        
        //COMP CAPES
        attachments.put(65316, new int[][] { {151, 25}, {148, 25}, {147, 25}, {146, 25}, {145, 25}, {169, 25}, {168, 25}, {167, 25}, {166, 25}, {121, 25} });
        attachments.put(65328, new int[][] { {151, 35}, {148, 35}, {147, 35}, {146, 35}, {145, 35}, {169, 35}, {168, 35}, {167, 35}, {166, 35}, {121, 35} });
    }
}
