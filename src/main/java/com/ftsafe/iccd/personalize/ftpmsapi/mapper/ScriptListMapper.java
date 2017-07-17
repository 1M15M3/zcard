package com.ftsafe.iccd.personalize.ftpmsapi.mapper;

public class ScriptListMapper {
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFileHash() {
		return fileHash;
	}

	public void setFileHash(String fileHash) {
		this.fileHash = fileHash;
	}

	public String getFileDownloadPath() {
		return fileDownloadPath;
	}

	public void setFileDownloadPath(String fileDownloadPath) {
		this.fileDownloadPath = fileDownloadPath;
	}

	private String fileName; // 脚本名称
	private String fileType; // 脚本类型
	private String fileHash; // 脚本哈希值
	private String fileDownloadPath; // 文件路径
	
	public enum ScriptType {
		CHECK_SCRIPT ("checkScript"),
		PERSONALIZE_SCRIPT ("personalScript"),
		HUIPAN_SCRIPT ("backToDisk"),
		DLL ("dynamicLibrary");
		
		private final String type;
		
		ScriptType(String type){
			this.type = type;
		}
		
		private String getType(){
			return type;
		}
		
		public static ScriptType getEnum(String type)
		{
			for (ScriptType s : ScriptType.values()) {
				if (type.equals(s.getType()))
					return s;
			}
			return null;
		}
	}
}
