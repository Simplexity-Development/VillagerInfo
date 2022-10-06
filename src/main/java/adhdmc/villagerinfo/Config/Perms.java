package adhdmc.villagerinfo.Config;

import org.bukkit.permissions.Permission;

public enum Perms {
    TOGGLE("villagerinfo.toggle"),
    RELOAD("villagerinfo.reload"),
    USE("villagerinfo.use");

    String perm;
    Perms(String perm) {
        this.perm = perm;
    }
    public String getPerm() { return this.perm; }
}
