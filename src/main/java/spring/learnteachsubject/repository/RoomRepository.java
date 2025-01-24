package spring.learnteachsubject.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import spring.learnteachsubject.model.Institution;
import spring.learnteachsubject.model.Room;
import spring.learnteachsubject.model.enumeration.RoomType;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query("SELECT r from Room r WHERE r.isDeleted = false AND r.institution.isDeleted = false ")
    Page<Room> findAllRooms(Pageable pageable);

    @Query("SELECT r from Room r WHERE r.isDeleted = false AND r.institution.isDeleted = false ")
    List<Room> findNonDeletedRooms();
    @Query("SELECT r FROM Room r WHERE r.institution.id = :institutionId AND r.isDeleted = false ")
    List<Room> findRoomsByInstitutionId(@Param("institutionId") Long institutionId);

    @Query("SELECT R FROM Room AS R WHERE R.isDeleted = false AND " +
            "(:roomType IS NULL OR R.roomType = :roomType) AND " +
            "(:maxCapacity IS NULL OR R.maxCapacity > :maxCapacity) AND " +
            "(:institutionId IS NULL OR R.institution.id = :institutionId)")
    Page<Room> findByCriteria(@Param("roomType") RoomType roomType,
                              @Param("maxCapacity") Integer maxCapacity,
                              @Param("institutionId") Long institutionId,
                              Pageable pageable);


}
