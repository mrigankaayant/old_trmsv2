package com.ayantsoft.trms.jsf.converter;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import com.ayantsoft.trms.hibernate.pojo.CandidateRemarks;
import com.ayantsoft.trms.jsf.view.AppDataBean;


@ManagedBean
@FacesConverter(value = "candidateRemarksConverter")
public class CandidateRemarksConverter implements Converter{




	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		try{
			AppDataBean appDataBean=context.getApplication().evaluateExpressionGet(context,"#{appDataBean}" ,AppDataBean.class);
			if(value!=null){
				Integer courseId=Integer.parseInt(value);
				List<CandidateRemarks> candidateRemarks=appDataBean.getCandidateStatusesTillEnrolled();
				for(CandidateRemarks cr:candidateRemarks){
					if(cr.getId()==courseId){
						return cr;
					}
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value!=null && value instanceof CandidateRemarks){

			CandidateRemarks cr=(CandidateRemarks) value;
			return cr.getId()+"";
		}
		return null;
	}

}