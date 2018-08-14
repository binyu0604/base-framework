package com.liugh.base;

/**
 * @author liugh
 * @since 2018-05-03
 */
public interface Constant {

    int BYTE_BUFFER = 1024;

    /**
     * 用户注册默认角色
     */
    int DEFAULT_REGISTER_ROLE = 5;

    int BUFFER_MULTIPLE = 10;

    //验证码过期时间
    Long PASS_TIME =  50000 * 60 *1000L;
    /**
     * 普通用户
     */
    String ORDINARY_ROLE= "user";

    /**
     * 系统管理员
     */
    String SYS_ASMIN_ROLE= "admin";

    //根菜单节点
    String ROOT_MENU = "0";

    //菜单类型，1：菜单  2：按钮操作
    int TYPE_MENU = 1;

    //菜单类型，1：菜单  2：按钮操作
    int TYPE_BUTTON = 2;

    //启用
    int ENABLE = 1;
    //禁用
    int DISABLE = 0;

    // 存储在header中，当前用户的key名称
    String CURRENT_USER_KEY = "currentUser";

    interface FilePostFix{
       String ZIP_FILE =".zip";

       String [] IMAGES ={"jpg", "jpeg", "JPG", "JPEG", "gif", "GIF", "bmp", "BMP", "png"};
       String [] ZIP ={"ZIP","zip","rar","RAR"};
       String [] VIDEO ={"mp4","MP4","mpg","mpe","mpa","m15","m1v", "mp2","rmvb"};
       String [] APK ={"apk","exe"};
       String [] OFFICE ={"xls","xlsx","docx","doc","ppt","pptx"};

    }
    interface FileType{
        int FILE_IMG = 1;
        int FILE_ZIP = 2;
        int FILE_VEDIO= 3;
        int FILE_APK = 4;
        int FIVE_OFFICE = 5;
        String FILE_IMG_DIR= "/img/";
        String FILE_ZIP_DIR= "/zip/";
        String FILE_VEDIO_DIR= "/video/";
        String FILE_APK_DIR= "/apk/";
        String FIVE_OFFICE_DIR= "/office/";
    }


}
