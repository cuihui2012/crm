package com.neuedu.crm.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neuedu.crm.mapper.CustomerMapper;
import com.neuedu.crm.mapper.FollowUpMapper;
import com.neuedu.crm.pojo.FollowUp;
import com.neuedu.crm.pojo.FollowUpExample;
import com.neuedu.crm.service.IFollowUpService;
import com.neuedu.crm.service.IUserService;
/**
 * 
 * @author cuihui
 * @date 2018/07/24
 */
@Service
@Transactional(rollbackFor=Exception.class)
public class FollowUpServiceImpl implements IFollowUpService {

	@Autowired
	private FollowUpMapper followUpMapper;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private CustomerMapper customerMapper;
	
	@Override
	public long countByFollowUpExample(FollowUpExample followUpExample) {
		return followUpMapper.countByExample(followUpExample);
	}

	@Override
	public boolean deleteByFollowUpExample(FollowUpExample followUpExample) {
		return followUpMapper.deleteByExample(followUpExample) > 0 ? true : false;
	}

	@Override
	public boolean deleteByPrimaryKey(Integer id) {
		return followUpMapper.deleteByPrimaryKey(id) > 0 ? true : false;
	}

	@Override
	public boolean insertFollowUp(FollowUp followUp) {
		return followUpMapper.insert(followUp) > 0 ? true : false;
	}

	@Override
	public boolean insertSelective(FollowUp followUp) {
		return followUpMapper.insertSelective(followUp) > 0 ? true : false;
	}

	@Override
	public Map<String,List<FollowUp>> selectByFollowUpExample(FollowUpExample followUpExample) {
	    List<FollowUp> list = followUpMapper.selectByExample(followUpExample);
		Map<String,List<FollowUp>> map = new HashMap<String,List<FollowUp>>();
	    for(FollowUp followUp : list) {
	        if(followUp.getCustomerId() != null) {
                followUp.setCustomer(customerMapper.selectByPrimaryKey(followUp.getCustomerId()));
            }
            if(followUp.getManagerId() != null) {
                followUp.setManager(userService.findById(followUp.getManagerId()));
            }
            //对跟进记录进行分组
			if(map.containsKey(followUp.getCustomer().getName())){
				map.get(followUp.getCustomer().getName()).add(followUp);
			} else {
				List<FollowUp> groupList = new ArrayList<FollowUp>();
				groupList.add(followUp);
				map.put(followUp.getCustomer().getName(),groupList);
			}
	    }
		return map;
	}

	@Override
	public FollowUp selectFollowUpByPrimaryKey(Integer id) {
	    FollowUp followUp = followUpMapper.selectByPrimaryKey(id);
	    if(followUp != null) {
	        if(followUp.getCustomerId() != null) {
	            followUp.setCustomer(customerMapper.selectByPrimaryKey(followUp.getCustomerId()));
	        }
	        if(followUp.getManagerId() != null) {
	            followUp.setManager(userService.findById(followUp.getManagerId()));
	        }
	    }
		return followUp;
	}

	@Override
	public boolean updateByFollowUpExampleSelective(FollowUp followUp, FollowUpExample followUpExample) {
		return followUpMapper.updateByExampleSelective(followUp, followUpExample) > 0 ? true : false;
	}

	@Override
	public boolean updateByFollowUpExample(FollowUp followUp, FollowUpExample followUpExample) {
		return followUpMapper.updateByExample(followUp, followUpExample) > 0 ? true : false;
	}

	@Override
	public boolean updateFollowUpByPrimaryKeySelective(FollowUp followUp) {
		return followUpMapper.updateByPrimaryKeySelective(followUp) > 0 ? true : false;
	}

	@Override
	public boolean updateFollowUpByPrimaryKey(FollowUp followUp) {
		return followUpMapper.updateByPrimaryKey(followUp) > 0 ? true : false ;
	}

}
