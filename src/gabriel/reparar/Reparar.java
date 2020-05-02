package gabriel.reparar;

import nuvemplugins.solaryeconomy.app.SolaryEconomy;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Reparar implements Listener, CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {

        Player p = (Player) sender;

        Inventory inv = Bukkit.createInventory(p, 3*9, "Reparar:");

        ItemStack c = new ItemStack(Material.WOOL, 1, (short) 13);
        ItemMeta cm = c.getItemMeta();
        cm.setDisplayName("§aConfirmar reparação.");
        List<String> cl = new ArrayList<>();
        cl.add("");
        cl.add("§7Custo:§f 10.000§7 coins");
        cl.add("§7Adiquira já o seu §bMVP§7 ou superior");
        cl.add("§7para reparar de &f&lGRAÇA&7.");
        cm.setLore(cl);
        c.setItemMeta(cm);

        ItemStack n = new ItemStack(Material.WOOL, 1, (short) 14);
        ItemMeta nm = n.getItemMeta();
        nm.setDisplayName("§cNegar reparação.");
        List<String> nl = new ArrayList<>();
        nl.add("§7");
        nl.add("§7Clique aqui para não reparar.");
        nm.setLore(nl);
        n.setItemMeta(nm);

        inv.setItem(11, c);
        inv.setItem(15, n);

        if(p.getItemInHand().getType() == Material.AIR){

            inv.setItem(13, new ItemStack(Material.BARRIER));

        }else{

            inv.setItem(13, p.getItemInHand());

        }

        ItemStack mao = p.getItemInHand();

        p.openInventory(inv);

        return false;
    }

    @EventHandler
    public void event(InventoryClickEvent e){

        Player p = (Player) e.getWhoClicked();

        if(e.getInventory().getTitle() == "Reparar:"){

            e.setCancelled(true);

            if(e.getCurrentItem().getItemMeta().getDisplayName() == "§aConfirmar reparação."){

                ItemStack hand = p.getItemInHand();

                if(hand.getType() == Material.AIR){

                    p.sendMessage("§cVocê não está segurando um item.");
                    p.playSound(p.getLocation(), Sound.ANVIL_BREAK, 1 ,1);
                    p.closeInventory();

                }else{

                    if(hand.getType().getMaxDurability() == 0){

                        p.sendMessage("§cEste não é um item reparável.");
                        p.closeInventory();
                        p.playSound(p.getLocation(), Sound.ANVIL_BREAK, 1 ,1);

                    }else{

                        if(hand.getDurability() == 0){

                            p.sendMessage("§cO seu item já se encontra reparado.");
                            p.playSound(p.getLocation(), Sound.ANVIL_BREAK, 1 ,1);
                            p.closeInventory();

                        }else{

                            if(p.hasPermission("reparar.free")){

                                hand.setDurability((short) 0);
                                p.sendMessage("§aVocê reparou o seu item.");
                                p.playSound(p.getLocation(), Sound.ANVIL_USE, 1 ,1);

                            }else{

                                if(SolaryEconomy.economia.hasBalance(p.getName(), BigDecimal.valueOf(10000))){

                                    hand.setDurability((short) 0);
                                    p.sendMessage("§aVocê pagou pela reparação.");
                                    p.playSound(p.getLocation(), Sound.ANVIL_USE, 1 ,1);
                                    SolaryEconomy.economia.substractBalance(p.getName(), BigDecimal.valueOf(10000));

                                }else{

                                    p.sendMessage("§cVocê não tem dinheiro suficiente.");
                                    p.playSound(p.getLocation(), Sound.ANVIL_BREAK, 1 ,1);
                                    p.closeInventory();

                                }

                            }



                        }

                    }

                }



            }

            if(e.getCurrentItem().getItemMeta().getDisplayName() == "§cNegar reparação."){

                p.sendMessage("§cVocê negou a reparação.");
                p.closeInventory();

            }

        }

    }

}
