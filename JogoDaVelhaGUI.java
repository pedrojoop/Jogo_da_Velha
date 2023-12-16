import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.*;

/**
 * @author pmpedrolima@gmail.com
 */
public class JogoDaVelhaGUI extends JFrame {

  private JButton[][] buttons;
  private char currentPlayer;

  public JogoDaVelhaGUI() {
    super("Jogo da Velha");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(300, 300);
    setLocationRelativeTo(null);

    buttons = new JButton[3][3];
    currentPlayer = 'X';

    initializeButtons();
  }

  private void initializeButtons() {
    setLayout(new GridLayout(3, 3));

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        buttons[i][j] = new JButton("");
        buttons[i][j].setFont(new Font("Arial", Font.PLAIN, 40));
        buttons[i][j].setFocusPainted(false);
        buttons[i][j].addActionListener(new ButtonClickListener(i, j));
        add(buttons[i][j]);
      }
    }
  }

  private class ButtonClickListener implements ActionListener {

    private int row;
    private int col;

    public ButtonClickListener(int row, int col) {
      this.row = row;
      this.col = col;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      if (buttons[row][col].getText().equals("")) {
        buttons[row][col].setText(Character.toString(currentPlayer));
        if (checkWinner()) {
          JOptionPane.showMessageDialog(
            JogoDaVelhaGUI.this,
            "Jogador " + currentPlayer + " venceu!"
          );
          resetGame();
        } else if (isBoardFull()) {
          JOptionPane.showMessageDialog(JogoDaVelhaGUI.this, "Empate!");
          resetGame();
        } else {
          switchPlayer();
          playComputerMove();
        }
      }
    }
  }

  private void switchPlayer() {
    currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
  }

  private void playComputerMove() {
    // Lógica simples: Escolher um espaço vazio aleatoriamente
    Random random = new Random();
    int emptySpaces = 0;
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        if (buttons[i][j].getText().equals("")) {
          emptySpaces++;
        }
      }
    }

    if (emptySpaces > 0) {
      int randomIndex = random.nextInt(emptySpaces);
      emptySpaces = 0;

      outerLoop:for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 3; j++) {
          if (buttons[i][j].getText().equals("")) {
            if (emptySpaces == randomIndex) {
              buttons[i][j].setText(Character.toString(currentPlayer));
              break outerLoop;
            } else {
              emptySpaces++;
            }
          }
        }
      }

      if (checkWinner()) {
        JOptionPane.showMessageDialog(
          this,
          "Jogador " + currentPlayer + " venceu!"
        );
        resetGame();
      } else if (isBoardFull()) {
        JOptionPane.showMessageDialog(this, "Empate!");
        resetGame();
      }

      switchPlayer();
    }
  }

  private boolean checkWinner() {
    // Verificar linhas
    for (int i = 0; i < 3; i++) {
      if (
        buttons[i][0].getText().equals(Character.toString(currentPlayer)) &&
        buttons[i][1].getText().equals(Character.toString(currentPlayer)) &&
        buttons[i][2].getText().equals(Character.toString(currentPlayer))
      ) {
        return true;
      }
    }

    // Verificar colunas
    for (int i = 0; i < 3; i++) {
      if (
        buttons[0][i].getText().equals(Character.toString(currentPlayer)) &&
        buttons[1][i].getText().equals(Character.toString(currentPlayer)) &&
        buttons[2][i].getText().equals(Character.toString(currentPlayer))
      ) {
        return true;
      }
    }

    // Verificar diagonal principal
    if (
      buttons[0][0].getText().equals(Character.toString(currentPlayer)) &&
      buttons[1][1].getText().equals(Character.toString(currentPlayer)) &&
      buttons[2][2].getText().equals(Character.toString(currentPlayer))
    ) {
      return true;
    }

    // Verificar diagonal secundária
    if (
      buttons[0][2].getText().equals(Character.toString(currentPlayer)) &&
      buttons[1][1].getText().equals(Character.toString(currentPlayer)) &&
      buttons[2][0].getText().equals(Character.toString(currentPlayer))
    ) {
      return true;
    }

    return false;
  }

  private boolean isBoardFull() {
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        if (buttons[i][j].getText().equals("")) {
          return false;
        }
      }
    }
    return true;
  }

  private void resetGame() {
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        buttons[i][j].setText("");
      }
    }
    currentPlayer = 'X';

    // Se o jogador da máquina é 'O', faça o movimento inicial
    if (currentPlayer == 'O') {
      playComputerMove();
    }
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      JogoDaVelhaGUI jogoDaVelha = new JogoDaVelhaGUI();
      jogoDaVelha.setVisible(true);
    });
  }
}
