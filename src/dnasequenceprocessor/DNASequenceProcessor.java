/*
 * The MIT License
 *
 * Copyright 2020 Jayson Fong <fong.jayson@gmail.com>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package dnasequenceprocessor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 *
 * @author Jayson Fong <fong.jayson@gmail.com>
 */
public class DNASequenceProcessor {

    public static void main(String[] args) {
        String[] fragments = getFragments(
                getDNASequence(),
                getSplitSequence("Beginning"),
                getSplitSequence("Ending")
        );
        displayFragments(fragments);
    }
    
    private static void displayFragments(String[] fragments) {
        int i = 1;
        int totalBases = 0, fragmentLength;
        for (String fragment : fragments) {
            fragmentLength = fragment.length();
            totalBases += fragmentLength;
            System.out.printf(
                    "Fragment #%3d - %5d Bases\n", i++, fragmentLength
            );
        }
        System.out.printf("Total Bases: %d", totalBases);
    }
    
    private static String getDNASequence() {
        Scanner stdin = new Scanner(System.in);
        System.out.print("Enter DNA Sequence File: ");
        String path = stdin.nextLine().trim();
        return getFileContents(path);
    }
    
    private static String getFileContents(String filePath) {
        String content;
        try {
            content = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            System.out.printf(
                    "Error Fetching DNA Sequence From File: %s",
                    e.getLocalizedMessage()
            );
            return getDNASequence();
        }
        return content;
    }
    
    private static String getSplitSequence(String name) {
        Scanner stdin = new Scanner(System.in);
        System.out.printf("Enter %s Split Sequence: ", name);
        return stdin.next();
    }
    
    private static String[] getFragments(String sequence, String seqStart, String seqEnd) {
        return sequence.trim().toLowerCase().replace(
                seqStart + seqEnd,
                String.format("%s\\%s", seqStart.toLowerCase(), seqEnd.toLowerCase())
        ).split("\\\\");
    }
    
}
