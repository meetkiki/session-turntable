package com.thoughtworks.session.turntable;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Application {

    public static void main(String[] args) throws IOException, URISyntaxException {
        Stream<String> lines = readFileLines();

        List<String> emails = lines.skip(2).map(splitStrToEmail()).filter(Objects::nonNull).distinct().collect(Collectors.toList());

        Collections.shuffle(emails);

        for (int i = 0; i < emails.size(); i++) {
            System.out.println((i + 1) + " " + emails.get(i));
        }
    }

    private static Function<String, String> splitStrToEmail() {
        return line -> {
            if (line == null || line.isEmpty()) {
                return null;
            }
            return line.substring(0, line.indexOf(","));
        };
    }

    private static Stream<String> readFileLines() throws URISyntaxException, IOException {
        URI uri = Objects.requireNonNull(Application.class.getClassLoader().getResource("./td-backend-group.csv")).toURI();
        return Files.lines(Paths.get(uri), StandardCharsets.UTF_8);
    }

}
