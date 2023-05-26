import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Practice {
    public static void main(String[] args){
        Path input1 = Paths.get("src","input1.txt");
        Path input2 = Paths.get("src","input2.txt");
        Path merged = Paths.get("src", "merged.txt");
        Path common = Paths.get("src", "common.txt");
        ByteBuffer buffer = ByteBuffer.allocate(16);
        buffer.clear();
        List<Integer> numbers1 = readIntegers(input1);
        List<Integer> numbers2 = readIntegers(input2);
        List<Integer> mergedNums = mergeLists(numbers1, numbers2);
        List<Integer> commonNums = findCommon(numbers1, numbers2);

        try {
            if(Files.exists(merged)){
                System.out.println("File already created");
            } else {
                Files.createFile(merged);
            }
            if(Files.exists(common)){
                System.out.println("File already created");
            } else {
                Files.createFile(common);
            }
            writeToFile(merged, mergedNums);
            writeToFile(common, commonNums);
        } catch (IOException e){
            e.printStackTrace();
        }

    }
    public static List<Integer> readIntegers(Path path) {
        List<Integer> integerList = new ArrayList<>();
        try {
            List<String> data = Files.readAllLines(path);
            integerList = data.stream().map(line -> Integer.parseInt(line)).collect(Collectors.toList());
        }catch (IOException e){
            e.printStackTrace();
        }
        return integerList;
    }

    public static List<Integer> findCommon(List<Integer> input1, List<Integer> input2){
        List<Integer> commonNums = new ArrayList<>();
        commonNums.addAll(input1);
        commonNums.retainAll(input2);
        return commonNums;
    }
    public static List<Integer> mergeLists(List<Integer> input1, List<Integer> input2){
        List<Integer> mergedNums = new ArrayList<>();
        mergedNums.addAll(input1);
        mergedNums.addAll(input2);
        return mergedNums;
    }
    public static void writeToFile(Path path, List<Integer> integerList){
        try (FileChannel fileChannel = FileChannel.open(path, StandardOpenOption.WRITE)){
            for(int num: integerList){
                String numString = num + System.lineSeparator();
                ByteBuffer buffer = ByteBuffer.wrap(numString.getBytes());
                fileChannel.write(buffer);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
 }
