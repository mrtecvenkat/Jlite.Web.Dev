package org.jlite.core.testmodel;

import java.io.File;

import org.jlite.core.jlite.TestKeywords;

public class FileSystem {
	
	private String input_Dir;
	private String output_Dir;
	private String resourse_Dir;
	private String logfile_Dir;
	private String textfile_Dir;
	private String excelfile_Dir;
	private String wordfile_Dir;
	private String xmlfile_Dir;
	private String imagesfile_Dir;
	private String htmlfile_Dir;
	
	public FileSystem(String inputdir,String outputdir,String resoursedir, String runfoldername) {
		
		
		this.setInput_Dir(inputdir);
		this.setOutput_Dir(outputdir);
		this.setResourse_Dir(resoursedir);
		this.setTextfile_Dir(outputdir+"\\"+runfoldername+"\\"+TestKeywords.FOLDER_NAME_TEXT_REPORT);
		 new File(this.getTextfile_Dir()).mkdirs();
		this.setExcelfile_Dir(outputdir+"\\"+runfoldername+"\\"+TestKeywords.FOLDER_NAME_EXCEL_REPORT);
		 new File(this.getExcelfile_Dir()).mkdirs();
		this.setWordfile_Dir(outputdir+"\\"+runfoldername+"\\"+TestKeywords.FOLDER_NAME_WORD_REPORT);
		 new File(this.getWordfile_Dir()).mkdirs();
		this.setXmlfile_Dir(outputdir+"\\"+runfoldername+"\\"+TestKeywords.FOLDER_NAME_XML_REPORT);
		 new File(this.getXmlfile_Dir()).mkdirs();
		this.setHtmlfile_Dir(outputdir+"\\"+runfoldername+"\\"+TestKeywords.FOLDER_NAME_HTML_REPORT);
		 new File(this.getHtmlfile_Dir()).mkdirs();
		 
	}
	public String getInput_Dir() {
		return input_Dir;
	}
	public void setInput_Dir(String input_Dir) {
		this.input_Dir = input_Dir;
	}
	public String getOutput_Dir() {
		return output_Dir;
	}
	public void setOutput_Dir(String output_Dir) {
		this.output_Dir = output_Dir;
	}
	public String getResourse_Dir() {
		return resourse_Dir;
	}
	public void setResourse_Dir(String resourse_Dir) {
		this.resourse_Dir = resourse_Dir;
	}
	public String getLogfile_Dir() {
		return logfile_Dir;
	}
	public void setLogfile_Dir(String logfile_Dir) {
		this.logfile_Dir = logfile_Dir;
	}
	public String getTextfile_Dir() {
		return textfile_Dir;
	}
	public void setTextfile_Dir(String textfile_Dir) {
		this.textfile_Dir = textfile_Dir;
	}
	public String getExcelfile_Dir() {
		return excelfile_Dir;
	}
	public void setExcelfile_Dir(String excelfile_Dir) {
		this.excelfile_Dir = excelfile_Dir;
	}
	public String getWordfile_Dir() {
		return wordfile_Dir;
	}
	public void setWordfile_Dir(String wordfile_Dir) {
		this.wordfile_Dir = wordfile_Dir;
	}
	public String getXmlfile_Dir() {
		return xmlfile_Dir;
	}
	public void setXmlfile_Dir(String xmlfile_Dir) {
		this.xmlfile_Dir = xmlfile_Dir;
	}
	public String getImagesfile_Dir() {
		return imagesfile_Dir;
	}
	public void setImagesfile_Dir(String imagesfile_Dir) {
		this.imagesfile_Dir = imagesfile_Dir;
	}
	public String getHtmlfile_Dir() {
		return htmlfile_Dir;
	}
	public void setHtmlfile_Dir(String htmlfile_Dir) {
		this.htmlfile_Dir = htmlfile_Dir;
	}
	
	
	
}
