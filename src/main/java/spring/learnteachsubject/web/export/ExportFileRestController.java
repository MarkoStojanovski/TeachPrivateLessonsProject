package spring.learnteachsubject.web.export;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.learnteachsubject.service.*;

import java.io.IOException;
import java.io.PrintWriter;

@RestController
@RequestMapping("/export")
public class ExportFileRestController {

    private final SessionService sessionService;
    private final InstitutionService institutionService;
    private final RoomService roomService;
    private final ProfessorService professorService;
    private final StudentService studentService;

    private final SubjectService subjectService;

    public ExportFileRestController(SessionService sessionService, InstitutionService institutionService, RoomService roomService, ProfessorService professorService, StudentService studentService, SubjectService subjectService) {
        this.sessionService = sessionService;
        this.institutionService = institutionService;
        this.roomService = roomService;
        this.professorService = professorService;
        this.studentService = studentService;
        this.subjectService = subjectService;
    }

    @GetMapping("/sessions/csv")
    public void exportSessionsToCsv(HttpServletResponse response) throws IOException {
        try {
            response.setContentType("text/csv");
            response.setHeader("Content-Disposition", "attachment; filename=\"sessions.csv\"");

            try(PrintWriter writer = response.getWriter()) {
                this.sessionService.writeSessionsToCSV(writer);
            }
        } catch (IOException exception) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error generating CSV file.");
        }
    }

    @GetMapping("/institutions/csv")
    public void exportInstitutionsToCsv(HttpServletResponse response) throws IOException {
        try {
            response.setContentType("text/csv");
            response.setHeader("Content-Disposition", "attachment; filename=\"sessions.csv\"");

            try(PrintWriter writer = response.getWriter()) {
                this.institutionService.writeInstitutionsToCSV(writer);
            }
        } catch (IOException exception) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error generating CSV file.");
        }
    }

    @GetMapping("/rooms/csv")
    public void exportRoomsToCsv(HttpServletResponse response) throws IOException {
        try {
            response.setContentType("text/csv");
            response.setHeader("Content-Disposition", "attachment; filename=\"sessions.csv\"");

            try(PrintWriter writer = response.getWriter()) {
                this.roomService.writeRoomsToCSV(writer);
            }
        } catch (IOException exception) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error generating CSV file.");
        }
    }

    @GetMapping("/professors/csv")
    public void exportProfessorsToCsv(HttpServletResponse response) throws IOException {
        try {
            response.setContentType("text/csv");
            response.setHeader("Content-Disposition", "attachment; filename=\"sessions.csv\"");

            try(PrintWriter writer = response.getWriter()) {
                this.professorService.writeProfessorsToCSV(writer);
            }
        } catch (IOException exception) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error generating CSV file.");
        }
    }
    @GetMapping("/subjects/csv")
    public void exportSubjectsToCsv(HttpServletResponse response) throws IOException {
        try {
            response.setContentType("text/csv");
            response.setHeader("Content-Disposition", "attachment; filename=\"sessions.csv\"");

            try(PrintWriter writer = response.getWriter()) {
                this.subjectService.writeSubjectsToCSV(writer);
            }
        } catch (IOException exception) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error generating CSV file.");
        }
    }

    @GetMapping("/students/csv")
    public void exportStudentsToCsv(HttpServletResponse response) throws IOException {
        try {
            response.setContentType("text/csv");
            response.setHeader("Content-Disposition", "attachment; filename=\"sessions.csv\"");

            try(PrintWriter writer = response.getWriter()) {
                this.studentService.writeStudentsToCSV(writer);
            }
        } catch (IOException exception) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error generating CSV file.");
        }
    }

}
