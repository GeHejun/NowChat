package com.ghj.chat.protocol;

import java.util.ArrayList;
import java.util.List;

/**
 * @author GeHejun
 * @date 2019-06-24
 */
public class ProtoGenerateClass {
    private static final String PROTOC_FILE = "C:\\Users\\gehj\\Downloads\\protoc-3.8.0-win64\\bin\\protoc.exe";
    private static final String IMPOR_TPROTO = "C:\\Users\\gehj\\IdeaProjects\\qr\\2\\NowChat";
    private static final String JAVA_OUT = "C:\\Users\\gehj\\IdeaProjects\\qr\\2\\NowChat\\NowChat-Chat\\src\\main\\java\\com\\ghj\\chat\\protocol\\";
    private static final String protos = "C:\\Users\\gehj\\IdeaProjects\\qr\\2\\NowChat\\NowChat-Chat\\src\\main\\java\\com\\ghj\\chat\\protocol\\message.proto";

    public static void main(String[] args) {
        List<String> lCommand = new ArrayList<>();
        lCommand.add(PROTOC_FILE);
        lCommand.add("-I=" + IMPOR_TPROTO );
        lCommand.add("--java_out=" + JAVA_OUT);
        lCommand.add(protos);

        Cmd cmd = new Cmd();
        cmd.execute(lCommand);
    }

}
