/**
 *
 * @author Tommy MacWilliam
 * @version 0.3
 * Last revised 10/14/08
 *
 */
package tmacsoftware.whitematter;

import java.io.*;
import java.util.Vector;

public class Filio
{

    // file to edit
    private File ioFile;
    // true if caching is enabled, false if not
    private boolean enableCache = false;
    // vector to hold contents of file
    private Vector<String> fileCache;

    /**
     * Constructor, creates file if not already existing
     * @param ioFile File to read/write to
     */
    public Filio(File ioFile)
    {
        if (!ioFile.exists())
        {
            try
            {
                ioFile.createNewFile();
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }
        this.ioFile = ioFile;
        this.fileCache = new Vector<String>();
        // enable caching by default
        enableCache();
    }

    /**
     * Constructor, creates file if not already existing
     * @param path Path to file
     */
    public Filio(String path)
    {
        File ioFile = new File(path);
        if (!ioFile.exists())
        {
            try
            {
                ioFile.createNewFile();
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }
        this.ioFile = ioFile;
        this.fileCache = new Vector<String>();
        // enable caching by default
        enableCache();
    }

    /**
     * Enable file caching
     */
    public void enableCache()
    {
        this.enableCache = true;
        refreshCache();
    }

    /**
     * Disable file caching
     */
    public void disableCache()
    {
        this.enableCache = false;
    }

    /**
     * Convert string array to single string
     * Each element of the array represents a line
     * @param array String array to concatenate
     * @return String representation of array
     */
    private String arrayToString(String[] array)
    {
        // create StringBuilder to concatenate array
        StringBuilder stringData = new StringBuilder();
        // convert array back to single string
        for (int i = 0; i < array.length; i++)
        {
            stringData.append(array[i]);
            // preserve newlines from original file
            stringData.append(System.getProperty("line.separator"));
        }
        return stringData.toString();
    }

    public void refreshCache()
    {
        // don't refresh cache if caching is disabled
        if (!this.enableCache)
        {
            return;
        }
        // clear existing cache
        this.fileCache.clear();
        try
        {
            // point reader to file being manipulated
            BufferedReader input = new BufferedReader(new FileReader(ioFile));
            try
            {
                String line = null;
                // add each line to file cache
                while ((line = input.readLine()) != null)
                {
                    this.fileCache.add(line);
                }
            }
            finally
            {
                // close file stream
                input.close();
            }
        }
        catch (FileNotFoundException ex)
        {
            ex.printStackTrace();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

    /**
     * Get total number of lines in file
     * @return Integer representing number of lines in file (starting at 1)
     */
    public int getTotalLines()
    {
        // if cache is enabled, return size of cache
        if (this.enableCache)
        {
            return this.fileCache.size();
        }
        int currLine = 0;
        try
        {
            // point reader to file being manipulated
            BufferedReader input = new BufferedReader(new FileReader(ioFile));
            try
            {
                String line = "";
                // count number of lines in file
                while ((line = input.readLine()) != null)
                {
                    currLine++;
                }
            }
            finally
            {
                // close file stream
                input.close();
            }
        }
        catch (FileNotFoundException ex)
        {
            ex.printStackTrace();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        return currLine;
    }

    /**
     * Get total number of characters in file
     * @return Integer representing number of characters in file
     */
    public int getTotalCharacters()
    {
        String data = read();
        return data.length();
    }

    /**
     * Read all data in file
     * @return String containing file data
     */
    public String read()
    {
        if (this.enableCache)
        {
            String contents = "";
            for (int i = 0; i < this.fileCache.size(); i++)
            {
                contents += this.fileCache.get(i);
                contents += System.getProperty("line.separator");
            }
            return contents;
        }
        // create string to hold all file data
        String contents = "";
        try
        {
            // point reader to file being manipulated
            BufferedReader input = new BufferedReader(new FileReader(ioFile));
            try
            {
                String line = null;
                // append all lines to content string
                while ((line = input.readLine()) != null)
                {
                    contents += line;
                    contents += System.getProperty("line.separator");
                }
            }
            finally
            {
                // close file stream
                input.close();
                // delete trailing newline
                contents = contents.substring(0, contents.length() - 1);
            }
        }
        catch (FileNotFoundException ex)
        {
            ex.printStackTrace();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        return contents.toString();
    }

    /**
     * Read one line from file
     * @param lineToRead Line to read
     * @return String containing contents of line
     */
    public String read(int lineToRead)
    {
        if (this.enableCache)
        {
            return this.fileCache.get(lineToRead);
        }

        // create string to hold line data
        String contents = "";
        try
        {
            // point reader to file being manipulated
            BufferedReader input = new BufferedReader(new FileReader(ioFile));
            try
            {
                String line = null;
                int currLine = 0;
                // read all lines of file
                while ((line = input.readLine()) != null)
                {
                    if (currLine == lineToRead)
                    {
                        // only save string data for matching line
                        contents += line;
                    }
                    currLine++;
                }
            }
            finally
            {
                // close file stream
                input.close();
            }
        }
        catch (FileNotFoundException ex)
        {
            ex.printStackTrace();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        return contents;
    }

    /**
     * Read multiple lines from file
     * @param startLine Line to startLine reading from
     * @param endLine Line to stop reading from
     * @return String containing data from lines
     */
    public String read(int startLine, int endLine)
    {
        if (this.enableCache)
        {
            String contents = "";
            for (int i = startLine; i <= endLine; i++)
            {
                contents += this.fileCache.get(i);
            }
            return contents;
        }
        // Create string to hold file data
        String contents = "";
        try
        {
            // point reader to file being manipulated
            BufferedReader input = new BufferedReader(new FileReader(ioFile));
            try
            {
                String line = null;
                int currLine = 0;
                // read all lines of file
                while ((line = input.readLine()) != null)
                {
                    // only save data between given endpoints
                    if (currLine >= startLine && currLine <= endLine)
                    {
                        contents += line;
                        contents += System.getProperty("line.separator");
                    }
                    currLine++;
                }
            }
            finally
            {
                // close file stream
                input.close();
                // delete trailing newline
                contents = contents.substring(0, contents.length());
            }
        }
        catch (FileNotFoundException ex)
        {
            ex.printStackTrace();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        return contents;
    }

    /**
     * Overwrite entire file with new data
     * @param data Data to be written to file
     */
    public void write(String data)
    {
        try
        {
            // point writer to file being manipulated
            Writer output = new BufferedWriter(new FileWriter(ioFile));
            // write new file to disk
            output.write(data);
            // close file stream
            output.close();
            refreshCache();
        }
        catch (FileNotFoundException ex)
        {
            ex.printStackTrace();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

    /**
     * Overwrite line with new data
     * @param data Data to be written to file
     * @param line Line to overwrite
     */
    public void write(String data, int line)
    {
        // store all file data in string
        String fileData = read();
        // split file into array
        String[] fileLines = fileData.split(System.getProperty("line.separator"));
        // replace line with new data
        fileLines[line] = data;
        // convert array to single string and write to file
        write(arrayToString(fileLines));
        refreshCache();
    }

    /**
     * Append data to end of file
     * @param data Data to append to file
     */
    public void append(String data)
    {
        // read current file
        String fileData = read();
        // add data to string
        fileData += data;
        // write file to disk
        write(fileData);
        refreshCache();
    }

    /**
     * Append data to end of line
     * @param data Data to append to file
     * @param line Line to append data to
     */
    public void append(String data, int line)
    {
        // read current file
        String fileData = read();
        // split file into array
        String[] fileLines = fileData.split(System.getProperty("line.separator"));
        // add data to end of line
        fileLines[line] += data;
        // convert to single string and save to disk
        write(arrayToString(fileLines));
        refreshCache();
    }

    /**
     * Insert data at a point
     * @param startLine Position to startLine inserting
     * @param data Data to insert
     */
    public void insert(String data, int start)
    {
        // save file data in string
        StringBuilder fileData = new StringBuilder();
        fileData.append(read());
        // insert data at given starting point
        fileData.insert(start, data);
        // save file to disk
        write(fileData.toString());
        refreshCache();
    }

    /**
     * Insert data to a position in a line
     * @param lineStart Line number to startLine writing at
     * @param charStart Character number to startLine writing at
     * @param data Data to write
     */
    public void insert(String data, int lineStart, int charStart)
    {
        // store all file data in string
        String fileData = read();
        // split file into array
        String[] fileLines = fileData.split(System.getProperty("line.separator"));
        // store file data in string
        StringBuilder lineData = new StringBuilder();
        lineData.append(read(lineStart));
        // insert new data into lines
        lineData.insert(charStart, data);
        // replace old line with new line containing data
        fileLines[lineStart] = lineData.toString();
        // convert to array and save to disk
        write(arrayToString(fileLines));
        refreshCache();
    }

    public void delete(int line)
    {
        // store all file data in string
        String fileData = read();
        // split file into array
        String[] fileLines = fileData.split(System.getProperty("line.separator"));
        // convert array to vector
        Vector<String> vector = new Vector<String>();
        for (int i = 0; i < fileLines.length; i++)
        {
            vector.add(fileLines[i]);
        }
        // remove line from vector
        vector.remove(line);
        String[] writeArray = new String[vector.size()];
        for (int i = 0; i < vector.size(); i++)
        {
            writeArray[i] = vector.get(i);
        }
        // convert to single string and write file to disk
        write(arrayToString(writeArray));
        refreshCache();
    }

    /**
     * Delete characters between start and end position
     * @param start Position to start deleting characters
     * @param end Position to stop deleting characters
     */
    public void delete(int start, int end)
    {
        StringBuilder fileData = new StringBuilder();
        // read entire file
        fileData.append(read());
        // overwrite characters in given interval with blank string
        fileData.replace(start, end, "");
        // save file
        write(fileData.toString());
        refreshCache();
    }

    /**
     * Delete characters on a certain line between startLine and endLine position
     * @param line Line in which to delete characters
     * @param start Position on line to start deleting characters
     * @param end Position on line to stop deleting characters
     */
    public void delete(int line, int start, int end)
    {
        // character in end argument should be deleted
        end++;
        // read entire file
        String fileData = read();
        // split file into array
        String[] fileLines = fileData.split(System.getProperty("line.separator"));
        StringBuilder deleteLine = new StringBuilder();
        // add line to delete characters from to array
        deleteLine.append(fileLines[line]);
        deleteLine.replace(start, end, "");
        // replace line with new string
        fileLines[line] = deleteLine.toString();
        // convert to string and save to disk
        write(arrayToString(fileLines));
        refreshCache();
    }
}