package com.server.mock.model.exam;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "quiz_attempt")
public class QuizAttempt {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;


    @Column(name = "date_of_attempt", nullable = false)
    private LocalDate dateOfAttempt;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @Column(name = "total_questions")
    private String totalQuestions;

    @Column(name = "no_of_questions_attempted")
    private String noOfQuestionsAttempted;

    @Column(name = "marks_got")
    private String marksGot;

    @Column(name = "time_taken_in_minutes")
    private String timeTakenInMinutes;

    @Column(name = "quiz_title")
    private String quizTitle;

    @Column(name = "total_marks")
    private String totalMarks;

}