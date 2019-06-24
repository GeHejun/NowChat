package com.ghj.common.protocol;

import java.util.ArrayList;
import java.util.List;

public class ProtoGenerateClass {
    private static final String PROTOC_FILE = "D:/tool/protoc/protoc-3.0.0-win32/bin/protoc.exe";
    private static final String IMPOR_TPROTO = "C:\\Users\\gehj\\IdeaProjects\\qr\\2\\NowChat";
    private static final String JAVA_OUT = "C:\\Users\\gehj\\IdeaProjects\\qr\\2\\NowChat\\NowChat-Common\\src\\main\\java\\com\\ghj\\common\\protocol";
    private static final String protos = "C:\\Users\\gehj\\IdeaProjects\\qr\\2\\NowChat\\NowChat-Common\\src\\main\\java\\com\\ghj\\common\\protocol\\*.proto";

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
