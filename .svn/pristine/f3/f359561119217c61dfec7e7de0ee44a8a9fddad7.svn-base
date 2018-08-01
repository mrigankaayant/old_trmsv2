package com.ayantsoft.trms.service;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import com.ayantsoft.trms.hibernate.pojo.Candidate;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.List;
import com.lowagie.text.ListItem;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfCell;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.GrayColor;

public class PdfGenerator {


	public static void createenrollmentFormPdf(Candidate candidate,int year,String courses,String enrolledmentDate,String ttnTrainingDate,String txnId,String paymentDate,String folderPathWithFile,String paidAmount)
	{
		try
		{
			Document doc = new Document(PageSize.A4);
			File f1=new File(folderPathWithFile);
			OutputStream file = new FileOutputStream(f1);

			PdfWriter writer = PdfWriter.getInstance(doc, file);

			HeaderFooter header = new HeaderFooter(new Phrase("GLOBAL IT EXPERTS INC TRAINING ENROLLMENT FORM" ,FontFactory.getFont(FontFactory.HELVETICA,10 )), false);  


			HeaderFooter footer = new HeaderFooter(new Phrase("Global IT Experts Inc.-All Rights Reserved "),false );  
			header.setAlignment(1);
			footer.setAlignment(1);

			doc.setHeader(header); 
			doc.setFooter(footer); 
			Font fontHead = FontFactory.getFont("Times-Roman", 12, Font.BOLD|Font.UNDERLINE);    
			Font fontcontent = FontFactory.getFont("Times-Roman", 10);    

			doc.open();

			Paragraph para1 = new Paragraph("Reference # GITES"+year+"/T"+candidate.getCandidateId(),fontcontent);
			para1.setAlignment(1);
			para1.setSpacingBefore(1);
			para1.setSpacingAfter(6);
			doc.add(para1);
			PdfPTable section = new PdfPTable(1);
			PdfPCell sectionCell = new PdfPCell(new Phrase("SECTION -I(For Candidate's Use Only)"));
			section.addCell(sectionCell);
			doc.add(section);
			Paragraph sp = new Paragraph("    ");
			doc.add(sp);
			PdfPTable canInfo = new PdfPTable(1);
			PdfPCell cell1= new PdfPCell(new Phrase("CANDIDATE'S INFORMATION :"));
			canInfo.addCell(cell1);


			doc.add(canInfo);
			PdfPTable candiInfo = new PdfPTable(4);
			PdfPCell  c5=new PdfPCell(new Phrase("Student Full Name :",fontcontent));
			c5.setColspan(4);
			PdfPCell  Address=new PdfPCell(new Phrase("Address :",fontcontent));
			PdfPCell  city=new PdfPCell(new Phrase("City :",fontcontent));
			PdfPCell  state=new PdfPCell(new Phrase("State :",fontcontent));
			PdfPCell  Zip=new PdfPCell(new Phrase("Zip :",fontcontent));
			PdfPCell  country=new PdfPCell(new Phrase("Country :",fontcontent));
			PdfPCell  mobile=new PdfPCell(new Phrase("Mobile :",fontcontent));
			PdfPCell  landPhone=new PdfPCell(new Phrase("Phone :",fontcontent));
			PdfPCell  email=new PdfPCell(new Phrase("Email :",fontcontent));

			Address.setColspan(4);
			email.setColspan(2);
			candiInfo.addCell(c5);
			candiInfo.addCell(Address);
			candiInfo.addCell(city);
			candiInfo.addCell(state);
			candiInfo.addCell(Zip);
			candiInfo.addCell(country);
			candiInfo.addCell(mobile);
			candiInfo.addCell(landPhone);
			candiInfo.addCell(email);
			doc.add(candiInfo);

			PdfPTable trainingInfo = new PdfPTable(2);

			PdfPCell  heading=new PdfPCell(new Phrase("TRAINING & COURSE DETAILS :"));
			heading.setColspan(2);
			PdfPCell  course=new PdfPCell(new Phrase("Course Name :"+courses,fontcontent));
			PdfPCell  doe=new PdfPCell(new Phrase("Date of Enrollment :"+enrolledmentDate,fontcontent));
			PdfPCell  stDate=new PdfPCell(new Phrase("Tentative Training Start Date :"+ttnTrainingDate,fontcontent));
			PdfPCell  tth=new PdfPCell(new Phrase("Total Training Hours/Week :3-4Weeks",fontcontent));


			trainingInfo.addCell(heading);
			trainingInfo.addCell(course);
			trainingInfo.addCell(doe);
			trainingInfo.addCell(stDate);
			trainingInfo.addCell(tth);

			doc.add(trainingInfo);

			PdfPTable terms = new PdfPTable(1);
			PdfPCell termsCell = new PdfPCell(new Phrase("GENERAL TERMS OF SERVICE :"));
			//  termsCell.setBorder(PdfCell.NO_BORDER);
			terms.addCell(termsCell);
			terms.setSpacingAfter(4);
			doc.add(terms);

			PdfPTable contentHead = new PdfPTable(1);
			PdfPCell contentCell = new PdfPCell(new Phrase("EXHIBIT A",fontHead));
			contentCell.setBorder(PdfCell.NO_BORDER);
			contentHead.addCell(contentCell);
			doc.add(contentHead);

			PdfPTable content = new PdfPTable(1);
			PdfPCell contentCellA = new PdfPCell(new Phrase("The purpose of this form is to ensure"
					+ " that the element and commitments are in place to provide the online training delivered to the candidate by Global IT Experts Inc",fontcontent));
			contentCellA.setBorder(PdfCell.NO_BORDER);
			content.addCell(contentCellA);


			PdfPCell contentCellB = new PdfPCell(new Phrase("EXHIBIT B",fontHead));
			contentCellB.setBorder(PdfCell.NO_BORDER);
			content.addCell(contentCellB);
			PdfPCell contentCellB1 = new PdfPCell(new Phrase("Service Scope"));
			contentCellB1.setBorder(PdfCell.NO_BORDER);
			content.addCell(contentCellB1);

			PdfPCell contentCellB2 = new PdfPCell(new Phrase("1. Online training through web based software Cisco WebEx."
					+ "2. Dedicated Industry oriented trainers to provide online training in respective skill set",fontcontent));
			contentCellB2.setBorder(PdfCell.NO_BORDER);
			content.addCell(contentCellB2);
			PdfPCell contentCellB3 = new PdfPCell(new Phrase("2. Dedicated Industry oriented trainers to provide online training in respective skill set",fontcontent));
			contentCellB3.setBorder(PdfCell.NO_BORDER);
			content.addCell(contentCellB3);

			PdfPCell exC = new PdfPCell(new Phrase("EXHIBIT C",fontHead));
			exC.setBorder(PdfCell.NO_BORDER);
			content.addCell(exC);

			PdfPCell exCContent = new PdfPCell(new Phrase("Service Availability :"));
			exCContent.setBorder(PdfCell.NO_BORDER);
			content.addCell(exCContent);

			PdfPCell exCContent1 = new PdfPCell(new Phrase("Service coverage parameters specific to the services covered in this agreement are as follows :",fontcontent));
			exCContent1.setBorder(PdfCell.NO_BORDER);
			content.addCell(exCContent1);

			PdfPCell exCContent2 = new PdfPCell(new Phrase("1. Telephone Support: 10.30 AM to 6.30 PM from Monday to Friday(Central Standard Time)",fontcontent));
			exCContent2.setBorder(PdfCell.NO_BORDER);
			content.addCell(exCContent2);

			PdfPCell exCContent3 = new PdfPCell(new Phrase("2. Online Chat Support: 10.30 AM to 6.30 PM from Monday to Friday(Central Standard Time) ",fontcontent));
			exCContent3.setBorder(PdfCell.NO_BORDER);
			content.addCell(exCContent3);

			PdfPCell exCContent4 = new PdfPCell(new Phrase("3. Voicemail Support :Calls received after the office hours will be forwarded to voicemail and voicemails will be followed up the next working day ",fontcontent));
			exCContent4.setBorder(PdfCell.NO_BORDER);
			content.addCell(exCContent4);

			PdfPCell exCContent5 = new PdfPCell(new Phrase("4. Email Support:24 x 7 email support",fontcontent));
			exCContent5.setBorder(PdfCell.NO_BORDER);
			content.addCell(exCContent5);

			PdfPCell exCContent6 = new PdfPCell(new Phrase("Note:Best way to reach is via email if not reachable through phone or online chat support.",fontcontent));
			exCContent6.setBorder(PdfCell.NO_BORDER);
			content.addCell(exCContent6);


			PdfPCell exD = new PdfPCell(new Phrase("EXHIBIT D",fontHead));
			exD.setBorder(PdfCell.NO_BORDER);
			content.addCell(exD);

			PdfPCell exDcontent1 = new PdfPCell(new Phrase("1) It is the candidate's responsibility to acquire all"
					+ " necessary software if required to maintain connectivity and to combat the menace of Internet hacking, such as but not limited to anti-virus, firewalls, vpn etc",fontcontent));
			exDcontent1.setBorder(PdfCell.NO_BORDER);
			content.addCell(exDcontent1);
			PdfPCell exDcontent2 = new PdfPCell(new Phrase("2) The company is not responsible anyway for the software you are using for the training purpose and also the company is not responsible for the info "
					+ "you are providing for your background verification check.",fontcontent));
			exDcontent2.setBorder(PdfCell.NO_BORDER);
			content.addCell(exDcontent2);

			PdfPCell exDcontent3 = new PdfPCell(new Phrase("3) You will not use the information obtained as a result of this training in any way against "
					+ "Global IT Experts Inc. or its employees.",fontcontent));
			exDcontent3.setBorder(PdfCell.NO_BORDER);
			content.addCell(exDcontent3);

			PdfPCell exDcontent4 = new PdfPCell(new Phrase("4) In case the training has to be stopped or cancelled "
					+ "because of 'Acts- of-God' such as but not limited to sickness, earthquake, storm or any "
					+ "other natural disaster or reasons, Global IT Experts Inc. "
					+ "will refund to candidates prorated training fees",fontcontent));
			exDcontent4.setBorder(PdfCell.NO_BORDER);
			content.addCell(exDcontent4);

			PdfPCell exDcontent5 = new PdfPCell(new Phrase("5) If Global IT Experts Inc. cannot start the training after 30 days of the tentative start date of the training, Global IT Experts Inc. "
					+ "will refund the full money collected as training fees "
					+ "within 7 business days.",fontcontent));
			exDcontent5.setBorder(PdfCell.NO_BORDER);
			content.addCell(exDcontent5);


			PdfPCell exDcontent6 = new PdfPCell(new Phrase("6) Global IT Experts Inc. will only entertain any claims for refunds if and only if for a valid reason with supporting documents you cannot take the training "
					+ "or quit the training program and accordingly if you inform Global IT Experts Inc. within 3 calendar days of enrollment. Global IT Experts Inc. will refund you 70% of your training fees/ Security deposit which you have paid and if after 3 days of enrollment/ Training start date, you want to quit or discontinue with the training program for any reason whatsoever Global IT Experts Inc. will not refund you any money "
					+ "which you have paid as the training fees/ Security Deposit.",fontcontent));
			exDcontent6.setBorder(PdfCell.NO_BORDER);
			content.addCell(exDcontent6);


			PdfPCell exDcontent7 = new PdfPCell(new Phrase("7) Global IT Experts Inc. reserves 48 hours every month for servicing and maintenance. These 48 hours can be together spread over the 30-day cycle. If for any reason the outage of service lasts more than the scheduled hours, Global IT Experts Inc."
					+ " will compensate lost time with equal time.",fontcontent));
			exDcontent7.setBorder(PdfCell.NO_BORDER);
			content.addCell(exDcontent7);

			PdfPCell exDcontent8 = new PdfPCell(new Phrase("8) Global IT Experts Inc. reserves the right to pull any candidate out of the training with assigning a valid reason as per its own discretion. In such an incident Global IT Experts Inc."
					+ " will prorate the number of hours attended by the candidate and refunds the rest of the money on a prorate basis.",fontcontent));
			exDcontent8.setBorder(PdfCell.NO_BORDER);
			content.addCell(exDcontent8);

			PdfPCell exDcontent9 = new PdfPCell(new Phrase("9) Please understand that all communications should be via E-Mail and no E-Mails received from any other E-Mail addresses other than "
					+ "the one/ones registered while subscribing will be entertained",fontcontent));
			exDcontent9.setBorder(PdfCell.NO_BORDER);
			content.addCell(exDcontent9);

			PdfPCell exDcontent10 = new PdfPCell(new Phrase("10) Global IT Experts Inc. will entertain any issues or "
					+ "queries only from the registered candidate only. Issues of registered candidate's spouse, husband, wife, son, daughter, friends, fiance or colleagues will not be entertained at all.",fontcontent));
			exDcontent10.setBorder(PdfCell.NO_BORDER);
			content.addCell(exDcontent10);

			PdfPCell exDcontent11 = new PdfPCell(new Phrase("11) Re-Scheduling of classes will be at the sole discretion of Global IT Experts Inc. management depending on trainer's availability.",fontcontent));
			exDcontent11.setBorder(PdfCell.NO_BORDER);
			content.addCell(exDcontent11);

			PdfPCell exDcontent12 = new PdfPCell(new Phrase("12) If the candidate is absconding without any notice, Global IT Experts Inc. will not refund "
					+ "you any money which you have paid as the training fees.",fontcontent));
			exDcontent12.setBorder(PdfCell.NO_BORDER);
			content.addCell(exDcontent12);

			PdfPCell exDcontent13 = new PdfPCell(new Phrase("13) After finishing the training Global IT Experts Inc. "
					+ "agrees to assist you for Job placement as per your location preferences.",fontcontent));
			exDcontent13.setBorder(PdfCell.NO_BORDER);
			content.addCell(exDcontent13);

			PdfPCell exDcontent14 = new PdfPCell(new Phrase("14) All the payment will be process through Global IT Experts's "
					+ "partner Ayant Software Pvt. Ltd.",fontcontent));
			exDcontent14.setBorder(PdfCell.NO_BORDER);
			content.addCell(exDcontent14);

			PdfPCell exDcontent15 = new PdfPCell(new Phrase("15) The training Fee/Security deposit is only refundable in your first paycheck once "
					+ "you start working in our client's project.",fontcontent));
			exDcontent15.setBorder(PdfCell.NO_BORDER);
			content.addCell(exDcontent15);

			doc.add(content);

			Paragraph paraGap = new Paragraph(" ");
			para1.setSpacingAfter(6);
			doc.add(paraGap);

			PdfPTable canAccept = new PdfPTable(1);
			PdfPCell canAcceptCell = new PdfPCell(new Phrase("Acceptance by the Candidate:"));
			canAccept.addCell(canAcceptCell);

			PdfPCell canAcceptCellPara = new PdfPCell(new Phrase("I understand that all payments are final and "
					+ "I agree to the above 'General Terms of Service' and "
					+ "acknowledge the receipt of the service. I agree that "
					+ "I am making the total payment of $"+candidate.getTotalAmount()+" through Credit/Debit Card/eCommerce/Internet Banking/PayPal/EFT/eCheck/ as "+courses +
					"Training fees / Security Deposit only and this payment should not be mistaken as guaranteed job placement offers.",fontcontent));
			canAccept.addCell(canAcceptCellPara);

			PdfPCell canAcceptCellPara1 = new PdfPCell(new Phrase("Candidate's Signature: _____________________ Date: _______________",fontcontent));
			canAcceptCellPara1.setMinimumHeight(30f);
			canAccept.addCell(canAcceptCellPara1);
			doc.add(canAccept);
			Paragraph paraGap1 = new Paragraph(" ");
			para1.setSpacingAfter(6);
			doc.add(paraGap1);

			PdfPTable section2 = new PdfPTable(1);
			PdfPCell section2Cell = new PdfPCell(new Phrase("SECTION -II(For Payee's Use Only)"));
			section2.addCell(section2Cell);

			doc.add(section2);
			Paragraph paraGap2 = new Paragraph(" ");
			para1.setSpacingAfter(6);
			doc.add(paraGap2);

			PdfPTable payeeInfo = new PdfPTable(4);
			PdfPCell  payeeInfoCell1=new PdfPCell(new Phrase("PAYEE'S INFORMATION :"));
			payeeInfoCell1.setColspan(4);
			PdfPCell  pName=new PdfPCell(new Phrase("Payee Full Name:",fontcontent));

			PdfPCell  pAddress=new PdfPCell(new Phrase("Address :",fontcontent));
			PdfPCell  pCity=new PdfPCell(new Phrase("City :",fontcontent));
			PdfPCell  pState=new PdfPCell(new Phrase("State :",fontcontent));
			PdfPCell  pZip=new PdfPCell(new Phrase("Zip :",fontcontent));
			PdfPCell  pCountry=new PdfPCell(new Phrase("Country :",fontcontent));
			PdfPCell  pMobile=new PdfPCell(new Phrase("Mobile :",fontcontent));
			PdfPCell  pLandPhone=new PdfPCell(new Phrase("Phone :",fontcontent));
			PdfPCell  pEmail=new PdfPCell(new Phrase("Email :",fontcontent));

			pName.setColspan(4);
			pAddress.setColspan(4);
			pEmail.setColspan(2);

			payeeInfo.addCell(payeeInfoCell1);
			payeeInfo.addCell(pName);
			payeeInfo.addCell(pAddress);
			payeeInfo.addCell(pCity);
			payeeInfo.addCell(pState);
			payeeInfo.addCell(pZip);
			payeeInfo.addCell(pCountry);
			payeeInfo.addCell(pMobile);
			payeeInfo.addCell(pLandPhone);
			payeeInfo.addCell(pEmail);
			doc.add(payeeInfo);

			doc.newPage(); 


			Paragraph para2 = new Paragraph("Reference # GITES"+year+"/T"+candidate.getCandidateId(),fontcontent);
			para2.setAlignment(1);
			para2.setSpacingBefore(1);
			para2.setSpacingAfter(6);
			doc.add(para2);


			PdfPTable accPaye = new PdfPTable(1);
			PdfPCell accPayeCell = new PdfPCell(new Phrase("Acceptance by the Payee:"));
			accPayeCell.setBorder(PdfCell.NO_BORDER);
			accPaye.addCell(accPayeCell);

			PdfPCell accPayeCell1 = new PdfPCell(new Phrase("I understand that all payments are final and I agree to the above 'General Terms of Service' "
					+ "and acknowledge the receipt of the service",fontcontent));
			accPayeCell1.setBorder(PdfCell.NO_BORDER);
			accPaye.addCell(accPayeCell1);

			PdfPCell accPayeCell2 = new PdfPCell(new Phrase("I __________________________________ hereby, "
					+ "declare that the following payment has been made by me on"
					+ " behalf of ___________________________________________ (Candidate's Name/Self) towards the training program/ Direct Marketing "
					+ "via ___________________ (Payment Mode) on ",fontcontent));
			accPayeCell2.setBorder(PdfCell.NO_BORDER);
			accPaye.addCell(accPayeCell2);

			doc.add(accPaye);
			Paragraph paraGap3 = new Paragraph(" ");
			paraGap3.setSpacingAfter(6);
			doc.add(paraGap3);

			PdfPTable payeSec = new PdfPTable(1);
			PdfPCell payeSecCell1 = new PdfPCell(new Phrase("Payment Date : "+paymentDate));
			payeSecCell1.setBorder(PdfCell.NO_BORDER);
			payeSec.addCell(payeSecCell1);

			PdfPCell payeSecCell2 = new PdfPCell(new Phrase("Amount Payable : $"+candidate.getTotalAmount()));
			payeSecCell2.setBorder(PdfCell.NO_BORDER);
			payeSec.addCell(payeSecCell2);

			PdfPCell payeSecCell3 = new PdfPCell(new Phrase("Amount Paid : $"+paidAmount));
			payeSecCell3.setBorder(PdfCell.NO_BORDER);
			payeSec.addCell(payeSecCell3);

			PdfPCell payeSecCell4 = new PdfPCell(new Phrase("Transaction ID : "+txnId));
			payeSecCell4.setBorder(PdfCell.NO_BORDER);
			payeSec.addCell(payeSecCell4);

			PdfPCell payeSecCell5 = new PdfPCell(new Phrase("Due Amount : NA"));
			payeSecCell5.setBorder(PdfCell.NO_BORDER);
			payeSec.addCell(payeSecCell5);

			PdfPCell payeSecCell6 = new PdfPCell(new Phrase("Due Date : NA"));
			payeSecCell6.setBorder(PdfCell.NO_BORDER);
			payeSec.addCell(payeSecCell6);

			PdfPCell payeSecCell7 = new PdfPCell(new Phrase("Payment Mode : Paypal"));
			payeSecCell7.setBorder(PdfCell.NO_BORDER);
			payeSec.addCell(payeSecCell7);

			doc.add(payeSec);

			Paragraph paraGap4 = new Paragraph("   ");
			doc.add(paraGap4);

			PdfPTable idProof = new PdfPTable(2);
			idProof.setWidthPercentage(100);
			PdfPCell idProofCell1 = new PdfPCell(new Phrase("Candidate's ID Proof"
					+ "                                                                                                    "
					+ "While scanning this document,pleace attach your ID proof here.",fontcontent));
			idProofCell1.setMinimumHeight(250f);
			idProof.addCell(idProofCell1);
			PdfPCell idProofCell2 = new PdfPCell(new Phrase("Payee's ID Proof  "
					+ "                                                                                                     "
					+ " While scanning this document,please attach your ID proof here        ",fontcontent));
			idProof.addCell(idProofCell2);
			idProofCell2.setMinimumHeight(250f);
			doc.add(idProof);

			Paragraph paye1 = new Paragraph("Payee's Signature:______________________Date:_____",fontcontent);
			paye1.setAlignment(0);
			paye1.setSpacingBefore(1);
			paye1.setSpacingAfter(6);
			doc.add(paye1);

			Paragraph paye2 = new Paragraph("Candidate's Signature:______________________Date:_____",fontcontent);
			paye2.setAlignment(0);
			paye2.setSpacingBefore(1);
			paye2.setSpacingAfter(6);
			doc.add(paye2);

			doc.close();
			file.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}

	}





