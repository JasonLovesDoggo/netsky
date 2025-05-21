public class GameMain {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                GameFrame gameFrame = new GameFrame("My Game");
                gameFrame.setVisible(true);
            }
        });
    }
}

