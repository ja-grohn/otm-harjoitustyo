/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otmkurssiprojekti.dataaccessobject;

import java.nio.file.Path;

public class ByteFileGameSaveDao extends AbstractGameSaveDao implements GameSaveDao {

    public ByteFileGameSaveDao(Path directory) {
        super(directory, new ByteFileLevelDao(directory));
    }

}