	public static void createInvoicePdf(String createdDate,String courseName,String trainerName,String batchno,String batchDescription,String trainingstatus,String folderPathWithFile,String noOfDays,String noOfParticipants,String unitRate,String totalAmount,String panNo,String tBankName,String tBankBranch,String tIFSCode,String tAccountNo,String invoiceId)
	{
		try{
			Document doc = new Document(PageSize.A4);
			File f1=new File(folderPathWithFile);
			OutputStream file = new FileOutputStream(f1);
			PdfWriter writer = PdfWriter.getInstance(doc, file);

			Font fontHead = FontFactory.getFont("Times-Roman", 14, Font.BOLD);    
			Font fontHead1 = FontFactory.getFont("Times-Roman", 14, Font.BOLD|Font.UNDERLINE);  

			Font fontcontent = FontFactory.getFont("Times-Roman", 14);    
			Font fontcontent1 = FontFactory.getFont("Times-Roman", 12);    


			doc.open();
			Paragraph paraDate = new Paragraph(" Date : "+createdDate,fontHead);
			paraDate.setAlignment(Element.ALIGN_RIGHT);
			doc.add(paraDate);

			Paragraph invoiceNumber = new Paragraph("                 Invoice number : "+invoiceId,fontHead);
			invoiceNumber.setAlignment(Element.ALIGN_RIGHT);
			doc.add(invoiceNumber);


			Paragraph to = new Paragraph("To,",fontHead);
			doc.add(to);
			Paragraph subHead = new Paragraph("   Ayant Software Pvt. Ltd.",fontHead);
			doc.add(subHead);
			Paragraph subHeadLine1 = new Paragraph("   AC 30, Sector 1,",fontcontent);
			doc.add(subHeadLine1);
			Paragraph subHeadLine2 = new Paragraph("   Salt Lake,",fontcontent);
			doc.add(subHeadLine2);
			Paragraph subHeadLine3 = new Paragraph("   Kolkata - 700064",fontcontent);
			doc.add(subHeadLine3);

			Paragraph gap = new Paragraph("    ");
			doc.add(gap);

			Paragraph gap1 = new Paragraph("    ");
			doc.add(gap1);


			Paragraph course = new Paragraph("Course: "+courseName,fontHead);
			doc.add(course);
			Paragraph trainer = new Paragraph("Trainer: "+trainerName,fontHead);
			doc.add(trainer);
			Paragraph batchNo  = new Paragraph("Batch No: "+batchno,fontHead);
			doc.add(batchNo );
			Paragraph trainingStatus = new Paragraph("Training Status: "+trainingstatus,fontHead);
			doc.add(trainingStatus);

			Paragraph gap2 = new Paragraph("    ");
			doc.add(gap2);

			PdfPTable section = new PdfPTable(5);
			section.setHorizontalAlignment(Element.ALIGN_LEFT);
			section.setWidthPercentage(100);

			PdfPCell sectionCell1 = new PdfPCell(new Phrase("Description",fontcontent1));
			sectionCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			section.addCell(sectionCell1);

			PdfPCell sectionCell2 = new PdfPCell(new Phrase("No of Days",fontcontent1));
			sectionCell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			section.addCell(sectionCell2);

			PdfPCell sectionCell3 = new PdfPCell(new Phrase("No of Participant",fontcontent1));
			sectionCell3.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			section.addCell(sectionCell3);

			PdfPCell sectionCell4 = new PdfPCell(new Phrase("Unit Rate(Rs.)",fontcontent1));
			sectionCell4.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			section.addCell(sectionCell4);

			PdfPCell sectionCell5 = new PdfPCell(new Phrase("Total(Rs.)",fontcontent1));
			sectionCell5.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			section.addCell(sectionCell5);


			PdfPCell sectionCell6 = new PdfPCell(new Phrase(batchDescription,fontcontent1));
			sectionCell6.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			section.addCell(sectionCell6);

			PdfPCell sectionCell7 = new PdfPCell(new Phrase(noOfDays,fontcontent1));
			sectionCell7.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			section.addCell(sectionCell7);

			PdfPCell sectionCell8 = new PdfPCell(new Phrase(noOfParticipants,fontcontent1));
			sectionCell8.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			section.addCell(sectionCell8);

			PdfPCell sectionCell9 = new PdfPCell(new Phrase(unitRate+" INR",fontcontent1));
			sectionCell9.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			section.addCell(sectionCell9);

			PdfPCell sectionCell10 = new PdfPCell(new Phrase(totalAmount+" INR",fontcontent1));
			sectionCell10.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			section.addCell(sectionCell10);

			doc.add(section);

			Paragraph trainerDetails = new Paragraph("Trainer Personal Details :",fontHead);
			trainerDetails.setAlignment(Element.ALIGN_LEFT);
			doc.add(trainerDetails);

			Paragraph pan = new Paragraph("PAN No.-"+panNo,fontcontent1);
			pan.setAlignment(Element.ALIGN_LEFT);
			doc.add(pan);

			Paragraph gap3 = new Paragraph("  ");
			doc.add(gap3);
			Paragraph gap4 = new Paragraph("  ");
			doc.add(gap4);

			Paragraph trainerBankDetails = new Paragraph("Trainer Bank Details :",fontHead);
			trainerBankDetails.setAlignment(Element.ALIGN_LEFT);
			doc.add(trainerBankDetails);


			Paragraph bankName = new Paragraph("Bank Name – "+tBankName,fontcontent1);
			bankName.setAlignment(Element.ALIGN_LEFT);
			doc.add(bankName);

			Paragraph branch = new Paragraph("Branch  – "+tBankBranch,fontcontent1);
			branch.setAlignment(Element.ALIGN_LEFT);
			doc.add(branch);


			Paragraph ifsc = new Paragraph("IFS Code - "+tIFSCode,fontcontent1);
			ifsc.setAlignment(Element.ALIGN_LEFT);
			doc.add(ifsc);

			Paragraph bankAccount = new Paragraph("A/C number - "+tAccountNo,fontcontent1);
			bankAccount.setAlignment(Element.ALIGN_LEFT);
			doc.add(bankAccount);

			Paragraph g4 = new Paragraph("    ");
			doc.add(g4);

			Paragraph g5 = new Paragraph("    ");
			doc.add(g5);

			Paragraph signature = new Paragraph("Trainer's Signature:______________________",fontHead);
			signature.setAlignment(Element.ALIGN_RIGHT);
			doc.add(signature);

			Paragraph signatureDate = new Paragraph("Date:_____________________________",fontHead);
			signatureDate.setAlignment(Element.ALIGN_RIGHT);
			doc.add(signatureDate);

			doc.close();	
			file.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}




	public static void createFeedbackForm(String candidateName,String courseName,String batchCode,String trainerName,String timings,String startDate,String endDate,String folderPathWithFile )
	{
		try{
			Document doc = new Document(PageSize.A4);
			File f1=new File(folderPathWithFile);
			OutputStream file = new FileOutputStream(f1);
			PdfWriter writer = PdfWriter.getInstance(doc, file);

			Font fontHead = FontFactory.getFont("Times-Roman", 12, Font.BOLD|Font.UNDERLINE);    

			Font fontcontent = FontFactory.getFont("Times-Roman", 10);    
			doc.open();


			PdfPTable section = new PdfPTable(1);
			PdfPCell sectionCell1 = new PdfPCell(new Phrase("TRAINING COMPLETION & FEEDBACK FORM",fontHead));
			PdfPCell sectionCell2 = new PdfPCell(new Phrase("We Value Your Feedback",fontcontent));
			sectionCell1.setHorizontalAlignment(0);
			sectionCell2.setHorizontalAlignment(0); 	
			sectionCell1.setBorder(PdfPCell.NO_BORDER);
			sectionCell2.setBorder(PdfPCell.NO_BORDER);
			sectionCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			sectionCell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

			section.addCell(sectionCell1);
			section.addCell(sectionCell2);
			doc.add(section);
			Paragraph paraGap1 = new Paragraph(" ");
			paraGap1.setSpacingAfter(3);
			doc.add(paraGap1);

			PdfPTable participentInfo = new PdfPTable(2);
			PdfPCell  participentInfoCell1=new PdfPCell(new Phrase("Participant's Name : "+candidateName,fontcontent));
			participentInfoCell1.setColspan(2);
			PdfPCell  participentInfoCell2=new PdfPCell(new Phrase("Course Title : "+courseName,fontcontent));

			PdfPCell  participentInfoCell3=new PdfPCell(new Phrase("Batch code : "+batchCode,fontcontent));
			PdfPCell  participentInfoCell4=new PdfPCell(new Phrase("Trainer's Name : "+trainerName,fontcontent));


			PdfPCell  participentInfoCell5=new PdfPCell(new Phrase("Timings : "+timings,fontcontent));

			PdfPCell  participentInfoCell6=new PdfPCell(new Phrase("Start Date : "+startDate,fontcontent));
			PdfPCell  participentInfoCell7=new PdfPCell(new Phrase("End Date : "+endDate,fontcontent));

			PdfPCell  participentInfoCell8=new PdfPCell(new Phrase("Transaction ID#:",fontcontent));

			PdfPCell  participentInfoCell9=new PdfPCell(new Phrase("Form Ref#:",fontcontent));

			participentInfo.addCell(participentInfoCell1);
			participentInfo.addCell(participentInfoCell2);
			participentInfo.addCell(participentInfoCell3);
			participentInfo.addCell(participentInfoCell4);
			participentInfo.addCell(participentInfoCell5);
			participentInfo.addCell(participentInfoCell6);
			participentInfo.addCell(participentInfoCell7);
			participentInfo.addCell(participentInfoCell8);
			participentInfo.addCell(participentInfoCell9);
			participentInfo.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

			doc.add(participentInfo);

			Paragraph paraGap3 = new Paragraph(" ");
			paraGap3.setSpacingAfter(6);
			doc.add(paraGap3);

			PdfPTable infoTable = new PdfPTable(1);
			PdfPCell  infoTableCell1=new PdfPCell(new Phrase("By signing this form ,I_________________________(Candidates Name) hereby confirm that,the above committed training services has been delivered to me by Global IT Experts,Inc"
					+ " through his training partner Ayant software Pvt.Ltd and I have successfully completed the training program. ",fontcontent));
			infoTableCell1.setColspan(1);
			infoTableCell1.setMinimumHeight(60f);
			infoTable.addCell(infoTableCell1);
			infoTable.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			doc.add(infoTable);

			Paragraph paraGap4 = new Paragraph(" ");
			paraGap4.setSpacingAfter(3);
			doc.add(paraGap4);

			PdfPTable infoTable1 = new PdfPTable(1);
			PdfPCell  infoTable1Cell1=new PdfPCell(new Phrase("Kindly rate the following parameters of the Training program on a scale of 1 to 5.Mark NA where "
					+ "not applicable to this training program. [1-Poor,2-Fair,3-Good,4-Very good,5-Excellent]",fontcontent));
			infoTable1Cell1.setColspan(1);
			infoTable1.addCell(infoTable1Cell1);
			infoTable.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			doc.add(infoTable1);

			GrayColor gray = new GrayColor(.75f);
			PdfPTable infoTable2 = new PdfPTable(2);

			infoTable2.setWidths(new float[] { 75,25 });
			PdfPCell  infoTable2Cell1=new PdfPCell(new Phrase("Parameters",fontcontent));
			infoTable2Cell1.setBackgroundColor(gray);
			PdfPCell  infoTable2Cell2=new PdfPCell(new Phrase("Rating",fontcontent));
			infoTable2Cell2.setBackgroundColor(gray);
			infoTable2.addCell(infoTable2Cell1);
			infoTable2.addCell(infoTable2Cell2);
			infoTable2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			doc.add(infoTable2);

			PdfPTable infoTable3 = new PdfPTable(1);
			PdfPCell  infoTable3Cell1=new PdfPCell(new Phrase("Section I:Course Content/Methodology",fontcontent));
			infoTable3Cell1.setBackgroundColor(gray);
			infoTable3Cell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			infoTable3.addCell(infoTable3Cell1);
			infoTable3.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			doc.add(infoTable3);

			PdfPTable infoTable4 = new PdfPTable(2);
			infoTable4.setWidths(new float[] { 75,25 });
			PdfPCell  infoTable4Cell1=new PdfPCell(new Phrase("Overall Course Coverage",fontcontent));
			PdfPCell  infoTable4Cell2=new PdfPCell(new Phrase("     ",fontcontent));
			PdfPCell  infoTable4Cell3=new PdfPCell(new Phrase("Effectiveness in Skill Upgradation",fontcontent));
			PdfPCell  infoTable4Cell4=new PdfPCell(new Phrase("     ",fontcontent));
			PdfPCell  infoTable4Cell5=new PdfPCell(new Phrase("Relating theory to practical applications",fontcontent));
			PdfPCell  infoTable4Cell6=new PdfPCell(new Phrase("     ",fontcontent));
			PdfPCell  infoTable4Cell7=new PdfPCell(new Phrase("Exercises/Case studies/Recordings",fontcontent));
			PdfPCell  infoTable4Cell8=new PdfPCell(new Phrase("     ",fontcontent));
			PdfPCell  infoTable4Cell9=new PdfPCell(new Phrase("Training Quality",fontcontent));
			PdfPCell  infoTable4Cell10=new PdfPCell(new Phrase("     ",fontcontent));
			PdfPCell  infoTable4Cell11=new PdfPCell(new Phrase("Support from the training Team",fontcontent));
			PdfPCell  infoTable4Cell12=new PdfPCell(new Phrase("     ",fontcontent));

			infoTable4.addCell(infoTable4Cell1);
			infoTable4.addCell(infoTable4Cell2);
			infoTable4.addCell(infoTable4Cell3);
			infoTable4.addCell(infoTable4Cell4);
			infoTable4.addCell(infoTable4Cell5);
			infoTable4.addCell(infoTable4Cell6);
			infoTable4.addCell(infoTable4Cell7);
			infoTable4.addCell(infoTable4Cell8);
			infoTable4.addCell(infoTable4Cell9);
			infoTable4.addCell(infoTable4Cell10);
			infoTable4.addCell(infoTable4Cell11);
			infoTable4.addCell(infoTable4Cell12);
			infoTable4.setHorizontalAlignment(PdfPCell.ALIGN_CENTER); 
			doc.add(infoTable4);

			PdfPTable infoTable5 = new PdfPTable(1);
			PdfPCell  infoTable5Cell1=new PdfPCell(new Phrase("Section II:Attributes of Instructor",fontcontent));
			infoTable5Cell1.setBackgroundColor(gray);
			infoTable5Cell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			infoTable5.addCell(infoTable5Cell1);
			infoTable5.setHorizontalAlignment(PdfPCell.ALIGN_CENTER); 
			doc.add(infoTable5);

			PdfPTable infoTable6 = new PdfPTable(2);
			infoTable6.setWidths(new float[] { 75,25 });
			PdfPCell  infoTable6Cell1=new PdfPCell(new Phrase("Communication Skills",fontcontent));
			PdfPCell  infoTable6Cell2=new PdfPCell(new Phrase("     ",fontcontent));
			PdfPCell  infoTable6Cell3=new PdfPCell(new Phrase("Presentation Skills",fontcontent));
			PdfPCell  infoTable6Cell4=new PdfPCell(new Phrase("     ",fontcontent));
			PdfPCell  infoTable6Cell5=new PdfPCell(new Phrase("Interaction with participants",fontcontent));
			PdfPCell  infoTable6Cell6=new PdfPCell(new Phrase("     ",fontcontent));
			PdfPCell  infoTable6Cell7=new PdfPCell(new Phrase("Domain Knowledge",fontcontent));
			PdfPCell  infoTable6Cell8=new PdfPCell(new Phrase("     ",fontcontent));
			PdfPCell  infoTable6Cell9=new PdfPCell(new Phrase("Adherence to schedule",fontcontent));
			PdfPCell  infoTable6Cell10=new PdfPCell(new Phrase("     ",fontcontent));


			infoTable6.addCell(infoTable6Cell1);
			infoTable6.addCell(infoTable6Cell2);
			infoTable6.addCell(infoTable6Cell3);
			infoTable6.addCell(infoTable6Cell4);
			infoTable6.addCell(infoTable6Cell5);
			infoTable6.addCell(infoTable6Cell6);
			infoTable6.addCell(infoTable6Cell7);
			infoTable6.addCell(infoTable6Cell8);
			infoTable6.addCell(infoTable6Cell9);
			infoTable6.addCell(infoTable6Cell10);
			infoTable6.setHorizontalAlignment(PdfPCell.ALIGN_CENTER); 
			doc.add(infoTable6);

			Paragraph paraGap5 = new Paragraph(" ");
			paraGap5.setSpacingAfter(3);
			doc.add(paraGap5);

			Paragraph para1 = new Paragraph("We are constantly trying to improve our training provision,if you have any comments about"
					+ " this training,please let us know in the space below:",fontcontent);
			para1.setSpacingAfter(1);
			para1.setIndentationLeft(50);
			para1.setIndentationRight(35);
			para1.setAlignment(Element.ALIGN_LEFT);
			doc.add(para1);

			Paragraph para2 = new Paragraph("               ..........................................................................................................................");
			para2.setSpacingAfter(1);
			para2.setAlignment(Element.ALIGN_LEFT);
			doc.add(para2);

			Paragraph para3 = new Paragraph("               ..........................................................................................................................");
			para3.setSpacingAfter(1);
			para3.setAlignment(Element.ALIGN_LEFT);
			doc.add(para3);

			Paragraph para4 = new Paragraph("               ..........................................................................................................................");
			para4.setSpacingAfter(3);
			para4.setAlignment(Element.ALIGN_LEFT);
			doc.add(para4);

			Paragraph paraGap7 = new Paragraph(" ");
			paraGap7.setSpacingAfter(3);
			doc.add(paraGap7);

			PdfPTable infoTable7 = new PdfPTable(1);
			PdfPCell  infoTable7Cell1=new PdfPCell(new Phrase("Testimonial:",fontcontent));

			infoTable7Cell1.setMinimumHeight(40f);
			infoTable7.addCell(infoTable7Cell1);
			infoTable7.setHorizontalAlignment(PdfPCell.ALIGN_CENTER); 
			doc.add(infoTable7);

			Paragraph paraGap6 = new Paragraph(" ");
			paraGap6.setSpacingAfter(3);
			doc.add(paraGap6);


			Paragraph para5 = new Paragraph("Candidate Signature:_________________________                 Date:_______________________",fontcontent);
			para5.setSpacingAfter(3);
			para5.setIndentationLeft(50);
			para5.setIndentationRight(35);
			para5.setAlignment(Element.ALIGN_LEFT);
			doc.add(para5);
			doc.close();
			file.close();	 
		}catch(Exception e){
			e.printStackTrace();
		}
	}




	public static void createDatasheetForm(String folderPathWithFile){
		try{
			Document doc = new Document(PageSize.A4);
			File f1=new File(folderPathWithFile);
			OutputStream file = new FileOutputStream(f1);
			PdfWriter writer = PdfWriter.getInstance(doc, file);

			Font fontHead = FontFactory.getFont("Times-Roman", 14, Font.BOLD);    
			Font fontHead1 = FontFactory.getFont("Times-Roman", 12, Font.BOLD|Font.UNDERLINE);    


			Font fontcontent = FontFactory.getFont("Times-Roman", 14);    
			Font fontcontent1 = FontFactory.getFont("Times-Roman", 12);


			doc.open();

			PdfContentByte canvas = writer.getDirectContent();
			Rectangle rect = doc.getPageSize();
			rect.setBorder(Rectangle.BOX);
			rect.setBorderWidth(2); 
			rect.setUseVariableBorders(true); 
			canvas.rectangle(rect);

			Paragraph paraHead = new Paragraph("Employee Datasheet",fontHead1);
			paraHead.setAlignment(1);
			doc.add(paraHead);

			Paragraph para2 = new Paragraph("1. Name:",fontcontent1);
			doc.add(para2);
			Paragraph paraGap1 = new Paragraph(" ");
			doc.add(paraGap1);

			Paragraph para3 = new Paragraph("2. Skill: ",fontcontent1);
			doc.add(para3);
			Paragraph paraGap2 = new Paragraph(" ");
			doc.add(paraGap2);

			Paragraph para4 = new Paragraph("3. Visa/Immigration Status- (Mark whichever is applicable)",fontcontent1);
			doc.add(para4);

			List list = new List(false, 20);

			list.setListSymbol(new Chunk("•"));


			list.add(new ListItem("F1",fontcontent1));	
			list.add(new ListItem("	F1 OPT",fontcontent1));
			list.add(new ListItem("	F1 CPT",fontcontent1));

			list.add(new ListItem("	H1B",fontcontent1));	
			list.add(new ListItem("	H4",fontcontent1));
			list.add(new ListItem("	GC",fontcontent1));

			list.add(new ListItem("	US Citizen",fontcontent1));	
			list.add(new ListItem("L1",fontcontent1));
			list.add(new ListItem("L2",fontcontent1));

			doc.add(list);
			Paragraph paraGap3 = new Paragraph(" ");
			doc.add(paraGap3);
			Paragraph para5 = new Paragraph("(a) If on F1 OPT, please furnish the following details--",fontcontent1);
			doc.add(para5);



			List list1 = new List(false, 20);
			list1.setListSymbol(new Chunk("•"));
			list1.add(new ListItem("OPT Start Date:",fontcontent1));	
			list1.add(new ListItem("OPT End Date:",fontcontent1));
			list1.add(new ListItem("Whether this is your first OPT, or extension:",fontcontent1));

			doc.add(list1);
			Paragraph paraGap4 = new Paragraph(" ");
			doc.add(paraGap4);

			Paragraph para6 = new Paragraph("(b) If on F1 CPT, please furnish the following details--",fontcontent1);
			doc.add(para6);

			List list2 = new List(false, 20);
			list2.setListSymbol(new Chunk("•"));
			list2.add(new ListItem("CPT Start Date:",fontcontent1));	
			list2.add(new ListItem("CPT End Date:",fontcontent1));
			list2.add(new ListItem("Location Preference from your University:",fontcontent1));

			doc.add(list2);
			Paragraph paraGap5 = new Paragraph(" ");
			doc.add(paraGap5);

			Paragraph para7 = new Paragraph("4. When did you come to US? Mention the month and year.",fontcontent1);
			doc.add(para7);
			Paragraph paraGap6 = new Paragraph(" ");
			doc.add(paraGap6);

			Paragraph para8 = new Paragraph("5. On what Visa did you come to US?",fontcontent1);
			doc.add(para8);
			Paragraph paraGap7 = new Paragraph(" ");
			doc.add(paraGap7);

			Paragraph para9 = new Paragraph("6. What is your current location?",fontcontent1);
			doc.add(para9); 
			Paragraph paraGap8 = new Paragraph(" ");
			doc.add(paraGap8);

			Paragraph para10 = new Paragraph("7. What are your location preferences as far as full-time opportunity is concerned?",fontcontent1);
			doc.add(para10);
			Paragraph paraGap9 = new Paragraph(" ");
			doc.add(paraGap9);

			Paragraph para11 = new Paragraph("8. Do you have SSN? ",fontcontent1);
			doc.add(para11);

			List list3 = new List(false, 20);
			list3.setListSymbol(new Chunk(""));
			list3.add(new ListItem("[yes] If yes, where was your SSN issued?",fontcontent1));	
			list3.add(new ListItem("[yes] Mention the date (month and year) of issue of your SSN.",fontcontent1));
			list3.add(new ListItem("[yes]Have you shifted to any other place after issue of your SSN?",fontcontent1));

			doc.add(list3);
			Paragraph paraGap10 = new Paragraph(" ");
			doc.add(paraGap10);

			doc.newPage();

			Paragraph para12 = new Paragraph("9. Please mention your Date of Birth:",fontcontent1);
			doc.add(para12);
			Paragraph paraGap11 = new Paragraph(" ");
			doc.add(paraGap11);


			Paragraph para13 = new Paragraph("10. Details of your graduation:",fontcontent1);
			doc.add(para13);


			List list4 = new List(false, 20);
			list4.setListSymbol(new Chunk("•"));
			list4.add(new ListItem("Date (month and year):",fontcontent1));	
			list4.add(new ListItem("Location:",fontcontent1));
			list4.add(new ListItem("Duration:",fontcontent1));
			list4.add(new ListItem("Course Name: ",fontcontent1));
			doc.add(list4);	
			Paragraph paraGap12 = new Paragraph(" ");
			doc.add(paraGap12);

			Paragraph para14 = new Paragraph("11. Details of your post-graduation:",fontcontent1);
			doc.add(para14);

			List list5 = new List(false, 20);
			list5.setListSymbol(new Chunk("•"));
			list5.add(new ListItem("Date (month and year):",fontcontent1));	
			list5.add(new ListItem("Location:",fontcontent1));
			list5.add(new ListItem("Duration:",fontcontent1));
			list5.add(new ListItem("Course Name: ",fontcontent1));
			doc.add(list5);
			Paragraph paraGap13 = new Paragraph(" ");
			doc.add(paraGap13);

			Paragraph para15 = new Paragraph("12. If you have professional experience, please furnish the following details:",fontcontent1);
			doc.add(para15);

			List list6 = new List(false, 20);
			list6.setListSymbol(new Chunk(""));
			list6.add(new ListItem("[yes] Name of the Company:",fontcontent1));	
			list6.add(new ListItem("[yes] Location:",fontcontent1));
			list6.add(new ListItem("[yes] Designation:",fontcontent1));
			list6.add(new ListItem("[yes] Your tenure:",fontcontent1));
			list6.add(new ListItem("[yes] Domain (Healthcare, Finance, Banking, IT, Clinical, Engineering, etc):",fontcontent1));
			doc.add(list6);


			Paragraph para16 = new Paragraph("  ",fontcontent1);
			doc.add(para16);

			Paragraph para17 = new Paragraph("  ",fontcontent1);
			para17.setSpacingAfter(50f);
			doc.add(para17);

			Paragraph para18 = new Paragraph("Date:",fontcontent1);
			doc.add(para18);

			Paragraph paragap1 = new Paragraph("  ",fontcontent1);
			doc.add(paragap1);


			Paragraph para19 = new Paragraph("-------------------------",fontcontent1);
			doc.add(para19);

			Paragraph para20 = new Paragraph("Signature of Candidate",fontcontent1);
			doc.add(para20);


			PdfContentByte canvas22 = writer.getDirectContent();
			Rectangle rect22 = doc.getPageSize();
			rect22.setBorder(Rectangle.BOX); 
			rect22.setBorderWidth(2); 
			rect22.setUseVariableBorders(true); 
			canvas22.rectangle(rect22);

			doc.close();
			file.close();	

		}catch(Exception e){
			e.printStackTrace();
		}
	}



	public static void createPaymentDeclarationForm(String firstpaidAmount,String firsttxnId,String firsttxnDate,String secondpaidAmount,String secondtxnId,String secondtxnDate,String folderPathWithFile){
		try{
			Document doc = new Document(PageSize.A4);
			File f1=new File(folderPathWithFile);
			OutputStream file = new FileOutputStream(f1);
			PdfWriter writer = PdfWriter.getInstance(doc, file);

			Font fontcontent = FontFactory.getFont("Times-Roman", 10);    
			HeaderFooter header = new HeaderFooter(new Phrase("Payment Declaration Form",FontFactory.getFont(FontFactory.HELVETICA,10)), false);  
			doc.setHeader(header);
			header.setAlignment(1);
			doc.open();


			PdfPTable payeeInfo = new PdfPTable(4);
			PdfPCell  payeeInfoCell1=new PdfPCell(new Phrase("PAYEE'S INFORMATION :"));
			payeeInfoCell1.setColspan(4);
			PdfPCell  pName=new PdfPCell(new Phrase("Payee Full Name: ",fontcontent));

			PdfPCell  pAddress=new PdfPCell(new Phrase("Address: ",fontcontent));
			PdfPCell  pCity=new PdfPCell(new Phrase("City: ",fontcontent));
			PdfPCell  pState=new PdfPCell(new Phrase("State: ",fontcontent));
			PdfPCell  pZip=new PdfPCell(new Phrase("Zip: ",fontcontent));
			PdfPCell  pCountry=new PdfPCell(new Phrase("Country: ",fontcontent));
			PdfPCell  pMobile=new PdfPCell(new Phrase("Mobile: ",fontcontent));
			PdfPCell  pLandPhone=new PdfPCell(new Phrase("Phone: ",fontcontent));
			PdfPCell  pEmail=new PdfPCell(new Phrase("Email: ",fontcontent));

			pName.setColspan(4);
			pAddress.setColspan(4);
			pEmail.setColspan(2);

			payeeInfo.addCell(payeeInfoCell1);
			payeeInfo.addCell(pName);
			payeeInfo.addCell(pAddress);
			payeeInfo.addCell(pCity);
			payeeInfo.addCell(pState);
			payeeInfo.addCell(pZip);
			payeeInfo.addCell(pCountry);
			payeeInfo.addCell(pMobile);
			payeeInfo.addCell(pLandPhone);
			payeeInfo.addCell(pEmail);
			doc.add(payeeInfo);

			Paragraph paraGap3 = new Paragraph(" ");
			paraGap3.setSpacingAfter(6);
			doc.add(paraGap3);

			PdfPTable accPaye = new PdfPTable(1);
			PdfPCell accPayeCell = new PdfPCell(new Phrase("Acceptance by the Payee:"));
			accPayeCell.setBorder(PdfCell.NO_BORDER);
			accPaye.addCell(accPayeCell);
			
			Paragraph para4 = new Paragraph("  ");
			doc.add(para4);

			PdfPCell accPayeCell1 = new PdfPCell(new Phrase("I________________________________hereby,agree that the following payment has been made by me on "
					+ "behalf of_________________________________________________(Candidate's Name/Self) for the reference____________________________towards the___________________________trining  program  via_______________________date____________________",fontcontent));
			accPayeCell1.setBorder(PdfCell.NO_BORDER);
			accPaye.addCell(accPayeCell1);


			doc.add(accPaye);


			Paragraph para1 = new Paragraph("-------------------------------------------------------------------------------------------------------------------------------");
			doc.add(para1);


			PdfPTable txnTable = new PdfPTable(2);
			PdfPCell  txnTableCell1=new PdfPCell(new Phrase("Last transaction Details :",fontcontent));
			txnTableCell1.setBorder(PdfCell.NO_BORDER);
			txnTableCell1.setHorizontalAlignment(1);

			PdfPCell  txnTableCell2=new PdfPCell(new Phrase("Current transaction Details :",fontcontent));
			txnTableCell2.setBorder(PdfCell.NO_BORDER);
			txnTableCell2.setHorizontalAlignment(1);

			PdfPCell  txnTableCell3=new PdfPCell(new Phrase("Amount:$ "+firstpaidAmount+"                                             "
					+ " Transaction ID: "+firsttxnId+"                                                                           "
					+ "Payment Date: "+firsttxnDate+"                                                         ",fontcontent));

			txnTableCell3.setMinimumHeight(55f);

			PdfPCell  txnTableCell4=new PdfPCell(new Phrase("Amount:$ "+secondpaidAmount+"                                              "
					+ " Transaction ID: "+secondtxnId+"                                                                             "
					+ "Payment Date: "+secondtxnDate+"                                                        ",fontcontent));

			txnTableCell4.setMinimumHeight(55f);

			txnTable.addCell(txnTableCell1);
			txnTable.addCell(txnTableCell2);
			txnTable.addCell(txnTableCell3);
			txnTable.addCell(txnTableCell4);

			PdfPTable txnTable1 = new PdfPTable(2);
			PdfPCell  txnTableCell5=new PdfPCell(new Phrase("Payee's Signature:___________________                                              "
					+ "                                                                                                  "
					+ "Date:___________________                                                                           ",fontcontent));

			txnTableCell5.setBorder(PdfCell.NO_BORDER);
			PdfPCell  txnTableCell6=new PdfPCell(new Phrase("While scanning this document,please attach your ID proof here                                              "
					+ "                                                                             "
					+ "                                                           ",fontcontent));

			txnTableCell6.setMinimumHeight(80f);


			txnTable1.addCell(txnTableCell5);
			txnTable1.addCell(txnTableCell6);

			doc.add(txnTable);

			Paragraph paraGap4 = new Paragraph(" ");
			paraGap4.setSpacingAfter(6);
			doc.add(paraGap4);


			doc.add(txnTable1);


			doc.close();
			file.close();		

		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	public static void referenceCheckForm(String folderPathWithFile){
		try{
			Document doc = new Document(PageSize.A4);
			File f1=new File(folderPathWithFile);
			OutputStream file = new FileOutputStream(f1);
			PdfWriter writer = PdfWriter.getInstance(doc, file);
			
			Font fontHead = FontFactory.getFont("Times-Roman", 12, Font.BOLD);    
			Font fontHead1 = FontFactory.getFont("Times-Roman", 10, Font.BOLD);    
			Font fontHeadBanner = FontFactory.getFont("Times-Roman", 20, Font.BOLD);    


			Font fontcontent = FontFactory.getFont("Agency FB", 14);    
			Font fontcontent1 = FontFactory.getFont("Agency FB", 10);    
			Font fontcontent2 = FontFactory.getFont("Times-Roman",10);    
            
			HeaderFooter footer = new HeaderFooter(new Phrase("11210 Steeplecrest Dr, Suite 120, Houston, TX  77065, USA.  Phone: 1-832-906-3040, Fax: 832-220-9277. Email: info@globalitexperts.com"),false );  
			footer.setAlignment(1);
			doc.setFooter(footer); 
			
			
			
			doc.open();
			
			Image image1 = Image.getInstance("/home/ayant/UPLOAD_FILES/TRMS_v2/LOGO/COMPANY/global_it_experts.png");
			image1.scaleAbsolute(200f, 100f);

			PdfPTable infoHeaderTable = new PdfPTable(2);
			infoHeaderTable.setWidths(new float[] { 30,70 });
			PdfPCell  infoTableCell1=new PdfPCell();
			infoTableCell1.addElement(image1);
			PdfPCell  infoTableCell2=new PdfPCell();
			infoTableCell1.setMinimumHeight(30f);
			infoTableCell2.setBorder(Rectangle.BOTTOM);
			infoTableCell2.addElement(new Phrase("                                    Global IT Experts,Inc.",fontcontent));
			infoTableCell2.addElement(new Phrase("                                                    www.globalitexperts.com",fontcontent1));
			infoTableCell2.setHorizontalAlignment(2);

			infoHeaderTable.addCell(infoTableCell1);
			infoHeaderTable.addCell(infoTableCell2);	
			doc.add(infoHeaderTable);

			Paragraph paraGap = new Paragraph("");
			doc.add(paraGap);

			Paragraph paraName = new Paragraph("                  Name :",fontHead);
			doc.add(paraName);

			Paragraph paraDate = new Paragraph("                  Date :",fontHead);
			doc.add(paraDate);
			Paragraph paraText = new Paragraph("In order to work with you I need to have your two most previous supervisor references (at a minimum). "
					+ "Please get in touch with your references so they are prepared to talk to me during our interview/placement process. "
					+ "Thank you for your assistance in advance.",fontcontent2);
			PdfPTable infoTextTable = new PdfPTable(1);
			PdfPCell  infoTextTableCell=new PdfPCell();
			infoTextTableCell.setBorder(PdfPCell.NO_BORDER);
			infoTextTableCell.addElement(paraText);
			infoTextTable.addCell(infoTextTableCell);

			doc.add(infoTextTable);

			Paragraph paraGap1 = new Paragraph("    ");
			doc.add(paraGap1);

			PdfPTable infoBodyTable1 = new PdfPTable(2);
			infoBodyTable1.setWidths(new float[] { 85,15 });
			PdfPCell  infoBodyTable1cell1=new PdfPCell(new Phrase("Supervisors ",fontHead1));
			infoBodyTable1cell1.setBorder(PdfPCell.NO_BORDER);
			PdfPCell  infoBodyTable1cell2=new PdfPCell(new Phrase("May We Call?",fontcontent2));

			infoBodyTable1.addCell(infoBodyTable1cell1);
			infoBodyTable1.addCell(infoBodyTable1cell2);

			doc.add(infoBodyTable1);

			PdfPTable infoBodyTable2 = new PdfPTable(8);
			infoBodyTable2.setWidths(new float[] {15,15,15,10,15,20,5,5});
			
			PdfPCell  infoBodyTable2cell1=new PdfPCell(new Phrase("Name",fontcontent2));
			infoBodyTable2.addCell(infoBodyTable2cell1);
			PdfPCell  infoBodyTable2cell2=new PdfPCell(new Phrase("Title",fontcontent2));
			infoBodyTable2.addCell(infoBodyTable2cell2);

			PdfPCell  infoBodyTable2cell3=new PdfPCell(new Phrase("Company",fontcontent2));
			infoBodyTable2.addCell(infoBodyTable2cell3);
			PdfPCell  infoBodyTable2cell4=new PdfPCell(new Phrase("Team Size",fontcontent2));
			infoBodyTable2.addCell(infoBodyTable2cell4);
			PdfPCell  infoBodyTable2cell5=new PdfPCell(new Phrase("Phone #",fontcontent2));
			infoBodyTable2.addCell(infoBodyTable2cell5);
			PdfPCell  infoBodyTable2cell6=new PdfPCell(new Phrase("Email Address",fontcontent2));
			infoBodyTable2.addCell(infoBodyTable2cell6);
			PdfPCell  infoBodyTable2cell7=new PdfPCell(new Phrase("Yes",fontcontent2));
			infoBodyTable2.addCell(infoBodyTable2cell7);
			PdfPCell  infoBodyTable2cell8=new PdfPCell(new Phrase("No",fontcontent2));
			infoBodyTable2.addCell(infoBodyTable2cell8);

			for (int j = 0; j < 4; j++) {


				for (int i = 0; i < 7; i++) {
					infoBodyTable2.addCell(" ");

				}
			}


			doc.add(infoBodyTable2);


			Paragraph paraGap2 = new Paragraph("    ");
			doc.add(paraGap2);

			PdfPTable infoBodyTable3 = new PdfPTable(2);
			infoBodyTable3.setWidths(new float[] { 85,15 });
			PdfPCell  infoBodyTable3cell1=new PdfPCell(new Phrase("Peers ",fontHead1));
			infoBodyTable3cell1.setBorder(PdfPCell.NO_BORDER);
			PdfPCell  infoBodyTable3cell2=new PdfPCell(new Phrase("May We Call?",fontcontent2));

			infoBodyTable3.addCell(infoBodyTable3cell1);
			infoBodyTable3.addCell(infoBodyTable3cell2);

			doc.add(infoBodyTable3);

			PdfPTable infoBodyTable4 = new PdfPTable(7);
			infoBodyTable4.setWidths(new float[] {15,15,20,15,25,5,5});
			
			PdfPCell  infoBodyTable4cell1=new PdfPCell(new Phrase("Name",fontcontent2));
			infoBodyTable4.addCell(infoBodyTable4cell1);
			PdfPCell  infoBodyTable4cell2=new PdfPCell(new Phrase("Title",fontcontent2));
			infoBodyTable4.addCell(infoBodyTable4cell2);

			PdfPCell  infoBodyTable4cell3=new PdfPCell(new Phrase("Company",fontcontent2));
			infoBodyTable4.addCell(infoBodyTable4cell3);
			PdfPCell  infoBodyTable4cell5=new PdfPCell(new Phrase("Phone #",fontcontent2));
			infoBodyTable4.addCell(infoBodyTable4cell5);
			PdfPCell  infoBodyTable4cell6=new PdfPCell(new Phrase("Email Address",fontcontent2));
			infoBodyTable4.addCell(infoBodyTable4cell6);
			PdfPCell  infoBodyTable4cell7=new PdfPCell(new Phrase("Yes",fontcontent2));
			infoBodyTable4.addCell(infoBodyTable4cell7);
			PdfPCell  infoBodyTable4cell8=new PdfPCell(new Phrase("No",fontcontent2));
			infoBodyTable4.addCell(infoBodyTable4cell8);

			for (int j = 0; j < 4; j++) {


				for (int i = 0; i < 7; i++) {
					infoBodyTable4.addCell(" ");

				}
			}


			doc.add(infoBodyTable4);

			
			Paragraph paraGap3 = new Paragraph("    ");
			doc.add(paraGap3);

			PdfPTable infoBodyTable5 = new PdfPTable(2);
			infoBodyTable5.setWidths(new float[] { 85,15 });
			PdfPCell  infoBodyTable5cell1=new PdfPCell(new Phrase("Subordinates ",fontHead1));
			infoBodyTable5cell1.setBorder(PdfPCell.NO_BORDER);
			PdfPCell  infoBodyTable5cell2=new PdfPCell(new Phrase("May We Call?",fontcontent2));

			infoBodyTable5.addCell(infoBodyTable5cell1);
			infoBodyTable5.addCell(infoBodyTable5cell2);

			doc.add(infoBodyTable5);

			PdfPTable infoBodyTable6 = new PdfPTable(7);
			infoBodyTable6.setWidths(new float[] {15,15,20,15,25,5,5});
			
			PdfPCell  infoBodyTable6cell1=new PdfPCell(new Phrase("Name",fontcontent2));
			infoBodyTable6.addCell(infoBodyTable6cell1);
			PdfPCell  infoBodyTable6cell2=new PdfPCell(new Phrase("Title",fontcontent2));
			infoBodyTable6.addCell(infoBodyTable6cell2);

			PdfPCell  infoBodyTable6cell3=new PdfPCell(new Phrase("Company",fontcontent2));
			infoBodyTable6.addCell(infoBodyTable6cell3);
			PdfPCell  infoBodyTable6cell5=new PdfPCell(new Phrase("Phone #",fontcontent2));
			infoBodyTable6.addCell(infoBodyTable6cell5);
			PdfPCell  infoBodyTable6cell6=new PdfPCell(new Phrase("Email Address",fontcontent2));
			infoBodyTable6.addCell(infoBodyTable6cell6);
			PdfPCell  infoBodyTable6cell7=new PdfPCell(new Phrase("Yes",fontcontent2));
			infoBodyTable6.addCell(infoBodyTable6cell7);
			PdfPCell  infoBodyTable6cell8=new PdfPCell(new Phrase("No",fontcontent2));
			infoBodyTable6.addCell(infoBodyTable6cell8);

			for (int j = 0; j < 4; j++) {


				for (int i = 0; i < 7; i++) {
					infoBodyTable6.addCell(" ");

				}
			}


			doc.add(infoBodyTable6);
			
			Paragraph paraGap4 = new Paragraph("    ");
			doc.add(paraGap4);

			PdfPTable infoBodyTable7 = new PdfPTable(1);
			PdfPCell  infoBodyTable7cell1=new PdfPCell(new Phrase("Please indicate the companies in which you are currently being considered for employment or have interviewed in the last 6 months:",fontHead1));
			infoBodyTable7cell1.setBorder(PdfPCell.NO_BORDER);
			infoBodyTable7.addCell(infoBodyTable7cell1);

			doc.add(infoBodyTable7);

			PdfPTable infoBodyTable8 = new PdfPTable(5);
			
			PdfPCell  infoBodyTable8cell1=new PdfPCell(new Phrase("Company",fontcontent2));
			infoBodyTable8.addCell(infoBodyTable8cell1);
			PdfPCell  infoBodyTable8cell2=new PdfPCell(new Phrase("Role",fontcontent2));
			infoBodyTable8.addCell(infoBodyTable8cell2);

			PdfPCell  infoBodyTable8cell3=new PdfPCell(new Phrase("Stage in process",fontcontent2));
			infoBodyTable8.addCell(infoBodyTable8cell3);
			PdfPCell  infoBodyTable8cell5=new PdfPCell(new Phrase("Contract or Full Time",fontcontent2));
			infoBodyTable8.addCell(infoBodyTable8cell5);
			PdfPCell  infoBodyTable8cell6=new PdfPCell(new Phrase("If Contract, list the agency",fontcontent2));
			infoBodyTable8.addCell(infoBodyTable8cell6);
			
			for (int j = 0; j < 10; j++) {


				for (int i = 0; i < 5; i++) {
					infoBodyTable8.addCell(" ");

				}
			}


			doc.add(infoBodyTable8);

			doc.newPage();
			
			Image image2 = Image.getInstance("/home/ayant/UPLOAD_FILES/TRMS_v2/LOGO/COMPANY/global_it_experts.png");
			                                 
			image2.scaleAbsolute(200f, 100f);

			PdfPTable infoHeaderTable1 = new PdfPTable(2);
			infoHeaderTable1.setWidths(new float[] { 30,70 });
			PdfPCell  infoTable1Cell1=new PdfPCell();
			infoTable1Cell1.addElement(image1);
			PdfPCell  infoTable1Cell2=new PdfPCell();
			infoTable1Cell1.setMinimumHeight(0f);
			infoTable1Cell2.setBorder(Rectangle.BOTTOM);
			infoTable1Cell2.addElement(new Phrase("                                    Global IT Experts,Inc.",fontcontent));
			infoTable1Cell2.addElement(new Phrase("                                                    www.globalitexperts.com",fontcontent1));
			
			infoTable1Cell2.setHorizontalAlignment(2);

			infoHeaderTable1.addCell(infoTable1Cell1);
			infoHeaderTable1.addCell(infoTable1Cell2);	
			doc.add(infoHeaderTable1);

			Paragraph paraGap5 = new Paragraph("    ");
			doc.add(paraGap5);
			
			Paragraph paraGap6 = new Paragraph("    ");
			doc.add(paraGap6);
			
			Paragraph newPagePara1 = new Paragraph("                  What are the top five things you are looking for in your next role?",fontHead1);
			doc.add(newPagePara1);
			
			Paragraph paraGap7 = new Paragraph("    ");
			doc.add(paraGap7);
			
			Paragraph newPagePara2 = new Paragraph("Team size should be same as mentioned to the vendor. If your two references are from same project, then ensure they know each other.",fontHeadBanner);
			doc.add(newPagePara2);
			
			doc.close();
			file.close();	
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}


}