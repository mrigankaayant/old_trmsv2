package com.ayantsoft.trms.service;

import java.io.File;


public class DirectoryCreationService {
	
	
	public static boolean createDirectories(String rootPath, String rootDirName)
	{
		boolean isCreate=false;
		try{
			 String diringenerate[] = {"enrollment_form","offerletter_form","feedback_form","datasheet_form","referencecheck_form","paymentdeclaration_form"};
			 String dirinupload[] = {"enrollment_form","offerletter_form","feedback_form","datasheet_form","referencecheck_form","paymentdeclaration_form","resume"};
		     boolean d = new File(rootPath+"/"+rootDirName).mkdir();
			 boolean s = new File(rootPath+"/"+rootDirName+"/generate").mkdir();
			 boolean w = new File(rootPath+"/"+rootDirName+"/upload").mkdir();
			 for(int i=0;i<diringenerate.length;i++){
				 new File(rootPath+"/"+rootDirName+"/generate/"+diringenerate[i]).mkdir(); 
			 }
			 
			 for(int i=0;i<dirinupload.length;i++){
				 new File(rootPath+"/"+rootDirName+"/upload/"+dirinupload[i]).mkdir(); 
			 }
			 isCreate = true;
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return isCreate; 
	}

}