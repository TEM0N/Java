package lab3;

public class Student implements Comparable<Student> {
    int course;
    String fio;

    public Student(int course, String s) {
        this.course = course;
        this.fio = s;
    }

    @Override
    public int compareTo(Student student) {
        if (course != student.course) {
            return course - student.course;
        }
        return fio.compareTo(student.fio);
    }

    public String toString() {
        return "course: " + course + " fio: " + fio;
    }
}
