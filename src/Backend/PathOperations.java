package Backend;

public class PathOperations {

    public static String changeSlash(String pathToChange) {
        String path = pathToChange.trim();
        if (path.contains("\\")) {
            return path.replace((char) 92, (char) 47);
        } else {
            return pathToChange;
        }
    }

    public static boolean isExtension(String path, String... extensions) {
        for (String extension : extensions) {
            if (path.substring(path.lastIndexOf(".")).equalsIgnoreCase(extension)) {
                return true;
            }
        }
        return false;
    }
}
