package org.example.game.cli;

public interface CombatUI {
    enum ActionType { ATTACK, USE_ITEM, FLEE, HELP, UNKNOWN }

    record Action(ActionType type, Integer oneBasedItemIndex) {
        public static Action attack() {
            return new Action(ActionType.ATTACK, null);
        }

        public static Action use(int idx) {
            return new Action(ActionType.USE_ITEM, idx);
        }

        public static Action flee() {
            return new Action(ActionType.FLEE, null);
        }

        public static Action help() {
            return new Action(ActionType.HELP, null);
        }

        public static Action unknown() {
            return new Action(ActionType.UNKNOWN, null);
        }
    }

    // Output
    void showIntro(String enemyName, int playerHp, int playerMaxHp, int enemyHp, int enemyMaxHp);
    void showStatus(int playerHp, int playerMaxHp, int enemyHp, int enemyMaxHp);
    void playerAttackFeedback(String enemyName, int damage);
    void enemyAttackFeedback(String enemyName, int damage);
    void usedItemFeedback(String itemName, boolean success);
    void showMessage(String text);

    // Input
    Action readAction();
}
