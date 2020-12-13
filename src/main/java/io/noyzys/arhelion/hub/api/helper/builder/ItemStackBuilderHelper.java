package dev.noyzys.arhelion.hub.api.helper.builder;

import dev.noyzys.arhelion.hub.api.helper.message.StringMessageHelper;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

/**
 * @author: NoyZys on 20:02, 19.10.2019
 **/
public final class ItemStackBuilderHelper {

    private final ItemStack itemStack;
    private final ItemMeta itemMeta;

    public ItemStackBuilderHelper(Material material) {
        this.itemStack = new ItemStack(material);
        this.itemMeta = itemStack.getItemMeta();
    }

    public ItemStackBuilderHelper(Material material, int stack) {
        this.itemStack = new ItemStack(material, stack);
        this.itemMeta = itemStack.getItemMeta();
    }

    public ItemStackBuilderHelper setName(String name, boolean color) {
        this.itemMeta.setDisplayName(color ? StringMessageHelper
                .colored(name) : name
        );

        return this;
    }

    public ItemStackBuilderHelper setLore(List<String> lore) {
        this.itemMeta.setLore(StringMessageHelper.colored(lore));
        this.itemStack.setItemMeta(this.itemMeta);
        return this;
    }

    public ItemStackBuilderHelper addEnchantment(Enchantment enchantment, int level) {
        this.itemMeta.addEnchant(enchantment, level, true);
        this.itemStack.setItemMeta(this.itemMeta);
        return this;
    }

    public ItemStack toBuild() {
        return this.itemStack;
    }

    public ItemMeta getItemMeta() {
        return this.itemMeta;
    }
}
