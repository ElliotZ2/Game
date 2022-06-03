import javax.swing.*;
import java.awt.*;

public class ZombieGameGUI {
    Player player;
    JFrame window;
    Container container;
    JPanel playerNamePanel;
    JLabel playerNameLabel;
    JPanel playerHealthPanel;
    JLabel playerHealthLabel;
    JPanel playerHealthBar;
    JPanel playerHungerPanel;
    JLabel playerHungerLabel;
    JPanel playerThirstPanel;
    JLabel playerThirstLabel;
    JPanel playerInfectionPanel;
    JLabel playerInfectionLabel;
    JPanel playerEquippedWeaponPanel;
    JLabel playerEquippedWeaponLabel;
    JPanel middleDivider;
    JPanel enemyNamePanel;
    JLabel enemyNameLabel;
    JPanel enemyHealthPanel;
    JLabel enemyHealthLabel;
    JPanel enemyHealthBar;
    JPanel timePanel;
    JLabel timeLabel;
    Font playerNameFont;
    Font playerHealthFont;
    Font playerStatsFont;
    Font enemyNameFont;
    Color backgroundColor;
    Color panelColor;
    JLabel background;

    public ZombieGameGUI(Player player) {
        this.player = player;
        window = new JFrame();
        container = window.getContentPane();
        playerNamePanel = new JPanel();
        playerNameLabel = new JLabel();
        playerHealthPanel = new JPanel();
        playerHealthLabel = new JLabel();
        playerHealthBar = new JPanel();
        playerNameFont = new Font("Times New Roman", Font.PLAIN, 50);
        playerHealthFont = new Font("Comfortaa", Font.PLAIN, 35);
        playerStatsFont = new Font("Georgia", Font.PLAIN, 30);
        playerHungerPanel= new JPanel();;
        playerHungerLabel= new JLabel();;
        playerThirstPanel= new JPanel();;
        playerThirstLabel= new JLabel();;
        playerInfectionPanel= new JPanel();;
        playerInfectionLabel= new JLabel();;
        playerEquippedWeaponPanel = new JPanel();
        playerEquippedWeaponLabel = new JLabel();
        middleDivider = new JPanel();
        enemyNamePanel = new JPanel();
        enemyNameLabel = new JLabel();
        enemyHealthPanel= new JPanel();
        enemyHealthLabel= new JLabel();
        enemyHealthBar= new JPanel();
        timePanel = new JPanel();
        timeLabel = new JLabel();
        enemyNameFont = new Font("Times New Roman", Font.PLAIN, 40);
        backgroundColor = new Color(15,15,15);
        panelColor = new Color(50,50,85);
        background = new JLabel();
    }

