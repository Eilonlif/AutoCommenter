import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

public class functioncommets {
    public static void main(String[] args) throws IOException {
        Path path = Paths.get("src/guessing_game.java");    // The path to the file
        List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
        List<String> tmpLines = new ArrayList<>();
        for (String l : lines) {
            if (l.contains("public") && l.contains("(") && !l.contains("class")) {
                List<String[]> comments = getInfo(l);
                String extraLines = comments.get(0)[1];
                for (int i = 1; i < comments.size(); i++) {
                    extraLines = new StringBuilder(extraLines).append(comments.get(i)[0]).append(" ").append(comments.get(i)[1]).toString();
                }
                tmpLines.add(extraLines);
            }
            tmpLines.add(l);
        }
        Files.write(path, tmpLines, StandardCharsets.UTF_8);
        System.out.println("Comments added successfully");
    }


    public static List<String[]> getInfo(String line) {
        List<String[]> comments = new ArrayList<>();
        line = line.replaceAll("public", "").replaceAll("static", "");
        String[] tmpOfReturn = new String[]{"\n// It's returning a variable of the type:", line.trim().substring(0, line.trim().indexOf(" "))};
        String tmp = line.substring(0, line.indexOf("(")), funcName = tmp.split(" ")[tmp.split(" ").length - 1];
        line = line.replace(line.substring(0, line.indexOf(funcName)), "").replace(funcName, "").replace("(", "").replace(")", "").replace("{", "");
        String[] splitedLine = line.split(",");
        comments.add(new String[]{"numOfVar", "// The function " + funcName + " has " + splitedLine.length + " variables. "});
        comments.add(tmpOfReturn);
        return comments;
    }
}

// Code for the variables that each function is getting:
//      for (String var : splitedLine) {
//            System.out.println("var: "  + var.trim());
//            var = var.trim();
//            comments.add(new String[] {var.substring(0, var.indexOf(" ")), var.substring(var.indexOf(" ")).trim()});
//        }
