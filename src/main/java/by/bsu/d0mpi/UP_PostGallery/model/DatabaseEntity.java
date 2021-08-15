package by.bsu.d0mpi.UP_PostGallery.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The basic abstract class of any entity contained in the database.
 *
 * @author d0mpi
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class DatabaseEntity {
    private int id;
}
