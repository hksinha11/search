package com.movies.search.helper;

import com.movies.search.models.Movies;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CSVHelper {
    public static String TYPE = "text/csv";
    static String[] HEADERs = { "showId", "title", "director", "cast", "country", "date_added",
                                "release_year", "rating", "duration", "listed_id", "description"};

    public static boolean hasCSVFormat(MultipartFile file) {

        if (!TYPE.equals(file.getContentType())) {
            return false;
        }

        return true;
    }

    public static List<Movies> csvToMovies(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

            List<Movies> movies = new ArrayList<Movies>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                Movies movie = new Movies(
                        csvRecord.get("show_id"),
                        csvRecord.get("type"),
                        csvRecord.get("title"),
                        csvRecord.get("director"),
                        csvRecord.get("cast"),
                        csvRecord.get("country"),
                        csvRecord.get("date_added"),
                        csvRecord.get("release_year"),
                        csvRecord.get("rating"),
                        csvRecord.get("duration"),
                        csvRecord.get("listed_in"),
                        csvRecord.get("description")
                );

                movies.add(movie);
            }

            return movies;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }

}
