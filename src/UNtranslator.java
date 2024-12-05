import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class UNtranslator {

    private static final Map<String, String> DICTIONARY = new LinkedHashMap<>();
    static {
        DICTIONARY.put("db", "1");
        DICTIONARY.put("d", "2");
        DICTIONARY.put("df", "3");
        DICTIONARY.put("b", "4");
        DICTIONARY.put("n", "5");
        DICTIONARY.put("f", "6");
        DICTIONARY.put("ub", "7");
        DICTIONARY.put("u", "8");
        DICTIONARY.put("uf", "9");
        DICTIONARY.put("DB", "[1]");
        DICTIONARY.put("D", "[2]");
        DICTIONARY.put("DF", "[3]");
        DICTIONARY.put("B", "[4]");
        DICTIONARY.put("N", "[5]");
        DICTIONARY.put("F", "[6]");
        DICTIONARY.put("UB", "[7]");
        DICTIONARY.put("U", "[8]");
        DICTIONARY.put("UF", "[9]");

        DICTIONARY.put("1", "a");
        DICTIONARY.put("2", "b");
        DICTIONARY.put("3", "c");
        DICTIONARY.put("4", "d");

        DICTIONARY.put("h", "h");
        DICTIONARY.put("m", "m");
        DICTIONARY.put("l", "l");
        DICTIONARY.put("sm", "sm");
        DICTIONARY.put("sl", "sl");
        DICTIONARY.put("H", "H");
        DICTIONARY.put("M", "M");
        DICTIONARY.put("L", "L");
        DICTIONARY.put("SM", "SM");
        DICTIONARY.put("SL", "SL");

        DICTIONARY.put("ss", "ss");
        DICTIONARY.put("ssl", "ss+");
        DICTIONARY.put("ssr", "ss-");
        DICTIONARY.put("sw", "sw");
        DICTIONARY.put("swl", "sw+");
        DICTIONARY.put("swr", "sw-");
        DICTIONARY.put("SS", "SS");
        DICTIONARY.put("SSL", "SS+");
        DICTIONARY.put("SSR", "SS-");
        DICTIONARY.put("SW", "SW");
        DICTIONARY.put("SWL", "SW+");
        DICTIONARY.put("SWR", "SW-");
        DICTIONARY.put("wr", "R");
        DICTIONARY.put("ws", "WR");
        DICTIONARY.put("WR", "R");
        DICTIONARY.put("WS", "WR");
        DICTIONARY.put("iwr", "iR");
        DICTIONARY.put("iws", "iWR");

        DICTIONARY.put("hb", "HB!");
        DICTIONARY.put("hd", "HD!");
        DICTIONARY.put("HB", "HB");
        DICTIONARY.put("HD", "HD");
        DICTIONARY.put("HB!", "HB!");
        DICTIONARY.put("HD!", "HD!");
        DICTIONARY.put("RA", "RA");

        DICTIONARY.put("qcf", "236");
        DICTIONARY.put("qcb", "214");
        DICTIONARY.put("hcf", "41236");
        DICTIONARY.put("hcb", "63214");
        DICTIONARY.put("dp", "623");
        DICTIONARY.put("rdp", "421");
        DICTIONARY.put("QCF", "236");
        DICTIONARY.put("QCB", "214");
        DICTIONARY.put("HCF", "41236");
        DICTIONARY.put("HCB", "63214");
        DICTIONARY.put("DP", "623");
        DICTIONARY.put("RDP", "421");
        DICTIONARY.put("cc", "FCx");
        DICTIONARY.put("CC", "FCx");
        DICTIONARY.put("fc", "FC");
        DICTIONARY.put("FC", "FC");
        DICTIONARY.put("hFC", "hFC");

        DICTIONARY.put("SNK", "SNK");
        DICTIONARY.put("SNKc", "SNKx");
        DICTIONARY.put("snk", "SNK");
        DICTIONARY.put("snkc", "SNKx");
        DICTIONARY.put("snkC", "SNKx");

        DICTIONARY.put("P", "P");
        DICTIONARY.put("J", "J");
        DICTIONARY.put("bt", "BT");
        DICTIONARY.put("BT", "BT");
        DICTIONARY.put("fuft", "FUFT");
        DICTIONARY.put("fufa", "FUFA");
        DICTIONARY.put("fdft", "FDFT");
        DICTIONARY.put("fdfa", "FDFA");
        DICTIONARY.put("FUFT", "FUFT");
        DICTIONARY.put("FUFA", "FUFA");
        DICTIONARY.put("FDFT", "FDFT");
        DICTIONARY.put("FDFA", "FDFA");
        DICTIONARY.put("cd", "6523");
        DICTIONARY.put("CD", "6523");
        DICTIONARY.put("otg", "OTG");
        DICTIONARY.put("OTG", "OTG");
        DICTIONARY.put("ch", "CH");
        DICTIONARY.put("CH", "CH");
        DICTIONARY.put("cl", "CL");
        DICTIONARY.put("CL", "CL");
        DICTIONARY.put("i", "i");
        DICTIONARY.put("w", "w");
        DICTIONARY.put("W", "w");
        DICTIONARY.put("t", "t");
        DICTIONARY.put("pc", "pc");
        DICTIONARY.put("PC", "pc");
        DICTIONARY.put("ps", "ps");
        DICTIONARY.put("PS", "PS");
        DICTIONARY.put("js", "js");
        DICTIONARY.put("JS", "js");
        DICTIONARY.put("cs", "cs");
        DICTIONARY.put("CS", "cs");
        DICTIONARY.put("fs", "fs");
        DICTIONARY.put("FS", "fs");
        DICTIONARY.put("is", "is");
        DICTIONARY.put("IS", "is");
        DICTIONARY.put("gs", "gs");
        DICTIONARY.put("GS", "gs");
        DICTIONARY.put("W!", "W!");
        DICTIONARY.put("WB!", "WB!");
        DICTIONARY.put("WBl!", "WBl!");
        DICTIONARY.put("WBo!", "WBo!");
        DICTIONARY.put("w!", "W!");
        DICTIONARY.put("wb!", "WB!");
        DICTIONARY.put("wbl!", "WBl!");
        DICTIONARY.put("wbo!", "WBo!");
        DICTIONARY.put("F!", "F!");
        DICTIONARY.put("FB!", "FB!");
        DICTIONARY.put("FBl!", "FBl!");
        DICTIONARY.put("BB!", "BB!");
        DICTIONARY.put("f!", "F!");
        DICTIONARY.put("fb!", "FB!");
        DICTIONARY.put("fbl!", "FBl!");
        DICTIONARY.put("bb!", "BB!");

        DICTIONARY.put("(", "(");
        DICTIONARY.put(")", ")");
        DICTIONARY.put("?", "?");
        DICTIONARY.put(",", ",");
        DICTIONARY.put(">", ">");
        DICTIONARY.put("~", "~");
        DICTIONARY.put("+", "+");
        DICTIONARY.put("T!", "T!");
        DICTIONARY.put("#", "#");
        DICTIONARY.put("<", "<");
        DICTIONARY.put("-", "-");
    }

    public static void main(String[] args) {
        String inputFile = "input.txt";
        String outputFile = "output.txt";

        try {
            String inputText = readFile(inputFile);
            Mode mode = determineMode(inputText);
            String result = switch (mode) {
                case WHITELIST -> processWhitelistMode(inputText);
                case BLACKLIST -> processBlacklistMode(inputText);
                default -> translateSection(inputText);
            };

            writeFile(outputFile, result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private enum Mode {
        NORMAL,
        BLACKLIST,
        WHITELIST
    }

    private static String readFile(String filename) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!sb.isEmpty()) {
                    sb.append("\n");
                }
                sb.append(line);
            }
        }
        return sb.toString();
    }

    private static void writeFile(String filename, String content) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            bw.write(content);
        }
    }

    private static Mode determineMode(String text) {
        boolean hasSingle = text.contains("{") && text.contains("}");
        boolean hasDouble = text.contains("{{") && text.contains("}}");

        if (hasDouble) {
            return Mode.WHITELIST;
        } else if (hasSingle) {
            return Mode.BLACKLIST;
        } else {
            return Mode.NORMAL;
        }
    }

    private static String processWhitelistMode(String inputText) {
        StringBuilder sb = new StringBuilder();
        int start = 0;
        while (true) {
            int open = inputText.indexOf("{{", start);
            if (open == -1) {
                sb.append(inputText.substring(start));
                break;
            }
            int close = inputText.indexOf("}}", open + 2);
            if (close == -1) {
                throw new RuntimeException("Unmatched double braces.");
            }

            // verbatim outside
            sb.append(inputText, start, open);

            // translate inside
            String inside = inputText.substring(open + 2, close);
            String translated = translateSection(inside);
            sb.append(translated);

            start = close + 2;
        }

        return sb.toString();
    }

    private static String processBlacklistMode(String inputText) {
        StringBuilder sb = new StringBuilder();
        int start = 0;
        while (true) {
            int open = inputText.indexOf("{", start);
            if (open == -1) {
                // translate remainder
                String toTranslate = inputText.substring(start);
                String translated = translateSection(toTranslate);
                sb.append(translated);
                break;
            }
            int close = inputText.indexOf("}", open + 1);
            if (close == -1) {
                throw new RuntimeException("Unmatched single brace.");
            }

            // translate outside braces
            String toTranslate = inputText.substring(start, open);
            String translated = translateSection(toTranslate);
            sb.append(translated);

            // verbatim inside braces
            String verbatim = inputText.substring(open + 1, close);
            sb.append(verbatim);

            start = close + 1;
        }

        return sb.toString();
    }

    private static String translateSection(String text) {
        List<String> tokens = tokenize(text);
        StringBuilder sb = new StringBuilder();
        for (String token : tokens) {
            if (isWhitespace(token)) {
                // Preserve whitespace as is
                sb.append(token);
                continue;
            }

            // Check if token ends with '*'
            boolean wrap = false;
            String baseToken = token;
            if (token.endsWith("*")) {
                wrap = true;
                baseToken = token.substring(0, token.length() - 1);
            }

            if (!DICTIONARY.containsKey(baseToken)) {
                throw new RuntimeException("Unrecognized token: " + baseToken);
            }

            String translated = DICTIONARY.get(baseToken);
            if (wrap) {
                translated = "[" + translated + "]";
            }
            sb.append(translated);
        }

        return sb.toString();
    }

    private static boolean isWhitespace(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isWhitespace(s.charAt(i))) return false;
        }
        return true;
    }

    private static List<String> tokenize(String text) {
        // We'll attempt a greedy match for dictionary tokens or '*' appended tokens.
        // If no dictionary match, and character is not whitespace, we throw an exception.
        // Whitespace sequences are kept as separate tokens.

        // Sort dictionary keys by length descending for longest match
        List<String> keys = new ArrayList<>(DICTIONARY.keySet());
        keys.sort((a,b) -> Integer.compare(b.length(), a.length()));

        List<String> tokens = new ArrayList<>();
        int i = 0;
        while (i < text.length()) {
            char c = text.charAt(i);

            // If whitespace, gather the full run of whitespace
            if (Character.isWhitespace(c)) {
                int start = i;
                while (i < text.length() && Character.isWhitespace(text.charAt(i))) {
                    i++;
                }
                tokens.add(text.substring(start, i));
                continue;
            }

            // Try longest match
            boolean matched = false;
            for (String k : keys) {
                int len = k.length();
                if (i + len <= text.length()) {
                    String sub = text.substring(i, i+len);
                    if (sub.equals(k)) {
                        // Check for trailing '*'
                        int nextPos = i+len;
                        String token = k;
                        if (nextPos < text.length() && text.charAt(nextPos) == '*') {
                            token += "*";
                            len++;
                        }
                        tokens.add(token);
                        i += len;
                        matched = true;
                        break;
                    }
                }
            }

            if (!matched) {
                // No dictionary match
                // Check if next char is '*', it's meaningless without preceding token, so fail
                if (c == '*') {
                    throw new RuntimeException("'*' found without preceding token.");
                }

                // If it's not whitespace (already handled), and no match found, unknown symbol
                throw new RuntimeException("Unrecognized symbol: '" + c + "'");
            }
        }

        return tokens;
    }
}
