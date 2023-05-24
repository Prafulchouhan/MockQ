package com.server.mock.model.exam;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "quiz")
public class Quiz implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "max_marks")
    private String maxMarks;

    @Column(name = "no_of_questions")
    private String noOfQuestions;

    @Column(name = "active")
    private Boolean active=false;

    @Column(name = "time")
    private Integer time;

    @ManyToOne(fetch = FetchType.EAGER)
    private Category category;

    @OneToMany(mappedBy = "quiz",cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Question> questions= new HashSet<>();
}