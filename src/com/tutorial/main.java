package com.tutorial;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Date;
import java.io.File;

public class main {
    public static void main(String[] args) throws IOException {

        Scanner terminalInput = new Scanner(System.in);
        String pilihanUser;
        boolean isLanjutkan = true;

        while (isLanjutkan) {
            clearScreen();
            System.out.println("Database UlurRosyad\n");
            System.out.println("1.\tLihat Seluruh Siswa");
            System.out.println("2.\tCari Data Siswa");
            System.out.println("3.\tTambah Data Siswa");
            System.out.println("4.\tUbah Data Siswa");
            System.out.println("5.\tHapus Data Siswa");

            System.out.print("\nPilihan Anda : ");
            pilihanUser = terminalInput.next();

            switch (pilihanUser) {
                case "1":
                    System.out.println("\n==================");
                    System.out.println("LIST SELURUH SISWA");
                    System.out.println("==================");
                    tampilkanData();
                    break;
                case "2":
                    System.out.println("\n==========");
                    System.out.println("CARI SISWA");
                    System.out.println("==========");
                    cariData();
                    break;
                case "3":
                    System.out.println("\n=================");
                    System.out.println("TAMBAH DATA SISWA");
                    System.out.println("=================");
                    tambahData();
                    tampilkanData();
                    break;
                case "4":
                    System.out.println("\n===============");
                    System.out.println("UBAH DATA SISWA");
                    System.out.println("===============");
                    updateData();
                    break;
                case "5":
                    System.out.println("\n================");
                    System.out.println("HAPUS DATA SISWA");
                    System.out.println("================");
                    deleteData();
                    break;
                default:
                    System.err.println("\nPilihane ko langka\nPilih sing nggenah (1-5)");

            }

            isLanjutkan = getYesorNo("Pengen maning oraa?");

        }
        System.out.println("Yawis dadaah");
    }

    private static void tampilkanData() throws IOException{

        FileReader fileInput;
        BufferedReader bufferInput;

        try {
            fileInput = new FileReader("database.txt");
            bufferInput = new BufferedReader(fileInput);
        } catch (Exception e) {
            System.err.println("Database tidak ditemukan");
            System.err.println("Silahkan tambah data terlebih dahulu");
            tambahData();
            return;
        }

        System.out.println("\n| No |\tNama \t\t\t\t\t\t\t| Tanggal lahir|\tAsal \t\t|\tKuliah");
        System.out.println("---------------------------------------------------------------------------------------------------------");

        String data = bufferInput.readLine();
        int nomorData = 0;

        while (data != null) {
            nomorData++;
            StringTokenizer stringToken = new StringTokenizer(data, ",");
            System.out.printf("| %2d ", nomorData);
            System.out.printf("|\t%-30s \t", stringToken.nextToken());
            System.out.printf("|\t%4s  ", stringToken.nextToken());
            System.out.printf("|\t%-10s \t", stringToken.nextToken());
            System.out.printf("|\t%s ", stringToken.nextToken());
            System.out.print("\n");

            data = bufferInput.readLine();
        }
        System.out.println("---------------------------------------------------------------------------------------------------------");
    }

    private static void cariData() throws  IOException{

        // membaca database ada atau tidak
        try {
            File file = new File("database.txt");
        } catch (Exception e) {
            System.err.println("Database tidak ditemukan");
            System.err.println("Silahkan tambah data terlebih dahulu");
            tambahData();
            return;
        }
        // Ambil keyword dari user

        Scanner terminalInput = new Scanner(System.in);
        System.out.print("Masukan nama siswa : ");
        String cariSiswa = terminalInput.nextLine();

        String[] keywords = cariSiswa.split("\\s+");

        //Cek keyword di database
        cariSiswaDiDataBase(keywords,true);

    }

    private static boolean cariSiswaDiDataBase(String[] keywords,boolean isDisplay) throws IOException{

        FileReader fileInput = new FileReader("database.txt");
        BufferedReader bufferInput = new BufferedReader(fileInput);

        String data = bufferInput.readLine();
        boolean isExist = false;
        int nomorData = 0;
        if (isDisplay) {
            System.out.println("\n| No |\tNama \t\t\t\t\t\t\t| Tanggal lahir|\tAsal \t\t|\tKuliah");
            System.out.println("---------------------------------------------------------------------------------------------------------");
        }
        while (data != null){

            //cek keywords didalam data
            isExist = true;
            for(String keyword:keywords){
                isExist = isExist && data.toLowerCase().contains(keyword.toLowerCase());
            }

            if(isExist) {
                if(isDisplay) {

                    nomorData++;
                    StringTokenizer stringToken = new StringTokenizer(data, ",");
                    System.out.printf("| %2d ", nomorData);
                    System.out.printf("|\t%-30s \t", stringToken.nextToken());
                    System.out.printf("|\t%4s  ", stringToken.nextToken());
                    System.out.printf("|\t%-10s \t", stringToken.nextToken());
                    System.out.printf("|\t%s ", stringToken.nextToken());
                    System.out.print("\n");
                } else {
                    break;
                }
            }

            data = bufferInput.readLine();
        }
        if (isDisplay) {
            System.out.println("---------------------------------------------------------------------------------------------------------");
        }
        return isExist;
    }

