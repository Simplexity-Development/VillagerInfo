package adhdmc.villagerinfo.Config;

public enum Perms {
    TOGGLE("villagerinfo.toggle"),
    RELOAD("villagerinfo.reload"),
    USE("villagerinfo.use");

    final String VIPerm;
    Perms(String perm) {
        this.VIPerm = perm;
    }
    public String getVIPerm() { return this.VIPerm; }
}
