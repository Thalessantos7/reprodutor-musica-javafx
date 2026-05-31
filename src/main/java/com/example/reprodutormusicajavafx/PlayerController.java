package com.example.reprodutormusicajavafx;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import java.io.File;

public class PlayerController {

    @FXML private Label labelTitulo;
    @FXML private Label labelArtista;
    @FXML private ListView<String> listaMusicas;
    @FXML private Slider sliderProgresso;
    @FXML private Slider sliderVolume;
    @FXML private Button btnPlay;
    @FXML private Button btnStop;
    @FXML private Button btnAnterior;
    @FXML private Button btnProxima;
    @FXML private Button btnAdicionar;
    @FXML private Button btnRemover;

    private Biblioteca biblioteca = new Biblioteca();
    private MediaPlayer player;
    private int indiceMusicaAtual = 0;

    @FXML
    public void initialize() {

        sliderVolume.setValue(0.5);

        listaMusicas.setOnMouseClicked(event -> {

            int index = listaMusicas.getSelectionModel().getSelectedIndex();

            if (index >= 0) {

                indiceMusicaAtual = index;
                carregarMusica(biblioteca.listarMusicas().get(index));

            }

        });

    }

    @FXML
    public void play() {

        if (player != null) {

            if (player.getStatus() == MediaPlayer.Status.PLAYING) {

                player.pause();
                btnPlay.setText("▶ Play");

            } else {

                player.play();
                btnPlay.setText("⏸ Pause");

            }

        }

    }

    @FXML
    public void stop() {

        if (player != null) {

            player.stop();
            btnPlay.setText("▶ Play");

        }

    }

    @FXML
    public void proxima() {

        try {

            if (indiceMusicaAtual < biblioteca.listarMusicas().size() - 1) {

                indiceMusicaAtual++;
                carregarMusica(biblioteca.listarMusicas().get(indiceMusicaAtual));
                listaMusicas.getSelectionModel().select(indiceMusicaAtual);

            } else {

                mostrarAlerta("Não há próxima música.");

            }

        } catch (IllegalStateException e) {

            mostrarAlerta("A biblioteca está vazia.");

        }

    }

    @FXML
    public void anterior() {

        try {

            if (indiceMusicaAtual > 0) {

                indiceMusicaAtual--;
                carregarMusica(biblioteca.listarMusicas().get(indiceMusicaAtual));
                listaMusicas.getSelectionModel().select(indiceMusicaAtual);

            } else {

                mostrarAlerta("Não há música anterior.");

            }

        } catch (IllegalStateException e) {

            mostrarAlerta("A biblioteca está vazia.");

        }

    }

    @FXML
    public void adicionarMusica() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecionar Música");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Arquivos de Áudio", "*.mp3", "*.wav")
        );

        File arquivo = fileChooser.showOpenDialog(null);

        if (arquivo != null) {

            try {

                Musica musica = new Musica(
                        arquivo.getName(),
                        "Desconhecido",
                        arquivo.toURI().toString(),
                        Duration.ZERO
                );

                biblioteca.adicionarMusica(musica);
                listaMusicas.getItems().add(musica.getTitulo());

                if (biblioteca.listarMusicas().size() == 1) {

                    indiceMusicaAtual = 0;
                    carregarMusica(musica);

                }

            } catch (IllegalArgumentException e) {

                mostrarAlerta(e.getMessage());

            }

        }

    }

    @FXML
    public void removerMusica() {

        String selecionada = listaMusicas.getSelectionModel().getSelectedItem();

        if (selecionada != null) {

            try {

                biblioteca.removerMusica(selecionada);
                listaMusicas.getItems().remove(selecionada);

                stop();

            } catch (IllegalArgumentException e) {

                mostrarAlerta(e.getMessage());

            }

        } else {

            mostrarAlerta("Selecione uma música para remover.");

        }

    }

    private void carregarMusica(Musica musica) {

        if (player != null) {

            player.stop();

        }

        Media media = new Media(musica.getCaminhoArquivo());
        player = new MediaPlayer(media);
        player.volumeProperty().bind(sliderVolume.valueProperty());

        player.currentTimeProperty().addListener((obs, antigo, novo) -> {

            if (!sliderProgresso.isValueChanging()) {

                double progresso = novo.toSeconds() /
                        player.getTotalDuration().toSeconds() * 100;

                sliderProgresso.setValue(progresso);

            }

        });

        sliderProgresso.valueChangingProperty().addListener((obs, antigo, novo) -> {

            if (!novo) {

                double posicao = sliderProgresso.getValue() / 100 *
                        player.getTotalDuration().toSeconds();

                player.seek(Duration.seconds(posicao));
            }

        });

        player.play();
        btnPlay.setText("⏸ Pause");
        labelTitulo.setText(musica.getTitulo());
        labelArtista.setText(musica.getArtista());

    }

    private void mostrarAlerta(String mensagem) {

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Atenção");
        alert.setContentText(mensagem);
        alert.showAndWait();

    }

}