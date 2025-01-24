package spring.learnteachsubject.web;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.learnteachsubject.model.Institution;
import spring.learnteachsubject.model.Professor;
import spring.learnteachsubject.model.Room;
import spring.learnteachsubject.service.InstitutionService;
import spring.learnteachsubject.service.RoomService;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/institutions")
public class InstitutionRestController {

    private final InstitutionService institutionService;
    private final RoomService roomService;

    public InstitutionRestController(InstitutionService institutionService, RoomService roomService) {
        this.institutionService = institutionService;
        this.roomService = roomService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllInstitutions(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
            @RequestParam(value = "locationCity", required = false) String locationCity,
            @RequestParam(value = "page", defaultValue = "0") int page
    ) {
        Pageable pageable = PageRequest.of(page, 6);
        Page<Institution> institutionsPage = this.institutionService.findByCriteria(email, phoneNumber, locationCity, name, pageable);
        Map<String, Object> response = new HashMap<>();
        response.put("institutions", institutionsPage.getContent());
        response.put("totalPages", institutionsPage.getTotalPages());
        response.put("currentPage", institutionsPage.getNumber());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Institution>> getAllInstitutions() {
        List<Institution> institutions = this.institutionService.findAll();
        return new ResponseEntity<>(institutions, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getInstitutionWithRooms(@PathVariable Long id) {
        Optional<Institution> institution = this.institutionService.findInstitutionById(id);
        List<Room> rooms = this.roomService.findRoomsByInstitutionId(id);
        Map<String, Object> response = new HashMap<>();
        response.put("institution", institution.get());
        response.put("rooms", rooms);
        return ResponseEntity.ok(response);
    }


    @PostMapping
    public ResponseEntity<Institution> createInstitution(
            @RequestBody Institution institution) {

        Institution newInstitution = this.institutionService.createInstitution(institution);
        return new ResponseEntity<>(newInstitution, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Institution> updateInstitution(
            @PathVariable Long id,
            @RequestBody Institution institution) {

        Institution updatedInstitution = this.institutionService.updateInstitution(id, institution);
        return new ResponseEntity<>(updatedInstitution, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Institution> deleteInstitution(@PathVariable Long id) {
        this.institutionService.deleteInstitutionById(id);
        return ResponseEntity.ok().build();
    }


}
