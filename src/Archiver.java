import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * A small utility that creates a zip file off of the entire project directory,
 * <b>erasing the <tt>.git</tt> directory which might make the submission too heavy for submit.cs.</b>
 * Execute as Java application. Tested on Eclipse and IntelliJ.
 *
<<<<<<< HEAD
=======
 * Do <b>not</b> edit this file! It is provided to you as a script that you should run as a Java application
 * from your IDE every time you want to submit your project.
 * 
>>>>>>> 5d4b6771e2b8065c65a649be16f616cef09246c9
 * @author <a href = "ahmdtaha@cs.umd.edu">Ahmed Taha</a>
 */
public class Archiver {
    public static void pack(String sourceDirPath, String zipFilePath) throws IOException {
        Path p = Files.createFile(Paths.get(zipFilePath));
        try (ZipOutputStream zs = new ZipOutputStream(Files.newOutputStream(p))) {
            Path pp = Paths.get(sourceDirPath);

            Files.walk(pp)
                    .filter(path -> !Files.isDirectory(path) && (!path.toString().contains(".git")))
                    .forEach(path -> {
                        ZipEntry zipEntry = new ZipEntry(pp.relativize(path).toString());
                        try {
                            zs.putNextEntry(zipEntry);
                            Files.copy(path, zs);
                            zs.closeEntry();
                        } catch (IOException e) {
                            System.err.println(e);
                        }
                    });
        }
    }

    public static void main(String[] args) {

        try {
            System.out.println(System.getProperty("user.dir"));
            String source = System.getProperty("user.dir");
            String zipTarget = source + ".zip";
            Files.deleteIfExists(FileSystems.getDefault().getPath(zipTarget));
            pack(source, zipTarget);
            System.out.println("Done");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
