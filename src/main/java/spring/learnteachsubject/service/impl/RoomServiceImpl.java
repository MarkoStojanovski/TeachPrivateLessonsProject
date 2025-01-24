package spring.learnteachsubject.service.impl;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.learnteachsubject.model.Institution;
import spring.learnteachsubject.model.Room;
import spring.learnteachsubject.model.enumeration.RoomType;
import spring.learnteachsubject.model.exceptions.InstitutionNotFoundException;
import spring.learnteachsubject.model.exceptions.RoomNotFoundException;
import spring.learnteachsubject.repository.InstitutionRepository;
import spring.learnteachsubject.repository.RoomRepository;
import spring.learnteachsubject.service.RoomService;

import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final InstitutionRepository institutionRepository;

    public RoomServiceImpl(RoomRepository roomRepository, InstitutionRepository institutionRepository) {
        this.roomRepository = roomRepository;
        this.institutionRepository = institutionRepository;
    }

    @Override
    public Page<Room> findAllRooms(Pageable pageable) {
        return this.roomRepository.findAllRooms(pageable);
    }

    @Override
    public List<Room> findAll() {
        return this.roomRepository.findNonDeletedRooms();
    }

    @Override
    public Optional<Room> getRoomById(Long id) {
        return Optional.of(this.roomRepository.findById(id).orElseThrow(RoomNotFoundException::new));
    }

    @Override
    public void deleteRoomById(Long id) {
        Room room = this.roomRepository.findById(id)
                .orElseThrow(RoomNotFoundException::new);
        room.setDeleted(true);
        this.roomRepository.save(room);
    }

    @Override
    @Transactional
    public Room createRoom(Room room) {
        Institution institution = this.institutionRepository.findById(room.getInstitution().getId()).orElseThrow(InstitutionNotFoundException::new);

        Room createdRoom = new Room();

        createdRoom.setRoomNumber(room.getRoomNumber());
        createdRoom.setFloorNumber(room.getFloorNumber());
        createdRoom.setMaxCapacity(room.getMaxCapacity());
        createdRoom.setInUse(room.getInUse());
        createdRoom.setRoomType(room.getRoomType());
        createdRoom.setInstitution(institution);

        return this.roomRepository.save(createdRoom);
    }

    @Override
    @Transactional
    public Room updateRoom(Long id, Room room) {
        Institution institution = this.institutionRepository.findById(room.getInstitution().getId()).orElseThrow(InstitutionNotFoundException::new);

        Room updatedRoom = this.roomRepository.findById(id).orElseThrow(RoomNotFoundException::new);

        updatedRoom.setRoomNumber(room.getRoomNumber());
        updatedRoom.setFloorNumber(room.getFloorNumber());
        updatedRoom.setMaxCapacity(room.getMaxCapacity());
        updatedRoom.setInUse(room.getInUse());
        updatedRoom.setRoomType(room.getRoomType());
        updatedRoom.setInstitution(institution);

        return this.roomRepository.save(updatedRoom);
    }

    @Override
    public List<Room> findRoomsByInstitutionId(Long institutionId) {
        return this.roomRepository.findRoomsByInstitutionId(institutionId);
    }

    @Override
    public Page<Room> findByCriteria(RoomType roomType, Long institutionId, Integer maxCapacity, Pageable pageable) {
        if (roomType != null || institutionId != null || maxCapacity != null) {
            return this.roomRepository.findByCriteria(roomType, maxCapacity, institutionId, pageable);
        }
        return this.roomRepository.findAllRooms(pageable);
    }

    @Override
    public void writeRoomsToCSV(PrintWriter writer) {
        writer.println("ID, Room Number, Floor Number, Max Capacity, In Use, Room Type, Institution");

        List<Room> rooms = this.roomRepository.findNonDeletedRooms();

        for (Room room : rooms) {
            writer.printf("%s,%s,%s,%s,%s,%s,%s%n",
                    room.getId() != null ? room.getId() : "",
                    room.getRoomNumber() != 0 ? room.getRoomNumber() : "",
                    room.getFloorNumber() != 0 ? room.getFloorNumber() : "",
                    room.getMaxCapacity() != 0 ? room.getMaxCapacity() : "",
                    room.getInUse() != null ? room.getInUse().toString() : "",
                    room.getRoomType() != null ? room.getRoomType().toString() : "",
                    room.getInstitution() != null ? room.getInstitution().getName() + ' ' + room.getInstitution().getLocation() : ""
            );
        }
    }
}
