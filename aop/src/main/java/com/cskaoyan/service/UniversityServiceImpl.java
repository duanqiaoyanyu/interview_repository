package com.cskaoyan.service;

import com.cskaoyan.annotation.University;
import com.cskaoyan.pojo.UniversityDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author duanqiaoyanyu
 * @date 2022/10/12 11:03
 */
@Slf4j
@Service
public class UniversityServiceImpl implements UniversityService {

    @Override
    @University(province = "Jiangsu", city = "NanJing")
    public UniversityDTO entrance() {
        log.info("入学仪式...");

        UniversityDTO dto = new UniversityDTO();
        dto.setId(2022L);
        dto.setName("NanJing Tech University");

        return dto;
    }

}