    private static void tambahData() throws IOException{

        FileWriter fileOutput = new FileWriter("database.txt",true);
        BufferedWriter bufferedOutput = new BufferedWriter(fileOutput);

        Scanner terminalInput = new Scanner(System.in);
        String namaSiswa, tanggal, asal, kuliah;

        System.out.print("Masukkan nama siswa\t:");
        namaSiswa = terminalInput.nextLine();
        System.out.print("Masukkan tanggal lahir (DD/MM/YY)\t:");
        tanggal = ambilTanggal();
        System.out.print("Masukkan asal siswa\t:");
        asal = terminalInput.nextLine();
        System.out.print("Masukkan studi lanjut\t:");
        kuliah = terminalInput.nextLine();

        // Cek data wis ana apa urung

        String[] keywords = {namaSiswa+","+tanggal+","+asal+","+kuliah};
        System.out.println(Arrays.toString(keywords));

        boolean isExist = cariSiswaDiDataBase(keywords,false);

        // Mencantumkan data ke database

        if (!isExist) {
            System.out.println("Data yang akan anda masukkan adalah : ");
            System.out.println("-------------------------------------");
            System.out.println("Nama            : "+namaSiswa);
            System.out.println("Tanggal Lahir   : "+tanggal);
            System.out.println("Asal            : "+asal);
            System.out.println("Studi lanjut    : "+kuliah);

            boolean isTambah = getYesorNo("Apakah anda ingin menambahkan data?");

            if (isTambah) {
                bufferedOutput.write(namaSiswa + "," + tanggal + "," + asal + "," + kuliah+"\n");
                bufferedOutput.newLine();
                bufferedOutput.flush();
            }

        } else {
            System.out.println("Data siswa yang anda cantumkan telah tersedia");
            cariSiswaDiDataBase(keywords,true);
        }
        bufferedOutput.close();

    }

    private static String ambilTanggal() throws IOException {
        Scanner terminalInput = new Scanner(System.in);
        String tanggal = terminalInput.nextLine();
        boolean tanggalValid = false;

        while (!tanggalValid) {
            try {
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                String dateString = format.format(new Date());
                Date date = format.parse(tanggal);
                tanggalValid = true;
            } catch (Exception e) {
                System.err.println("format tanggal salah mas mba");
                System.out.println("Masukkan tanggal lagi mas mba");
                tanggal = terminalInput.nextLine();
            }
        }
        return tanggal;
    }

    private static void deleteData() throws IOException{

        //ambil database original
        File database = new File("database.txt");
        FileReader fileInput = new FileReader(database);
        BufferedReader bufferedInput = new BufferedReader(fileInput);

        //buat database sementara
        File tempDB = new File("tempDB.txt");
        FileWriter fileOutput = new FileWriter(tempDB);
        BufferedWriter bufferedOutput = new BufferedWriter(fileOutput);

        //tampilkan data
        System.out.println("List Siswa");
        tampilkanData();

        //ambil input user untuk mendelete data
        Scanner terminalInput = new Scanner(System.in);
        System.out.println("\n\nMasukkan nomor siswa yang ingin dihapus");
        int deleteNum = terminalInput.nextInt();

        //looping untuk membaca data dan skip data yg akan dihapus

        int entryCounts = 0;

        String data = bufferedInput.readLine();

        while(data != null) {
            entryCounts++;
            boolean isDelete = false;

            StringTokenizer st = new StringTokenizer(data,",");

            // tampilkan data yg ingin dihapus
            if(deleteNum == entryCounts) {
                System.out.println("Data yang ingin dihapus adalah : ");
                System.out.println("---------------------------------");
                System.out.println("Nama            : " + st.nextToken());
                System.out.println("Tanggal lahir   : " + st.nextToken());
                System.out.println("Asal            : " + st.nextToken());
                System.out.println("Studi Lanjut    : " + st.nextToken());

                isDelete = getYesorNo("Apakah anda ingin menghapus data ini?");
            }

            if(isDelete) {
                //skip pindahkan data dari original ke sementara
                System.out.println("Data berhasil dihapus");
            }else {
                bufferedOutput.write(data);
                bufferedOutput.newLine();
            }
            data = bufferedInput.readLine();
        }
        //menulis data ke file
        bufferedOutput.flush();
        //tutup file
        bufferedInput.close();
        bufferedOutput.close();
        fileInput.close();
        fileOutput.close();
        //jalankan method
        System.gc();
        //delete original file
        database.delete();
        //rename file yg baru
        tempDB.renameTo(database);


    }

