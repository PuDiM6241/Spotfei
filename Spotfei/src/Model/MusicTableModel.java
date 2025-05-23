/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * Modelo de tabela para exibir uma lista de objetos Music em uma JTable.
 * Cada coluna representa um atributo da música.
 * 
 * Colunas: id, name, artistId, artistName, genre, likes, deslikes.
 * 
 * @author pudim
 */
public class MusicTableModel extends AbstractTableModel {
    private List<Music> musics;
    private final String[] columns = {"id", "name", "artistId", "artistName", "genre", "likes", "deslikes"};
    
    /**
     * Construtor que inicializa o modelo com uma lista de músicas.
     * 
     * @param musics lista de músicas para popular a tabela
     */
    public MusicTableModel(List<Music> musics) {
        this.musics = musics;
    }

    public List<Music> getMusics() {
        return musics;
    }

    public void setMusics(List<Music> musics) {
        this.musics = musics;
    }
    
    @Override
    public int getRowCount() {
        return musics.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    /**
     * Retorna o valor da célula especificada por linha e coluna.
     * 
     * @param rowIndex índice da linha
     * @param columnIndex índice da coluna
     * @return valor da célula
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Music music = musics.get(rowIndex);
        switch (columnIndex) {
            case 0: return music.getId();
            case 1: return music.getName();
            case 2: return music.getArtistid();
            case 3: return music.getArtistName();
            case 4: return music.getGenre();
            case 5: return music.getLikes();
            case 6: return music.getDesLikes();
            default: return null;
        }
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columns[columnIndex];
    }
}
