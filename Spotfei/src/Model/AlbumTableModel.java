/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 *
 * @author pudim
 */
/**
 * Modelo de tabela para exibir dados de álbuns.
 */
public class AlbumTableModel extends AbstractTableModel {
    private List<Album> albums;
    private final String[] columns = {"id", "name", " musics", "owner", "ownerId"};
    
    /**
     * Cria um modelo de tabela para a lista de álbuns fornecida.
     * @param albums lista de álbuns
     */
    public AlbumTableModel(List<Album> albums) {
        this.albums =  albums;
    }
    
    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }
    
    @Override
    public int getRowCount() {
        return albums.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    /**
     * Retorna o valor da célula na linha e coluna especificadas.
     * @param rowIndex índice da linha
     * @param columnIndex índice da coluna
     * @return valor da célula
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Album album = albums.get(rowIndex);
        switch (columnIndex) {
            case 0: return album.getId();
            case 1: return album.getName();
            case 2: return album.getMusics().size();
            case 3: return album.getOwner();
            case 4: return album.getOwnerId();
            default: return null;
        }
    }

    /**
     * Retorna o nome da coluna.
     * @param col índice da coluna
     * @return nome da coluna
     */
    @Override
    public String getColumnName(int col) {
        return columns[col];
    }
}
