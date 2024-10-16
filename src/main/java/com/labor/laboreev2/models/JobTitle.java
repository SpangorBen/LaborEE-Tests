package com.labor.laboreev2.models;

import jakarta.persistence.*;

@Entity
@Table(name = "JobTitles")
public class JobTitle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "title", nullable = false)
    private String Title;

    @Column(name = "description", nullable = false)
    private String Description;

    public JobTitle() {
    }

    public JobTitle(String title, String description) {
        Title = title;
        Description = description;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    @Override
    public String toString() {
        return "JobTitle{" +
                "Id=" + Id +
                ", Title='" + Title + '\'' +
                ", Description='" + Description + '\'' +
                '}';
    }
}
