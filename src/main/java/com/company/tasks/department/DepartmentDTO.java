package com.company.tasks.department;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class DepartmentDTO {

    private Long id;

    @NotBlank
    @Size(min = 2, max = 100)
    private String name;

    private String description;

    private Long headId;

    private String headName;

    private LocalDateTime createdAt;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Long getHeadId() {
        return headId;
    }
    public void setHeadId(Long headId) {
        this.headId = headId;
    }

    public String getHeadName() {
        return headName;
    }
    public void setHeadName(String headName) {
        this.headName = headName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public static DepartmentDTO fromEntity(Department d) {
        if (d == null) return null;
        DepartmentDTO dto = new DepartmentDTO();
        dto.setId(d.id);
        dto.setName(d.getName());
        dto.setDescription(d.getDescription());
        if (d.getHead() != null) {
            dto.setHeadId(d.getHead().getId());
            dto.setHeadName(d.getHead().getName());
        }
        dto.setCreatedAt(d.getCreatedAt());
        return dto;
    }

    public Department toEntity() {
        Department d = new Department();
        d.setName(this.name);
        d.setDescription(this.description);
        return d;
    }
}
