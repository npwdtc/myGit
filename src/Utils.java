import ReedSolomon.GenericGF;
import ReedSolomon.ReedSolomonEncoder;
import net.fec.openrq.EncodingPacket;
import net.fec.openrq.OpenRQ;
import net.fec.openrq.encoder.DataEncoder;
import net.fec.openrq.encoder.SourceBlockEncoder;
import net.fec.openrq.parameters.FECParameters;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by zhantong on 2016/11/17.
 */
public class Utils {
    public static void main(String[] args){
        int[] data={1,2,3,4,5};
        System.out.println(Arrays.toString(data));
        BitSet bitSet=intArrayToBitSet(data,10);
        System.out.println(bitSet.toString());
        int[] restore=bitSetToIntArray(bitSet,50,10);
        System.out.println(Arrays.toString(restore));
    }
    public static int bitsToInt(BitSet bitSet,int length,int offset){
        int value=0;
        for(int i=0;i<length;i++){
            value+=bitSet.get(offset+i)?(1<<i):0;
        }
        return value;
    }
    public static BitSet intArrayToBitSet(int[] data,int bitsPerInt){
        int index=0;
        BitSet bitSet=new BitSet();
        for(int current:data){
            for(int i=0;i<bitsPerInt;i++){
                if((current&(1<<i))>0){
                    bitSet.set(index);
                }
                index++;
            }
        }
        return bitSet;
    }
    public static BitSet intToBitSet(int data){
        return intToBitSet(data,32);
    }
    public static BitSet intToBitSet(int data,int bitsInInt){
        BitSet bitSet=new BitSet();
        for(int i=0;i<bitsInInt;i++){
            if((data&(1<<i))>0){
                bitSet.set(i);
            }
        }
        return bitSet;
    }
    public static int[] bitSetToIntArray(BitSet bitSet,int length,int bitsPerInt){
        int[] array=new int[length/bitsPerInt];
        for(int i=0;i<length;i++){
            if(bitSet.get(i)){
                array[i/bitsPerInt]|=1<<(i%bitsPerInt);
            }
        }
        return array;
    }
    public static int[] byteArrayToIntArray(byte[] data,int bitsPerInt){
        int numBitsPerByte=8;
        int numDataBits=data.length*numBitsPerByte;
        int[] array=new int[(int)Math.ceil((float) numDataBits/bitsPerInt)];
        for(int i=0;i<numDataBits;i++){
            if((data[i/numBitsPerByte]&(1<<(i%numBitsPerByte)))>0){
                array[i/bitsPerInt]|=1<<(i%bitsPerInt);
            }
        }
        return array;
    }
    public static byte[] intArrayToByteArray(int[] data,int bitsPerInt){
        int numBitsPerByte=8;
        int numDataBits=data.length*bitsPerInt;
        byte[] array=new byte[(int)Math.ceil((float) numDataBits/numBitsPerByte)];
        for(int i=0;i<numDataBits;i++){
            if((data[i/bitsPerInt]&(1<<(i%bitsPerInt)))>0){
                array[i/numBitsPerByte]|=1<<(i%numBitsPerByte);
            }
        }
        return array;
    }
    public static byte[] fileToByteArray(String filePath) throws IOException{
        Path inputPath= Paths.get(filePath);
        return Files.readAllBytes(inputPath);
    }
    public static List<byte[]> raptorQEncode(byte[] data, FECParameters parameters,float repairPercent,boolean isReplaceLastSourcePacketAsRepair){
        List<byte[]> buffer=new LinkedList<>();
        DataEncoder dataEncoder= OpenRQ.newEncoder(data,parameters);
        for(SourceBlockEncoder sourceBlockEncoder:dataEncoder.sourceBlockIterable()){
            for(EncodingPacket encodingPacket:sourceBlockEncoder.sourcePacketsIterable()){
                byte[] packet=encodingPacket.asArray();
                buffer.add(packet);
            }
        }
        if(isReplaceLastSourcePacketAsRepair){
            buffer.remove(buffer.size()-1);
            SourceBlockEncoder lastSourceBlock = dataEncoder.sourceBlock(dataEncoder.numberOfSourceBlocks() - 1);
            buffer.add(lastSourceBlock.repairPacket(lastSourceBlock.numberOfSourceSymbols()).asArray());
        }
        int numRepairPacket=Math.round(buffer.size()*repairPercent);
        for(int i=1;i<=numRepairPacket;i++){
            for(SourceBlockEncoder sourceBlockEncoder:dataEncoder.sourceBlockIterable()){
                byte[] packet=sourceBlockEncoder.repairPacket(sourceBlockEncoder.numberOfSourceSymbols()+i).asArray();
                buffer.add(packet);
            }
        }
        return buffer;
    }
    public static int[] changeNumBitsPerInt(int[] originData,int originNumBits,int newNumBits){
        int[] newData=new int[originData.length*originNumBits/newNumBits];
        for(int i=0;i<newData.length*newNumBits;i++){
            if((originData[i/originNumBits]&(1<<(i%originNumBits)))>0){
                newData[i/newNumBits]|=1<<(i%newNumBits);
            }
        }
        return newData;
    }
    public static int[] rSEncode(int[] originData, int numEc, int ecSize){
        GenericGF field;
        switch (ecSize){
            case 12:
                field=GenericGF.AZTEC_DATA_12;
                break;
            default:
                field=GenericGF.QR_CODE_FIELD_256;
        }
        return rSEncode(originData,numEc,field);
    }
    public static int[] rSEncode(int[] originData, int numEc, GenericGF field){
        ReedSolomonEncoder encoder=new ReedSolomonEncoder(field);
        int[] encodedData=new int[originData.length+numEc];
        System.arraycopy(originData,0,encodedData,0,originData.length);
        encoder.encode(encodedData,numEc);
        return encodedData;
    }
    public static String combinePaths(String ... paths){
        if(paths.length==0){
            return "";
        }
        File combined=new File(paths[0]);
        int i=1;
        while(i<paths.length){
            combined=new File(combined,paths[i]);
            i++;
        }
        return combined.getPath();
    }
    public static BitSet concatBitSets(BitSet a,int length,BitSet b){
        for(int i=b.nextSetBit(0);i>=0;i=b.nextSetBit(i+1)){
            a.set(length+i);
        }
        return a;
    }
    public static BitSet intWithCRC8Checksum(int data){
        CRC8 crc = new CRC8();
        crc.update(data);
        int checksum=(int)crc.getValue();
        BitSet checksumBitSet=intToBitSet(checksum,8);
        BitSet result=intToBitSet(data);
        concatBitSets(result,32,checksumBitSet);
        return result;
    }
    public static String inputStreamToString(InputStream s) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(s));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null)
            sb.append(line).append("\n");
        return sb.toString();
    }
    public static List<BitSet> randomBitSetList(int bitSetLength,int listLength,int randomSeed){
        List<BitSet> bitSets=new ArrayList<>(listLength);
        Random random=new Random(randomSeed);
        for(int i=0;i<listLength;i++){
            BitSet bitSet=new BitSet(bitSetLength);
            for(int pos=0;pos<bitSetLength;pos++){
                if(random.nextBoolean()){
                    bitSet.set(pos);
                }
            }
            bitSets.add(bitSet);
        }
        return bitSets;
    }
    /*
    public static int[] YUVToRGB(int y,int u,int v){
        int r=Math.round(1.164f * (y - 16) + 1.596f * (v - 128));
        int g= Math.round(1.164f * (y - 16) - 0.813f * (v - 128) - 0.391f * (u - 128));
        int b = Math.round(1.164f * (y - 16) + 2.018f * (u - 128));
        r = r < 0 ? 0 : (r > 255 ? 255 : r);
        g = g < 0 ? 0 : (g > 255 ? 255 : g);
        b = b < 0 ? 0 : (b > 255 ? 255 : b);
        return new int[]{r,g,b};
    }
    */
    public static int[] YUVToRGB(int y,int u,int v){
        int delta=128;
        int r=Math.round(y+1.403f*(u-delta));
        int g= Math.round(y-0.714f*(u-delta)-0.344f*(v-delta));
        int b = Math.round(y+1.773f*(v-delta));
        r = r < 0 ? 0 : (r > 255 ? 255 : r);
        g = g < 0 ? 0 : (g > 255 ? 255 : g);
        b = b < 0 ? 0 : (b > 255 ? 255 : b);
        return new int[]{r,g,b};
    }
}
