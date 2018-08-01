package com.ayantsoft.trms.service;

import java.io.Serializable;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import com.ayantsoft.trms.hibernate.dao.DocumentsDao;
import com.ayantsoft.trms.hibernate.pojo.Documents;

@ManagedBean
@ApplicationScoped
public class DocumentsService implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 6978294485941396449L;
	
	
	@ManagedProperty(value="#{documentsDao}")
	private DocumentsDao documentsDao ;
	
	
	public void saveDocuments(Documents documents)
	{
		documentsDao.save(documents);
	}	
	
	public void deleteDocuments(Documents documents)
	{
		documentsDao.delete(documents);
	}
	
	public DocumentsDao getDocumentsDao() {
		return documentsDao;
	}

	public void setDocumentsDao(DocumentsDao documentsDao) {
		this.documentsDao = documentsDao;
	}

	
}