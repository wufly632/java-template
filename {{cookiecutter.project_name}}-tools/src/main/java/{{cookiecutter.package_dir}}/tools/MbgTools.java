package com.coraool.bingoplus.dgug.dashboard.tools;

import org.mybatis.generator.api.ShellRunner;

public class MbgTools {

    public static void main(String[] args) {
        /**
         * 需要指定
         * -configfile 绝对路径
         */
        String[] arys = new String[]{
                "-configfile", "dgug-dashboard-tools/src/main/resources/mbg_hologres.xml"
        };
        ShellRunner.main(arys);
    }
}
