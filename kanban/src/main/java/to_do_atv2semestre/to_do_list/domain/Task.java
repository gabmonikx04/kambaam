package to_do_atv2semestre.to_do_list.domain;

import to_do_atv2semestre.to_do_list.domain.enums.Priority;
import lombok.*;
import to_do_atv2semestre.to_do_list.domain.dto.UpdateTaskDTO;
import to_do_atv2semestre.to_do_list.domain.dto.CreateTaskDTO;
import to_do_atv2semestre.to_do_list.domain.enums.Status;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "task")
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    @Column(name = "priority")
    private Priority priority;

    @NonNull
    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private Status status;

    @Column(name = "created_date")
    private LocalDate created_date;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Task(CreateTaskDTO dto) {
        this.title = dto.getTitle();
        this.description = dto.getDescription();
        this.status = Status.A_Fazer;
        this.priority = dto.getPriority();
        this.created_date = LocalDate.now();
        this.due_date = dto.getDueDate();
    }

    public Task moveToNextStatus() {
        this.status = this.nextStatus(this.status);
        return this;
    }

    @Column(name = "due_date")
    private LocalDate due_date;

    public Task update(UpdateTaskDTO dto) {
        if (dto.getTitle().isEmpty() || dto.getTitle().isBlank()) {
            throw new IllegalArgumentException("É obrigatorio ter um Title");
        }

        this.title = dto.getTitle();
        this.description = dto.getDescription();
        this.priority = dto.getPriority();
        this.due_date = dto.getDueDate();

        return this;
    }

    private Status nextStatus(Status status) {
        return status.equals(Status.A_Fazer) ? Status.Em_Progresso : Status.Concluido;
    }
}
