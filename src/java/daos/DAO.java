/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 *
 * @author THANH HUNG
 */
public class DAO implements Serializable {
    private String fileName = "\\SpellChecking\\CSD_SpellChecking\\words.txt";
    public DAO() {
    }

    public List<String> getWordList(String metaSearch) {
        Hashtable<String, List<String>> data = new Hashtable<>();
        List<String> result = new ArrayList<>();
        List<String> existed = new ArrayList<>();
        Metaphone mp = new Metaphone();
        try {
            File f = new File(fileName);
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            String line;
            String meta;

            while ((line = br.readLine()) != null) {
                meta = mp.encode(line);
                if (data.containsKey(meta)) {
                    existed = data.get(meta);
                    existed.add(line);
                    data.put(meta, existed);
                } else {
                    List<String> word = new ArrayList<>();
                    word.add(line);
                    data.put(meta, word);
                }
            }
            result = data.get(metaSearch);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private int min(int x, int y, int z) {
        if (x <= y && x <= z) {
            return x;
        }
        if (y <= x && y <= z) {
            return y;
        } else {
            return z;
        }
    }

    public int editDistDP(String str1, String str2, int m, int n) {
        // Create a table to store results of subproblems
        int dp[][] = new int[m + 1][n + 1];

        // Fill d[][] in bottom up manner
        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                // If first string is empty, only option is to
                // isnert all characters of second string
                if (i == 0) {
                    dp[i][j] = j;  // Min. operations = j
                } // If second string is empty, only option is to
                // remove all characters of second string
                else if (j == 0) {
                    dp[i][j] = i; // Min. operations = i
                } // If last characters are same, ignore last char
                // and recur for remaining string
                else if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } // If the last character is different, consider all
                // possibilities and find the minimum
                else {
                    dp[i][j] = 1 + min(dp[i][j - 1], // Insert
                            dp[i - 1][j], // Remove
                            dp[i - 1][j - 1]); // Replace
                }
            }
        }

        return dp[m][n];
    }
}
