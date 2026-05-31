package com.example.reprodutormusicajavafx;

import java.util.ArrayList;

public class Biblioteca {

    private ArrayList<Musica> musicas = new ArrayList<>();

    public void adicionarMusica(Musica musica) {

        if (musica == null) {

            throw new IllegalArgumentException("Música não pode ser nula.");

        }

        if (musica.getTitulo() == null || musica.getTitulo().isBlank()) {

            throw new IllegalArgumentException("Título da música é obrigatório.");

        }

        if (musica.getCaminhoArquivo() == null || musica.getCaminhoArquivo().isBlank()) {

            throw new IllegalArgumentException("Caminho do arquivo é obrigatório.");

        }

        musicas.add(musica);

    }

    public void removerMusica(String titulo) {

        if (titulo == null || titulo.isBlank()) {

            throw new IllegalArgumentException("Título não pode ser vazio.");

        }

        Musica encontrada = null;

        for (Musica m : musicas) {

            if (m.getTitulo().equalsIgnoreCase(titulo)) {

                encontrada = m;

                break;

            }

        }

        if (encontrada != null) {

            musicas.remove(encontrada);

        } else {

            throw new IllegalArgumentException("Música não encontrada.");

        }

    }

    public Musica buscarMusica(String titulo) {

        if (titulo == null || titulo.isBlank()) {

            throw new IllegalArgumentException("Título não pode ser vazio.");

        }

        for (Musica m : musicas) {

            if (m.getTitulo().equalsIgnoreCase(titulo)) {

                return m;

            }

        }

        throw new IllegalArgumentException("Música não encontrada.");

    }

    public ArrayList<Musica> listarMusicas() {

        if (musicas.isEmpty()) {

            throw new IllegalStateException("A biblioteca está vazia.");

        }

        return musicas;

    }

}