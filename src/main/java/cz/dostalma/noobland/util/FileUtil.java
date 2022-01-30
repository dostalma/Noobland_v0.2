package cz.dostalma.noobland.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileUtil {

    public File findLatestSavedFileOfName(String name, String pathToDirectory) {
        try (Stream<Path> stream = Files.list(Paths.get(pathToDirectory))) {
            Comparator<Path> fileNameDateComparator
                    = Comparator.comparing(
                    Path::getFileName, (s1, s2) -> {
                        String dateStr1 = s1.toString().split("_")[2].split("\\.")[0];
                        String dateStr2 = s2.toString().split("_")[2].split("\\.")[0];

                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy'T'HH-mm-ss");
                        LocalDate date1 = LocalDate.parse(dateStr1, dtf);
                        LocalDate date2 = LocalDate.parse(dateStr2, dtf);

                        return date1.compareTo(date2);
                    });

            return stream
                    .filter(file -> !Files.isDirectory((Path)file))
                    .filter(file -> file.getFileName().toString().contains(name))
                    .max(fileNameDateComparator)
                    .map(Path::toFile)
                    .get();
        } catch (IOException e) {
//            e.printStackTrace();
            // @TODO
        } catch (NoSuchElementException e) {
            // @TODO
        }
        return null;
    }
}
