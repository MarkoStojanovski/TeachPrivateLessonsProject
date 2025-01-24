package spring.learnteachsubject.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import spring.learnteachsubject.model.Institution;
import spring.learnteachsubject.model.Room;
import spring.learnteachsubject.model.enumeration.RoomType;

import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

public interface RoomService {

    Page<Room> findAllRooms(Pageable pageable);
    List<Room> findAll();

    Optional<Room> getRoomById(Long id);

    void deleteRoomById(Long id);

    Room createRoom(Room room);

    Room updateRoom(Long id, Room room);

    List<Room> findRoomsByInstitutionId(Long institutionId);

    Page<Room> findByCriteria(RoomType roomType, Long institutionId, Integer maxCapacity, Pageable pageable);

    void writeRoomsToCSV(PrintWriter writer);

}
