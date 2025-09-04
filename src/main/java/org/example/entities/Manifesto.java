package org.example.entities;

public class Manifesto extends Item{
    private final String winMessage;

    public Manifesto(int x, int y) {
        super("Manifesto", x, y);
        this.winMessage = "Wow! You have uncovered the Farmageddon Manifesto. " +
                "The truth is milkier than you ever imagined. The game is over—but the legend begins...";
    }

    public String getWinMessage() {
        return winMessage;
    }

    @Override
    public void use(Player player) {
        System.out.println(winMessage);
    }
}