    private static void updateData() throws IOException{
        //ambil database original
        File database = new File("database.txt");
        FileReader fileInput = new FileReader(database);
        BufferedReader bufferedInput = new BufferedReader(fileInput);

        //buat database sementara
        File tempDB = new File("tempDB.txt");
        FileWriter fileOutput = new FileWriter(tempDB);
        BufferedWriter bufferedOutput = new BufferedWriter(fileOutput);

        //tampilkan data
        System.out.println("List SIswa");
        tampilkanData();

        //ambil input user
        Scanner terminalInput = new Scanner(System.in);
        System.out.print("\nmasukkan nomor siswa yang ingin diubah");
        int updateNum = terminalInput.nextInt();

        //tampilkan data yg ingin diupdate
        String data = bufferedInput.readLine();

        int entryCounts = 0;

        while(data != null) {
            entryCounts++;
            StringTokenizer st = new StringTokenizer(data,",");

            //tampilkan entrycounts == num
            if(entryCounts == updateNum){
                System.out.println("\nData yang ingin diubah adalah :");
                System.out.println("---------------------------------");
                System.out.println("Nama            : " + st.nextToken());
                System.out.println("Tanggal Lahir   : " + st.nextToken());
                System.out.println("Asal            : " + st.nextToken());
                System.out.println("Studi Lanjut    : " + st.nextToken());
                //update data

                //mengambil input dari user
                String[] fieldData = {"nama","tanggal Lahir","asal","studi lanjut"};
                String[] tempData  = new String[4];



                for (int i = 0;i < fieldData.length;i++) {
                    boolean isUpdate = getYesorNo("Apakah anda ingin merubah " + fieldData[i] + "?");


                    if(isUpdate) {
                        //user input
                        terminalInput = new Scanner(System.in);
                        System.out.println("\nMasukkan " + fieldData[i] + " baru : ");
                        tempData[i] = terminalInput.nextLine();
                    }else {
                        tempData[i] = fieldData[i];
                    }
                }

                //tampilkan data baru
                st = new StringTokenizer(data,",");
                System.out.println("\nData anda yang baru adalah :");
                System.out.println("---------------------------------");
                System.out.println("Nama            : " + st.nextToken() + " --> " + tempData[0]);
                System.out.println("Tanggal Lahir   : " + st.nextToken() + " --> " + tempData[1]);
                System.out.println("Asal            : " + st.nextToken() + " --> " + tempData[2]);
                System.out.println("Studi Lanjut    : " + st.nextToken() + " --> " + tempData[3]);

                boolean isUpdate = getYesorNo("Apakah anda ingin menetapkan data tersebut?");

                if(isUpdate) {
                    //cek data
                    boolean isExist = cariSiswaDiDataBase(tempData,false);

                    if(isExist) {
                        System.err.println("Data anda sudah terdaftar di database");
                    }else {

                        //format data baru
                        String nama = tempData[0];
                        String tanggal = tempData[1];
                        String asal = tempData[2];
                        String kuliah = tempData[3];

                        //tulis data ke database
                        bufferedOutput.write(nama + "," + tanggal + "," + asal + "," + kuliah);

                    }

                }else{
                    //copy data
                    bufferedOutput.write(data);
                    bufferedOutput.newLine();
                }

            } else {
                //copy data
                bufferedOutput.write(data);
                bufferedOutput.newLine();
            }

            data = bufferedInput.readLine();

        }
        bufferedOutput.flush();
        //tutup file
        bufferedInput.close();
        bufferedOutput.close();
        fileInput.close();
        fileOutput.close();
        //jalankan method
        System.gc();
        //delete original file
        database.delete();
        //rename file yg baru
        tempDB.renameTo(database);


    }

    private static boolean getYesorNo(String messages) {
        Scanner terminalInput = new Scanner(System.in);
        System.out.print("\n"+messages+"(ya/ora)");
        String pilihanUser = terminalInput.next();

        while(!pilihanUser.equalsIgnoreCase("ya") && !pilihanUser.equalsIgnoreCase("ora")){
            System.err.println("Pilihane ko udu ya/ora\npilih sing nggenah ya/ora");
            System.out.print("\n"+messages+"(ya/ora)");
            pilihanUser = terminalInput.next();
        }

        return pilihanUser.equalsIgnoreCase("ya");


    }

    private static void clearScreen(){
        try {
            if (System.getProperty("os.name").contains("Windows")){
                new ProcessBuilder("cmd","/c","cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033\143");
            }
        } catch (Exception ex) {
            System.err.println("tidak bisa clear screen");
        }
    }
}