    public void run() {
        window.setSize(1024,576);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().setBackground(backgroundColor);
        window.setLayout(null);
        window.setVisible(true);

        playerNamePanel.setBounds(15,15,450,70);
        playerNamePanel.setBackground(panelColor);
        playerNameLabel.setHorizontalAlignment(JLabel.LEFT);
        playerNameLabel.setText(player.getName());
        playerNameLabel.setForeground(Color.white);
        playerNameLabel.setFont(playerNameFont);
        playerNamePanel.add(playerNameLabel);
        container.add(playerNamePanel);

        playerHealthPanel.setBounds(15,105,200,40);
        playerHealthPanel.setBackground(panelColor);
        playerHealthLabel.setText("HP: 100");
        playerHealthLabel.setForeground(Color.white);
        playerHealthLabel.setFont(playerHealthFont);
        playerHealthPanel.add(playerHealthLabel);
        container.add(playerHealthPanel);//

        playerHealthBar.setBounds(15, 165, 400, 50);
        playerHealthBar.setBackground(Color.green);
        container.add(playerHealthBar);

        playerHungerPanel.setBounds(15,235, 350, 45);
        playerHungerPanel.setBackground(panelColor);
        playerHungerLabel.setText("Hunger: 0");
        playerHungerLabel.setForeground(Color.white);
        playerHungerLabel.setFont(playerStatsFont);
        playerHungerPanel.add(playerHungerLabel);
        container.add(playerHungerPanel);

        playerThirstPanel.setBounds(15,290, 350, 45);
        playerThirstPanel.setBackground(panelColor);
        playerThirstLabel.setText("Thirst: 0");
        playerThirstLabel.setForeground(Color.white);
        playerThirstLabel.setFont(playerStatsFont);
        playerThirstPanel.add(playerThirstLabel);
        container.add(playerThirstPanel);

        playerInfectionPanel.setBounds(15,345, 350, 45);
        playerInfectionPanel.setBackground(panelColor);
        playerInfectionLabel.setText("Infection Level: 0");
        playerInfectionLabel.setForeground(Color.white);
        playerInfectionLabel.setFont(playerStatsFont);
        playerInfectionPanel.add(playerInfectionLabel);
        container.add(playerInfectionPanel);

        playerEquippedWeaponPanel.setBounds(15,415,350,45);
        playerEquippedWeaponPanel.setBackground(panelColor);
        playerEquippedWeaponLabel.setText("Weapon: " + player.getEquippedWeapon().getName());
        playerEquippedWeaponLabel.setForeground(Color.white);
        playerEquippedWeaponLabel.setFont(playerStatsFont);
        playerEquippedWeaponPanel.add(playerEquippedWeaponLabel);
        container.add(playerEquippedWeaponPanel);

        middleDivider.setBounds(window.getWidth() / 2, 25, 5, 480);
        middleDivider.setBackground(Color.white);
        container.add(middleDivider);

        enemyNamePanel.setBounds(((window.getWidth() / 2) + 20), 15,450,70);
        enemyNamePanel.setBackground(panelColor);
        enemyNameLabel.setText("");
        enemyNameLabel.setForeground(Color.white);
        enemyNameLabel.setFont(enemyNameFont);
        enemyNamePanel.add(enemyNameLabel);
        container.add(enemyNamePanel);

        enemyHealthPanel.setBounds((window.getWidth() / 2) + 20, 105, 200, 40);
        enemyHealthPanel.setBackground(panelColor);
        enemyHealthLabel.setText("HP: 0");
        enemyHealthLabel.setForeground(Color.white);
        enemyHealthLabel.setFont(playerHealthFont);
        enemyHealthPanel.add(enemyHealthLabel);
        container.add(enemyHealthPanel);

        enemyHealthBar.setBounds(((window.getWidth() / 2) + 20), 165, 400, 50);
        enemyHealthBar.setBackground(Color.green);
        container.add(enemyHealthBar);

        middleDivider.setVisible(false);
        enemyNamePanel.setVisible(false);
        enemyHealthPanel.setVisible(false);
        enemyHealthBar.setVisible(false);

        timePanel.setBounds(window.getWidth() - 225, 455, 200, 40);
        timePanel.setBackground(backgroundColor);
        timeLabel.setText("Day 1");
        timeLabel.setForeground(Color.white);
        timeLabel.setFont(playerStatsFont);
        timePanel.add(timeLabel);
        container.add(timePanel);

        ImageIcon testImageIcon = new ImageIcon("images/night.jpg");
        Image testImage = testImageIcon.getImage();
        Image newTestImage = testImage.getScaledInstance(window.getWidth(), window.getHeight(), Image.SCALE_FAST);
        testImageIcon = new ImageIcon(newTestImage);
        background.setSize(window.getSize());
        background.setBounds(window.getBounds());
        background.setIcon(testImageIcon);
        background.setVisible(true);
        container.add(background);
        //TODO scale this image everytime the window size changes
    }

    private void setPlayerName(String name) {
        playerNameLabel.setText(name);
    }

    public void setPlayerHealth(int hp) {
        playerHealthBar.setBounds(playerHealthBar.getX(),playerHealthBar.getY(),(hp * 4),playerHealthBar.getHeight());
        if(hp > 50) {
            playerHealthBar.setBackground(Color.green);
        }
        else if(hp > 25) {
            playerHealthBar.setBackground(Color.yellow);
        }
        else{
            playerHealthBar.setBackground(Color.red);
        }
        playerHealthLabel.setText("HP: " + hp);
    }

    public void setPlayerHunger(int hunger) {
        playerHungerLabel.setText("Hunger: " + hunger);
    }

    public void setPlayerThirst(int thirst) {
        playerThirstLabel.setText("Thirst: " + thirst);
    }

    public void setPlayerInfectionLevel(int infectionLevel) {
        playerInfectionLabel.setText("Infection Level: " + infectionLevel);
    }

    public void setAllPlayerStats() {
        setPlayerHealth(player.getHealth());
        setPlayerHunger(player.getHunger());
        setPlayerThirst(player.getThirst());
        setPlayerInfectionLevel(player.getInfectionLevel());
        playerEquippedWeaponLabel.setText("Weapon: " + player.getEquippedWeapon().getName());
    }

    public void setEnemyName(String enemyName) {
        enemyNameLabel.setText(enemyName);
    }

    public void setEnemyHealth(int hp, int maxHP) {
        enemyHealthLabel.setText("HP: " + hp);
        int width = (int) ((((double) hp) / maxHP) * 400);
        enemyHealthBar.setBounds(enemyHealthBar.getX(),enemyHealthBar.getY(),width,enemyHealthBar.getHeight());
        int halfOfHP = maxHP / 2;
        if(hp > halfOfHP) {
            enemyHealthBar.setBackground(Color.green);
        }
        else if(hp > halfOfHP / 2) {
            enemyHealthBar.setBackground(Color.yellow);
        }
        else{
            enemyHealthBar.setBackground(Color.red);
        }
    }

    public void showEnemyData() {
        middleDivider.setVisible(true);
        enemyNamePanel.setVisible(true);
        enemyHealthPanel.setVisible(true);
        enemyHealthBar.setVisible(true);
    }

    public void hideEnemyData() {
        middleDivider.setVisible(false);
        enemyNamePanel.setVisible(false);
        enemyHealthPanel.setVisible(false);
        enemyHealthBar.setVisible(false);
    }

    public void setTime(String time) {
        timeLabel.setText(time);
    }
}
