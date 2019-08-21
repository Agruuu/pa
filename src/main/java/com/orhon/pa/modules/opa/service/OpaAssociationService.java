package com.orhon.pa.modules.opa.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.orhon.pa.common.service.CrudService;
import com.orhon.pa.modules.opa.dao.OpaAssociationDao;
import com.orhon.pa.modules.opa.entity.OpaAssociation;

@Service
@Transactional(readOnly = true)
public class OpaAssociationService extends CrudService<OpaAssociationDao,OpaAssociation>{
	
	public OpaAssociation get(String id) {
		OpaAssociation opaAssociation=super.get(id);
		return opaAssociation;
	}
	

	@Transactional(readOnly = true)
	public void save(OpaAssociation opaAssociation) {
		super.save(opaAssociation);
	}

}
