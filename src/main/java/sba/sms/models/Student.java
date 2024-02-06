package sba.sms.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.LinkedHashSet;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


/**
 * Student is a POJO, configured as a persistent class that represents (or maps to) a table
 * name 'student' in the database. A Student object contains fields that represent student
 * login credentials and a join table containing a registered student's email and course(s)
 * data. The Student class can be viewed as the owner of the bi-directional relationship.
 * Implement Lombok annotations to eliminate boilerplate code.
 */
@Entity
@Table(name = "student")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @RequiredArgsConstructor
@ToString(exclude = "courses")

public class Student {
    @Id
    @Column(name = "email", length = 50, unique = true)
    @NonNull
    private String email;

    @Column(name = "name", nullable = false, length = 50)
    @NonNull
    private String name;

    @Column(name = "password", nullable = false, length = 50)
    @NonNull
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.REMOVE, CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "student_courses",
            joinColumns = @JoinColumn(name = "student_email"),
            inverseJoinColumns = @JoinColumn(name = "courses_id")
    )
    private Set<Course> courses = new HashSet<>();

    // equals and hashCode methods manually implemented
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student student = (Student) o;
        return email.equals(student.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    // Helper method
    public void addCourse(Course course) {
        if (!this.courses.contains(course)) {
            this.courses.add(course);
            course.getStudents().add(this);
        }
    }


    }



