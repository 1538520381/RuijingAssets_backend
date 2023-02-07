package com.ruijing.assets.enume.exception;


/**
 * @author K0n9D1KuA
 * @version 1.0
 * @description: 项目自定义异常发生错误常量汇总
 * @email 3161788646@qq.com
 * @date 2023/1/31 16:11
 */

public enum RuiJingExceptionEnum {

    NO_JURISDICTION("没有权限访问该接口", 100010),
    PASSWORD_OR_USERNAME_WRONG("用户名或密码错误", 100011),
    NO_THIS_USER("用户名不存在！", 100012),
    INVALID_TOKEN("token不合法或者token已超时", 100013),
    NEED_LOGIN("需要登录才能访问相关资源！", 100014),
    LOGIN_FAILED("登录失败", 100015),
    UPLOAD_FILE_FAILED("文件上传失败", 100016),
    DELETE_FILE_FAILED("文件删除失败", 100017),
    ROLE_ALREADY_BIND("该角色已被用户绑定，请先移除绑定关系后再删除！", 100018),
    UNCLEAR_EXCEPTION("系统未知异常！", 100019),
    ADD_DUPLICATION("请勿重复收藏哦  亲~", 100020),
    LOGOUT_FAILED("退出登录失败",100021);
    //状态码
    private final int code;
    //状态消息
    private final String msg;

    //构造方法
    RuiJingExceptionEnum(String msg, int code) {
        this.code = code;
        this.msg = msg;
    }

    //getOpenID / set

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
