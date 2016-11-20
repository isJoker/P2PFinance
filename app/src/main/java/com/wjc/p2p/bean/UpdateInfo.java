package com.wjc.p2p.bean;

/**
 * 最新版本相关信息的类
 *
 */
public class UpdateInfo {

	private String version;// 版本号
	private String apkUrl;// apk的下载路径
	private String desc;// 升级描述信息

	public UpdateInfo(String version, String apkUrl, String desc) {
		super();
		this.version = version;
		this.apkUrl = apkUrl;
		this.desc = desc;
	}

	public UpdateInfo() {
		super();
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getApkUrl() {
		return apkUrl;
	}

	public void setApkUrl(String apkUrl) {
		this.apkUrl = apkUrl;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Override
	public String toString() {
		return "UpdateInfo [version=" + version + ", apkUrl=" + apkUrl
				+ ", desc=" + desc + "]";
	}
}
