package com.example.reprodutormusicajavafx;

import javafx.util.Duration;

public class Musica {

    private String titulo;
    private String artista;
    private String caminhoArquivo;
    private Duration duracao;

    public Musica(String titulo, String artista, String caminhoArquivo, Duration duracao) {

        this.titulo = titulo;
        this.artista = artista;
        this.caminhoArquivo = caminhoArquivo;
        this.duracao = duracao;

    }

    public String getTitulo() {

        return titulo;

    }

    public void setTitulo(String titulo) {

        this.titulo = titulo;

    }

    public String getArtista() {

        return artista;

    }

    public void setArtista(String artista) {

        this.artista = artista;

    }

    public String getCaminhoArquivo() {

        return caminhoArquivo;

    }

    public void setCaminhoArquivo(String caminhoArquivo) {

        this.caminhoArquivo = caminhoArquivo;

    }

    public Duration getDuracao() {

        return duracao;

    }

    public void setDuracao(Duration duracao) {

        this.duracao = duracao;

    }

}