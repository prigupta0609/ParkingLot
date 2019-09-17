package com.company.parkinglot;

import com.company.parkinglot.service.RequestHandler;
import com.company.parkinglot.validate.RequestValidator;

import java.io.*;

public class Main {

    public static void main (String[] args) {
        BufferedReader bufferReader = null;
        String input = null;
        try {
            switch (args.length) {
                case 0: {
                    System.out.println("Welcome to parking lot! Type exit to stop the system");
                    while (true) {
                        try {
                            bufferReader = new BufferedReader(new InputStreamReader(System.in));
                            input = bufferReader.readLine();
                            if (input.equalsIgnoreCase("exit")) {
                                break;
                            } else {
                                if (RequestValidator.isValidRequest(input)) {
                                    try {
                                        RequestHandler.getInstance().handle(input);
                                    } catch (Exception e) {
                                        System.out.println(e.getMessage());
                                    }
                                }
                            }
                        } catch (IOException e) {
                            System.out.println("Couldn't read input");
                        }
                    }
                }
                break;
                case 1: {
                    File inputFile = new File(args[0]);
                    try {
                        bufferReader = new BufferedReader(new FileReader(inputFile));
                        int lineNo = 1;
                        while ((input = bufferReader.readLine()) != null) {
                            input = input.trim();
                            if (RequestValidator.isValidRequest(input)) {
                                try {
                                    RequestHandler.getInstance().handle(input);
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                            } else {
                                System.out.println("Incorrect command at line : " + lineNo + " , Input : " + input);
                            }
                            lineNo++;
                        }
                    } catch (Exception e) {
                        System.out.println("File not found");
                    }
                }
                break;
                default:
                    System.out.println("Invalid input");
            }
        } finally {
            try {
                if (bufferReader != null)
                    bufferReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}