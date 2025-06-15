package com.project.organizer.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class StudyMaterial {
    private Long id;
    private String title;
    private String discipline;
    private String description;
    private LocalDateTime dateAdded;
    private List<String> tags;
    private List<String> filePaths;
    private List<String> links;

    public StudyMaterial() {
        this.tags = new ArrayList<>();
        this.filePaths = new ArrayList<>();
        this.links = new ArrayList<>();
        this.dateAdded = LocalDateTime.now();
    }

    public StudyMaterial(String title, String discipline, String description, List<String> tags) {
        this.title = title;
        this.discipline = discipline;
        this.description = description;
        this.tags = tags;
        this.dateAdded = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDiscipline() { return discipline; }
    public void setDiscipline(String discipline) { this.discipline = discipline; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getDateAdded() { return dateAdded; }
    public void setDateAdded(LocalDateTime dateAdded) { this.dateAdded = dateAdded; }

    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }

    public List<String> getFilePaths() { return filePaths; }
    public void setFilePaths(List<String> filePaths) { this.filePaths = filePaths; }

    public List<String> getLinks() { return links; }
    public void setLinks(List<String> links) { this.links = links; }

    public void addTag(String tag) {
        if (!tags.contains(tag)) {
            tags.add(tag);
        }
    }

    public void addFile(String filePath) {
        if (!filePaths.contains(filePath)) {
            filePaths.add(filePath);
        }
    }

    public void addLink(String link) {
        if (!links.contains(link)) {
            links.add(link);
        }
    }
} 