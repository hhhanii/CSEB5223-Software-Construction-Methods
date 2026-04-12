import java.util.ArrayList;
import java.util.List;

public class CacheApi {
    private static final int CACHE_SIZE = 20;
    private String[] cacheKeys = new String[CACHE_SIZE];
    private Object[] cacheValues = new Object[CACHE_SIZE];
    private int head = 0;
    private int count = 0;
    
    private CourseConfig config;
    private Student[] studentArray;
    private int studentCount;
    
    //constructor for course-only caching
    public CacheApi(CourseConfig config) {
        this.config = config;
    }
    
    //constructor for full caching (courses + students)
    public CacheApi(CourseConfig config, Student[] students, int studentCount) {
        this.config = config;
        this.studentArray = students;
        this.studentCount = studentCount;
    }
    
    public void cache(String key, Object value) {
        if (key == null) return;
        cacheKeys[head] = key.toLowerCase();
        cacheValues[head] = value;
        head = (head + 1) % CACHE_SIZE;
        if (count < CACHE_SIZE) count++;
    }
    
    public Object getCached(String key) {
        if (key == null) return null;
        String lowerKey = key.toLowerCase();
        for (int i = 0; i < count; i++) {
            int idx = (head - count + i + CACHE_SIZE) % CACHE_SIZE;
            if (cacheKeys[idx] != null && cacheKeys[idx].equals(lowerKey)) {
                System.out.println("Cache hit: " + key);
                return cacheValues[idx];
            }
        }
        return null;
    }
    
    public List<String> suggest(String prefix, String type) {
        List<String> results = new ArrayList<>();
        if (prefix == null || prefix.length() < 2) return results;
        
        String p = prefix.toLowerCase();
        
        if ("course".equalsIgnoreCase(type) && config != null) {
            Course[] courses = config.getAllCourses();
            for (Course c : courses) {
                if (c == null) continue;
                String code = c.getCourseCode().toLowerCase();
                String name = c.getCourseName().toLowerCase();
                if (code.startsWith(p) || name.startsWith(p)) {
                    String suggestion = c.getCourseCode() + " - " + c.getCourseName();
                    if (!results.contains(suggestion)) {
                        results.add(suggestion);
                        if (results.size() >= 5) break;
                    }
                }
            }
        } else if ("student".equalsIgnoreCase(type) && studentArray != null) {
            for (int i = 0; i < studentCount; i++) {
                Student s = studentArray[i];
                if (s == null) continue;
                String id = s.getStudentID().toLowerCase();
                String name = s.getFullName().toLowerCase();
                if (id.startsWith(p) || name.startsWith(p)) {
                    String suggestion = s.getStudentID() + " - " + s.getFullName();
                    if (!results.contains(suggestion)) {
                        results.add(suggestion);
                        if (results.size() >= 5) break;
                    }
                }
            }
        }
        return results;
    }
}