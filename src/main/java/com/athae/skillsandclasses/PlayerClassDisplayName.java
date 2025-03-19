public enum PlayerClass {
    WARRIOR, MAGE, ROGUE;

    public String getDisplayName() {
        return switch (this) {
            case WARRIOR -> "Warrior";
            case MAGE -> "Mage";
            case ROGUE -> "Rogue";
        };
    }
}