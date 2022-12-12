package com.badbones69.crazycrates.support.structures;

import com.badbones69.crazycrates.CrazyCrates;
import com.google.common.collect.Lists;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.structure.Mirror;
import org.bukkit.block.structure.StructureRotation;
import org.bukkit.structure.StructureManager;
import org.bukkit.util.BlockVector;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StructureHandler {

    private final File file;

    private final CrazyCrates plugin = CrazyCrates.getPlugin();

    public StructureHandler(File file) {
        this.file = file;
    }

    private final List<Block> structureBlocks = new ArrayList<>();

    private final List<Block> preStructureBlocks = new ArrayList<>();

    private StructureManager getStructureManager() {
        return plugin.getServer().getStructureManager();
    }

    private BlockVector getStructureSize() {
        try {
            return getStructureManager().loadStructure(file).getSize();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void pasteStructure(Location location) {
        try {
            getNearbyBlocks(location);

            getStructureManager().loadStructure(file).place(location, false, StructureRotation.NONE, Mirror.NONE, 0, 1F, new Random());

            getBlocks(true, location);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeStructure() {
        structureBlocks.forEach(block -> {
            block.getLocation().getBlock().setType(Material.AIR, false);
        });
    }

    public void saveStructure(Location location) {

    }

    public double getStructureX() {
        try {
            return getStructureSize().getX();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public double getStructureZ() {
        try {
            return getStructureSize().getZ();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    private void getBlocks(Boolean getStructureBlocks, Location location) {
        for (int x = 0; x < getStructureSize().getX(); x++) {
            for (int y = 0; y < getStructureSize().getY(); y++) {
                for (int z = 0; z < getStructureSize().getZ(); z++) {

                    Block relativeLocation = location.getBlock().getRelative(x, y, z);

                    if (getStructureBlocks) {
                        structureBlocks.add(relativeLocation);

                        structureBlocks.forEach(block -> block.getState().update());

                        return;
                    }

                    preStructureBlocks.add(relativeLocation);
                }
            }
        }
    }

    public List<Block> getNearbyBlocks(Location location) {
        getBlocks(false, location);

        return preStructureBlocks;
    }

    public List<Material> getBlockBlackList() {
        return Lists.newArrayList(Material.ACACIA_SIGN, Material.BIRCH_SIGN, Material.DARK_OAK_SIGN, Material.JUNGLE_SIGN, Material.OAK_SIGN,
                Material.SPRUCE_SIGN, Material.ACACIA_WALL_SIGN, Material.BIRCH_WALL_SIGN, Material.DARK_OAK_WALL_SIGN, Material.JUNGLE_WALL_SIGN, Material.OAK_WALL_SIGN,
                Material.SPRUCE_WALL_SIGN,Material.STONE_BUTTON,Material.BIRCH_BUTTON,Material.ACACIA_BUTTON,Material.DARK_OAK_BUTTON, Material.JUNGLE_BUTTON, Material.SPRUCE_BUTTON);
    }
}