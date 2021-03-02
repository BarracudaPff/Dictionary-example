import de.siegmar.fastcsv.reader.CsvReader;
import de.siegmar.fastcsv.reader.CsvRow;
import models.TranslationUnit;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {
        //TODO: edit path
        File f = new File("/Users/kirill.krylov/IdeaProjects/Dictionary-example/src/main/java/models/others.csv");

//        InputStreamReader isr = new InputStreamReader(is, "Windows-1251");
        FileInputStream is = new FileInputStream(f);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader buffReader = new BufferedReader(isr);

        String data = buffReader.lines().collect(Collectors.joining("\n"));

        List<CsvRow> rows = CsvReader.builder()
                .fieldSeparator('\t')
                .build(data)
                .stream()
                .collect(Collectors.toList());

        ArrayList<TranslationUnit> translationUnits = new ArrayList<>();
        for (CsvRow row : rows) {
            String[] translations = row.getField(2).split("[,;]");
            for (String translation : translations) {
                TranslationUnit unit = new TranslationUnit(row.getField(0), translation);
                translationUnits.add(unit);
            }

        }
        System.out.println(translationUnits.size());

        //////////////////////////////////////////////////////

//        HashMap<String, String> translations = new HashMap<>();
        HashMap<String, List<String>> translations = new HashMap<>();

        for (TranslationUnit unit : translationUnits) {
//            translations.put(unit.ru, unit.en);
//            translations.put(unit.ru, new ArrayList<>());
            if (translations.containsKey(unit.ru)) {
                translations.get(unit.ru).add(unit.en);
            } else {
                ArrayList<String> list = new ArrayList<>();
                list.add(unit.en);
                translations.put(unit.ru, list);
            }
        }

        Scanner scanner = new Scanner(System.in);

        while (true) {
            String word = scanner.nextLine();
            System.out.println(word + "\t:\t" + translations.get(word));
        }
    }
}
