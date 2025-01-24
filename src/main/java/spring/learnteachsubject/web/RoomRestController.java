package spring.learnteachsubject.web;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.learnteachsubject.model.Institution;
import spring.learnteachsubject.model.Room;
import spring.learnteachsubject.model.enumeration.RoomType;
import spring.learnteachsubject.service.RoomService;

import java.util.*;

@RestController
@RequestMapping("/api/rooms")
public class RoomRestController {

    private final RoomService roomService;

    public RoomRestController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Room>> getAllRooms() {
        List<Room> rooms = this.roomService.findAll();
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Map<String,Object>> getRooms(
            @RequestParam(value = "maxCapacity", required = false) Integer maxCapacity,
            @RequestParam(value = "institutionId", required = false) Long institutionId,
            @RequestParam(value = "roomType", required = false) RoomType roomType,
            @RequestParam(value = "page", defaultValue = "0") int page
    ) {
        Pageable pageable = PageRequest.of(page, 6);
        Page<Room> roomPage = this.roomService.findByCriteria(roomType, institutionId,maxCapacity,pageable);

        Map<String, Object> response = new HashMap<>();

        response.put("rooms",roomPage.getContent());
        response.put("totalPages",roomPage.getTotalPages());
        response.put("currentPage",roomPage.getNumber());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/types")
    public ResponseEntity<List<RoomType>> getAllRoomTypes() {
        List<RoomType> roomTypes = Arrays.stream(RoomType.values()).toList();
        return new ResponseEntity<>(roomTypes,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> getRoom(@PathVariable Long id) {
        Optional<Room> room = this.roomService.getRoomById(id);
        return room.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Room> createRoom(
            @RequestBody Room room
    ) {

        Room createdRoom = this.roomService.createRoom(room);
        return new ResponseEntity<>(createdRoom, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Room> updateRoom(
            @PathVariable Long id,
            @RequestBody Room room) {

        Room updatedRoom = this.roomService.updateRoom(id, room);
        return new ResponseEntity<>(updatedRoom, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Room> deleteRoom(@PathVariable Long id) {
        this.roomService.deleteRoomById(id);
        return ResponseEntity.ok().build();
    }


}
