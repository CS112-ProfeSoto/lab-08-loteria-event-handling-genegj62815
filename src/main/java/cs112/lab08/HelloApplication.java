package cs112.lab08;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;

public class HelloApplication extends Application {

    private static final LoteriaCard[] LOTERIA_CARDS = {
            new LoteriaCard("Las matematicas", "1.png", 1),
            new LoteriaCard("Las ciencias", "2.png", 2),
            new LoteriaCard("La Tecnología", "8.png", 8),
            new LoteriaCard("La ingeniería", "9.png", 9),
    };

    private int cardsDrawn = 0;
    private LoteriaCard currentCard;
    private ImageView cardImageView;
    private Label messageLabel;
    private Button drawCardButton;
    private ProgressBar gameProgressBar;

    @Override
    public void start(Stage stage) throws IOException {
        // Load FXML and get root
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        VBox root = fxmlLoader.load();

        // Initialize GUI components
        cardImageView = new ImageView();
        cardImageView.setFitWidth(200);
        cardImageView.setFitHeight(200);
        currentCard = new LoteriaCard(); // Start with EChALE STEM logo
        cardImageView.setImage(currentCard.getImage());

        Label titleLabel = new Label("EChALE STEM Loteria");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: #333333;");

        messageLabel = new Label("Welcome to EChALE STEM Loteria!");
        messageLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #666666;");

        drawCardButton = new Button("Draw Card");
        drawCardButton.setStyle("-fx-background-color: #0078d4; -fx-text-fill: white; -fx-font-size: 14px;");
        drawCardButton.setOnAction(e -> {
            if (cardsDrawn < LOTERIA_CARDS.length) {
                Random rand = new Random();
                int index;
                do {
                    index = rand.nextInt(LOTERIA_CARDS.length);
                } while (LOTERIA_CARDS[index].getCardNum() == 0); // Skip used cards

                currentCard = new LoteriaCard(LOTERIA_CARDS[index]);
                cardImageView.setImage(currentCard.getImage());
                messageLabel.setText("Current Card: " + currentCard.getCardName());
                LOTERIA_CARDS[index] = new LoteriaCard(); // Mark as used
                cardsDrawn++;
                gameProgressBar.setProgress((double) cardsDrawn / LOTERIA_CARDS.length);
            }
            if (cardsDrawn >= LOTERIA_CARDS.length) {
                gameOver();
            }
        });

        gameProgressBar = new ProgressBar(0.0);
        gameProgressBar.setStyle("-fx-accent: #0078d4;");

        // Layout
        VBox vBox = new VBox(10, titleLabel, cardImageView, messageLabel, drawCardButton, gameProgressBar);
        vBox.setStyle("-fx-padding: 10; -fx-alignment: center;");

        Scene scene = new Scene(vBox, 350, 500);
        stage.setTitle("EChALE STEM Loteria");
        stage.setScene(scene);
        stage.show();
    }

    private void gameOver() {
        cardImageView.setImage(new LoteriaCard().getImage()); // EChALE STEM logo
        messageLabel.setText("GAME OVER. No more cards! Exit and run program again to reset ^_^");
        drawCardButton.setDisable(true);
        gameProgressBar.setStyle("-fx-accent: #ff0000;");
    }

    public static void main(String[] args) {
        launch();
    }
}
