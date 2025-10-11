package org.huazhi.util;

public interface CommonConstants {

	String VALID_CLIENT_ID = "myclient";

    String VALID_CLIENT_SECRET = "mysecret";

	/**
	 * 菜单
	 */
	Integer MENU = 1;

	/**
	 * 按钮
	 */
	Integer ACTION_BUTTON = 2;

	/**
	 * 生效时间
	 */
    long EXPIRES_IN = 3600L;

	/**
	 * 刷新 token 生效时间
	 */
	long REFRESH_EXPIRES_IN = 7200L;

	/**
	 * 成功标记
	 */
	Integer SUCCESS = 10200;

	/**
	 * 失败标记
	 */
	Integer FAIL = 10400;

	/**
	 * 安全策略
	 */
	String SYS_SECURITY_POLICY = "sys_security_policy";

	/**
	 * 用户登录密码错误锁定时长（分钟）
	 */
	String SYS_LOGIN_LOCKTIME = "sysLoginMaxLockTime";

	/**
	 * 用户登录密码错误最大尝试次数
	 */
	String SYS_LOGIN_MAXTRYCOUNT = "sysLoginMaxTryCount";

	/**
	 * 登录超时
	 */
	String SYS_OVER_TIME = "sysOvertime";

	/**
	 * 密码修改周期
	 */
	String SYS_PASS_CHANE = "sysPassChange";

}
