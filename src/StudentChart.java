import java.util.ArrayList;
import java.util.List;
public class StudentChart {
    private List<Student> students = new ArrayList<> ();
    public void addStudent (Student student) {
        students.add(student);
    }
    @Override
    public String toString() {
        return "StudentChart{" + "students=" + students + '}';
    }
}
