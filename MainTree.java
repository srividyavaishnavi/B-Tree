import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

public class MainTree implements Constants {
    public static BplusTree t;

    public static void main(String[] args) {

        try {
            String s = null, x = null, output =null;
            // fetch the details from the input file
          String inputFilePath = args[0];
            if (inputFilePath == null) {
                return;
            }
            //To read the input file
            BufferedReader br = new BufferedReader(new FileReader(inputFilePath));
            //To write into the output file
            PrintWriter pw = new PrintWriter("output_file.txt", "UTF-8");
            /*
            Reading the contents of the file line by line
             */
            while ((s = br.readLine()) != null) {
                if (null != s) {
                    //Fetching the keyword
                    x = s.substring(0, s.indexOf(Constants.open_bracket));
                    /*
                    Case 1:Initialize the tree
                     */
                    if (Constants.initialize.equalsIgnoreCase(x)) {
                        if (null != (x = s.substring(x.length() + 1, s.indexOf(Constants.close_bracket)))) {
                            t = new BplusTree(Integer.parseInt(x));
                        }
                    }
                    /*
                    Case 2:Insert into the tree
                     */
                    if (Constants.insert.equalsIgnoreCase(x)) {
                        int f = Integer.parseInt(s.substring(x.length() + 1, s.indexOf(Constants.comma)));
                        double d = Double.parseDouble(s.substring(s.indexOf(Constants.comma) + 1, s.length() - 1));
                        t.insert(f, d);

                    }
                    /*
                    Case 3:Delete from the tree
                     */
                    else if (Constants.delete.equalsIgnoreCase(x.trim())) {
                        s = s.substring(x.length() + 1, s.indexOf(Constants.close_bracket));
                        t.deleteNode(Integer.parseInt((s.trim())));


                    }
                    /*
                    Case 4: Search in the tree
                     */
                    else if (Constants.search.equalsIgnoreCase(x)) {
                        s = s.substring(x.length() + 1, s.indexOf(Constants.close_bracket));
                        /*
                        Case 4(i)-Search single element
                         */
                        if (s.indexOf(Constants.comma) == -1) {
                            s = t.searchNode(Integer.parseInt(s.trim())) + "\n";
                            output = (null == output) ? s : output + s;
                        }
                        /*
                        Case 4 (ii) - Search for a range of elements
                         */
                        else {
                            s = t.searchRangeTree(Integer.parseInt(s.substring(0, s.indexOf(Constants.comma)).trim()),
                                    Integer.parseInt(s.substring(s.indexOf(Constants.comma) + 1).trim())) + "\n";
                            output = (null == output )? s : output + s;

                        }
                    }
                }
            }
            //Write the search results in the output file
            pw.write(output);
            pw.close();

        } catch (FileNotFoundException f) {
            // In case of the input file not existing
            f.printStackTrace();
        } catch (IOException i) {
            //In case of any input output exception
            i.printStackTrace();
        }
    }
}