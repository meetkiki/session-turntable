package com.thoughtworks.session.turntable;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Application {

    /**
     * 价值一个亿的转盘代码
     *
     * @param args 啥都行
     */
    public static void main(String[] args) throws Exception {
        Stream<String> lines = readFileLines();

        List<String> emails = lines.skip(2).map(splitStrToEmail()).filter(filterFunction()).distinct().collect(Collectors.toList());

        Collections.shuffle(emails, new Random(1));
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

    private static Predicate<? super String> filterFunction() {
        return email -> !skipList.contains(email);
    }

    private static Stream<String> readFileLines() throws URISyntaxException, IOException {
        URI uri = Objects.requireNonNull(Application.class.getClassLoader().getResource("./td-backend-group.csv")).toURI();
        return Files.lines(Paths.get(uri), StandardCharsets.UTF_8);
    }


    public static List<String> skipList = new ArrayList<>();

    static {
        skipList.add("min.tian@thoughtworks.com");
    }


}
