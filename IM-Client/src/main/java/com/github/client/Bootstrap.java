package com.github.client;

import com.github.client.chat.NettyClient;
import com.github.client.command.CommandHandler;

import java.util.Scanner;

/**
 * @Description:
 * @Author: July
 * @Date: 2021-11-23 17:30
 **/
public class Bootstrap {
    private static CommandHandler commandHandler = new CommandHandler();

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (true){
            String input = in.nextLine();
            input = input.trim();
            String[] content = input.split(" +");
            if (content.length > 1) {
                String command = content[0];
                String[] params = new String[content.length-1];
                System.arraycopy(content,1,params,0,params.length);
                commandHandler.handle(command, params);
            }else {
                commandHandler.handle(input);
            }
        }

    }
}
